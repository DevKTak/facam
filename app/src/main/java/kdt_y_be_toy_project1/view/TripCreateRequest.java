package kdt_y_be_toy_project1.view;

import java.time.LocalDate;

public record TripCreateRequest(
    String tripName,
    LocalDate startDate,
    LocalDate endDate
) {

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
