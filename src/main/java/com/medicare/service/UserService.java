package com.medicare.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicare.entity.Roles;
import com.medicare.entity.User;
import com.medicare.repository.RoleRepository;
import com.medicare.repository.UserRepository;


@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	private void encodePassword(User user) {
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);		
	}

	public User saveNewUser(User user) {
		encodePassword(user);
		Roles findRole=roleRepository.findById(2).get();
		user.setEnabled(true);
		user.addRole(findRole);
		return userRepository.save(user);		
	}

}
