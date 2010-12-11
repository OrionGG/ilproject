package GetBlogText;

import java.util.Set;

import net.didion.jwnl.JWNLException;

import CategoryGenerator.Synonym;

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
	    // Set the HTTP referrer to your website address.
	    Translate.setHttpReferrer("en-es");

	    String translatedText = Translate.execute(sWord, Language.ENGLISH, Language.SPANISH);
	    translatedText = translatedText.replace(".", "");
	    translatedText = translatedText.replace(" ", "");
	    String sUrlWipkipedia = "http://es.wikipedia.org/wiki/";
	    sUrlWipkipedia += translatedText;
	    String sResultText = ExtractText.GetBlogText(sUrlWipkipedia);
	    return sResultText;
	    
	  }
}
