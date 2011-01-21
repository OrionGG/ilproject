package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.Events;

/**
 * Rss view application events
 * 
 * @author igd
 * 
 */
public final class RssEvents extends Events
{
	
	/** The events */
	public static final String	INIT			= "RssEvent.initiated";
	
	public static final String	DIE				= "RssEvent.die";
	
	public static final String	READ			= "RssEvent.read";
	
	public static final String	IDLE			= "RssEvent.idle";
	
	public static final String	BACK			= "RssEvent.back";
	
	public static final String	SELECT_FOLDER	= "RssEvent.selectFolder";
	
	public static final String	SELECT_FEED		= "RssEvent.selectFeed";
	
	public static final String	SELECT_ITEM		= "RssEvent.selectItem";
	
	public static final String	REPEAT			= "RssEvent.repeat";
	
	public static final String	NEXT			= "RssEvent.next";
	
	public static final String	PREVIOUS		= "RssEvent.previous";
	
	
	/**
	 * Constructor
	 */
	private RssEvents ( )
	{
		// Do nothing
	}
}
