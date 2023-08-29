package com.book.application.port;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FindBookQueryTest {

	@Autowired
	FindBookQuery sut;

	@Test
	@DisplayName("입력받은 도서를 검색한다.")
	void 도서_검색() {
		// given

		// when

		//then
	}
}
