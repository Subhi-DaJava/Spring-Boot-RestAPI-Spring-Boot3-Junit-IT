package com.uyghurjava.restapi.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserDetailsRepository userDetailsRepo;
	public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepo) {
		super();
		this.userDetailsRepo = userDetailsRepo;
	}
	@Override
	public void run(String... args) throws Exception {
		logger.info("This is args : " + Arrays.toString(args));
		userDetailsRepo.save(new UserDetails("Adam", "ADMIN"));
		userDetailsRepo.save(new UserDetails("Gheni", "USER"));
		userDetailsRepo.save(new UserDetails("Gul", "USER"));
		userDetailsRepo.save(new UserDetails("Shir", "ADMIN"));
		
		logger.info("Users have been insered.");
		
		List<UserDetails> users = userDetailsRepo.findAll();
		
		users.forEach(user -> System.out.println(user.toString()));
		users.forEach(user -> logger.info(user.toString()));
		
		
		List<UserDetails> usersByRole = userDetailsRepo.findByRole("ADMIN");
		usersByRole.forEach(user -> logger.info(user.toString()));
	}

}
