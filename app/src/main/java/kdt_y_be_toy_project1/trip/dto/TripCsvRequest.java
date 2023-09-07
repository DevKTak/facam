package kdt_y_be_toy_project1.trip.dto;

import kdt_y_be_toy_project1.trip.domain.TripCsv;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripCsvRequest {
	private long tripId;
	private String tripName;
	private String startDate;
	private String endDate;
	
	public static TripCsvRequest fromTripCsvDomain(TripCsv tripCsvDomain){
		return TripCsvRequest.builder().tripId(tripCsvDomain.getTripId()).tripName(tripCsvDomain.getTripName())
		.startDate(tripCsvDomain.getStartDate()).endDate(tripCsvDomain.getEndDate()).build();
	}
}
