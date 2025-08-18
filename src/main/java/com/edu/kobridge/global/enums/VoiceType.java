package com.edu.kobridge.global.enums;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum VoiceType {
	ONE("GIRL_ONE"),
	TWO("GIRL_TWO"),
	THREE("BOY_ONE"),
	FOUR("BOY_TWO");

	// 추후 voice 확정 되면 name 필드 추가
	private final String code;

	VoiceType(String code) {
		this.code = code;
	}

	public static VoiceType of(String code) {
		return Arrays.stream(VoiceType.values())
			.filter(r -> r.getCode().equals(code))
			.findAny()
			.orElse(null);
	}
}
