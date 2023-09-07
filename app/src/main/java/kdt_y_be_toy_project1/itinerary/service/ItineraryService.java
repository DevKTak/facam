package kdt_y_be_toy_project1.itinerary.service;

import kdt_y_be_toy_project1.itinerary.dao.ItineraryCSVDao;
import kdt_y_be_toy_project1.itinerary.dao.ItineraryJSONDao;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import kdt_y_be_toy_project1.itinerary.dto.ItineraryResponse;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.type.FileType;

import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.JSON;

public class ItineraryService {

  private final ItineraryJSONDao jsonDao = new ItineraryJSONDao();
  private final ItineraryCSVDao csvDao = new ItineraryCSVDao();

  public List<ItineraryResponse> getAllItineraryList(int tripId, FileType type) {
    // fileApi를 통해 Itinerary 객체 리스트를 받아온다
    if (type.equals(JSON)) {
      return jsonDao.getItineraryListFromFile(tripId)
          .stream().map(ItineraryResponse::fromJSONEntity)
          .toList();
    } else {
      return csvDao.getItineraryListFromFile(tripId)
          .stream().map(ItineraryResponse::fromCSVEntity)
          .toList();
    }
  }

  public ItineraryResponse getItinerary(int tripId, int itineraryId, FileType type) {
    // fileApi를 통해 Itinerary 객체를 받아온다
    if (type.equals(JSON)) {
      return ItineraryResponse
          .fromJSONEntity(jsonDao.getItineraryFromFile(tripId, itineraryId));
    } else {
      return ItineraryResponse
          .fromCSVEntity(csvDao.getItineraryFromFile(tripId, itineraryId));
    }
  }

  public void addItinerary(int tripId, AddItineraryRequest request) {
    // fileApi를 통해 Itinerary 객체를 넣는다
    csvDao.addItineraryToFile(tripId, ItineraryCSV.from(request));
    jsonDao.addItineraryToFile(tripId, ItineraryJSON.from(request));
  }
}