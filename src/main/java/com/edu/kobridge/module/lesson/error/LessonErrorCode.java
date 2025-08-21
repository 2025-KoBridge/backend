package com.edu.kobridge.module.lesson.error;

import org.springframework.http.HttpStatus;

import com.edu.kobridge.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum LessonErrorCode implements ErrorCode {

	LESSON_CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 질문을 찾을 수 없습니다."),
	LESSON_SENTENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 레슨 문장을 찾을 수 없습니다."),
	LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 레슨을 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	LessonErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
