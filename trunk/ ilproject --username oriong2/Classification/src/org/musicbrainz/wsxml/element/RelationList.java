package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Relation;

/**
 *  A list of {@link Relation}s.
 *  
 * @author Patrick Ruhkopf
 */
public class RelationList extends ListElement {

	private List<Relation> relations;

	
	/**
	 * @return the relations
	 */
	public List<Relation> getRelations() {
		return relations;
	}

	/**
	 * @param relations the relations to set
	 */
	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public void addRelation(Relation relation) 
	{
		if (relations == null)
			relations = new ArrayList<Relation>();
	
		relations.add(relation);
		
	}
	
	
}
