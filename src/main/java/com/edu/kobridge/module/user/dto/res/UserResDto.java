package com.edu.kobridge.module.user.dto.res;

import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.enums.VoiceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 간단 정보 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserResDto {

	@Schema(description = "이름", example = "김철수")
	private final String name;

	@Schema(description = "번역 언어", example = "ENG")
	private final LangType lang;

	@Schema(description = "선택한 Voice", example = "ONE")
	private final VoiceType voice;

	@Schema(description = "현재 level", example = "3")
	private final int level;

	public static UserResDto of(String name, LangType lang, VoiceType voice, int level) {
		return UserResDto.builder()
			.name(name)
			.lang(lang)
			.voice(voice)
			.level(level)
			.build();
	}
}
