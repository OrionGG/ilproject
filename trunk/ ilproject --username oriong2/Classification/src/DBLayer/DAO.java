/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBLayer;

import java.sql.*;

/**
 *
 * @author chemi
 */
public class DAO {
	public String SERVERNAME="cat";
	

    public DAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Error cargando el driver de acceso a base de datos.");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/"+SERVERNAME,"root", "admin");
    }

    protected ResultSet executeQuery(String query, Object ...parameters) throws SQLException {
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement(query);
        if (parameters != null && parameters.length > 0) {
            int i = 1;
            for (Object parameter : parameters) {
                statement.setObject(i++, parameter);
            }
        }
        return statement.executeQuery();
    }

    protected void executeUpdate(String query, Object ...parameters)throws SQLException{
        Connection con=getConnection();
        PreparedStatement statement=con.prepareStatement(query);
        if (parameters != null && parameters.length > 0) {
            int i = 1;
            for (Object parameter : parameters) {
                statement.setObject(i++, parameter);
            }
        }
        statement.executeUpdate();

    }

    protected ResultSet executeSimpleQuery(String query) throws SQLException{
        Connection con =getConnection();
        PreparedStatement statement =con.prepareStatement(query);
        return statement.executeQuery();
    }
}
