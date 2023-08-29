package com.book.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponse(List<BookResponse> documents) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record BookResponse(String title, int price, String publisher, List<String> authors, int sale_price,
							   String isbn) {
	}
}
