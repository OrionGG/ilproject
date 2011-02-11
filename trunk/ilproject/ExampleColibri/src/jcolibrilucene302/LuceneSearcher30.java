package jcolibrilucene302;

import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.extensions.textual.lucene.LuceneSearchResult;
import jcolibri.extensions.textual.lucene.LuceneSearcher;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

public class LuceneSearcher30 {
	/**
	 * Performs a search using Lucene
	 * @param index with the documents to search
	 * @param query to search
	 * @param fieldName field where search inside the documents
	 * @return the search result
	 */
	public static LuceneSearchResult30 search(LuceneIndex30 index, String query, String fieldName,int n, Analyzer oAnalizer)
	{	    
		try {

		    Searcher searcher = new IndexSearcher(index.getDirectory());
		    Analyzer analyzer = oAnalizer;
		    QueryParser parser = new QueryParser(Version.LUCENE_30, fieldName, analyzer);
			   // Term term=new Term("baile");
		    BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
		    parser.setAllowLeadingWildcard(false);
		    Query q = parser.parse(query);
		    TopDocs hits  = searcher.search(q, n);
		    LuceneSearchResult30 lsr = new LuceneSearchResult30(hits, index, searcher);
		    searcher.close();
		    return lsr;
			
		} catch (Exception e) {
			org.apache.commons.logging.LogFactory.getLog(LuceneSearcher.class).error(e);
		}
	    return null;
	}
}
