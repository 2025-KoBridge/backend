package com.edu.kobridge.infra.api.chatgpt.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGptResDto {
	private String id;
	private String object;
	private long created_at;
	private String status;
	private List<Output> output;
	private Usage usage;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Output {
		private String id;
		private String type;
		private String status;
		private List<Content> content;
		private String role;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Content {
		private String type;
		private String text; // JSON 문자열 그대로 저장
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Usage {
		private int input_tokens;
		private int output_tokens;
		private int total_tokens;
	}
}