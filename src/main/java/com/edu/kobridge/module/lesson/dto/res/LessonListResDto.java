package com.edu.kobridge.module.lesson.dto.res;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LessonListResDto {
	@Schema(description = "현재 레벨", example = "1")
	private final int level;

	@Schema(description = "레슨 전체 리스트")
	private final List<LessonBriefResDto> lessons;

	public static LessonListResDto of(int level, List<LessonBriefResDto> lessons) {
		return LessonListResDto.builder()
			.level(level)
			.lessons(lessons)
			.build();
	}
}
