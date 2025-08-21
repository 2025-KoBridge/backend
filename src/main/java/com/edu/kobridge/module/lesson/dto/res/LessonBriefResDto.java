package com.edu.kobridge.module.lesson.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LessonBriefResDto {
	@Schema(description = "레슨 id", example = "1")
	private final Long id;

	@Schema(description = "레슨 순서", example = "1")
	private final int number;

	@Schema(description = "레슨 제목", example = "")
	private final String title;

	@Schema(description = "레슨 1단계 제목(따라하기)", example = "")
	private final String subTitle1;

	@Schema(description = "레슨 2단계 제목(대화하기)", example = "")
	private final String subTitle2;

	@Schema(description = "레슨 3단계 제목(평가하기)", example = "")
	private final String subTitle3;

	public static LessonBriefResDto of(Long id, int number, String title, String subTitle1, String subTitle2,
		String subTitle3) {
		return LessonBriefResDto.builder()
			.id(id)
			.number(number)
			.title(title)
			.subTitle1(subTitle1)
			.subTitle2(subTitle2)
			.subTitle3(subTitle3)
			.build();
	}
}
