package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.itsnat.core.domutil.ItsNatDOMUtil;
import org.osgi.service.component.ComponentContext;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

import es.tid.networkedvehicle.drive.platform.hmi.gui.BaseDocument;
import es.tid.networkedvehicle.drive.platform.hmi.service.HMIService;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.ApplicationException;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.EscapeChars;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.GenericHMIEventHandler;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.GenericHMIProvider;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.HMIApplicationInfo;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.HMIListener;

/**
 * Clase que implementa la aplicacion RSS
 * 
 * @author jorgev
 * 
 */

public class RssViewHMIProvider extends GenericHMIProvider
{
	
	private static final String			BUTTON_HTML					= "gui/button.html";
	
	private static final String			ITEMS_XHTML					= "gui/items.xhtml";
	
	private static final String			FEEDS_XHTML					= "gui/feeds.xhtml";
	
	private static final String			RSS_XHTML					= "gui/rss.xhtml";
	
	private static final String			ICON_PATH					= "gui/rss.png";
	
	private static final String			DOWNLOADING_HTML			= "<p style=\"font-size: 20px;font-weight:bold;color: white;text-align: center\"><br/><br/>Downloading.... <br/><br/><img src='/resource/gui/images/bar-loader.gif'/></p>";
	
	private Map < String, SyndFeed >	m_feedsMap					= new HashMap < String, SyndFeed > ( );
	
	private BaseDocument				m_contentDoc				= null;
	
	private BaseDocument				m_buttonDoc					= null;
	
	private volatile boolean			m_updating					= false;
	
	private TimerTask					m_enqueuedFeedsUpdate		= null;
	
	private Timer						m_timer						= new Timer (
																			true );
	
	private TimerTask					m_enqueuedFeedDataUpdate	= null;
	
	private Element						m_contentElement			= null;
	
	/**
	 * Constructor
	 * 
	 */
	public RssViewHMIProvider ( HMIApplicationInfo applicationInfo,
			ComponentContext componentContext, HMIService service )
	{
		super ( applicationInfo, componentContext, service );
		RssViewHMIEventHandler handler = ( RssViewHMIEventHandler ) getHMIEventHandler ( );
	}
	
	@Override
	protected GenericHMIEventHandler initHMIEventListener ( HMIListener listener )
	{
		return new RssViewHMIEventHandler ( listener );
	}
	
	public void loadFeedsHMI ( )
	{
		/*if ( !m_updating )
		{
			
			m_updating = true;
			if ( m_contentDoc != null )
			{
				m_contentDoc.update ( new Runnable ( )
					{
						
						@Override
						public void run ( )
						{
							try
							{
								loadFeedsHMIImpl ( );
							}
							catch ( Exception e )
							{
								getLogger ( ).error ( e );
								e.printStackTrace ( );
							}
							m_updating = false;
						}
					} );
			}
		}
		else if ( m_enqueuedFeedsUpdate == null )
		{
			m_enqueuedFeedsUpdate = new TimerTask ( )
				{
					
					@Override
					public void run ( )
					{
						m_enqueuedFeedsUpdate = null;
						loadFeedsHMI ( );
					}
				};
			
			m_timer.schedule ( m_enqueuedFeedsUpdate, 2000 );
		}
		*/
	}
	
	public void loadFeedEntriesHMI ( final String feed )
	{
		getLogger ( ).debug ( "updating?" + m_updating );
		if ( !m_updating )
		{
			
			m_updating = true;
			if ( m_contentDoc != null )
			{
				m_contentDoc.update ( new Runnable ( )
					{
						
						@Override
						public void run ( )
						{
							createFeedItems ( feed );
							m_updating = false;
						}
					} );
			}
		}
		else if ( m_enqueuedFeedDataUpdate == null )
		{
			m_enqueuedFeedDataUpdate = new TimerTask ( )
				{
					
					@Override
					public void run ( )
					{
						m_enqueuedFeedsUpdate = null;
						loadFeedEntriesHMI ( feed );
					}
				};
			
			m_timer.schedule ( m_enqueuedFeedDataUpdate, 10000 );
		}
	}
	
	/**
	 * Update Feeds screen
	 * 
	 * @param map
	 * @throws ApplicationException
	 */
	public void updateFeedsScreen ( Map < String, SyndFeed > map )
			throws ApplicationException
	{
		m_feedsMap = map;
		// loadFeedsHMI ( );
	}
	
