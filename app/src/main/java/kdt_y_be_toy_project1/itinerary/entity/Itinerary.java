package kdt_y_be_toy_project1.itinerary.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Itinerary {
  @CsvBindByName(column = "itinerary_id")
  int itineraryId;

  @CsvBindByName(column = "departure_place")
  String departurePlace;

  @CsvBindByName(column = "destination")
  String destination;

  @CsvBindByName(column = "departure_time")
  String departureTime;

  @CsvBindByName(column = "arrival_time")
  String arrivalTime;

  @CsvBindByName(column = "check_in")
  String checkIn;

  @CsvBindByName(column = "check_out")
  String checkOut;
}
