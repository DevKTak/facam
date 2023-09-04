package kdt_y_be_toy_project1.itinerary.service;

import kdt_y_be_toy_project1.itinerary.dao.ItineraryCSVDao;
import kdt_y_be_toy_project1.itinerary.dao.ItineraryJSONDao;
import kdt_y_be_toy_project1.itinerary.entity.Itinerary;
import kdt_y_be_toy_project1.itinerary.type.FileType;

import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.JSON;

public class ItineraryService {

  private ItineraryJSONDao jsonDao = new ItineraryJSONDao();
  private ItineraryCSVDao csvDao = new ItineraryCSVDao();

  List<Itinerary> getAllItineraryList(int tripId, FileType type) {
    // fileApi를 통해 Itinerary 객체 리스트를 받아온다
    if (type.equals(JSON)) {
      return jsonDao.getItineraryListFromFile(tripId);
    } else {
      return csvDao.getItineraryListFromFile(tripId);
    }
  }

  Itinerary getItinerary(int tripId, int itineraryId, FileType type) {
    // fileApi를 통해 Itinerary 객체를 받아온다
    if (type.equals(JSON)) {
      return jsonDao.getItineraryFromFile(tripId, itineraryId);
    } else {
      return csvDao.getItineraryFromFile(tripId, itineraryId);
    }
  }

  void addItinerary(int tripId, Itinerary itinerary) {
    // fileApi를 통해 Itinerary 객체를 넣는다
    csvDao.addItineraryToFile(tripId, itinerary);
    jsonDao.addItineraryToFile(tripId, itinerary);
  }
}
