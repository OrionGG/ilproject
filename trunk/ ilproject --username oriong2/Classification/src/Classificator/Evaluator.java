package Classificator;
import java.util.*;
import java.util.Map.Entry;


import CategoryGenerator.Categories;



import DBLayer.DAOCategorization;



public class Evaluator {


public static int evaluate(Hashtable<Categories,List<String>> urlsCategorizadas){
	double score=0;
	double totalScore=0;
	
	//entryset
	for(Entry<Categories,List<String>> oEntry:urlsCategorizadas.entrySet()){
		Categories oCategories = oEntry.getKey();

		Hashtable<String, List<CategIndexScore>> scores = new Hashtable<String, List<CategIndexScore>>();
		for(String sUrl: oEntry.getValue()){
			List<CategIndexScore> lCategIndexScore = Classificator.getScoresCat(sUrl, oCategories);
			scores.put(sUrl, lCategIndexScore);
		}
		
		score= FinalScoreCalculator.calculateFinalScore(scores.get(0),scores.get(1),scores.get(2));
		totalScore=score+totalScore;
			
		}
	System.out.print("Total score of evaluating "+totalScore);
	
	return 0;
	
	
}
}
