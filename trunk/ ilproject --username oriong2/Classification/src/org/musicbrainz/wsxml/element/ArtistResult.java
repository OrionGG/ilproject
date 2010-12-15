package org.musicbrainz.wsxml.element;

import org.musicbrainz.model.Artist;

/**
 * Represents an artist result.
 * 
 * @see Artist
 * @author Patrick Ruhkopf
 */
public class ArtistResult extends SearchResult {
	
	/**
	 * The artist
	 */
	private Artist artist;

	/**
	 * @return the artist
	 */
	public Artist getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
}
