package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripCsvDAO;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripCsvService {
	
	private final TripCsvDAO tripCsvDao=new TripCsvDAO();
	
	/**
	 * TripRequest를 CSV파일로 저장한다.
	 * @param tripRequest
	 * @return tripId
	 */
	public long saveTrip(CreateTripRequest tripRequest) {
		return tripCsvDao.saveTrip(TripCsv.fromTripRequest(tripRequest));
	}

	/**
	 * 모든 resources/format/trip.csv 파일을 List<TripCsvResponse로 변환해 반환
	 * @return List<TripCsvResponse>
	 */
	public List<TripResponse> findAllTrips(){
		return tripCsvDao.findAllTrips().stream().map(tripCsvDomain->TripCsvToTripResponse(tripCsvDomain)).toList();
	}
	
	public TripResponse TripCsvToTripResponse(TripCsv tripCsv) {
		if(tripCsv==null)
			throw new RuntimeException("TripCsv 객체가 NULL값입니다.");
		return new TripResponse(tripCsv.getTripId(), tripCsv.getTripName(), tripCsv.getStartDate(), tripCsv.getEndDate());
	}

}
