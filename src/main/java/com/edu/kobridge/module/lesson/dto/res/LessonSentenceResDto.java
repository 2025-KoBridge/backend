package com.edu.kobridge.module.lesson.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LessonSentenceResDto {

	@Schema(description = "문장 id", example = "1")
	private final Long id;

	@Schema(description = "오늘의 문장", example = "이름이 뭐야?")
	private final String sentence;

	@Schema(description = "문장 번역", example = "What's your name?")
	private final String translation;

	@Schema(description = "문장 발음", example = "I-reum-i mwoya?")
	private final String pronunciation;

	public static LessonSentenceResDto of(Long id, String sentence, String translation, String pronunciation) {
		return LessonSentenceResDto.builder()
			.id(id)
			.sentence(sentence)
			.translation(translation)
			.pronunciation(pronunciation)
			.build();
	}
}
