package com.edu.kobridge.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.edu.kobridge.global.common.filter.JwtAuthorizationFilter;
import com.edu.kobridge.global.util.JwtUtil;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	private final JwtUtil jwtUtil;

	// jwt 필터 등록
	@Bean
	public FilterRegistrationBean<Filter> filterBean() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(
			new JwtAuthorizationFilter(jwtUtil));
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*");

		return filterRegistrationBean;
	}
}
