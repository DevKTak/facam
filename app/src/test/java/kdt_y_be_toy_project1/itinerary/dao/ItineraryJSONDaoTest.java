package kdt_y_be_toy_project1.itinerary.dao;

import kdt_y_be_toy_project1.itinerary.entity.Itinerary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItineraryJSONDaoTest {

    @Test
    void getItineraryListFromFile() {

        int tripId = 1;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        var list = itineraryJSONDao.getItineraryListFromFile(tripId);

        System.out.println("list = " + list);
        assertNotNull(list);

    }

    @Test
    void testGetItineraryListFromFile() {
    }

    @Test
    void getItineraryFromFile() {
        int tripId = 1;
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        var itinerary = itineraryJSONDao.getItineraryFromFile(tripId, 1);

        System.out.println("itinerary = " + itinerary);
    }

    @Test
    void addItineraryToFile() {

        var a = Itinerary.builder().itineraryId(4)
                .destination("123").departurePlace("123").departureTime("123").arrivalTime("123")
                .checkOut("123").checkIn("123").departurePlace("123").build();
        ItineraryJSONDao itineraryJSONDao = new ItineraryJSONDao();

        itineraryJSONDao.addItineraryToFile(1, a);


    }
}