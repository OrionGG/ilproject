package Language;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;

public class Traductor {

	public static String Translate(String sWord) throws Exception {
		Translate.setHttpReferrer("en-es");
		String translatedText;
		try{
		 translatedText = Translate.execute(sWord, Language.ENGLISH, Language.SPANISH);
		}catch(Exception e)
		{
			translatedText="";
		}
		
		return translatedText;
	}
}
