package kdt_y_be_toy_project1.itinerary.exception.service;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ItineraryNotFoundException extends ServiceException {
    public ItineraryNotFoundException(String message){
        super(message);
    }
}
