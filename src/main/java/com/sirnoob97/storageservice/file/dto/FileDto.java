package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class FileDto {

  public String fileName;
  public Long fileSize;
  public String mimeType;
  public Byte[] fileData;
}
