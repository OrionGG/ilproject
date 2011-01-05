package retriever;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.*;

import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;

import Analizer.SpanishAnalyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.util.Version;

import entities.APiecesOfNews;
import entities.APiecesOfNews.NewsType;
import entities.DB.APieceOfNewsDao;




public class GetNews {

	public static void GetCasesFromUrl(String sWebPageParam, int pagesPerCategory) throws Exception{

		String sWebPage = "http://www.elpais.com/";



		if(sWebPageParam != null)
		{
			sWebPage = sWebPageParam;
		}	


		APieceOfNewsDao oAPieceOfNewsDao = new APieceOfNewsDao();
		List<String> oUrlsToDelete = new ArrayList<String>();

		for(APiecesOfNews.NewsType oN : APiecesOfNews.oArrayNewsTypes){


			getNewsFromType(sWebPage, oAPieceOfNewsDao, oN, pagesPerCategory);
		}
	}

	private static void getNewsFromType(String sWebPage,
			APieceOfNewsDao oAPieceOfNewsDao, APiecesOfNews.NewsType oN, int pagesPerCategory)
	throws MalformedURLException, IOException {
		String sUrl = sWebPage + oN.ToString();
		AddNews(sUrl, oN, oAPieceOfNewsDao, 1.1);

		List<String> oUrls = Spider.GetSubUrls.SpiderUrl(sUrl, 1, pagesPerCategory - 1, "");
		for(String sUrlSub : oUrls){
			//Reader stringReader = new StringReader(sText);
			//TokenStream tokenStream = (new SpanishAnalyzer()).tokenStream("defaultFieldName", stringReader);
			//sText =  (new Analizer.TermAnalyzerView()).GetView(tokenStream, 0).trim();
			AddNews(sUrlSub, oN, oAPieceOfNewsDao, 1);
		}
	}

	private static void AddNews(String sUrl, NewsType oNewsType, APieceOfNewsDao oAPieceOfNewsDao, double dWeight)  {		

		List<Integer> iList = new ArrayList<Integer>();
		try {
			iList = oAPieceOfNewsDao.getId(sUrl);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(int iId : iList){
			try {
				APiecesOfNews oAPieceOfNews = oAPieceOfNewsDao.getApieceOfNews(iId);
				NewsType oNewsTypeId = oAPieceOfNews.getNewsType();
				if(oNewsTypeId.equals(oNewsType)){
					return;
				}
				if(oAPieceOfNews.getWeigt() > 1){
					return;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String sText = "";
		if(iList.size() == 0){
			try {
				sText = GetBlogText.ExtractText.GetBlogText(sUrl, false);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			if(sText.equals("")){
				return;
			}
		}
		else{
			try {
				sText = oAPieceOfNewsDao.getApieceOfNews(iList.get(0)).getsSimpleText();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for(int iId : iList){
			try {
				oAPieceOfNewsDao.updateTextWeight(iId, sText , Math.pow((0.9), iList.size())* dWeight);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		try {
			oAPieceOfNewsDao.insertUrl(sUrl, sText, oNewsType,  Math.pow((0.9), iList.size())* dWeight);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<APiecesOfNews> GetCasesFromDB() throws IOException{
		List<APiecesOfNews> oListAPiecesOfNews = new ArrayList<APiecesOfNews>();
		APieceOfNewsDao oAPieceOfNewsDao = new APieceOfNewsDao();
		try {
			oListAPiecesOfNews = oAPieceOfNewsDao.getAllNews();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oListAPiecesOfNews;

	}


	public static void main(String[] args) throws Exception {		
		GetNews.GetCasesFromUrl(null, 30);

		/*String sWebPage = "http://www.elpais.com/";
		APieceOfNewsDao oAPieceOfNewsDao = new APieceOfNewsDao();
		List<String> oUrlsToDelete = new ArrayList<String>();
		getNewsFromType(sWebPage, oAPieceOfNewsDao, NewsType.DEPORTES);*/
	}
}
