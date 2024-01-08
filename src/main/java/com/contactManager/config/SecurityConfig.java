package com.contactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.contactManager.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/signinPage", "/signupPage", "/login", "/user/register").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
                 )
                .formLogin(form -> form
                        .loginPage("/signinPage")
                        .successForwardUrl("/")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login-error")
                 )
                .logout(logout -> logout
                		.invalidateHttpSession(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/signinPage")
                 );
//		        .and()
//		        .csrf().disable();
//		http.authenticationProvider(authProvider());
		return http.build();
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//	
//	@Bean 
//	public UserService userService() {
//		return new UserService();
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withUsername("root").password(passwordEncoder().encode("root")).roles("ADMIN").build();
//		UserDetails user1 = User.withUsername("root1").password(passwordEncoder().encode("root1")).roles("USER").build();
//		return new InMemoryUserDetailsManager(user,user1);
//		
//	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
