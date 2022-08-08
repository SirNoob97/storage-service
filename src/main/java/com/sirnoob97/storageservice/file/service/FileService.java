package com.sirnoob97.storageservice.file.service;

import java.util.Set;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public interface FileService {
  
  Long persistFile();

  FileInfoDto getFileInfoDto();

  FileDto getFileDto(Long id);

  Set<FileInfoDto> listFiles(int limit, int offset);

  void deleteFile(Long id);
}
