package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import dominio.Category;



public class DAOUrlCategory extends DAO{
	public enum Fields{
		idURLS_CATEGORYTYPE,
		URLNAME,
		CATEGORYTYPE,
		STATE
	}
	
	private static DAOUrlCategory oInstance;
	
	public DAOUrlCategory(){
		super();
		SERVERNAME = "categorizedWebs";
	}
	
	public static DAOUrlCategory getInstance(){
		if(oInstance == null){
			oInstance = new DAOUrlCategory();
		}
		return oInstance;
	}
	
	 public int getId(String sUrl)throws SQLException{
	    	ResultSet oResultSet = executeQuery("SELECT idURLS_CATEGORYTYPE FROM urls_categorytype WHERE URLNAME = ?",sUrl);
	    	 if (oResultSet.next()) {
	             return oResultSet.getInt(1);
	         }
	         else {
	             return -1;
	         }
	    }
	    
	    public void insertOrUpdateUrlCategory(String sUrl, Category oCategories)throws SQLException{
	    	int iId = getId(sUrl);
	    	if(iId > 0){
	    		updateCategory(iId, oCategories);
	    	}
	    	else{
	    		insertUrlCategory(sUrl, oCategories);
	    	}
	     }

	    public void insertUrlCategory(String sUrl, Category oCategories)throws SQLException{
	        executeUpdate("INSERT INTO urls_categorytype(URLNAME,CATEGORYTYPE, STATE) VALUES(?,?)",sUrl,oCategories.ordinal());
	     }

	    public void updateCategory(int id,Category oCategories)throws SQLException{
	        executeUpdate("UPDATE urls_categorytype SET CATEGORYTYPE=? WHERE ID=?",oCategories.ordinal(),id);
	    }

	    public void deleteUrlCategory(int id)throws SQLException{
	        executeUpdate("DELETE FROM urls_categorytype WHERE ID=?",id);
	     }
	    
	    public List<String> getUrlsCategory(Category oCategory) {
	    	List<String> lUrls = new java.util.ArrayList<String>();
			try {
				ResultSet oResultSet = executeQuery("SELECT URLNAME FROM urls_categorytype WHERE CATEGORYTYPE=?",oCategory.ordinal());
				while(oResultSet.next()){
					String sUrl = oResultSet.getString(Fields.URLNAME.toString());
					lUrls.add(sUrl);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lUrls;
		}

		public void saveUrlCategory(String sUrls, Category oCategory) {
			try {
				executeUpdate("INSERT INTO urls_categorytype(URLNAME,CATEGORYTYPE) VALUES (?,?)",sUrls, oCategory.ordinal());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
