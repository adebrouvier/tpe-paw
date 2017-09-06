package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserService {
	
	public User findById(long id);

	public User create(String name,String password);
}
