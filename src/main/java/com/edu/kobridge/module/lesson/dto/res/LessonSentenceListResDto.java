package com.edu.kobridge.module.lesson.dto.res;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class LessonSentenceListResDto {
	@Schema(description = "주제", example = "오늘은 학교에 가는 상황에서 자주 쓰는 표현들을 배워봐요. 교실에서 친구와 이야기할 때 유용하게 쓸 수 있어요.")
	private final String subject;

	@Schema(description = "레슨 문장 리스트")
	private final List<LessonSentenceResDto> lessonSentences;

	@Schema(description = "대화 시작 chat 정보")
	private final ChatResDto startChat;

	public static LessonSentenceListResDto of(String subject, List<LessonSentenceResDto> lessonSentences,
		ChatResDto chatRes) {
		return LessonSentenceListResDto.builder()
			.subject(subject)
			.lessonSentences(lessonSentences)
			.startChat(chatRes)
			.build();
	}
}
