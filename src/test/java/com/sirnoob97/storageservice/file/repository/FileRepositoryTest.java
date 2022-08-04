package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFile;
import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sirnoob97.storageservice.file.entity.FileRepository;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileRepositoryTest {

  @Autowired
  private FileRepository fileRepository;

  @Test
  void test_FindFileDtoById_ReturnAPresentFileDtoOptional_WhenSuccessful() {
    var optional = fileRepository.findFileDtoById(1L);

    assertNotNull(optional);
    assertTrue(optional.isPresent());
    assertNotNull(optional.get());
  }

  @Test
  void test_FindFileDtoById_ReturnAnEmptyFileDtoOptional_WhenNoFileWasFound() {
    var optional = fileRepository.findFileDtoById(-1L);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_FindFileDtoById_ReturnAnEmptyFileDtoOptional_WhenIdIsNull() {
    var optional = fileRepository.findFileDtoById(null);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_FindFileInfoDtoById_ReturnAPresentFileInfoDtoOptional_WhenSuccessful() {
    var optional = fileRepository.findFileInfoDtoById(1L);

    assertNotNull(optional);
    assertTrue(optional.isPresent());
    assertNotNull(optional.get());
  }

  @Test
  void test_FindFileInfoDtoById_ReturnAnEmptyFileInfoDtoOptional_WhenNoFileWasFound() {
    var optional = fileRepository.findFileInfoDtoById(-1L);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_FindFileInfoDtoById_ReturnAnEmptyFileInfoDtoOptional_WhenIdIsNull() {
    var optional = fileRepository.findFileInfoDtoById(null);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  }
}
