package org.musicbrainz.webservice.includes;

import java.util.ArrayList;
import java.util.List;


/**
 * A specification on how much data to return with a track.
 * 
 * @author Patrick Ruhkopf
 */
public class TrackIncludes implements Includes 
{	
	private boolean artist = false;
	
	private boolean releases = false;
	
	private boolean puids = false;
	
	private boolean artistRelations = false;
	
	private boolean releaseRelations = false;
	
	private boolean trackRelations = false;
	
	private boolean urlRelations = false;

	
	/**
	 * Default constructor
	 */
	public TrackIncludes()
	{
		
	}


	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Includes#createIncludeTags()
	 */
	public List<String> createIncludeTags() 
	{
		List<String> includeTags = new ArrayList<String>();
		
		if (artist) includeTags.add("artist");
		if (releases) includeTags.add("releases");
		if (puids) includeTags.add("puids");
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
	 * @return the puids
	 */
	public boolean isPuids() {
		return puids;
	}


	/**
	 * @param puids the puids to set
	 */
	public void setPuids(boolean puids) {
		this.puids = puids;
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
	 * @return the releases
	 */
	public boolean isReleases() {
		return releases;
	}


	/**
	 * @param releases the releases to set
	 */
	public void setReleases(boolean releases) {
		this.releases = releases;
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
