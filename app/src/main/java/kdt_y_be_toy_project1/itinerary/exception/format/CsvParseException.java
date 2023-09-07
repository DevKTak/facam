package kdt_y_be_toy_project1.itinerary.exception.format;

import com.opencsv.exceptions.CsvException;

public class CsvParseException extends FileFormatException {

    public CsvParseException(String message) {
        super(message);
    }
}
