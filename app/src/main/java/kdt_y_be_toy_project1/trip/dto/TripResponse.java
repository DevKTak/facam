package kdt_y_be_toy_project1.trip.dto;

import kdt_y_be_toy_project1.trip.domain.TripCsv;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripResponse {
	private long tripId;
	private String tripName;
	private String startDate;
	private String endDate;

	
	public static TripResponse fromTripCsvDomain(TripCsv tripCsvDomain){
		return TripResponse.builder().tripId(tripCsvDomain.getTripId()).tripName(tripCsvDomain.getTripName())
		.startDate(tripCsvDomain.getStartDate()).endDate(tripCsvDomain.getEndDate()).build();
	}
	
}