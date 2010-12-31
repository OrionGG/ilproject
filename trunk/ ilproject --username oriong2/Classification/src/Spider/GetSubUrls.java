package Spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GetBlogText.ExtractText;



public class GetSubUrls {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParserException 
	 */
	public static void main(String[] args) throws IOException {
		String sUrl = "http://www.elpais.com/gentetv/";
		//String sUrl = "http://wordpress.com/";
		//String sTail = "blogalia.com";
		String sTail = "";
		//List<String> oList = SpiderUrl(sUrl, sTail);
		List<String> oList = SpiderUrl(sUrl, 1,1000, sTail);
		Map<String,String> oMap = new HashMap<String, String>();
		for(String sSubUrl : oList){
			String sText = ExtractText.GetBlogText(sSubUrl);
			oMap.put(sSubUrl, sText);
		
		}
	}

	private static List<String> SpiderUrl(String sUrl, String sTail) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		//URL oURL = new URL("http://www.blogia.com");
		URL oURL = new URL(sUrl);
		
		List<String> oList = oApacheURLLister.listAll(oURL, 2, 1000, sTail);
		return oList;
	}
	
	public static List<String> SpiderUrl(String sUrl, int depth, int wide, String sTail) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		//URL oURL = new URL("http://www.blogia.com");
		URL oURL = new URL(sUrl);
		
		List<String> oList = oApacheURLLister.listAll(oURL, depth, wide, sTail);
		return oList;
	}
	
	public static List<String> DefaultSpiderUrl(String sUrl) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		//URL oURL = new URL("http://www.blogia.com");
		URL oURL = new URL(sUrl);
		
		List<String> oList = oApacheURLLister.listAll(oURL, 2, 1000, "");
		return oList;
	}

}
