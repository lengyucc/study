package com.antbean.springbootdemowebsocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //
				.authorizeRequests() //
				.antMatchers("/", "/login").permitAll() // 1 不拦截的路径
				.anyRequest().authenticated() //
				.and() //
				.formLogin() //
				.loginPage("/login") // 2 设置登录页面
				.defaultSuccessUrl("/chat") // 3 登录成功后的页面
				.permitAll() //
				.and() //
				.logout() //
				.permitAll();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 在内存中分配两个用户
		auth //
				.inMemoryAuthentication() //
				.withUser("lmh").password("111222").roles("USER") //
				.and() //
				.withUser("admin").password("111222").roles("USER");
	}

}
