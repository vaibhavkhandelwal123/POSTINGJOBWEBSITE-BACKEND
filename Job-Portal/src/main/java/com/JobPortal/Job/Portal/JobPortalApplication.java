package com.JobPortal.Job.Portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"api", "service", "repository", "entity", "dto","utility","exception","config"})
@EnableMongoRepositories(basePackages = "repository")
public class JobPortalApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}
}

