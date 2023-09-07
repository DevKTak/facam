package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kdt_y_be_toy_project1.trip.TripTestFixture;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

class TripJsonServiceTest {

	private final TripService tripService = new TripJsonService();

	@Test
	@DisplayName("여행 정보 저장")
	public void 여행_기록() {
		// given
		CreateTripRequest createTripRequest = TripTestFixture.getCreateTripRequest();

		// when
		List<TripResponse> trips = tripService.findAll();
		tripService.save(createTripRequest);
		List<TripResponse> trips2 = tripService.findAll();

		// then
		Assertions.assertThat(trips2.size()).isEqualTo(trips.size() + 1);
	}

	@Test
	@DisplayName("여행 정보 조회")
	public void 여행_조회() {
		// given

		// when
		List<TripResponse> trips = tripService.findAll();

		// then
		System.out.println(trips);
	}

	@Test
	@DisplayName("새로 등록될 여행 ID 생성")
	public void 여행_아이디_생성() {
		// given
		NextTripIdTest nextTripIdTest = new NextTripIdTest();
		// when
		Long nextTripId = nextTripIdTest.callNextTripId();

		// then
		System.out.println(nextTripId);
	}
}
