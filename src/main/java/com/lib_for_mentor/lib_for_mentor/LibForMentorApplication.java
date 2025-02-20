package com.lib_for_mentor.lib_for_mentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.lib_for_mentor.lib_for_mentor.client")
public class LibForMentorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibForMentorApplication.class, args);
	}

}
