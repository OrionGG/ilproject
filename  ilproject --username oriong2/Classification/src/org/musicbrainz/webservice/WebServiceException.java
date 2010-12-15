package org.musicbrainz.webservice;

import org.musicbrainz.JMBWSException;

/**
 * A general web service exception
 * 
 * @author Patrick Ruhkopf
 */
public class WebServiceException extends JMBWSException 
{
	public WebServiceException() {
		super();
	}

	public WebServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebServiceException(String message) {
		super(message);
	}

	public WebServiceException(Throwable cause) {
		super(cause);
	}
}
