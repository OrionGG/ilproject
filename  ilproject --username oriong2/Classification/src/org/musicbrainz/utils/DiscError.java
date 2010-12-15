package org.musicbrainz.utils;

import org.musicbrainz.JMBWSException;

/**
 * The Audio CD could not be read.
 * 
 * This may be simply because no disc was in the drive, the device name
 * was wrong or the disc can't be read. Reading errors can occur in case
 * of a damaged disc or a copy protection mechanism, for example.
 * 
 * @author Patrick Ruhkopf
 */
public class DiscError extends JMBWSException {

	public DiscError() {
		super();
	}

	public DiscError(String message, Throwable cause) {
		super(message, cause);
	}

	public DiscError(String message) {
		super(message);
	}

	public DiscError(Throwable cause) {
		super(cause);
	}
}
