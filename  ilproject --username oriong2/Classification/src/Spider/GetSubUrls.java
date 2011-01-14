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
		
		String sMainUrl = "http://www.elpais.com";
		String sRestUrl = "/deportes/";
		//String sUrl = "http://wordpress.com/";
		//String sTail = "blogalia.com";
		String sTail = "";
		//List<String> oList = SpiderUrl(sUrl, sTail);
		List<String> oList = SpiderUrl(sMainUrl, sRestUrl, 1,1000, sTail);
		Map<String,String> oMap = new HashMap<String, String>();
		for(String sSubUrl : oList){
			String sText = ExtractText.GetBlogText(sSubUrl);
			oMap.put(sSubUrl, sText);
		
		}
	}

	private static List<String> SpiderUrl(String sMainUrl, String sRestUrl, String sTail) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		
		List<String> oList = oApacheURLLister.listAll(sMainUrl, sRestUrl, 2, 1000, sTail);
		return oList;
	}
	
	public static List<String> SpiderUrl(String sMainUrl, String sRestUrl, int depth, int wide, String sTail) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		
		List<String> oList = oApacheURLLister.listAll(sMainUrl, sRestUrl, depth, wide, sTail);
		return oList;
	}
	
	public static List<String> DefaultSpiderUrl(String sMainUrl, String sRestUrl) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		ApacheURLListerRecursive oApacheURLLister = new ApacheURLListerRecursive();
		//URL oURL = new URL("http://www.blogia.com");
		
		List<String> oList = oApacheURLLister.listAll(sMainUrl, sRestUrl, 2, 10, "");
		return oList;
	}

}
