package kdt_y_be_toy_project1.view;


import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ARGUMENT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ONLY_JSON_OR_CSV_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.KEEP_SAVE_ITINERARY_OR_NOT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.INSERT_ONLY_Y_OR_N_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SAVE_MENU_HEADER;
import static kdt_y_be_toy_project1.view.ViewTemplate.SAVE_OR_NOT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SELECT_FILE_FORMAT_FOR_SEARCH_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SELECT_MENU_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.SELECT_SAVE_FILE_FORMAT_DISPLAY;
import static kdt_y_be_toy_project1.view.ViewTemplate.TRIP_DETAIL_RESPONSE_HEADER;
import static kdt_y_be_toy_project1.view.ViewTemplate.TRIP_RESPONSE_HEADER;

import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.itinerary.controller.ItineraryFileController;
import kdt_y_be_toy_project1.itinerary.controller.dto.FindItinerariesFromFileRequestDto;
import kdt_y_be_toy_project1.itinerary.controller.dto.SaveItineraryToFileRequestDto;
import kdt_y_be_toy_project1.itinerary.controller.dto.SaveItineraryToFileRequestDto.SaveItineraryToFileRequestDtoBuilder;
import kdt_y_be_toy_project1.trip.controller.TripFileController;
import kdt_y_be_toy_project1.trip.controller.dto.SaveTripToFileRequestDto;
import kdt_y_be_toy_project1.trip.controller.dto.SaveTripToFileRequestDto.SaveTripToFileRequestDtoBuilder;
import lombok.Getter;

public class AppConsole {

    private final TripFileController tripFileController;
    private final ItineraryFileController itineraryFileController;
    private final TextOutput output;

    @Getter
    private boolean shutdown;
    private Processor processor;

    public AppConsole() {
        this.tripFileController = new TripFileController();
        this.itineraryFileController = new ItineraryFileController();
        output = new TextOutput();
        processor = getSelectMenuProcessor();
    }

    private Processor getSelectMenuProcessor() {
        output.print(SELECT_MENU_DISPLAY);

        return input -> switch (input) {
            case "1" -> getInsertTripProcessor();
            case "2" -> getInsertItineraryProcessor();
            case "3" -> getSearchTripProcessor();
            case "4" -> getShutdownProcessor();
            default -> getSelectMenuProcessor();
        };
    }

    // CASE 1
    private Processor getInsertTripProcessor() {
        output.println(SAVE_MENU_HEADER.formatted("여행"));
        SaveTripToFileRequestDtoBuilder builder = SaveTripToFileRequestDto.builder();
        return getInsertTripStartDateProcessor(builder);
    }

