package jcolibrilucene302;

import jcolibri.datatypes.Text;
import jcolibri.extensions.textual.lucene.LuceneDocument;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;

public class LuceneDocument30 {
	Document doc;
	public static String ID_FIELD = "ID";
	public static String WEIGHT_FIELD = "WEIGHT";

/*	public LuceneDocument30(String docID)
	{
		doc = new Document();
		setDocID(docID);
	}*/

	public LuceneDocument30(String docID, Double dWeight)
	{
		doc = new Document();
		setDocID(docID);
		setDocWeight( dWeight);
	}

	protected Document getInternalDocument()
	{
		return doc;
	}


	public void setDocID(String id)
	{
		doc.add(new Field(ID_FIELD, id, Field.Store.YES, Field.Index.NO));    	
	}
	public String getDocID()
	{
		return doc.get(ID_FIELD);
	}

	public void setDocWeight(Double dWeight)
	{
		doc.add(new Field(WEIGHT_FIELD, dWeight.toString(), Field.Store.YES, Field.Index.NO));    	
	}
	public String getDocWeight()
	{
		return doc.get(WEIGHT_FIELD);
	}


	public String getContentField(String fieldname)
	{
		return doc.get(fieldname);
	}
	public void addContentField(String fieldname, Text content, Index indexAnalyzer)
	{
		Document doc = getInternalDocument();
		switch (indexAnalyzer) {
		case ANALYZED:
			doc.add(new Field(fieldname, content.toString(), Field.Store.YES, Index.ANALYZED,Field.TermVector.YES));
			break;
		case NOT_ANALYZED:
			doc.add(new Field(fieldname, content.toString(), Field.Store.YES, Index.NOT_ANALYZED));
			break;
		case NO:
			doc.add(new Field(fieldname, content.toString(), Field.Store.YES, Index.NO));
			break;
		default:
			break;
		}
	}
}
