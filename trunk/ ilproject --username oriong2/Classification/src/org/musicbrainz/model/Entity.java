package org.musicbrainz.model;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.wsxml.element.RelationList;

/**
 * <p>A first-level MusicBrainz class.</p>
 * 
 * <p>All entities in MusicBrainz have unique IDs (which are absolute URIs)
 * and may have any number of {@link Relation}s to other entities.
 * This class is abstract and should not be instantiated.</p>
 * 
 * <p>Relations are differentiated by their target type, that means,
 * where they link to. MusicBrainz currently supports four target types
 * (artists, releases, tracks, and URLs) each identified using a URI.
 * To get all relations with a specific target type, you can use
 * {@link Entity#getRelations(String, String, List, String)} and pass
 * one of the following constants as the parameter:</p>
 * <ul>
 *   <li>{@link Relation#TO_ARTIST}</li>
 *   <li>{@link Relation#TO_RELEASE}</li>
 *   <li>{@link Relation#TO_TRACK}</li>
 *   <li>{@link Relation#TO_URL}</li>
 * </ul>
 * 
 * @see Relation
 * @author Patrick Ruhkopf
 */
public abstract class Entity {
	
	/**
	 * Default namespace prefix for all MusicBrainz metadata.
	 */
	public static final String NS_MMD_1 = "http://musicbrainz.org/ns/mmd-1.0#";
	
	/**
	 * Namespace prefix for relations.
	 */
	public static final String NS_REL_1 = "http://musicbrainz.org/ns/rel-1.0#";
	
	/**
	 * Namespace prefix for MusicBrainz extensions.
	 */
	public static final String NS_EXT_1 = "http://musicbrainz.org/ns/ext-1.0#";
	
	/**
	 * The ID of the special "Various Artists" artist.
	 */
	public static final String VARIOUS_ARTISTS_ID = "http://musicbrainz.org/artist/89ad4ac3-39f7-470e-963a-56509c546377";
	
	/**
	 * The entity's id
	 */
	private String id;
	
	/**
	 * The entity's relations
	 */
	private RelationList relationList = new RelationList();
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}	
	
	/**
	 * @return the relations
	 */
	public RelationList getRelationList() {
		return relationList;
	}

	/**
	 * @param relationList the relation list to set
	 */
	public void setRelationList(RelationList relationList) {
		this.relationList = relationList;
	}

	/**
	 * Returns a list of relations.
	 * 
	 * If <code>targetType</code> is given, only relations of that target
	 * type are returned. For MusicBrainz, the following target
	 * types are defined: 
	 * <ul>
	 *   <li>{@link Relation#TO_ARTIST}</li>
	 *   <li>{@link Relation#TO_RELEASE}</li>
	 *   <li>{@link Relation#TO_TRACK}</li>
	 *   <li>{@link Relation#TO_URL}</li>
	 * </ul>
	 * 
	 * If <code>targetType</code> is {@link Relation#TO_ARTIST}, for example,
	 * this method returns all relations between this Entity and artists.
	 * 
	 * You may use the <code>relationType</code> parameter to further restrict
	 * the selection. If it is set, only relations with the given relation
	 * type are returned. The <code>requiredAttributes</code> sequence
	 * lists attributes that have to be part of all returned relations.
	 * 
	 * If <code>direction</code> is set, only relations with the given reading
	 * direction are returned. You can use the {@link Relation#DIR_FORWARD},
	 * {@link Relation#DIR_BACKWARD}, and {@link Relation#DIR_BOTH} constants
	 * for this.
	 * 
	 * @param targetType The target relation type (can be null)
	 * @param relationType The relation type (can be null)
	 * @param requiredAttributes Attributes that are required for every relation (can be null)
	 * @param direction The direction of the relation (can be null)
	 * @return A list of relations
	 */
	public List<Relation> getRelations(String targetType, String relationType, List<String> requiredAttributes, String direction)
	{
		List<Relation> allRelations = new ArrayList<Relation>();
		if (relationList == null)
			return allRelations;
		
		for (Relation relation : relationList.getRelations())
		{
			// filter target type
			if (targetType != null) {
				if (!targetType.equals(relation.getTargetType())) {
					continue;
				}
			}

			// filter directions
			if (direction != null) {
				if (!direction.equals(relation.getDirection())) {
					continue;
				}
			}
			
			// filter relation type
			if (relationType != null) {
				if (!relationType.equals(relation.getType())) {
					continue;
				}
			}
			
			// filter attributes
			if (requiredAttributes != null) {
				for (String attr : requiredAttributes) {
					if (!relation.getAttributes().contains(attr)) {
						continue;
					}
				}
			}
			
			// if no filter applied, add to result list
			allRelations.add(relation);
		}
		
		return allRelations;
	}

	/**
	 * Subclasses should return its type here
	 * 
	 * @return The type (without URI)
	 */
	//public abstract String getEntityType();
}
