package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.User;

public interface UserDao {
	
	public User findById (long id);

	public User findByName(String name);

	public User create(String name,String password);

}
