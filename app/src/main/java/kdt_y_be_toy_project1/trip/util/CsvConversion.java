package kdt_y_be_toy_project1.trip.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;

import kdt_y_be_toy_project1.trip.dto.CreateTripRequest;
import kdt_y_be_toy_project1.trip.dto.TripResponse;

public class CsvConversion{


	private String path=System.getProperty("user.dir")+"/src/main/resources/format/";

	/**
	 * Path resourcePath = Path.of(Objects.requireNonNull(this.getClass().getResource(BASE_PATH)).toURI())
            .toAbsolutePath();
	 */
	public void convertToCsv(CreateTripRequest createTripRequest){

		if(createTripRequest!=null) {
			//trip fts후, maxId +1 해서 저장하기
			String filename=path+"trip_"+/*createTripRequest.getTripId()+*/".csv";

			try(CSVWriter writer = new CSVWriter(new FileWriter(filename))){
				ArrayList<String> attributes=new ArrayList<String>();
 				ArrayList<String> tuple=new ArrayList<String>();
 				for(Field field: createTripRequest.getClass().getDeclaredFields()) {
 					attributes.add(field.getName());
					field.setAccessible(true);
					String value=field.get(createTripRequest).toString();
					tuple.add(value);
				}
				writer.writeNext(attributes.toArray(String[]::new));
				writer.writeNext(tuple.toArray(String[]::new));
			}
			catch(IOException e) {
				e.printStackTrace();
				throw new RuntimeException("해당 파일 경로가 유효하지 않습니다.");
			}
			catch(IllegalAccessException e) {
				throw new RuntimeException("createTripRequest에 접근할 수 없습니다.");
			}
		}
		else {
			throw new RuntimeException("여행 정보가 올바르게 입력되지 않았습니다.");
		}

		//createTripReqeust가 변경됐을 때, 너무 의존적이다.
	}


	public List<TripResponse> convertToTrip(){
		List<TripResponse> tripResponses=new ArrayList<TripResponse>();
 		String[] line;
		File dir=new File(path);

		if(dir!=null && dir.length()!=0) {
			for(String fileName:dir.list()) {
				if(fileName.startsWith("trip_") && fileName.endsWith(".csv")) {
					try {
						List<TripResponse> tripResponse = new CsvToBeanBuilder<TripResponse>(new FileReader(path+fileName))
						            .withType(TripResponse.class)
						            .build()
						            .parse();
						tripResponses.addAll(tripResponse);

						//id로 조회 itineraies
						//csv 줄수 보고 count 넣어서 return;
						long findTripId=tripResponse.get(0).getTripId();
						String itinerariesFileName=path+"itineraries_"+String.valueOf(findTripId)+".csv";
						File itinerariesfile=new File(itinerariesFileName);
						if(!itinerariesfile.exists()) {
							throw new RuntimeException("관련 여정 기록이 없습니다.");
						}
						else {
							long itineraiesNum=0;
							try(CSVReader csvReader=new CSVReader(new FileReader(itinerariesFileName))){
								itineraiesNum=csvReader.getLinesRead()-1;
								if(itineraiesNum==-1) {
									itineraiesNum=0;
								}
							}
							tripResponse.get(0).setItineraryCount(itineraiesNum);
						}
					}
					catch(IOException e) {
						e.printStackTrace();
						throw new RuntimeException(path+fileName+":해당 파일을 찾을 수 없습니다.");
					}
				}
			}
		}
		else {
			throw new RuntimeException("여행 기록이 존재하지 않습니다.");
		}

		return tripResponses;
	}

}
