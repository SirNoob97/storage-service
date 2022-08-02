package com.sirnoob97.storageservice.file.repository;

import static com.sirnoob97.storageservice.util.EntityGenerator.randomFileData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.transaction.Transactional;
import com.sirnoob97.storageservice.file.entity.FileDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileDataRepositoryTest {

  @Autowired
  private FileDataRepository fileDataRepository;

  @Test
  public void test_Save_ReturnANonFileDataEntity_WhenSuccessful() {
    var fileData = randomFileData();
    var fileDataDb = fileDataRepository.save(fileData);

    assertNotNull(fileData);
    assertNotNull(fileDataDb);
    assertNotNull(fileDataDb.getId());
    assertNotNull(fileDataDb.getFileData());

    assertEquals(fileData.getId(), fileDataDb.getId());
    assertEquals(fileData.getFileData(), fileDataDb.getFileData());
  }
}
