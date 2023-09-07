package kdt_y_be_toy_project1.itinerary.service;

import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryTestDataFileProvider;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import kdt_y_be_toy_project1.itinerary.dto.ItineraryResponse;
import kdt_y_be_toy_project1.itinerary.type.FileType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ItineraryServiceTest {

  String departurePlace = "City X";
  String destination = "City Y";
  String departureTime = "2023-08-15T08:00:00";
  String arrivalTime = "2023-08-15T10:00:00";
  String checkIn = "2023-08-15T12:00:00";
  String checkOut = "2023-08-30T10:00:00";

  private static ItineraryService itineraryService;

  @BeforeAll
  static void beforeAll() {
    DataFileProvider dataFileProvider = new ItineraryTestDataFileProvider();

    itineraryService = new ItineraryService(dataFileProvider);

    File itineraryTestCSVDataFile = dataFileProvider.getDataFile(1, FileType.CSV);
    File itineraryTestJSONDataFile = dataFileProvider.getDataFile(1, FileType.JSON);

    itineraryTestCSVDataFile.deleteOnExit();
    itineraryTestJSONDataFile.deleteOnExit();
  }

  @DisplayName("1. 한 여행에 있는 여정 리스트를")
  @Order(1)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  @Nested
  class AddItinerary {

    int tripId = 1;

    @DisplayName("JSON 파일과 CSV 파일에 저장할 수 있어야 함")
    @Order(1)
    @Test
    void addItineraryToJsonFileAndCsvFile() {
      AddItineraryRequest request = AddItineraryRequest.builder()
          .departurePlace(departurePlace)
          .destination(destination)
          .departureTime(toLocalDateTime(departureTime))
          .arrivalTime(toLocalDateTime(arrivalTime))
          .checkIn(toLocalDateTime(checkIn))
          .checkOut(toLocalDateTime(checkOut))
          .build();

      itineraryService.addItinerary(tripId, request);
    }

    @DisplayName("JSON 파일에 넣은 내용이 기대하는 것과 일치해야 함")
    @Order(2)
    @Test
    void shouldEqualTOExpectedItineraryList() {
      List<ItineraryResponse> itineraryJSONResponseList =
          itineraryService.getAllItineraryList(1, FileType.JSON);

      itineraryJSONResponseList.forEach(System.out::println);

      compareItineraryAttributes(itineraryJSONResponseList.get(0));
    }

    @DisplayName("CSV 파일에 넣은 내용이 기대하는 것과 일치해야 함")
    @Order(3)
    @Test
    void getAllItineraryListFromCSV() {
      List<ItineraryResponse> itineraryCSVResponseList =
          itineraryService.getAllItineraryList(1, FileType.CSV);

      itineraryCSVResponseList.forEach(System.out::println);

      compareItineraryAttributes(itineraryCSVResponseList.get(0));
    }
  }

  @DisplayName("2. 한 여행에 있는 모든 여정 리스트를")
  @Order(2)
  @Nested
  class GetItineraryList {

    int tripId = 1;

    @DisplayName("JSON 파일에서 가져올 수 있어야 함")
    @Test
    void getAllItineraryListFromJSON() {
      List<ItineraryResponse> itineraryJSONResponseList =
          itineraryService.getAllItineraryList(tripId, FileType.JSON);
      itineraryJSONResponseList.forEach(System.out::println);
    }

    @DisplayName("CSV 파일에서 가져올 수 있어야 함")
    @Test
    void getAllItineraryListFromCSV() {
      List<ItineraryResponse> itineraryCSVResponseList =
          itineraryService.getAllItineraryList(tripId, FileType.CSV);
      itineraryCSVResponseList.forEach(System.out::println);
    }
  }

  @DisplayName("3. 한 여행에 있는 한 여정을 ")
  @Order(3)
  @Nested
  class GetItineraryElement {

    int tripId = 1;
    int itineraryId = 1;

    @DisplayName("JSON 파일에서 가져올 수 있어야 함")
    @Test
    void getAllItineraryListFromJSON() {
      ItineraryResponse itineraryJSONResponse =
          itineraryService.getItinerary(tripId, itineraryId, FileType.JSON);

      compareItineraryAttributes(itineraryJSONResponse);
    }

    @DisplayName("CSV 파일에서 가져올 수 있어야 함")
    @Test
    void getAllItineraryListFromCSV() {
      ItineraryResponse itineraryCSVResponse =
          itineraryService.getItinerary(tripId, itineraryId, FileType.CSV);

      compareItineraryAttributes(itineraryCSVResponse);
    }
  }

  private void compareItineraryAttributes(ItineraryResponse itineraryResponse) {
    assertEquals(departurePlace, itineraryResponse.getDeparturePlace());
    assertEquals(destination, itineraryResponse.getDestination());
    assertEquals(toLocalDateTime(departureTime), itineraryResponse.getDepartureTime());
    assertEquals(toLocalDateTime(arrivalTime), itineraryResponse.getArrivalTime());
    assertEquals(toLocalDateTime(checkIn), itineraryResponse.getCheckIn());
    assertEquals(toLocalDateTime(checkOut), itineraryResponse.getCheckOut());
  }

  private LocalDateTime toLocalDateTime(String dateStr) {
    return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}