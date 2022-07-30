package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileDto {

  private String fileName;
  private Long fileSize;
  private String mimeType;
  private Byte[] fileData;
}
