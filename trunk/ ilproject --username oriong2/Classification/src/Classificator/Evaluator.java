package Classificator;
import java.util.*;


import CategoryGenerator.Categories;



import DBLayer.DAOCategorization;



public class Evaluator {


public static int evaluate(Hashtable<Categories,List<String>> urlsCategorizadas){
	double score=0;
	double totalScore=0;
	
	//entryset
	Categories categories=urlsCategorizadas.keys().nextElement();
	while(!(categories.equals(null))){
		for(List<String> urls : urlsCategorizadas.values()) {
			int i =0;
			while(urls.size()>i){
				String url=urls.get(i);
				
				String urlArray[] = null;
				urlArray[1]=url;
				ArrayList<Float> scores=Classificator.getScoresCat(urlArray,categories);
				score=FinalScoreCalculator.calculateFinalScore(scores.get(0),scores.get(1),scores.get(2));
				totalScore=score+totalScore;
			}
			
		}
		categories=urlsCategorizadas.keys().nextElement();
		
	}
	System.out.print("Total score of evaluating "+totalScore);
	
	return 0;
	
	
}
}
