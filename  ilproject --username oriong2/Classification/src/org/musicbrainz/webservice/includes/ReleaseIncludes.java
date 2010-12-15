package org.musicbrainz.webservice.includes;

import java.util.ArrayList;
import java.util.List;


/**
 * A specification on how much data to return with a release.
 * 
 * @author Patrick Ruhkopf
 */
public class ReleaseIncludes implements Includes 
{

	
	private boolean artist = false;
	
	private boolean counts = false;
	
	private boolean releaseEvents = false;
	
	private boolean discs = false;
	
	private boolean tracks = false;
	
	private boolean artistRelations = false;
	
	private boolean releaseRelations = false;
	
	private boolean trackRelations = false;
	
	private boolean urlRelations = false;
	
	/**
	 * Default constructor
	 */
	public ReleaseIncludes()
	{
		
	}
	

	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Includes#createIncludeTags()
	 */
	public List<String> createIncludeTags() 
	{
		List<String> includeTags = new ArrayList<String>();
		
		
		// not that elegant but straight forward :)
		if (artist) includeTags.add("artist");
		if (counts) includeTags.add("counts");
		if (releaseEvents) includeTags.add("release-events");
		if (discs) includeTags.add("discs");
		if (tracks) includeTags.add("tracks");
		if (artistRelations) includeTags.add("artist-rels");
		if (releaseRelations) includeTags.add("release-rels");
		if (trackRelations) includeTags.add("track-rels");
		if (urlRelations) includeTags.add("url-rels");
			
		return includeTags;
	}


	/**
	 * @return the artist
	 */
	public boolean isArtist() {
		return artist;
	}


	/**
	 * @param artist the artist to set
	 */
	public void setArtist(boolean artist) {
		this.artist = artist;
	}


	/**
	 * @return the artistRelations
	 */
	public boolean isArtistRelations() {
		return artistRelations;
	}


	/**
	 * @param artistRelations the artistRelations to set
	 */
	public void setArtistRelations(boolean artistRelations) {
		this.artistRelations = artistRelations;
	}


	/**
	 * @return the counts
	 */
	public boolean isCounts() {
		return counts;
	}


	/**
	 * @param counts the counts to set
	 */
	public void setCounts(boolean counts) {
		this.counts = counts;
	}


	/**
	 * @return the discs
	 */
	public boolean isDiscs() {
		return discs;
	}


	/**
	 * @param discs the discs to set
	 */
	public void setDiscs(boolean discs) {
		this.discs = discs;
	}


	/**
	 * @return the releaseEvents
	 */
	public boolean isReleaseEvents() {
		return releaseEvents;
	}


	/**
	 * @param releaseEvents the releaseEvents to set
	 */
	public void setReleaseEvents(boolean releaseEvents) {
		this.releaseEvents = releaseEvents;
	}


	/**
	 * @return the releaseRelations
	 */
	public boolean isReleaseRelations() {
		return releaseRelations;
	}


	/**
	 * @param releaseRelations the releaseRelations to set
	 */
	public void setReleaseRelations(boolean releaseRelations) {
		this.releaseRelations = releaseRelations;
	}


	/**
	 * @return the trackRelations
	 */
	public boolean isTrackRelations() {
		return trackRelations;
	}


	/**
	 * @param trackRelations the trackRelations to set
	 */
	public void setTrackRelations(boolean trackRelations) {
		this.trackRelations = trackRelations;
	}


	/**
	 * @return the tracks
	 */
	public boolean isTracks() {
		return tracks;
	}


	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(boolean tracks) {
		this.tracks = tracks;
	}


	/**
	 * @return the urlRelations
	 */
	public boolean isUrlRelations() {
		return urlRelations;
	}


	/**
	 * @param urlRelations the urlRelations to set
	 */
	public void setUrlRelations(boolean urlRelations) {
		this.urlRelations = urlRelations;
	}

	

}
