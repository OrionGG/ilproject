package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jena.Jenate;

import dominio.DbPediaResource;
import encoders.Encode;


public class DAO_MySQL {

	protected static Connection conexion;

	public static void setUp() throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		   Class.forName("com.mysql.jdbc.Driver");
		    conexion = DriverManager.getConnection("jdbc:mysql://localhost/myweb", "root", "admin");
	}

    public static boolean readResource(String urlResource) throws SQLException {
	Statement st = conexion.createStatement();
	st.executeUpdate("SELECT (name,url,relation,resource) FROM resources WHERE url='"+urlResource+"')");
	// TODO Auto-generated method stub	
	ResultSet rs=st.getResultSet();
	return rs.next();
	
}
    

	public static void storeResourceInfo(String resource, String urlResource, String image,String type) throws SQLException {
		
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO resource(name,url,image,type) VALUES ('"+resource+"','"+urlResource+"','"+image+"','"+type+"')");
		//st.executeUpdate("INSERT INTO properties (name,url,value) VALUES ('"+resource+"','"+urlProperty+"','"+value+"')");
		//st.executeUpdate("INSERT INTO user_resources (nameUser,nameResources,value) VALUES ('"+user+"','"+resource+"','"+value+"')");
	}
	public static void storeResource(String resource, String urlResource) throws SQLException, UnsupportedEncodingException {
		System.out.print(urlResource);
		Statement st = conexion.createStatement();
		urlResource=Encode.encodeString(urlResource);
		urlResource=Encode.encodeURL(urlResource);
		
		st.executeUpdate("INSERT INTO resource(name,url) VALUES ('"+resource+"','"+urlResource+"')");
		//st.executeUpdate("INSERT INTO properties (name,url,value) VALUES ('"+resource+"','"+urlProperty+"','"+value+"')");
		//st.executeUpdate("INSERT INTO user_resources (nameUser,nameResources,value) VALUES ('"+user+"','"+resource+"','"+value+"')");
	}
	public static void storeResource2(String resource, String urlResource) throws SQLException, UnsupportedEncodingException {
		//System.out.println(urlResource);
		Statement st = conexion.createStatement();
		//urlResource=Encode.encodeString(urlResource);
		//urlResource=Encode.encodeURL(urlResource);
		if(urlResource.contains("'")){
			urlResource=urlResource.replace("'","");
		}
		st.executeUpdate("INSERT INTO resource(name,url) VALUES ('"+resource+"','"+urlResource+"')");
		
		//st.executeUpdate("INSERT INTO properties (name,url,value) VALUES ('"+resource+"','"+urlProperty+"','"+value+"')");
		//st.executeUpdate("INSERT INTO user_resources (nameUser,nameResources,value) VALUES ('"+user+"','"+resource+"','"+value+"')");
	}
	public static void asignUser_Resource(String user, String urlResource,int value) throws SQLException, UnsupportedEncodingException {
		
		Statement st = conexion.createStatement();
		if(urlResource.contains("'")){
			urlResource=urlResource.replace("'","");
		}
		//urlResource=Encode.encodeURL(urlResource);
		st.executeUpdate("INSERT INTO user_resources (name,url,value) VALUES ('"+user+"','"+urlResource+"','"+value+"')");

	}
	public static void storeUser(String login, String email, String pass) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO user(name,email,password) VALUES ('"+login+"','"+email+"','"+pass+"')");
		
	}
	public static ResultSet getResources(String login) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
	String query="SELECT * FROM user_resources WHERE name='"+login+"' ORDER BY value DESC";
		System.out.println(query);
		ResultSet rs=st.executeQuery(query);

		return rs;
	}
	/*
	public static void storeResource(String resource, String urlResource,String relation,String resource2,String property,String urlProperty,int value,String user) throws SQLException {
		// TODO Auto-generated method stub
		Statement st = conexion.createStatement();
		st.executeUpdate("INSERT INTO resources(name,url,relation,resource) VALUES ('"+resource+"','"+urlResource+"','"+relation+"','"+resource2+"');" +
				"INSERT INTO properties (name,url,value) VALUES ('"+resource+"','"+url+"','"+number+"');INSERT INTO user_resources (nameUser,nameResource,value) VALUES ('"+user+"','"+resource+"','"+value+"')");
		
		
	}
	*/





	public static void saveFavorites(List<String> listInterestedResources, String name) throws SQLException {
		// TODO Auto-generated method stub
	
		Statement st = conexion.createStatement();
		Iterator<String> it=listInterestedResources.iterator();
		System.out.println("save favorites: Entra en DAO_MySQL ");
		
		while(it.hasNext()){
			String favoritePath=it.next();
			System.out.println(favoritePath);
			st.executeUpdate("INSERT INTO favorites(user,url) VALUES ('"+name+"','"+favoritePath+"')");
			System.out.println("Entra en DAO_MySQL, insert");
		}
		
	}





	public static ResultSet readFavorites(String login) throws SQLException {
		// TODO Auto-generated method stub
		System.out.print("Entra en DAO_MySQL: ");
		Statement st = conexion.createStatement();
		String query="SELECT * FROM favorites WHERE user='"+login+"'";
			System.out.println(query);
			ResultSet rs=st.executeQuery(query);
		
			return rs;
	}

}
