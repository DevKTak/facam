package kdt_y_be_toy_project1.itinerary.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Itinerary {
  int itineraryId;
  String departurePlace;
  String destination;
  String departureTime;
  String arrivalTime;
  String checkIn;
  String checkOut;
}
