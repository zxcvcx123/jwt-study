package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.auth.jwt.JwtAuthenticationFilter;
import com.example.demo.filter.MyFilter1;
import com.example.demo.filter.MyFilter3;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다.
public class SecurityConfig {
	
	private final CorsConfig corsConfig; //
	private final CorsFilter corsFilter; // Bean 으로 등록되어 있어서 바로 가져다 써도됨 근데 나는 걍 위에 클래스에서 메소드 호출할거임~
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		AuthenticationManager authenticationManager =  http.getSharedObject(AuthenticationManager.class);
		
//		http.addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class);
		http.csrf(CsrfConfigurer::disable);
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.sessionManagement((sessionManagement) -> 
								sessionManagement
									.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(corsConfig.corsFilter()); // @CrossOrigin(인증X), 시큐리티 필터에 등록인증(O)
//		http.formLogin().disable();
		http.formLogin((form)->
						form.disable());
//		http.httpBasic().disable();
		http.httpBasic((basic)->
						basic.disable());
		http.addFilter(new JwtAuthenticationFilter(authenticationManager)); // AuthenticationManger
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/user/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
				.requestMatchers("/admin/**")
				.hasAnyRole("ADMIN").anyRequest().permitAll());
		return http.build();
	}
}