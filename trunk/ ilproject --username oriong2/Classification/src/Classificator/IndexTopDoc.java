package Classificator;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

public class IndexTopDoc {
	public IndexSearcher oIndexSearcher;
	public TopDocs oTopDocs;
	public IndexTopDoc(IndexSearcher oIndexSearcher, TopDocs oTopDocs) {
		super();
		this.oIndexSearcher = oIndexSearcher;
		this.oTopDocs = oTopDocs;
	}

}
