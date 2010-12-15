package org.musicbrainz.webservice.includes;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Release;
import org.musicbrainz.utils.MbUtils;

/**
 * <p>A specification on how much data to return with an artist.</p>
 * 
 * <p>The MusicBrainz server only supports some combinations of release
 * types for the <code>releases</code> and <code>vaReleases</code> include
 * tags. At the moment, not more than two release types should be selected,
 * while one of them has to be {@link Release#TYPE_OFFICIAL}
 * {@link Release#TYPE_PROMOTION} or {@link Release#TYPE_BOOTLEG}.</p>
 * 
 * <p><em>Note: Only one of <code>releases</code> and 
 * <code>vaReleases</code> may be given.</em></p>
 * 
 * @author Patrick Ruhkopf
 */
public class ArtistIncludes implements Includes 
{
	private boolean aliases = false;
	
	private boolean artistRelations = false;
	
	private boolean releaseRelations = false;
	
	private boolean trackRelations = false;
	
	private boolean urlRelations = false;
	
	private String[] releases = null;
	
	private String[] vaReleases = null;
	
	/**
	 * Default constructor
	 */
	public ArtistIncludes()
	{
		
	}
	
	public ArtistIncludes(String[] releases)
	{
		this.releases = releases;
	}
	
	public ArtistIncludes(String[] releases, String[] vaReleases)
	{
		this.releases = releases;
		this.vaReleases = vaReleases;
	} 

	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Includes#createIncludeTags()
	 */
	public List<String> createIncludeTags() 
	{
		List<String> includeTags = new ArrayList<String>();
		
		// not that elegant but straight forward :)
		if (aliases) includeTags.add("aliases");
		if (artistRelations) includeTags.add("artist-rels");
		if (releaseRelations) includeTags.add("release-rels");
		if (trackRelations) includeTags.add("track-rels");
		if (urlRelations) includeTags.add("url-rels");
		
		// build releases includes
		if (releases != null) 
		{
			for (String releaseType : releases) {
				includeTags.add("sa-" + MbUtils.extractTypeFromURI(releaseType));
			}
		}
		if (vaReleases != null) 
		{
			for (String vaReleaseType : vaReleases) {
				includeTags.add("sa-" + MbUtils.extractTypeFromURI(vaReleaseType));
			}
		}
			
		return includeTags;
	}

	/**
	 * @return the aliases
	 */
	public boolean isAliases() {
		return aliases;
	}

	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(boolean aliases) {
		this.aliases = aliases;
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
	public String[] getReleases() {
		return releases;
	}

	/**
	 * @param releases the releases to set
	 */
	public void setReleases(String[] releases) {
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

	/**
	 * @return the vaReleases
	 */
	public String[] getVaReleases() {
		return vaReleases;
	}

	/**
	 * @param vaReleases the vaReleases to set
	 */
	public void setVaReleases(String[] vaReleases) {
		this.vaReleases = vaReleases;
	}
	
	

}
