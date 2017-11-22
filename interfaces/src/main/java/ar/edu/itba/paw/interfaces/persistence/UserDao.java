package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.MostFollowedDTO;
import ar.edu.itba.paw.model.TopUserDTO;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface UserDao {

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
	 * @param twitchUrl the twitch channel url
	 * @param twitterUrl the twitter profile url
	 * @param youtubeUrl the youtube channel url
	 */
	void updateDescription(User user, String description, String twitchUrl, String twitterUrl, String youtubeUrl);

	/**
	 * Finds a User with the specified name
	 * @param name of the User
	 * @return instance of the User
	 */
	User findByName(String name);

	/**
	 * Creates a User with the specified
	 * name and password.
	 * @param name of the User
	 * @param password of the User
	 * @return instance of the new User
	 */
	User create(String name, String password);

    List<TopUserDTO> findTopWinners(int top);

    List<MostFollowedDTO> findMostFollowed(int userCount);
}
