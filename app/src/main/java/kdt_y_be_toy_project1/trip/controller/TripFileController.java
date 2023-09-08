package kdt_y_be_toy_project1.trip.controller;

import java.util.List;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.trip.controller.dto.SaveTripToFileRequestDto;
import kdt_y_be_toy_project1.trip.controller.dto.TripControllerResponse;
import kdt_y_be_toy_project1.trip.dto.TripResponse;
import kdt_y_be_toy_project1.trip.service.TripCsvService;
import kdt_y_be_toy_project1.trip.service.TripJsonService;
import kdt_y_be_toy_project1.trip.service.TripService;

public class TripFileController {

    private final TripService tripJsonService = new TripJsonService();
    private final TripService tripCsvService = new TripCsvService();

    public long saveTrip(SaveTripToFileRequestDto request) {
        return switch (request.getFileFormat()) {
            case JSON -> tripJsonService.save(request.toServiceDto());
            case CSV -> tripCsvService.save(request.toServiceDto());
        };
    }

    public List<TripControllerResponse> findAllTripsFromFile(FileFormat format) {
        List<TripResponse> responses = switch (format) {
            case JSON -> tripJsonService.findAll();
            case CSV -> tripCsvService.findAll();
        };

        return responses.stream()
            .map(TripControllerResponse::toControllerResponse)
            .toList();
    }
}
