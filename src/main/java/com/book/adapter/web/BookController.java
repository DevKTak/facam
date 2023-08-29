package com.book.adapter.web;

import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.application.port.FindBookQuery;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final FindBookQuery findBookQuery;

	private final Scanner scanner;

	@GetMapping
	public void getBooks() {
		System.out.print("도서를 검색할 제목을 입력하세요: ");
		String bookName = scanner.nextLine();

		findBookQuery.findBooks("query=" + bookName);
	}
}
