package org.musicbrainz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.musicbrainz.wsxml.element.DiscList;
import org.musicbrainz.wsxml.element.ReleaseEventList;
import org.musicbrainz.wsxml.element.TrackList;

/**
 * <p>Represents a release.</p>
 * 
 * <p>A release within MusicBrainz is an {@link Entity} which contains {@link Track}
 * objects. Releases may be of more than one type: There can be albums,
 * singles, compilations, live recordings, official releases, bootlegs etc.</p>
 * 
 * <p>The current MusicBrainz server implementation supports only a
 * limited set of types.</p>
 *
 * @author Patrick Ruhkopf
 */
public class Release extends Entity {

	// TODO: we need a type for NATs	
	public static final String TYPE_NONE = NS_MMD_1 + "None";

	public static final String TYPE_ALBUM = NS_MMD_1 + "Album";
	public static final String TYPE_SINGLE = NS_MMD_1 + "Single";
	public static final String TYPE_EP = NS_MMD_1 + "EP";
	public static final String TYPE_COMPILATION = NS_MMD_1 + "Compilation";
	public static final String TYPE_SOUNDTRACK = NS_MMD_1 + "Soundtrack";
	public static final String TYPE_SPOKENWORD = NS_MMD_1 + "Spokenword";
	public static final String TYPE_INTERVIEW = NS_MMD_1 + "Interview";
	public static final String TYPE_AUDIOBOOK = NS_MMD_1 + "Audiobook";
	public static final String TYPE_LIVE = NS_MMD_1 + "Live";
	public static final String TYPE_REMIX = NS_MMD_1 + "Remix";
	public static final String TYPE_OTHER = NS_MMD_1 + "Other";

	public static final String TYPE_OFFICIAL = NS_MMD_1 + "Official";
	public static final String TYPE_PROMOTION = NS_MMD_1 + "Promotion";
	public static final String TYPE_BOOTLEG = NS_MMD_1 + "Bootleg";
	public static final String TYPE_PSEUDO_RELEASE = NS_MMD_1 + "Pseudo-Release";

	
	private String title;
	
	private String textLanguage;
	
	private String textScript;
	
	private String asin;
	
	private Artist artist;
	
	private String typeString;
	
	private ReleaseEventList releaseEventList = new ReleaseEventList();
	
	private DiscList discList = new DiscList();
	
	private TrackList trackList = new TrackList();
	
	
	public String[] getTypes() {
		return (typeString == null ? null : typeString.split(" "));
	}
	
	public void setTypes(String[] types) {
		setTypeString(StringUtils.join(types, " "));
	}

	/**
	 * @return the typeString
	 */
	public String getTypeString() {
		return typeString;
	}

	/**
	 * @param typeString the typeString to set
	 */
	public void setTypeString(String typeString) {
		this.typeString = typeString;
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
	 * @return the asin
	 */
	public String getAsin() {
		return asin;
	}

	/**
	 * @param asin the asin to set
	 */
	public void setAsin(String asin) {
		this.asin = asin;
	}

	/**
	 * @return the textLanguage
	 */
	public String getTextLanguage() {
		return textLanguage;
	}

	/**
	 * @param textLanguage the textLanguage to set
	 */
	public void setTextLanguage(String textLanguage) {
		this.textLanguage = textLanguage;
	}

	/**
	 * @return the textScript
	 */
	public String getTextScript() {
		return textScript;
	}

	/**
	 * @param textScript the textScript to set
	 */
	public void setTextScript(String textScript) {
		this.textScript = textScript;
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
	 * @return the discs
	 */
	public DiscList getDiscList() {
		return discList;
	}

	/**
	 * @param discs the discs to set
	 */
	public void setDiscList(DiscList discs) {
		this.discList = discs;
	}

	/**
	 * @return the releaseEvents
	 */
	public ReleaseEventList getReleaseEventList() {
		return releaseEventList;
	}

	/**
	 * @param releaseEvents the releaseEvents to set
	 */
	public void setReleaseEventList(ReleaseEventList releaseEvents) {
		this.releaseEventList = releaseEvents;
	}

	/**
	 * @return the tracks
	 */
	public TrackList getTrackList() {
		return trackList;
	}

	/**
	 * @param tracks the tracks to set
	 */
	public void setTrackList(TrackList tracks) {
		this.trackList = tracks;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "release id=" + getId() + ", title=" + getTitle();
	}
	
	/**
	 * Returns the earliest release event as a date string.
	 * 
	 * @return A String containing the date or null
	 */
	public String getEarliestReleaseDate()
	{
		return getEarliestReleaseEvent().getDateStr();
	}

	
	/**
	 * Returns the earliest release event.
	 * 
	 * @return A {@link ReleaseEvent} or null
	 */
	public ReleaseEvent getEarliestReleaseEvent()
	{
		if ( (releaseEventList == null) || (releaseEventList.getReleaseEvents() == null) ||
				(releaseEventList.getReleaseEvents().size() < 1))
			return null;
		
		List<ReleaseEvent> sortedREs = new ArrayList<ReleaseEvent>(releaseEventList.getReleaseEvents());
		Collections.sort(sortedREs, new Comparator<ReleaseEvent>()
			{
				public int compare(ReleaseEvent o1, ReleaseEvent o2) {
					return o1.getDate().compareTo(o2.getDate());
				}
			}
		);
		
		return sortedREs.get(0);
	}
	
	/**
	 * Returns the release events represented as a map.
	 * 
	 * Keys are ISO-3166 country codes like 'DE', 'UK', 'FR' etc.
	 * Values are dates in 'YYYY', 'YYYY-MM' or 'YYYY-MM-DD' format.
	 * 
	 * @return A map containing (countryCode, date) entries
	 */
	public Map<String, String> getReleaseEventsAsMap()
	{
		if ( (releaseEventList == null) || (releaseEventList.getReleaseEvents() == null)) {
			return null;
		}
		
		Map<String, String> ret = new HashMap<String, String>();
		for (ReleaseEvent event : releaseEventList.getReleaseEvents()) {
			ret.put(event.getCountryId(), event.getDateStr());
		}
		return ret;
	}
}
