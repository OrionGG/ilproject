
package org.musicbrainz.wsxml.element;

import org.musicbrainz.Query;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.Track;
import org.musicbrainz.wsxml.MbXmlParser;

/**
 * <p>Represents a parsed Music Metadata XML document.</p>
 * 
 * <p>The Music Metadata XML format is very flexible and may contain a
 * diverse set of data (e.g. an artist, a release and a list of tracks),
 * but usually only a small subset is used (either an artist, a release
 * or a track, or a lists of objects from one class).</p>
 * 
 * 
 * @see Query
 * @see MbXmlParser
 * 
 * @author Patrick Ruhkopf
 */
public class Metadata {

    Artist artist = null;
	
    Release release = null;
	
	Track track = null;
	
	ArtistSearchResults artistResults = new ArtistSearchResults();
	
	ReleaseSearchResults releaseResults = new ReleaseSearchResults();
	
	TrackSearchResults trackResults = new TrackSearchResults();
	
	UserList userList = new UserList();   
	                   

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
	 * @return the artistResults
	 */
	public ArtistSearchResults getArtistResults() {
		return artistResults;
	}

	/**
	 * @param artistResults the artistResults to set
	 */
	public void setArtistResults(ArtistSearchResults artistResults) {
		this.artistResults = artistResults;
	}

	/**
	 * @return the release
	 */
	public Release getRelease() {
		return release;
	}

	/**
	 * @param release the release to set
	 */
	public void setRelease(Release release) {
		this.release = release;
	}

	/**
	 * @return the releaseResults
	 */
	public ReleaseSearchResults getReleaseResults() {
		return releaseResults;
	}

	/**
	 * @param releaseResults the releaseResults to set
	 */
	public void setReleaseResults(ReleaseSearchResults releaseResults) {
		this.releaseResults = releaseResults;
	}

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

	/**
	 * @return the trackResults
	 */
	public TrackSearchResults getTrackResults() {
		return trackResults;
	}

	/**
	 * @param trackResults the trackResults to set
	 */
	public void setTrackResults(TrackSearchResults trackResults) {
		this.trackResults = trackResults;
	}

	/**
	 * @return the userList
	 */
	public UserList getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(UserList userList) {
		this.userList = userList;
	}
	
	

}
