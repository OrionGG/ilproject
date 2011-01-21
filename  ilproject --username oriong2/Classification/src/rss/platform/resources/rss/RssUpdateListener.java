package es.tid.networkedvehicle.drive.platform.resources.rss;

import java.util.Map;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * Interface implementing the RSS Resource
 * 
 * @author jorgev
 * 
 */
public interface RssUpdateListener
{
	void feedUpdated ( Map < String, SyndFeed > map );
}
