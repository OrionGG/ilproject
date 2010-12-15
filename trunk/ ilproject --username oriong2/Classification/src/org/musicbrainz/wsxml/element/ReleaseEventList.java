
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.ReleaseEvent;


/**
 * A list of {@link ReleaseEvent}s.
 * 
 * @see ReleaseEvent
 * @author Patrick Ruhkopf
 */
public class ReleaseEventList extends ListElement{

	private List<ReleaseEvent> releaseEvents = new ArrayList<ReleaseEvent>();

	/**
	 * @return the release events
	 */
	public List<ReleaseEvent> getReleaseEvents() {
		return releaseEvents;
	}

	/**
	 * @param releaseEvents the releases events to set
	 */
	public void setReleaseEvents(List<ReleaseEvent> releaseEvents) {
		this.releaseEvents = releaseEvents;
	}
	
	/**
	 * Adds a release event to the list.
	 * 
	 * It will also create and set new ArrayList if
	 * {@link #releaseEvents} is null.
	 * 
	 * @param releaseEvent The release event to add
	 */
	public void addReleaseEvent(ReleaseEvent releaseEvent) 
	{
		if (releaseEvents == null) {
			releaseEvents = new ArrayList<ReleaseEvent>();
		}
		
		releaseEvents.add(releaseEvent);
	}
}
