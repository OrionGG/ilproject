package GetText;
import net.htmlparser.jericho.*;
import java.util.*;
import java.io.*;
import java.net.*;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.WordlistLoader;



import encoders.Encode;

public class ExtractText {
	
	public static void main(String[] args) throws Exception {
		String sourceUrlString="http://terceranfiteatro.wordpress.com/2010/12/11/44/";
		if (args.length==0)
		  System.err.println("Using default argument of \""+sourceUrlString+'"');
		else
			sourceUrlString=args[0];
		if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
		//GetBlogInfo(sourceUrlString);
		String sResult= GetBlogText(sourceUrlString);
		System.out.println(sResult);
		
  }

	private static void GetBlogInfo(String sourceUrlString) throws IOException,
			MalformedURLException {
		MicrosoftTagTypes.register();
		PHPTagTypes.register();
		PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
		MasonTagTypes.register();
		Source source=new Source(new URL(sourceUrlString));

		// Call fullSequentialParse manually as most of the source will be parsed.
		source.fullSequentialParse();

		System.out.println("Document title:");
		String title=getTitle(source);
		System.out.println(title==null ? "(none)" : title);

		System.out.println("\nDocument description:");
		String description=getMetaValue(source,"description");
		System.out.println(description==null ? "(none)" : description);

		System.out.println("\nDocument keywords:");
		String keywords=getMetaValue(source,"keywords");
		System.out.println(keywords==null ? "(none)" : keywords);
	
		System.out.println("\nLinks to other documents:");
		List<Element> linkElements=source.getAllElements(HTMLElementName.A);
		for (Element linkElement : linkElements) {
			String href=linkElement.getAttributeValue("href");
			if (href==null) continue;
			// A element can contain other tags so need to extract the text from it:
			String label=linkElement.getContent().getTextExtractor().toString();
			System.out.println(label+" <"+href+'>');
		}

		System.out.println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
		System.out.println(source.getTextExtractor().setIncludeAttributes(true).toString());

		System.out.println("\nSame again but this time extend the TextExtractor class to also exclude text from P elements and any elements with class=\"control\":\n");
		TextExtractor textExtractor=new TextExtractor(source) {
			public boolean excludeElement(StartTag startTag) {
				return startTag.getName()==HTMLElementName.P || "control".equalsIgnoreCase(startTag.getAttributeValue("class"));
			}
		};
		System.out.println(textExtractor.setIncludeAttributes(true).toString());
	}
	
	public static String GetBlogText(String sourceUrlString) throws IOException, MalformedURLException{
		
		return GetBlogText(sourceUrlString, true);
	}
	
	public static String GetBlogText(String sourceUrlString, Boolean bDeleteSpecialChars) throws IOException,	
		MalformedURLException {
		MicrosoftTagTypes.register();
		PHPTagTypes.register();
		PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
		MasonTagTypes.register();
		
		String sResult = "";
		String sResultTextExtractor= "";

		try{
			Source source=new Source(new URL(sourceUrlString));

			// Call fullSequentialParse manually as most of the source will be parsed.
			source.fullSequentialParse();

			TextExtractor oTextExtractor = source.getTextExtractor();
			//sResult = oTextExtractor.setExcludeNonHTMLElements(true).setIncludeAttributes(true).toString();
			sResultTextExtractor = oTextExtractor.setIncludeAttributes(true).toString();
		
			/*if(bDeleteSpecialChars){
				sResult = DeleteSpecialChars(sResultTextExtractor);
			}
			else{
				sResult = sResultTextExtractor;
			}*/
			sResult = sResultTextExtractor;
		}
		catch(Exception ex){
		}
		
		return sResult;
		
	}

	/*private static String DeleteSpecialChars(String sResultTextExtractor)
			throws IOException, UnsupportedEncodingException {
		char[] cArray = sResultTextExtractor.toCharArray();
		//ArrayList<String> oSpecialCharsList = SpecialChars.getSpecialChars();
		Set<String> oSpecialCharsList = new HashSet<String>();
		try{
			oSpecialCharsList = new HashSet<String>(WordlistLoader.getWordSet(new File(".\\resources\\specialwords\\specialSpanishSmart.txt")));
    	}
    	catch(IOException oEx)
    	{
    		List<String> list = Arrays.asList(SPECIAL_WORDS);
    		oSpecialCharsList = new HashSet<String>(list); 
    	}
		String sResult= "";
		for (char c : cArray){
			String sStringC = new String(String.valueOf(c).getBytes("UTF8"), "US-Ascii");
			boolean bSpecialWord = false;
			for(String sSpecialWord:oSpecialCharsList){
				sSpecialWord = new String(sSpecialWord.getBytes("UTF8"), "US-Ascii");
				if(sSpecialWord.equals(sStringC)){
					bSpecialWord = true;
					break;
				}
			}
			if(!bSpecialWord){
				sResult += c;
			}
		}
		return sResult;
	}
	
	private static String SPECIAL_WORDS[] = {".", ",", "(", ")", "/", "|", "[", "]", "*", "?", "←", ":", "-", "_", "!", "="};
*/
	private static String getTitle(Source source) {
		Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
		if (titleElement==null) return null;
		// TITLE element never contains other tags so just decode it collapsing whitespace:
		return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
	}

	private static String getMetaValue(Source source, String key) {
		for (int pos=0; pos<source.length();) {
			StartTag startTag=source.getNextStartTag(pos,"name",key,false);
			if (startTag==null) return null;
			if (startTag.getName()==HTMLElementName.META)
				return startTag.getAttributeValue("content"); // Attribute values are automatically decoded
			pos=startTag.getEnd();
		}
		return null;
	}
}
