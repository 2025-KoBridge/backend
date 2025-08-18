package com.edu.kobridge.global.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum UserRoleType {
	USER("ROLE_USER", "일반 사용자 권한"),
	ADMIN("ROLE_ADMIN", "관리자 권한"),
	GUEST("GUEST", "게스트 권한");

	private final String code;
	private final String name;

	UserRoleType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static UserRoleType of(String code) {
		return Arrays.stream(UserRoleType.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(GUEST);
	}
}
