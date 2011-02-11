/**
 * Houses6.java
 * jCOLIBRI2 framework. 
 * @author Juan A. Recio-García.
 * GAIA - Group for Artificial Intelligence Applications
 * http://gaia.fdi.ucm.es
 * 23/10/2007
 */
package recomender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.*;

import connector.TheNewsConnector;
import dominio.Category;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.UserChoice;
import jcolibri.extensions.recommendation.conditionals.BuyOrQuit;
import jcolibri.extensions.recommendation.conditionals.ContinueOrFinish;
import jcolibri.extensions.recommendation.navigationByProposing.CriticalUserChoice;
import jcolibri.extensions.recommendation.navigationByProposing.CritiqueOption;
import jcolibri.extensions.recommendation.navigationByProposing.DisplayCasesTableWithCritiquesMethod;
import jcolibri.extensions.recommendation.navigationByProposing.queryElicitation.MoreLikeThis;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.FilterBasedRetrieval.FilterBasedRetrievalMethod;
import jcolibri.method.retrieve.FilterBasedRetrieval.FilterConfig;
import jcolibri.method.retrieve.FilterBasedRetrieval.predicates.NotEqual;
import jcolibri.method.retrieve.FilterBasedRetrieval.predicates.QueryLess;
import jcolibri.method.retrieve.FilterBasedRetrieval.predicates.QueryMore;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Table;
import jcolibri.method.retrieve.NNretrieval.similarity.local.recommenders.InrecaLessIsBetter;
import jcolibri.method.retrieve.NNretrieval.similarity.local.recommenders.InrecaMoreIsBetter;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.main.SwingProgressBar;
import jcolibri.test.recommenders.housesData.HouseDescription;

import entities.DB.*;
/**
 * Conversational (type A) flats recommender using Navigation by Proposing and Filtered+NearestNeighbour+topKselection retrieval.
 * <br>
 * This recommender implements the Navigation by Proposing strategy. 
 * This strategy selects and displays some items to the user and the he/she makes
 * a critique over one of the displayed items (i.e.: "like this but cheaper").
 * To obtain the user preferences this recommender uses a form. Then, the filtering method
 * is executed using as filters the critiques over the previously presented items. Finally, the 
 * Nearest Neighbour method is applied to the filtered set to obtain the items displayed
 * to the user. 
 * <br>Summary:
 * <ul>
 * <li>Type: Conversational A
 * <li>Case base: houses
 * <li>One off Preference Elicitation: Form filling (without initial values)
 * <li>Retrieval: Filtering + NN + topKselection
 * <li>Display: In table with critiques
 * <li>Iterated Preference Elecitiation: Navigation by Proposing: More Like This.
 * </ul>
 * This recommender implements the following template:<br>
 * <center><img src="../Template6_Cycle.jpg"/></center>
 * 
 * <br>Read the documentation of the recommenders extension for details about templates
 * and recommender strategies: {@link jcolibri.extensions.recommendation}
 * 
 * @see jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod
 * @see jcolibri.method.retrieve.FilterBasedRetrieval.FilterBasedRetrievalMethod
 * @see jcolibri.method.retrieve.NNretrieval.NNScoringMethod
 * @see jcolibri.method.retrieve.selection.SelectCases
 * @see jcolibri.extensions.recommendation.casesDisplay.DisplayCasesTableMethod
 * @see jcolibri.extensions.recommendation.navigationByProposing.queryElicitation.MoreLikeThis
 * 
 * @author Juan A. Recio-Garcia
 * @author Developed at University College Cork (Ireland) in collaboration with Derek Bridge.
 * @version 1.0
 */
public class Recomender implements StandardCBRApplication
{

	protected List<NewsDescription> oNewsList;
	/** Connector object */
	Connector _connector;
	/** CaseBase object */
	CBRCaseBase _caseBase;

	/** KNN configuration*/
	NNConfig simConfig;

	/** Obtain query configuration*/
	Collection<Attribute> hiddenAtts;
	/** Obtain query configuration*/
	Map<Attribute,String> labels;

	/** Critiques configuration object */
	Collection<CritiqueOption> critiques;

