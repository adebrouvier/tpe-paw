package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.model.TopUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public void updateDescription(User user, String description) {
		userDao.updateDescription(user, description);
	}

	@Override
	public User findByName(String name) {
		return userDao.findByName(name);
	}

	@Transactional
	@Override
	public User create(String name,String password) throws DuplicateUsernameException {
		return userDao.create(name,password);
	}

	@Override
	public List<TopUserDTO> findTopWinners(int top) {
		return userDao.findTopWinners(top);
	}

}
