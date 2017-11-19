package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.model.TopUserDTO;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface UserService {

	/**
	 * Finds a User with the specified id
	 * @param id of the User
	 * @return instance of the User
	 */
	User findById(long id);

	/**
	 * Add a description to user
	 * @param user the User
	 * @param description the new description of the User
	 */
	void updateDescription(User user, String description);

	/**
	 * Finds a User with the specified name
	 * @param name of the User
	 * @return instance of the User
	 */
	User findByName(String name);

	/**
	 * Creates a {@link User} with the specified
	 * name and password.
	 * @param name of the User
	 * @param password of the User
	 * @return instance of the new User
	 * @throws DuplicateUsernameException
	 */
	User create(String name, String password) throws DuplicateUsernameException;

    List<TopUserDTO> findTopWinners(int top);
}
