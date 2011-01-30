package Classificator;
import java.util.*;
import java.util.Map.Entry;

import org.apache.james.mime4j.codec.EncoderUtil.Encoding;


import dominio.Category;
import DBLayer.*;
import DBLayer.DAOUrlsClassified.Fields;
import DBLayer.DAOUrlsRastreated.State;




import DBLayer.*; 
import Classificator.FinalScoreCalculator;


public class Evaluator {

	public static void changeValues(){
		//read DB
	
	}
	public static void evaluate(){
		
		//read DB, take the rastreated webd webs, rady for categ
		TreeMap<String,Category> treeUrlsEvaluation=DAOUrlsRastreated.getInstance().selectUrlsCategory(State.Classified);
		Set<Entry<String, Category>>set=treeUrlsEvaluation.entrySet();
		////Read Db, taking the catgeorizated and ready for evaluated webs
		for(Entry<String, Category> entry : set){
			String url=entry.getKey();
			Category cat=entry.getValue();
			
			ArrayList<>=DAOUrlsClassified.getInstance().selectClassifiedUrl(url);
			for(){
				
			}
			if(url.equals(urlOld)){
				if(cat==catNew){
					
				}
			}
		}
	
	}
	@Deprecated
	public static void evaluate(Hashtable<Category,List<String>> urlsCategorizadas){
		//read DB
		
		//entryset
		for(Entry<Category,List<String>> oEntry:urlsCategorizadas.entrySet()){
			Category oCategory = oEntry.getKey();

			List<IndexCategScore> lCategIndexScore = new java.util.ArrayList<IndexCategScore>();

			for(String sUrl: oEntry.getValue()){
				lCategIndexScore = Classificator.getScoresCat(sUrl);

				TreeMap<Double, Category> oTreeMap = FinalScoreCalculator.indexShortedCross(lCategIndexScore);
				System.out.println("");
				System.out.println("URL: "+ sUrl);
				System.out.println("");

				FinalScoreCalculator.showFinalResults(oTreeMap);
			}
		}
	}

}
