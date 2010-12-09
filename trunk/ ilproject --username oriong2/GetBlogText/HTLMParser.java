
import org.htmlparser.*;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.*;
import org.htmlparser.tags.Div;
import org.htmlparser.util.*;

public class HTLMParser {

	/**
	 * @param args
	 * @throws ParserException 
	 */
	public static void main(String[] args) throws ParserException {
		if(args.length > 0){
			String sUrl = args[0];
			//String sUrl = "http://pacozamora.blogia.com/2010/112201-investigue-sobre-el-cambio-climatico-desde-su-casa.php";
			GetBlogText(sUrl);
		}
	}


	public static String GetBlogText(String sUrl) throws ParserException {
		// TODO Auto-generated method stub
		//Parser parser = new Parser ("http://gatopardo.blogia.com/");
		//Parser parser = new Parser ("http://www.google.es");

		String sResult = "";
		Parser parser = new Parser (sUrl);
		
		NodeList nl = parser.parse (null);
		//NodeFilter filter = new HasAttributeFilter("class", "articulo");
		//NotFilter filter = new NotFilter(new TagNameFilter("script"));NotFilter filter = new NotFilter(new HasAttributeFilter("type", "text/javascript"));
		//NodeFilter filter = new HasAttributeFilter("type", "text/javascript");
		
		NotFilter filter = new NotFilter(new HasAttributeFilter("type", "text/javascript"));
		//NodeList list = nl.extractAllNodesThatMatch(filter, true);
		NodeList list = nl.extractAllNodesThatMatch(filter, true);
		 for (NodeIterator i = list.elements (); i.hasMoreNodes (); )
			 sResult += HTLMParser.processMyNodes (i.nextNode ());
		 return sResult;
	}
	

	static String processMyNodes (Node node) throws ParserException
	 {
		String sResult = "";
	     if (node instanceof TextNode)
	     {
	         // downcast to TextNode
	         TextNode text = (TextNode)node;
	         String sText = Translate.decode (text.getText ());
	         // do whatever processing you want with the text
	         sResult = sText;
	         System.out.println (sText);
	     }
	     else if (node instanceof TagNode)
	     {
	         // downcast to TagNode
	         TagNode tag = (TagNode)node;
	         // do whatever processing you want with the tag itself
	         // ...
	         // process recursively (nodes within nodes) via getChildren()
	         NodeList nl = tag.getChildren ();
	         if (null != nl)
	             for (NodeIterator i = nl.elements (); i.hasMoreNodes (); )
	                 sResult +=  processMyNodes (i.nextNode ());
	     }
	     
	     return sResult;
	 }

}
