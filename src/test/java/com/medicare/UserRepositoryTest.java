package com.medicare;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.medicare.entity.Roles;
import com.medicare.entity.User;
import com.medicare.repository.RoleRepository;
import com.medicare.repository.UserRepository;
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void addRoleTest() {
		Roles roleAdmin=new Roles("Admin");
		Roles roleUser=new Roles("User");
		repository.saveAll(List.of(roleAdmin,roleUser));
		List<Roles>roles=(List<Roles>) repository.findAll();
		assertThat(roles.size()).isGreaterThan(0);
	}
	
	@Test
	public void addUserTest() {
		User user=new User();
		user.setEmail("njr58618@gmail.com");
		user.setPassword("neymar");
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	@Test
	public void addUserWithRole() {
		Roles findRole=repository.findById(2).get();
		System.out.println("Testing...(Role Name:) " +findRole.getName());
		User user=userRepository.findById(2).get();
		user.addRole(findRole);
		userRepository.save(user);
		assertThat(user.getRoles()).isNotEmpty();
	}
	
	@Test
	public void findUserTest() {
		String email="piyushmalik08@gmail.com";
		User user=userRepository.findByEmail(email);		
		assertThat(user.getEmail()).isNotEmpty();
	}
}
