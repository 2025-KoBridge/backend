package com.edu.kobridge.global.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.module.user.user.domain.entity.User;
import com.edu.kobridge.module.user.user.error.UserErrorCode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleOAuthUtil {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String CLIENT_ID;

	public User authenticate(String idToken)
		throws GeneralSecurityException, IOException {
		// idToken 유효성 확인을 위한 Google 인증 설정
		HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		GsonFactory gsonFactory = GsonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonFactory)
			.setAudience(Collections.singletonList(CLIENT_ID))
			.build();

		// 전달된 idToken을 검증하여 GoogleIdToken 객체 생성
		GoogleIdToken googleIdToken = verifier.verify(idToken);
		if (googleIdToken == null) {
			throw new AppException(UserErrorCode.INVALID_ID_TOKEN);
		}

		// 검증된 ID 토큰의 페이로드에서 사용자 정보 추출
		GoogleIdToken.Payload payload = googleIdToken.getPayload();
		String email = payload.getEmail();

		return User.of(email);
	}
}
