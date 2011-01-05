package jcolibrilucene302;

import java.io.File;
import java.io.IOException;
import java.util.*;

import jcolibri.extensions.textual.lucene.LuceneDocument;
import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.util.ProgressController;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import Analizer.SpanishAnalyzer;

public class LuceneIndex30{

	private Directory directory;
	private java.util.HashMap<String, LuceneDocument> docsMapping;

	/**
	 * Creates a LuceneIndex stored in the File System.
	 * @param directory to store the index once generated
	 * @param documents to index
	 */
	public LuceneIndex30(File directory, Collection<LuceneDocument30> documents, Analyzer oAnalyze)
	{
		this.docsMapping = new java.util.HashMap<String, LuceneDocument>();
	    org.apache.commons.logging.LogFactory.getLog(LuceneIndex.class).info("Creating File System Index in: "+directory.getPath());
		
		try {
			this.directory = FSDirectory.open(directory);
		} catch (IOException e) {
			org.apache.commons.logging.LogFactory.getLog(LuceneIndex.class).error(e);
		}

		createIndex(documents, oAnalyze);

	}
	
	/**
	 * Creates an index stored into memory.
	 * @param documents to index.
	 */
	public LuceneIndex30(Collection<LuceneDocument30> documents, Analyzer oAnalyze) {
		this.docsMapping = new java.util.HashMap<String, LuceneDocument>();
		
	    this.directory = new RAMDirectory();
	    createIndex(documents, oAnalyze);
	}

	private void createIndex(Collection<LuceneDocument30> documents, Analyzer oAnalyzer)
	{
		try {
			
			IndexWriter writer = new IndexWriter(getDirectory(),  oAnalyzer, true, MaxFieldLength.UNLIMITED);
		    
			ProgressController.init(this.getClass(),"Lucene. Indexing documents", documents.size());
			
			for(LuceneDocument30 doc: documents)
			{
				writer.addDocument(doc.getInternalDocument());
				ProgressController.step(this.getClass());
			}		    
			
			writer.optimize();
		    writer.close();
		    ProgressController.finish(this.getClass());
		} catch (Exception e) {
			org.apache.commons.logging.LogFactory.getLog(LuceneIndex.class).error(e);		
		}
	}
	
	/**
	 * @return the directory
	 */
	public Directory getDirectory() {
		return directory;
	}
	
	
	
		
	public int getNumberOfDocuments()
	{
		return docsMapping.size();
	}
	
	public LuceneDocument getDocument(String docId)
	{
		return docsMapping.get(docId);
	}
}
