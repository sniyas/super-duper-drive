package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private HashService hashService;

	/**
	 * Checks if user name is already taken
	 * 
	 * @param username
	 * @return
	 */
	public boolean isUsernameAvailable(String username) {
		return userMapper.getUser(username) == null;
	}

	/**
	 * Adds a new user
	 * 
	 * @param user
	 * @return
	 */
	public int createUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setSalt(encodedSalt);
		newUser.setPassword(hashedPassword);
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());

		return userMapper.insert(newUser);
	}

	/**
	 * Gets user details by user name
	 * 
	 * @param username
	 * @return
	 */
	public User getUser(String username) {
		return userMapper.getUser(username);
	}

}