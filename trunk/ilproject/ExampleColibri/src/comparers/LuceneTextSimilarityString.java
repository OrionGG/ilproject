package comparers;

import org.apache.lucene.analysis.Analyzer;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.datatypes.Text;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.extensions.textual.lucene.LuceneSearchResult;
import jcolibri.extensions.textual.lucene.LuceneSearcher;
import jcolibri.method.retrieve.LuceneRetrieval.LuceneRetrieval;
import jcolibri.method.retrieve.NNretrieval.similarity.InContextLocalSimilarityFunction;
import jcolibrilucene302.LuceneIndex30;
import jcolibrilucene302.LuceneSearchResult30;
import jcolibrilucene302.LuceneSearcher30;

public class LuceneTextSimilarityString extends InContextLocalSimilarityFunction
{
	LuceneSearchResult30 lsr = null;
	boolean normalized = false;

	/**
	 * Creates a LuceneTextSimilarity object. This constructor pre-computes the similarity of the query with
	 * the textaul attributes of the case (as these attributes are in the index). 
	 * @param index Index that contains the attributes of the case
	 * @param query query that will be compared
	 * @param at textual attribute of the case or query object that is being compared
	 * @param normalized if the Lucene result must be normalized to [0..1]
	 */
	public LuceneTextSimilarityString(LuceneIndex30 index, CBRQuery query, Attribute at, boolean normalized,int n, Analyzer oAnalizer)
	{
		this.normalized = normalized;
		Object queryString = jcolibri.util.AttributeUtils.findValue(at, query);
		if(!(queryString instanceof String))
		{
			org.apache.commons.logging.LogFactory.getLog(LuceneRetrieval.class).error("Search field has not a Text value. Returning empty RetrievalResult list.");
			return;
		}
		String qs = (String) queryString;
		String sf = at.getName();
		lsr = LuceneSearcher30.search(index, qs.toString(), sf, n, oAnalizer);

	}

	/* (non-Javadoc)
	 * @see jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction#compute(java.lang.Object, java.lang.Object)
	 */
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException
	{
		if ((caseObject == null) || (queryObject == null))
			return 0;
		if (!(caseObject instanceof String))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
		if (!(queryObject instanceof String))
			throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());

		return lsr.getDocScore(_case.getID().toString(), normalized);
	}

	/* (non-Javadoc)
	 * @see jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction#isApplicable(java.lang.Object, java.lang.Object)
	 */
	public boolean isApplicable(Object o1, Object o2)
	{
		if((o1==null)&&(o2==null))
			return true;
		else if(o1==null)
			return o2 instanceof String;
		else if(o2==null)
			return o1 instanceof String;
		else
			return (o1 instanceof String)&&(o2 instanceof String);
	}

}
