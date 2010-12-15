package org.musicbrainz;

/**
 * All application specific exceptions extend this class.
 * 
 * @author Patrick Ruhkopf
 */
public class JMBWSException extends Exception {

	public JMBWSException() {
		super();
	}

	public JMBWSException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMBWSException(String message) {
		super(message);
	}

	public JMBWSException(Throwable cause) {
		super(cause);
	}

}
