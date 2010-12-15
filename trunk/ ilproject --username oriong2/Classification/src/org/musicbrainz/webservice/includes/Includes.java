package org.musicbrainz.webservice.includes;

import java.util.List;

/**
 * An interface implemented by include tag generators.
 * 
 * @author Patrick Ruhkopf
 */
public interface Includes {
	
	/**
	 * Create a list of includes.
	 * 
	 * This method creates a list of includes that will be used
	 * with the <code>inc</code> parameter.
	 * 
	 * @return A List containing includes.
	 */
	public List<String> createIncludeTags();
}
