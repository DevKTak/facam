package com.book.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(ignoreUnknown = true): Jackson 라이브러리를 사용하여 JSON을 Java 객체로 변환할 때,
// 알려지지 않은 속성들을 무시하도록 지정하는 어노테이션
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(List<BookResponse> documents) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record BookResponse(String title, int price, String publisher, List<String> authors, int sale_price,
							   String isbn) {
	}
}
