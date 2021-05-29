package com.careerit.ipl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@EnableMongoAuditing
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "IPLSTAT",description = "IPL STAT USING SPRING BOOT",contact = @Contact(name = "admin",email = "amdin@iplstat.com")))
public class IplApplication {

		public static void main(String[] args) {
			SpringApplication.run(IplApplication.class, args);
		}
}
