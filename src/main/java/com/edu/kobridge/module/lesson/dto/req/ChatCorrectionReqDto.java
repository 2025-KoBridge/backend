package com.edu.kobridge.module.lesson.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "채팅 답변 교정 요청 DTO")
public record ChatCorrectionReqDto(
	@Schema(description = "채팅 답변", example = "만나서 반가워!")
	@NotBlank(message = "답변 값은 필수입니다.")
	String answer
) {
}
