package dao;




/*
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.musicbrainz.JMBWSException;
import org.musicbrainz.Query;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Disc;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.ReleaseEvent;
import org.musicbrainz.model.Track;
import org.musicbrainz.model.User;
import org.musicbrainz.utils.MbUtils;
import org.musicbrainz.webservice.AuthorizationException;
import org.musicbrainz.webservice.filter.ArtistFilter;
import org.musicbrainz.webservice.filter.ReleaseFilter;
import org.musicbrainz.webservice.filter.TrackFilter;
import org.musicbrainz.webservice.impl.HttpClientWebService;
import org.musicbrainz.webservice.includes.ArtistIncludes;
import org.musicbrainz.webservice.includes.ReleaseIncludes;
import org.musicbrainz.webservice.includes.TrackIncludes;
import org.musicbrainz.wsxml.element.ArtistResult;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.ReleaseResult;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackResult;
import org.musicbrainz.wsxml.element.TrackSearchResults;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Integration tests using the {@link Query} class.
 * 
 * @author Patrick Ruhkopf
 * @see Query
 */
public class DAO_MusicBrainz
{
	private static Log log = LogFactory.getLog(DAO_MusicBrainz.class);
	
	/**
	 * Enter valid credentials here:
	 */
	private static final String MY_USERNAME = "musicRecomendation";
	private static final String MY_PASSWORD = "musicRecomendation";
	
	/**
	 * A default {@link Query}
	 */
	static Query q;
	
	/**
	 * A {@link Query} with Authentication information
	 */
	Query authQ;
	/*
	@BeforeClass
	public void setUp() {
	    this.q = new Query();
	    
	    // if you want to test the functions that require authentication,
	    // edit the username and passwort constants!
	    HttpClient client = new HttpClient();
	    client.getState().setCredentials(
	    	new AuthScope( "musicbrainz.org", 80, AuthScope.ANY_REALM),
            new UsernamePasswordCredentials(MY_USERNAME, MY_PASSWORD)
        );
	    this.authQ = new Query(new HttpClientWebService(client));
        
	}
	
	@BeforeMethod
	public static void wait1Second()
	{
		// because we are only allowed to query the web service once in a second
		try {
			log.debug("waiting 1 second to avoid too much server load");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.warn(e.getMessage(), e);
		}
	}
	*/
	
	/*
	 
	 * Search for an artist by name.
	 * @throws JMBWSException
	 */
	/*
	@Test
	public static Artist findArtist(String nameArtist) throws JMBWSException 
	{
		// Search for all artists matching the given name.
		ArtistFilter f = new ArtistFilter(nameArtist);

		// Limit the results to the 5 best matches.
		f.setLimit(1L);
		ReleaseFilter filter=new ReleaseFilter();
		q.getReleases(filter);
		ArtistSearchResults artistResults = q.getArtists(f);
		// some tests
		//assertTrue(artistResults.getArtistResults().size() > 0);
		boolean found = false;
		Artist artist = null;
		for (ArtistResult ar : artistResults.getArtistResults()) 
		{
			artist = ar.getArtist();
			log.debug("[artist] " + artist.getId() + " - " + artist.getName() + " (" + artist.getBeginDate() + " - " + artist.getEndDate() + ")");
			System.out.print(artist.getName());
			assertNotNull(artist.getId());
			if (nameArtist.equals(artist.getName())) {
				artist.getSortName();
				artist.getBeginDate();
				found = true;
			}
		}
		
		return artist;
	}
	
	*/
	@BeforeClass
	public void setUp() {
	    q = new Query();
	    
	    // if you want to test the functions that require authentication,
	    // edit the username and passwort constants!
	    HttpClient client = new HttpClient();
	    client.getState().setCredentials(
	    	new AuthScope( "musicbrainz.org", 80, AuthScope.ANY_REALM),
            new UsernamePasswordCredentials(MY_USERNAME, MY_PASSWORD)
        );
	    this.authQ = new Query(new HttpClientWebService(client));
        
	}
	
