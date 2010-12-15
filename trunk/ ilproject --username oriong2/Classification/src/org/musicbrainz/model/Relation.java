package org.musicbrainz.model;

import java.util.List;

import org.musicbrainz.utils.MbUtils;

/**
 * <p>Represents a relation between two Entities.</p>
 * 
 * <p>There may be an arbitrary number of relations between all first
 * class objects in MusicBrainz. The Relation itself has multiple
 * attributes, which may or may not be used for a given relation
 * type.</p>
 * 
 * <p>Note that a {@link Relation} object only contains the target but not
 * the source end of the relation.</p>
 *
 * @author Patrick Ruhkopf
 */
public class Relation {
	
	/**
	 * Relation types
	 */
	public static final String TO_ARTIST = Entity.NS_REL_1 + "Artist";
	public static final String TO_RELEASE = Entity.NS_REL_1 + "Release";
	public static final String TO_TRACK = Entity.NS_REL_1 + "Track";
	public static final String TO_URL = Entity.NS_REL_1 + "Url";

	/**
	 * Relation reading directions
	 */
	public static final String DIR_BOTH = "both";
	public static final String DIR_FORWARD = "forward";
	public static final String DIR_BACKWARD = "backward";

	/**
	 * The relation's tpye
	 */
	public String type; 
	
	/**
	 * The target's id
	 */
	public String targetId;
	
	/**
	 * The target's type
	 */
	public String targetType;
	
	/**
	 * One of {@link Relation#DIR_FORWARD}, {@link Relation#DIR_BACKWARD} or {@link Relation#DIR_BOTH} 
	 */
	public String direction = DIR_BOTH;
	
	/**
	 * A list of attributes (URIs)
	 */
	public List<String> attributes;

	/**
	 * The begin date
	 */
	public String beginDate;

	/**
	 * The end date
	 */
	public String endDate;
	
	/**
	 * The target entity
	 */
	private Entity target;

	public Relation() {
		
	}

	/**
	 * @return the attributes
	 */
	public List<String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the relationType
	 */
	public String getType() {
		return type;
	}

	/**
	 * Note: This setter will prepend a (default) URI unless
	 * the parameter <code>type</code> is not already an URI.
	 * 
	 * @param type the relationType to set
	 */
	public void setType(String type) {
		this.type = MbUtils.convertTypeToURI(type, Entity.NS_REL_1) ;
	}

	/**
	 * @return the target
	 */
	public Entity getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Entity target) {
		this.target = target;
	}

	/**
	 * Returns the targetId
	 * 
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * @param targetType the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}


	
}
