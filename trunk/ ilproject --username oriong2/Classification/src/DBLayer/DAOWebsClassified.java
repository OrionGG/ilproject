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

	
	public DAOWebsClassified(){
		super();
		SERVERNAME = "WebsClassified";
	}



}
