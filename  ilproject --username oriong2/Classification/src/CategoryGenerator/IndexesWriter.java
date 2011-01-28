package CategoryGenerator;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.NoLockFactory;

import Analizer.SpanishAnalyzer;

public class IndexesWriter {
	public enum IndexType{
		DBPediaIndex,
		WikipediaIndex,
		ListWebsIndex
	}
	static Hashtable<String, IndexWriter> IndexHash = new Hashtable<String, IndexWriter>();
	
	public static void CreateIndexes(IndexType[] lIndesList, SpanishAnalyzer analyzer ) throws CorruptIndexException, LockObtainFailedException, IOException{
		
		for(IndexType oIndexType:lIndesList){
			IndexWriter iIndexWriter = CreateIndex(analyzer, oIndexType.toString());
			IndexHash.put( oIndexType.toString(), iIndexWriter);
		}
	}

	private static IndexWriter CreateIndex(SpanishAnalyzer analyzer, String sName)
	throws IOException, CorruptIndexException,
	LockObtainFailedException {
		File fDirectory=new File(".\\resources\\" + sName);
		if(fDirectory.exists()){
			fDirectory.delete();
		}
		Directory dDBPediaIndexDirectory = FSDirectory.open(fDirectory,new NoLockFactory());
		IndexWriter iWriter = new IndexWriter(dDBPediaIndexDirectory, analyzer, true, new IndexWriter.MaxFieldLength(25000));
		return iWriter;
	}
	
	public static IndexWriter getIndex(IndexType oIndexType){
		IndexWriter oResult = IndexHash.get(oIndexType);
		return oResult;
		
	}

	public static void optimize() throws CorruptIndexException, IOException {
		for(Entry<String, IndexWriter> oEntry:IndexHash.entrySet()){
			IndexWriter oIndexWriter = oEntry.getValue();
			oIndexWriter.optimize();		
		}
		
	}

	public static void close() throws CorruptIndexException, IOException {
		for(Entry<String, IndexWriter> oEntry:IndexHash.entrySet()){
			IndexWriter oIndexWriter = oEntry.getValue();
			oIndexWriter.close();		
		}
		
	}

}
