package com.sirnoob97.storageservice.file.service;

import static com.sirnoob97.storageservice.util.DtoGenerator.randomFileDto;
import static com.sirnoob97.storageservice.util.DtoGenerator.randomFileInfoDto;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomByteArray;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomString;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sirnoob97.storageservice.file.entity.File;
import com.sirnoob97.storageservice.file.entity.FileData;
import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import com.sirnoob97.storageservice.file.entity.FileRepository;

@SpringBootTest
class FileServiceTest {

  @Mock
  private FileRepository fileRepository;

  @Mock
  private FileDataRepository fileDataRepository;

  private FileService fileService;

  private static final FileData staticFileData = new FileData(1L, randomByteArray());
  private static final File staticFile = new File(1L, "TEST", 1L, "TEST", staticFileData);

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.openMocks(this);
    this.fileService = new FileServiceDefaultImpl(fileRepository, fileDataRepository);
  }

  @Test
  void test_PersistFile_ReturnALongValueGreaterThanZero() throws IOException {
    given(fileDataRepository.save(any(FileData.class))).willReturn(staticFileData);
    given(fileRepository.save(any(File.class))).willReturn(staticFile);

    var mpf = new MockMultipartFile(randomString(), "", randomString(), randomByteArray());
    long ret = fileService.persistFile(mpf);

    assertTrue(ret > 0L);
    verify(fileRepository, times(1)).save(any());
    verify(fileDataRepository, times(1)).save(any());
  }

  @Test
  void test_GetFileInfoDto_ReturnAPresentOptionalOfFileInfoDto() {
    given(fileRepository.findFileInfoDtoById(anyLong())).willReturn(Optional.of(randomFileInfoDto()));
    var ret = fileService.getFileInfoDto(1L);

    assertNotNull(ret);
    assertNotNull(ret.getId());
    assertNull(ret.getDownloadUrl());
    verify(fileRepository, times(1)).findFileInfoDtoById(anyLong());
  }

  @Test
  void test_GetFileInfoDto_ReturnAEmptyOptionalOfFileInfoDto() {
    given(fileRepository.findFileInfoDtoById(anyLong())).willReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> fileService.getFileInfoDto(1L));
    verify(fileRepository, times(1)).findFileInfoDtoById(anyLong());
  }

  @Test
  void test_GetFileDto_ReturnAPresentOptionalOfFileDto() {
    given(fileRepository.findFileDtoById(anyLong())).willReturn(Optional.of(randomFileDto()));
    var ret = fileService.getFileDto(1L);

    assertNotNull(ret);
    assertNotNull(ret.getFileData());
    verify(fileRepository, times(1)).findFileDtoById(anyLong());
  }

  @Test
  void test_GetFileDto_ReturnAEmptyOptionalOfFileDto() {
    given(fileRepository.findFileDtoById(anyLong())).willReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> fileService.getFileDto(1L));
    verify(fileRepository, times(1)).findFileDtoById(anyLong());
  }
}