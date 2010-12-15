package org.musicbrainz.webservice.filter;

import java.util.HashMap;
import java.util.Map;

import org.musicbrainz.model.Release;
import org.musicbrainz.utils.MbUtils;

/**
 * <p>A filter for the release collection.</p>
 * 
 * <p>If {@link #discId} or {@link #artistId} are set, only releases matching
 * those IDs are returned. The {@link #releaseTypes} parameter allows
 * to limit the types of the releases returned. You can set it to
 * {@link Release#TYPE_ALBUM}, {@link Release#TYPE_OFFICIAL}, for example,
 * to only get officially released albums. Note that those values
 * are connected using the </em>AND</em> operator. MusicBrainz' support
 * is currently very limited, so {@link Release#TYPE_LIVE} and
 * {@link Release#TYPE_COMPILATION} exclude each other (see the 
 * documentation on release attributes http://wiki.musicbrainz.org/AlbumAttribute
 * for more information and all valid values).</p>
 * 
 * <p>If both the {@link #artistName} and the {@link #artistId} parameter are
 * given, the server will ignore {@link #artistName}.</p>
 * 
 * <p><em>Note that these filter properties properties and <code>query</code>
 * may not be used together.</em></p>
 * 
 * @author Patrick Ruhkopf
 */
public class ReleaseFilter extends DefaultFilter 
{
	/**
	 * A String containing the release's title
	 */
	private String title;
	
	/**
	 * A String containing the DiscID
	 */
	private String discId;
	
	/**
	 * A sequence of release type URIs
	 */
	private String[] releaseTypes;
	
	/**
	 * A String containing the artist's name
	 */
	private String artistName;
	
	/**
	 * A String containing the artist's ID
	 */
	private String artistId;
	


	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter()
	 */
	public ReleaseFilter() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter(long, long, String)
	 */
	public ReleaseFilter(long limit, long offset, String query) {
		super(limit, offset, query);
	}
	
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter#createParameters()
	 */
	@Override
	public Map<String, String> createParameters() 
	{
		Map<String, String> map = super.createParameters();
		Map<String, String> localmap = new HashMap<String, String>();
		
		StringBuffer releaseTypesStr = null;
		if (releaseTypes != null && releaseTypes.length > 0) {
			releaseTypesStr = new StringBuffer();
			for (String rt : releaseTypes) {
				releaseTypesStr.append(MbUtils.extractTypeFromURI(rt)).append(" ");
			}
		}
		
		// construct the track filter's map			
		if (this.artistId != null) localmap.put("artistid", artistId);
		if (this.artistName != null) localmap.put("artist", artistName);
		if (this.discId != null) localmap.put("discid", discId);
		if (releaseTypesStr != null) localmap.put("releasetypes", releaseTypesStr.substring(0, releaseTypesStr.length()-1));
		if (this.title != null) localmap.put("title", title);
		
		// using a custom query together with the release filter's fields is not allowed
		if (map.containsKey("query") && !localmap.isEmpty()) {
			throw new IllegalArgumentException("The query property may not be used together with other filters!");
		}
		
		// using neither a custom query nor one of release filter's fields is not allowed
		if (!map.containsKey("query") && localmap.isEmpty()) {
			throw new IllegalArgumentException("This filter must specify a query or one of ReleaseFilter's properties!");
		}
		
		//Combine default filter's parameters with release filter's
		map.putAll(localmap);
		
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
	 * @return the discId
	 */
	public String getDiscId() {
		return discId;
	}

	/**
	 * @param discId the discId to set
	 */
	public void setDiscId(String discId) {
		this.discId = discId;
	}

	/**
	 * @return the releaseTypesStr
	 */
	public String[] getReleaseTypes() {
		return releaseTypes;
	}

	/**
	 * @param releaseTypes the releaseTypes to set
	 */
	public void setReleaseTypesStr(String[] releaseTypes) {
		this.releaseTypes = releaseTypes;
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