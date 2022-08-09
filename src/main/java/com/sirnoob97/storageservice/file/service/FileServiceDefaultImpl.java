package com.sirnoob97.storageservice.file.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;
import com.sirnoob97.storageservice.file.entity.File;
import com.sirnoob97.storageservice.file.entity.FileData;
import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import com.sirnoob97.storageservice.file.entity.FileRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileServiceDefaultImpl implements FileService {

  private static final ResponseStatusException FILE_NOT_FOUND_EXCEPTION = new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");

  private final FileRepository fileRepository;
  private final FileDataRepository fileDataRepository;

  @Override
  public long persistFile(MultipartFile mpf) throws IOException {
    FileData fileData = FileData.builder().fileData(mpf.getBytes()).build();
    var persistedData = fileDataRepository.save(fileData);
    var fileInfo = File.builder()
        .fileName(mpf.getName())
        .mimeType(mpf.getContentType())
        .fileSize(mpf.getSize())
        .data(persistedData)
        .build();

    return fileRepository.save(fileInfo).getId();
  }

  @Override
  public FileInfoDto getFileInfoDto(long id) {
    return fileRepository.findFileInfoDtoById(id).orElseThrow(
        () -> FILE_NOT_FOUND_EXCEPTION);
  }

  @Override
  public FileDto getFileDto(long id) {
    return fileRepository.findFileDtoById(id).orElseThrow(
        () -> FILE_NOT_FOUND_EXCEPTION);
  }

  /**
   * TODO:
   * 1) Create a new native query to return a set of {@link com.sirnoob97.storageservice.file.dto.FileInfoDto}
   * 2) Create an enum for the order criteria
   */
  @Override
  public Set<FileInfoDto> listFiles(int limit, int offset, String orderBy) {
    return null;
  }

  /**
   * BUG: This method should delete both {@link com.sirnoob97.storageservice.file.entity.File} and the {@link com.sirnoob97.storageservice.file.entity.FileData}
   *  without a previous search of the {@link com.sirnoob97.storageservice.file.entity.File} entity
   * 
   * Refactor the native query to delete the related {@link com.sirnoob97.storageservice.file.entity.FileData}
   * Or add a cascade on delete operations in the sql table
   */
  @Override
  public void deleteFile(long id) {
    int ret = fileRepository.deleteFileById(id);

    if (ret == 0) {
      throw FILE_NOT_FOUND_EXCEPTION;
    }
  }
}
