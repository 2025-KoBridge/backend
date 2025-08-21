package com.edu.kobridge.module.lesson.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "발음 평가 요청 DTO")
public record PronunciationEvaluationReqDto(
	@Schema(description = "오디오 url", example = "https://{buket-url}/pronumciation-evaluation-audio/{audio-url-name}.m4a")
	@NotBlank(message = "오디오 url은 필수 값입니다.")
	String audioUrl
) {
}
