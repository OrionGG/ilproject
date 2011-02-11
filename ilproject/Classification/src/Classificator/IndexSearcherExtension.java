package Classificator;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

public class IndexSearcherExtension extends IndexSearcher {
	public int iIndex = 0;
	public Directory oDirectory;
	public TopDocs oTopDocs;
	public IndexSearcherExtension(int index, Directory oDirectory, Boolean readOnly, TopDocs oTopDocs) throws CorruptIndexException, IOException {
		super(oDirectory, readOnly);
		this.iIndex = index;
		this.oDirectory = oDirectory;
		this.oTopDocs = oTopDocs;
	}
	
	public void close() {

		try {
			this.oDirectory.close();
			super.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
