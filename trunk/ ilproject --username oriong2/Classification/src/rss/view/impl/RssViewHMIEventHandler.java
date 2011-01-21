package es.tid.networkedvehicle.drive.applications.rss.view.impl;

import es.tid.networkedvehicle.drive.platform.hmi.model.message.FormElementType;
import es.tid.networkedvehicle.drive.platform.hmi.model.message.FormType;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.GenericHMIEventHandler;
import es.tid.networkedvehicle.drive.platform.scheduler.generic.application.hmi.HMIListener;

public class RssViewHMIEventHandler extends GenericHMIEventHandler
{
	
	public RssViewHMIEventHandler ( HMIListener listener )
	{
		super ( listener );
	}
	
	/**
	 * Handle form event
	 * 
	 * @param form
	 *            Form content
	 */
	@SuppressWarnings ( "unchecked" )
	@Override
	public void handleFormEvent ( FormType form )
	{
		
		HMIListener listener = getListener ( );
		if ( listener != null )
		{
			
			String scxmlEvent = form.getFormAction ( );
			
			getLogger ( ).debug ( scxmlEvent );
			
			// Feed selected
			if ( scxmlEvent.equals ( RssEvents.SELECT_FEED ) )
			{
				FormElementType element = null;
				if ( ( element = form
						.getFormElementTypeByKey ( RssViewConstants.HMI_FEED_PROP ) ) != null )
				{
					// si no una carpeta
					String feedUri = element.getValue ( );
					if ( !feedUri.equals ( "" ) )
						listener.fireEvent ( scxmlEvent, feedUri );
				}
				
			}
			// Item selected
			else if ( scxmlEvent.equals ( RssEvents.SELECT_ITEM ) )
			{
				FormElementType element = null;
				if ( ( element = form
						.getFormElementTypeByKey ( RssViewConstants.HMI_ITEM_PROP ) ) != null )
				{
					// si no una carpeta
					String item = element.getValue ( );
					if ( !item.equals ( "" ) )
						listener.fireEvent ( scxmlEvent, item );
				}
				
			}
			// Fire action
			else
			{
				listener.fireEvent ( scxmlEvent );
			}
		}
	}
	
	@Override
	public void handleSelectionEvent ( String key )
	{
		HMIListener listener = getListener ( );
		if ( listener != null )
		{
			// if ( key.equals ( GUIReservedNames.BUTTON_APPBAR_UP ) )
			// {
			// listener.fireEvent ( RssEvents.PREVIOUS );
			// }
			// else if ( key.equals ( GUIReservedNames.BUTTON_APPBAR_DOWN ) )
			// {
			// listener.fireEvent ( RssEvents.NEXT );
			// }
		}
		
	}
}
