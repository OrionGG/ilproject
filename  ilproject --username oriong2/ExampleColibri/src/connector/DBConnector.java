package connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBConnector {
	public void loadDriver();
	public void setConnection(String sDBName) throws Exception;
	public ResultSet executeQuery(String query, Object ...parameters) throws SQLException;

    public void executeUpdate(String query, Object ...parameters)throws Exception;

    public ResultSet executeSimpleQuery(String query)throws Exception;
}
