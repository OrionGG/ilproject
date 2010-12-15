package org.musicbrainz;

import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.Track;
import org.musicbrainz.model.User;
import org.musicbrainz.utils.MbUtils;
import org.musicbrainz.webservice.AuthorizationException;
import org.musicbrainz.webservice.RequestException;
import org.musicbrainz.webservice.ResponseException;
import org.musicbrainz.webservice.WebService;
import org.musicbrainz.webservice.WebServiceException;
import org.musicbrainz.webservice.filter.ArtistFilter;
import org.musicbrainz.webservice.filter.DefaultFilter;
import org.musicbrainz.webservice.filter.Filter;
import org.musicbrainz.webservice.filter.ReleaseFilter;
import org.musicbrainz.webservice.filter.TrackFilter;
import org.musicbrainz.webservice.filter.UserFilter;
import org.musicbrainz.webservice.impl.HttpClientWebService;
import org.musicbrainz.webservice.includes.ArtistIncludes;
import org.musicbrainz.webservice.includes.Includes;
import org.musicbrainz.webservice.includes.ReleaseIncludes;
import org.musicbrainz.webservice.includes.TrackIncludes;
import org.musicbrainz.wsxml.MbXMLException;
import org.musicbrainz.wsxml.MbXmlParser;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.Metadata;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackSearchResults;

/**
 * <p>A simple interface to the MusicBrainz web service.</p>
 * 
 * <p>This is a facade which provides a simple interface to the MusicBrainz
 * web service. It hides all the details like fetching data from a server,
 * parsing the XML and creating an object tree. Using this class, you can
 * request data by ID or search the collection of all resources
 * (artists, releases, or tracks) to retrieve those matching given
 * criteria. This document contains examples to get you started.</p>
 * 
 * 
 * <h3>Working with Identifiers</h3>
 * <p>MusicBrainz uses absolute URIs as identifiers. For example, the artist
 * 'Tori Amos' is identified using the following URI:</p>
 * <pre>http://musicbrainz.org/artist/c0b2500e-0cef-4130-869d-732b23ed9df5</pre>
 * 
 * <p>In some situations it is obvious from the context what type of 
 * resource an ID refers to. In these cases, abbreviated identifiers may
 * be used, which are just the UUID part of the URI. Thus the ID above
 * may also be written like this:</p>
 * <pre>c0b2500e-0cef-4130-869d-732b23ed9df5</pre>
 * <p>All methods in this class which require IDs accept both the absolute
 * URI and the abbreviated form (aka the relative URI).</p>
 * 
 * 
 * <h3>Creating a Query Object</h3>
 * <p>In most cases, creating a {@link Query} object is as simple as this:</p>
 * <pre>Query q = new Query();</pre>
 * 
 * <p>The instantiated object uses standard {@link WebService} and {@link MbXmlParser}
 * implementations to access the MusicBrainz web service. If you want to use a
 * different server or you have to pass user name and password because one of
 * your queries requires authentication, you have to create the {@link WebService}
 * object yourself and configure it appropriately.<br/>
 * This example uses the MusicBrainz test server and also sets authentication data:</p>
 * <pre>
 *   HttpClient client = new HttpClient();
 * 	 client.getState().setCredentials(
 *     new AuthScope("test.musicbrainz.org", 80, AuthScope.ANY_REALM),
 *     new UsernamePasswordCredentials("whatever", "secret")
 *   );
 *   Query.q = new Query(new HttpClientWebService(client));
 * </pre>
 *   
 *   
 * <h3>Querying for Individual Resources</h3> 
 * <p>If the MusicBrainz ID of a resource is known, then the {@link Query#getArtistById(String, ArtistIncludes)},
 * {@link Query#getReleaseById(String, ReleaseIncludes)}, or {@link Query#getTrackById(String, TrackIncludes)}
 * method can be used to retrieve it. Example:</p>
 * <pre>
 *   Artist artist = q.getArtistById('c0b2500e-0cef-4130-869d-732b23ed9df5')
 * </pre>
 * 
 * <p>This returned just the basic artist data, however. To get more detail
 * about a resource, the include parameters may be used which expect
 * an {@link ArtistIncludes}, {@link ReleaseIncludes}, or {@link TrackIncludes}
 * object, depending on the resource type.</p>
 * 
 * <p>To get data about a release which also includes the main artist
 * and all tracks, for example, the following query can be used:</p>
 * <pre>
 *   ReleaseIncludes includes = new ReleaseIncludes();
 *   includes.setArtist(true);
 *   includes.setTracks(true);
 *   
 *   Release r = q.getReleaseById(id, includes);
 * </pre>
 * 
 * <p><em>Note that the query gets more expensive for the server the more
 * data you request, so please be nice.</em></p>
 * 
 * <h3>Searching in Collections</h3>
 * <p>For each resource type (artist, release, and track), there is one
 * collection which contains all resources of a type. You can search
 * these collections using the {@link Query#getArtists(ArtistFilter)},
 * {@link Query#getReleases(ReleaseFilter)} and {@link Query#getTracks(TrackFilter)}
 * methods. The collections are huge, so you have to use filters {@link ArtistFilter},
 * {@link ReleaseFilter} or {@link TrackFilter} to retrieve only resources
 * matching given criteria.</p>
 * 
 * <p>For example, If you want to search the release collection for
 * releases with a specified DiscID, you would use {@link Query#getReleases(ReleaseFilter)}
 * and a {@link ReleaseFilter} object:</p>
 * <pre>
 *   ReleaseFilter f = new ReleaseFilter();
 *   f.discId("8jJklE258v6GofIqDIrE.c5ejBE-");
 *   ReleaseSearchResults releaseResults = q.getReleases(f);
 * </pre>
 * 
 * <p>The query returns a {@link ReleaseSearchResults} which contains the
 * releases ordered by score (a higher score indicating a better match).
 * Note that those results don't contain all the data about a resource.
 * If you need more detail, you can then use the {@link Query#getArtistById(String, ArtistIncludes)},
 * {@link Query#getReleaseById(String, ReleaseIncludes)} or {@link Query#getTrackById(String, TrackIncludes)}
 * methods to request the resource.</p>
 * 
 * <p>All filters support the {@link DefaultFilter#setLimit(Long)} argument
 * to limit the number of results returned. This defaults to 25, but the
 * server won't send more than 100 results to save bandwidth and processing
 * power. Using <em>limit</em> and <em>offset</em> parameter, you can page
 * through the results.</p>
 * 
 * <h3>Error Handling</h3>
 * <p>All methods in this class throw a {@link JMBWSException} in case of
 * errors. Depending on the method, a subclass of {@link JMBWSException}
 * be raised which allows an application to handle errors more precisely.
 * (see {@link WebServiceException}, {@link MbXMLException}, ...)<p>
 * 
 * @todo TODO Implement submitPuids
 * @author Patrick Ruhkopf
 */
