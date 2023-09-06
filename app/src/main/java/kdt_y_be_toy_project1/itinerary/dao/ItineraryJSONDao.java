package kdt_y_be_toy_project1.itinerary.dao;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kdt_y_be_toy_project1.itinerary.entity.Itinerary;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;

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
    public List<ItineraryJSON> getItineraryListFromFile(int tripId) {

      if (tripId < 1) {
        throw new RuntimeException("tripId must be greater than 1");
      }
      List<Itinerary> itineraries = null;

      File file = new File(getItineraryFilePathString(tripId));
      Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

      try {

          file.createNewFile();
          if(file.createNewFile()){

              itineraries = new ArrayList<>();
          } else{
              Reader bufferedReader = new BufferedReader(new FileReader(file));
//              String jsonContent = readFileAsString(filename);
              itineraries = gson.fromJson(bufferedReader, new TypeToken<List<Itinerary>>() {}.getType());
        }
        } catch (IOException | NullPointerException e) {
          throw new RuntimeException(e);
        }
        return itineraries;
    }

    @Override
    public ItineraryJSON getItineraryFromFile(int tripId, int itineraryId) {

        List<Itinerary> list = getItineraryListFromFile(tripId);
        return list.stream()
                .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
                .findFirst().orElse(null);
    }


    @Override
    public void addItineraryToFile(int tripId, ItineraryJSON itinerary) {
      List<Itinerary> itineraries = getItineraryListFromFile(tripId);
      itinerary.setItineraryId(itineraries.size() + 1);
      itineraries.add(itinerary);

      File file = new File(getItineraryFilePathString(tripId));
      Gson gson = new GsonBuilder()
              .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        String jsonArray = gson.toJson(itineraries);
        System.out.println("jsonArray = " + jsonArray);
        try{
          FileWriter writer = new FileWriter(file);
          writer.write(jsonArray);
          writer.close();
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
