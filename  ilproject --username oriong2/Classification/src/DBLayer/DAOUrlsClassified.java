package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import CategoryGenerator.Categories;

public class DAOUrlsClassified extends DAOWebsClassified{
	public enum Fields{
		id,
		URLNAME,
		CATEGORYTYPE
	}
	


	public static void saveUrls(String url, String category)  {

		try {
			setUpCatego();
			Statement st = conexionCategorized.createStatement();
			st.executeUpdate("INSERT INTO urls_classified(url,category) VALUES ('"+url+"','"+category+"')");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conexionCategorized.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	

	

	}



