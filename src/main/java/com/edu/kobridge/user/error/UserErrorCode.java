package com.edu.kobridge.user.error;

import org.springframework.http.HttpStatus;

import com.edu.kobridge.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {
	ID_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Id Token이 필요합니다."),
	INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 Id Token이 아닙니다."),
	GRADE_SIZE_ERROR(HttpStatus.BAD_REQUEST, "중학생, 고등학생은 3학년까지만 존재합니다.");

	private final HttpStatus httpStatus;
	private final String message;

	UserErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
