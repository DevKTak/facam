package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItineraryJSONDaoTest {

    @Test
    void getItineraryListFromFile() {

        int tripId = 3;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        var list = itineraryJSONDao.getItineraryListFromFile(tripId);
    }

    @Test
    void 존재하지_않는_여행파일에_여정을_추가한경우(){
        int tripId = Integer.MAX_VALUE;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();

        assertThrows(FileIOException.class, () -> {
            itineraryJSONDao.addItineraryToFile(tripId, itinerary);
        });

    }

    @Test
    void 원하는_인덱스의_여정을_가져오기() {
        int tripId = 1;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

    }

    @Test
    void 마지막으로_추가된_여정파일의_인덱스가_일치하는지() {

        ItineraryJSON itinerary = ItineraryJSON.builder()
                .departurePlace("City Test")
                .destination("City Test")
                .departureTime("2023-08-15T08:00:00")
                .arrivalTime("2023-08-15T10:00:00")
                .checkIn("2023-08-15T12:00:00")
                .checkOut("2023-08-30T10:00:00")
                .build();

        int tripId = 1;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();
        itineraryJSONDao.addItineraryToFile(1, itinerary);
        var list = itineraryJSONDao.getItineraryListFromFile(tripId);

       
        assertEquals(list.get(list.size()-1).getItineraryId(), itinerary.getItineraryId());

    }

    @Test
    void getItineraryFromFile() {

        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        List<ItineraryJSON> itineraryListFromFile = itineraryJSONDao.getItineraryListFromFile(1);

        ItineraryJSON a = itineraryJSONDao.getItineraryFromFile(1, 99);
        System.out.println(a);

    }

    @Test
    void addItineraryToFile() {
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();
        var a = ItineraryJSON.builder().departurePlace("서울").build();
        itineraryJSONDao.addItineraryToFile(1, a);
    }
}