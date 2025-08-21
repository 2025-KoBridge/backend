package com.edu.kobridge.infra.api.chatgpt.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptCorrectionResDto {
	private String correction;
	private String reason;
	private String translation;
	private String response;
}
