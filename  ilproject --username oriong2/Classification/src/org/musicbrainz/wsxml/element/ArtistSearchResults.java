
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;


/**
 * A list of {@link ArtistResult}s.
 * 
 * @see ArtistResult
 * @author Patrick Ruhkopf
 */
public class ArtistSearchResults extends ListElement{

    protected List<ArtistResult> artistResults = new ArrayList<ArtistResult>();

	/**
	 * @return the artist results
	 */
	public List<ArtistResult> getArtistResults() {
		return artistResults;
	}

	/**
	 * @param artistResults the artists results to set
	 */
	public void setArtistResults(List<ArtistResult> artistResults) {
		this.artistResults = artistResults;
	}
	
	/**
	 * Convenience method to adds an {@link ArtistResult} to the list.
	 * 
	 * This will create a new <code>ArrayList</code> if {@link #artistResults} is null.
	 * 
	 * @param artistResult The artist result to add
	 */
	public void addArtistResult(ArtistResult artistResult) 
	{
		if (artistResults == null) {
			artistResults = new ArrayList<ArtistResult>();
		}
		artistResults.add(artistResult);
	}
	
}
