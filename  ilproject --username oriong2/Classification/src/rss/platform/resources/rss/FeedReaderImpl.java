package es.tid.networkedvehicle.drive.platform.resources.rss;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherListener;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;

public class FeedReaderImpl
{
	private static Log	s_logger	= LogFactory.getLog ( FeedReaderImpl.class );
	
	public static void fetch ( String url, FetcherListener listener )
	{
		try
		{
			s_logger.debug ( url + " retrieving" );
			URL feedUrl = new URL ( url );
			FeedFetcherCache feedInfoCache = HashMapFeedInfoCache
					.getInstance ( );
			FeedFetcher fetcher = new HttpURLFeedFetcher ( feedInfoCache );
			
			fetcher.addFetcherEventListener ( listener );
			
			s_logger.debug ( "Retrieving feed " + feedUrl );
			// Retrieve the feed.
			// We will get a Feed Polled Event and then a
			// Feed Retrieved event (assuming the feed is valid)
			SyndFeed feed = fetcher.retrieveFeed ( feedUrl );
			
			s_logger.debug ( feedUrl + " retrieved" );
			s_logger.debug ( feedUrl + " has a title: " + feed.getTitle ( )
					+ " and contains " + feed.getEntries ( ).size ( )
					+ " entries." );
		}
		catch ( Exception ex )
		{
			s_logger.error ( "ERROR: " + ex.getMessage ( ) );
			//ex.printStackTrace ( );
		}
	}
}
