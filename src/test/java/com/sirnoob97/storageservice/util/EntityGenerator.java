package com.sirnoob97.storageservice.util;

import com.sirnoob97.storageservice.file.entity.File;
import com.sirnoob97.storageservice.file.entity.FileData;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomByteArray;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomString;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomLong;

public class EntityGenerator {

  public static FileData randomFileData() {
    //@formatter:off
    return FileData.builder()
      .fileData(randomByteArray())
      .build();
    //@formatter:on
  }

  public static File randomFile() {
    //@formatter:off
    return File.builder()
      .fileName(randomString())
      .mimeType(randomString())
      .fileSize(randomLong())
      .data(randomFileData())
      .build();
    //@formatter:on
  }
}
