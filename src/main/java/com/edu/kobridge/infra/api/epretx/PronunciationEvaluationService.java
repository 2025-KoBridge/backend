package com.edu.kobridge.infra.api.epretx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.infra.api.epretx.error.PronunciationErrorCode;
import com.edu.kobridge.infra.api.epretx.req.PronunciationReqDto;
import com.edu.kobridge.infra.api.epretx.res.PronunciationResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PronunciationEvaluationService {

	@Value("${api.e-pre-tx.uri}")
	private String uri;

	@Value("${api.e-pre-tx.key}")
	private String key;

	private final WebClient pronunciationWebClient;

	public PronunciationResDto checkPronunciation(String script, String audioBase64) {
		PronunciationReqDto pronunciationReq = new PronunciationReqDto(
			new PronunciationReqDto.Argument("korean", script, audioBase64)
		);

		try {
			return pronunciationWebClient.post()
				.uri(uri)
				.header("Authorization", key)
				.bodyValue(pronunciationReq)
				.retrieve()
				.bodyToMono(PronunciationResDto.class)
				.block();

		} catch (WebClientResponseException e) {
			switch (e.getStatusCode().value()) {
				case 403: // FORBIDDEN
					log.error("[Pronunciation API] api key or else -- {}", e.getResponseBodyAsString());
					throw new AppException(PronunciationErrorCode.PRONUNCIATION_INVALID_KEY);

				case 408: // REQUEST_TIMEOUT
					throw new AppException(PronunciationErrorCode.PRONUNCIATION_TIMEOUT);

				case 413: // PAYLOAD_TOO_LARGE
					throw new AppException(PronunciationErrorCode.PRONUNCIATION_BODY_TOO_LARGE);

				case 429: // TOO_MANY_REQUESTS
					throw new AppException(PronunciationErrorCode.PRONUNCIATION_LIMIT_EXCEEDED);

				default:
					if (e.getStatusCode().is5xxServerError()) {
						log.error("[Pronunciation API] external server error -- {}", e.getResponseBodyAsString());
						throw new AppException(PronunciationErrorCode.PRONUNCIATION_API_SERVER_ERROR);
					} else {
						log.error("[Pronunciation API] unexpected error -- {}", e.getResponseBodyAsString(), e);
						throw new AppException(PronunciationErrorCode.PRONUNCIATION_EVALUATION_API_FAILED);
					}
			}

		} catch (Exception e) {
			log.error("[Pronunciation API] internal error -- " + e.getMessage());
			throw new AppException(PronunciationErrorCode.PRONUNCIATION_EVALUATION_API_FAILED);
		}
	}
}