package com.sirnoob97.storageservice.util;

import static com.sirnoob97.storageservice.util.RandomValueGenerator.*;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public class DtoGenerator {

  public static FileInfoDto randomFileInfoDto() {
    return FileInfoDto.builder()
        .id(randomLong())
        .fileName(randomString())
        .fileSize(randomLong())
        .mimeType(randomString())
        .downloadUrl(randomString())
        .build();
  }

  public static FileDto randomFileDto() {
    return FileDto.builder()
        .fileName(randomString())
        .fileSize(randomLong())
        .mimeType(randomString())
        .fileData(randomByteArray())
        .build();
  }
}
