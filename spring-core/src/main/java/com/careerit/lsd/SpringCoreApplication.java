package com.careerit.lsd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("paytm.properties")
public class SpringCoreApplication implements CommandLineRunner {

	@Value("${app.message}")
	private String message;

	public static void main(String[] args) {
		SpringApplication.run(SpringCoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(message);
	}
}
