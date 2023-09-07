package kdt_y_be_toy_project1.itinerary.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import kdt_y_be_toy_project1.common.data.DataFileProvider;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.common.data.ItineraryDataFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static kdt_y_be_toy_project1.itinerary.type.FileType.CSV;

public class ItineraryCSVDao implements ItineraryDao<ItineraryCSV> {

  private final DataFileProvider dataFileProvider;

  public ItineraryCSVDao() {
    this.dataFileProvider = new ItineraryDataFile();
  }

  public ItineraryCSVDao(DataFileProvider dataFileProvider) {
    this.dataFileProvider = dataFileProvider;
  }

  @Override
  public List<ItineraryCSV> getItineraryListByTripId(int tripId) {
    return getItineraryListFromFile(dataFileProvider.getDataFile(tripId, CSV));
  }

  @Override
  public ItineraryCSV getItineraryById(int tripId, int itineraryId) {
    return getItineraryFromFile(dataFileProvider.getDataFile(tripId, CSV), itineraryId);
  }

  @Override
  public void addItineraryByTripId(int tripId, ItineraryCSV itineraryCSV) {
    addItineraryToFile(dataFileProvider.getDataFile(tripId, CSV), itineraryCSV);
  }

  public List<ItineraryCSV> getItineraryListFromFile(File itineraryFile) {
    List<ItineraryCSV> itineraries;
    try {
      if (itineraryFile.createNewFile()) {
        itineraries = new ArrayList<>();
      } else {
        Reader bufferedReader = new BufferedReader(new FileReader(itineraryFile));
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

  public ItineraryCSV getItineraryFromFile(File itineraryFile, int itineraryId) {
    return getItineraryListFromFile(itineraryFile).stream()
        .filter(it -> it.getItineraryId() == itineraryId)
        .findFirst().orElse(null);
  }

  public void addItineraryToFile(File itineraryFile, ItineraryCSV itineraryCSV) {
    List<ItineraryCSV> itineraries = getItineraryListFromFile(itineraryFile);
    itineraryCSV.setItineraryId(itineraries.size() + 1);
    itineraries.add(itineraryCSV);

    try (Writer bufferedWriter = new BufferedWriter(new FileWriter(itineraryFile))) {
      StatefulBeanToCsv<ItineraryCSV> beanToCsv = new StatefulBeanToCsvBuilder<ItineraryCSV>(bufferedWriter).build();
      beanToCsv.write(itineraries);
    } catch (IOException | CsvException e) {
      throw new RuntimeException(e);
    }
  }
}
