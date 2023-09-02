package com.book.adapter.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.application.port.FindBookQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Deprecated
public class BookController {

	private final FindBookQuery findBookQuery;

	@GetMapping
	public void getBooks() {
		findBookQuery.searchBooks();
	}
}
