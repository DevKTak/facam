package kdt_y_be_toy_project1.trip.service;

import java.util.List;

import kdt_y_be_toy_project1.trip.dao.TripCsvDao;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripCsvRequest;
import kdt_y_be_toy_project1.trip.dto.TripCsvResponse;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class TripCsvService implements TripService{
	
	private final TripCsvDao tripCsvDao=new TripCsvDao();
	
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
	
	
	/**
	 * @deprecated
	 */
	@Override
	public void save(CreateTripRequest trip) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @deprecated
	 */
	@Override
	public TripResponse findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
