package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;

public interface TripService {

	void save(CreateTripRequest tripRequestDto);

	List<Trip> findAll();
}
