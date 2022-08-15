package com.sirnoob97.storageservice.file.service;

import java.io.IOException;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
@Transactional
@Service
public class FileServiceDefaultImpl implements FileService {

  private static final ResponseStatusException FILE_NOT_FOUND_EXCEPTION = new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");

  private final FileRepository fileRepository;
  private final FileDataRepository fileDataRepository;

  @Override
  public FileInfoDto persistNewFile(MultipartFile mpf, String downloadURL) throws IOException {
    var fileData = extractFileData(mpf);
    var persistedData = fileDataRepository.save(fileData);
    var file = buildFile(mpf, persistedData);
    var persistedFile = fileRepository.save(file);

    return builFileInfoDto(downloadURL, persistedFile);
  }

  @Override
  public FileDto getFileDto(long id) {
    return fileRepository.findFileDtoById(id).orElseThrow(
        () -> FILE_NOT_FOUND_EXCEPTION);
  }

  @Override
  public Set<FileInfoDto> listFiles(int limit, int offset, String downloadURL) {
    var dtos = fileRepository.listFileInfoDtos(limit, offset);
    for (var dto : dtos) {
      dto.setDownloadUrl(downloadURL + dto.getId());
    }

    return dtos;
  }

  @Override
  public void deleteFile(long id) {
    int ret = fileDataRepository.deleteFileDataById(id);

    if (ret == 0) {
      throw FILE_NOT_FOUND_EXCEPTION;
    }
  }

  private FileInfoDto builFileInfoDto(String downloadURL, File file) {
    return FileInfoDto.builder()
        .id(file.getId())
        .fileName(file.getFileName())
        .fileSize(file.getFileSize())
        .mimeType(file.getMimeType())
        .downloadUrl(downloadURL + file.getId())
        .build();
  }

  private File buildFile(MultipartFile mpf, FileData data) {
    var fileName = StringUtils.cleanPath(mpf.getOriginalFilename());
    return File.builder()
        .fileName(fileName)
        .mimeType(mpf.getContentType())
        .fileSize(mpf.getSize())
        .data(data)
        .build();
  }

  private FileData extractFileData(MultipartFile mpf) throws IOException {
    return FileData.builder().fileData(mpf.getBytes()).build();
  }

}
