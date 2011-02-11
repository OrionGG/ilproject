package entities.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DBLayer.DAOUrlsClassified;
import DBLayer.DAOWebsClassified;

import connector.DBConnector;
import connector.MysqlDBConnector;
import dominio.Category;
import entities.NewsDescription;

public class NewsDescriptionDao extends DAOWebsClassified{
	public enum fields{
		ID,
		URL,
		CAT1,
		SCORE1,
		CAT2,
		SCORE2,
		CAT3,
		SCORE3
	}
	
	private static NewsDescriptionDao oInstance;
	public static NewsDescriptionDao getInstance(){
		if(oInstance == null){
			oInstance = new NewsDescriptionDao();
		}
		return oInstance;
	}

/*	public NewsDescriptionDao(){
		try {
			getDBConnector().setConnection("NewsDB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

/*	public DBConnector getDBConnector(){
		//DBConnector oDBConnector = SQLServerDBConnector.getInstance();
		DBConnector oDBConnector = MysqlDBConnector.getInstance();
		try {
			oDBConnector.setConnection("NewsDB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//could be other instance
		return oDBConnector;

	}*/

	public List<Integer>  getIds(String sUrl) throws Exception{
		sUrl = normalizeUrl(sUrl);
		
		List<Integer>  oResultList = new ArrayList<Integer>();
		ResultSet oResultSet;
		oResultSet = executeQuery("SELECT ID FROM URL WHERE URL = '" + sUrl + "'");
		while (oResultSet.next()) {
			int i = oResultSet.getInt(fields.ID.toString());
			oResultList.add(i);
		}

		return oResultList;
	}

	private String normalizeUrl(String sUrl) {
		StringBuilder sUrlNormalized = new StringBuilder(sUrl);
		while (sUrlNormalized.toString().endsWith("/")){
			sUrlNormalized.deleteCharAt(sUrlNormalized.length()-1);
		}
		sUrl = sUrlNormalized.toString();
		return sUrl;
	}

	public NewsDescription getNewsDescription(int iId) throws Exception{
		ResultSet oResultSet = executeQuery("SELECT * FROM CATS WHERE ID=?",iId);
		if (oResultSet.next()) {
			String sUrl = oResultSet.getString(fields.URL.toString());
			//CATEGORY 1
			Category oCategory1;
			double dScore1;
			try {
				oCategory1 = Category.values()[oResultSet.getInt(fields.CAT1.toString())];
				dScore1 = oResultSet.getDouble(fields.SCORE1.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//CATEGORY 2
			Category oCategory2;
			double dScore2;
			try {
				oCategory2 = Category.values()[oResultSet.getInt(fields.CAT2.toString())];
				dScore2 = oResultSet.getDouble(fields.SCORE2.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//CATEGORY 3
			Category oCategory3;
			double dScore3;
			try {
				oCategory3 = Category.values()[oResultSet.getInt(fields.CAT3.toString())];
				dScore3 = oResultSet.getDouble(fields.SCORE3.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			NewsDescription oNewsDescription = new oNewsDescription(sUrl, sText, oNewsType, dWeight);

			return oNewsDescription;


		}
		else {
			return null;
		}

	}

	public void insertUrl(String sUrl, String sText, NewsType oNewsType, double iWeight)throws Exception{
		sUrl = normalizeUrl(sUrl);
		executeUpdate("INSERT INTO URL(URL,TEXT, TYPE, Weight) VALUES(?,?,?,?)",sUrl,sText, oNewsType.ToString(), iWeight);
	}

	public void updateTextWeight(int id,String sText, double iWeight)throws Exception{
		executeUpdate("UPDATE URL SET TEXT=?,Weight=? WHERE ID=?",sText,iWeight, id);
	}

	public void deleteUrl(int id)throws Exception{
		executeUpdate("DELETE FROM URL WHERE ID=?",id);
	}

	public void disableUrl(int id)throws Exception{
		executeUpdate("UPDATE URL SET ENABLE=? WHERE ID=?", false, id);
	}

	public List<NewsDescription> getAllNews() throws Exception{
		List<NewsDescription> oListAPiecesOfNews = new ArrayList<NewsDescription>();
		ResultSet oResultSet = executeSimpleQuery("SELECT * FROM URL");
		while (oResultSet.next()) {

			String sUrl = oResultSet.getString(fields.URL.toString());
			NewsType oNewsType = NewsType.NONE;
			try {
				oNewsType = NewsType.toNewsType(oResultSet.getString(fields.TYPE.toString()));
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double dWeight = oResultSet.getDouble(fields.WEIGHT.toString());

			NewsDescription oNewsDescription = new oNewsDescription();
			
			(sUrl, sText, oNewsType, dWeight);
			oListAPiecesOfNews.add(oNewsDescription);
		}

		return oListAPiecesOfNews;


	}
}
