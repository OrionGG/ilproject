package dominio;

import java.util.Hashtable;


public class StringToCategories {
	private Hashtable<String, Category> CategoriesTable = new Hashtable<String, Category>();
	
	private static StringToCategories oInstance = null;
	
	public StringToCategories(){
		CategoriesTable = new Hashtable<String, Category>();
		for(Category oCategories: Category.allCategories){
			CategoriesTable.put(oCategories.toString(), oCategories);
		}		
	}
	
	public static StringToCategories getInstance(){
		if(oInstance == null){
			oInstance = new StringToCategories();
		}
		return oInstance;
	}
	
	public static Category getCategory(String sCategoryName){
		Category sResult = StringToCategories.getInstance().CategoriesTable.get(sCategoryName);
		return sResult;
	}

}
