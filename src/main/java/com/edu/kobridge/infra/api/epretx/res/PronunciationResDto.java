package com.edu.kobridge.infra.api.epretx.res;

import lombok.Data;

@Data
public class PronunciationResDto {
	private String request_id;
	private int result;
	private String return_type;
	private ReturnObject return_object;

	@Data
	public static class ReturnObject {
		private String recognized;
		private float score;
	}
}
