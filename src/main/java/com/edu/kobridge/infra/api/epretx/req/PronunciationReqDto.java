package com.edu.kobridge.infra.api.epretx.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PronunciationReqDto {
	private Argument argument;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Argument {
		private String language_code;
		private String script;
		private String audio;
	}
}
