package Classificator;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import CategoryGenerator.Categories;
import DBLayer.DAOCategorization;

public class FinalScoreCalculator {
	private static float weightWiki= new Float(0.2);	
	private static float weightDbpedia=new Float(0.2);	
	private static float weightNews= new Float(0.6);
	
	
	public static Float calculateFinalScore(float score1, float score2, float score3){
		float finalScore = score1*weightWiki+score2*weightDbpedia+score3*weightNews;
		return  finalScore;
	}
	
	public static void calculateFinalScoreTable() throws SQLException{
		
		Float finalScore=new Float(0);
		for(Categories category : Categories.allCategories){
			ResultSet rs= DAOCategorization.getEvaWeb(category);
			int i=0;
			String lastUrl=rs.getString("url");
			while(rs.next()){
				
			Float score1 = new Float(0);
			Float score2 = new Float(0);
				if(rs.getInt("index")==1 && lastUrl.equals(rs.getString("url"))){
					 score1=rs.getFloat("score");
				}else{
					if(rs.getInt("index")==2 && lastUrl.equals(rs.getString("url"))){
						 score2=rs.getFloat("score");
					}else
					if(lastUrl.equals(rs.getString("url")))	{
							Float score3=rs.getFloat("score");
							finalScore=calculateFinalScore(score1, score2, score3);	
							DAOCategorization.storeWebCat(lastUrl, category.toString(), finalScore);
					}
					
				}
				 lastUrl=rs.getString("url");
			
			}
			
		}
		
	}

	public static TreeMap<Float, Categories> getFinalCategories(ArrayList<TopDocs> listTopDocs) {
		// TODO Auto-generated method stub
				
		for(TopDocs topDocs : listTopDocs ){
				
			for(ScoreDoc oScoreDoc : topDocs.scoreDocs)	{
				
				//oScoreDoc.doc;
				//oScoreDoc.score;
				
					
				Document hitDoc = oIndexSearcher.doc(oScoreDoc.doc); 
	
			
				System.out.println("     "+hitDoc.getField("CategoryName").stringValue() + " "+ oScoreDoc.score+"->ALMACENADO");
			}
				
		
		
		}
		
		
		
		return null;
	}

}
