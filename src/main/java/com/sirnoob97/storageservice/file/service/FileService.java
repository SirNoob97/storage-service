package com.sirnoob97.storageservice.file.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public interface FileService {
  
  long persistNewFile(MultipartFile mpf) throws IOException;

  FileDto getFileDto(long id);

  Set<FileInfoDto> listFiles(int limit, int offset);

  void deleteFile(long id);
}
