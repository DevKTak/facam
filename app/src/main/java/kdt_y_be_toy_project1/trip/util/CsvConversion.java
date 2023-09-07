package kdt_y_be_toy_project1.trip.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import kdt_y_be_toy_project1.trip.domain.TripCsv;

public class CsvConversion{

	private static final String TRIP_PATH=System.getProperty("user.dir")+"/src/main/resources/trip/csv/";
	private static final String ITINERARY_PATH=System.getProperty("user.dir")+"/src/main/resources/itinerary/csv/";
	private static final String TRIP_BASE="trip_";
	private static final String ITINERARY_BASE="itineraries_trip_";
	private static final String EXTENSION=".csv";
	
	
	public CsvConversion(){
		File tripDir=new File(TRIP_PATH);
		
		if(!tripDir.exists()) {
			tripDir.mkdirs();
		}
	}
	
	public void generateTripId(TripCsv tripCsv) {
		if(tripCsv!=null) {
			long newTripId=1;  
			File dir=new File(TRIP_PATH);
			
			if(dir.list()==null) {
				throw new RuntimeException("해당 디렉토리가 존재하지 않습니다.");
			}
			for(String fileName:dir.list()) {
				if(fileName.startsWith("trip_") && fileName.endsWith(".csv")) {
					String existTripId=fileName.subSequence(TRIP_BASE.length(), fileName.length()-EXTENSION.length()).toString();
					try {
						newTripId=Math.max(Long.parseLong(existTripId)+1,newTripId);
					}
					catch (NumberFormatException e){
						continue;
						//trip_[잘못된 id값].csv
					}
				}
			}
			//path내 trip_.csv 파일 중 가장 큰 값+1로 id 설정
			tripCsv.setTripId(newTripId);
		
		}
		else {
			throw new RuntimeException("여행 정보가 올바르게 입력되지 않았습니다.");
		}
			
	}
      
	public long saveAsCsv(TripCsv tripCsv){
		
		generateTripId(tripCsv);
		
		String filename=TRIP_PATH+TRIP_BASE+tripCsv.getTripId()+EXTENSION;
		
			try {
				Path filePath= Paths.get(filename);
				Files.createFile(filePath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
				StatefulBeanToCsv<TripCsv> beanToCsv=new StatefulBeanToCsvBuilder<TripCsv>(writer).build();
				beanToCsv.write(tripCsv);
			}
			catch(IOException | CsvException e) {
				throw new RuntimeException(e);
			} 
			
			return tripCsv.getTripId();
	}
	public long countItineraries(long tripId) {
		System.out.println(tripId);
		long itinerariesCount=0;
		String itinerariesFileName=ITINERARY_PATH+ITINERARY_BASE+String.valueOf(tripId)+EXTENSION;
		File itinerariesfile=new File(itinerariesFileName);
		if(itinerariesfile.exists()) {
			try {
				itinerariesCount=Files.lines(itinerariesfile.toPath()).count();
				itinerariesCount=(itinerariesCount==0)?0:itinerariesCount-1;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return itinerariesCount;
	}
		
	public List<TripCsv> saveAsTrip(){
		List<TripCsv> tripCsvs=new ArrayList<TripCsv>();
		File dir=new File(TRIP_PATH);
		
		if(dir.list()!=null && dir.list().length!=0) {
			for(String fileName:dir.list()) {
				if(fileName.startsWith("trip_") && fileName.endsWith(".csv")) {
					try {
						BufferedReader br=new BufferedReader(new FileReader(TRIP_PATH+fileName));
						List<TripCsv> retriveTripCsv = new CsvToBeanBuilder<TripCsv>(br)
						            .withType(TripCsv.class)
						            .build()
						            .parse();
					
						br.close();
						
						if(retriveTripCsv==null || retriveTripCsv.size()==0) {
							continue;
						}
						else {
							tripCsvs.addAll(retriveTripCsv);
							retriveTripCsv.get(0).setItineraryCount(countItineraries(retriveTripCsv.get(0).getTripId()));
						}
					}
					catch(IOException e) {
						throw new RuntimeException(e);
					}		
				}		
			}
		}
		else {
			throw new RuntimeException("해당 폴더에 여행 기록이 존재하지 않습니다.");
		}
		
		if(tripCsvs.size()==0) {
			throw new RuntimeException("여행 기록이 존재하지 않습니다.");
		}
		
		return tripCsvs;
	}
	
}
