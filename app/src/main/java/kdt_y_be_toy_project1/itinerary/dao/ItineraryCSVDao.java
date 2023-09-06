package kdt_y_be_toy_project1.itinerary.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItineraryCSVDao implements ItineraryDao<ItineraryCSV> {
  private static final String BASE_PATH = "/itinerary";
  private static final String BASE_NAME = "itineraries_trip_";
  private static final String FORMAT = "csv";

  @Override
  public List<ItineraryCSV> getItineraryListFromFile(int tripId) {
    if (tripId < 1) {
      throw new RuntimeException("tripId must be greater than 1");
    }

    List<ItineraryCSV> itineraries = null;

    File file = new File(getItineraryFilePathString(tripId));
    try {
      if (file.createNewFile()) {
        itineraries = new ArrayList<>();
      } else {
        Reader bufferedReader = new BufferedReader(new FileReader(file));
        itineraries = new CsvToBeanBuilder<ItineraryCSV>(bufferedReader)
            .withType(ItineraryCSV.class)
            .build()
            .parse();
        bufferedReader.close();
      }
    } catch (IOException | NullPointerException e) {
      throw new RuntimeException(e);
    }
    return itineraries;
  }

  @Override
  public ItineraryCSV getItineraryFromFile(int tripId, int itineraryId) {
    List<ItineraryCSV> itineraries = getItineraryListFromFile(tripId);
    return itineraries.stream()
        .filter(it -> it.getItineraryId() == itineraryId)
        .findFirst().orElse(null);
  }

  @Override
  public void addItineraryToFile(int tripId, ItineraryCSV itineraryCSV) {
    List<ItineraryCSV> itineraries = getItineraryListFromFile(tripId);
    itineraryCSV.setItineraryId(itineraries.size() + 1);
    itineraries.add(itineraryCSV);

    File file = new File(getItineraryFilePathString(tripId));
    try (Writer bufferedWriter = new BufferedWriter(new FileWriter(file))) {
      StatefulBeanToCsv<ItineraryCSV> beanToCsv = new StatefulBeanToCsvBuilder<ItineraryCSV>(bufferedWriter).build();
      beanToCsv.write(itineraries);
    } catch (IOException | CsvException e) {
      throw new RuntimeException(e);
    }
  }

  public String getItineraryFilePathString(int tripId) {
    try {
      Path resourcePath = Path.of(Objects.requireNonNull(this.getClass().getResource(BASE_PATH)).toURI())
            .toAbsolutePath();
      Path basePath = resourcePath.resolve(FORMAT);
      Files.createDirectories(basePath);

      return basePath.resolve(BASE_NAME + tripId + "." + FORMAT).toString();
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
