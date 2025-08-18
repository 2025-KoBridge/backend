package com.edu.kobridge.user.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.kobridge.global.enums.JwtVo;
import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.enums.SchoolType;
import com.edu.kobridge.global.error.ErrorCode;
import com.edu.kobridge.global.error.GlobalErrorCode;
import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.global.util.GoogleOAuthUtil;
import com.edu.kobridge.global.util.JwtUtil;
import com.edu.kobridge.global.util.RedisUtil;
import com.edu.kobridge.user.domain.entity.User;
import com.edu.kobridge.user.domain.repository.UserRepository;
import com.edu.kobridge.user.dto.req.SignUpReqDto;
import com.edu.kobridge.user.dto.res.LoginResDto;
import com.edu.kobridge.user.dto.res.UserResDto;
import com.edu.kobridge.user.error.UserErrorCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;
	private final GoogleOAuthUtil googleOAuthUtil;
	private final UserRepository userRepository;

	@Transactional
	public LoginResDto getGoogleLogin(String idToken) {
		// 요청 인자 유효성 검사
		if (idToken.isBlank()) {
			throw new AppException(UserErrorCode.ID_TOKEN_REQUIRED);
		}

		// 구글 인증 진행
		User googleUser = null;
		try {
			googleUser = googleOAuthUtil.authenticate(idToken);
		} catch (GeneralSecurityException | IOException e) {
			throw new AppException(UserErrorCode.INVALID_ID_TOKEN);
		}

		// 유저 찾고, 없다면 새 유저 생성
		User finalGoogleUser = googleUser;
		User user = userRepository.findByEmail(googleUser.getEmail())
			.orElseGet(() -> userRepository.save(finalGoogleUser));

		// JWT 토큰 생성 및 refreshToken 저장
		JwtVo jwtVo = jwtUtil.generateTokens(user);
		redisUtil.setOpsForValue(user.getId() + "_refresh", jwtVo.getRefreshToken(),
			jwtUtil.getREFRESH_TOKEN_EXPIRATION());

		return LoginResDto.of(jwtVo.getAccessToken(), jwtVo.getRefreshToken(), user.getLang() == null);
	}

	@Transactional
	public LoginResDto getAccessToken(String refreshToken) {
		if (refreshToken.isBlank()) {
			throw new AppException(GlobalErrorCode.REFRESH_TOKEN_REQUIRED);
		}

		// refreshToken 유효성 검사 실행
		User tokenUser;
		try {
			tokenUser = jwtUtil.validateToken(false, refreshToken);
		} catch (JwtException e) {
			ErrorCode code =
				e instanceof ExpiredJwtException ? GlobalErrorCode.EXPIRED_JWT : GlobalErrorCode.INVALID_TOKEN;

			throw new AppException(code);
		}

		// JWT 토큰 생성 및 refreshToken 저장
		JwtVo jwtVo = jwtUtil.generateTokens(tokenUser);
		redisUtil.setOpsForValue(tokenUser.getId() + "_refresh", jwtVo.getRefreshToken(),
			jwtUtil.getREFRESH_TOKEN_EXPIRATION());

		return LoginResDto.of(jwtVo.getAccessToken(), jwtVo.getRefreshToken(), tokenUser.getLang() == null);
	}

	@Transactional
	public void postSignUp(User user, SignUpReqDto signUpReq) {
		// 학년 유효성 검사
		if (signUpReq.school() == SchoolType.MIDDLE || signUpReq.school() == SchoolType.HIGH || signUpReq.grade() > 3) {
			throw new AppException(UserErrorCode.GRADE_SIZE_ERROR);
		}

		// 사용자 정보 업데이트
		user.updateInfo(signUpReq.name(), signUpReq.lang(), signUpReq.school(), signUpReq.grade(), signUpReq.voice());

		userRepository.save(user);
	}

	public UserResDto getUserInfo(User user) {
		return UserResDto.of(user.getName(), user.getLang(), user.getVoice(), user.getLevel());
	}

	@Transactional
	public void updateLang(User user, LangType lang) {
		user.updateLang(lang);
		userRepository.save(user);
	}

	@Transactional
	public void updateLevel(User user) {
		user.updateLevel(user.getLevel() + 1);
		userRepository.save(user);
	}

	@Transactional
	public void deleteGoogleLogout(User user) {
		// 사용자 refreshToken 삭제
		redisUtil.delete(user.getId() + "_refresh");
	}
}
