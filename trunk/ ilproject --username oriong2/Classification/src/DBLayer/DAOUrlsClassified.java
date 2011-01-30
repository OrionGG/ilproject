package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.TreeMap;

import dominio.Category;
import dominio.Url;


public class DAOUrlsClassified extends DAOWebsClassified{
	public enum Fields{
		id,
		URLNAME,
		CATEGORYTYPE
	}
	
	private static DAOUrlsClassified oInstance;
	public static DAOUrlsClassified getInstance(){
		if(oInstance == null){
			oInstance = new DAOUrlsClassified();
		}
		return oInstance;
	}
	

	
	public Category selectUrl(String url) throws SQLException, ClassNotFoundException {
		String query="SELECT category FROM urls_classified WHERE url='"+url+"'";
		System.out.println(query);
		ResultSet rs = executeQuery(query);
		int iEnum = rs.getInt(Fields.CATEGORYTYPE.toString());
		Category oCategory = Category.values()[iEnum];
		return oCategory;
	}
	
	public void saveUrl(String url, Category oCategory, float score)  {

		try {
			executeUpdate("INSERT INTO urls_classified(url,category,score) VALUES (?, ?, ?)", url, oCategory.ordinal(), score);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public  TreeMap <Float,Category>  getScoresCategory(Url url ) {
		String query="SELECT score,category FROM urls_classified WHERE url='"+url+"' ORDER BY score";
		
		ResultSet rs = executeQuery(query);
		for(rs)
		int iEnum = rs.getInt(Fields.CATEGORYTYPE.toString());
		Category oCategory = Category.values()[iEnum];
		return oCategory;
	}
	

	

	}



