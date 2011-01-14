package entities.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.*;
import entities.APiecesOfNews;
import entities.APiecesOfNews.NewsType;

public class APieceOfNewsDao{
	public enum fields{
		ID,
		URL,
		TEXT,
		TYPE,
		WEIGHT
	}

	public APieceOfNewsDao(){
		try {
			getDBConnector().setConnection("NewsDB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DBConnector getDBConnector(){
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

	}

	public List<Integer>  getIds(String sUrl) throws Exception{
		sUrl = normalizeUrl(sUrl);
		
		List<Integer>  oResultList = new ArrayList<Integer>();
		ResultSet oResultSet;
		oResultSet = getDBConnector().executeQuery("SELECT ID FROM URL WHERE URL = '" + sUrl + "'");
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

	public APiecesOfNews getApieceOfNews(int iId) throws Exception{
		ResultSet oResultSet = getDBConnector().executeQuery("SELECT * FROM URL WHERE ID=?",iId);
		if (oResultSet.next()) {
			String sUrl = oResultSet.getString(fields.URL.toString());
			String sText =  oResultSet.getString(fields.TEXT.toString());
			NewsType oNewsType = NewsType.NONE;
			try {
				oNewsType = NewsType.toNewsType(oResultSet.getString(fields.TYPE.toString()));
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double dWeight = oResultSet.getDouble(fields.WEIGHT.toString());

			APiecesOfNews oAPiecesOfNews = new APiecesOfNews(sUrl, sText, oNewsType, dWeight);

			return oAPiecesOfNews;


		}
		else {
			return null;
		}

	}

	public void insertUrl(String sUrl, String sText, NewsType oNewsType, double iWeight)throws Exception{
		sUrl = normalizeUrl(sUrl);
		getDBConnector().executeUpdate("INSERT INTO URL(URL,TEXT, TYPE, Weight) VALUES(?,?,?,?)",sUrl,sText, oNewsType.ToString(), iWeight);
	}

	public void updateTextWeight(int id,String sText, double iWeight)throws Exception{
		getDBConnector().executeUpdate("UPDATE URL SET TEXT=?,Weight=? WHERE ID=?",sText,iWeight, id);
	}

	public void deleteUrl(int id)throws Exception{
		getDBConnector().executeUpdate("DELETE FROM URL WHERE ID=?",id);
	}

	public void disableUrl(int id)throws Exception{
		getDBConnector().executeUpdate("UPDATE URL SET ENABLE=? WHERE ID=?", false, id);
	}

	public List<APiecesOfNews> getAllNews() throws Exception{
		List<APiecesOfNews> oListAPiecesOfNews = new ArrayList<APiecesOfNews>();
		ResultSet oResultSet = getDBConnector().executeSimpleQuery("SELECT * FROM URL");
		while (oResultSet.next()) {

			String sUrl = oResultSet.getString(fields.URL.toString());
			String sText = oResultSet.getString(fields.TEXT.toString());
			NewsType oNewsType = NewsType.NONE;
			try {
				oNewsType = NewsType.toNewsType(oResultSet.getString(fields.TYPE.toString()));
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double dWeight = oResultSet.getDouble(fields.WEIGHT.toString());

			APiecesOfNews oAPiecesOfNews = new APiecesOfNews(sUrl, sText, oNewsType, dWeight);
			oListAPiecesOfNews.add(oAPiecesOfNews);
		}

		return oListAPiecesOfNews;


	}

}
