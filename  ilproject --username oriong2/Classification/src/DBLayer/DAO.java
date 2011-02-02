/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBLayer;

import java.sql.*;

import com.sun.rowset.CachedRowSetImpl;

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

    protected CachedRowSetImpl executeQuery(String query, Object ...parameters) throws SQLException {
        Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement(query);
        if (parameters != null && parameters.length > 0) {
            int i = 1;
            for (Object parameter : parameters) {
                statement.setObject(i++, parameter);
            }
        }

        ResultSet rs = statement.executeQuery();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(rs);
        con.close();
        return crs;
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
        con.close();
    }

    protected CachedRowSetImpl executeSimpleQuery(String query) throws SQLException{
        Connection con =getConnection();
        PreparedStatement statement =con.prepareStatement(query);
        

        ResultSet rs = statement.executeQuery();
        CachedRowSetImpl crs = new CachedRowSetImpl();
        crs.populate(rs);
        con.close();
        return crs;
    }
}