	private void loadFeedsHMIImpl ( )
	{
		if ( m_contentDoc != null && m_contentElement != null )
		{
			Document doc = m_contentDoc.getDocument ( );
			if ( m_contentElement.getFirstChild ( ) != null )
				m_contentElement.removeChild ( m_contentElement
						.getFirstChild ( ) );
			
			Map < String, SyndFeed > map = Collections
					.unmodifiableMap ( m_feedsMap );
			
			String newId = "" + System.currentTimeMillis ( );
			
			Element items = doc.createElement ( "div" );
			items.setAttribute ( "id", newId );
			items.setAttribute ( "class", "list_0_items items" );
			m_contentElement.appendChild ( items );
			
			// Create list
			if ( map.size ( ) == 0 )
			{
				items.appendChild ( m_contentDoc.toDom ( DOWNLOADING_HTML ) );
			}
			else
			{
				int i = 0;
				Element row = null;
				for ( String key : map.keySet ( ) )
				{
					if ( i % 2 == 0 )
					{
						row = doc.createElement ( "div" );
						row.setAttribute ( "class", "list_0_row" );
						row
								.setAttribute (
										"onmouseover",
										"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_0_hover.png'" );
						row
								.setAttribute (
										"onmouseout",
										"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_0.png'" );
						items.appendChild ( row );
						
						Element imgBG = doc.createElement ( "img" );
						imgBG.setAttribute ( "class", "item_bg_0" );
						imgBG.setAttribute ( "src",
								"/resource/gui/images/button_list_0.png" );
						imgBG
								.setAttribute (
										"onmouseover",
										"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_0_hover.png'" );
						imgBG
								.setAttribute (
										"onmouseout",
										"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_0.png'" );
						row.appendChild ( imgBG );
					}
					SyndFeed syndFeed = map.get ( key );
					createFeed ( syndFeed, i, row );
					i++;
				}
			}
			
			// Reload scroll
			m_contentDoc.addCodeToSendAsync ( "setTimeout('init_scroll()',1000);" );
		}
	}
	
	private Element createFeed ( SyndFeed syndFeed, int i, Element row )
	{
		// String text = EscapeChars.forHTML ( syndFeed.getTitle ( ) );
		String text = syndFeed.getTitle ( );
		text = text.replaceAll ( "\\<.*?>", "" );
		Element imageElement = null;
		if ( syndFeed.getImage ( ) != null )
		{
			String image = syndFeed.getImage ( ).getUrl ( );
			imageElement = createFeedImage ( image );
		}
		else
		{
			imageElement = createFeedImage ( "/resource/gui/images/periodico.jpg" );
		}
		
		Element item = m_contentDoc.getDocument ( ).createElement ( "a" );
		item.setTextContent ( text );
		item.setAttribute ( "href", "#" );
		item.setAttribute ( "rel", "nofollow" );
		
		if ( i % 2 == 0 )
		{
			item.setAttribute ( "class", "list_0_left" );
			row.appendChild ( item );
			if ( imageElement != null )
			{
				imageElement.setAttribute ( "class", "list_0_left_image" );
				row.appendChild ( imageElement );
			}
		}
		else
		{
			item.setAttribute ( "class", "list_0_right" );
			if ( imageElement != null )
			{
				imageElement.setAttribute ( "class", "list_0_right_image" );
				row.appendChild ( imageElement );
			}
			row.appendChild ( item );
		}
		
		final String id = EscapeChars.forHTML ( syndFeed.getTitle ( ) );
		getLogger ( ).info ( "Inserting feed: " + id );
		EventListener clickListener = new EventListener ( )
			{
				public void handleEvent ( Event evt )
				{
					getListener ( ).fireEvent ( RssEvents.SELECT_FEED, id );
				}
			};
		( ( EventTarget ) item ).addEventListener ( "click", clickListener,
				false );
		if ( imageElement != null )
		{
			( ( EventTarget ) imageElement ).addEventListener ( "click",
					clickListener, false );
		}
		
		return row;
	}
	
	private Element createFeedImage ( String imagePath )
	{
		Element imageImg = m_contentDoc.getDocument ( ).createElement ( "img" );
		imageImg.setAttribute ( "class", "list_0_image" );
		imageImg.setAttribute ( "src", imagePath );
		return imageImg;
	}
	
