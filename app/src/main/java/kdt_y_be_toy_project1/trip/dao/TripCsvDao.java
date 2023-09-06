package kdt_y_be_toy_project1.trip.dao;

import java.util.List;

import kdt_y_be_toy_project1.trip.domain.Trip;
import kdt_y_be_toy_project1.trip.domain.TripCsv;
import kdt_y_be_toy_project1.trip.util.CsvConversion;

public class TripCsvDao implements TripDao {
	
	private final CsvConversion csvConversion=new CsvConversion();

	public long saveTrip(TripCsv tripCsv) {
		return csvConversion.saveAsCsv(tripCsv);
	}

	public List<TripCsv> findAllTrips(){
		return csvConversion.saveAsTrip();
	}

	/**
	 * @deprecated
	 */
	@Override
	public Trip findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @deprecated
	 */
	@Override
	public void save(Trip trip) {
		// TODO Auto-generated method stub
	}

}