public class Query {
	
	/**
	 * The web service implementation to use
	 */
	private WebService ws;	
	
	/**
	 * Default Constructor
	 */
	public Query()
	{
		this.ws = new HttpClientWebService();
	}

	/**
	 * Custom WebService Constructor
	 *  
	 * @param ws An implementation of {@link WebService}
	 */
	public Query(WebService ws)
	{
		this.ws = ws;
	}
	
	/**
	 * Returns an artist.
	 * 
	 * If no artist with that ID can be found, <code>include</code>
	 * contains invalid tags or there's a server problem, an exception is
	 * thrown.
	 * 
	 * @param id A string containing the artist's ID
	 * @param includes An {@link ArtistIncludes} object or null
	 * @return An {@link Artist}
	 * @throws JMBWSException 
	 */
	public Artist getArtistById(String id, ArtistIncludes includes) throws JMBWSException
	{
		String uuid;
		try {
			uuid = MbUtils.extractUuid(id, "artist");
		}
		catch(IllegalArgumentException e) {
			throw new RequestException("Invalid artist id: " + e.getMessage(), e);
		}
		
		Metadata md = getFromWebService("artist", uuid, includes, null);
		if(md.getArtist() == null)
			throw new ResponseException("Server didn't return artist!");
		
		return md.getArtist();
	}
	
	
	/**
	 * Returns artists matching given criteria.
	 * 
	 * @param filter The search criteria
	 * @throws JMBWSException 
	 * @return A list of artists
	 */
	public ArtistSearchResults getArtists(ArtistFilter filter) throws JMBWSException
	{
		Metadata result = this.getFromWebService("artist", "", null, filter);
		return result.getArtistResults();
	}
	

