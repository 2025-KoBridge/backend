package com.edu.kobridge.global.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum SchoolType {
	ELEMENTARY("elementary", "초등학교", 6),
	MIDDLE("middle-school", "중학교", 3),
	HIGH("high-school", "고등학교", 3);

	private final String code;
	private final String name;
	private final int maxGrade;

	SchoolType(String code, String name, int maxGrade) {
		this.code = code;
		this.name = name;
		this.maxGrade = maxGrade;
	}

	public static SchoolType of(String code) {
		return Arrays.stream(SchoolType.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(null);
	}
}
