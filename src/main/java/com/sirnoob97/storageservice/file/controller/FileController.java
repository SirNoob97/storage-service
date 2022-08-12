package com.sirnoob97.storageservice.file.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sirnoob97.storageservice.file.dto.FileInfoDto;
import com.sirnoob97.storageservice.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller(value = "/file")
public class FileController {

  private final FileService fileService;

  @PostMapping
  public ResponseEntity<FileInfoDto> upload(@RequestPart(name = "attachment") MultipartFile mpf) throws IOException {
    var downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/")
        .toUriString();
    var ret = fileService.persistNewFile(mpf, downloadUrl);

    return ResponseEntity.status(HttpStatus.CREATED).body(ret);
  }

  @PostMapping(path = "/{id}")
  public ResponseEntity<?> update(@RequestPart(name = "attachment") MultipartFile mpf,
      @PathVariable long id) {

    return null;
  }

  @GetMapping
  public ResponseEntity<Set<FileInfoDto>> list(@RequestParam int limit, @RequestParam int offset) {
    return ResponseEntity.ok(fileService.listFiles(limit, offset));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Resource> download(@PathVariable long id) {
    var file = fileService.getFileDto(id);
    var contentDisposition = String.format("attachment; filename=\"%s\"", file.getFileName());
    var responseBody = new ByteArrayResource(file.getFileData());

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getMimeType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(responseBody);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {

    return null;
  }
}
