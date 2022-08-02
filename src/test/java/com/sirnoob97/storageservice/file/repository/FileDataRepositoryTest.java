package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    var fileDataDb = fileDataRepository.save(fileData);

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
}
