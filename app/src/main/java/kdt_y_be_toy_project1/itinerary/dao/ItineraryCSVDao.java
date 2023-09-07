package kdt_y_be_toy_project1.itinerary.dao;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryCSV;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import kdt_y_be_toy_project1.itinerary.exception.format.CsvParseException;
import kdt_y_be_toy_project1.itinerary.exception.format.JsonParseException;
import kdt_y_be_toy_project1.itinerary.exception.service.ItineraryNotFoundException;
import kdt_y_be_toy_project1.itinerary.exception.service.TripPeriodMismatchException;

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
  public List<ItineraryCSV> getItineraryListFromFile(long tripId) {

    File file = creatFile(tripId);
    Reader bufferedReader = createFileReader(file);
    return parseCsvToList(bufferedReader);
  }

  @Override
  public ItineraryCSV getItineraryFromFile(long tripId, long itineraryId) {
    checkValidSize(tripId, itineraryId);
    List<ItineraryCSV> list = getItineraryListFromFile(tripId);
    return list.get((int)(itineraryId));
  }

  @Override
  public void addItineraryToFile(long tripId, ItineraryCSV itineraryCSV) {

    if(false){ //Trip 기간에만 여정을 추가할 때 Trip의 정보가 필요함..
      throw new TripPeriodMismatchException("여행기간에만 여정을 추가할 수 있습니다");
    }
    List<ItineraryCSV> itineraries = getItineraryListFromFile(tripId);
    itineraryCSV.setItineraryId(itineraries.size() + 1);
    itineraries.add(itineraryCSV);

    File file = creatFile(tripId);
    writeCsvToFile(file, itineraries);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  public void writeCsvToFile(File file, List<ItineraryCSV> itineraries) {
    try (FileWriter writer = new FileWriter(file)) {
      StatefulBeanToCsv<ItineraryCSV> beanToCsv = new StatefulBeanToCsvBuilder<ItineraryCSV>(writer).build();
      beanToCsv.write(itineraries);
    } catch (IOException | CsvException e) {
      throw new FileIOException("File에 내용을 쓸 수 없습니다.");
    }
  }

  public void checkValidSize(long tripId, long itineraryId){
    List<ItineraryCSV> list = getItineraryListFromFile(tripId);
    if(list.size() < itineraryId){
      throw new ItineraryNotFoundException("찾으려는 여정이 없습니다");
    }
  }

  public List<ItineraryCSV> parseCsvToList(Reader bufferedReader){
    List<ItineraryCSV> itineraries;
    try{
      itineraries = new CsvToBeanBuilder<ItineraryCSV>(bufferedReader)
              .withType(ItineraryCSV.class)
              .build()
              .parse();
      bufferedReader.close();
    } catch (IllegalStateException | IOException e){
      throw new CsvParseException("읽으려는 파일이 Csv 파일 형식에 맞지 않습니다.");
    }

    return itineraries;
  }

  public File creatFile(long tripId){
    return new File(getItineraryFilePathString(tripId));
  }

  public Reader createFileReader(File file){
    Reader bufferedReader;
    try{
      bufferedReader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e){
      throw new FileIOException("파일을 읽을 수 없습니다.");
    }
    return bufferedReader;
  }

  public String getItineraryFilePathString(long tripId) {
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
