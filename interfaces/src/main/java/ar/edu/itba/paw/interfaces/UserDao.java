package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserDao {
	
	public User findById (long id);

	public User create(String name,String password);

}
