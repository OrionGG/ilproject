package es.tid.networkedvehicle.drive.platform.resources.rss;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.fetcher.FetcherEvent;
import com.sun.syndication.fetcher.FetcherListener;

public class FetcherEventListener implements FetcherListener
{
	private static Log				s_logger	= LogFactory
														.getLog ( FetcherEventListener.class );
	
	private FeedRetrievedListener	m_listener	= null;
	
	private SyndFeed				m_feed		= null;
	
	public FetcherEventListener ( String text, FeedRetrievedListener listener )
	{
		this.m_listener = listener;
		
		m_feed = new SyndFeedImpl ( );
		m_feed.setFeedType ( "" );
		
		m_feed.setTitle ( text );
		m_feed.setDescription ( text );
		m_feed.setAuthor ( text );
		m_feed.setLink ( text );
		
		clear ( );
	}
	
	public void clear ( )
	{
		List entries = new ArrayList ( );
		m_feed.setEntries ( entries );
	}
	
	public SyndFeed getFeed ( )
	{
		return m_feed;
	}
	
	/**
	 * @see com.sun.syndication.fetcher.FetcherListener#fetcherEvent(com.sun.syndication.fetcher.FetcherEvent)
	 */
	public void fetcherEvent ( FetcherEvent event )
	{
		String eventType = event.getEventType ( );
		if ( FetcherEvent.EVENT_TYPE_FEED_POLLED.equals ( eventType ) )
		{
			s_logger.debug ( "\tEVENT: Feed Polled. URL = "
					+ event.getUrlString ( ) );
		}
		else if ( FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals ( eventType ) )
		{
			s_logger.debug ( "\tEVENT: Feed Retrieved. URL = "
					+ event.getUrlString ( ) );
			m_feed.getEntries ( ).addAll ( event.getFeed ( ).getEntries ( ) );
			if ( m_listener != null )
				m_listener.feedRetrieved ( m_feed );
		}
		else if ( FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals ( eventType ) )
		{
			s_logger.debug ( "\tEVENT: Feed Unchanged. URL = "
					+ event.getUrlString ( ) );
		}
	}
}