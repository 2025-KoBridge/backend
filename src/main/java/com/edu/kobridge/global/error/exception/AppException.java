package com.edu.kobridge.global.error.exception;

import com.edu.kobridge.global.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
	private ErrorCode errorCode;
	private String message;

	public AppException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
