package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripCsvDAO;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.dto.TripCsvRequest;
import kdt_y_be_toy_project1.trip.dto.TripCsvResponse;

public class TripCsvService{
	
	private final TripCsvDAO tripCsvDao=new TripCsvDAO();
	
	/**
	 * TripRequest를 CSV파일로 저장한다.
	 * @param tripRequest
	 * @return tripId
	 */
	public long saveTrip(TripCsvRequest tripRequest) {
		return tripCsvDao.saveTrip(TripCsv.fromTripCsvRequest(tripRequest));
	}

	/**
	 * 모든 resources/format/trip.csv 파일을 List<TripCsvResponse로 변환해 반환
	 * @return List<TripCsvResponse>
	 */
	public List<TripCsvResponse> findAllTrips(){
		return tripCsvDao.findAllTrips().stream().map(tripCsvDomain->TripCsvResponse.fromTripCsvDomain(tripCsvDomain)).toList();
	}
	
}
