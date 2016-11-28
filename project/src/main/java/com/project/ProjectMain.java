package com.project;

import com.project.Config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.project")
@ContextConfiguration(classes = AppConfig.class)
public class ProjectMain {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProjectMain.class);
		app.run(args);
		//SpringApplication.run(ProjectMain.class, args);
	}
}
