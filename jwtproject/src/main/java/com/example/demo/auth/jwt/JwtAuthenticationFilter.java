package com.example.demo.auth.jwt;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.domain.Users;
import com.example.demo.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면 (post)
// UserPasswordAuthenticationFilter가 동작을 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	
	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인 시도: JwtAuthenticationFilter");
		 
		
		// 1.username, password 받아서
		try {
//			BufferedReader br = request.getReader();
//			
//			String input = null;
//			while((input = br.readLine()) != null) {
//				System.out.println(input);
//			}
			ObjectMapper oms = ObjectMapperSingleton.getInstance();
			Users user = oms.readValue(request.getInputStream(), Users.class);
			System.out.println(user);
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// PrincipalDetailService의 loadUserByUsername() 함수가 실행됨
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);
			
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println(principalDetails.getUsername());
			
			return authentication;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("==============================");
		// 2. 정상인지 로그인 시도를 해보는 거에요. authenticationManager로 로그인 시도를 하면 
		// PrincipalDetailService가 호출 loadUserByUsername() 함수 실행됨.
		
		// 3. PrinciaplDetails를 세션에 담고 (권한 관리를 위해서)
		
		// 4. JWT토큰을 만들어서 응답해주며 됨.
		return super.attemptAuthentication(request, response);
	}
	
}
