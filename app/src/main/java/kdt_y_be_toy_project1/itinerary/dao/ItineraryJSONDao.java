package kdt_y_be_toy_project1.itinerary.dao;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.JSON;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {

  @Override
  public List<ItineraryJSON> getItineraryListFromFile(int tripId) {
    return getItineraryList(new ItineraryDataFile().getDataFile(tripId, JSON));
  }

  @Override
  public ItineraryJSON getItineraryFromFile(int tripId, int itineraryId) {
    return getItinerary(new ItineraryDataFile().getDataFile(tripId, JSON), itineraryId);
  }

  @Override
  public void addItineraryToFile(int tripId, ItineraryJSON itineraryJSON) {
    addItinerary(new ItineraryDataFile().getDataFile(tripId, JSON), itineraryJSON);
  }

  public List<ItineraryJSON> getItineraryList(File itineraryFile) {
    List<ItineraryJSON> itineraries;

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    try {
      if (itineraryFile.createNewFile()) {

        itineraries = new ArrayList<>();
      } else {
        Reader bufferedReader = new BufferedReader(new FileReader(itineraryFile));
//              String jsonContent = readFileAsString(filename);
        itineraries = gson.fromJson(bufferedReader, new TypeToken<List<ItineraryJSON>>() {
        }.getType());
      }
    } catch (IOException | NullPointerException e) {
      throw new RuntimeException(e);
    }
    return itineraries;
  }

  public ItineraryJSON getItinerary(File itineraryFile, int itineraryId) {

    List<ItineraryJSON> list = getItineraryList(itineraryFile);
    return list.stream()
        .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
        .findFirst().orElse(null);
  }

  public void addItinerary(File itineraryFile, ItineraryJSON itinerary) {
    List<ItineraryJSON> itineraries = getItineraryList(itineraryFile);
    itinerary.setItineraryId(itineraries.size() + 1);
    itineraries.add(itinerary);

    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    String jsonArray = gson.toJson(itineraries);
    try {
      FileWriter writer = new FileWriter(itineraryFile);
      writer.write(jsonArray);
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
