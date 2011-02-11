package encoders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Encode {

	

	public static String encodeString(String any) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

		if (any.contains("_")){
			any=any.replace("_", " ");
		}if (any.contains("-")){
			any=any.replace("-", " ");
		}
		if (any.contains("@en")){
			any=any.replace("@en", "");
		}
	//	System.out.println(", despues de la cod, el nobre queda= "+any+ " ");
	
		return any;
	}
	public static String encodeURL(String any) throws UnsupportedEncodingException {
	////Encode//UTF-8 y 16
		
		//ISO-8859-1
		// URLUTF8Encoder
		any=URLEncoder.encode(any,"UTF-8");
		return any;
	}
	
	public static String decodeURL(String any) throws UnsupportedEncodingException {
		////Encode//UTF-8 y 16
			
			//ISO-8859-1
			// URLUTF8Encoder
			//any=URLDecoder.decode(any,"UTF-8");
			return any;
		}

	private static String decodeString(String any) {
		// TODO Auto-generated method stub
		if (any.contains(" ")){
			any=any.replace(" ", "_");
		}
		/*if (any.contains("-")){
			any=any.replace("-", " ");
		}
		if (any.contains("@en")){
			any=any.replace("@en", "");
		}*/
			return any;
	}
	public static String mysqlEncode(String url) {
		// TODO Auto-generated method stub
		System.out.print(url);
		if(url.contains("%3A")){
			url=url.replace("%3A",":");
		}
		if(url.contains("%2F")){
			url=url.replace("%2F", "/");
			
		}
		System.out.print(url);
		return url;
	}
	public static String getNameFromUrl(String url) throws UnsupportedEncodingException{
		//http://dbpedia.org/resource/ -> 
		url=encodeURL(url);
		String name=url.substring(38, url.length());
		//System.out.print("El nombre es"+name);
		name=encodeString(name);
		return name;
	}

}
