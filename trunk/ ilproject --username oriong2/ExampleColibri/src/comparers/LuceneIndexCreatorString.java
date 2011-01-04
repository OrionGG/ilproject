package comparers;

import java.util.ArrayList;
import java.util.Collection;

import entities.APiecesOfNews;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.datatypes.Text;
import jcolibri.extensions.textual.lucene.LuceneDocument;
import jcolibri.extensions.textual.lucene.LuceneIndex;
import jcolibri.method.precycle.LuceneIndexCreator;

public class LuceneIndexCreatorString {
	/**
	 * Creates a Lucene Index with the text contained in some attributes. The type of that attributes must be "Text".
	 * This method creates a LuceneDocument for each case, and adds a new field for each attribute (recived as parameter). 
	 * The name and content of the Lucene document field is the name and content of the attribute.
	 */
	public static LuceneIndex createLuceneIndex(CBRCaseBase casebase, Collection<Attribute> fields)
	{
		for(Attribute field: fields)
		{	
			Class c = field.getType();
			if(!String.class.isAssignableFrom(c))
			{
				org.apache.commons.logging.LogFactory.getLog(LuceneIndexCreator.class).error("Field "+field+" is not an jcolibri.datatyps.Text. Aborting Lucene index creation");
				return null;
			}
		}
		
		ArrayList<LuceneDocument> docs = new ArrayList<LuceneDocument>();
		for(CBRCase c: casebase.getCases())
		{
			LuceneDocument ld = new LuceneDocument((String)c.getID());
			for(Attribute field: fields){
				Text oText =  new Text();
				try {
					oText.fromString((String)jcolibri.util.AttributeUtils.findValue(field, c));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ld.addContentField(field.getName(), oText);
			}
			docs.add(ld);
		}
		return new LuceneIndex(docs);

	}
	

	/**
	 * Creates a Lucene Index with the text contained in some attributes. The type of that attributes must be "Text".
	 * This method creates a LuceneDocument for each case, and adds a new field for each attribute (recived as parameter). 
	 * The name and content of the Lucene document field is the name and content of the attribute.
	 */
	public static LuceneIndex createLuceneIndex(CBRCaseBase casebase)
	{
	    	CBRCase _case = casebase.getCases().iterator().next();
	    	Collection<Attribute> attributes = new ArrayList<Attribute>();
	    	if(_case.getDescription() != null)
	    		attributes.add(new Attribute("ssimpletext", APiecesOfNews.class));
	    	    //attributes.addAll(jcolibri.util.AttributeUtils.getAttributes(_case.getDescription(), String.class));
	    	if(_case.getSolution() != null)
	    	    attributes.addAll(jcolibri.util.AttributeUtils.getAttributes(_case.getSolution(), String.class));
	    	if(_case.getResult() != null)
	    	    attributes.addAll(jcolibri.util.AttributeUtils.getAttributes(_case.getResult(), String.class));
	    	if(_case.getJustificationOfSolution() != null)
	    	    attributes.addAll(jcolibri.util.AttributeUtils.getAttributes(_case.getJustificationOfSolution(), String.class));
	    	

		return createLuceneIndex(casebase, attributes);

	}	
}
