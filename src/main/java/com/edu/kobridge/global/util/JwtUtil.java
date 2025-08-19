package com.edu.kobridge.global.util;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edu.kobridge.global.enums.JwtVo;
import com.edu.kobridge.global.error.GlobalErrorCode;
import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.module.user.domain.entity.User;
import com.edu.kobridge.module.user.domain.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
	@Value("${jwt.issuer}")
	private String ISSUER;

	@Value("${jwt.secret}")
	private String JWT_SECRET_KEY;

	@Value("${jwt.access-token-expiration}")
	private int ACCESS_TOKEN_EXPIRATION;

	@Getter
	@Value("${jwt.refresh-token-expiration}")
	private int REFRESH_TOKEN_EXPIRATION;

	private final String PAYLOAD_KEY_ID = "id";

	private final UserRepository userRepository;
	private final RedisUtil redisUtil;

	public JwtVo generateTokens(User user) {
		final String PAYLOAD_KEY_EMAIL = "email";

		// payload에 사용자 식별 값 추가
		Map<String, Object> payloads = new LinkedHashMap<>();
		payloads.put(PAYLOAD_KEY_ID, user.getId());
		payloads.put(PAYLOAD_KEY_EMAIL, user.getEmail());

		// Token 유효기간 설정
		Date now = new Date();
		Date accessExp = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);
		Date refreshExp = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

		// 토큰 생성
		return new JwtVo(
			createToken(payloads, accessExp, "access"),
			createToken(payloads, refreshExp, "refresh")
		);
	}

	// JWT 생성 메서드
	private String createToken(Map<String, Object> payloads, Date expiration, String subject) {
		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(payloads)
			.setIssuer(ISSUER)
			.setIssuedAt(new Date())
			.setExpiration(expiration)
			.setSubject(subject)
			.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY.getBytes())
			.compact();
	}

	// 토큰을 검증하고 유효한 사용자 정보를 반환
	@Transactional(readOnly = true)
	public User validateToken(boolean isAccessToken, String header) throws AppException {
		// Jwt 토큰 및 claims 추출
		String token = extractToken(header);
		Claims claims = parseToken(token);

		// 사용자 정보 추출 및 매핑
		Long userId = claims.get(PAYLOAD_KEY_ID, Long.class);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new AppException(GlobalErrorCode.USER_NOT_FOUND));

		if (!isAccessToken) {
			// refreshToken 있는지 확인
			String storedRefreshToken = redisUtil.getOpsForValue(user.getId() + "_refresh");
			if (storedRefreshToken == null || !storedRefreshToken.equals(token)) {
				throw new AppException(GlobalErrorCode.AUTHORIZATION_FAILED);
			}
		}

		return user;
	}

	// Authorization 헤더에서 Bearer 토큰 추출
	private String extractToken(String header) {
		if (header == null || !header.startsWith("Bearer ")) {
			throw new AppException(GlobalErrorCode.INVALID_TOKEN);
		}
		return header.substring(7);
	}

	// JWT 토큰에서 클레임(Claims) 추출
	private Claims parseToken(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(JWT_SECRET_KEY.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (JwtException e) {
			throw new AppException(GlobalErrorCode.INVALID_TOKEN);
		}
	}

}
