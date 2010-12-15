
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Artist;


/**
 * A list of {@link Artist}s.
 * 
 * @see Artist
 * @author Patrick Ruhkopf
 */
public class ArtistList extends ListElement{

    protected List<Artist> artists = new ArrayList<Artist>();

	/**
	 * @return the artists
	 */
	public List<Artist> getArtist() {
		return artists;
	}

	/**
	 * @param artists the artists to set
	 */
	public void setArtist(List<Artist> artists) {
		this.artists = artists;
	}
	
	/**
	 * Convenience method to adds an artist to the list.
	 * 
	 * This will create a new <code>ArrayList</code> if {@link #artists} is null.
	 * 
	 * @param artist
	 */
	public void addArtist(Artist artist) 
	{
		if (artists == null) {
			artists = new ArrayList<Artist>();
		}
		artists.add(artist);
	}
	
}
