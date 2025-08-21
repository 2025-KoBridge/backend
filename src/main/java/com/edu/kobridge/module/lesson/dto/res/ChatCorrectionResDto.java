package com.edu.kobridge.module.lesson.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChatCorrectionResDto {
	@Schema(description = "번역된 문장", example = "I know. When does class start?")
	private final String answerTrans;

	@Schema(description = "교정된 문장", example = "그러게~ 수업 언제 시작해?")
	private final String correction;

	@Schema(description = "교정된 이유", example = "은제’는 ‘언제’로 교정해야 해요. '시작햐'는 '시작해'로 수정해야 해요.")
	private final String reason;

	@Schema(description = "답변에 대한 대답", example = "곧 시작할 것 같아~")
	private final String response;

	@Schema(description = "다음 chat 정보")
	private final ChatResDto nextChat;

	public static ChatCorrectionResDto of(String answerTrans, String correction, String reason, String response,
		ChatResDto chatRes) {
		return ChatCorrectionResDto.builder()
			.answerTrans(answerTrans)
			.correction(correction)
			.reason(reason)
			.response(response)
			.nextChat(chatRes)
			.build();
	}
}
