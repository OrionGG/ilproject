package comparers;

import java.util.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field.Index;

import Analizer.SpanishAnalyzer;

import entities.APiecesOfNews;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.datatypes.Text;
import jcolibri.exception.AttributeAccessException;
import jcolibri.extensions.textual.lucene.LuceneDocument;
import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.method.precycle.LuceneIndexCreator;
import jcolibrilucene302.*;

public class LuceneIndexCreatorString {
	/**
	 * Creates a Lucene Index with the text contained in some attributes. The type of that attributes must be "Text".
	 * This method creates a LuceneDocument for each case, and adds a new field for each attribute (recived as parameter). 
	 * The name and content of the Lucene document field is the name and content of the attribute.
	 */
	public static LuceneIndex30 createLuceneIndex(CBRCaseBase casebase, Map<Attribute, Index> attributes, Attribute dWeightAttribute, Analyzer oAnalyzer)
	{
		for(Map.Entry<Attribute,Index> fieldAnalyzer: attributes.entrySet())
		{	
			Attribute field = fieldAnalyzer.getKey();

			Class c = field.getType();
			if(!String.class.isAssignableFrom(c))
			{
				org.apache.commons.logging.LogFactory.getLog(LuceneIndexCreator.class).error("Field "+field+" is not an jcolibri.datatyps.Text. Aborting Lucene index creation");
				return null;
			}
		}

		ArrayList<LuceneDocument30> docs = new ArrayList<LuceneDocument30>();
		for(CBRCase c: casebase.getCases())
		{
			double dWeight = 1.0;
			try {
				dWeight = (Double) dWeightAttribute.getValue(c.getDescription());
			} catch (AttributeAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LuceneDocument30 ld = new LuceneDocument30((String)c.getID(), dWeight);
			for(Map.Entry<Attribute,Index> fieldAnalyzer: attributes.entrySet())
			{	
				Attribute field = fieldAnalyzer.getKey();
				Index indexAnalyzer = fieldAnalyzer.getValue();
				Text oText =  new Text();
				try {
					oText.fromString((String)jcolibri.util.AttributeUtils.findValue(field, c));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					ld.addContentField(field.getName(), oText, indexAnalyzer);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			docs.add(ld);
		}
		return new LuceneIndex30(docs, oAnalyzer);

	}
}
