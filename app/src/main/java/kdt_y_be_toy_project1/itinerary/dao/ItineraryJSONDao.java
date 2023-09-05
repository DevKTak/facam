package kdt_y_be_toy_project1.itinerary.dao;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kdt_y_be_toy_project1.itinerary.entity.Itinerary;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ItineraryJSONDao implements ItineraryDao {

    private static final String BASE_PATH = "itinerary/json/";
    private static final String BASE_NAME = "itineraries_trip_";
    private static final String FORMAT = ".json";

    @Override
    public List<Itinerary> getItineraryListFromFile(int tripId) { //json파일에서 여정을 읽어옴, List로 만들어서 반환
        String filename = getItineraryFilePathString(tripId);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        List<Itinerary> list = new ArrayList<>();

        try {
            String jsonContent = readFileAsString(filename);
            list = gson.fromJson(jsonContent, new TypeToken<List<Itinerary>>() {
            }.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Itinerary getItineraryFromFile(int tripId, int itineraryId) {

        List<Itinerary> list = getItineraryListFromFile(tripId);

        return list.stream()
                .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
                .findFirst().orElse(null);
    }


  //TODO : 파일이 없으면 -> 파일 생성 후 객체리스트 넣고 저장 ,,
  // 파일이 있으면 -> 있는 파일 읽고 객체 리스트 넣고 저장
    @Override
    public void addItineraryToFile(int tripId, Itinerary itinerary) {
      String filename = getItineraryFilePathString(tripId);
      File file = new File(filename);
      Gson gson = new GsonBuilder()
              .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

      if(!file.exists()) {
        ////파일이 없으면 만들고 저장
      }

        //파일이 존재하면 추가
        String strItinerary = gson.toJson(itinerary);
        try{
          FileWriter writer = new FileWriter(filename);
          writer.write(strItinerary);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }


    // 파일을 읽어서 문자열로 반환하는 메서드
    private String readFileAsString(String filePath) throws IOException {
        byte[] buffer = new byte[(int) new File(filePath).length()];
        FileInputStream fileInputStream = new FileInputStream(filePath);
        fileInputStream.read(buffer);
        fileInputStream.close();
        return new String(buffer);
    }


    private String getItineraryFilePathString(int tripId) throws NullPointerException {
        return Objects.requireNonNull(ItineraryCSVDao.class.getClassLoader()
                        .getResource(BASE_PATH + BASE_NAME + tripId + FORMAT))
                .getPath();
    }
}
