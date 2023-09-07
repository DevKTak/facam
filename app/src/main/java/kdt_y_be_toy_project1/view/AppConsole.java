package kdt_y_be_toy_project1.view;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.Getter;

public class AppConsole {

    private static final String SELECT_MENU_DISPLAY = """
        ==========================================================
               #  여행 여정을 기록과 관리하는 SNS 서비스  #

            1. 여행기록
            2. 여정기록
            3. 여행조회
            4. 여정조회
            5. 종료
        ==========================================================
        시작할 메뉴번호를 입력 하세요:\t""";
    private static final String SAVE_TRIP_DISPLAY = """
        ==========================================================
                        #    여행기록 메뉴    #
        ==========================================================
        """;
    private static final String INSERT_ARGUMENT_DISPLAY = "%s을 입력하세요:\t";
    private static final String INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY = "입력 포맷(yyyy-mm-dd)을 지켜주세요. (예: 2023-09-05)";
    private static final String ASK_FOR_SAVE_DISPLAY = "저장하시겠습니까?(Y/N):\t";

    @Getter private boolean shutdown;
    private Processor processor;
    private TextOutput output;


    public AppConsole() {
        output = new TextOutput();
        processor = getSelectMenuProcessor();
    }

    private Processor getSelectMenuProcessor() {
        output.print(SELECT_MENU_DISPLAY);

        return input -> switch (input) {
            case "1" -> getInsertTripProcessor();
            case "2" -> getInsertItineraryProcessor();
            case "3" -> getSearchTripProcessor();
            case "4" -> getSearchItineraryProcessor();
            case "5" -> getShutdownProcessor();
            default -> getSelectMenuProcessor();
        };
    }

    private Processor getInsertTripProcessor() {

        output.println(SAVE_TRIP_DISPLAY);
        return getInsertTripStartDateProcessor();
    }

    private Processor getInsertTripStartDateProcessor() {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("시작일(yyyy-mm-dd)"));
        return input -> {
            try {
                LocalDate startDate = parseInputToLocalDate(input);
                return getInsertTripEndDateProcessor(new TripCreateRequest(null, startDate, null));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY);
                return getInsertTripStartDateProcessor();
            }
        };
    }

    private static LocalDate parseInputToLocalDate(String input) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, dateTimeFormatter);
    }

    private Processor getInsertTripEndDateProcessor(TripCreateRequest tripCreateRequest) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("종료일(yyyy-mm-dd)"));
        LocalDate startDate = tripCreateRequest.startDate();
        return input -> {
            try {
                LocalDate endDate = parseInputToLocalDate(input);

                if (endDate.isBefore(startDate)) {
                    output.println("종료일은 시작일보다 빠를 수 없습니다.");
                    return getInsertTripEndDateProcessor(tripCreateRequest);
                }

                return getInsertTripNameProcessor(new TripCreateRequest(null, startDate, endDate));
            } catch (DateTimeParseException e) {
                output.println(INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY);
                return getInsertTripEndDateProcessor(tripCreateRequest);
            }
        };
    }

    private Processor getInsertTripNameProcessor(TripCreateRequest tripCreateRequest) {
        output.print(INSERT_ARGUMENT_DISPLAY.formatted("여행명"));
        return input -> {
            TripCreateRequest result = new TripCreateRequest(input, tripCreateRequest.startDate(),
                tripCreateRequest.endDate());

            return getSaveTripProcessor(result);
        };
    }

    private Processor getSaveTripProcessor(TripCreateRequest tripCreateRequest) {
        output.println(tripCreateRequest.toString());
        output.print(ASK_FOR_SAVE_DISPLAY);
        return input -> {
            switch (input.toLowerCase()) {
                case "y" -> {
                    output.println("저장완료");
                    throw new UnsupportedOperationException();
                }

                case "n" -> {
                    return getInsertTripProcessor();
                }
                default -> {
                    output.print("'Y' 또는 'N' 만 입력해주세요");
                    return getSaveTripProcessor(tripCreateRequest);
                }
            }

        };
    }

    private Processor getInsertItineraryProcessor() {

        return input -> {
            throw new UnsupportedOperationException();
        };
    }

    private Processor getSearchTripProcessor() {

        return input -> {
            throw new UnsupportedOperationException();
        };
    }

    private Processor getSearchItineraryProcessor() {

        return input -> {
            throw new UnsupportedOperationException();
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