	/**
	 * Screen for the feed items
	 * 
	 * @param feedTitle
	 */
	private synchronized void createFeedItems ( String feedTitle )
	{
		getLogger ( ).debug ( "create feed items " + feedTitle );
		Document doc = m_contentDoc.getDocument ( );
		Element items = doc.getElementById ( "items" );
		ItsNatDOMUtil.removeAllChildren ( items );
		
		Map < String, SyndFeed > map = Collections
				.unmodifiableMap ( m_feedsMap );
		
		String newId = "" + System.currentTimeMillis ( );
		
		// Create list
		SyndFeed feed = map.get ( feedTitle );
		if ( feed != null )
		{
			// Create entries
			for ( Object obj : feed.getEntries ( ) )
			{
				SyndEntry entry = ( SyndEntry ) obj;
				final String textId = "item_"
						+ String.valueOf ( entry.hashCode ( ) );
				DocumentFragment entryElement = createEntry ( entry, textId );
				items.appendChild ( entryElement );
				getLogger ( ).info ( "Inserting item: " + entry.getTitle ( ) );
				// EventListener clickListener = new EventListener ( )
				// {
				// public void handleEvent ( Event evt )
				// {
				// m_contentDoc.addCodeToSendSync ( "slideText('"
				// + textId + "');" );
				// }
				// };
				// ( ( EventTarget ) entryElement ).addEventListener ( "click",
				// clickListener, false );
			}
		}
		else
		{
			items.appendChild ( m_contentDoc.toDom ( DOWNLOADING_HTML ) );
		}
		
		// Reload scroll
		m_contentDoc.addCodeToSendAsync ( "setTimeout('init_scroll()',5000);" );
	}
	
	private DocumentFragment createEntry ( SyndEntry entry, String textId )
	{
		// String title = EscapeChars.forHTML ( entry.getTitle ( ) );
		// String date = EscapeChars.forHTML ( entry.getPublishedDate ( )
		// .toString ( ) );
		// String text = EscapeChars.forHTML ( entry.getDescription ( )
		// .getValue ( ) );
		String title = entry.getTitle ( );
		String date = entry.getPublishedDate ( ).toString ( );
		String text = entry.getDescription ( ).getValue ( );
		text = text.replaceAll ( "\\<.*?>", "" );
		
		title = title.replaceAll ( "href", "href_disabled" );
		text = text.replaceAll ( "href", "href_disabled" );
		String html = "<div class=\"list_1_row\" onmouseover=\"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_1_hover.png'\""
				+ "onmouseout=\"this.getElementsByTagName('img')[0].src='/resource/gui/images/button_list_1.png'\">"
				+ "<div class=\"item\" onclick=\"slideText('"
				+ textId
				+ "');\">"
				+ "<span class=\"itemtitle\">"
				+ title
				+ "</span><br></br>"
				+ "<span class=\"itemdate\">"
				+ date
				+ "</span>"
				+ "<img class=\"item_bg\" src=\"/resource/gui/images/button_list_1.png\""
				+ "onmouseover=\"this.src='/resource/gui/images/button_list_1_hover.png'\""
				+ "onmouseout=\"this.src='/resource/gui/images/button_list_1.png'\"/>"
				+ "</div>"
				+ "<span class=\"itemtext\" id=\""
				+ textId
				+ "\">"
				+ text + "</span>" + "</div>";
		// Element item = m_contentDoc.getDocument ( ).createElement ( "div" );
		// Element titleE = m_contentDoc.getDocument ( ).createElement ( "p" );
		// titleE.appendChild ( m_contentDoc.getDocument ( ).createTextNode (
		// title ) );
		// titleE.setAttribute ( "class", "itemtitle" );
		// Element dateE = m_contentDoc.getDocument ( ).createElement ( "p" );
		// dateE
		// .appendChild ( m_contentDoc.getDocument ( ).createTextNode (
		// date ) );
		// dateE.setAttribute ( "class", "itemdate" );
		// Element textE = m_contentDoc.getDocument ( ).createElement ( "p" );
		// textE
		// .appendChild ( m_contentDoc.getDocument ( ).createTextNode (
		// text ) );
		// textE.setAttribute ( "class", "itemtext" );
		// textE.setAttribute ( "id", textId );
		// item.appendChild ( titleE );
		// item.appendChild ( dateE );
		// item.appendChild ( textE );
		DocumentFragment fragment = m_contentDoc.toDom ( html );
		return fragment;
	}
	
