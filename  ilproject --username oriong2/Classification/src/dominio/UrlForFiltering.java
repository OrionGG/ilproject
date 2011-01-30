package dominio;

public class UrlForFiltering {
	public UrlForFiltering(String mainUrl, String restUrl, String suffixFilter) {
		super();
		sMainUrl = mainUrl;
		sRestUrl = restUrl;
		sSuffixFilter = suffixFilter;
	}
	
	public String sMainUrl;
	public String sRestUrl;
	public String sSuffixFilter;
	
}
