package entities;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;


public class APiecesOfNews implements CaseComponent {
	public static APiecesOfNews.NewsType[] oArrayNewsTypes = new APiecesOfNews.NewsType[]{
			APiecesOfNews.NewsType.INTERNACIONAL,
			APiecesOfNews.NewsType.ESPAÑA,
			APiecesOfNews.NewsType.DEPORTES,
			APiecesOfNews.NewsType.ECONOMIA,
			APiecesOfNews.NewsType.TECNOLOGIA,
			APiecesOfNews.NewsType.CULTURA,
			APiecesOfNews.NewsType.GENTEYTV,
			APiecesOfNews.NewsType.SOCIEDAD
	};
	public enum NewsType {
		NONE("none"),
		INTERNACIONAL("internacional"),
		ESPAÑA("espana"),
		DEPORTES("deportes"),
		ECONOMIA("economia"),
		TECNOLOGIA("tecnologia"),
		CULTURA("cultura"),
		GENTEYTV("gentetv"),
		SOCIEDAD("sociedad");
		
		
		private String sToString;
		
		private NewsType(String sName){
			sToString = sName;
		}
		
		public String ToString(){
			return sToString;
			
		}
		
		public static NewsType toNewsType(String sName) throws NullPointerException{
			NewsType oNewstype = null ;

			for(APiecesOfNews.NewsType oN : APiecesOfNews.oArrayNewsTypes){
				if(sName.equals(oN.ToString())){
					oNewstype = oN;
					break;
				}
			}
			if(oNewstype == null){
				throw new NullPointerException();
			}
			
			return oNewstype;
			
		}
		
		public boolean equals(NewsType oNewsType){
			return this.ToString().equals(oNewsType.ToString());
		}
		
	}
	
	public String surl;
	public String ssimpletext;
	public NewsType onewstype;
	public double dWeight = 1;
	
    

	public double getWeigt() {
		return dWeight;
	}

	public void setWeigt(double dWeight) {
		this.dWeight = dWeight;
	}

	public APiecesOfNews(String sUrlParam, String sSimpleTextParam, NewsType oNewsTypeParam, double dWeightParam){
		surl = sUrlParam;
		ssimpletext = sSimpleTextParam;
		onewstype = oNewsTypeParam;
		dWeight = dWeightParam;
	}
	
	public void setsUrls(String sUrl) {
		this.surl = sUrl;
	}
	public String getsUrls() {
		return surl;
	}
	public void setsSimpleText(String sSimpleText) {
		this.ssimpletext = sSimpleText;
	}
	public String getsSimpleText() {
		return ssimpletext;
	}
	public void setNewsType(NewsType oNewsType) {
		this.onewstype = oNewsType;
	}
	public NewsType getNewsType() {
		return onewstype;
	}

	@Override
	public Attribute getIdAttribute() {

		return new Attribute("surl",this.getClass());
	}
	
	
}
