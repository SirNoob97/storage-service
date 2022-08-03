package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import javax.transaction.Transactional;
import com.sirnoob97.storageservice.file.entity.FileData;
import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileDataRepositoryTest {

  @Autowired
  private FileDataRepository fileDataRepository;

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

    var updated = assertDoesNotThrow(() -> fileDataRepository.save(fileData));
    var areEquals = Arrays.equals(fileData.getFileData(), updated.getFileData());

    assertNotNull(updated);
    assertNotEquals(emptyArray.length, oldArray.length);
    assertEquals(emptyArray.length, updated.getFileData().length);
    assertTrue(areEquals);
  }

  // BUG: A null byte array show throw an exception, normal postgreSQL db does but not in this test method
  @Test
  void test_Save_UpdateTheFileDataEntityAndNoExceptionIsThrown_WhenFileDataArrayIsNull() {
    var fileData = new FileData(1L, null);
    var oldArray = fileDataRepository.findById(1L).get().getFileData();
    var fileDataDb = fileDataRepository.save(fileData);

    assertNotNull(fileDataDb);
    assertNotNull(oldArray);
    assertNull(fileData.getFileData());
    assertNull(fileDataDb.getFileData());
  }
}
