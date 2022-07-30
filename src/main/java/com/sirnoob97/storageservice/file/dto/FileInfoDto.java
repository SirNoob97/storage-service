package com.sirnoob97.storageservice.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FileInfoDto {

  private String fileName;
  private Long fileSize;
  private String mimeType;

  @Setter
  private String downloadUrl;

  public FileInfoDto() {}

  public FileInfoDto(String fileName, Long fileSize, String mimeType) {
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.mimeType = mimeType;
  }
}
