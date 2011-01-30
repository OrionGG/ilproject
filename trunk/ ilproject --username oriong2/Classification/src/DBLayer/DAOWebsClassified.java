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


public class DAOWebsClassified extends DAO{
	public enum Fields{
		urls_rastreated,
		urls_classified,
		scores_intermediate
	}

	protected static Connection conexionCategorized;

	public static void setUpCatego() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		conexionCategorized = DriverManager.getConnection("jdbc:mysql://localhost:3306/WebsClassified","root","admin");
	}

	private static DAOUrlsClassified oInstance;
	
	public DAOWebsClassified(){
		super();
		SERVERNAME = "WebsClassified";
	}
	
	public static DAOUrlsClassified getInstance(){
		if(oInstance == null){
			oInstance = new DAOUrlsClassified();
		}
		return oInstance;
	}



}
