package com.sirnoob97.storageservice.file.service;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public interface FileService {
  
  long persistFile(MultipartFile mpf);

  FileInfoDto getFileInfoDto(long id);

  FileDto getFileDto(long id);

  Set<FileInfoDto> listFiles(int limit, int offset);

  void deleteFile(long id);
}
