package kdt_y_be_toy_project1.trip.controller.dto;

import static kdt_y_be_toy_project1.common.util.DateBindingFormatter.LOCAL_DATE_FORMATTER;

import java.time.LocalDate;
import java.util.Objects;
import kdt_y_be_toy_project1.common.StringValidator;
import kdt_y_be_toy_project1.common.util.FileFormat;
import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import lombok.Getter;

@Getter
public class SaveTripToFileRequestDto {

    private final String tripName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final FileFormat fileFormat;

    private SaveTripToFileRequestDto(String tripName, String startDate, String endDate, String fileFormat) {
        this.tripName = StringValidator.requireNotBlank(Objects.requireNonNull(tripName));
        this.startDate = LocalDate.parse(startDate, LOCAL_DATE_FORMATTER);
        this.endDate = LocalDate.parse(endDate, LOCAL_DATE_FORMATTER);
        this.fileFormat = FileFormat.valueOf(fileFormat);
    }

    public CreateTripRequest toServiceDto() {
        return new CreateTripRequest(tripName, startDate, endDate);
    }

    public static SaveTripToFileRequestDtoBuilder builder() {
        return new SaveTripToFileRequestDtoBuilder();
    }

    public static class SaveTripToFileRequestDtoBuilder {
        private String tripName;
        private String startDate;
        private String endDate;
        private String fileFormat;

        public SaveTripToFileRequestDtoBuilder tripName(String tripName) {
            this.tripName = tripName;
            return this;
        }

        public SaveTripToFileRequestDtoBuilder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public SaveTripToFileRequestDtoBuilder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public SaveTripToFileRequestDtoBuilder fileFormat(String fileFormat) {
            this.fileFormat = fileFormat;
            return this;
        }

        public SaveTripToFileRequestDto build() {
            return new SaveTripToFileRequestDto(tripName, startDate, endDate, fileFormat);
        }

        @Override
        public String toString() {
            return """
            ==========================================================
                                 #  여행 기록  #
                    일정  : %s ~ %s
                    여행명: %s
            ==========================================================
                """.formatted(startDate, endDate, tripName);
        }
    }
}
