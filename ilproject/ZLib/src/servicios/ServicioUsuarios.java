/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servicios;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.musicbrainz.model.Artist;

import jena.Algorithym;
import jena.Jenate;
import jena.MyMap;
import jena.MyModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;

import dao.*;
import dominio.*;

/**
 *
 * @author chemi
 */
public class ServicioUsuarios {
    private final static ServicioUsuarios instancia = new ServicioUsuarios();

    private ServicioUsuarios(){
        
    }

    public static ServicioUsuarios getInstancia() {
        return instancia;
    }
    
    public Usuario validaUsuario(String login, String password) throws SQLException {
        DAOUsuarios daoUsuarios = new DAOUsuarios();
        String passwordBD = daoUsuarios.getPasswordUsuario(login);
        if (passwordBD != null && passwordBD.length() > 0)  {
            if (passwordBD.equals(password)) {
                return daoUsuarios.getUsuarioByLogin(login);
            }
        }
    
        return null;
    }


  public Usuario generaUsuario(String login, String password,String email) throws SQLException {
        DAOUsuarios daoUsuarios = new DAOUsuarios();
        Usuario u=null;
         u=daoUsuarios.setUsuario(login,password,email);
        if (u !=null)  {
         	return u;  
        }
        return u;
    }

public static  ArrayList<DbPediaResource> getFavorites(String login)  {
	// TODO Auto-generated method stub
	ArrayList <DbPediaResource> favoritesDbR=new ArrayList<DbPediaResource> ();
	try{
		System.out.println("Entra en getFav");
	DAO_MySQL.setUp();
	ResultSet rs=DAO_MySQL.readFavorites(login);
	System.out.print("Entra read favoritos");
	while(rs.next()){
		String uri=rs.getString(2);
			if(uri!=null){
		
			System.out.println(uri);
			DbPediaResource dpr=new DbPediaResource(uri);
			dpr=DbPedia.getBasicInfo(uri);
			favoritesDbR.add(dpr);
		}
	}
	System.out.print("Sale en read favoritos ");
	}catch(Exception e){
		System.out.print("Exception en "+ e.toString());
	}
	return favoritesDbR;
}

public static void saveFavoritesToDb(List<String> listInterestedResources, String name) {
	try{
    	System.out.println("\nFavoritos guardandose hay "+listInterestedResources.size());
    	DAO_MySQL.setUp();
	DAO_MySQL.saveFavorites(listInterestedResources,name);
}catch(Exception e){
	System.out.print("\n\nException en "+ e.toString()+"\n\n");
}

}

public static ArrayList<DbPediaResource> deleteFavorite(String login, String pathResource) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
	// TODO Auto-generated method stub
	System.out.println("Entra en deleteFav");
	ArrayList<DbPediaResource> favoritesDPR=null;
	try{
	List <String>listInterestedResources=new ArrayList <String>();
    listInterestedResources.add(pathResource);
	ServicioUsuarios.saveFavoritesToDb(listInterestedResources, login);
	Model m=DbPedia.getRelatedModel(pathResource);
	
	Map<Integer, Collection<DbPediaResource>> newResourcesMap = MyModel.filterModelToMap(m,login);
	Map<Integer, Collection<DbPediaResource>> oldResourcesMap=MyMap.readMapFromDb(login);
	Map<Integer, Collection<DbPediaResource>> nextResourcesMap=MyMap.subtractMap(newResourcesMap, oldResourcesMap);
	MyMap.saveMapToDb(login, nextResourcesMap);
		favoritesDPR=ServicioUsuarios.getFavorites(login);
		System.out.print("Sale de deleteFav");
}catch(Exception e){
	System.out.print("Exception en "+ e.toString());
}
	return favoritesDPR;
}

public static ArrayList<DbPediaResource> addFavorites(String login, ArrayList <String> favorites)  {
	// TODO Auto-generated method stub
	ArrayList<DbPediaResource> favoritesDPR2=null;
	try{
		System.out.println("\nADD FAVORITE");
    //ArrayList<DbPediaResource> favoritesDPR=ServicioUsuarios.getFavorites(login);
    
    List <String>listFavoritedResources=new ArrayList <String>();
    List <Statement>statementsEliminar=new ArrayList <Statement>();
    List <Artist>listInterestedArtists=new ArrayList <Artist>();
	List<String> listInterestedResources=new ArrayList <String>();
	List<String> listFinalInterestedResources=new ArrayList <String>();
	System.out.print("Recorre los nuevos favoritos");
    int i =0;
    while(i<favorites.size()){
    	
    	String pathResource =favorites.get(i);
    	i++;
    	if(!(pathResource.startsWith("http://"))){
    		pathResource=DbPedia.getDbpediaUrlResource(pathResource);
    		pathResource=pathResource.replace("<", "").replace(">", "");
    	}
    	
	    listFavoritedResources.add(pathResource);
		ServicioUsuarios.saveFavoritesToDb(listFavoritedResources, login);
		System.out.println("\nGET RELATED MODEL Of"+pathResource);
		Model m=ModelFactory.createDefaultModel();
		listInterestedResources.add(pathResource);
    }
	
	
	Model childModel=Jenate.main(listInterestedResources, listInterestedArtists, listFinalInterestedResources);
	System.out.println("\nGET THE OLD MAP");
		Map<Integer, Collection<DbPediaResource>> oldResourcesMap=MyMap.readMapFromDb(login);
	System.out.println("\nFILTER THE MAP");
	Map<Integer, Collection<DbPediaResource>> newResourcesMap;
	if(childModel.size()>0){
		newResourcesMap = MyModel.filterModelToMap(childModel,login);
		System.out.println("\nADDING MAPS");
		if(newResourcesMap.size()>0){
			Map<Integer, Collection<DbPediaResource>> nextResourcesMap=MyMap.addMaps(newResourcesMap, oldResourcesMap);
			MyMap.saveMapToDb(login, nextResourcesMap);
			
		}else{
			System.out.print("->El new map esta vacio");
		}
		
	}else{
		System.out.print("->El new model esta vacio");
	}
	//HYA QUE CAMBIAR PRA LEER de LA BD EL MODELO, NO EL MAP 

	

//	favoritesDPR2=ServicioUsuarios.getFavorites(login);
	System.out.println("Envia addFav a favoritos.jsp");
}catch(Exception e){
	System.out.print("Exception en "+ e.toString());
}
	return favoritesDPR2;
}

public static Map<Integer, Collection<DbPediaResource>> deleteRecomendation(String login, String pathResource)  {
	// TODO Auto-generated method stub
	Map<Integer, Collection<DbPediaResource>> nextResourcesMap=null;
	try{
		System.out.println("Entra en deleteRec");
	Model m=DbPedia.getRelatedModel(pathResource);

	Map<Integer, Collection<DbPediaResource>> newResourcesMap = MyModel.filterModelToMap(m,login);
	Map<Integer, Collection<DbPediaResource>> oldResourcesMap=MyMap.readMapFromDb(login);
	nextResourcesMap=MyMap.addMaps(newResourcesMap, oldResourcesMap);
	MyMap.saveMapToDb(login, nextResourcesMap);
	System.out.println("sale de deleteRec");
	}catch(Exception e){
		System.out.print("Exception en "+ e.toString());
	}
	return nextResourcesMap;
}
}

