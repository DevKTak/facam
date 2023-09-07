package kdt_y_be_toy_project1.trip.dao;

import java.util.List;

import kdt_y_be_toy_project1.trip.domain.Trip;

public interface TripDAO {

	void save(Long tripId, String jsonTrip);

	List<Trip> findAll();
}