	@BeforeMethod
	public void wait1Second()
	{
		// because we are only allowed to query the web service once in a second
		try {
			log.debug("waiting 1 second to avoid too much server load");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.warn(e.getMessage(), e);
		}
	}
/*
	/**
	 * Search for an artist by name.
	 * @return 
	 * @throws JMBWSException
	 */
	@Test
	public Artist findArtist(String s) throws JMBWSException 
	{
		// Search for all artists matching the given name.
		ArtistFilter f = new ArtistFilter(s);
		
		// Limit the results to the 5 best matches.
		f.setLimit(1L);
		//System.out.print(s+" "+f.toString());
		ArtistSearchResults artistResults = q.getArtists(f);
	  
		/*
		if(artistResults.getArtistResults().size() > 0)
		{}
		else
		{}
		*/
		
		boolean found = false;
		Artist artist=null;
		for (ArtistResult ar : artistResults.getArtistResults()) 
		{
			artist = ar.getArtist();
			log.debug("[artist] " + artist.getId() + " - " + artist.getName() + " (" + artist.getBeginDate() + " - " + artist.getEndDate() + ")");
	  
			assertNotNull(artist.getId());
			if (s.equals(artist.getName())) {
				assertNotNull(artist.getSortName());
				assertNotNull(artist.getBeginDate());
				found = true;
			}
		}
		assertTrue(found);
		return artist;
	}
	

	
	/**
	 * Search for an artist by name.
	 * @throws JMBWSException
	 */
	
	public static List <Artist> findListArtists(List <String> listArtist) throws JMBWSException 
	{
		Iterator iter = listArtist.iterator();
    	int i=1;
    	List <Artist> artistList= new  ArrayList <Artist>();
    	while (iter.hasNext())
    	{
    	
    		String nameArtist=(String) iter.next();
    		if(nameArtist==null)
    		{
    			nameArtist="U2";
    		}
			// Search for all artists matching the given name.
			ArtistFilter f = new ArtistFilter(nameArtist);
			
			// Limit the results to the 5 best matches.
			f.setLimit(1L);
			ArtistSearchResults artistResults = q.getArtists(f);
	
		//	boolean found = false;
			Artist artist = null;
			ArtistResult ar=(ArtistResult) artistResults.getArtistResults();
			artist = ar.getArtist();
			
			/*for (ArtistResult ar : artistResults.getArtistResults()) 
			{
				artist = ar.getArtist();
				//log.debug("[artist] " + artist.getId() + " - " + artist.getName() + " (" + artist.getBeginDate() + " - " + artist.getEndDate() + ")");
				System.out.print(artist.getName());
				if (nameArtist.equals(artist.getName())) {
					artist.getSortName();
					artist.getBeginDate();
					found = true;
				}
			}*/
			
			artistList.add(artist);
    	}
		return artistList;
	}
	
	
	private static void assertNotNull(String id) {
		// TODO Auto-generated method stub
		
	}

	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Retrieve an artist by ID and display all official albums.
	 * @throws JMBWSException
	 */
	@Test
	public void getArtist(String id) throws JMBWSException 
	{		
		// 3 doors down id
		id = "2386cd66-e923-4e8e-bf14-2eebe2e9b973";
		  
		// The result should include all official albums.
		ArtistIncludes includes = new ArtistIncludes(new String[] { Release.TYPE_OFFICIAL, Release.TYPE_ALBUM } );
		Artist artist = q.getArtistById(id, includes);
		log.debug(artist.toString());
		
		// some tests
		MbUtils.convertIdToURI(id, "artist");
		//assertEquals("3 Doors Down", artist.getName());
		assertTrue(artist.getReleases().size() > 0);
		
		for (Release r : artist.getReleases()) {
			log.debug(r.toString());
		}
	}
	
	/**
	 * Search for an release by name.
	 * @throws JMBWSException
	 */
	@Test
	public void findRelease() throws JMBWSException 
	{
		// Search for a specific release
		ReleaseFilter f = new ReleaseFilter();
		f.setArtistName("3 Doors Down");
		f.setTitle("Away from the Sun");
		f.setReleaseTypesStr(new String[] { Release.TYPE_OFFICIAL } );
		ReleaseSearchResults releaseResults = q.getReleases(f);
	  
		// some tests
		assertTrue(releaseResults.getReleaseResults().size() > 0);
		ReleaseResult rs = releaseResults.getReleaseResults().get(0);
		assertNotNull(rs);
		
		Release r = rs.getRelease();
		log.debug("[release] " + r.getId() + " - " + r.getArtist() + " - " + r.getTitle());
		r.getId();
		r.getArtist();
		assertNotNull(r.getAsin());
		assertNotNull(r.getTitle());
		assertNotNull(r.getTextLanguage());
		assertNotNull(r.getTextScript());
	}
	
	private void assertNotNull(ReleaseResult rs) {
		// TODO Auto-generated method stub
		
	}

