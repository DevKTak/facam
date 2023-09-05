package kdt_y_be_toy_project1.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TripResponse {
	private long tripId;
	private String tripName;
	private String startDate;
	private String endDate;
	private long itineraryCount;
	
}
