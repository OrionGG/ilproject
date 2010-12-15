package org.musicbrainz.model;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a MusicBrainz user.
 * 
 * @author Patrick Ruhkopf
 */
public class User 
{
	/**
	 * The MusicBrainz user name.
	 */
	private String name;
	
	/**
	 * The user's types as a string representation
	 */
	private String typeString;
	
	/**
	 * The value of the nag screen flag.
	 */
	private boolean showNag;
	

	/**
	 * Most users' type list is empty. Currently, the following types
	 * are defined:
	 * 
	 * <ul>
	 *   <li>http://musicbrainz.org/ns/ext-1.0#AutoEditor</li>
	 *   <li>http://musicbrainz.org/ns/ext-1.0#RelationshipEditor</li>
	 *   <li>http://musicbrainz.org/ns/ext-1.0#Bot</li>
	 *   <li>http://musicbrainz.org/ns/ext-1.0#NotNaggable</li>
	 * </ul>
	 * @return An array of types
	 */
	public String[] getTypes() {
		return (typeString == null ? null : typeString.split(" "));
	}
	
	public void setTypes(String[] types) {
		setTypeString(StringUtils.join(types, " "));
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the showNag
	 */
	public boolean isShowNag() {
		return showNag;
	}

	/**
	 * @param showNag the showNag to set
	 */
	public void setShowNag(boolean showNag) {
		this.showNag = showNag;
	}

	/**
	 * @return the typeString
	 */
	public String getTypeString() {
		return typeString;
	}

	/**
	 * @param typeString the typeString to set
	 */
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User userName=" + name;
	} 

	

}
