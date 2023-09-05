package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;

import java.util.List;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {
  @Override
  public List<ItineraryJSON> getItineraryListFromFile(int tripId) {
    return null;
  }

  @Override
  public ItineraryJSON getItineraryFromFile(int tripId, int itineraryId) {
    return null;
  }

  @Override
  public void addItineraryToFile(int tripId, ItineraryJSON itinerary) {

  }
}
