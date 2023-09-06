package kdt_y_be_toy_project1.common.data;

import kdt_y_be_toy_project1.itinerary.type.FileType;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@AllArgsConstructor
public abstract class DataFileProvider {
  protected String domainPath;
  protected String baseName;

  public final File getDataFile(int tripId, FileType fileType) {
    String format = switch (fileType) {
      case JSON -> "json";
      case CSV -> "csv";
    };

    try {
      Path resourceDirPath = Path.of(System.getProperty("user.dir"), domainPath, format);
      Files.createDirectories(resourceDirPath);
      Path dataFilePath = Path.of(resourceDirPath.toString(), baseName + "_" + tripId + "." + format);

      return dataFilePath.toFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
