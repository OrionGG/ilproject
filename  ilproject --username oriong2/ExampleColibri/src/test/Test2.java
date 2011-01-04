package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.textual.LuceneTextSimilarity;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.main.SwingProgressBar;
import retriever.GetNews;

import comparers.AverageCoefficient;
import comparers.LuceneIndexCreatorString;

import connector.TheNewsConnector;
import entities.APiecesOfNews;
import entities.NewsAnalyzer;

public class Test2 {
	List<APiecesOfNews> oNewsList;
	Connector _connector;
	CBRCaseBase _caseBase;


    LuceneIndex luceneIndex;

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


		luceneIndex = LuceneIndexCreatorString.createLuceneIndex(_caseBase);

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
		nnConfig.setDescriptionSimFunction(new Average());
		//We only compare the "description" attribute using Lucene

		Attribute at1 = new Attribute("ssimpletext", APiecesOfNews.class);
		nnConfig.addMapping(at1, new comparers.LuceneTextSimilarityString(luceneIndex,query,at1, true));

		
		System.out.println("RESULT:");
		Collection<RetrievalResult> res = NNScoringMethod.evaluateSimilarity(cases, query, nnConfig);
		res = SelectCases.selectTopKRR(res, 5);

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
		Test2 test = new Test2();
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
			String sUrl = "http://www.marca.es";

			String sText = GetBlogText.ExtractText.GetBlogText(sUrl, false);
			
			CBRQuery query = new CBRQuery();
			APiecesOfNews queryDescription = new APiecesOfNews(sUrl, sText, null);
			query.setDescription(queryDescription);

			test.cycle(query);


			test.postCycle();

		} catch (ExecutionException e)
		{
			org.apache.commons.logging.LogFactory.getLog(Test1.class).error(e);
		}
	}
}
