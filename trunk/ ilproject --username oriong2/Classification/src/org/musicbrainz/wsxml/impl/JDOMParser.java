package org.musicbrainz.wsxml.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.musicbrainz.model.Artist;
import org.musicbrainz.model.ArtistAlias;
import org.musicbrainz.model.Disc;
import org.musicbrainz.model.Entity;
import org.musicbrainz.model.Relation;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.ReleaseEvent;
import org.musicbrainz.model.Track;
import org.musicbrainz.model.User;
import org.musicbrainz.utils.MbUtils;
import org.musicbrainz.wsxml.MbXMLException;
import org.musicbrainz.wsxml.MbXMLParseException;
import org.musicbrainz.wsxml.MbXmlParser;
import org.musicbrainz.wsxml.element.ArtistResult;
import org.musicbrainz.wsxml.element.ArtistSearchResults;
import org.musicbrainz.wsxml.element.DiscList;
import org.musicbrainz.wsxml.element.ListElement;
import org.musicbrainz.wsxml.element.Metadata;
import org.musicbrainz.wsxml.element.RelationList;
import org.musicbrainz.wsxml.element.ReleaseEventList;
import org.musicbrainz.wsxml.element.ReleaseList;
import org.musicbrainz.wsxml.element.ReleaseResult;
import org.musicbrainz.wsxml.element.ReleaseSearchResults;
import org.musicbrainz.wsxml.element.TrackList;
import org.musicbrainz.wsxml.element.TrackResult;
import org.musicbrainz.wsxml.element.TrackSearchResults;
import org.musicbrainz.wsxml.element.UserList;

/**
 * <p>A parser for the Music Metadata XML format.</p>
 * 
 * <p>This parser supports all basic features and extensions defined by
 * MusicBrainz, including unlimited document nesting. By default it
 * reads an XML document from a stream and returns an object tree
 * representing the document using the flexible {@link Metadata}
 * class.</p>
 * 
 * <p>The implementation tries to be as permissive as possible. Invalid
 * contents are skipped, but documents have to be well-formed and using
 * the correct namespace. In case of unrecoverable errors, a 
 * {@link MbXMLParseException} exception is raised.</p>
 * 
 * @author Patrick Ruhkopf
 */
public class JDOMParser implements MbXmlParser {
	
	protected static final Namespace NS_MMD_1 = Namespace.getNamespace(Entity.NS_MMD_1);
	protected static final Namespace NS_EXT_1 = Namespace.getNamespace(Entity.NS_EXT_1);

	protected static final Pattern scriptPattern = Pattern.compile("^[A-Z][a-z]{3}$");
	protected static final Pattern languagePattern = Pattern.compile("^[A-Z]{3}$");
	protected static final Pattern countryPattern = Pattern.compile("^[A-Z]{2}$");
	protected static final Pattern directionPattern = Pattern.compile("^\\s*(" + Relation.DIR_BOTH + "|" + Relation.DIR_FORWARD + "|" + Relation.DIR_BACKWARD + ")\\s*$");

	private Log log = LogFactory.getLog(JDOMParser.class);
	
	/**
	 * Default constructor
	 */
	public JDOMParser() {
		
	}

	/* (non-Javadoc)
	 * @see org.musicbrainz.wsxml.MbXmlParser#parse(java.io.InputStream)
	 */
	public Metadata parse(InputStream inputStream) throws MbXMLException 
	{
		SAXBuilder builder = new SAXBuilder();
		try 
		{
			Document doc = builder.build(inputStream);
			
			Element root = doc.getRootElement();
			if ( (root == null) || (!NS_MMD_1.equals(root.getNamespace())) ||
					!"metadata".equals(root.getName())) {
				throw new MbXMLParseException("No root element with the name metadata in " + NS_MMD_1 + " found!");
			}
			
			return createMetadata(root);
		} 
		catch (Exception e) 
		{
			throw new MbXMLParseException("The xml could not be read!", e);
		}
	}
	
