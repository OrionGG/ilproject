package org.musicbrainz.wsxml.element;

import org.musicbrainz.model.Track;

/**
 * Represents an track result.
 * 
 * @see Track
 * @author Patrick Ruhkopf
 */
public class TrackResult extends SearchResult {
	
	/**
	 * The track
	 */
	private Track track;

	/**
	 * @return the track
	 */
	public Track getTrack() {
		return track;
	}

	/**
	 * @param track the track to set
	 */
	public void setTrack(Track track) {
		this.track = track;
	}
	
}
