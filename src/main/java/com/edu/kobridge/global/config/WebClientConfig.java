package com.edu.kobridge.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder()
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}

	@Bean(name = "pronunciationWebClient")
	public WebClient pronunciationWebClient(WebClient.Builder builder,
		@Value("${api.e-pre-tx.url}") String baseUrl) {
		return builder.baseUrl(baseUrl).build();
	}

	@Bean(name = "chatGptWebClient")
	public WebClient chatGptWebClient(WebClient.Builder builder,
		@Value("${api.chat-gpt.url}") String baseUrl) {
		return builder.baseUrl(baseUrl).build();
	}
}
