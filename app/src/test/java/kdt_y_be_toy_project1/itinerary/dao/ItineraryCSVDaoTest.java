package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.itinerary.entity.Itinerary;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItineraryCSVDaoTest {

  private final ItineraryCSVDao dao = new ItineraryCSVDao();

  @Test
  void shouldGetItineraryListFromFile() {
    int tripId = 1;
    String expected = "[Itinerary(itineraryId=1, departurePlace=City X, destination=City Y, departureTime=2023-08-15T08:00:00, arrivalTime=2023-08-15T10:00:00, checkIn=2023-08-15T12:00:00, checkOut=2023-08-30T10:00:00)]";
    List<Itinerary> itineraries = dao.getItineraryListFromFile(tripId);
    assertEquals(expected, Arrays.toString(itineraries.toArray()));
  }

  @Test
  void shouldGetItineraryFromFile() {
    int tripId = 1;
    int itineraryId = 1;
    String expected = "Itinerary(itineraryId=1, departurePlace=City X, destination=City Y, departureTime=2023-08-15T08:00:00, arrivalTime=2023-08-15T10:00:00, checkIn=2023-08-15T12:00:00, checkOut=2023-08-30T10:00:00)";
    Itinerary itinerary = dao.getItineraryFromFile(tripId, itineraryId);
    assertEquals(expected, itinerary.toString());
  }

  @Test
  void shouldAddItineraryToFile() {
    int tripId = 1;
    Itinerary itinerary = Itinerary.builder()
        .departurePlace("City X")
        .destination("City Y")
        .departureTime("2023-08-15T08:00:00")
        .arrivalTime("2023-08-15T10:00:00")
        .checkIn("2023-08-15T12:00:00")
        .checkOut("2023-08-30T10:00:00")
        .build();
    dao.addItineraryToFile(1, itinerary);
    List<Itinerary> itineraries = dao.getItineraryListFromFile(tripId);
    assertEquals(itineraries.get(itineraries.size() - 1), itinerary);
  }

  @AfterAll
  static void afterAll() {
    // 임시로 경로 설정
    String testFilePathStr = "out/test/resources/itinerary/csv/itineraries_trip_1.csv";

    try {
      Files.delete(Path.of(testFilePathStr).toAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}