package kdt_y_be_toy_project1.itinerary.entity;

import com.opencsv.bean.CsvBindByName;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ItineraryCSV {
  @CsvBindByName(column = "itinerary_id")
  private int itineraryId;

  @CsvBindByName(column = "departure_place")
  private String departurePlace;

  @CsvBindByName(column = "destination")
  private String destination;

  @CsvBindByName(column = "departure_time")
  private String departureTime;

  @CsvBindByName(column = "arrival_time")
  private String arrivalTime;

  @CsvBindByName(column = "check_in")
  private String checkIn;

  @CsvBindByName(column = "check_out")
  private String checkOut;

  public static ItineraryCSV from(AddItineraryRequest request) {
    return ItineraryCSV.builder()
        .itineraryId(request.getItineraryId())
        .departurePlace(request.getDeparturePlace())
        .destination(request.getDestination())
        .departureTime(String.valueOf(request.getDepartureTime()))
        .arrivalTime(String.valueOf(request.getArrivalTime()))
        .checkIn(String.valueOf(request.getCheckIn()))
        .checkOut(String.valueOf(request.getCheckOut()))
        .build();
  }
}
