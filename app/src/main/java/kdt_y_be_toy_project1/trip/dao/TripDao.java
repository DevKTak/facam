package kdt_y_be_toy_project1.trip.dao;

import kdt_y_be_toy_project1.trip.domain.Trip;

public interface TripDao {
	
	void save(Trip trip);
	
	Trip findAll();
}
