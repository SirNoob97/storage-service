package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileInfoDto {

  private String fileName;
  private Long fileSize;
  private String mimeType;

  @Setter
  private String downloadUrl;
}
