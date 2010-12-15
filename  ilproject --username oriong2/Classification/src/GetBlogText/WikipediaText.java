package GetBlogText;

import java.util.*;

import net.didion.jwnl.JWNLException;

import Language.Synonym;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class WikipediaText {	
	public static void main(String[] args) throws JWNLException  {
		//Set<String> oSyn = lookupSynonyms(args[0]);

		String stext;
		try {
			stext = GetTextFromWikipedia("wing");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Set<String> oSyn = Synonym.lookupSynonyms("wing");
		for(String sWord:oSyn){

			String sTextSynonym;
			try {
				sTextSynonym = WikipediaText.GetTextFromWikipedia(sWord);
			} catch (Exception e) {
			}
		}
	}

	public static String GetTextFromWikipedia(String sWord) throws Exception {
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
