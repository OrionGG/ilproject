package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dominio.Category;;

public class DAOScoresIntermediate extends DAOWebsClassified{
	
	public enum Fields{
		indextype,
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
	public void saveUrl(String domainUrl, int i, Category oCategory, float score)  {

		try {
			String sql = "INSERT INTO scores_intermediate(url,indextype, category,score) VALUES (?,?,?,?)";
			executeUpdate(sql,domainUrl, i, oCategory.ordinal(), score);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
