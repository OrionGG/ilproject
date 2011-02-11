package dao;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Artist;


public class DAO_Interface {


	private static int recomendationsNumber =6; //Number of reesources

	private static int limitValue=2; //Number limit for showing the results	
	
	
	
	
	
	public static int getLimitValue(){
		return limitValue;
	}
	public static int getRecomendationNumber(){
		return recomendationsNumber;
	}

	
	public static List<String> getUserResources(ArrayList <String> list) {
		// TODO Auto-generated method stub
		

    	/*String nameArtist;
    	int i=0;
    	while(i<2){	
	    	System.out.println("\nIntroduce el recurso que quieras:");
	    	InputStreamReader isr = new InputStreamReader(System.in);
	    	BufferedReader bf = new BufferedReader (isr);
	    	try {
				nameArtist = bf.readLine();
				listInterestedArtists.add(nameArtist);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i+=1;
    	}*/
		return list;
	}
	
	public static List<Artist> getUserArtist(ArrayList <String> list) {
		// TODO Auto-generated method stub
	  	//LEER NOMBRE ARTSTA DEL TECLADO
    	List <Artist> listInterestedArtists=new ArrayList <Artist>();
    	String nameArtist;
    	int i=0;
    	while(list.size()>i){	
	    //	System.out.println("\nIntroduce el artista que quieras:");
	    	//InputStreamReader isr = new InputStreamReader(System.in);
	    	//BufferedReader bf = new BufferedReader (isr);
	    	//try {
				//nameArtist = bf.readLine();
	    		nameArtist=list.get(i);
				Artist a=new Artist();
				a.setName(nameArtist);
				listInterestedArtists.add(a);
		//	} catch (IOException e) {
			//	// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			i+=1;
    	}
		return listInterestedArtists;
	}



}
