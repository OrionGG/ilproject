package org.musicbrainz.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.musicbrainz.webservice.filter.ReleaseFilter;

/**
 * <p>A release event, indicating where and when a release took place.</p>
 * 
 * <p>All country codes used must be valid ISO-3166 country codes (i.e. 'DE',
 * 'UK' or 'FR'). The dates are strings and must have the format 'YYYY',
 * 'YYYY-MM' or 'YYYY-MM-DD'.</p>
 * 
 * @author Patrick Ruhkopf
 */
public class ReleaseEvent 
{
	private Log log = LogFactory.getLog(ReleaseEvent.class);
	
	/**
	 * A string containing an ISO-3166 country code
	 */
	private String countryId;
	
	/**
	 * A string containing a date string
	 */
	private String dateStr;

	/**
	 * Default Constructor
	 */
	public ReleaseEvent()
	{
		
	}
	
	/**
	 * Minimal Constructor
	 * @param countryId A string containing an ISO-3166 country code
	 * @param dateStr A string containing a date string
	 */
	public ReleaseEvent(String countryId, String dateStr)
	{
		this.countryId = countryId;
		this.dateStr = dateStr;
	}

	/**
	 * Due to a server limitation, the web service does not
	 * return country IDs for release collection queries. This only
	 * affects the {@link org.musicbrainz.Query#getReleases(ReleaseFilter)} query.
	 * 
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the dateStr
	 */
	public String getDateStr() {
		return dateStr;
	}

	/**
	 * @param dateStr the dateStr to set
	 */
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	/**
	 * Parses the date string and returns a Date
	 * @return A Date object
	 */
	public Date getDate() {
		return dateForString();
	}
	
	private Date dateForString() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy");
		
		if (dateStr == null) 
			return null;
		
		if (dateStr.length() == 10) 
			f = new SimpleDateFormat("yyyy-MM-dd");
			
		if (dateStr.length() == 7) 
			f = new SimpleDateFormat("yyyy-MM");
			
		try {
			return f.parse(dateStr);
		} catch (ParseException e) {
			log.warn("Could not parse date string - returning null", e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReleaseEvent country=" + countryId + ", date=" + dateStr;
	}
	
	
}