    private Processor getInsertTripStartDateProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("시작일(yyyy-mm-dd)"));
        return input -> getInsertTripEndDateProcessor(builder.startDate(input));
    }

    private Processor getInsertTripEndDateProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("종료일(yyyy-mm-dd)"));
        return input -> getInsertTripNameProcessor(builder.endDate(input));
    }

    private Processor getInsertTripNameProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행명"));
        return input -> getSaveOrNotTripProcessor(builder.tripName(input));
    }

    private Processor getSaveOrNotTripProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(SAVE_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    return getSelectSaveTripFormatProcessor(builder);
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.print(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getSaveOrNotTripProcessor(builder);
                }
            }
        };
    }

    private Processor getSelectSaveTripFormatProcessor(SaveTripToFileRequestDtoBuilder builder) {
        output.print(SELECT_SAVE_FILE_FORMAT_DISPLAY);
        return input -> {
            String fileFormat = input.toUpperCase();

            if (isSupportedFileFormat(fileFormat)) {
                output.print(INSERT_ONLY_JSON_OR_CSV_DISPLAY);
                return getSelectSaveTripFormatProcessor(builder);
            }

            long savedTripId = tripFileController.saveTrip(builder.fileFormat(fileFormat).build());
            itineraryFileController.createItineraryFile(savedTripId);

            return getInsertItineraryProcessor(savedTripId);
        };
    }

    private static boolean isSupportedFileFormat(String fileFormat) {
        return !fileFormat.equals("JSON") && !fileFormat.equals("CSV");
    }

    /**
     * It will be run right after getSelectSaveTripFormatProcessor()
     */
    private Processor getInsertItineraryProcessor(long id) {
        output.println(SAVE_MENU_HEADER.formatted("여정"));

        SaveItineraryToFileRequestDtoBuilder builder = SaveItineraryToFileRequestDto.builder();
        return getInsertItineraryDeparturePlaceProcessor(builder.tripId(id));
    }

    // CASE 2

    /**
     * If select 2 in MenuSelectProcessor
     */
    private Processor getInsertItineraryProcessor() {
        output.println(SAVE_MENU_HEADER.formatted("여정"));
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행 ID"));

        return input -> {
            SaveItineraryToFileRequestDtoBuilder builder = SaveItineraryToFileRequestDto.builder();
            return getInsertItineraryDeparturePlaceProcessor(builder.tripId(Long.parseLong(input)));
        };
    }

    private Processor getInsertItineraryDeparturePlaceProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("출발지"));
        return input -> getInsertItineraryDestinationProcessor(builder.departurePlace(input));
    }

    private Processor getInsertItineraryDestinationProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("도착 장소"));
        return input -> getInsertItineraryDepartureTimeProcessor(builder.destination(input));
    }

    private Processor getInsertItineraryDepartureTimeProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("출발 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> getInsertItineraryArrivalTimeProcessor(builder.departureTime(input));
    }

    private Processor getInsertItineraryArrivalTimeProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("도착 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> getInsertItineraryCheckInProcessor(builder.arrivalTime(input));
    }

    private Processor getInsertItineraryCheckInProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("CheckIn 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> getInsertItineraryCheckOutProcessor(builder.checkIn(input));
    }

    private Processor getInsertItineraryCheckOutProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("CheckOut 시간(yyyy-MM-dd HH:mm:ss)"));
        return input -> getSaveOrNotItineraryProcessor(builder.checkOut(input));
    }

    private Processor getSaveOrNotItineraryProcessor(SaveItineraryToFileRequestDtoBuilder builder) {
        output.println(builder.toString());
        output.print(SAVE_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    return getSelectSaveItineraryFormatProcessor(builder);
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.println(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getSaveOrNotItineraryProcessor(builder);
                }
            }
        };
    }

    private Processor getSelectSaveItineraryFormatProcessor(
        SaveItineraryToFileRequestDtoBuilder builder) {
        output.print(SELECT_SAVE_FILE_FORMAT_DISPLAY);
        return input -> {
            String fileFormat = input.toUpperCase();

            if (isSupportedFileFormat(fileFormat)) {
                output.println(INSERT_ONLY_JSON_OR_CSV_DISPLAY);
                return getSelectSaveItineraryFormatProcessor(builder);
            }

            SaveItineraryToFileRequestDto saveDto = builder.fileFormat(fileFormat).build();

            itineraryFileController.saveItinerary(saveDto);
            return getKeepSaveItineraryOrNotProcessor(saveDto.getTripId());
        };
    }

    private Processor getKeepSaveItineraryOrNotProcessor(long tripId) {
        output.print(KEEP_SAVE_ITINERARY_OR_NOT_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    return getInsertItineraryProcessor(tripId);
                }
                case "n" -> {
                    return getSelectMenuProcessor();
                }
                default -> {
                    output.println(INSERT_ONLY_Y_OR_N_DISPLAY);
                    return getKeepSaveItineraryOrNotProcessor(tripId);
                }
            }
        };
    }

    private Processor getSearchTripProcessor() {
        output.print(SELECT_FILE_FORMAT_FOR_SEARCH_DISPLAY);

        return input -> {
            String fileFormat = input.toUpperCase();

            if (isSupportedFileFormat(fileFormat)) {
                output.println(INSERT_ONLY_JSON_OR_CSV_DISPLAY);
                return getSearchTripProcessor();
            }

            output.print(TRIP_RESPONSE_HEADER);
            tripFileController.findAllTripsFromFile(FileFormat.valueOf(fileFormat))
                .forEach(tripResponse -> output.println(tripResponse.toString()));

            return getSearchItinerariesByTripIdProcessor(fileFormat);
        };
    }

    private Processor getSearchItinerariesByTripIdProcessor(String fileFormat) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행 ID"));

        return input -> {
            output.print(TRIP_DETAIL_RESPONSE_HEADER);
            FindItinerariesFromFileRequestDto requestDto =
                new FindItinerariesFromFileRequestDto(Long.parseLong(input), fileFormat);
            itineraryFileController.findItinerariesByTripId(requestDto)
                .forEach(tripResponse -> output.println(tripResponse.toString()));

            return getSelectMenuProcessor();
        };
    }

    private Processor getShutdownProcessor() {
        shutdownApp();
        return null;
    }

    public void processInput(String input) {
        processor = processor.run(input);
    }

    public String flush() {
        return output.flush();
    }

    private void shutdownApp() {
        shutdown = true;
    }
}
