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
import CategoryGenerator.IndexesWriter;
import DBLayer.DAOScoresIntermediate;
import DBLayer.DAOScoresIntermediate;
import DBLayer.DAOUrlsClassified;

public class FinalScoreCalculator {
	private static float weightDbpedia=new Float(0.2);	
	private static float weightWiki= new Float(0.2);	
	private static float weightNews= new Float(0.6);

	public static void main(String[] args) throws Exception {
		fillUrlsClassifiedTable();
	}



	public static void fillUrlsClassifiedTable() throws SQLException, ClassNotFoundException{
		DAOUrlsClassified.getInstance().deleteAll();
		List<String> lAllUrls = DAOScoresIntermediate.getInstance().selectAllUrls();
		for(String sUrl : lAllUrls){
			System.out.println("Final score for: " + sUrl);
			for(Category category : Category.values()){
				Hashtable<Integer, Float> hIndexScore = DAOScoresIntermediate.getInstance().getIntermediateScoresByUrlAndCategory(sUrl, category);
				calculeAndSaveFinalScore(category, sUrl, hIndexScore);
			}
			System.out.println("");
		}	

	}

	private static void calculeAndSaveFinalScore(Category category, String lastUrl, 
			Hashtable<Integer, Float> hIndexScore) {
		float finalScore = calculateFinalScore(hIndexScore);
		if(finalScore != 0){
			DAOUrlsClassified.getInstance().saveUrl(lastUrl, category, finalScore);
		}
	}

	private static Hashtable<Integer, Float> getIntermediateScoresByUrl(ResultSet oResultSet, String sUrl) throws SQLException{
		Hashtable<Integer, Float> hIndexScore = new Hashtable<Integer, Float>();

		int i = 0;
		//numero de indices que tenemos, normalmente tendremos 3
		int MaxIndexNum = IndexesWriter.IndexType.values().length;
		while(oResultSet.next() && i < MaxIndexNum){
			String newUrl = oResultSet.getString(DAOScoresIntermediate.Fields.url.toString());
			if(sUrl.equals(newUrl)){
				int iIndex = oResultSet.getInt(DAOScoresIntermediate.Fields.indextype.toString());
				float score = oResultSet.getFloat(DAOScoresIntermediate.Fields.score.toString());
				hIndexScore.put(iIndex, score);
				i++;
			}
		}

		return hIndexScore;
	}

	public static float calculateFinalScore(float score1, float score2, float score3){

		float finalScore = score1 * weightWiki + score2 * weightDbpedia + score3 * weightNews;
		return  finalScore;
	}
	private static float calculateFinalScore(Hashtable<Integer, Float> hIndexScore) {
		Float iIndexScore1 = hIndexScore.get(1);
		if(iIndexScore1 == null) iIndexScore1 = 0.0f;
		Float iIndexScore2 = hIndexScore.get(2);
		if(iIndexScore2 == null) iIndexScore2 = 0.0f;
		Float iIndexScore3 = hIndexScore.get(3);
		if(iIndexScore3 == null) iIndexScore3 = 0.0f;
		float fTotalScore = calculateFinalScore(iIndexScore1,iIndexScore2, iIndexScore3);
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
		for(Category cat:Category.values()){

			Float iIndexScore1 = lCategIndexScore.get(0).hCategScore.get(cat.ordinal());
			if(iIndexScore1 == null) iIndexScore1 = 0.0f;
			Float iIndexScore2 = lCategIndexScore.get(1).hCategScore.get(cat.ordinal());
			if(iIndexScore2 == null) iIndexScore2 = 0.0f;
			Float iIndexScore3 = lCategIndexScore.get(2).hCategScore.get(cat.ordinal());
			if(iIndexScore3 == null) iIndexScore3 = 0.0f;

			double score = FinalScoreCalculator.calculateFinalScore(iIndexScore1, iIndexScore2, iIndexScore3);

			oTreeMap.put(score, cat);
		}
		return oTreeMap;
	}


	public static float getWeightDbpedia() {
		return weightDbpedia;
	}

	public static void setWeightDbpedia(float weightDbpedia) {
		FinalScoreCalculator.weightDbpedia = weightDbpedia;
	}

	public static float getWeightWiki() {
		return weightWiki;
	}

	public static void setWeightWiki(float weightWiki) {
		FinalScoreCalculator.weightWiki = weightWiki;
	}

	public static float getWeightNews() {
		return weightNews;
	}

	public static void setWeightNews(float weightNews) {
		FinalScoreCalculator.weightNews = weightNews;
	}

}