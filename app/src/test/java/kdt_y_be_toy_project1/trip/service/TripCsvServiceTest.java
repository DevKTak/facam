package kdt_y_be_toy_project1.trip.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kdt_y_be_toy_project1.trip.TripTestFixture;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

class TripCsvServiceTest {

	private final TripCsvService tripService = new TripCsvService();

	@Test
	@DisplayName("여행 정보 저장")
	public void 여행_기록() {
		// given
		CreateTripRequest createTripRequest = TripTestFixture.getCreateTripRequest();
		// when
		tripService.saveTrip(createTripRequest);
		// then
	}

	@Test
	@DisplayName("여행 정보 조회")
	public void 여행_조회() {
		// given

		// when
		tripService.findAllTrips().forEach(x->System.out.println(x.getTripId()+" "+x.getTripName()+" "
				+x.getStartDate()+" "+x.getEndDate()));


	}

}
