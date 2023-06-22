package com.carparking.core_auth.config;

import com.carparking.core_auth.handler.CustomAccessDeniedHandler;
import com.carparking.core_auth.handler.CustomAuthenticationFailureHandler;
import com.carparking.core_auth.jwt.JwtAuthenticationEntryPoint;
import com.carparking.core_auth.jwt.JwtAuthenticationFilter;
import com.carparking.core_auth.jwt.JwtTokenProvider;
import com.carparking.core_auth.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	CustomUserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	JwtAuthenticationEntryPoint unauthorizedHandler() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	JwtTokenProvider jwtTokenProvider() {
		return new JwtTokenProvider();
	}

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService())
				.passwordEncoder(passwordEncoder());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/uploads/**").permitAll()
				.antMatchers("/api/auth/**").permitAll()
				// INTERNAL API
				.antMatchers("/api/timekeeping/internal/**").permitAll()
				.antMatchers("/ws/**").permitAll()
				// SWAGGER
				.antMatchers(
						"/swagger-ui.html",
						"/webjars/**",
						"/swagger-resources/**",
						"/v2/api-docs/**",
						"/favicon.ico"
				).permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

		http.addFilterBefore(
				jwtAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class
		);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/uploads/**");
	}
}
