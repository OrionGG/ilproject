package DBLayer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

  @Deprecated
public class DAOUrl extends DAO{
    
  
    public int getId(String sUrl)throws SQLException{
    	ResultSet oResultSet = executeQuery("SELECT ID FROM URL WHERE URL = ?",sUrl);
    	 if (oResultSet.next()) {
             return oResultSet.getInt(1);
         }
         else {
             return -1;
         }
    }
    
    public void insertOrUpdateUrl(String sUrl, String sText)throws SQLException{
    	int iId = getId(sUrl);
    	if(iId > 0){
    		updateText(iId, sText);
    	}
    	else{
    		insertUrl(sUrl, sText);
    	}
     }

    public void insertUrl(String sUrl, String sText)throws SQLException{
        executeUpdate("INSERT INTO URL(URL,TEXT) VALUES(?,?)",sUrl,sText);
     }

    public void updateText(int id,String sText)throws SQLException{
        executeUpdate("UPDATE URL SET TEXT=? WHERE ID=?",sText,id);
    }

    public void deleteUrl(int id)throws SQLException{
        executeUpdate("DELETE FROM URL WHERE ID=?",id);
     }


}
