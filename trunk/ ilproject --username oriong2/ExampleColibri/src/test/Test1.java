package test;
/**
 * Test13a.java
 * jCOLIBRI2 framework. 
 * @author Juan A. Recio-García.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 23/06/2007
 */



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import retriever.GetNews;

import comparers.*;

import connector.TheNewsConnector;
import entities.*;
import entities.APiecesOfNews.NewsType;


import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.ExecutionException;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.main.SwingProgressBar;

/**
 * This test shows how to use the Textual CBR extension in a Restaurant recommender. See the jcolibri.extensions.textual.IE package documentation for
 * details about this extension. This example uses the OpenNLP implementation.
 * <br>
 * It uses a custum connector (RestaurantConnector) and similarity functions (AverageMultipleTextValues and TokensContained).
 * The connector loads cases from a normal txt file and the similarity functions work with the information extracted by the textual CBR methods.
 * These methods extract information from text and store it in the other attributes of the description. That information is stored as a string with
 * several values separated with white spaces, so specific similarity measures are requiered to compare those attributes.
 * See their javadoc for more information.
 * <br>
 * To compare the texts it uses a textual similarity function from the jcolibri.method.retrieve.NNretrieval.similarity.local.textual package.
 * Test13b uses the Lucene similarity function instead that one.
 * 
 * @author Juan A. Recio-Garcia
 * @version 1.0
 * 
 * @see jcolibri.test.test13.similarity.AverageMultipleTextValues
 * @see jcolibri.test.test13.similarity.TokensContained
 * @see jcolibri.test.test13.connector.RestaurantsConnector
 * @see jcolibri.extensions.textual.IE
 */
public class Test1 implements StandardCBRApplication
{
	List<APiecesOfNews> oNewsList;
	Connector _connector;
	CBRCaseBase _caseBase;


	/*
	 * (non-Javadoc)
	 * 
	 * @see jcolibri.cbraplications.BasicCBRApplication#configure()
	 */
	public void configure() throws ExecutionException
	{
		try
		{
			//Use a custom connector
			_connector = new TheNewsConnector(oNewsList);
			_caseBase = new LinealCaseBase();

			//To show the progress
			jcolibri.util.ProgressController.clear();
			SwingProgressBar pb = new SwingProgressBar();
			jcolibri.util.ProgressController.register(pb);   
		} catch (Exception e)
		{
			throw new ExecutionException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcolibri.cbraplications.StandardCBRApplication#preCycle()
	 */
	public CBRCaseBase preCycle() throws ExecutionException
	{

		//Obtain cases
		_caseBase.init(_connector);
		Collection<CBRCase> cases = _caseBase.getCases();

		//Perform IE methods in the cases
		try {
			NewsAnalyzer.analyze(cases);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return _caseBase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcolibri.cbraplications.StandardCBRApplication#cycle(jcolibri.cbrcore.CBRQuery)
	 */
	public void cycle(CBRQuery query) throws ExecutionException
	{
		Collection<CBRCase> cases = _caseBase.getCases();

		//Perform IE methods in the cases
		try {
			NewsAnalyzer.analyze(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//Now we configure the NN method with some user-defined similarity measures
		NNConfig nnConfig = new NNConfig();
		nnConfig.setDescriptionSimFunction(new Multiply());

		Attribute at1 = new Attribute("ssimpletext", APiecesOfNews.class);
		nnConfig.addMapping(at1, new AverageCoefficient());

		Attribute at2 = new Attribute("dWeight", APiecesOfNews.class);
		nnConfig.addMapping(at2, new WeightCoefficient());

		System.out.println("RESULT:");
		Collection<RetrievalResult> res = NNScoringMethod.evaluateSimilarity(cases, query, nnConfig);
		res = SelectCases.selectTopKRR(res, APiecesOfNews.oArrayNewsTypes.length);

		for(RetrievalResult rr: res){
			System.out.println(rr);
			System.out.println(((APiecesOfNews)rr.get_case().getDescription()).getsUrls().toString());
			System.out.println(((APiecesOfNews)rr.get_case().getDescription()).getNewsType().ToString());
			//System.out.println();
		}


		System.out.println();
		APiecesOfNews qrd = (APiecesOfNews)query.getDescription();
		CBRCase mostSimilar = res.iterator().next().get_case();
		APiecesOfNews rrd = (APiecesOfNews)mostSimilar.getDescription();
		System.out.println(rrd.getNewsType().ToString());
		System.out.println();

		ResultsAvegare(res);

	}

	private void ResultsAvegare(Collection<RetrievalResult> res) {
		Map<NewsType, Double> oAverageType = new TreeMap<NewsType, Double>();
		Map<NewsType, Integer> oCountType = new TreeMap<NewsType, Integer>();

		for(RetrievalResult rr: res){
			APiecesOfNews oAPiecesOfNews = ((APiecesOfNews)rr.get_case().getDescription());
			NewsType oNewsType = oAPiecesOfNews.getNewsType();
			for(NewsType oN : APiecesOfNews.oArrayNewsTypes){
				if(oNewsType.equals(oN)){
					int countValue = 0;
					try{
						countValue = (Integer)oCountType.get(oN);
					}
					catch(NullPointerException ex){
						countValue = 0;	
					}
					double averageValue = 0.0;
					try{
						averageValue = (Double)oAverageType.get(oN);
					}
					catch(NullPointerException ex){
						averageValue = 0.0;						
					}

					int lastCountValue = countValue;
					int newCountValue = countValue + 1;

					double dEval =  rr.getEval();

					averageValue = (((averageValue * lastCountValue) + dEval) / newCountValue);
					oAverageType.put(oN, averageValue);
					oCountType.put(oN, newCountValue);


					break;
				}
			}
		}

		for(Map.Entry<NewsType, Double> oKV: oAverageType.entrySet()){
			System.out.println(((NewsType)oKV.getKey()).ToString() + " = " + ((Integer)oCountType.get(((NewsType)oKV.getKey()))).toString());
			System.out.println((Double)oKV.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jcolibri.cbraplications.StandardCBRApplication#postCycle()
	 */
	public void postCycle() throws ExecutionException
	{
		_connector.close();

	}


	public static void main(String[] args) throws MalformedURLException, IOException
	{
		Test1 test = new Test1();
		try
		{
			//test.oNewsList = GetNews.GetCasesFromUrl(null);
			test.oNewsList = GetNews.GetCasesFromDB();

			test.configure();

			CBRCaseBase caseBase = test.preCycle();

			System.out.println("CASE BASE: ");
			for(CBRCase c: caseBase.getCases())
				System.out.println(c);
			System.out.println("Total: "+caseBase.getCases().size()+" cases");


			//String sUrl = javax.swing.JOptionPane.showInputDialog("Please enter the restaurant description:");
			//String sUrl = "http://www.marca.es";
				
			//String sUrl = "http://www.hola.com/";
			//String sUrl = "http://www.elpais.com";
			String sUrl = "http://www.barrapunto.com";
			//String sUrl = "http://www.waping.com.ar/";
			//String sUrl = "http://www.elmundo.es/elmundodeporte";

			String sText = GetText.ExtractText.GetBlogText(sUrl, false);

			CBRQuery query = new CBRQuery();
			APiecesOfNews queryDescription = new APiecesOfNews(sUrl, sText, null, 1.0);
			query.setDescription(queryDescription);

			test.cycle(query);


			test.postCycle();

		} catch (ExecutionException e)
		{
			org.apache.commons.logging.LogFactory.getLog(Test1.class).error(e);
		}
	}

}
