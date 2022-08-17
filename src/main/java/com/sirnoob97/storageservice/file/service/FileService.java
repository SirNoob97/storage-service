package com.sirnoob97.storageservice.file.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

public interface FileService {
  
  FileInfoDto persistNewFile(MultipartFile mpf, String downloadURL) throws IOException;

  FileDto getFileDto(long id);

  Set<FileInfoDto> listFiles(int limit, int offset, String downloadURL);

  void deleteFile(long id);
}
