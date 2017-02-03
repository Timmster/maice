package com.maice;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SuppressWarnings("deprecation")
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class MaiceConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().anonymous().and().authorizeRequests().antMatchers("/register", "/messages").permitAll().and().authorizeRequests()
				.antMatchers("/index.html", "/html/public.html", "/html/templates/**", "/").permitAll().anyRequest().authenticated().and().logout().and()
				.addFilterAfter(new OncePerRequestFilter() {
					@Override
					protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
							throws ServletException, IOException {
						CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
						if (csrf != null) {
							Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
							String token = csrf.getToken();
							if (cookie == null || token != null && !token.equals(cookie.getValue())) {
								cookie = new Cookie("XSRF-TOKEN", token);
								cookie.setPath("/");
								response.addCookie(cookie);
							}
						}
						filterChain.doFilter(request, response);
					}
				}, CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/register");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = builder.inMemoryAuthentication();
		enableAuthentication(inMemoryAuthentication, "timm.schwemann@googlemail.com", "test");
	}

	private void enableAuthentication(InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder, String username, String password)
			throws Exception {
		builder.withUser(username).password(password).roles("USER");
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

}
