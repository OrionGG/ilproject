package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import CategoryGenerator.Categories;
import DBLayer.DAOUrlsClassified.Fields;

public class DAOUrlsRastreated extends DAOWebsClassified {
	public enum State{
		nothing,
		toIndex,
		toClassify,
		Indexed,
		Classified
	}
	


	private static DAOUrlsRastreated oInstance;
	public static DAOUrlsRastreated getInstance(){
		if(oInstance == null){
			oInstance = new DAOUrlsRastreated();
		}
		return oInstance;
	}


	 public int getId(String sUrl) throws SQLException{
	    	ResultSet oResultSet;
				oResultSet = executeQuery("SELECT id FROM urls_rastreated WHERE url = ?",sUrl);
	    	 if (oResultSet.next()) {
	             return oResultSet.getInt(1);
	         }
	         else {
	             return -1;
	         }
	    }
	    
	    public void insertOrUpdateUrlCategory(String sUrl, Categories oCategories){
	    	int iId = -1;
			try {
				iId = getId(sUrl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(iId > 0){
	    		updateCategory(iId, oCategories);
	    	}
	    	else{
	    		insertUrlCategory(sUrl, oCategories);
	    	}
	     }

	    public void insertUrlCategory(String sUrl, Categories oCategories){
	        try {
				executeUpdate("INSERT INTO urls_rastreated(url,category) VALUES(?,?)",sUrl,oCategories.ordinal());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }

	    public void updateCategory(int id,Categories oCategories){
	        try {
				executeUpdate("UPDATE urls_rastreated SET category=? WHERE ID=?",oCategories.ordinal(),id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    public void deleteUrlCategory(int id){
	        try {
				executeUpdate("DELETE FROM urls_rastreated WHERE ID=?",id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	    
	    public List<String> getUrlsCategory(Categories oCategory) {
	    	List<String> lUrls = new java.util.ArrayList<String>();
			try {
				ResultSet oResultSet = executeQuery("SELECT url FROM urls_rastreated WHERE category=?",oCategory.ordinal());
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

		public void updateUrlState(String sUrls, State eState) {
			try {
				executeUpdate("UPDATE INTO urls_rastreated(url,state) VALUES (?,?)",sUrls, eState.ordinal());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}