package Classificator;

import java.util.Hashtable;

import org.apache.lucene.search.IndexSearcher;

import dominio.Category;

public class IndexCategScore {
	public IndexSearcher oIndex;
	public Hashtable<String, Float> hCategScore;
	
	public IndexCategScore(IndexSearcher oIndex) {
		this.oIndex = oIndex;
		this.hCategScore = new Hashtable<String, Float>();
	}
	
	

}