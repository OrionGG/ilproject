package Classificator;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import dominio.Category;
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

		Float finalScore = new Float(0);
		for(Category category : Category.allCategories){
			ResultSet rs= DAOScoresIntermediate.getInstance().selectUrlsFromCategory(category);
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
			DAOScoresIntermediate.getInstance().saveUrl(lastUrl, category.ordinal(), finalScore);
			lastUrl = rs.getString(DAOScoresIntermediate.Fields.url.toString());
		}

	}

	private static float calculateFinalScore(Hashtable<Integer, Float> hIndexScore) {
		float fTotalScore = calculateFinalScore(hIndexScore.get(1), hIndexScore.get(2), hIndexScore.get(3));
		return fTotalScore;
	}

	public static void showFinalResults(TreeMap<Double, Category> oTreeMap) {
		int i= 0;
		for(Entry<Double,Category> oTreeMapEntry: oTreeMap.entrySet()){
			double dScore =  oTreeMapEntry.getKey();
			Category oCategoryTreeMapEntry = oTreeMapEntry.getValue();


			System.out.println(i + ": " + oCategoryTreeMapEntry.toString() + " = " + dScore);
			i++;
		}
	}

	public static TreeMap<Double, Category> indexShortedCross(List<IndexCategScore> lCategIndexScore) {
		TreeMap<Double,Category> oTreeMap = new TreeMap<Double,Category>(Collections.reverseOrder()); 
		for(Category cat:Category.allCategories){
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
