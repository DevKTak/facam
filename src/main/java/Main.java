import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ApiResponse;

public class Main {

	private static final String REST_API_KEY = "3d5113f27a487c01ac4500942161c28a";
	private static final String KAKAO_SEARCH_BASE_URL = "https://dapi.kakao.com/v2/local/search";
	private static final String CATEGORY_GROUP_CODE = "PM9";
	private static final int size = 10;

	private static String buildUrlConcatQueryStringByKeyword(final String keyword) {
		String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		return KAKAO_SEARCH_BASE_URL + "/keyword.json?query=" + encodedKeyword;
	}

	private static String buildUrlConcatQueryStringByCategory(double[] coordinate, int radius) {
		return KAKAO_SEARCH_BASE_URL + "/category.json?category_group_code=" + CATEGORY_GROUP_CODE
			+ "&x=" + coordinate[0] + "&y=" + coordinate[1]
			+ "&radius=" + radius + "&size=" + size;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("위치 키워드를 입력하세요: ");
		String keyword = br.readLine();

		System.out.print("검색 반경을 입력하세요(1000:1km): ");
		int radius = Integer.parseInt(br.readLine());

		System.out.print(System.lineSeparator());

		String requestKeywordUrl = buildUrlConcatQueryStringByKeyword(keyword);

		// 키워드로 장소 검색해서 위도와 경도 얻기
		double[] coordinate = getCoordinateByKeyword(requestKeywordUrl);

		String requestCategoryUrl = buildUrlConcatQueryStringByCategory(coordinate, radius);

		// 카테고리로 장소 검색해서 약국 정보 리스트 10개 얻기
		List<ApiResponse.PlaceInfoResponse> placeInfoList = getPlaceInfoByCategory(requestCategoryUrl);

		printPlaceInfo(keyword, radius, placeInfoList);

		while (true) {
			System.out.print("kakaomap URL(장소 URL): ");
			String urlOrExit = br.readLine();

			if ("exit".equals(urlOrExit)) {
				System.out.println("프로그램 종료");
				break;
			} else {
				openUrlInBrowser(urlOrExit.trim());
			}
		}
	}

	// 브라우저 오픈하기
	private static void openUrlInBrowser(String url) {
		String osName = System.getProperty("os.name").toLowerCase();
		String[] command;

		if (osName.contains("win")) {
			command = new String[] {"cmd", "/c", "start", url};
		} else if (osName.contains("mac")) {
			command = new String[] {"open", url};
		} else {
			System.out.println("해당 운영체제에서는 지원되지 않습니다.");
			return;
		}

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.start();
		} catch (IOException e) {
			System.err.println("URL을 열 수 없습니다: " + e);
			throw new RuntimeException(e);
		}
	}

	// 검색 결과 출력하기
	private static void printPlaceInfo(String keyword, int radius, List<ApiResponse.PlaceInfoResponse> placeInfoList) {
		StringBuilder sb = new StringBuilder();
		sb.append("입력한 위치 키워드: ").append(keyword).append(System.lineSeparator());
		sb.append("검색 반경: ").append(String.format("%.1fkm", radius / 1000d))
			.append(System.lineSeparator())
			.append(System.lineSeparator());

		for (ApiResponse.PlaceInfoResponse placeInfo : placeInfoList) {
			buildSearchInfo(sb, placeInfo);
		}
		System.out.println(sb);
	}

	private static void buildSearchInfo(StringBuilder sb, ApiResponse.PlaceInfoResponse placeInfo) {
		sb.append("** 약국 검색 결과 **").append(System.lineSeparator());
		sb.append("장소 URL(지도 위치): ").append(placeInfo.place_url()).append(System.lineSeparator());
		sb.append("상호명: ").append(placeInfo.place_name()).append(System.lineSeparator());
		sb.append("주소: ").append(placeInfo.address_name()).append(System.lineSeparator());
		sb.append("전화번호: ").append(placeInfo.phone()).append(System.lineSeparator());
		sb.append("거리(km): ")
			.append(String.format("%.3fkm", placeInfo.distance() / 1000d))
			.append(System.lineSeparator())
			.append("----------------------------------------")
			.append(System.lineSeparator());
	}

	// 카테고리로 장소 검색하기
	private static List<ApiResponse.PlaceInfoResponse> getPlaceInfoByCategory(String requestCategoryUrl) throws
		JsonProcessingException {
		String responseBody = httpGet(requestCategoryUrl);

		if (StringUtils.isBlank(responseBody)) {
			throw new IllegalStateException("API 응답값이 존재하지 않습니다.");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);

		return apiResponse.documents();
	}

	// 키워드로 장소 검색하기
	private static double[] getCoordinateByKeyword(String requestKeywordUrl) {
		JsonNode firstObject;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String responseBody = httpGet(requestKeywordUrl);

			if (StringUtils.isBlank(responseBody)) {
				throw new IllegalStateException("API 응답값이 존재하지 않습니다.");
			}
			JsonNode rootNode = objectMapper.readTree(responseBody);
			firstObject = rootNode.get("documents").get(0);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		return new double[] {firstObject.get("x").asDouble(), firstObject.get("y").asDouble()};
	}

	// HttpClient, HttpGet 객체 생성
	private static String httpGet(String requestUrl) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestUrl);
		getRequest.addHeader("Authorization", "KakaoAK " + REST_API_KEY);

		try {
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();

				return handler.handleResponse(response);
			} else {
				System.err.println("[API 호출 실패]\n 상태 코드: " + statusCode);
				throw new RuntimeException("API 호출 실패");
			}
		} catch (Exception e) {
			System.err.println("[API 호출 오류]: " + e);
			throw new RuntimeException("[API 호출 오류]: " + e);
		}
	}
}
