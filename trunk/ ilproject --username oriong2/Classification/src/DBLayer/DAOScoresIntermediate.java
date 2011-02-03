package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import CategoryGenerator.IndexesWriter;
import Classificator.IndexCategScore;

import dominio.Category;

public class DAOScoresIntermediate extends DAOWebsClassified{
	
	public enum Fields{
		indextype,
		category,
		score,
		url
	}	
	
	private static DAOScoresIntermediate oInstance;
	public static DAOScoresIntermediate getInstance(){
		if(oInstance == null){
			oInstance = new DAOScoresIntermediate();
		}
		return oInstance;
	}

	
	public ResultSet selectUrlsFromCategory(Category category) throws SQLException, ClassNotFoundException {
		String query="SELECT url,indextype,score FROM scores_intermediate WHERE category='"+category+"' ORDER BY value url,index";
		System.out.println(query);
		ResultSet rs = executeQuery(query);

		return rs;
	}
	
	
	public List<String> selectAllUrls() throws SQLException, ClassNotFoundException {
		List<String> lResult = new ArrayList<String>();
		
		String query="SELECT distinct url FROM scores_intermediate";
		System.out.println(query);
		ResultSet oResultSet = executeQuery(query);
		while(oResultSet.next()){
			String sUrl = oResultSet.getString(DAOScoresIntermediate.Fields.url.toString());
			lResult.add(sUrl);
		}
		return lResult;
	}
		
	
	public void saveUrl(String domainUrl, int i, Category oCategory, float score)  {

		try {
			String sql = "INSERT INTO scores_intermediate(url,indextype, category,score) VALUES (?,?,?,?)";
			executeUpdate(sql,domainUrl, i, oCategory.ordinal(), score);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public Hashtable<Integer, Float> getIntermediateScoresByUrlAndCategory(String sUrl,
			Category category)  {
		Hashtable<Integer, Float> hResult = new Hashtable<Integer, Float>();
		try {
			String query="SELECT indextype,score FROM scores_intermediate WHERE url=? and category=?";
		
			ResultSet oResultSet = executeQuery(query, sUrl, category.ordinal());
			while(oResultSet.next()){
				int iIndex = oResultSet.getInt(Fields.indextype.toString());
				float fScore = oResultSet.getFloat(Fields.score.toString());
				hResult.put(iIndex, fScore);
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hResult;
	}
}
