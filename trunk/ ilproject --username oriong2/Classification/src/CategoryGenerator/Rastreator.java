package CategoryGenerator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import DBLayer.DAOUrlCategory;
import DBLayer.DAOUrlsRastreated;
import DBLayer.DAOUrlsRastreated.State;

public class Rastreator {
	public static double percentage = 0.8;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		saveUrlsByCategory();
		
		List<String> sUrlsSubListToIndex = new ArrayList<String>();
		List<String> sUrlsSubListToClassify = new ArrayList<String>();
		
		getListToIndexToClassify(sUrlsSubListToIndex, sUrlsSubListToClassify);
		
		
		saveUrlState(sUrlsSubListToIndex, State.ToIndex);
		saveUrlState(sUrlsSubListToClassify, State.ToClassify);


	}

	private static void getListToIndexToClassify(
			List<String> sUrlsSubListToIndex,
			List<String> sUrlsSubListToClassify) {
		
		for(Categories oCategory: Categories.allCategories){
			List<String> lUrls = DAOUrlsRastreated.getInstance().getUrlsCategory(oCategory);

			int iMaxToText =(int) (lUrls.size() * percentage);
			sUrlsSubListToIndex.addAll(lUrls.subList(0, iMaxToText));
		
			sUrlsSubListToClassify.addAll( lUrls.subList(iMaxToText, lUrls.size()));
		}
	}

	private static void saveUrlState(List<String> sUrlsSubListToIndex, State eState) {
		for(String sUrls: sUrlsSubListToIndex){
			DAOUrlsRastreated.getInstance().updateUrlState(sUrls, eState);
		}
	}

	private static void saveUrlsByCategory() throws MalformedURLException,
			IOException {
		//list we are going to return
		for(Categories oCategory: Categories.allCategories){
			//list of the urls of this category saved yet
			List<String> lUrlsSaved = DAOUrlCategory.getInstance().getUrlsCategory(oCategory);
			//list with all news urls for a category
			List<String> lUrls = getUrlsFromCategory(oCategory.toString(), lUrlsSaved);
			for(String sUrls: lUrls){
				DAOUrlsRastreated.getInstance().insertOrUpdateUrlCategory(sUrls, oCategory);
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
