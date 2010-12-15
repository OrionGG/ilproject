package org.musicbrainz.wsxml;

import org.musicbrainz.JMBWSException;

/**
 * This exception and its sublcasses are thrown by
 * {@link MbXmlParser} implementations.
 * 
 * @author Patrick Ruhkopf
 */
public class MbXMLException extends JMBWSException {

	public MbXMLException() {
		super();
	}

	public MbXMLException(String message, Throwable cause) {
		super(message, cause);
	}

	public MbXMLException(String message) {
		super(message);
	}

	public MbXMLException(Throwable cause) {
		super(cause);
	}
}
