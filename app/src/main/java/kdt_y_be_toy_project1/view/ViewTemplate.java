package kdt_y_be_toy_project1.view;

public final class ViewTemplate {

    public static final String SELECT_MENU_DISPLAY = """
        ==========================================================
               #  여행 여정을 기록과 관리하는 SNS 서비스  #

            1. 여행기록
            2. 여정기록
            3. 여행조회
            4. 종료
        ==========================================================
        시작할 메뉴번호를 입력 하세요:\t""";
    public static final String SAVE_MENU_HEADER = """
        ==========================================================
                        #    %s기록 메뉴    #
        ==========================================================
        """;

    public static final String SAVE_TRIP_FORMAT_DISPLAY = """
        ==========================================================
                             #  여행 기록  #
                일정  : %s ~ %s
                여행명: %s
        ==========================================================
            """;
    public static final String TRIP_RESPONSE_HEADER = """
        =================================================================
           id                 이름               시작일        종료일
        =================================================================
        """;

    public static final String TRIP_DETAIL_RESPONSE_HEADER = """
        ===========================================================================================================================
           id         출발지         주소             출 발                 도 착                체 크 인                체 크 아 웃
        ===========================================================================================================================
        """;

    public static final String INSERT_ARGUMENT_DISPLAY = "%s을 입력하세요:\t";
    public static final String INSERT_CORRECT_LOCAL_DATE_FORMAT_DISPLAY = "입력 포맷(yyyy-mm-dd)을 지켜주세요. (예: 2023-09-05)";
    public static final String SAVE_OR_NOT_DISPLAY = "저장하시겠습니까?(Y/N):\t";
    public static final String SAVE_DONE_DISPLAY = "저장완료!";
    public static final String INSERT_ONLY_Y_OR_N_DISPLAY = "'Y' 또는 'N' 만 입력해주세요";
    public static final String SELECT_SAVE_FILE_FORMAT_DISPLAY = "어떤 파일로 저장하시겠습니까? (json, csv):\t";
    public static final String INSERT_ONLY_JSON_OR_CSV_DISPLAY = "'json' 또는 'csv' 만 입력해주세요";
    public static final String KEEP_SAVE_ITINERARY_OR_NOT_DISPLAY = "여정을 계속 저장하시겠습니까? (Y/N):\t";
    public static final String SELECT_FILE_FORMAT_FOR_SEARCH_DISPLAY = "어떤 파일로 검색하시겠습니까? (json, csv):\t";
    public static final String SELECT_TRIP_ID_DISPLAY = "검색할 아이디가 있나요?:\t";


    private ViewTemplate() {
    }
}
