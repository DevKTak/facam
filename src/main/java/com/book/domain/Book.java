package com.book.domain;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title; // 도서 제목

	private int price; // 도서 정가

	private String publisher; // 도서 출판사

	@ElementCollection
	private List<String> authors; // 저자 리스트

	private int salePrice; // 할인 가격

	private String isbn; // ISBN

	public static Book from(String title, int price, String publisher, List<String> authors, int salePrice, String isbn) {
		return Book.builder()
			.title(title)
			.price(price)
			.publisher(publisher)
			.authors(authors)
			.salePrice(salePrice)
			.isbn(isbn)
			.build();
	}
}
