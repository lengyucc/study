package com.antbean.springbootdemosecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService customUserService() {
		return new CustomUserService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.authorizeRequests() //
				.anyRequest().authenticated() //
				.and() //
				.formLogin() //
				.loginPage("/login") //
				.failureUrl("/login?error") //
				.permitAll() //
				.and() //
				.logout() //
				.permitAll();
	}
}