	/**
	 * Returns a release.
	 * 
	 * If no release with that ID can be found, <code>include</code>
	 * contains invalid tags or there's a server problem, and an 
	 * exception is thrown.
	 * 		
	 * @param id A string containing the release's ID
	 * @param include A {@link ReleaseIncludes} object or null
	 * 
	 * @return A {@link Release}
	 * @throws JMBWSException
	 */
	public Release getReleaseById(String id, ReleaseIncludes include) throws JMBWSException
	{
		String uuid;
		try {
			uuid = MbUtils.extractUuid(id, "release");
		}
		catch(IllegalArgumentException e) {
			throw new RequestException("Invalid release id: " + e.getMessage(), e);
		}
		
		Metadata md = getFromWebService("release", uuid, include, null);
		if(md.getRelease() == null)
			throw new ResponseException("Server didn't return release!");
		
		return md.getRelease();
	}
	
	
	/**
	 * Returns releases matching given criteria.
	 * 
	 * @param filter A {@link ReleaseFilter} object.
	 * @return A list of releases.
	 * @throws JMBWSException
	 */
	public ReleaseSearchResults getReleases(ReleaseFilter filter) throws JMBWSException
	{
		Metadata result = this.getFromWebService("release", "", null, filter);
		return result.getReleaseResults();
	}
	
	/**
	 * Returns a track.
	 * 
	 * If no track with that ID can be found, C{include} contains
	 * invalid tags or there's a server problem, and exception is raised.
	 * 
	 * @param id A string containing the track's ID
	 * @param include A {@link TrackIncludes} object
	 * @return A {@link Track}
	 * @throws JMBWSException
	 */
	public Track getTrackById(String id, TrackIncludes include) throws JMBWSException
	{
		String uuid;
		try {
			uuid = MbUtils.extractUuid(id, "track");
		}
		catch(IllegalArgumentException e) {
			throw new RequestException("Invalid track id: " + e.getMessage(), e);
		}
		
		Metadata md = getFromWebService("track", uuid, include, null);
		if(md.getTrack() == null)
			throw new ResponseException("Server didn't return track!");
		
		return md.getTrack();
	}
	
	/**
	 * Returns tracks matching given criteria.
	 * 
	 * @param filter A {@link TrackFilter} object
	 * @return A list of tracks.
	 * @throws JMBWSException
	 */
	public TrackSearchResults getTracks(TrackFilter filter) throws JMBWSException
	{
		Metadata result = this.getFromWebService("track", "", null, filter);
		return result.getTrackResults();
	}
	
	/**
	 * Returns information about a MusicBrainz user.
	 * 
	 * You can only request user data if you know the user name and
	 * password for that account. If username and/or password are
	 * incorrect, an {@link AuthorizationException} is raised.
	 * 
	 * @param name The user's name
	 * @return A {@link User}
	 */
	public User getUserByName(String name) throws JMBWSException
	{
		UserFilter filter = new UserFilter(name);
		Metadata md = getFromWebService("user", "", null, filter);
		if ( (md.getUserList() == null) || (md.getUserList().getUsers() == null) )
			throw new ResponseException("Server didn't return user!");
		
		return md.getUserList().getUsers().get(0);
	}

	/**
	 * Uses the {@link WebService} instance to make a request and
	 * returns a {@link MetaData} object.
	 * 
	 * @param entity The entity
	 * @param id The id
	 * @param includes {@link Includes}
	 * @param filter {@link Filter}
	 * @return A {@link MetaData} object.
	 * @throws JMBWSException
	 */
	protected Metadata getFromWebService(String entity, String id, Includes includes, Filter filter) throws JMBWSException
	{
		return getWs().get(entity, id, (includes == null ? null : includes.createIncludeTags()), (filter == null ? null : filter.createParameters()));
	}

	/**
	 * @return the ws
	 */
	public WebService getWs() {
		return ws;
	}

	/**
	 * @param ws the ws to set
	 */
	public void setWs(WebService ws) {
		this.ws = ws;
	}
	
}
