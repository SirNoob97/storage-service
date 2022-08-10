package com.sirnoob97.storageservice.util;

import static com.sirnoob97.storageservice.util.RandomValueGenerator.*;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public class DtoGenerator {
  
  public static FileInfoDto randomFileInfoDto() {
    return new FileInfoDto(randomLong(), randomString(), randomLong(), randomString());
  }

  public static FileDto randomFileDto() {
    return new FileDto(randomString(), randomLong(), randomString(), randomByteArray());
  }
}
