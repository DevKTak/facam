package kdt_y_be_toy_project1.itinerary.util;

import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import org.json.JSONObject;

public class JSONConversion {

  //obj 반환값 생각하기
  static String objToJson(ItineraryJSON itinerary) {

    if(itinerary == null){
      throw new NullPointerException("Itinerary is null");
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("departurePlace", itinerary.getDeparturePlace())
            .put("destination", itinerary.getDestination())
            .put("departureTime", itinerary.getDepartureTime())
            .put("arrivalTime", itinerary.getArrivalTime())
            .put("checkIn", itinerary.getCheckIn())
            .put("checkOut", itinerary.getCheckOut());
    return jsonObject.toString();
  }

  static ItineraryJSON jsonToObj(JSONObject jsonObject) {

    if(jsonObject == null){
      throw new NullPointerException("JSONObject is null");
    }

    return ItineraryJSON.builder()
            .departurePlace(jsonObject.getString("departurePlace"))
            .destination(jsonObject.getString("destination"))
            .departureTime(jsonObject.getString("departureTime"))
            .arrivalTime(jsonObject.getString("arrivalTime"))
            .checkIn(jsonObject.getString("checkIn"))
            .checkOut(jsonObject.getString("checkIn"))
            .build();
  }
}
