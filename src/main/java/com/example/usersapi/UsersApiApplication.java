package com.example.usersapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Christopher Cook
 * @author Qin Zhu
 */
@SpringBootApplication
@EnableEurekaClient
public class UsersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApiApplication.class, args);
	}

}
