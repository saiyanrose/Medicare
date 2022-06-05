package com.medicare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medicare.entity.User;
import com.medicare.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=repository.findByEmail(email);		
		if(user!=null) {
			return new MyUserDetails(user);
		}
		throw new UsernameNotFoundException("User Not Found: " +email);
	}

}
