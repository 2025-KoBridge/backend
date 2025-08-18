package com.edu.kobridge.global.common.filter;

import java.io.IOException;

import com.edu.kobridge.global.error.ErrorCode;
import com.edu.kobridge.global.error.GlobalErrorCode;
import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.global.error.exception.FilterException;
import com.edu.kobridge.global.util.JwtUtil;
import com.edu.kobridge.global.util.ResponseUtil;
import com.edu.kobridge.user.domain.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter implements Filter {
	private final JwtUtil jwtUtil;

	// JWT 검사 제외할 경로 설정
	final String LOGIN_PATH = "/api/user/google-login";
	final String TOKEN_PATH = "/api/user/token";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("Filter initialized.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		// 요청 URI를 가져옴
		String requestURI = req.getRequestURI();

		// swagger 인증 필터링 없이 처리
		if (requestURI.startsWith("/swagger-ui/") ||
			requestURI.startsWith("/webjars/") ||
			requestURI.startsWith("/v3/api-docs")) {
			chain.doFilter(request, response);
			return;
		}

		// 로그인 및 토큰 재발급 요청은 JWT 인증 필터링 없이 처리
		if (requestURI.equals(LOGIN_PATH) && req.getMethod().equals("GET")) {
			chain.doFilter(request, response);
			return;
		} else if (requestURI.equals(TOKEN_PATH) && req.getMethod().equals("GET")) {
			chain.doFilter(request, response);
			return;
		} else if (requestURI.contains("test/no-auth")) {
			chain.doFilter(request, response);
			return;
		}

		try {
			// Authorization 헤더에서 JWT 토큰을 가져옴
			String header = req.getHeader("Authorization");
			if (header == null) {
				throw new FilterException(GlobalErrorCode.ACCESS_TOKEN_REQUIRED);
			}

			// 토큰 유효성 검사 후 사용자 정보 추출
			User user = jwtUtil.validateToken(true, header);
			req.setAttribute("user", user);

			// 필터 체인 다음 필터로 이동
			chain.doFilter(request, response);
		} catch (FilterException e) { // 토큰이 없는 경우
			ResponseUtil.setResponse(res, e.getErrorCode().getHttpStatus().value(), e.getMessage());
		} catch (AppException e) { // 토큰이 유효하지 않은 경우
			ResponseUtil.setResponse(res, e.getErrorCode().getHttpStatus().value(), e.getMessage());
		} catch (JwtException e) {  // JWT 토큰 예외 처리
			ErrorCode code =
				e instanceof ExpiredJwtException ? GlobalErrorCode.EXPIRED_JWT : GlobalErrorCode.INVALID_TOKEN;

			ResponseUtil.setResponse(res, code.getHttpStatus().value(), code.getMessage());
		}
	}

	@Override
	public void destroy() {
		log.info("Filter destroyed.");
	}
}