	private void assertNotNull(Artist artist) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Retrieve an release by ID and display some releations.
	 * @throws JMBWSException
	 */
	@Test
	public void getRelease() throws JMBWSException 
	{
		// Away from the Sun from 3 Doors Down id
		String id = "6fc2c148-58d4-4120-842c-a56d8c961ae9";
		  
		// Additionally to the release itself, we want the server to include
		// the release's artist, all release events, associated discs and
		// the track list.
		ReleaseIncludes includes = new ReleaseIncludes();
		includes.setArtist(true);
		includes.setReleaseEvents(true);
		includes.setDiscs(true);
		includes.setTracks(true);

		Release r = q.getReleaseById(id, includes);
		log.debug(r.toString());
		
		// some tests
		//assertEquals(MbUtils.convertIdToURI(id, "release"), r.getId());
		assertTrue("away from the sun".equalsIgnoreCase(r.getTitle()));
		assertNotNull(r.getArtist());
		assertTrue("3 Doors Down".equals(r.getArtist().getName()));
		assertNotNull(r.getAsin());
		//assertNotNull(r.getReleaseEventList());
		//assertNotNull(r.getTrackList());
		assertTrue(r.getTrackList().getTracks().size() > 10);
		//assertNotNull(r.getDiscList());
		assertTrue(r.getDiscList().getDiscs().size() > 0);
		
		// print releases
		log.debug("earliest release: " + r.getEarliestReleaseDate());
		for (ReleaseEvent re : r.getReleaseEventList().getReleaseEvents()) {
			log.debug(re);
		}
		
		// print discs
		for (Disc disc : r.getDiscList().getDiscs()) {
			log.debug(disc);
		}
		
		// print tracks
		for (Track t : r.getTrackList().getTracks()) {
			log.debug(t.toString());
		}
	}
	
	/**
	 * Search for a track by title (and optionally by artist name).
	 * @throws JMBWSException 
	 */
	@Test
	public void findTrack() throws JMBWSException 
	{
		// find all tracks with a title superman
		TrackFilter tf = new TrackFilter();
		tf.setTitle("gone");
		TrackSearchResults trackResults = q.getTracks(tf);
		  
		// some tests
		assertTrue(trackResults.getTrackResults().size() > 0);
		for (TrackResult tr : trackResults.getTrackResults()) 
		{
			Track t = tr.getTrack();
			log.debug(t);
			assertNotNull(t.getId());
			t.getDuration();
			assertNotNull(t.getTitle());
		}
		
		// now limit results to artist
		tf.setArtistName("3 Doors Down");
		
		trackResults = q.getTracks(tf);
		  
		// some tests
		assertTrue(trackResults.getTrackResults().size() > 0);
		for (TrackResult tr : trackResults.getTrackResults()) 
		{
			Track t = tr.getTrack();
			log.debug(t);
			assertNotNull(t.getId());
			assertNotNull(t.getTitle());
		}
		
	}
	
	/**
	 * Retrieve a tack by ID.
	 * @throws JMBWSException
	 */
	@Test
	public void getTrack() throws JMBWSException 
	{
		// "Here Without You" by 3 Doors Down
		String id = "d3f1cd62-ae04-46d0-ba11-ec1970f890a0";
		  
		TrackIncludes includes = new TrackIncludes();
		includes.setArtist(true);
		
		Track t = q.getTrackById(id, includes);
		log.debug(t.toString());
		
		// some tests
		//assertEquals(MbUtils.convertIdToURI(id, "track"), t.getId());
		assertTrue("here without you".equalsIgnoreCase(t.getTitle()));		
		//assertNotNull(t.getDuration());
		assertTrue("3 doors down".equalsIgnoreCase(t.getArtist().getName()));		
	}
	
	@Test
	public void testGetUserByName() throws JMBWSException 
	{
		String userName = MY_USERNAME;
		
		// test unauthorized request
		try
		{
			q.getUserByName(userName);
			System.out.println("User should be authenticated to use this function!");
		}
		catch (AuthorizationException e) {
			// expected...
			log.debug("Expected exception was:", e);
		}
		
		// test authorized request
		User user = null;
		try
		{
			user = authQ.getUserByName(userName);
		}
		catch (AuthorizationException e) {
			log.error(e, e);
			System.out.print("Authentication failed - Did you provide valid credentials in the setUp method?");
		}
		
	//	user;
		log.debug(user);
	}



}
