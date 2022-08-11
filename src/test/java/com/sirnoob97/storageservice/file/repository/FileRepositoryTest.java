package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFile;
import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomByteArray;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomLong;
import static com.sirnoob97.storageservice.util.RandomValueGenerator.randomString;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.transaction.TestTransaction;

import com.sirnoob97.storageservice.file.dto.FileInfoDto;
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
  void test_ListFileInfoDtos_ReturnANonEmptySetOfFileInfoDto_WhenSuccessful() {
    var optional = fileRepository.listFileInfoDtos(10, 0);

    optional.stream().map(FileInfoDto::getFileName).forEach(System.out::println);

    assertNotNull(optional);
    assertFalse(optional.isEmpty());
    assertTrue(optional.size() > 0);
  }

  @Test
  void test_ListFileInfoDtos_ReturnAnEmptySetOfFileInfoDto_WhenOffsetIsTooHigh() {
    var optional = fileRepository.listFileInfoDtos(10, 1000000);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_ListFileInfoDtos_ReturnAnEmptySetOfFileInfoDto_WhenLimitIsZero() {
    var optional = fileRepository.listFileInfoDtos(0, 0);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_ListFileInfoDtos_ReturnAnEmptySetOfFileInfoDto_WhenOrderByCriteriaIsNull() {
    var optional = fileRepository.listFileInfoDtos(10, 0);

    assertNotNull(optional);
    assertFalse(optional.isEmpty());
  }

  @Test
  void test_FindById_ReturnAPresentOptional_WhenSuccessful() {
    var id = 1L;
    var optional = fileRepository.findById(id);

    assertNotNull(optional);
    assertTrue(optional.isPresent());
    assertNotNull(optional.get());
    assertNotNull(optional.get().getId());
    assertEquals(id, optional.get().getId());
  }

  @Test
  void test_FindById_ReturnAnEmptyOptional_WhenNoFileEntityWasFound() {
    var optional = fileRepository.findById(-1L);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_FindById_ThrowInvalidDataAccessApiUsageException_WhenIdIsNull() {
    assertThrows(InvalidDataAccessApiUsageException.class, () -> fileRepository.findById(null));
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
  void test_Save_DataIntegrityViolationException_WhenFileNameIsEmpty() {
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
  void test_Save_DataIntegrityViolationException_WhenFileSizeIsNegative() {
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
  void test_Save_ThrowDataIntegrityViolationException_WhenMimeTypeIsEmpty() {
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

  @Test
  void test_Save_UpdateAndReturnAFileEntity_WhenSuccessful() {
    var file = fileRepository.findById(1L).get();
    var oldFileName = file.getFileName();
    var oldMimeType = file.getMimeType();
    var oldFileSize = file.getFileSize();

    file.setFileName(randomString());
    file.setMimeType(randomString());
    file.setFileSize(randomLong());

    var updated = assertDoesNotThrow(() -> {
      TestTransaction.flagForCommit();
      var u = fileRepository.save(file);
      TestTransaction.end();
      return u;
    });

    assertNotNull(file);
    assertNotNull(updated);
    assertNotEquals(oldFileName, updated.getFileName());
    assertNotEquals(oldMimeType, updated.getMimeType());
    assertNotEquals(oldFileSize, updated.getFileSize());
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenFileNameIsEmpty() {
    var file = fileRepository.findById(1L).get();
    file.setFileName("");

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenFileNameIsNull() {
    var file = fileRepository.findById(1L).get();
    file.setFileName(null);

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenMimeTypeIsEmpty() {
    var file = fileRepository.findById(1L).get();
    file.setMimeType("");

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenMimeTypeIsNull() {
    var file = fileRepository.findById(1L).get();
    file.setMimeType(null);

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenFileSizeIsNegative() {
    var file = fileRepository.findById(1L).get();
    file.setFileSize(-1L);

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenFileSizeIsNull() {
    var file = fileRepository.findById(1L).get();
    file.setFileSize(null);

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }

  @Test
  void test_Save_DontTrownException_WhenFileDataArrayIsUpdated() {
    var file = fileRepository.findById(1L).get();
    var fileData = file.getData();
    var oldArray = fileData.getFileData();
    fileData.setFileData(randomByteArray());

    var fileDataUpdated = fileDataRepository.save(fileData);
    file.setData(fileDataUpdated);

    var fileUpdated = assertDoesNotThrow(() -> {
      TestTransaction.flagForCommit();
      var u = fileRepository.save(file);
      TestTransaction.end();
      return u;
    });

    assertNotNull(fileUpdated);
    assertNotNull(fileUpdated.getData());
    assertNotNull(fileDataUpdated.getFileData());
    assertNotEquals(oldArray, fileUpdated.getData().getFileData());
    assertNotEquals(oldArray, fileDataUpdated.getFileData());
  }

  @Test
  void test_Save_ThrowJpaSystemException_WhenFileDataEntityIsReplacedWithAnotherAlreadyPersistedFileDataEntity() {
    var fileData = fileDataRepository.save(randomFileData());
    var file = fileRepository.findById(1L).get();
    file.setData(fileData);

    assertThrows(JpaSystemException.class, () -> {
      TestTransaction.flagForCommit();
      fileRepository.save(file);
      TestTransaction.end();
    });
  }
}
