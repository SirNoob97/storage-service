package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FileDto {

  private String fileName;
  private Long fileSize;
  private String mimeType;
  private byte[] fileData;
}
