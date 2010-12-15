
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Release;


/**
 * A list of {@link Release}s.
 * 
 * @see Release
 * @author Patrick Ruhkopf
 */
public class ReleaseList extends ListElement{

	private List<Release> releases = new ArrayList<Release>();

	/**
	 * @return the releases
	 */
	public List<Release> getReleases() {
		return releases;
	}

	/**
	 * @param releases the releases to set
	 */
	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}
	
	/**
	 * Adds a release to the list.
	 * 
	 * It will also create and set new ArrayList if
	 * {@link #releases} is null.
	 * 
	 * @param release The release to add
	 */
	public void addRelease(Release release) 
	{
		if (releases == null) {
			releases = new ArrayList<Release>();
		}
		
		releases.add(release);
	}
}
