package CategoryGenerator;

import java.util.Hashtable;

public class StringToCategories {
	private Hashtable<String, Categories> CategoriesTable = new Hashtable<String, Categories>();
	
	private static StringToCategories oInstance = null;
	
	public StringToCategories(){
		CategoriesTable = new Hashtable<String, Categories>();
		for(Categories oCategories: Categories.allCategories){
			CategoriesTable.put(oCategories.toString(), oCategories);
		}		
	}
	
	public static StringToCategories getInstance(){
		if(oInstance == null){
			oInstance = new StringToCategories();
		}
		return oInstance;
	}
	
	public static Categories getCategory(String sCategoryName){
		Categories sResult = StringToCategories.getInstance().CategoriesTable.get(sCategoryName);
		return sResult;
	}

}
