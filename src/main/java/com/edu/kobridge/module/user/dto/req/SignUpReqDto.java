package com.edu.kobridge.module.user.dto.req;

import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.enums.SchoolType;
import com.edu.kobridge.global.enums.VoiceType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "사용자 가입 요청 DTO")
public record SignUpReqDto(
	@Schema(description = "이름", example = "김철수")
	@NotBlank(message = "이름은 필수 값입니다.")
	@Size(min = 2, max = 50, message = "이름은 2자이상 50자 미만이어야 합니다.")
	String name,

	@Schema(description = "나이", example = "23")
	@Min(value = 1, message = "나이는 1 이상이어야 합니다.")
	@Max(value = 100, message = "나이는 100 이하여야 합니다.")
	int age,

	@Schema(description = "언어", example = "ENG , VET, CHN, JPN, NONE")
	@NotNull(message = "역할은 필수 값입니다.")
	LangType lang,

	@Schema(description = "학교", example = "ELEMENTARY , MIDDLE, HIGH")
	@NotNull(message = "학교는 필수 값입니다.")
	SchoolType school,

	@Schema(description = "학년", example = "3")
	@Min(value = 1, message = "학년은 1 이상이어야 합니다.")
	@Max(value = 6, message = "학년은 6 이하여야 합니다.")
	byte grade,

	@Schema(description = "음성", example = "BASIC, CUSTOM")
	@NotNull(message = "음성은 필수 값입니다.")
	VoiceType voice
) {
}
