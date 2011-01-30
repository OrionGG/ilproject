package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Category;

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
		Category Category=null;
		Category=rs.getInt(0);
		Category.ordinal();
		return Category;
	}
	public void saveUrls(String url, String category)  {

		try {
			executeUpdate("INSERT INTO urls_classified(url,category) VALUES ('"+url+"','"+category+"')");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public List<String> getUrlsCategory(dominio.Category oCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	}



