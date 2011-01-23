package DBLayer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import CategoryGenerator.Categories;

import dao.DAO_MySQL;


public class DAOCategorization extends DAO_MySQL{
	public enum Fields{
		index,
		url,
		score
	}
	

	
	public static ResultSet getEvaWeb(Categories category) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
	String query="SELECT url,index,score FROM eva_web WHERE category='"+category+"' ORDER BY value url,index";
		System.out.println(query);
		ResultSet rs=st.executeQuery(query);

		return rs;
	}
	
	public static void storeWebCat(String url, String category, Float score) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO web_cat(url,category,score) VALUES ('"+url+"','"+category+"','"+score+"')");
		
	}
	
	public static void storeEvaWeb(String domainUrl, int i, String stringValue,	float score) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO web_cat_eva(url,index,category,score) VALUES ('"+domainUrl+"','"+i+"','"+stringValue+"','"+score+"')");
		
	}
	
}
    