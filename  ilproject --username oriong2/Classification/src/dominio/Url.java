package dominio;

import java.util.TreeMap;

public class Url {
	private String url;
	private Category originalCategory;
	private TreeMap <Float,Category> categoryScore =new  TreeMap <Float,Category>();

	public  TreeMap <Float,Category> getCategoryScore(){
		return categoryScore;
	}
	public String getUrl(){
		return url;
	}
	public Category getOriginalCategory(){
		return originalCategory;
	}
}
