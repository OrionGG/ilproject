
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;


/**
 * A list of {@link ReleaseResult}s.
 * 
 * @see ReleaseResult
 * @author Patrick Ruhkopf
 */
public class ReleaseSearchResults extends ListElement{

    protected List<ReleaseResult> releaseResults = new ArrayList<ReleaseResult>();

	/**
	 * @return the release results
	 */
	public List<ReleaseResult> getReleaseResults() {
		return releaseResults;
	}

	/**
	 * @param releaseResults the release results to set
	 */
	public void setReleaseResults(List<ReleaseResult> releaseResults) {
		this.releaseResults = releaseResults;
	}
	
	/**
	 * Convenience method to adds an release result to the list.
	 * 
	 * This will create a new <code>ArrayList</code> if {@link #releaseResults} is null.
	 * 
	 * @param releaseResult The release result to add
	 */
	public void addReleaseResult(ReleaseResult releaseResult) 
	{
		if (releaseResults == null) {
			releaseResults = new ArrayList<ReleaseResult>();
		}
		releaseResults.add(releaseResult);
	}
	
}
