package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFile;
import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import com.sirnoob97.storageservice.file.entity.FileRepository;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileRepositoryTest {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private FileDataRepository fileDataRepository;

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

  @Test
  void test_Save_PersistAndRetunrAFileEntity_WhenSuccessful() {
    var persistedData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(persistedData);

    var fileDb = fileRepository.save(file);

    assertNotNull(persistedData);
    assertNotNull(fileDb);
    assertNotNull(fileDb.getData());
    assertEquals(file.getData(), fileDb.getData());
    assertEquals(persistedData, fileDb.getData());
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenFileDataIsNull() {
    var file = randomFile();
    file.setData(null);
    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_NothingIsThrown_WhenFileNameIsEmpty() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setFileName("");

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenFileNameIsNull() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setFileName(null);

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_NothingIsThrown_WhenFileSizeIsNegative() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setFileSize(-1L);

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenFileSizeIsNull() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setFileSize(null);

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_NothingIsThrown_WhenMimeTypeIsEmpty() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setMimeType("");

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenMimeTypeIsNull() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = randomFile();
    file.setData(fileData);
    file.setMimeType(null);

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenFileDataIsAlreadyRelatedWithAnotherFileEntity() {
    var fileData = fileDataRepository.findById(1L).get();
    var file = randomFile();
    file.setData(fileData);

    assertThrows(DataIntegrityViolationException.class, () -> fileRepository.save(file));
  }
}
