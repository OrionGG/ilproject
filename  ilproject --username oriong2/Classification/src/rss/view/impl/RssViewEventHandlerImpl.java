package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.EventHandlerImpl;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.HMIListener;

public class RssViewEventHandlerImpl extends EventHandlerImpl
{
	
	/**
	 * Event property constant
	 */
	public static final String	EVENT_PROPERTY	= "event.property";
	
	/**
	 * Logger
	 */
	private Log					_logger			= null;
	
	/**
	 * Constructor
	 */
	public RssViewEventHandlerImpl ( )
	{
	}
	
	/**
	 * Constructor
	 */
	public RssViewEventHandlerImpl ( HMIListener listener )
	{
		super ( listener );
	}
	
	/**
	 * Get logger
	 * 
	 * @return Log
	 */
	protected Log getLogger ( )
	{
		if ( _logger == null )
		{
			_logger = LogFactory.getLog ( getClass ( ).getName ( ) );
		}
		return _logger;
	}
}
