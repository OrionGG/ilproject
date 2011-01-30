package Classificator;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import CategoryGenerator.Categories;
import DBLayer.DAOScoresIntermediate;
import DBLayer.DAOScoresIntermediate;

public class FinalScoreCalculator {
	private static float weightDbpedia=new Float(0.2);	
	private static float weightWiki= new Float(0.2);	
	private static float weightNews= new Float(0.6);


	public static Float calculateFinalScore(float score1, float score2, float score3){
		float finalScore = score1 * weightWiki + score2 * weightDbpedia + score3 * weightNews;
		return  finalScore;
	}

	public static void calculateFinalScoreTable() throws SQLException, ClassNotFoundException{

		Float finalScore=new Float(0);
		for(Categories category : Categories.allCategories){
			ResultSet rs= DAOScoresIntermediate.selectUrlsFromCategory(category);
			String lastUrl=rs.getString(DAOScoresIntermediate.Fields.url.toString());

			Hashtable<Integer, Float> hIndexScore = new Hashtable<Integer, Float>();

			while(rs.next()){
				String newUrl = rs.getString(DAOScoresIntermediate.Fields.url.toString());
				if(lastUrl.equals(newUrl)){
					int iIndex = rs.getInt(DAOScoresIntermediate.Fields.indextype.toString());
					float score = rs.getFloat(DAOScoresIntermediate.Fields.score.toString());
					hIndexScore.put(iIndex, score);
				}
			}
			finalScore = calculateFinalScore(hIndexScore);	
			DAOScoresIntermediate.saveUrl(lastUrl, category.ordinal(), finalScore);
			lastUrl = rs.getString(DAOScoresIntermediate.Fields.url.toString());
		}

	}

	private static float calculateFinalScore(Hashtable<Integer, Float> hIndexScore) {
		float fTotalScore = calculateFinalScore(hIndexScore.get(1), hIndexScore.get(2), hIndexScore.get(3));
		return fTotalScore;
	}

}