	protected Metadata createMetadata(Element elem) 
	{
		Metadata md = new Metadata();
		
		Iterator itr = elem.getChildren().iterator();
		Element node;
		while(itr.hasNext()) 
		{
		     node = (Element)itr.next();
		    
		     if ("artist".equals(node.getName())) {
		    	 md.setArtist(createArtist(node));
		     }
		     else if ("artist-list".equals(node.getName())) {
				addArtistResults(node, md.getArtistResults());
		     }
		     else if ("track".equals(node.getName())) {
		    	 md.setTrack(createTrack(node));
		     }
		     else if ("track-list".equals(node.getName())) {
		    	 addTrackResults(node, md.getTrackResults());
		     }
		     else if ("release".equals(node.getName())) {
		    	 md.setRelease(createRelease(node));
		     }
		     else if ("release-list".equals(node.getName())) {
		    	 addReleaseResults(node, md.getReleaseResults());
		     }
		     else if ("user-list".equals(node.getName()) && NS_EXT_1.equals(node.getNamespace())) {
		    	 addUserToList(node, md.getUserList());
		     }
	   }
	
		return md;
	}
	
	protected Artist createArtist(Element node) 
	{
		Artist artist = new Artist();
		artist.setId(MbUtils.convertIdToURI(node.getAttributeValue("id"), "artist") );
		artist.setType(MbUtils.convertTypeToURI(node.getAttributeValue("type"), Entity.NS_MMD_1));
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
		    
		     if ("name".equals(child.getName())) {
		    	 artist.setName(child.getText());
		     }
		     else if ("sort-name".equals(child.getName())) {
		    	 artist.setSortName(child.getText());
		     }
		     else if ("disambiguation".equals(child.getName())) {
		    	 artist.setDisambiguation(child.getText());
		     }
		     else if ("life-span".equals(child.getName())) {
		    	 artist.setBeginDate(child.getAttributeValue("begin"));
		    	 artist.setEndDate(child.getAttributeValue("end"));
		     }
		     else if ("alias-list".equals(child.getName())) {
		    	 addArtistAliases(child, artist);
		     }
		     else if ("release-list".equals(child.getName())) {
		    	 addReleasesToList(child, artist.getReleaseList());
		     }
		     else if ("relation-list".equals(child.getName())) {
				addRelationsToEntity(child, artist.getRelationList());
			}
		}
		
