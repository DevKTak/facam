package kdt_y_be_toy_project1.itinerary.dao;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import kdt_y_be_toy_project1.itinerary.entity.ItineraryJSON;
import kdt_y_be_toy_project1.itinerary.exception.file.FileIOException;
import kdt_y_be_toy_project1.itinerary.exception.service.ItineraryNotFoundException;
import kdt_y_be_toy_project1.itinerary.exception.format.JsonParseException;
import kdt_y_be_toy_project1.itinerary.exception.service.TripPeriodMismatchException;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItineraryJSONDao implements ItineraryDao<ItineraryJSON> {

    private static final String BASE_PATH = "itinerary/json/";
    private static final String BASE_NAME = "itineraries_trip_";
    private static final String FORMAT = ".json";

    @Override
    public List<ItineraryJSON> getItineraryListFromFile(long tripId) {

      File file = creatFile(tripId);
      Reader bufferedReader = createFileReader(file);
      return parseJsonToList(bufferedReader);
    }

    @Override
    public void addItineraryToFile(long tripId, ItineraryJSON itinerary) {

        if(false){ //Trip 기간에만 여정을 추가할 때 Trip의 정보가 필요함..
            throw new TripPeriodMismatchException("여행기간에만 여정을 추가할 수 있습니다");
        }
        List<ItineraryJSON> itineraries = getItineraryListFromFile(tripId);
        itinerary.setItineraryId(itineraries.size() + 1);
        itineraries.add(itinerary);

        File file = creatFile(tripId);
        String jsonArray = convertItineraryListToJson(itineraries);
        writeJsonToFile(file, jsonArray);
    }

    @Override
    public ItineraryJSON getItineraryFromFile(long tripId, long itineraryId) {

        checkValidSize(tripId, itineraryId);
        List<ItineraryJSON> list = getItineraryListFromFile(tripId);

        return list.get((int) itineraryId);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////

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

    public List<ItineraryJSON> parseJsonToList(Reader bufferedReader){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        List<ItineraryJSON> itineraries;

        try{
            itineraries = gson.fromJson(bufferedReader, new TypeToken<List<ItineraryJSON>>() {}.getType());
        } catch (JsonSyntaxException e){
            throw new JsonParseException("읽으려는 파일이 Json 파일 형식에 맞지 않습니다.");
        }
        return itineraries;
    }

    public String convertItineraryListToJson(List<ItineraryJSON> itineraries){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String response;
        try{
            response = gson.toJson(itineraries);
        } catch (JsonParseException e){
            throw new JsonParseException("Json 파일 형식에 맞지 않습니다.");
        }
        return response;
    }

    public void writeJsonToFile(File file, String jsonArray){
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(jsonArray);
            writer.close();
        } catch (IOException e) {
            throw new FileIOException("File을 쓸 수 없습니다.");
        }
    }

    private String getItineraryFilePathString(long tripId) {
        String path;
        try{
            path = Objects.requireNonNull(ItineraryCSVDao.class.getClassLoader()
                            .getResource(BASE_PATH + BASE_NAME + tripId + FORMAT))
                    .getPath();
        } catch (NullPointerException e){
            throw new FileIOException("해당하는 여행 파일이 존재하지 않습니다.");
        }
        return path;
    }

    public void checkValidSize(long tripId, long itineraryId){
        List<ItineraryJSON> list = getItineraryListFromFile(tripId);
        if(list.size() < itineraryId){
            throw new ItineraryNotFoundException("찾으려는 여정이 없습니다");
        }
    }
}
