package kdt_y_be_toy_project1.trip.service;

import kdt_y_be_toy_project1.trip.dto.TripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public interface TripService {

	void save(TripRequest trip);
	
	TripResponse findAll();
}
