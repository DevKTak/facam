package com.book.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.adapter.persistence.BookRepository;
import com.book.application.port.FindBookQuery;
import com.book.common.util.RestApi;
import com.book.config.BookConfigurationProperties;
import com.book.domain.Book;
import com.book.dto.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindBookService implements FindBookQuery {

	@Qualifier("kakaoRestApi")
	private final RestApi kakaoRestApi;

	private final BookConfigurationProperties properties;

	private final Scanner scanner;

	private final BookRepository bookRepository;

	@Override
	public void findBooks(String query) {
		String restApi = kakaoRestApi.getRestApi(properties.bookUrl(), query);

		if (StringUtils.isNotBlank(restApi)) {
			ObjectMapper objectMapper = new ObjectMapper();
			ApiResponse apiResponse;

			try {
				apiResponse = objectMapper.readValue(restApi, ApiResponse.class);
			} catch (JsonProcessingException jsonProcessingException) {
				throw new RuntimeException("JSON processing error occurred", jsonProcessingException);
			}

			List<Book> books = new ArrayList<>();

			apiResponse.documents().stream().forEach(bookInfo -> {
					printBooks(bookInfo);

					books.add(Book.from(bookInfo.title(), bookInfo.price(), bookInfo.publisher(), bookInfo.authors(),
						bookInfo.sale_price(), bookInfo.isbn()));
				}
			);
			System.out.print("데이터베이스에 저장하시겠습니까? (Y | N) ");

			if ("Y".equals(scanner.nextLine())) {
				saveBooks(books);
			}
		}
	}

	private void printBooks(ApiResponse.BookResponse bookInfo) {
		StringBuilder sb = new StringBuilder();

		sb.append("도서 제목: ").append(bookInfo.title()).append(" | ")
			.append("가격: ").append(bookInfo.price()).append(" | ")
			.append("출판사: ").append(bookInfo.publisher()).append(" | ")
			.append("작가: ").append(bookInfo.authors().toString().replaceAll("[\\[\\]]", "")).append(" | ")
			.append("할인 가격: ").append(bookInfo.sale_price()).append(" | ")
			.append("ISBN: ").append(bookInfo.isbn()).append(System.lineSeparator());
		System.out.print(sb);
	}

	@Transactional
	void saveBooks(List<Book> books) {
		List<Book> result = bookRepository.saveAll(books);

		if (result.size() == books.size()) {
			System.out.println("저장 성공");

			List<Book> findBooks = bookRepository.findByOrderByTitleAsc();

			findBooks.stream().forEach(book -> {
				ApiResponse.BookResponse bookResponse = new ApiResponse.BookResponse(book.getTitle(),
					book.getPrice(), book.getPublisher(), book.getAuthors(), book.getSalePrice(), book.getIsbn());

				printBooks(bookResponse);
			});
		}
	}
}
