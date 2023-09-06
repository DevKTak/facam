package kdt_y_be_toy_project1.itinerary.dao;

import java.util.List;

public interface ItineraryDao<T> {
  List<T> getItineraryListFromFile(int tripId);
  T getItineraryFromFile(int tripId, int itineraryId);

  // 파일이 없으면 만들어서 여정을 넣고
  // 피일이 있으면 여정을 추가해서 파일에 추가한다
  // 파라미터로 온 객체를 포맷에 따라 변환 -> 넣고 -> 객체 변환<json,csv> -> 파일
  void addItineraryToFile(int tripId, T itinerary);
}
