
package org.musicbrainz.wsxml.element;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.model.User;


/**
 * A list of {@link User}s.
 * 
 * @see User
 * @author Patrick Ruhkopf
 */
public class UserList extends ListElement{

    protected List<User> users = new ArrayList<User>();

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	/**
	 * Convenience method to adds an user to the list.
	 * 
	 * This will create a new <code>ArrayList</code> if {@link #users} is null.
	 * 
	 * @param user
	 */
	public void addUser(User user) 
	{
		if (users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);
	}
	
}
