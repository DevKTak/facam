package kdt_y_be_toy_project1.common.data;

import kdt_y_be_toy_project1.itinerary.type.FileType;
import org.junit.jupiter.api.Test;

import java.io.File;

class ItineraryDataPathTest {

  @Test
  void shouldGetItineraryFilePathString() {
    File file = new ItineraryDataFile().getDataFile(1, FileType.CSV);
    System.out.println(file);
  }
}