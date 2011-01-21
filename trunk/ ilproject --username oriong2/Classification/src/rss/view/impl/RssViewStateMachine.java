package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.scxml.model.ModelException;
import org.xml.sax.SAXException;

import com.sun.syndication.feed.synd.SyndFeed;

import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.ApplicationException;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.GenericApplicationStateMachine;

/**
 * Clase que implementa la aplicacion RSS
 * 
 * @author isabel
 * 
 */

public class RssViewStateMachine extends GenericApplicationStateMachine
{
	
	/**
	 * HMI Provider
	 */
	protected RssViewHMIProvider	_hmiProvider	= null;
	
	/**
	 * Constructor
	 * 
	 * @throws ModelException
	 * @throws SAXException
	 * @throws IOException
	 */
	public RssViewStateMachine ( RssViewHMIProvider provider )
			throws IOException, SAXException, ModelException
	{
		super ( RssViewStateMachine.class.getClassLoader ( ).getResource (
				SCXML_URL ) );
		getLogger ( ).info ( "RSS VIEW CREATED" );
		this._hmiProvider = provider;
	}
	
	// Each method below is the activity corresponding to a state in the
	// SCXML document (see class constructor for pointer to the document).
	
	@Override
	public void init ( )
	{
		getLogger ( ).info ( "INIT" );
	}
	
	@Override
	public void idle ( )
	{
		getLogger ( ).info ( "IDLE" );
	}
	
	@Override
	public void die ( )
	{
		getLogger ( ).info ( "DIE" );
	}
	
	/**
	 * List state: show Rss folder list
	 * 
	 * @throws ApplicationException
	 */
	public void listFeeds ( ) throws ApplicationException
	{
		getLogger ( ).info ( "Folders list" );
		
		_hmiProvider.loadFeedsHMI ( );
	}
	
	/**
	 * Show a Rss feed
	 * 
	 * @throws ApplicationException
	 */
	public void readFeed ( ) throws ApplicationException
	{
		String feed = getSelectedUriFeed ( );
		getLogger ( ).info ( "readFeed " + feed );
		if ( feed != null )
		{
			_hmiProvider.loadFeedEntriesHMI ( feed );
		}
		else
		{
			fireEvent ( RssEvents.BACK );
		}
	}
	
	/**
	 * Error stare
	 */
	@Override
	public void errorHandler ( )
	{
		getLogger ( ).debug ( "Error" );
		
	}
	
	/**
	 * flow state
	 */
	@Override
	public void flow ( )
	{
		getLogger ( ).debug ( "Flow" );
		
	}
	
	/**
	 * interrupted state
	 */
	@Override
	public void interrupted ( )
	{
		getLogger ( ).debug ( "Interrupted" );
		
	}
	
	/**
	 * Get uri selected feed
	 * 
	 * @return SyndFeed selected by user
	 */
	private String getSelectedUriFeed ( )
	{
		if ( has ( RssViewConstants.RSS_FEED_SCXML_VAR ) )
		{
			String feedUri = String
					.valueOf ( get ( RssViewConstants.RSS_FEED_SCXML_VAR ) );
			
			return feedUri;
		}
		return null;
	}
	
	@Override
	public void dispose ( )
	{
		// TODO Auto-generated method stub
	}
	
	public void updateFeedsScreen ( Map < String, SyndFeed > map )
	{
		try
		{
			_hmiProvider.updateFeedsScreen ( map );
			readFeed ( );
		}
		catch ( ApplicationException e )
		{
			getLogger ( ).error ( e );
		}
		
		if ( "listFeeds".equals ( getCurrentStateId ( ) ) )
		{
			refresh ( );
		}
	}
}
