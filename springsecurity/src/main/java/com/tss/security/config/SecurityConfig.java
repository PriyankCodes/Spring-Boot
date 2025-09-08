package com.tss.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tss.security.security.JwtAuthenticationEntryPoint;
import com.tss.security.security.JwtAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(request -> request
				.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
				.requestMatchers(HttpMethod.GET, "/studentapp/**").hasAnyRole("USER", "ADMIN", "MODERATOR")
				.requestMatchers(HttpMethod.POST, "/studentapp/**").hasAnyRole("ADMIN", "MODERATOR")
				.requestMatchers(HttpMethod.PUT, "/studentapp/**").hasAnyRole("ADMIN", "MODERATOR")
				.requestMatchers(HttpMethod.DELETE, "/studentapp/**").hasRole("ADMIN")
				.anyRequest().authenticated());
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
