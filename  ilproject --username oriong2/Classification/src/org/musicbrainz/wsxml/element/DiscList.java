
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.Disc;


/**
 * A list of {@link Disc}s.
 * 
 * @see Disc
 * @author Patrick Ruhkopf
 */
public class DiscList extends ListElement{

    private List<Disc> discs = new ArrayList<Disc>();

	/**
	 * @return the discs
	 */
	public List<Disc> getDiscs() {
		return discs;
	}

	/**
	 * @param discs the discs to set
	 */
	public void setReleases(List<Disc> discs) {
		this.discs = discs;
	}
	
	/**
	 * Adds a disc to the list.
	 * 
	 * It will also create and set new ArrayList if
	 * {@link #discs} is null.
	 * 
	 * @param disc The disc to add
	 */
	public void addDisc(Disc disc) 
	{
		if (discs == null) {
			discs = new ArrayList<Disc>();
		}
		
		discs.add(disc);
	}
}