		return artist;
	}
	
	public Release createRelease(Element releaseNode) 
	{
		Release release = new Release();
		release.setId(MbUtils.convertIdToURI(releaseNode.getAttributeValue("id"), "release") );
		release.setTypeString((MbUtils.convertTypesToURI(releaseNode.getAttributeValue("type"), Entity.NS_MMD_1)));
		
		Iterator itr = releaseNode.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("title".equals(child.getName())) {
				release.setTitle(child.getText());
			}
			else if ("text-representation".equals(child.getName())) {
				release.setTextLanguage(getAttr(child, "language", languagePattern));
				release.setTextScript(getAttr(child, "script", scriptPattern));
			}
			else if ("asin".equals(child.getName())) {
				release.setAsin(child.getText());
			}
			else if ("artist".equals(child.getName())) {
				release.setArtist(createArtist(child));
			}
			else if ("release-event-list".equals(child.getName())) {
				addReleaseEventToList(child, release.getReleaseEventList());
			}
			else if ("disc-list".equals(child.getName())) {
				addDiscToList(child, release.getDiscList());
			}
			else if ("track-list".equals(child.getName())) {
				addTrackToList(child, release.getTrackList());
			}
			else if ("relation-list".equals(child.getName())) {
				addRelationsToEntity(child, release.getRelationList());
			}
		}
		
		return release;
	}

	protected ReleaseEvent createReleaseEvent(Element node) 
	{
		ReleaseEvent event = new ReleaseEvent();
		event.setCountryId(getAttr(node, "country", countryPattern));
		event.setDateStr(node.getAttributeValue("date"));
		return event;
	}
	
	protected Disc createDisc(Element node) 
	{
		Disc d = new Disc();
		d.setDiscId(node.getAttributeValue("id"));
		d.setSectors(getIntAttr(node, "sectors"));
		return d;
	}
	
	protected Track createTrack(Element node) 
	{
		Track t = new Track();
		t.setId(MbUtils.convertIdToURI(node.getAttributeValue("id"), "track") );
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("title".equals(child.getName())) {
				t.setTitle(child.getText());
			}
			else if ("artist".equals(child.getName())) {
				t.setArtist(createArtist(child));
			}
			else if ("duration".equals(child.getName())) 
			{
				String d = child.getValue();
				if (d != null) {
					try {
						Long l = Long.parseLong(d);
						t.setDuration(l);
					} catch (NumberFormatException e) {
						log.warn("Illegal duration value!", e);
					}
				}	
			}
			else if ("release-list".equals(child.getName())) {
				addReleasesToList(child, t.getReleaseList());
			}
			else if ("puid-list".equals(child.getName())) {
				addPuids(child, t);
			}
			else if ("relation-list".equals(child.getName())) {
				addRelationsToEntity(child, t.getRelationList());
			}
		}
		return t;
	}
	
	protected Relation createRelation(Element node, String targetType)
	{
		Relation relation = new Relation();
		relation.setType(MbUtils.convertTypeToURI(node.getAttributeValue("type"), Entity.NS_REL_1));
		relation.setTargetType(targetType);
		String resType = MbUtils.extractTypeFromURI(targetType);
		relation.setTargetId(MbUtils.convertIdToURI(node.getAttributeValue("target"), resType));
		
		if ( (relation.getType() == null) || (relation.getTargetType() == null)
		     || (relation.getTargetId() == null) ) {
			return null;
		}
		
		relation.setDirection(getAttr(node, "direction", directionPattern, Relation.DIR_BOTH, null));
		relation.setBeginDate(node.getAttributeValue("begin"));
		relation.setEndDate(node.getAttributeValue("end"));
		relation.setAttributes(getUriListAttr(node, "attributes", Entity.NS_REL_1));
		
		Entity target = null;
		if (node.getChildren().size() > 0) {
			Element child = (Element) node.getChildren().get(0);
			if ("artist".equals(child.getName())) {
				target = createArtist(child);
			}
			else if ("release".equals(child.getName())) {
				target = createRelease(child);
			}
			else if ("track".equals(child.getName())) {
				target = createTrack(child);
			}
		}
		relation.setTarget(target);
		return relation;
	}
	
	protected User createUser(Element node) 
	{
		User user = new User();
		user.setTypeString((MbUtils.convertTypesToURI(node.getAttributeValue("type"), Entity.NS_EXT_1)));
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("name".equals(child.getName())) {
				user.setName(child.getText());
			}
			else if ("nag".equals(child.getName()) && NS_EXT_1.equals(child.getNamespace())) {
				boolean nag = Boolean.parseBoolean(child.getAttributeValue("show"));
				user.setShowNag(nag);
			}
		}
		
		return user;
	}
	
	protected void addArtistResults(Element node, ArtistSearchResults artistResults)
	{
		if (artistResults == null)
			throw new NullPointerException();
		
		updateListElement(node, artistResults);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			ArtistResult r = new ArtistResult();
			r.setArtist(createArtist(child));
			r.setScore(getIntAttr(child, "score", NS_EXT_1));
			if (r.getArtist() != null) {
				artistResults.addArtistResult(r);
			}
		}
	}
	
	protected void addTrackResults(Element node, TrackSearchResults trackResults)
	{
		if (trackResults == null)
			throw new NullPointerException();
		
		updateListElement(node, trackResults);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			TrackResult r = new TrackResult();
			r.setTrack(createTrack(child));
			r.setScore(getIntAttr(child, "score", NS_EXT_1));
			if (r.getTrack() != null) {
				trackResults.addTrackResult(r);
			}
		}
	}
	
	protected void addReleaseResults(Element node, ReleaseSearchResults releaseResults)
	{
		if (releaseResults == null)
			throw new NullPointerException();
		
		updateListElement(node, releaseResults);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			ReleaseResult r = new ReleaseResult();
			r.setRelease(createRelease(child));
			r.setScore(getIntAttr(child, "score", NS_EXT_1));
			if (r.getRelease() != null) {
				releaseResults.addReleaseResult(r);
			}
		}
	}

	protected void addArtistAliases(Element node, Artist artist) 
	{
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			ArtistAlias alias;
			if ("alias".equals(child.getName())) 
			{
				alias = new ArtistAlias();
				alias.setValue(child.getText());
				alias.setType(MbUtils.convertTypeToURI(child.getAttributeValue("type"), Entity.NS_MMD_1));
				alias.setScript(getAttr(child, "script", scriptPattern));
				artist.addAlias(alias);
			}
		}
	}
	
	protected void addReleasesToList(Element node, ReleaseList releaseList) 
	{
		if (releaseList == null)
			throw new NullPointerException();
		
		updateListElement(node, releaseList);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("release".equals(child.getName())) {
				releaseList.addRelease(createRelease(child));
			}
		}
	}
	
	protected void addReleaseEventToList(Element node, ReleaseEventList releaseEventList) 
	{
		if (releaseEventList == null)
			throw new NullPointerException();
		
		updateListElement(node, releaseEventList);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("event".equals(child.getName())) {
				releaseEventList.addReleaseEvent(createReleaseEvent(child));
			}
		}
	}
	
	protected void addDiscToList(Element node, DiscList discList) 
	{
		if (discList == null)
			throw new NullPointerException();
		
		updateListElement(node, discList);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("disc".equals(child.getName())) {
				discList.addDisc(createDisc(child));
			}
		}
	}
	
	protected void addTrackToList(Element node, TrackList trackList) 
	{
		if (trackList == null)
			throw new NullPointerException();
		
		updateListElement(node, trackList);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("track".equals(child.getName())) {
				trackList.addTrack(createTrack(child));
			}
		}
	}
	
	protected void addRelationsToEntity(Element node, RelationList relationList) 
	{
		String targetType = MbUtils.convertTypeToURI(node.getAttributeValue("target-type"), Entity.NS_REL_1);
		if (targetType == null) {
			return;
		}
		
		if (relationList == null)
			throw new NullPointerException();
		
		updateListElement(node, relationList);
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			Relation rel;
			if ("relation".equals(child.getName())) {
				rel = createRelation(child, targetType);
				if (rel != null) {
					relationList.addRelation(rel);
				}
			}
		}
	}
	
	protected void addPuids(Element node, Track t) 
	{
		if (t == null)
			throw new NullPointerException();
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("puid".equals(child.getName())) {
				t.addPuid(child.getAttributeValue("id"));
			}
		}
	}
	
	protected void addUserToList(Element node, UserList userList) 
	{
		if (userList == null)
			throw new NullPointerException();
		
		Iterator itr = node.getChildren().iterator();
		Element child;
		while(itr.hasNext()) 
		{
			child = (Element)itr.next();
			if ("user".equals(child.getName()) && NS_EXT_1.equals(child.getNamespace())) {
				userList.addUser(createUser(child));
			}
		}
	}
	
	protected void updateListElement(Element node, ListElement le) 
	{
		le.setCount(getLongAttr(node, "count"));
		le.setOffset(getLongAttr(node, "offset"));
	}
	
	protected String getAttr(Element element, String attrName) {
		return getAttr(element, attrName, null, null, null);
	}
	
	protected String getAttr(Element element, String attrName, Pattern regex) {
		return getAttr(element, attrName, regex, null, null);
	}
	
	/**
	 * Returns an attribute of the given element.
	 * 
	 * If there is no attribute with that name or the attribute doesn't
	 * match the regular expression, default is returned.
	 * 		
	 * @param element
	 * @param attrName
	 * @param regex
	 * @param def
	 * @param ns
	 * @return A String
	 */
	protected String getAttr(Element element, String attrName, Pattern regex, String def, Namespace ns)
	{
		Attribute at;
		if (ns != null) {
			at = element.getAttribute(attrName, ns);
		} else {
			at = element.getAttribute(attrName);
		}
		
		if (at != null)
		{
			String txt = at.getValue();
			if (txt != null) {
				if (regex != null) {
					Matcher m = regex.matcher(txt);
					if (m.matches()) {
						return txt;
					}
					else {
						return def;
					}
				}
			}
			return txt;
		}
	
		return def;
	}
	
	protected Long getLongAttr(Element element, String attrName) 
	{
		String value=getAttr(element, attrName);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	protected Integer getIntAttr(Element element, String attrName) 
	{
		return getIntAttr(element, attrName, null);
	}
	
	protected Integer getIntAttr(Element element, String attrName, Namespace ns) 
	{
		String value=getAttr(element, attrName, null, null, ns);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	protected List<String> getUriListAttr(Element element, String attrName, String resType)
	{
		List<String> uris = new ArrayList<String>();
		Attribute attr = element.getAttribute(attrName);
		if ((attr == null )  || (attr.getValue() == null)) {
			return uris;
		}
		
		String[] uriArr = attr.getValue().split("\\s+");
		if ( (uriArr == null) || (uriArr.length < 1))
			return uris;
		
		for (String u : uriArr) {
			uris.add(MbUtils.convertIdToURI(u, resType));
		}
		
		return uris;
	}
}
