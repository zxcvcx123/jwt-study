package com.example.demo.filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		

		// 예시토큰: 코스
		// 토큰이 들어오면 필터 들어가게 없으면 컨트롤러도 안타게
		// 언제 만드냐? -> id.pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
		// 요청할 때 마다 header에 Authorization에 value 값으로 토큰을 가지고옴
		// 그 때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면됨. (RSA,HS256)
		if (req.getMethod().equals("POST")) {
			System.out.println("POST 요청됨");
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
			System.out.println("필터3");

			if (headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			} else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
		
	}

}
