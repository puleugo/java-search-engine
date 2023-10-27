package com.megabrain.javasearchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JavaSearchEngineApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaSearchEngineApplication.class, args);
	}
}
