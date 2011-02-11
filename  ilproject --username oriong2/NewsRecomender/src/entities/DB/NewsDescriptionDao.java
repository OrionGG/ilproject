package entities.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connector.DBConnector;
import connector.MysqlDBConnector;
import dominio.Category;

public class NewsDescriptionDao {
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

	public NewsDescriptionDao(){
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

	public NewsDescriptionDao getApieceOfNews(int iId) throws Exception{
		ResultSet oResultSet = getDBConnector().executeQuery("SELECT * FROM URL WHERE ID=?",iId);
		if (oResultSet.next()) {
			String sUrl = oResultSet.getString(fields.URL.toString());
			//CATEGORY 1
			Category oCategory1;
			try {
				oCategory1 = Category.values()[oResultSet.getInt(fields.CAT1.toString())];

				double sScore1 = oResultSet.getDouble(fields.SCORE1.toString());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			NewsDecription oNewsDecriptionDao = new NewsDescriptionDao(sUrl, sText, oNewsType, dWeight);

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

	public List<NewsDescriptionDao> getAllNews() throws Exception{
		List<NewsDescriptionDao> oListAPiecesOfNews = new ArrayList<NewsDescriptionDao>();
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

			NewsDescriptionDao oAPiecesOfNews = new NewsDescriptionDao(sUrl, sText, oNewsType, dWeight);
			oListAPiecesOfNews.add(oAPiecesOfNews);
		}

		return oListAPiecesOfNews;


	}
}
