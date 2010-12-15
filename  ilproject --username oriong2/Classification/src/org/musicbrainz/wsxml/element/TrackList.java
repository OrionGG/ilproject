package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Track;


/**
 * A list of {@link Track}s.
 * 
 * @see Track
 * @author Patrick Ruhkopf
 */
public class TrackList extends ListElement{

	private List<Track> tracks;

	/**
	 * @return the tracks
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	

	/**
	 * Adds a track to the list.
	 * 
	 * It will also create and set new ArrayList if
	 * {@link #tracks} is null.
	 * 
	 * @param track The track to add
	 */
	public void addTrack(Track track) 
	{
		if (tracks == null) {
			tracks = new ArrayList<Track>();
		}
		
		tracks.add(track);
	}
}
