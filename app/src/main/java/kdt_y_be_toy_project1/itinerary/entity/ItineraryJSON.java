package kdt_y_be_toy_project1.itinerary.entity;

import com.google.gson.annotations.SerializedName;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ItineraryJSON {
  @SerializedName("itinerary_id")
  private int itineraryId;

  @SerializedName("departure_place")
  private String departurePlace;

  @SerializedName("destination")
  private String destination;

  @SerializedName("departure_time")
  private String departureTime;

  @SerializedName("arrival_time")
  private String arrivalTime;

  @SerializedName("check_in")
  private String checkIn;

  @SerializedName("check_out")
  private String checkOut;

  public static ItineraryJSON from(AddItineraryRequest request) {
    return ItineraryJSON.builder()
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
