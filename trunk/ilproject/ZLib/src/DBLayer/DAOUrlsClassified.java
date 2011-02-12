package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import dominio.Category;
import dominio.Url;


public class DAOUrlsClassified extends DAOWebsClassified{
	public enum Fields{
		id,
		url,
		category,
		score
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
		int iEnum = rs.getInt(Fields.category.toString());
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



	public  TreeMap <Float,Category>  selectCategoryScores(String sUrl ) {
		String query="SELECT score,category FROM urls_classified WHERE url='"+sUrl+"'";

		TreeMap <Float,Category> categoryScores=new TreeMap<Float, Category>(Collections.reverseOrder());
		try {
			ResultSet rs = executeQuery(query);

			while(rs.next()){
				//Fill the score Category with the info form DB---already oredeereed by score
				categoryScores.put(rs.getFloat(Fields.score.toString()),  Category.values()[rs.getInt(Fields.category.toString())]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryScores;
	}



	public void deleteAll() {
		try {
			executeUpdate("DELETE FROM urls_classified");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




}



