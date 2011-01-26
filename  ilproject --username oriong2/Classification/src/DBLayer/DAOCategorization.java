package DBLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import CategoryGenerator.Categories;

import dao.DAO_MySQL;


public class DAOCategorization {
	public enum Fields{
		index,
		url,
		score
	}



	protected static Connection conexionCategorized;

	public static void setUpCatego() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		   Class.forName("com.mysql.jdbc.Driver");
		   conexionCategorized = DriverManager.getConnection("jdbc:mysql://localhost:3306/categorizedWebs"+"?user=root");
	}

	public static ResultSet getEvaWeb(Categories category) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		setUpCatego();
		Statement st = conexionCategorized.createStatement();
		String query="SELECT url,index,score FROM eva_web WHERE category='"+category+"' ORDER BY value url,index";
		System.out.println(query);
		ResultSet rs=st.executeQuery(query);

		return rs;
	}

	public static void storeWebCat(String url, String category, Float score) throws SQLException, ClassNotFoundException {

		setUpCatego();
		Statement st = conexionCategorized.createStatement();
		st.executeUpdate("INSERT INTO web_cat(url,category,score) VALUES ('"+url+"','"+category+"','"+score+"')");

	}

	public static void storeEvaWeb(String domainUrl, int i, String stringValue,	float score) throws SQLException, ClassNotFoundException {
		setUpCatego();
		Statement st = conexionCategorized.createStatement();
		st.executeUpdate("INSERT INTO web_cat_eva(url,index,category,score) VALUES ('"+domainUrl+"','"+i+"','"+stringValue+"','"+score+"')");

	}

}
