package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryTestDataFileProvider;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.type.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItineraryJSONDaoTest {

    private final ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();
    private final DataFileProvider dataFileProvider = new ItineraryTestDataFileProvider();

    @DisplayName("여정 리스트를 파일에서 받아와야 함")
    @Test
    void shouldGetItineraryListFromFile() {
        int tripId = 2;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileType.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        var list = itineraryJSONDao.getItineraryList(itineraryTestJSONDataFile);

    }

    @DisplayName("존재하지 않는 여행 파일에 여정을 추가한 경우")
    @Test
    void shouldThrowNullPointerExceptionWhenAddingItineraryToNonexistentTrip() {
        int tripId = Integer.MAX_VALUE;
        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileType.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
//        assertThrows(NullPointerException.class, () -> {
//            itineraryJSONDao.addItinerary(itineraryTestJSONDataFile, itinerary);
//        });

    }

    @DisplayName("원하는 인덱스의 여정을 가져와야 함")
    @Test
    void shouldGetItineraryAtIndex() {
        int tripId = 1;
        int itineraryId = 1;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileType.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        itineraryJSONDao.getItinerary(itineraryTestJSONDataFile, itineraryId);
    }

    @DisplayName("마지막으로 추가된 여정 파일의 인덱스가 일치하는지 확인해야 함")
    @Test
    void shouldCheckIfLastAddedItineraryIndexMatches() {

        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();

        int tripId = 1;
        File itineraryTestJSONDataFile = dataFileProvider.getDataFile(tripId, FileType.JSON);
        itineraryTestJSONDataFile.deleteOnExit();
        itineraryJSONDao.addItinerary(itineraryTestJSONDataFile, itinerary);
        var list = itineraryJSONDao.getItineraryList(itineraryTestJSONDataFile);

       
        assertEquals(list.get(list.size()-1).getItineraryId(), itinerary.getItineraryId());

    }
}