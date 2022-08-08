package com.sirnoob97.storageservice.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FileInfoDto {

  private Long id;
  private String fileName;
  private Long fileSize;
  private String mimeType;

  @Setter
  private String downloadUrl;

  public FileInfoDto() {}

  public FileInfoDto(Long id, String fileName, Long fileSize, String mimeType) {
    this.id = id;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.mimeType = mimeType;
  }
}
