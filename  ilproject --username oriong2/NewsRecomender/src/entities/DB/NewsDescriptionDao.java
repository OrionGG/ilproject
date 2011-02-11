package entities.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
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
		CAT,
		SCORE
	}
	
	public final static int NUMCAT = 5; 

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
		ResultSet oResultSet = executeQuery("SELECT * FROM LIST WHERE ID=?",iId);
		if (oResultSet.next()) {

			NewsDescription oNewsDescription = new NewsDescription();
			
			Integer iID = oResultSet.getInt(fields.ID.toString());
			oNewsDescription.setId(iID);
			String sUrl = oResultSet.getString(fields.URL.toString());
			oNewsDescription.setUrl(sUrl);
			
			for(int i = 0; i < NUMCAT; i++){
				try {
					Category oCategory = Category.values()[oResultSet.getInt(fields.CAT.toString()+i)];
					float fScore = oResultSet.getFloat(fields.SCORE.toString()+i);

					oNewsDescription.setCategoryScore(oCategory, fScore);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			return oNewsDescription;


		}
		else {
			return null;
		}

	}

	public void deleteUrl(int id)throws Exception{
		executeUpdate("DELETE FROM LIST WHERE ID=?",id);
	}


	public List<NewsDescription> getAllNews(){
		List<NewsDescription> oListAPiecesOfNews = new ArrayList<NewsDescription>();
		ResultSet oResultSet;
		try {
			oResultSet = executeSimpleQuery("SELECT * FROM LIST");
			while (oResultSet.next()) {

				NewsDescription oNewsDescription = new NewsDescription();
				

				Integer iID = oResultSet.getInt(fields.ID.toString());
				oNewsDescription.setId(iID);
				String sUrl = oResultSet.getString(fields.URL.toString());
				oNewsDescription.setUrl(sUrl);
				for(int i = 0; i < NUMCAT; i++){
					try {
						int iPosition = i+1;
						Category oCategory = Category.values()[oResultSet.getInt(fields.CAT.toString()+ iPosition)];
						float fScore = oResultSet.getFloat(fields.SCORE.toString()+ iPosition);

						oNewsDescription.setCategoryScore(oCategory, fScore);
					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				oListAPiecesOfNews.add(oNewsDescription);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return oListAPiecesOfNews;


	}
}
