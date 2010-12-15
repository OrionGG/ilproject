package org.musicbrainz.webservice.filter;

import java.util.Map;


/**
 * <p>A filter for the track collection.</p>
 * 
 * <p>If {@link #artistId}, {@link #releaseId} or {@link #puid} are set,
 * only tracks matching those IDs are returned.</p>
 * 
 * <p>The server will ignore {@link #artistName} and {@link #releaseTitle}
 * if {@link #artistId} or {@link #releaseId} are set respectively.</p>
 * 
 * <p><em>Note that thhese filter properties properties and <code>query</code>
 * may not be used together.</em></p>
 * 
 * @author Patrick Ruhkopf
 */
public class TrackFilter extends DefaultFilter {
	
	/**
	 * A String containing the track's title
	 */
	private String title;
	
	/**
	 * A String containing the artist's name
	 */
	private String artistName;
	
	/**
	 * A string containing the artist's ID
	 */
	private String artistId;
	
	/**
	 * A String containing the release's title
	 */
	private String releaseTitle;
	
	/**
	 * A String containing the release's title
	 */
	private String releaseId; 
	
	/**
	 * The track's length in milliseconds
	 */
	private Long duration;
	
	/**
	 * A string containing a PUID
	 */
	private String puid;

	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter()
	 */
	public TrackFilter() {
		super();
	}
	
	public TrackFilter(String title) {
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter(long, long, String)
	 */
	public TrackFilter(long limit, long offset, String query) {
		super(limit, offset, query);
	}
	
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter#createParameters()
	 */
	@Override
	public Map<String, String> createParameters() 
	{
		Map<String, String> map = super.createParameters();
		
		boolean makeUseOfTrackFilter = false;
		if ((this.artistId != null) || (this.artistName != null) || (this.duration != null) || 
			(this.puid != null) || (this.releaseId != null) || (this.releaseTitle != null) ||
			(this.title != null)) 
		{
			makeUseOfTrackFilter = true;
		}
		
		// using a custom query together with the track filter's fields is not allowed
		if (map.containsKey("query") && makeUseOfTrackFilter) {
			throw new IllegalArgumentException("The query property may not be used together with other filters!");
		}
		
		// using neither a custom query nor one of track filter's fields is not allowed
		if (!map.containsKey("query") && !makeUseOfTrackFilter) {
			throw new IllegalArgumentException("This filter must specify a query or one of TrackFilter's properties!");
		}
		
		// construct the track filter's map
		if (this.artistId != null) map.put("artistid", artistId);
		if (this.artistName != null) map.put("artist", artistName);
		if (this.duration != null) map.put("duration", duration.toString());
		if (this.puid != null) map.put("puid", puid);
		if (this.releaseId != null) map.put("releaseid", releaseId);
		if (this.releaseTitle != null) map.put("release", releaseTitle);
		if (this.title != null) map.put("title", title);
		
		return map;
	}

	/**
	 * @return the artistId
	 */
	public String getArtistId() {
		return artistId;
	}

	/**
	 * @param artistId the artistId to set
	 */
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	/**
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}

	/**
	 * @param artistName the artistName to set
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
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
	 * @return the puid
	 */
	public String getPuid() {
		return puid;
	}

	/**
	 * @param puid the puid to set
	 */
	public void setPuid(String puid) {
		this.puid = puid;
	}

	/**
	 * @return the releaseId
	 */
	public String getReleaseId() {
		return releaseId;
	}

	/**
	 * @param releaseId the releaseId to set
	 */
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	/**
	 * @return the releaseTitle
	 */
	public String getReleaseTitle() {
		return releaseTitle;
	}

	/**
	 * @param releaseTitle the releaseTitle to set
	 */
	public void setReleaseTitle(String releaseTitle) {
		this.releaseTitle = releaseTitle;
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


	
	

}