package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.transaction.TestTransaction;

import com.sirnoob97.storageservice.file.entity.FileData;
import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import com.sirnoob97.storageservice.file.entity.FileRepository;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileDataRepositoryTest {

  @Autowired
  private FileDataRepository fileDataRepository;

  @Autowired
  private FileRepository fileRepository;

  @Test
  void test_Save_ReturnANonFileDataEntity_WhenSuccessful() {
    var fileData = randomFileData();
    var fileDataDb = assertDoesNotThrow(() -> fileDataRepository.save(fileData));

    assertNotNull(fileData);
    assertNotNull(fileDataDb);
    assertNotNull(fileDataDb.getId());
    assertNotNull(fileDataDb.getFileData());

    assertEquals(fileData.getId(), fileDataDb.getId());
    assertEquals(fileData.getFileData(), fileDataDb.getFileData());
  }

  @Test
  void test_Save_ThrowInvalidDataAccessApiUsageException_WhenFileDataParameterIsNull() {
    assertThrows(InvalidDataAccessApiUsageException.class, () -> fileDataRepository.save(null));
  }

  @Test
  void test_Save_ThrowDataIntegrityViolationException_WhenFileDataByteArrayIsNull() {
    var fileData = FileData.builder().fileData(null).build();
    assertThrows(DataIntegrityViolationException.class, () -> fileDataRepository.save(fileData));
  }

  @Test
  void test_Save_NoExceptionIsThrown_WhenFilaDataByteArrayIsEmpty() {
    var emptyArray = new byte[0];
    var fileData = FileData.builder().fileData(emptyArray).build();
    var fileDataDb = assertDoesNotThrow(() -> fileDataRepository.save(fileData));

    assertNotNull(fileDataDb);
    assertEquals(emptyArray.length, fileData.getFileData().length);
    assertEquals(emptyArray.length, fileDataDb.getFileData().length);
  }

  @Test
  void test_FindById_ReturnAPresentOptional_WhenSuccessful() {
    var id = 1L;
    var optional = fileDataRepository.findById(id);

    assertNotNull(optional);
    assertTrue(optional.isPresent());
    assertNotNull(optional.get());
    assertNotNull(optional.get().getId());
    assertEquals(id, optional.get().getId());
  }

  @Test
  void test_FindById_ReturnAnEmptyOptional_WhenNoFileDataEntityWasFound() {
    var optional = fileDataRepository.findById(-1L);

    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_FindById_ThrowInvalidDataAccessApiUsageException_WhenIdIsNull() {
    assertThrows(InvalidDataAccessApiUsageException.class, () -> fileDataRepository.findById(null));
  }

  @Test
  void test_Save_UpdateTheFileDataEntityAndNoExceptionIsThrown_WhenFileDataArrayIsEmpty() {
    var emptyArray = new byte[0];
    var fileData = new FileData(1L, emptyArray);
    var oldArray = fileDataRepository.findById(1L).get().getFileData();

    var updated = assertDoesNotThrow(() -> {
      TestTransaction.flagForCommit();
      var u = fileDataRepository.save(fileData);
      TestTransaction.end();
      return u;
    });
    var areEquals = Arrays.equals(fileData.getFileData(), updated.getFileData());

    assertNotNull(updated);
    assertNotEquals(emptyArray.length, oldArray.length);
    assertEquals(emptyArray.length, updated.getFileData().length);
    assertTrue(areEquals);
  }

  /**
   * Hibernate rolls back the transaction when the non-null constraint violation
   * occurs, so for Junit the exception was never thrown.
   *
   * To avoid this behavior @{org.springframework.test.context.transaction.TestTransaction}
   * forces the transaction to commit and throws the exception.
   */
  @Test
  void test_Save_DontUpdateAndThrowDataIntegrityViolationException_WhenFileDataArrayIsNull() {
    var fileData = fileDataRepository.findById(1L).get();
    fileData.setFileData(null);

    assertThrows(DataIntegrityViolationException.class, () -> {
      TestTransaction.flagForCommit();
      fileDataRepository.save(fileData);
      TestTransaction.end();
    });
  }

  @Test
  void test_Delete_DeleteAFileDataRelatedWithAFileEntituAndReturnOne_WhenSuccessful() {
    fileRepository.deleteFileById(1L);

    var fileDataRet = fileDataRepository.deleteFileDataById(1L);
    var optional = fileDataRepository.findById(1L);

    assertEquals(1, fileDataRet);
    assertNotNull(optional);
    assertTrue(optional.isEmpty());
  }

  @Test
  void test_Delete_ThrowDataIntegrityViolationException_WhenFileDataEntityIsRelatedWithAFile() {
    var ret = assertDoesNotThrow(() -> fileDataRepository.deleteFileDataById(1L));
    assertEquals(1, ret);
  }

  @Test
  void test_Delete_NoExceptionIsThrownAndReturnZero_WhenIdIsNull() {
    var ret = assertDoesNotThrow(() -> fileDataRepository.deleteFileDataById(null));
    assertEquals(0, ret);
  }
}
