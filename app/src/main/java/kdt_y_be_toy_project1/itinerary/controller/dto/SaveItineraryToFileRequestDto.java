package kdt_y_be_toy_project1.itinerary.controller.dto;

import static java.util.Objects.requireNonNull;
import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import kdt_y_be_toy_project1.common.StringValidator;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.itinerary.dto.AddItineraryRequest;
import lombok.Getter;

@Getter
public class SaveItineraryToFileRequestDto {

    private final long tripId;
    private final String departurePlace;
    private final String destination;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final FileFormat fileFormat;

    private SaveItineraryToFileRequestDto(Long tripId, String departurePlace, String destination,
        String departureTime, String arrivalTime, String checkIn, String checkOut,
        String fileFormat) {
        this.tripId = requireNonNull(tripId);
        this.departurePlace = StringValidator.requireNotBlank(requireNonNull(departurePlace));
        this.destination = StringValidator.requireNotBlank(requireNonNull(destination));
        this.departureTime = LocalDateTime.parse(departureTime, LOCAL_DATE_TIME_FORMATTER);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, LOCAL_DATE_TIME_FORMATTER);
        this.checkIn = LocalDateTime.parse(checkIn, LOCAL_DATE_TIME_FORMATTER);
        this.checkOut = LocalDateTime.parse(checkOut, LOCAL_DATE_TIME_FORMATTER);
        this.fileFormat = FileFormat.valueOf(fileFormat);
    }

    public AddItineraryRequest toServiceDto() {
        return AddItineraryRequest.builder()
            .departurePlace(departurePlace)
            .destination(destination)
            .departureTime(departureTime)
            .arrivalTime(arrivalTime)
            .checkIn(checkIn)
            .checkOut(checkOut)
            .build();
    }

    public static SaveItineraryToFileRequestDtoBuilder builder() {
        return new SaveItineraryToFileRequestDtoBuilder();
    }

    public static class SaveItineraryToFileRequestDtoBuilder {
        private long tripId;
        private String departurePlace;
        private String destination;
        private String departureTime;
        private String arrivalTime;
        private String checkIn;
        private String checkOut;
        private String fileFormat;

        public SaveItineraryToFileRequestDtoBuilder tripId(long tripId) {
            this.tripId = tripId;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder departurePlace(String departurePlace) {
            this.departurePlace = departurePlace;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder departureTime(String departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder arrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder checkIn(String checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder checkOut(String checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public SaveItineraryToFileRequestDtoBuilder fileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
            return this;
        }

        public SaveItineraryToFileRequestDto build() {
            return new SaveItineraryToFileRequestDto(tripId, departurePlace, destination,
                departureTime, arrivalTime, checkIn, checkOut, fileFormat);
        }

        @Override
        public String toString() {
            return """
            ==========================================================
                                 #  여정 기록  #
                    이             름 : %s
                    도      착     지 : %s
                    출발  ~ 도착 시간 : %s ~ %s
                    체크인 ~ 체크아웃 : %s ~ %s
            ==========================================================
                """.formatted(departurePlace, destination, departureTime, arrivalTime,
                checkIn, checkOut);
        }
    }


}
