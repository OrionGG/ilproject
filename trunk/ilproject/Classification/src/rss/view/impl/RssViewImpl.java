package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.model.ModelException;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;
import org.xml.sax.SAXException;

import com.sun.syndication.feed.synd.SyndFeed;

import es.tid.networkedvehicle.drive.applications.rss.view.RssView;
import es.tid.networkedvehicle.drive.platform.hmi.service.HMIService;
import es.tid.networkedvehicle.drive.platform.resources.rss.RssUpdateListener;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.Events;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.HMIApplicationInfo;

/**
 * Clase que implementa la aplicacion RSS
 * 
 * @author jorgev
 * 
 */

public class RssViewImpl implements RssView, EventHandler, ManagedService,
		RssUpdateListener
{
	
	/**
	 * Logger
	 */
	private Log							_logger				= null;
	
	/**
	 * Bundle context
	 */
	private ComponentContext			_componentContext	= null;
	
	/**
	 * HMI Service
	 */
	protected HMIService				_hmiService			= null;
	
	/**
	 * Properties properties
	 */
	private Properties					_properties			= new Properties ( );
	
	/**
	 * Event admin ref
	 */
	private final EventAdmin			_eventAdmin			= null;
	
	/**
	 * HMI Application info
	 */
	private final HMIApplicationInfo	_hmiAppInfo			= null;
	
	/**
	 * Drive application contextp
	 */
	// private DriveApplicationContext _driveAppContext = null;
	
	/**
	 * Application state machine
	 */
	protected RssViewStateMachine		_stateMachine		= null;
	
	/**
	 * HMI Provider
	 */
	protected RssViewHMIProvider		_hmiProvider		= null;
	
	/**
	 * Event handler
	 */
	private RssViewEventHandlerImpl		_eventHandler		= null;
	
	/**
	 * Activate declarative service component implementation To be used or
	 * overridden by applications
	 * 
	 * @param context
	 *            OSGi Component context
	 */
	protected void activate ( ComponentContext context ) throws Exception
	{
		this._componentContext = context;
		getLogger ( ).info ( "Activated component:" + getClass ( ).getName ( ) );
		
		handleActivate ( );
		
		if ( _stateMachine != null )
		{
			_stateMachine.fireEvent ( Events.ACTIVATED );
		}
		
		HMIApplicationInfo hmiInfo = new HMIApplicationInfo ( );
		// hmiInfo.setAppId ( context.getApplicationId ( ) );
		// hmiInfo.setPriority ( context.getPriority ( ) );
		
		_hmiProvider = new RssViewHMIProvider ( hmiInfo,
				getComponentContext ( ), _hmiService );
		initStateMachine ( );
		
		_stateMachine.fireEvent ( RssEvents.READ );
	}
	
	/**
	 * Perform some processing while activating the Declarative service
	 * component TO BE OVERRIDEN
	 */
	protected void handleActivate ( )
	{
	}
	
	/**
	 * Deactivate declarative service component implementation To be used or
	 * overridden by applications
	 * 
	 * @param ctxt
	 *            OSGi Component context
	 */
	protected void deactivate ( ComponentContext ctxt ) throws Exception
	{
		getLogger ( )
				.info ( "Deactivate Component:" + getClass ( ).getName ( ) );
		_stateMachine.fireEvent ( Events.TERMINATE );
		this._componentContext = null;
		if ( _stateMachine != null )
		{
			_stateMachine.fireEvent ( Events.DEACTIVATED );
		}
		
		if ( _stateMachine != null )
		{
			_stateMachine.fireEvent ( Events.TERMINATE );
		}
	}
	
	/**
	 * Bind HMI Service
	 * 
	 * @param hmiService
	 *            HMI Service instance
	 */
	public void bindHMIService ( HMIService hmiService )
	{
		getLogger ( ).debug ( "BIND HMI MANAGER" );
		this._hmiService = hmiService;
		if ( _hmiProvider != null )
		{
			_hmiProvider.setHMIService ( hmiService );
		}
		
		if ( _stateMachine != null )
		{
			// Refresh hmi
			_stateMachine.refresh ( );
		}
	}
	
	/**
	 * Unbind HMI Service
	 * 
	 * @param guiService
	 *            HMI Service instance
	 */
	public void unbindHMIService ( HMIService hmiService )
	{
		getLogger ( ).debug ( "UNBIND HMI MANAGER" );
		this._hmiService = null;
		if ( _hmiProvider != null )
		{
			_hmiProvider.setHMIService ( null );
		}
	}
	
	/**
	 * Get application info for the HMI loader
	 * 
	 * @return String application id
	 */
	protected HMIApplicationInfo getHMIApplicationInfo ( )
	{
		return _hmiAppInfo;
	}
	
	/**
	 * Get component context
	 * 
	 * @return Component context
	 */
	protected ComponentContext getComponentContext ( )
	{
		return _componentContext;
	}
	
	/**
	 * Get properties
	 * 
	 * @return Properties
	 */
	protected Properties getProperties ( )
	{
		if ( _properties == null )
		{
			_properties = new Properties ( );
		}
		return _properties;
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
	
	/**
	 * Handle incoming event from event admin
	 * 
	 * Events from the bt connection manager
	 * 
	 * @param event
	 *            Event from event admin
	 */
	@Override
	public void handleEvent ( Event event )
	{
		if ( _eventHandler == null )
		{
			_eventHandler = new RssViewEventHandlerImpl ( _stateMachine );
		}
		
		_eventHandler.handleEvent ( event );
	}
	
	//
	// Configuration Manager methods
	//
	
	/**
	 * Update the configuration for the application.
	 * 
	 * Receive the properties configuration as a Dictionary from the
	 * Configuration Admin or from a local configuration
	 * 
	 * @param properties
	 *            Dictionary of properties
	 * @throws ConfigurationException
	 *             Exception
	 */
	@SuppressWarnings ( "unchecked" )
	@Override
	public void updated ( final Dictionary properties ) throws ConfigurationException
	{
		Runnable runnable = new Runnable ( )
			{
				
				@Override
				public void run ( )
				{
					// Configuration retrieved
					if ( properties != null )
					{
						for ( Enumeration < Object > keys = properties.keys ( ); keys
								.hasMoreElements ( ); )
						{
							
							Object key = keys.nextElement ( );
							_properties.setProperty ( String.valueOf ( key ),
									String.valueOf ( properties.get ( key ) ) );
							// System.out.println (getAppId ( ) + " " +
							// String.valueOf ( key
							// ) + " " + String.valueOf ( properties.get ( key )
							// ));
						}
						
						// ProcessProperties
						processHMIProperties ( );
					}
					
					// Fire properties updated event
					if ( _stateMachine != null )
					{
						_stateMachine.fireEvent ( Events.PROPERTIES_UPDATED );
					}
				}
			};
		
		new Thread ( "updating rss" ).start ( );
	}
	
	/**
	 * Process HMI properties updated
	 */
	protected void processHMIProperties ( )
	{
		
		// Fill in priority
		if ( _properties.containsKey ( RssViewConstants.PRIORITY_PROP ) )
		{
			try
			{
				_hmiAppInfo.setPriority ( Integer.valueOf ( String
						.valueOf ( _properties
								.get ( RssViewConstants.PRIORITY_PROP ) ) ) );
			}
			catch ( NumberFormatException e )
			{
				getLogger ( ).error ( "Error parsing priority property.", e );
			}
		}
	}
	
	private void initStateMachine ( ) throws IOException, SAXException,
			ModelException
	{
		_stateMachine = new RssViewStateMachine ( _hmiProvider );
		_hmiProvider.addListener ( _stateMachine );
		if ( _eventHandler != null )
		{
			_eventHandler.addListener ( _stateMachine );
		}
		
		_stateMachine.startMachine ( );
		_stateMachine.fireEvent ( RssEvents.INIT );
	}
	
	/*
	 * @Override public void pause ( ) { if ( _stateMachine != null ) {
	 * _stateMachine.fireEvent ( Events.PAUSE ); } }
	 * 
	 * @Override public void resume ( ) { if ( _stateMachine != null ) {
	 * _stateMachine.fireEvent ( Events.RESUME ); } }
	 */

	@Override
	public void feedUpdated ( Map < String, SyndFeed > map )
	{
		if ( _stateMachine != null )
		{
			_stateMachine.updateFeedsScreen ( map );
		}
	}
	
	/*
	 * @Override public void resume ( DriveApplicationContext context ) {
	 * this._driveAppContext = context; if ( _hmiProvider != null ) { try {
	 * _hmiProvider.bringToFront ( ); } catch ( ApplicationException e ) { //
	 * TODO Auto-generated catch block } } }
	 * 
	 * @Override public void dispose ( ) { if ( _hmiProvider != null ) {
	 * _hmiProvider.dispose ( ); }
	 * 
	 * }
	 */
}
