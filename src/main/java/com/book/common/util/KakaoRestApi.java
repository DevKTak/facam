package com.book.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.book.config.BookConfigurationProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoRestApi implements RestApi {

	private final RestTemplate restTemplate;

	private final BookConfigurationProperties properties;

	public String getRestApi(String url, String query) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + properties.restApiKey());

		return restTemplate.exchange(
			url + "?" + query,
			HttpMethod.GET,
			new HttpEntity<>(headers),
			String.class).getBody();
	}
}
