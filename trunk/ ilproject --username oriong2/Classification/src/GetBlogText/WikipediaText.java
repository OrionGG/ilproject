package GetBlogText;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import net.didion.jwnl.JWNLException;

import Analizer.SpanishAnalyzer;
import Language.Synonym;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

import net.sourceforge.jwbf.mediawiki.bots.*;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.contentRep.*;

public class WikipediaText {	
	ArrayList<String> aSearchedWords = new ArrayList<String>();
	public static void main(String[] args) throws JWNLException  {
		//Set<String> oSyn = lookupSynonyms(args[0]);

		WikipediaText oWikipediaText = new WikipediaText();
		String stext;
		try {

			stext = oWikipediaText.GetTextFromWikipedia("wing", true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Set<String> oSyn = Synonym.lookupSynonyms("wing");
		for(String sWord:oSyn){

			String sTextSynonym;
			try {
				sTextSynonym = oWikipediaText.GetTextFromWikipedia(sWord, true);
			} catch (Exception e) {
			}
		}
	}

	/*	public static String GetTextFromWikipedia(String sWord) throws Exception {
		String sResultText = null;
		try{
			// Set the HTTP referrer to your website address.
			Translate.setHttpReferrer("en-es");

			String translatedText = Translate.execute(sWord, Language.ENGLISH, Language.SPANISH);
			translatedText = translatedText.replace(".", "");
			translatedText = translatedText.replace(" ", "");
			String sUrlWipkipedia = "http://es.wikipedia.org/wiki/";
			sUrlWipkipedia += translatedText;
			sResultText = ExtractText.GetBlogText(sUrlWipkipedia);
		}
		catch(Exception e){
			sResultText = "";
		}
		sResultText = RemoveSomeText(sResultText);
		sResultText = RemoveFromText(sResultText);

		return sResultText;

	}*/

	/**
	 * We'll use this pattern as divider to split the string into an array.
	 * Usage: myString.split(DIVIDER_PATTERN);
	 */
	private static final String DIVIDER_PATTERN =

		"(?<=[^\\p{Upper}])(?=\\p{Upper})"
		// either there is anything that is not an uppercase character
		// followed by an uppercase character

		+ "|(?<=[\\p{Lower}])(?=\\d)"
		// or there is a lowercase character followed by a digit

		;

	public String GetTextFromWikipedia(String sWord, Boolean iSInEnglish) throws Exception {

		String sResult = "";
		sWord = sWord.replace("_", " ");
		sWord = splitOnCapitals(sWord);
		if(iSInEnglish){

			try{
				// Set the HTTP referrer to your website address.
				Translate.setHttpReferrer("en-es");

				String translatedText = Translate.execute(sWord, Language.ENGLISH, Language.SPANISH);
				translatedText = translatedText.replace(".", "");
				//translatedText = translatedText.replace(" ", "");
				sWord = translatedText;
				
				System.out.println(sWord);
			}
			catch(Exception e){
				sWord = sWord;
			}
		}

		try {
			MediaWikiBot oMediaWikiBot = new MediaWikiBot("http://es.wikipedia.org/w/");
			//b.login("UserName", "***");
			//SimpleArticle sa = new SimpleArticle(b.readContent("Main Page"));

			sResult = readData(sWord, oMediaWikiBot);
			if(sResult ==""){
				sResult = emptyTextInWikipedia(sWord, oMediaWikiBot);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sResult;
	}

	private String readData(String sWord, MediaWikiBot oMediaWikiBot)
	throws ActionException, ProcessException {
		String sResult = "";
		if(!aSearchedWords.contains(sWord.toLowerCase())){
			SimpleArticle sa = oMediaWikiBot.readData(sWord);

			if(sa.isRedirect() || sa.getText().trim().startsWith("#REDIRECC")){
				String saText = sa.getText();
				int lastOpenBraker = saText.lastIndexOf("[");
				int firstCloseBraker = saText.indexOf("]", lastOpenBraker);
				String sNewWord = saText.substring(lastOpenBraker + 1, firstCloseBraker);
				sa = oMediaWikiBot.readData(sNewWord);
			}
			sResult = sa.getText();

			aSearchedWords.add(sWord.toLowerCase());
		}
		
		return sResult;
	}

	private String emptyTextInWikipedia(String sWord, MediaWikiBot oMediaWikiBot) throws ActionException, ProcessException, IOException {
		String sResult = "";
		String[] sWordList = sWord.split(" ");
		if(sWordList.length > 1){

			//clean stopwords
			Reader stringReader = new StringReader(sWord); 
			TokenStream result = new StandardTokenizer(Version.LUCENE_30, stringReader);
			Set<Object> stopTable = StopFilter.makeStopSet(SpanishAnalyzer.SPANISH_STOP_WORDS);  
			TokenStream tokenStream = new StopFilter(true ,result, stopTable);
			String sText =  (new Analizer.test.TermAnalyzerView()).GetView(tokenStream, 0).trim();

			String[] sTextList = sText.split(" ");
			for(String sWordDivided:sTextList){
				sResult += readData(sWordDivided, oMediaWikiBot) + " ";
			}
		}

		return sResult.trim();
	}

	private static String splitOnCapitals(String sWord) {
		String sResult = "";
		String[] sWordList = sWord.split(DIVIDER_PATTERN);
		for(String sWordDivided:sWordList){
			sResult += sWordDivided + " ";
		}

		return sResult.trim();

	}

	private static String RemoveFromText(String sResultText) {
		String sTextToRemove = "Herramientas personales RegistrarseEntrar";
		int iStart = sResultText.indexOf(sTextToRemove);
		if(0 < iStart){
			StringBuffer sStringBuffer = new StringBuffer(sResultText);
			sStringBuffer.replace(iStart, sStringBuffer.length(), "");

			sResultText = sStringBuffer.toString();
		}
		return sResultText;
	}

	private static String RemoveSomeText(String sResultText) {
		List<String> sTextToRemove = new ArrayList<String>();
		sTextToRemove.add("Wikipedia la enciclopedia libre");
		sTextToRemove.add("De  Saltar a navegación búsqueda");
		sTextToRemove.add("El texto está disponible bajo la Licencia Creative Commons Atribución Compartir Igual 3.0; podrían ser aplicables cláusulas adicionales. Lee los términos de uso para más información. Política de privacidad Acerca de Wikipedia Descargo de responsabilidad");
		sTextToRemove.add("Wikcionario Esta es una página de desambiguación una ayuda a la navegación que cataloga páginas que de otra forma compartirían un mismo título. Si llegaste aquí a través de un enlace interno regresa por favor para corregirlo de modo que apunte al artículo apropiado.");
		for(String sText:sTextToRemove){
			sResultText = sResultText.replaceAll(sText, "");
		}
		return sResultText;
	}
}
