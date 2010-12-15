package org.musicbrainz.webservice.filter;

import java.util.Map;


/**
 * <p>A filter for the artist collection.</p>
 * 
 * <p>Note that the <code>name</code> and <code>query</code> properties
 * may not be used together.</p>
 * 
 * @author Patrick Ruhkopf
 */
public class ArtistFilter extends DefaultFilter {
	
	/**
	 * The name of the artist
	 */
	private String artistName = null;

	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter()
	 */
	public ArtistFilter() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter(long, long, String)
	 */
	public ArtistFilter(long limit, long offset, String query) {
		super(limit, offset, query);
	}
	
	/**
	 * Minimal constructor
	 * 
	 * @param artistName The name of an artist
	 */
	public ArtistFilter(String artistName) {
		this.artistName = artistName;
	}
	
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter#createParameters()
	 */
	@Override
	public Map<String, String> createParameters() 
	{
		Map<String, String> map = super.createParameters();
		if (this.artistName != null) 
		{
			if (map.containsKey("query")) {
				throw new IllegalArgumentException("The name and query properties may not be used together!");
			}
			
			map.put("name", this.artistName);
		} 
		else {
			if (!map.containsKey("query")) {
				throw new IllegalArgumentException("This filter must specify a query or an artist name!");
			}
		}
		
		return map;
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

}