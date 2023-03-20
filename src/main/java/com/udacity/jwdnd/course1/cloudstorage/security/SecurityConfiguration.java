package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private AuthenticationService authenticationService;

	public SecurityConfiguration(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) {
		builder.authenticationProvider(this.authenticationService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeRequests().antMatchers("/h2-console/**", "/resources/**", "/signup", "/css/**", "/js/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/home", true).permitAll().and().headers().frameOptions().disable().and().logout()
				.permitAll();

	}
}
