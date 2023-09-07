package kdt_y_be_toy_project1.trip.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kdt_y_be_toy_project1.trip.TripTestFixture;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

class TripJsonServiceTest {

	@Test
	@DisplayName("여행 정보 저장")
	public void 여행_기록() {
		// given
		CreateTripRequest createTripRequest = TripTestFixture.getCreateTripRequest();

		// when
		TripService tripService = new TripJsonService();
		tripService.save(createTripRequest);

		// then
	}
}
