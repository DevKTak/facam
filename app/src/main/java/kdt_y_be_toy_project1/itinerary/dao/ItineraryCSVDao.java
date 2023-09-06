package kdt_y_be_toy_project1.itinerary.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.CSV;

public class ItineraryCSVDao implements ItineraryDao<ItineraryCSV> {
  @Override
  public List<ItineraryCSV> getItineraryListFromFile(int tripId) {
    if (tripId < 1) {
      throw new RuntimeException("tripId must be greater than 1");
    }

    List<ItineraryCSV> itineraries;

    File file = new ItineraryDataFile().getDataFile(tripId, CSV);
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

    File file = new ItineraryDataFile().getDataFile(tripId, CSV);
    try (Writer bufferedWriter = new BufferedWriter(new FileWriter(file))) {
      StatefulBeanToCsv<ItineraryCSV> beanToCsv = new StatefulBeanToCsvBuilder<ItineraryCSV>(bufferedWriter).build();
      beanToCsv.write(itineraries);
    } catch (IOException | CsvException e) {
      throw new RuntimeException(e);
    }
  }
}
