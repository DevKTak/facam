package kdt_y_be_toy_project1.itinerary.dao;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.JSON;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {

  private final DataFileProvider dataFileProvider;

  public ItineraryJSONDao() {
    this.dataFileProvider = new ItineraryDataFile();
  }

  public ItineraryJSONDao(DataFileProvider dataFileProvider) {
    this.dataFileProvider = dataFileProvider;
  }

  @Override
  public List<ItineraryJSON> getItineraryListByTripId(int tripId) {
    return getItineraryListFromFile(dataFileProvider.getDataFile(tripId, JSON));
  }

  @Override
  public ItineraryJSON getItineraryById(int tripId, int itineraryId) {
    return getItineraryFromFile(dataFileProvider.getDataFile(tripId, JSON), itineraryId);
  }

  @Override
  public void addItineraryByTripId(int tripId, ItineraryJSON itineraryJSON) {
    addItineraryToFile(dataFileProvider.getDataFile(tripId, JSON), itineraryJSON);
  }

  public List<ItineraryJSON> getItineraryListFromFile(File itineraryFile) {
    List<ItineraryJSON> itineraries = Collections.emptyList();

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    try {
      if (!itineraryFile.createNewFile()) {
        JsonReader jsonReader = new JsonReader(new FileReader(itineraryFile));
//              String jsonContent = readFileAsString(filename);

        List<ItineraryJSON> temp = gson.fromJson(jsonReader, new TypeToken<List<ItineraryJSON>>() {}.getType());
        if (temp != null) itineraries = temp;
        jsonReader.close();
      }
    } catch (IOException | NullPointerException e) {
      throw new RuntimeException(e);
    }
    return itineraries;
  }

  public ItineraryJSON getItineraryFromFile(File itineraryFile, int itineraryId) {

    List<ItineraryJSON> list = getItineraryListFromFile(itineraryFile);
    return list.stream()
        .filter(itinerary -> itinerary.getItineraryId() == itineraryId)
        .findFirst().orElse(null);
  }

  public void addItineraryToFile(File itineraryFile, ItineraryJSON itinerary) {
    List<ItineraryJSON> itineraries = getItineraryListFromFile(itineraryFile);
    if (itineraries.isEmpty()) {
      itinerary.setItineraryId(1);
      itineraries = Collections.singletonList(itinerary);
    } else {
      itinerary.setItineraryId(itineraries.size() + 1);
      itineraries.add(itinerary);
    }

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
