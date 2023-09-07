package kdt_y_be_toy_project1.trip.domain;

import com.opencsv.bean.CsvBindByName;

import kdt_y_be_toy_project1.trip.dto.TripCsvRequest;
import kdt_y_be_toy_project1.trip.dto.TripCsvResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCsv {
	@CsvBindByName(column = "trip_id")
	private long tripId;
	@CsvBindByName(column = "trip_name")
	private String tripName;
	@CsvBindByName(column = "start_date")
	private String startDate;
	@CsvBindByName(column = "end_date")
	private String endDate;
	
	private long itineraryCount;
	
	public static TripCsv fromTripCsvRequest(TripCsvRequest tripRequest) {
		return TripCsv.builder().tripId(tripRequest.getTripId()).tripName(tripRequest.getTripName())
				.startDate(tripRequest.getStartDate()).endDate(tripRequest.getEndDate()).build();
	}
	public static TripCsv fromTripCsvResponse(TripCsvResponse tripResponse) {
		return TripCsv.builder().tripId(tripResponse.getTripId()).tripName(tripResponse.getTripName())
				.startDate(tripResponse.getStartDate()).endDate(tripResponse.getEndDate())
				.itineraryCount(tripResponse.getItineraryCount()).build();
	}
}