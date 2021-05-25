package com.careerit.scart.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private DataSource dataSoruce;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/checkoutitems").hasAnyRole("USER","ADMIN")
				.antMatchers("**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().and().httpBasic()
				.and().logout().permitAll();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		auth.inMemoryAuthentication().passwordEncoder(encoder).withUser("krish").password(encoder.encode("krish@123"))
//				.roles("USER").and().withUser("admin").password(encoder.encode("admin@123")).roles("ADMIN");
		
		auth.jdbcAuthentication()
		    .passwordEncoder(encoder)
		    .dataSource(dataSoruce)
		    .usersByUsernameQuery("select username,password,enabled from users where username=?")
		    .authoritiesByUsernameQuery("select username,authority,enabled from users where username=?");
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	
	// Custom login
	// Custom logout
	// Common header and footer
	// create two table users and authorities  and change queries 
	// Register with paytm and change the credentials test end-to-end
	
}
