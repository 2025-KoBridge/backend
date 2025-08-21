package com.edu.kobridge.infra.api.chatgpt;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.infra.api.chatgpt.error.ChatGptErrorCode;
import com.edu.kobridge.infra.api.chatgpt.res.ChatGptCorrectionResDto;
import com.edu.kobridge.infra.api.chatgpt.res.ChatGptResDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChatGptService {

	@Value("${api.chat-gpt.key}")
	private String key;

	@Value("${api.chat-gpt.model}")
	private String model;

	private final WebClient chatGptWebClient;

	public ChatGptCorrectionResDto postAnswerCorrectionAndResponse(String userSentence, Boolean isNextChatExist,
		LangType lang) {
		String userPrompt = String.format(
			"User sentence: %s | NextChat: %s | Language: %s:",
			userSentence, isNextChatExist, lang.getName()
		);

		Map<String, Object> request = Map.of(
			"model", model,
			"input", List.of(
				Map.of("role", "system", "content",
					"너는 한국어 학습자를 위한 교정 선생님이자 친근한 대화 파트너야. " +
						"항상 아래 JSON 형식으로만 답해. 출력은 반드시 한국어로 해.\n\n" +
						"{ \"correction\": \"교정된 한국어 문장\", " +
						"\"reason\": \"제공하는 교정 이유 (선생님 톤)\", " +
						"\"translation\": \"Language 로 번역된 correction\", " +
						"\"response\": \"교정된 문장에 대한 답변 / isNextChatExist 이 false 이면 답변 후 대화 마무리\"}\n\n"
						+
						"규칙:\n" +
						"- correction / reason → 선생님 톤\n" +
						"- response → 친구처럼 친근한 톤\n" +
						"- 반드시 유효한 JSON만 출력하고, JSON 외의 다른 텍스트는 출력하지 마."
				),
				Map.of("role", "user", "content", userPrompt)
			),
			"temperature", 0.7
		);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ChatGptResDto chatGptRes = chatGptWebClient.post()
				.header("Authorization", "Bearer " + key)
				.bodyValue(request)
				.retrieve()
				.onStatus(HttpStatusCode::isError, resp ->
					resp.bodyToMono(String.class)
						.flatMap(body -> {
							log.error("[ChatGPT API] connection error --  Status: {}, Body: {}",
								resp.statusCode(), body);
							return Mono.error(new AppException(ChatGptErrorCode.CHAT_GPT_CHAT_API_FAILED));
						})
				)
				.bodyToMono(ChatGptResDto.class)
				.block();

			String jsonContent = chatGptRes.getOutput().get(0).getContent().get(0).getText();

			return objectMapper.readValue(jsonContent, ChatGptCorrectionResDto.class);

		} catch (Exception e) {
			log.error("[ChatGPT Chat API] internal error -- " + e.getMessage(), e);
			throw new AppException(ChatGptErrorCode.CHAT_GPT_CHAT_API_FAILED);
		}
	}
}