	public void configure() throws ExecutionException
	{
		
		//Get cases from Db
		oNewsList = NewsDescriptionDao.getInstance().getAllNews();
		//Use a custom connector
		_connector = new TheNewsConnector(oNewsList);
		_caseBase = new LinealCaseBase();

		//To show the progress
	//	jcolibri.util.ProgressController.clear();
		//SwingProgressBar pb = new SwingProgressBar();
		//jcolibri.util.ProgressController.register(pb);


		//Lets configure the KNN
		simConfig = new NNConfig();
		// Set the average() global similarity function for the description of the case
		simConfig.setDescriptionSimFunction(new Average());
		for(Category oCategory : Category.values()){
			simConfig.addMapping(new Attribute("cat" + oCategory.ordinal(), NewsDescription.class), new Equal());
		}

		// Obtain query configuration
		hiddenAtts = new ArrayList<Attribute>();
		for(Category oCategory : Category.values()){
			
			labels = new HashMap<Attribute,String>();
			labels.put(new Attribute("cat" + oCategory.ordinal(), NewsDescription.class), "Min "+"Cat" + oCategory.toString());
			//labels.put(new Attribute(oCategory.toString(), HouseDescription.class), "Min bedrooms");
		//	labels.put(new Attribute("price", HouseDescription.class), "Max price");
			//labels.put(new Attribute("baths", HouseDescription.class), "Min bahtrooms");	
		}


		// Critiques configuration
		critiques = new ArrayList<CritiqueOption>();
		for(Category oCategory : Category.values()){
			critiques.add(new CritiqueOption("cat" + oCategory.ordinal(),new Attribute("cat" + oCategory.ordinal(), NewsDescription.class),new QueryMore()));
		}
	}
	
	public CBRCaseBase preCycle() throws ExecutionException
	{
		// Load cases from connector into the case base
		
		_caseBase.init(_connector);		
		// Print the cases
		Collection<CBRCase> cases = _caseBase.getCases();
		/*for(CBRCase c: cases){
			System.out.println(c);
		}*/
		return _caseBase;
	}

	public void cycle(CBRQuery query) throws ExecutionException
	{	
		// Obtain query with form filling
		ObtainQueryWithFormMethod.obtainQueryWithoutInitialValues(query,hiddenAtts,labels);

		// Jump to main conversation
		sequence1(query, new FilterConfig());

	}


	

	public void postCycle() throws ExecutionException
	{
	}


	public static void main(String[] args) {
		StandardCBRApplication recommender = new Recomender();
		try
		{
			
			recommender.configure();

			recommender.preCycle();

			CBRQuery query = new CBRQuery();

			NewsDescription hd = new NewsDescription();

			query.setDescription(hd);

			recommender.cycle(query);

			recommender.postCycle();

			//System.exit(0);
		} catch (Exception e)
		{
			org.apache.commons.logging.LogFactory.getLog(Recomender.class).error(e);
			System.out.print(e.toString());
		}


	}
	public void sequence1(CBRQuery query, FilterConfig filterConfig)  throws ExecutionException
	{	
		// Execute Filter
		Collection<CBRCase> filtered = FilterBasedRetrievalMethod.filterCases(_caseBase.getCases(), query, filterConfig);

		// Execute NN
		Collection<RetrievalResult> retrievedCases = NNScoringMethod.evaluateSimilarity(filtered, query, simConfig);

		// Select cases
		Collection<CBRCase> selectedCases = SelectCases.selectTopK(retrievedCases, 10);

		// Obtain critizied query
		CriticalUserChoice choice = DisplayCasesTableWithCritiquesMethod.displayCasesInTableWithCritiques(selectedCases, critiques, _caseBase.getCases());

		if(ContinueOrFinish.continueOrFinish(choice))
			sequence2(choice.getSelectedCaseAsQuery(), choice);
		else
			sequence3(choice, selectedCases);
	}

	public void sequence2(CBRQuery query, CriticalUserChoice cuc) throws ExecutionException
	{
		// Replaze current query with the critizied one
		MoreLikeThis.moreLikeThis(query, cuc.getSelectedCase());
		sequence1(query, cuc.getFilterConfig());
	}

	public void sequence3(UserChoice choice, Collection<CBRCase> retrievedCases)  throws ExecutionException
	{
		if(BuyOrQuit.buyOrQuit(choice))
			System.out.println("Finish - User Buys: "+choice.getSelectedCase());

		else
			System.out.println("Finish - User Quits");
	}

}