	@Override
	public void bringToFront ( ) throws ApplicationException
	{
		setEnabled ( true );
		super.bringToFront ( );
	}
	
	@Override
	public void dispose ( )
	{
		setEnabled ( false );
	}
	
	@Override
	public void disposeAppButton ( BaseDocument doc )
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disposeContent ( BaseDocument doc )
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disposeStatus ( BaseDocument doc )
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getAppButtonPath ( )
	{
		return BUTTON_HTML;
	}
	
	@Override
	public String getContentPath ( )
	{
		return RSS_XHTML;
	}
	
	@Override
	public String getStatusPath ( )
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void loadAppButton ( BaseDocument doc )
	{
		m_buttonDoc = doc;
	}
	
	@Override
	public void loadContent ( BaseDocument doc )
	{
		m_contentDoc = doc;
		
		Element back = doc.getDocument ( ).getElementById ( "back" );
		if ( back != null )
		{
			EventListener clickListener = new EventListener ( )
				{
					public void handleEvent ( Event evt )
					{
						sendBackEvent ( );
					}
				};
			( ( EventTarget ) back ).addEventListener ( "click", clickListener,
					false );
		}
		
		// Element prev = doc.getDocument ( ).getElementById ( "prev" );
		// if ( prev != null )
		// {
		// EventListener clickListener = new EventListener ( )
		// {
		// public void handleEvent ( Event evt )
		// {
		// prevPressed ( );
		// }
		// };
		// ( ( EventTarget ) prev ).addEventListener ( "click", clickListener,
		// false );
		// }
		//		
		// Element next = doc.getDocument ( ).getElementById ( "next" );
		// if ( next != null )
		// {
		// EventListener clickListener = new EventListener ( )
		// {
		// public void handleEvent ( Event evt )
		// {
		// nextPressed ( );
		// }
		// };
		// ( ( EventTarget ) next ).addEventListener ( "click", clickListener,
		// false );
		// }
		
		m_contentElement = ItsNatDOMUtil.getElementById ( "list_container", doc
				.getDocument ( ) );
		if ( getListener ( ) != null )
			getListener ( ).refresh ( );
	}
	
	// protected void nextPressed ( )
	// {
	// Element items = ( Element ) m_contentElement.getFirstChild ( );
	// if ( items != null )
	// {
	// ElementCSSInlineStyle styleElem = ( ElementCSSInlineStyle ) items;
	// CSSStyleDeclaration cssDec = ( CSSStyleDeclaration ) styleElem
	// .getStyle ( );
	// String currentTop = cssDec.getPropertyValue ( "top" );
	// try
	// {
	// int top = Integer.parseInt ( currentTop );
	// top += 100;
	// cssDec.setProperty ( "top", "" + top + "px", "" );
	// }
	// catch ( Exception e )
	// {
	// getLogger ( ).error ( e );
	// }
	// }
	// }
	//	
	// protected void prevPressed ( )
	// {
	// Element items = ( Element ) m_contentElement.getFirstChild ( );
	// if ( items != null )
	// {
	// ElementCSSInlineStyle styleElem = ( ElementCSSInlineStyle ) items;
	// CSSStyleDeclaration cssDec = ( CSSStyleDeclaration ) styleElem
	// .getStyle ( );
	// String currentTop = cssDec.getPropertyValue ( "top" );
	// try
	// {
	// int top = Integer.parseInt ( currentTop );
	// top = 100;
	// m_contentDoc.addCodeToSendSync ( "seek ('"
	// + items.getAttribute ( "id" ) + "', 2000, 10, "
	// + currentTop + ", " + top + ")" );
	// cssDec.setProperty ( "top", "" + top + "px", "" );
	// }
	// catch ( Exception e )
	// {
	// getLogger ( ).error ( e );
	// }
	// }
	//		
	// }
	
	@Override
	public void loadStatus ( BaseDocument doc )
	{
		// TODO Auto-generated method stub
		
	}
	
	private void sendBackEvent ( )
	{
		if ( getListener ( ) != null )
			getListener ( ).fireEvent ( RssEvents.BACK );
	}
	
	@Override
	public String getIconPath ( )
	{
		return ICON_PATH;
	}
	
	@Override
	public String getAppType ( )
	{
		return "rss";
	}
}
