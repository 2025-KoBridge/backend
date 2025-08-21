package com.edu.kobridge.infra.api.chatgpt.error;

import org.springframework.http.HttpStatus;

import com.edu.kobridge.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum ChatGptErrorCode implements ErrorCode {
	CHAT_GPT_CHAT_API_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "chat gpt chat 답변 기능 사용 불가");

	private final HttpStatus httpStatus;
	private final String message;

	ChatGptErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
