package com.sirnoob97.storageservice.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class FileInfoDto {

  public String fileName;
  public Long fileSize;
  public String mimeType;

  @Setter
  public String downloadUrl;
}
