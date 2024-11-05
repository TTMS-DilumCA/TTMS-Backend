package com.TTMSGislavedGummiLanka.TTMS_Backend;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Role;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TtmsBackendApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;


	public static void main(String[] args) {

		SpringApplication.run(TtmsBackendApplication.class, args);
	}

	public void run(String... args){
		User user = new User();

		user.setEmail("admin@gmail.com");
		user.setFirstname("admin");
		user.setLastname("admin");
		user.setRole(Role.ADMIN);
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		userRepo.save(user);
	}

}
