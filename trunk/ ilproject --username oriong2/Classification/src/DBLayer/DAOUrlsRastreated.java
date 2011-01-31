package DBLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import dominio.Category;
import DBLayer.DAOUrlsClassified.Fields;
import DBLayer.DAOUrlsRastreated.State;

public class DAOUrlsRastreated extends DAOWebsClassified {
	public enum State{
		Nothing,
		ToIndex,
		ToClassify,
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
	    
	    public void insertOrUpdateUrlCategory(String sUrl, Category oCategories, State eState){
	    	int iId = -1;
			try {
				iId = getId(sUrl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	if(iId > 0){
	    		update(iId, oCategories, eState);
	    	}
	    	else{
	    		insert(sUrl, oCategories, eState);
	    	}
	     }

	    public void insert(String sUrl, Category oCategories, State eState){
	        try {
				executeUpdate("INSERT INTO urls_rastreated(url,category, state ) VALUES(?,?, ?)",sUrl,oCategories.ordinal(), eState.ordinal());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }

	    public void update(int id,Category oCategories, State eState){
	        try {
				executeUpdate("UPDATE urls_rastreated SET category=?, state=? WHERE ID=?",oCategories.ordinal(), eState.ordinal(),id);
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
	    
	    public List<String> selectUrlsCategory(Category oCategory) {
	    	List<String> lUrls = new ArrayList<String>();
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
		public List<String> selectUrls(State eState) {
			List<String> lUrls = new java.util.ArrayList<String>();
			try {
				ResultSet oResultSet = executeQuery("SELECT url FROM urls_rastreated WHERE state=?",eState.ordinal());
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
		

		public List<String> selectUrls(Category oCategory, State eState) {
			List<String> lUrls = new java.util.ArrayList<String>();
			try {
				ResultSet oResultSet = executeQuery("SELECT url FROM urls_rastreated WHERE category=? AND state=?",
						oCategory.ordinal(), eState.ordinal());
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
		
		public TreeMap<String,Category> selectUrlsCategory( State eState) {
			TreeMap<String,Category> tUrls = new TreeMap<String,Category>();
			try {
				ResultSet oResultSet = executeQuery("SELECT url,category FROM urls_rastreated WHERE state=?",eState.ordinal());
				while(oResultSet.next()){
					String sUrl = oResultSet.getString("url");
					int category = oResultSet.getInt("category");
					Category oCategory = Category.values()[category];
					tUrls.put(sUrl,oCategory);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tUrls;
		}

}