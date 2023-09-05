package kdt_y_be_toy_project1.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTripRequest {
	long tripId;
	String tripName;
	String startDate;
	String endDate;
}