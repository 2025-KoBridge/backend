package com.edu.kobridge.module.user.user.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LoginResDto {
	@Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGc...")
	private final String accessToken;

	@Schema(description = "refreshToken", example = "eyJ0eXAiOiJKV1QiLCJhbGc...")
	private final String refreshToken;

	@Schema(description = "isFirstLogin : 사용자가 회원가입한 적이 없다면 false 반환 / 사용자가 회원가입한 적이 있다면 true 반환", example = "false")
	private final boolean isFirstLogin;

	public static LoginResDto of(String accessToken, String refreshToken, boolean isFirstLogin) {
		return LoginResDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.isFirstLogin(isFirstLogin)
			.build();
	}
}
