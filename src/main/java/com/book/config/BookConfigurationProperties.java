package com.book.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "book.kakao")
public record BookConfigurationProperties(String restApiKey,String bookUrl) {
}
