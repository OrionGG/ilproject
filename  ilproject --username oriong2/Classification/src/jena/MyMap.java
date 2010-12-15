package jena;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;

import dao.DAO_Interface;
import dao.DAO_MySQL;
import dominio.DbPedia;
import dominio.DbPediaResource;
import encoders.*;

public class MyMap {

	

    public static ArrayList<DbPediaResource> convertMapToArrays(Map<Integer,Collection<DbPediaResource>> mapUrlResources, ArrayList<DbPediaResource> urlsResources, ArrayList<DbPediaResource> urlsPersons,  ArrayList<DbPediaResource> urlsPlaces) {
		// TODO Auto-generated method stub
		
		
		ArrayList<DbPediaResource> urlsArtists = new ArrayList<DbPediaResource>();

	
		    		        
	        int bigger=getBiggerValueMap(mapUrlResources);
	       			
			////We go throught the array to get the bigger ones and make a big array of resources in the value order
			System.out.println("CONVERT MAP TO ARRAY: el mayor valor del map es "+bigger);
			 
			////Make bigger the limit number to not saturated the server
			int limit=DAO_Interface.getLimitValue();
			int numRes=DAO_Interface.getRecomendationNumber();
			boolean finish =false;

			a:while((bigger>limit) && !finish){

				Collection<DbPediaResource> urlsValues=mapUrlResources.get(bigger);

				if (urlsValues!=null){
					System.out.print("\n\n\n\nResultados con valor ="+bigger);
					System.out.println(",   Hay "+urlsValues.size()+" recursos");
					//System.out.println("urlsValues tiene "+urlsValues.size());	
		        	Iterator<DbPediaResource> it1=urlsValues.iterator();
		        	 while(it1.hasNext()){
		        		DbPediaResource dpr=it1.next();
		        	
		        		if(dpr!=null && dpr.getUri()!=null){
		        		if(dpr.getUri().startsWith("http://dbpedia.org/resource/")){
		        			
		        		System.out.println(" "+dpr.getUri()+ " ");
		        		//	String name=dpr.getName();
		        		
		        			dpr=DbPedia.getBasicInfo(dpr.getUri());
		        			System.out.println(" "+dpr.getName()+ " ");
		        			//CLASIFICATION OF RESOURCES --If is a band,person or resources
			        		Boolean artist=false; Boolean person=false; Boolean place=false;
		         		try{
		         			
		         			
			        	
				        		if(dpr.getTypes() != null){
				        			Iterator <String>it=dpr.getTypes().iterator();
				        			while(it.hasNext() && !artist){
				        				 String type=it.next();
				        				if(type.endsWith("sMusicGroups") || type.endsWith("Band") || type.contains("MusicGroups")  || type.contains("Artist")|| type.contains("Singers")){
				        					artist=true;
				        					System.out.println("Es un artista+tamaño="+urlsArtists.size()+"\n");
				        					break;
				        					
				        				}else{
				        					//Palce and settlement
				        					
				        					if (type.endsWith("Person")){
				        						person=true;
				        						System.out.println("Es una persona+tamaño="+urlsPersons.size()+"\n");
				        						break;
				        					}else{ 
				        						if( type.contains("Place") || type.equals("Settlement")  || type.contains("Citi")  || type.contains("Captital")){
				        							place=true;
				        							System.out.println("Es un sitio+tamaño="+urlsPlaces.size()+"\n");
				        							break;
				        							
				        						}else{
				        							//Es un resource
				        							
				        						}
				        					}
				        				}
				        			}
				        		}
			        		
		         		
			        		}catch(Exception e){
		        				System.out.println("Problem clasificating");
		        				System.out.println(e.toString());
		        				e.printStackTrace();
		        			}
			        		
		        		
			        		if(artist && urlsArtists.size()<numRes){
			        			urlsArtists.add(dpr);
			        			
			        		}else{
			        			if(person && urlsPersons.size()<numRes){
			        				urlsPersons.add(dpr);
			        				
			        			}else{
			        				if(place && urlsPlaces.size()<numRes){
				        				urlsPlaces.add(dpr);
				        				
				        			}else{
					        			if(urlsResources.size()<2*numRes){
					        				urlsResources.add(dpr);
					        				System.out.println("Es un resource+tamaÃ±o="+urlsResources.size()+"\n");
					        			}
				        			}
			        			}
			        		}
							if((urlsArtists.size()==numRes && urlsResources.size()>=numRes ) || (urlsPersons.size()>numRes/2 && urlsArtists.size()>numRes/2 && urlsResources.size()>numRes/2 && urlsPlaces.size()>numRes/2)){
								limit=50;
								finish=true;
								break a;
							}
							//if(urlsResources.size()>=2*numRes){
								//finish=true;
								//break a;
				//			}
							
		        		}
		        		}
		        	}
			
				}
				bigger=bigger-1;
			
			}
			System.out.println("El Map queda complemamente recorrido\n\n\n");

	
			return urlsArtists; 
	}




