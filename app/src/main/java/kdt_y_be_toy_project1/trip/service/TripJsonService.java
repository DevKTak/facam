package kdt_y_be_toy_project1.trip.service;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripJsonDAO;
import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

public class TripJsonService implements TripService {

	private final TripJsonDAO tripJsonDAO = new TripJsonDAO();

	@Override
	public void save(CreateTripRequest dto) {
		Trip trip = Trip.from(nextTripId(), dto.tripName(), dto.startDate(), dto.endDate());
		tripJsonDAO.save(trip.getTripId(), trip.toJson(trip));
	}

	@Override
	public List<Trip> findAll() {
		return tripJsonDAO.findAll();
	}

	protected Long nextTripId() {
		File[] files = tripJsonDAO.getListFiles();

		if (files.length == 0) {
			return 1L;
		}
		String lastFileName = Arrays.stream(files).max(Comparator.comparing(file -> {
				System.out.println(file.lastModified());
				return file.lastModified();
			}
		)).orElseThrow().getName();

		int startIndex = lastFileName.lastIndexOf("_");
		int endIndex = lastFileName.lastIndexOf(".json");

		return Long.parseLong(lastFileName.substring(startIndex + 1, endIndex)) + 1;
	}
}