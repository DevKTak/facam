package kdt_y_be_toy_project1.trip.dao;

import java.io.FileWriter;
import java.io.IOException;

import kdt_y_be_toy_project1.trip.domain.Trip;

public class TripJsonDAO implements TripDAO {

	private final String BASE_PATH = "src/main/resources/trip/json";

	@Override
	public void save(Long tripId, String jsonTrip) {
		try (FileWriter fileWriter = new FileWriter(BASE_PATH + "/trip_" + tripId + ".json")) {
			fileWriter.write(jsonTrip);
		} catch (IOException e) {
			throw new RuntimeException("JSON 파일 저장 실패", e);
		}
	}

	@Override
	public Trip findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
