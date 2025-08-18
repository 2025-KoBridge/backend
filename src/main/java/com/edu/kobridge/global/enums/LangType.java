package com.edu.kobridge.global.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum LangType {
	ENG("ENG", "영어"),
	VET("VET", "베트남어"),
	CHN("CHN", "중국어"),
	JPN("JPN", "일본어"),
	NONE("NONE", "none");

	private final String code;
	private final String name;

	LangType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static UserRoleType of(String code) {
		return Arrays.stream(UserRoleType.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(null);
	}
}
