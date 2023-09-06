package kdt_y_be_toy_project1.trip.service;

import java.util.Random;

import kdt_y_be_toy_project1.trip.dao.TripDAO;
import kdt_y_be_toy_project1.trip.dao.TripJsonDAO;
import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripJsonService implements TripService {

	private final TripDAO tripDao = new TripJsonDAO();

	@Override
	public void save(CreateTripRequest dto) {
		Random random = new Random();
		Long tripId = random.nextLong();
		Trip trip = Trip.from(tripId, dto.tripName(), dto.startDate(), dto.endDate());

		tripDao.save(tripId, trip.toJson(trip));
	}

	@Override
	public TripResponse findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
