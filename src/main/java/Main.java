import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ApiResponse;

public class Main {

	private static final String REST_API_KEY = "3d5113f27a487c01ac4500942161c28a";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("위치 키워드를 입력하세요: ");
		String keyword = br.readLine();

		System.out.print("검색 반경을 입력하세요(1000:1km): ");
		int radius = Integer.parseInt(br.readLine());

		System.out.println();

		String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		String kakaoSearchUrl = "https://dapi.kakao.com/v2/local/search";
		String requestKeywordUrl = kakaoSearchUrl + "/keyword.json?query=" + encodedKeyword;

		double[] coordinate = getCoordinateByKeyword(requestKeywordUrl);

		if (coordinate != null) {
			String requestCategoryUrl =
				kakaoSearchUrl + "/category.json?category_group_code=PM9&x=" + coordinate[0] + "&y=" + coordinate[1]
					+ "&radius=" + radius + "&size=10";

			List<ApiResponse.PlaceInfoResponse> placeInfoList = getPlaceInfoByCategory(requestCategoryUrl);

			printPlaceInfo(keyword, radius, placeInfoList);

			while (true) {
				System.out.print("kakaomap URL(장소 URL): ");
				String urlOrExit = br.readLine();

				if ("exit".equals(urlOrExit)) {
					System.out.println("프로그램 종료");
					break;
				} else {
					String osName = System.getProperty("os.name").toLowerCase();
					String[] command;

					if (osName.contains("win")) {
						command = new String[] { "cmd", "/c", "start", urlOrExit };
					} else if (osName.contains("mac")) {
						command = new String[]{"open", urlOrExit};
					} else {
						System.out.println("운영체제가 지원되지 않습니다.");
						continue;
					}
					ProcessBuilder processBuilder = new ProcessBuilder(command);
					processBuilder.start();
				}
			}
		}
	}

	private static void printPlaceInfo(String keyword, int radius, List<ApiResponse.PlaceInfoResponse> placeInfoList) {
		System.out.println("입력한 위치 키워드: " + keyword);
		System.out.println("검색 반경: " + String.format("%.1fkm", radius / 1000d) + System.lineSeparator());

		for (ApiResponse.PlaceInfoResponse placeInfo : placeInfoList) {
			if (placeInfoList.size() > 0) {
				System.out.println("** 약국 검색 결과 **");
				System.out.println("장소 URL(지도 위치): " + placeInfo.place_url());
				System.out.println("상호명: " + placeInfo.place_name());
				System.out.println("주소: " + placeInfo.address_name());
				System.out.println("전화번호: " + placeInfo.phone());
				System.out.println(
					"거리(km): " + String.format("%.3fkm", placeInfo.distance() / 1000d) + System.lineSeparator());
			}
		}
	}

	private static List<ApiResponse.PlaceInfoResponse> getPlaceInfoByCategory(String requestCategoryUrl) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestCategoryUrl);
		getRequest.addHeader("Authorization", "KakaoAK " + REST_API_KEY);

		try {
			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String responseBody = handler.handleResponse(response);

				ObjectMapper objectMapper = new ObjectMapper();
				ApiResponse apiResponse = objectMapper.readValue(responseBody, ApiResponse.class);

				return apiResponse.documents();
			} else {
				System.out.println("[API 호출 실패]\n 상태 코드: " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.err.println("[API 호출 오류]: " + e);
		}
		return new ArrayList<>();
	}

	private static double[] getCoordinateByKeyword(String requestKeywordUrl) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestKeywordUrl);
		getRequest.addHeader("Authorization", "KakaoAK " + REST_API_KEY);

		try {
			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String responseBody = handler.handleResponse(response);

				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(responseBody);

				JsonNode firstObject = rootNode.get("documents").get(0);
				double x = firstObject.get("x").asDouble();
				double y = firstObject.get("y").asDouble();

				return new double[] {x, y};
			} else {
				System.out.println("[API 호출 실패]\n 상태 코드: " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			System.err.println("[API 호출 오류]: " + e);
		}
		return null;
	}
}
