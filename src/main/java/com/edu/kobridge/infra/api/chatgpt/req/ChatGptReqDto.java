package com.edu.kobridge.infra.api.chatgpt.req;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChatGptReqDto {
	private final String model;
	private final List<Message> messages;

	@Getter
	@Builder(access = AccessLevel.PRIVATE)
	public static class Message {
		private final String role;
		private final String content;

		public static Message of(String role, String content) {
			return Message.builder()
				.role(role)
				.content(content)
				.build();
		}
	}

	public static ChatGptReqDto of(String model, List<Message> messages) {
		return ChatGptReqDto.builder()
			.model(model)
			.messages(messages)
			.build();
	}
}