	public static void saveMapToDb(String name, Map<Integer, Collection<DbPediaResource>> mapUrlResources) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
			// TODO Auto-generated method stub
	//	mapUrlResources.size();
		ArrayList<DbPediaResource> urlsArtists = new ArrayList<DbPediaResource>();
		DAO_MySQL.setUp();
	   
		//encode.
    		        
        int bigger=MyMap.getBiggerValueMap(mapUrlResources);
       			
		////We go thought the array to get the bigger ones and make a big array of resources in the value order
System.out.println(" Almacen de resultados: el mayor valor es "+bigger);
		while(bigger>0){
			
			Collection<DbPediaResource> urlsValues=mapUrlResources.get(bigger);
			if (urlsValues!=null){
				
				System.out.println("   Hay "+urlsValues.size()+" recursos para el usuario "+ name);
		       	Iterator<DbPediaResource> it1=urlsValues.iterator();
		
		       	while(it1.hasNext()){
		       		DbPediaResource dpr=it1.next();
	        		if(dpr.getName()!=null){	        	
	        			//decodeString(dpr.getUri());
	        			DAO_MySQL.storeResource2(dpr.getName(),dpr.getUri());
	        			DAO_MySQL.asignUser_Resource(name, dpr.getUri(), bigger);
	        		}
		       		
		       	}
		     }
		
		
		bigger--;
		}
		
		
	}
    


	
	public static Map<Integer, Collection<DbPediaResource>> readMapFromDb(String login) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Map<Integer, Collection<DbPediaResource>> mapUrlResources=new HashMap<Integer, Collection<DbPediaResource>>();
		ResultSet rs= null;
		DAO_MySQL.setUp();
		//System.out.println("generada conexion");
		rs=DAO_MySQL.getResources(login);
		//System.out.println("recoje login conexion");
		ArrayList <DbPediaResource> arrayDPResources=new ArrayList <DbPediaResource>();
		int prevValue=0;
	
		while(rs.next()){
			//System.out.println("entra result set conexion");
			String url=rs.getString(2);
			int value=rs.getInt (3);
			if(prevValue==0){
				prevValue=value;
			}
			if(value==prevValue){
				url=Encode.mysqlEncode(url);
				DbPediaResource dp=new DbPediaResource(url);
				arrayDPResources.add(dp);
				
			}else{
				mapUrlResources.put(prevValue, arrayDPResources);
			}
			prevValue=value;
		}
		System.out.println("sale conexion");
			
			///mapUrlResources.put(value, resources);
		return mapUrlResources;
		
	}
    
	public static int getBiggerValueMap(	Map<Integer, Collection<DbPediaResource>> mapUrlResources) {
			// TODO Auto-generated method stub
			Set <Integer> s=mapUrlResources.keySet();
			Iterator it =s.iterator();
			//Get the bigger value of the keys 
			int mayor=0;
			while(it.hasNext()){
				int valor =(Integer) it.next();
				if (valor>mayor){
					mayor=valor;
				}
			}
			return mayor;
		}




	public static Map<Integer, Collection<DbPediaResource>> addMaps(Map<Integer, Collection<DbPediaResource>> newResourcesMap, Map<Integer, Collection<DbPediaResource>> oldResourcesMap) {
		// TODO Auto-generated method stub

		int oldBigger=MyMap.getBiggerValueMap(oldResourcesMap);
		int newBigger=MyMap.getBiggerValueMap(newResourcesMap);
		Map<Integer, Collection<DbPediaResource>> nextResourcesMap=new HashMap<Integer, Collection<DbPediaResource>> ();
		
		int nextBigger=0;
		//Se meten los nuevos recursos si coinciden con los antiguos
		while(0<oldBigger){
			Collection<DbPediaResource> oldColDpr=oldResourcesMap.get(oldBigger);
			if(oldColDpr!=null){
			Iterator <DbPediaResource> oldDprIt=oldColDpr.iterator();
			while(oldDprIt.hasNext()){
				boolean insertado=false;
				DbPediaResource oldDpr=oldDprIt.next();
				
				while(0<newBigger){
					Collection<DbPediaResource> newColDpr=newResourcesMap.get(newBigger);
					if(newColDpr!=null){
						Iterator <DbPediaResource> newDprIt=newColDpr.iterator();
						while(newDprIt.hasNext()){
							
							DbPediaResource newDpr=newDprIt.next();
							
							if(newDpr.getUri().equals(oldDpr.getUri())){
								nextBigger= newBigger+oldBigger;
								Collection<DbPediaResource> list = nextResourcesMap.get(nextBigger);
				                if (list == null){
				                	list=new ArrayList<DbPediaResource> ();
				                }
				                list.add(oldDpr);
								nextResourcesMap.put(nextBigger,list);
								System.out.println("Se suma al recurso "+oldDpr+" con valor "+oldBigger+"un valor y queda con valor"+nextBigger);
								insertado=true;
								
								//FINALIZARLO
							}else{
					
							/*	Collection<DbPediaResource> list = nextResourcesMap.get(oldBigger);
				                if (list == null){
				                	list=new ArrayList<DbPediaResource> ();
				                }
				                list.add(newDpr);
								nextResourcesMap.put(oldBigger,list);*/
							}
						}
						
					}
					newBigger--;
				}
				if(!insertado){
					
					Collection<DbPediaResource> list = nextResourcesMap.get(oldBigger);
	                if (list == null){
	                	list=new ArrayList<DbPediaResource> ();
	                }
	                list.add(oldDpr);
					nextResourcesMap.put(oldBigger,list);
					System.out.println("Se almacena el recurso "+oldDpr+" con valor "+oldBigger+"tal y como estaba");
				}
				
			
			
			}
			
		}
		oldBigger--;
		}
		newBigger=MyMap.getBiggerValueMap(newResourcesMap);
		nextBigger=MyMap.getBiggerValueMap(nextResourcesMap);
		Map<Integer, Collection<DbPediaResource>> lastResourcesMap=new HashMap<Integer, Collection<DbPediaResource>> ();
		//se meten los nuevos recursos si no coinciden con el anterior
		while(0<newBigger){
			Collection<DbPediaResource> newColDpr=newResourcesMap.get(newBigger);
			if(newColDpr!=null){
			Iterator <DbPediaResource> newDprIt=newColDpr.iterator();
			while(newDprIt.hasNext()){
				
				DbPediaResource newDpr=newDprIt.next();
				
				while(0<nextBigger){
					Collection<DbPediaResource> nextColDpr=newResourcesMap.get(nextBigger);
					if(nextColDpr!=null){
					Iterator <DbPediaResource> nextDprIt=nextColDpr.iterator();
					while(nextDprIt.hasNext()){
						
						DbPediaResource nextDpr=nextDprIt.next();
						
						if(newDpr.getUri().equals(nextDpr.getUri())){
						}else{
							
							Collection<DbPediaResource> list = nextResourcesMap.get(newBigger);
			                if (list == null){
			                	list=new ArrayList<DbPediaResource> ();
			                }
			                list.add(newDpr);
							nextResourcesMap.put(newBigger,list);
							System.out.print("Se añde el recurso "+newDpr.getName()+" el vlor "+newBigger);
							
						}
					}
		
					}
					nextBigger--;
			
				}
			}
			
		}newBigger--;
		}
		return nextResourcesMap;
		
	}




	public static Map<Integer, Collection<DbPediaResource>> subtractMap(	Map<Integer, Collection<DbPediaResource>> newResourcesMap,	Map<Integer, Collection<DbPediaResource>> oldResourcesMap) {
		
		int oldBigger=MyMap.getBiggerValueMap(oldResourcesMap);
		int newBigger=MyMap.getBiggerValueMap(newResourcesMap);
		Map<Integer, Collection<DbPediaResource>> nextResourcesMap=new HashMap<Integer, Collection<DbPediaResource>> ();
		
		int nextBigger=0;
		while(oldResourcesMap.size()<oldBigger){
			Collection<DbPediaResource> oldColDpr=oldResourcesMap.get(oldBigger);
			if(oldColDpr!=null){
			Iterator <DbPediaResource> oldDprIt=oldColDpr.iterator();
			while(oldDprIt.hasNext()){
				
				DbPediaResource oldDpr=oldDprIt.next();
				
				while(newResourcesMap.size()<newBigger){
					Collection<DbPediaResource> newColDpr=newResourcesMap.get(newBigger);
					if(newColDpr!=null){
					Iterator <DbPediaResource> newDprIt=newColDpr.iterator();
					while(newDprIt.hasNext()){
						
						DbPediaResource newDpr=newDprIt.next();
						
						if(newDpr.getUri().equals(oldDpr.getUri())){
							nextBigger= oldBigger-newBigger;
							Collection<DbPediaResource> list = nextResourcesMap.get(nextBigger);
			                if (list == null){
			                	list=new ArrayList<DbPediaResource> ();
			                }
			                list.add(oldDpr);
							nextResourcesMap.put(nextBigger,list);
							
							//FINALIZARLO
						}else{
				
							Collection<DbPediaResource> list = nextResourcesMap.get(oldBigger);
			                if (list == null){
			                	list=new ArrayList<DbPediaResource> ();
			                }
			                list.add(oldDpr);
							nextResourcesMap.put(oldBigger,list);
						}
					}
					}
					newBigger--;
			}
				
			}
			
		}oldBigger--;
		}
		newBigger=MyMap.getBiggerValueMap(newResourcesMap);
		nextBigger=MyMap.getBiggerValueMap(nextResourcesMap);
		Map<Integer, Collection<DbPediaResource>> lastResourcesMap=new HashMap<Integer, Collection<DbPediaResource>> ();
		
		while(newResourcesMap.size()<newBigger){
			Collection<DbPediaResource> newColDpr=newResourcesMap.get(newBigger);
			if(newColDpr!=null){
			Iterator <DbPediaResource> newDprIt=newColDpr.iterator();
			while(newDprIt.hasNext()){
				
				DbPediaResource newDpr=newDprIt.next();
				
				while(nextResourcesMap.size()<nextBigger){
					Collection<DbPediaResource> nextColDpr=newResourcesMap.get(nextBigger);
					if(nextColDpr!=null){
					Iterator <DbPediaResource> nextDprIt=nextColDpr.iterator();
					while(nextDprIt.hasNext()){
						
						DbPediaResource nextDpr=nextDprIt.next();
						
						if(newDpr.getUri().equals(nextDpr.getUri())){
						}else{
							
							Collection<DbPediaResource> list = nextResourcesMap.get(newBigger);
			                if (list == null){
			                	list=new ArrayList<DbPediaResource> ();
			                }
			                list.add(newDpr);
							nextResourcesMap.put(newBigger,list);
							
						}
					}
		
					}nextBigger--;
			
				}
			}
			
		}newBigger--;
		}
		
		
		// TODO Auto-generated method stub
		return nextResourcesMap;
	}



}
