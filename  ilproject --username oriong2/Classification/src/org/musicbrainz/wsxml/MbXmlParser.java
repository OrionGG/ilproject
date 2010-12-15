package org.musicbrainz.wsxml;

import java.io.InputStream;

import org.musicbrainz.Query;
import org.musicbrainz.wsxml.element.Metadata;
import org.musicbrainz.wsxml.impl.JDOMParser;


/**
 * A simple parser interface to parse XML responses of the MusicBrainz's
 * web service. The {@link Query} class uses {@link JDOMParser} as a default
 * {@link MbXmlParser} implementation. However, you can write your own
 * implementation and inject it to the {@link Query} using particular
 * constructors, e.g. {@link Query#Query(MbXmlParser)}.
 * 
 * @see Metadata
 * @author Patrick Ruhkopf
 */
public interface MbXmlParser {
	
	/**
	 * Parses xml from the input stream and returns a populated
	 * {@link Metadata} object.
	 * 
	 * @param inputStream The xml stream
	 * @return A populated {@link Metadata} object.
	 * 
	 * @throws MbXMLException A {@link MbXMLParseException} is thrown 
	 * if the xml could not be parsed. In all other exceptional cases
	 * a general {@link MbXMLException} is thrown. 
	 */
	public Metadata parse(InputStream inputStream) throws MbXMLException;
}
