package kdt_y_be_toy_project1.itinerary.dao;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {

    private static final String BASE_PATH = "itinerary/json/";
    private static final String BASE_NAME = "itineraries_trip_";
    private static final String FORMAT = ".json";

    @Override
    public List<ItineraryJSON> getItineraryListFromFile(int tripId) {

      if (tripId < 1) {
        throw new RuntimeException("tripId must be greater than 1");
      }
      List<ItineraryJSON> itineraries;

      File file = new File(getItineraryFilePathString(tripId));
      Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

      try {
          if(file.createNewFile()){

              itineraries = new ArrayList<>();
          } else{
              Reader bufferedReader = new BufferedReader(new FileReader(file));
//              String jsonContent = readFileAsString(filename);
              itineraries = gson.fromJson(bufferedReader, new TypeToken<List<ItineraryJSON>>() {}.getType());
        }
        } catch (IOException | NullPointerException e) {
          throw new RuntimeException(e);
        }
        return itineraries;
    }

    @Override
    public ItineraryJSON getItineraryFromFile(int tripId, int itineraryId) {

        List<ItineraryJSON> list = getItineraryListFromFile(tripId);
        return list.stream()
                .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
                .findFirst().orElse(null);
    }


    @Override
    public void addItineraryToFile(int tripId, ItineraryJSON itinerary) {
      List<ItineraryJSON> itineraries = getItineraryListFromFile(tripId);
      itinerary.setItineraryId(itineraries.size() + 1);
      itineraries.add(itinerary);

      File file = new File(getItineraryFilePathString(tripId));
      Gson gson = new GsonBuilder()
              .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        String jsonArray = gson.toJson(itineraries);
        try{
          FileWriter writer = new FileWriter(file);
          writer.write(jsonArray);
          writer.close();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }

    private String getItineraryFilePathString(int tripId) throws NullPointerException {
        return Objects.requireNonNull(ItineraryCSVDao.class.getClassLoader()
                        .getResource(BASE_PATH + BASE_NAME + tripId + FORMAT))
                .getPath();
    }
}
