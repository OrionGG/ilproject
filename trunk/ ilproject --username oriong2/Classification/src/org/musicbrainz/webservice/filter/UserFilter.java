package org.musicbrainz.webservice.filter;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>A filter for the user collection.</p>
 * 
 * <p><em>Note that the <code>name</code> and <code>query</code> properties
 * may not be used together.</em></p>
 * 
 * @author Patrick Ruhkopf
 */
public class UserFilter implements Filter {
	
	/**
	 * The name of the user
	 */
	private String userName = null;

	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter()
	 */
	public UserFilter() {
		super();
	}
	
	public UserFilter(String userName) {
		this.userName = userName;
	}
	
	
	/* (non-Javadoc)
	 * @see org.musicbrainz.webservice.Filter#createParameters()
	 */
	public Map<String, String> createParameters() 
	{
		Map<String, String> map = new HashMap<String, String>();
		if (this.userName != null)  map.put("name", this.userName);		
		return map;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	

}