package DBLayer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import dao.DAO_MySQL;


public class DAOCategorization extends DAO_MySQL{
	
	

	
	public static ResultSet getEvaWeb(String login) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
	String query="SELECT * FROM eva_web WHERE name='"+login+"' ORDER BY value DESC";
		System.out.println(query);
		ResultSet rs=st.executeQuery(query);

		return rs;
	}
	
	public static void storeWebCat(String login, String email, String pass) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO web_cat(name,email,password) VALUES ('"+login+"','"+email+"','"+pass+"')");
		
	}
	
	public static void storeEvaWeb(String domainUrl, int i, String stringValue,	float score) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO eva_web(url,index,category,score) VALUES ('"+domainUrl+"','"+i+"','"+stringValue+"','"+score+"')");
		
	}
	
}
    