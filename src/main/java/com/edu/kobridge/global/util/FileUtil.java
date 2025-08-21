package com.edu.kobridge.global.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.edu.kobridge.global.error.GlobalErrorCode;
import com.edu.kobridge.global.error.exception.AppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtil {
	public String convertS3UrlToBase64(String s3Url) {
		try (InputStream in = new URL(s3Url).openStream()) {
			byte[] fileBytes = in.readAllBytes();
			return Base64.getEncoder().encodeToString(fileBytes);
		} catch (IOException e) {
			log.error("[FileUtil] file convert error -- " + e.getMessage());
			throw new AppException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
