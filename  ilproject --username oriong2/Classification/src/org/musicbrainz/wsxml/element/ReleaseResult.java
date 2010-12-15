package org.musicbrainz.wsxml.element;

import org.musicbrainz.model.Release;

/**
 * Represents an release result.
 * 
 * @see Release
 * @author Patrick Ruhkopf
 */
public class ReleaseResult extends SearchResult {
	
	/**
	 * The release
	 */
	private Release release;

	/**
	 * @return the release
	 */
	public Release getRelease() {
		return release;
	}

	/**
	 * @param release the track to set
	 */
	public void setRelease(Release release) {
		this.release = release;
	}
	
}
