package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FileInfoDto {

  private Long id;
  private String fileName;
  private Long fileSize;
  private String mimeType;

  @Setter
  private String downloadUrl;

  public FileInfoDto(Long id, String fileName, Long fileSize, String mimeType) {
    this.id = id;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.mimeType = mimeType;
  }
}
