package CategoryGenerator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import DBLayer.DAOUrlCategory;

public class StoreAllUrls {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		for(Categories oCategory: Categories.allCategories){
			List<String> lUrlsSaved = DAOUrlCategory.getInstance().getUrlsCategory(oCategory);
			List<String> lUrls = getUrlsFromCategory(oCategory.toString(), lUrlsSaved);
			for(String sUrls: lUrls){
				DAOUrlCategory.getInstance().saveUrlCategory(sUrls, oCategory);
			}
		}


	}

	public static List<String> getUrlsFromCategory(String categoryName) throws MalformedURLException, IOException{
		return getUrlsFromCategory(categoryName,  new java.util.ArrayList<String>());
		
	}
	
	public static List<String> getUrlsFromCategory(String categoryName, List<String> urlList) throws MalformedURLException, IOException{
		List<String> lUrls = new java.util.ArrayList<String>();
		Categories oCategory = StringToCategories.getCategory(categoryName);
		String sTextUrls = "";
		for(UrlByCategory oUrlByCategory : oCategory.getLUrlList()){
			lUrls.addAll(Spider.GetSubUrls.SpiderUrl(oUrlByCategory.sMainUrl, oUrlByCategory.sRestUrl, 2,40,1, oUrlByCategory.sSuffixFilter,  urlList));
		}

		return lUrls;
	}

}
