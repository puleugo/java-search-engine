package com.megabrain.javasearchengine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JavaSearchEngineApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {}

	public static void main(String[] args) {
		SpringApplication.run(JavaSearchEngineApplication.class, args);
	}
}
