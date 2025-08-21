package com.edu.kobridge.module.lesson.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChatResDto {
	@Schema(description = "chat id", example = "1")
	private final Long id;

	@Schema(description = "질문", example = "숙제 있었는데, 너 다 했어?")
	private final String question;

	@Schema(description = "질문 번역본", example = "There was homework, did you finish it?")
	private final String questionTrans;

	public static ChatResDto of(Long id, String question, String questionTrans) {
		return ChatResDto.builder()
			.id(id)
			.question(question)
			.questionTrans(questionTrans)
			.build();
	}
}
