package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import CategoryGenerator.Categories;
import DBLayer.DAOUrlsClassified.Fields;

public class DAOUrlsRastreated extends DAOWebsClassified {


	 public int getId(String sUrl)throws SQLException{
	    	ResultSet oResultSet = executeQuery("SELECT id FROM urls_rastreated WHERE url = ?",sUrl);
	    	 if (oResultSet.next()) {
	             return oResultSet.getInt(1);
	         }
	         else {
	             return -1;
	         }
	    }
	    
	    public void insertOrUpdateUrlCategory(String sUrl, Categories oCategories)throws SQLException{
	    	int iId = getId(sUrl);
	    	if(iId > 0){
	    		updateCategory(iId, oCategories);
	    	}
	    	else{
	    		insertUrlCategory(sUrl, oCategories);
	    	}
	     }

	    public void insertUrlCategory(String sUrl, Categories oCategories)throws SQLException{
	        executeUpdate("INSERT INTO urls_rastreated(url,state) VALUES(?,?)",sUrl,oCategories.ordinal());
	     }

	    public void updateCategory(int id,Categories oCategories)throws SQLException{
	        executeUpdate("UPDATE urls_rastreated SET state=? WHERE ID=?",oCategories.ordinal(),id);
	    }

	    public void deleteUrlCategory(int id)throws SQLException{
	        executeUpdate("DELETE FROM urls_rastreated WHERE ID=?",id);
	     }
	    
	    public List<String> getUrlsCategory(Categories oCategory) {
	    	List<String> lUrls = new java.util.ArrayList<String>();
			try {
				ResultSet oResultSet = executeQuery("SELECT state FROM urls_rastreated WHERE state=?",oCategory.ordinal());
				while(oResultSet.next()){
					String sUrl = oResultSet.getString("url");
					lUrls.add(sUrl);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lUrls;
		}

		public void saveUrlCategory(String sUrls, Categories oCategory) {
			try {
				executeUpdate("INSERT INTO urls_rastreated(url,category) VALUES (?,?)",sUrls, oCategory.ordinal());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
