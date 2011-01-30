package Classificator;
import java.util.*;
import java.util.Map.Entry;


import dominio.Category;
import DBLayer.*;
import DBLayer.DAOUrlsRastreated.State;




import DBLayer.*; 
import Classificator.FinalScoreCalculator;


public class Evaluator {

	public static void changeValues(){
		//read DB
	
	}
	public static void evaluate(){
		
		//read DB
		TreeMap<String,Integer> listUrlsEvaluation=DAOUrlsRastreated.getInstance().selectUrls(State.Classified);
		
		for(String url : listUrlsEvaluation){
			String urlOld=url;
			Categories cat=DAOUrlsClassified.getInstance().selectUrl(url);
			if(url.equals(urlOld)){
				if(cat==catNew){
					
				}
			}
		}
	
	}
	@Deprecated
	public static void evaluate(Hashtable<Categories,List<String>> urlsCategorizadas){
		//read DB
		
		//entryset
		for(Entry<Categories,List<String>> oEntry:urlsCategorizadas.entrySet()){
			Categories oCategories = oEntry.getKey();

			List<IndexCategScore> lCategIndexScore = new java.util.ArrayList<IndexCategScore>();

			for(String sUrl: oEntry.getValue()){
				lCategIndexScore = Classificator.getScoresCat(sUrl);

				TreeMap<Double, Categories> oTreeMap = FinalScoreCalculator.indexShortedCross(lCategIndexScore);
				System.out.println("");
				System.out.println("URL: "+ sUrl);
				System.out.println("");

				FinalScoreCalculator.showFinalResults(oTreeMap);
			}
		}
	}

}
