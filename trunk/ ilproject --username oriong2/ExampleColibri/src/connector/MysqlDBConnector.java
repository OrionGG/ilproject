package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDBConnector  implements DBConnector{
	private static MysqlDBConnector instance = null;
	private Connection oCon = null;
	
	public MysqlDBConnector(){
		loadDriver();
	}
	
	public static MysqlDBConnector getInstance() {
	      if(instance == null) {
	         instance = new MysqlDBConnector();
	      }
	      return instance;
	   }
	
	public void loadDriver() {
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Error cargando el driver de acceso a base de datos.");
            e.printStackTrace();
        }
	}
	
	public void setConnection(String sDBName) throws SQLException {
		String connectionUrl ="jdbc:mysql://localhost:3306/"+ sDBName +"?user=root";
		oCon = DriverManager.getConnection(connectionUrl);
	}
	
	 public ResultSet executeQuery(String query, Object ...parameters) throws SQLException {
	        
	        PreparedStatement statement = oCon.prepareStatement(query);
	        if (parameters != null && parameters.length > 0) {
	            int i = 1;
	            for (Object parameter : parameters) {
	                statement.setObject(i++, parameter);
	            }
	        }
	        return statement.executeQuery();
	    }

	    public void executeUpdate(String query, Object ...parameters)throws SQLException{
	        
	        PreparedStatement statement=oCon.prepareStatement(query);
	        if (parameters != null && parameters.length > 0) {
	            int i = 1;
	            for (Object parameter : parameters) {
	                statement.setObject(i++, parameter);
	            }
	        }
	        statement.executeUpdate();

	    }

	    public ResultSet executeSimpleQuery(String query) throws SQLException{

	        PreparedStatement statement =oCon.prepareStatement(query);
	        return statement.executeQuery();
	    }
}
