package ar.edu.itba.paw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;

@Service
public class UserServiceImpl implements UserService {	
	
	@Autowired
	private UserDao userDao;

	@Override
	public User findById(long id) {
		return userDao.findById(id);
	}

	@Override
	public User create(String name,String password) {
		return userDao.create(name,password);
	}

}
