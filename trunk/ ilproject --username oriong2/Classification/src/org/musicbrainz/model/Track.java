package org.musicbrainz.model;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.wsxml.element.ReleaseList;

/**
 * <p>Represents a track.</p>
 * 
 * <p>This class represents a track which may appear on one or more releases.
 * A track may be associated with exactly one artist (the <em>main</em> 
 * artist).</p>
 * 
 * <p>Using {@link #getReleases()}, you can find out on which releases this track
 * appears. To get the track number, too, use the  {@link Release#getTrackList()}
 * method.</p>
 * 
 * <p>Note: Currently, the MusicBrainz server doesn't support tracks to be on
 * more than one release.</p>
 *
 * @author Patrick Ruhkopf
 */
public class Track extends Entity 
{
	/**
	 * The track's title.
	 */
	private String title;
	
	/**
	 * The track's main artist
	 */
	private Artist artist;
	
	/**
	 * The duration of this track in milliseconds
	 */
	private Long duration;
	
	/**
	 * PUIDs associated with this track
	 */
	private List<String> puids;
	
	/**
	 * The list of releases this track appears on.
	 */
	private ReleaseList releaseList = new ReleaseList();
	
	public Track() {
		
	}

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

	/**
	 * @return the duration
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	/**
	 * @return the puids
	 */
	public List<String> getPuids() {
		return puids;
	}

	/**
	 * @param puids the puids to set
	 */
	public void setPuids(List<String> puids) {
		this.puids = puids;
	}

	/**
	 * @return the releases
	 */
	public List<Release> getReleases() {
		return (releaseList == null ? null : releaseList.getReleases());
	}

	/**
	 * @param releases the releases to set
	 */
	public void setReleases(List<Release> releases) 
	{
		if (releaseList == null) {
			releaseList = new ReleaseList();
		}
		this.releaseList.setReleases(releases);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the releaseList
	 */
	public ReleaseList getReleaseList() {
		return releaseList;
	}

	/**
	 * @param releaseList the releaseList to set
	 */
	public void setReleaseList(ReleaseList releaseList) {
		this.releaseList = releaseList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "track id=" + getId() + ", title=" + getTitle() + ", artist=" + (getArtist() == null? "null" : getArtist().getName());
	}
	
	public void addPuid(String puid) 
	{
		if (puids == null)
			puids = new ArrayList<String>();
		
		puids.add(puid);
	}
	
}
