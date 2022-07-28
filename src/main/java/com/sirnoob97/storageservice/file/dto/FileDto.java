package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
class FileDto {

  public String fileName;
  public Long fileSize;
  public String mimeType;
  public Byte[] fileData;

}
