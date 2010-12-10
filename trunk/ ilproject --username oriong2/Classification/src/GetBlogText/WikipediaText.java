package GetBlogText;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class WikipediaText {
	
	public static String GetTextFromWikipedia(String sWord) throws Exception {
	    // Set the HTTP referrer to your website address.
	    Translate.setHttpReferrer("en-es");

	    String translatedText = Translate.execute(sWord, Language.ENGLISH, Language.SPANISH);
	    translatedText = translatedText.replace(".", "");
	    
	    String sUrlWipkipedia = "http://es.wikipedia.org/wiki/";
	    sUrlWipkipedia += translatedText;
	    String sResultText = ExtractText.GetBlogText(sUrlWipkipedia);
	    return sResultText;
	    
	  }
}
