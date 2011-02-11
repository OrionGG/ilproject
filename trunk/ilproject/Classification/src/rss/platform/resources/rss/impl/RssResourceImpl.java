package es.tid.networkedvehicle.drive.platform.resources.rss.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import opml.Body;
import opml.Opml;
import opml.Outline;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.ComponentContext;

import com.sun.syndication.feed.synd.SyndFeed;

import es.tid.networkedvehicle.drive.platform.resources.rss.FeedReaderImpl;
import es.tid.networkedvehicle.drive.platform.resources.rss.FeedRetrievedListener;
import es.tid.networkedvehicle.drive.platform.resources.rss.FetcherEventListener;
import es.tid.networkedvehicle.drive.platform.resources.rss.RssResource;
import es.tid.networkedvehicle.drive.platform.resources.rss.RssUpdateListener;

/**
 * Rss resource implementation
 * 
 * @author jorgev
 * 
 */
public class RssResourceImpl implements RssResource, ManagedService,
		FeedRetrievedListener
{
	
	/**
	 * Pool size
	 */
	private static final int						POOL_SIZE				= 10;
	
	private static final String						EMPTY_FOLDER			= "(empty)";
	
	/**
	 * Logger
	 */
	private static Log								s_logger				= LogFactory
																					.getLog ( RssResourceImpl.class );
	
	/**
	 * Changes listeners list
	 */
	private ArrayList < RssUpdateListener >			m_listeners				= new ArrayList < RssUpdateListener > ( );
	
	/**
	 * Changes listeners list
	 */
	private Map < String, FetcherEventListener >	m_fetcherListenersMap	= new HashMap < String, FetcherEventListener > ( );
	
	/**
	 * Pool
	 */
	private ExecutorService							m_pool					= Executors
																					.newFixedThreadPool ( POOL_SIZE );
	
	/**
	 * Current executions
	 */
	private ArrayList < Future >					m_currentExecutions		= new ArrayList < Future > ( );
	
	private ComponentContext						m_componentContext		= null;
	
	private JAXBContext								_jaxbContext			= null;
	
	private static final String						s_rssLoginPropertyKey	= "rss.login";
	
	private static final String						s_rssPassKey			= "rss.pass";
	
	private static final String						s_rssTmpDirKey			= "rss.tmp.dir";
	
	private String									m_login					= "netveh";
	
	private String									m_pass					= "pass4car";
	
	private String									m_tmpDir				= "/tmp/";
	
	private long									m_lastSyncTime			= 0L;
	
	private Map < String, SyndFeed >				m_feedsMap				= new HashMap < String, SyndFeed > ( );
	
	private String									m_command				= "/home/marta/workspace/rss.resource/rss.sh";
	
	/**
	 * Constructor, init folder list
	 */
	public RssResourceImpl ( )
	{
		initEmptyList ( );
	}
	
	private void initEmptyList ( )
	{
	}
	
	public void activate ( ComponentContext context )
	{
		this.m_componentContext = context;
		
		Runnable run = new Runnable ( )
			{
				
				@Override
				public void run ( )
				{
					Thread.currentThread ( ).setContextClassLoader (
							RssResourceImpl.class.getClassLoader ( ) );
					initJAXB ( );
					clearFeeds ( );
				}
			};
		new Thread ( run ).start ( );
		
		if ( m_componentContext.getProperties ( ).get ( "reader.command" ) != null )
		{
			m_command = String.valueOf ( m_componentContext.getProperties ( )
					.get ( "reader.command" ) );
		}
	}
	
	/**
	 * Init jaxb
	 */
	private void initJAXB ( )
	{
		try
		{
			String opmlPackage = opml.ObjectFactory.class.getPackage ( )
					.toString ( );
			opmlPackage = opmlPackage.substring ( opmlPackage
					.indexOf ( "package " )
					+ "package ".length ( ) );
			_jaxbContext = JAXBContext.newInstance ( opmlPackage, this
					.getClass ( ).getClassLoader ( ) );
		}
		catch ( JAXBException e )
		{
			s_logger.error ( e.getMessage ( ) );
		}
	}
	
	/**
	 * Clear feeds
	 */
	private void clearFeeds ( )
	{
		m_fetcherListenersMap.clear ( );
		m_feedsMap.clear ( );
	}
	
	protected void deactivate ( ComponentContext context )
	{
		this.m_componentContext = null;
	}
	
	/**
	 * Bind rss listener
	 * 
	 * @param listener
	 */
	public void bindRssListener ( RssUpdateListener listener )
	{
		// Add to listeners list
		if ( listener != null )
		{
			this.m_listeners.add ( listener );
			listener.feedUpdated ( m_feedsMap );
		}
	}
	
	/**
	 * Unbind rss listener
	 * 
	 * @param listener
	 */
	public void unbindRssListener ( RssUpdateListener listener )
	{
		if ( listener != null )
			this.m_listeners.remove ( listener );
	}
	
	@Override
	public void synchronizeFeeds ( )
	{
		s_logger.info ( "Synchronize all RSS" );
		
		// Stop previous executions
		stopAllRemainingThreads ( );
		
		// Sync feeds
		Runnable processOPML = new Runnable ( )
			{
				
				@Override
				public void run ( )
				{
					// String opml = downloadFile ( url );
					processOPML ( m_login, m_pass );
				}
			};
		try
		{
			m_currentExecutions.add ( m_pool.submit ( processOPML ) );
		}
		catch ( RejectedExecutionException e )
		{
			s_logger.error ( "Rejected Execution " + m_login, e );
		}
	}
	
	/**
	 * Stop all remaining threads
	 */
	private void stopAllRemainingThreads ( )
	{
		// Stop all threads
		for ( Future future : m_currentExecutions )
		{
			future.cancel ( true );
		}
		m_currentExecutions.clear ( );
	}
	
	@Override
	public void updated ( final Dictionary properties )
			throws ConfigurationException
	{
		if ( properties == null )
		{
			// no configuration from configuration admin
			// or old configuration has been deleted
			// ServiceReference configAdminServiceRef = m_componentContext
			// .getBundleContext ( ).getServiceReference (
			// ConfigurationAdmin.class.getName ( ) );
			//			
			// if ( configAdminServiceRef != null )
			// {
			// ConfigurationAdmin configAdmin = ( ConfigurationAdmin )
			// m_componentContext
			// .getBundleContext ( ).getService (
			// configAdminServiceRef );
			//				
			// Configuration config = configAdmin.getConfiguration (
			// "greetings_servlet_service",
			// getConfigurableBundleLocation ( ) );
			// Dictionary propertiesConf = config.getProperties ( );
			// if ( propertiesConf == null )
			// {
			// propertiesConf = new Properties ( );
			// }
			// propertiesConf.put ( "servletAlias", "/greetings" );
			// config.update ( properties );
			// }
			
		}
		
		// Configuration retrieved
		else
		{
			boolean update = false;
			if ( properties.get ( s_rssLoginPropertyKey ) != null )
			{
				m_login = String.valueOf ( properties
						.get ( s_rssLoginPropertyKey ) );
			}
			else if ( m_componentContext != null )
			{
				m_login = String.valueOf ( m_componentContext.getProperties ( )
						.get ( s_rssLoginPropertyKey ) );
				properties.put ( s_rssLoginPropertyKey, m_login );
				update = true;
			}
			
			if ( properties.get ( s_rssPassKey ) != null )
			{
				m_pass = String.valueOf ( properties.get ( s_rssPassKey ) );
			}
			else if ( m_componentContext != null )
			{
				m_pass = String.valueOf ( m_componentContext.getProperties ( )
						.get ( s_rssPassKey ) );
				properties.put ( s_rssPassKey, m_pass );
				update = true;
			}
			
			if ( properties.get ( s_rssPassKey ) != null )
			{
				m_tmpDir = String.valueOf ( properties.get ( s_rssTmpDirKey ) );
			}
			else if ( m_componentContext != null )
			{
				m_tmpDir = String.valueOf ( m_componentContext.getProperties ( )
						.get ( s_rssPassKey ) );
				properties.put ( s_rssTmpDirKey, m_tmpDir );
				update = true;
			}
			
			// Store default values in configuration
			if ( update )
			{
				Runnable runnable = new Runnable ( )
					{
						
						@Override
						public void run ( )
						{
							updateConfiguration ( properties );
						}
					};
				new Thread ( runnable ).start ( );
			}
			
			synchronizeFeeds ( );
		}
	}
	
	private void updateConfiguration ( Dictionary properties )
	{
		if ( m_componentContext != null
				&& Boolean.valueOf ( String.valueOf ( properties
						.get ( "rss.update_conf" ) ) ) )
		{
			ServiceReference configAdminServiceRef = m_componentContext
					.getBundleContext ( ).getServiceReference (
							ConfigurationAdmin.class.getName ( ) );
			
			if ( configAdminServiceRef != null )
			{
				ConfigurationAdmin configAdmin = ( ConfigurationAdmin ) m_componentContext
						.getBundleContext ( ).getService (
								configAdminServiceRef );
				
				Configuration config;
				try
				{
					config = configAdmin.getConfiguration ( String
							.valueOf ( properties.get ( "service.pid" ) ),
							m_componentContext.getBundleContext ( )
									.getBundle ( ).getLocation ( ) );
					config.update ( properties );
					
				}
				catch ( Exception e )
				{
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	protected void processOPML ( final String login, String pass )
	{
		try
		{
			String line = m_command + " " + login + " " + pass;
			CommandLine commandLine = CommandLine.parse ( line );
			DefaultExecutor executor = new DefaultExecutor ( );
			executor.setExitValue ( 1 );
			ExecuteWatchdog watchdog = new ExecuteWatchdog ( 60000 );
			executor.setWatchdog ( watchdog );
			executor.setExitValue ( 0 );
			executor.execute ( commandLine, new ExecuteResultHandler ( )
				{
					
					@Override
					public void onProcessFailed ( ExecuteException arg0 )
					{
						s_logger.error ( "execution failed:" + login, arg0 );
						
					}
					
					@Override
					public void onProcessComplete ( int arg0 )
					{
						if ( _jaxbContext != null )
						{
							try
							{
								Unmarshaller unmarshaller = _jaxbContext
										.createUnmarshaller ( );
								Opml request = ( Opml ) unmarshaller
										.unmarshal ( new File (
												"/tmp/subscriptions.xml" )
												.toURI ( ).toURL ( ) );
								s_logger.debug ( "Unmarshalled:" + request );
								
								if ( request != null )
								{
									m_lastSyncTime = System
											.currentTimeMillis ( );
									clearFeeds ( );
									updateFeeds ( request );
								}
							}
							catch ( JAXBException e )
							{
								s_logger.error ( e );
							}
							catch ( MalformedURLException e )
							{
								s_logger.error ( e );
							}
						}
					}
				} );
		}
		catch ( ExecuteException e )
		{
			s_logger.error ( e );
		}
		catch ( IOException e )
		{
			s_logger.error ( e );
		}
	}
	
	/**
	 * Update feeds from OPML reference
	 * 
	 * @param opml
	 */
	private void updateFeeds ( Opml opml )
	{
		Body body = opml.getBody ( );
		if ( body != null )
		{
			
			// Clean removed feeds
			ArrayList < String > opmlKeys = new ArrayList < String > ( );
			opmlKeys.add ( EMPTY_FOLDER );
			for ( Outline outline : body.getOutline ( ) )
			{
				if ( outline.getOutline ( ).size ( ) > 0 )
				{
					String text = outline.getText ( );
					opmlKeys.add ( text );
				}
			}
			cleanRemovedFeeds ( opmlKeys );
			
			// Update feeds
			for ( Outline outline : body.getOutline ( ) )
			{
				if ( outline.getOutline ( ).size ( ) > 0 )
				{
					String text = outline.getText ( );
					if ( !m_fetcherListenersMap.containsKey ( text ) )
					{
						m_fetcherListenersMap.put ( text,
								new FetcherEventListener ( text, this ) );
					}
					for ( Outline outline2 : outline.getOutline ( ) )
					{
						processFeed ( outline2, m_fetcherListenersMap
								.get ( text ) );
					}
				}
				else
				{
					// Create listener for empty folder
					if ( !m_fetcherListenersMap.containsKey ( EMPTY_FOLDER ) )
					{
						m_fetcherListenersMap
								.put ( EMPTY_FOLDER, new FetcherEventListener (
										EMPTY_FOLDER, this ) );
					}
					processFeed ( outline, m_fetcherListenersMap
							.get ( EMPTY_FOLDER ) );
				}
			}
		}
		
	}
	
	/**
	 * Clean any removed feeds not used so far
	 * 
	 * @param opmlKeys
	 */
	private void cleanRemovedFeeds ( ArrayList < String > opmlKeys )
	{
		for ( String key : m_fetcherListenersMap.keySet ( ) )
		{
			if ( !opmlKeys.contains ( key ) )
			{
				m_fetcherListenersMap.remove ( key );
				m_feedsMap.remove ( key );
			}
		}
	}
	
	private void processFeed ( final Outline outline,
			final FetcherEventListener fetcherEventListener )
	{
		if ( outline.getOutline ( ).size ( ) > 0 )
		{
			for ( Outline outline2 : outline.getOutline ( ) )
			{
				processFeed ( outline2, fetcherEventListener );
			}
		}
		else
		{
			Runnable fetcher = new Runnable ( )
				{
					
					@Override
					public void run ( )
					{
						FeedReaderImpl.fetch ( outline.getXmlUrl ( ),
								fetcherEventListener );
					}
				};
			
			try
			{
				m_currentExecutions.add ( m_pool.submit ( fetcher ) );
			}
			catch ( RejectedExecutionException e )
			{
				s_logger.error ( "Rejected Execution " + outline.getUrl ( ), e );
			}
		}
	}
	
	@Override
	public void feedRetrieved ( SyndFeed feed )
	{
		m_feedsMap.put ( feed.getTitle ( ), feed );
		
		synchronized ( m_listeners )
		{
			Map < String, SyndFeed > map = Collections
					.unmodifiableMap ( m_feedsMap );
			
			for ( RssUpdateListener listener : m_listeners )
			{
				listener.feedUpdated ( map );
			}
			
		}
		
	}
	// /**
	// * Download file
	// *
	// * @param url
	// * @return the file contents
	// */
	// public String downloadFile ( String url )
	// {
	//		
	// // -----------------------------------------------------//
	// // Step 1: Start creating a few objects we'll need.
	// // -----------------------------------------------------//
	//		
	// URL u = null;
	// InputStream is = null;
	// DataInputStream dis = null;
	// String s = "";
	// StringBuffer contents = new StringBuffer ( "" );
	// try
	// {
	//			
	// // ------------------------------------------------------------//
	// // Step 2: Create the URL. //
	// // ------------------------------------------------------------//
	// // Note: Put your real URL here, or better yet, read it as a //
	// // command-line arg, or read it from a file. //
	// // ------------------------------------------------------------//
	//			
	// u = new URL ( url );
	//			
	// // ----------------------------------------------//
	// // Step 3: Open an input stream from the url. //
	// // ----------------------------------------------//
	//			
	// is = u.openStream ( ); // throws an IOException
	//			
	// // -------------------------------------------------------------//
	// // Step 4: //
	// // -------------------------------------------------------------//
	// // Convert the InputStream to a buffered DataInputStream. //
	// // Buffering the stream makes the reading faster; the //
	// // readLine() method of the DataInputStream makes the reading //
	// // easier. //
	// // -------------------------------------------------------------//
	//			
	// dis = new DataInputStream ( new BufferedInputStream ( is ) );
	//			
	// // ------------------------------------------------------------//
	// // Step 5: //
	// // ------------------------------------------------------------//
	// // Now just read each record of the input stream, and print //
	// // it out. Note that it's assumed that this problem is run //
	// // from a command-line, not from an application or applet. //
	// // ------------------------------------------------------------//
	//			
	// while ( ( s = dis.readLine ( ) ) != null )
	// {
	// contents.append ( s );
	// }
	//			
	// }
	// catch ( MalformedURLException mue )
	// {
	//			
	// s_logger.error ( "MalformedURLException happened:" + url );
	// mue.printStackTrace ( );
	// System.exit ( 1 );
	//			
	// }
	// catch ( IOException ioe )
	// {
	//			
	// s_logger.error ( "IOException happened " + url );
	// ioe.printStackTrace ( );
	// System.exit ( 1 );
	//			
	// }
	// finally
	// {
	//			
	// // ---------------------------------//
	// // Step 6: Close the InputStream //
	// // ---------------------------------//
	//			
	// try
	// {
	// is.close ( );
	// }
	// catch ( IOException ioe )
	// {
	// // just going to ignore this one
	// }
	//			
	// } // end of 'finally' clause
	// return contents.toString ( )
	// }
}
