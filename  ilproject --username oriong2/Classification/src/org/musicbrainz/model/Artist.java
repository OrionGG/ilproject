package org.musicbrainz.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.musicbrainz.wsxml.element.ReleaseList;

/**
 * <p>Represents an artist.</p>
 * <p>Artists in MusicBrainz can have a type. Currently, this type can be 
 * either Person or Group for which the following URIs are assigned:</p>
 * <ul>
 *   <li>
 *     <code>http://musicbrainz.org/ns/mmd-1.0#Person</code>
 *   </li>
 *   <li>
 *     <code>http://musicbrainz.org/ns/mmd-1.0#Group</code>
 *   </li>
 * </ul>
 * <p>Use the {@link Artist#TYPE_PERSON} and {@link Artist#TYPE_GROUP}
 * constants for comparison.</p>
 *
 * @author Patrick Ruhkopf
 */
public class Artist extends Entity {
	
	public static final String TYPE_GROUP = NS_MMD_1 + "Group";
	
	public static final String TYPE_PERSON = NS_MMD_1 + "Person";
	
	/**
	 * The artist's type (as an absolute URI).
	 */
    private String type;
    
	/**
	 * The artist's name.
	 */
	private String name;
	
	/**
	 * The sort name is the artist's name in a special format which
	 * is better suited for lexicographic sorting. The MusicBrainz
	 * style guide specifies this format.
	 */
	private String sortName;
	
	/**
	 * The definition of the begin date depends on the artist's
	 * type. For persons, this is the day of birth, for groups it
	 * is the day the group was founded.
	 * 
	 * The returned date has the format 'YYYY', 'YYYY-MM', or 
	 * 'YYYY-MM-DD', depending on how much detail is known.
	 */
	private String beginDate;
	
	/**
	 * The definition of the end date depends on the artist's
	 * type. For persons, this is the day of death, for groups it
	 * is the day the group was dissolved.
	 * 
	 * The returned date has the format 'YYYY', 'YYYY-MM', or 
	 * 'YYYY-MM-DD', depending on how much detail is known.
	 */
	private String endDate;
	
	/**
	 * The disambiguation attribute may be used if there is more
	 * than one artist with the same name. In this case,
	 * disambiguation attributes are added to the artists' names
	 * to keep them apart.
	 * 
	 * For example, there are at least three bands named 'Vixen'.
	 * Each band has a different disambiguation in the MusicBrainz
	 * database, like 'Hip-hop' or 'all-female rock/glam band'.
	 */
	private String disambiguation;
	
	/**
	 * The list of aliases for this artist.
	 */
	private List<ArtistAlias> aliases = new ArrayList<ArtistAlias>();
	
	/**
	 * The list of releases from this artist.
	 * 
	 * This may also include releases where this artist isn't the
	 * main artist but has just contributed one or more tracks
	 * (aka VA-Releases).
	 */
	private ReleaseList releaseList = new ReleaseList();
	

	
	/**
	 * @return the aliases
	 */
	public List<ArtistAlias> getAliases() {
		return aliases;
	}


	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(List<ArtistAlias> aliases) {
		this.aliases = aliases;
	}


	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}


	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	/**
	 * @return the disambiguation
	 */
	public String getDisambiguation() {
		return disambiguation;
	}


	/**
	 * @param disambiguation the disambiguation to set
	 */
	public void setDisambiguation(String disambiguation) {
		this.disambiguation = disambiguation;
	}


	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the underlying <code>List</clode> of releases.
	 * 
	 * @return the releases
	 */
	public List<Release> getReleases() {
		return ( releaseList == null ? null : releaseList.getReleases());
	}


	/**
	 * Sets the underlying <code>List</clode> of releases.
	 * 
	 * Note: This will implicitly create a new {@link #releaseList}
	 * if it is null.
	 * 
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
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param sortName the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	public void addAlias(ArtistAlias alias) {
		aliases.add(alias);
	}
	
	/**
	 * <p>Adds a release to the underlying <code>List</clode>
	 * of releases.</p>
	 * 
	 * <p><em>Note: This will implicitly create a new {@link #releaseList}
	 * if it is null.</em></p>
	 * 
	 * @param release The {@link Release} to add.
	 */
	public void addRelease(Release release) 
	{
		if (releaseList == null) {
			releaseList = new ReleaseList();
		} 
		releaseList.addRelease(release);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "artist id=" + getId() + ", uname=" + getUniqueName();
	}
	
	
	/**
	 * Returns a unique artist name using disambiguation
	 * (if available).
	 * 
	 * This method returns the artist name together with the
	 * disambiguation attribute in parenthesis if it exists.
	 * Example: 'Vixen (Hip-hop)'.
	 */
	public String getUniqueName()
	{
		if (StringUtils.isNotBlank(disambiguation)) {
			return name + " (" + disambiguation + ")";
		}
		return name;
	}
	
}
