
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;


/**
 * A list of {@link TrackResult}s.
 * 
 * @see TrackResult
 * @author Patrick Ruhkopf
 */
public class TrackSearchResults extends ListElement{

    protected List<TrackResult> trackResults = new ArrayList<TrackResult>();

	/**
	 * @return the track results
	 */
	public List<TrackResult> getTrackResults() {
		return trackResults;
	}

	/**
	 * @param trackResults the track results to set
	 */
	public void setTrackResults(List<TrackResult> trackResults) {
		this.trackResults = trackResults;
	}
	
	/**
	 * Convenience method to adds an track result to the list.
	 * 
	 * This will create a new <code>ArrayList</code> if {@link #trackResults} is null.
	 * 
	 * @param trackResult The track result to add
	 */
	public void addTrackResult(TrackResult trackResult) 
	{
		if (trackResults == null) {
			trackResults = new ArrayList<TrackResult>();
		}
		trackResults.add(trackResult);
	}
	
}
