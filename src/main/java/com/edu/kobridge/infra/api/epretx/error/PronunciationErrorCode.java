package com.edu.kobridge.infra.api.epretx.error;

import org.springframework.http.HttpStatus;

import com.edu.kobridge.global.error.ErrorCode;

import lombok.Getter;

@Getter
public enum PronunciationErrorCode implements ErrorCode {
	PRONUNCIATION_INVALID_KEY(HttpStatus.FORBIDDEN, "API 키 문제 또는 접근 권한 없음"),
	PRONUNCIATION_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "발음 평가 API 요청 시간 초과"),
	PRONUNCIATION_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "호출 제한 횟수 초과"),
	PRONUNCIATION_BODY_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "전송된 오디오 데이터가 너무 큼"),
	PRONUNCIATION_API_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "외부 발음 평가 서버 오류"),
	PRONUNCIATION_EVALUATION_API_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "발음 평가 서비스 사용 불가");

	private final HttpStatus httpStatus;
	private final String message;

	PronunciationErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
