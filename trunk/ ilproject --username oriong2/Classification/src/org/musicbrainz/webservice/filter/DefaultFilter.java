package org.musicbrainz.webservice.filter;

import java.util.HashMap;
import java.util.Map;


/**
 * This abstract class implemets a {@link Filter} and provides
 * some common properties and functions.
 * 
 * @author Patrick Ruhkopf
 */
public abstract class DefaultFilter implements Filter 
{
	/**
	 * The maximum number of entities to return
	 */
	private Long limit = null;
	
	/**
	 * Start results at this zero-based offset 
	 */
	private Long offset = null;
	 
	/**
	 * A string containing a query in Lucene syntax
	 */
	private String query = null;
	
	/**
	 * Default constructor
	 */
	public DefaultFilter()
	{
		
	}
	
	/**
	 * Constructor for query
	 */
	public DefaultFilter(String query)
	{
		this.query = query;
	}
	
	/**
	 * Full constructor
	 * 
	 * The <code>query</code> parameter may contain a query in 
	 * <a href="http://lucene.apache.org/java/docs/queryparsersyntax.html">
	 * Lucene syntax</a>.
	 * 
	 * @param limit The maximum number of artists to return
	 * @param offset S
	 * @param query A string containing a query in Lucene syntax
	 */
	public DefaultFilter(long limit, long offset, String query)
	{
		this.limit = limit;
		this.offset = offset;
		this.query = query;
	}
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter#createParameters()
	 */
	public Map<String, String> createParameters()
	{
		Map<String, String> map = new HashMap<String, String>();
		
		if (this.limit != null) map.put("limit", this.limit.toString());
		if (this.offset != null) map.put("offset", this.offset.toString());
		if (this.query != null) map.put("query", this.query);
		
		return map;
	}
	
	/**
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Long limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
}
