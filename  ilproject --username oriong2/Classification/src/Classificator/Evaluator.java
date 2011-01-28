package Classificator;
import java.util.*;
import java.util.Map.Entry;


import CategoryGenerator.Categories;



import DBLayer.DAOCategorization;



public class Evaluator {


	public static void evaluate(Hashtable<Categories,List<String>> urlsCategorizadas){

		//entryset
		for(Entry<Categories,List<String>> oEntry:urlsCategorizadas.entrySet()){
			Categories oCategories = oEntry.getKey();

			List<IndexCategScore> lCategIndexScore = new java.util.ArrayList<IndexCategScore>();

			for(String sUrl: oEntry.getValue()){
				lCategIndexScore = Classificator.getScoresCat(sUrl);

				TreeMap<Double, Categories> oTreeMap = IndexShortedCross(lCategIndexScore);
				System.out.println("");
				System.out.println("URL: "+ sUrl);
				System.out.println("");

				ShowFinalResults(oTreeMap);
			}
		}
	}

	private static void ShowFinalResults(TreeMap<Double, Categories> oTreeMap) {
		int i= 0;
		for(Entry<Double,Categories> oTreeMapEntry: oTreeMap.entrySet()){
			double dScore =  oTreeMapEntry.getKey();
			Categories oCategoriesTreeMapEntry = oTreeMapEntry.getValue();


			System.out.println(i + ": " + oCategoriesTreeMapEntry.toString() + " = " + dScore);
			i++;
		}
	}

	private static TreeMap<Double, Categories> IndexShortedCross(List<IndexCategScore> lCategIndexScore) {
		TreeMap<Double,Categories> oTreeMap = new TreeMap<Double,Categories>(Collections.reverseOrder()); 
		for(Categories cat:Categories.allCategories){
			/*for(IndexCategScore oIndexCategScore: lCategIndexScore){
				float iIndexScore = oIndexCategScore.hCategScore.get(cat);
			}*/

			Float iIndexScore1 = lCategIndexScore.get(0).hCategScore.get(cat.toString());
			if(iIndexScore1 == null) iIndexScore1 = 0.0f;
			Float iIndexScore2 = lCategIndexScore.get(1).hCategScore.get(cat.toString());
			if(iIndexScore2 == null) iIndexScore2 = 0.0f;
			Float iIndexScore3 = lCategIndexScore.get(2).hCategScore.get(cat.toString());
			if(iIndexScore3 == null) iIndexScore3 = 0.0f;

			double score = FinalScoreCalculator.calculateFinalScore(iIndexScore1, iIndexScore2, iIndexScore3);

			oTreeMap.put(score, cat);
		}
		return oTreeMap;
	}
}
