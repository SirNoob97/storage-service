package com.sirnoob97.storageservice.file.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.transaction.Transactional;
import com.sirnoob97.storageservice.file.entity.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FileRepositoryTest {
  
  @Autowired
  private FileRepository fileRepository;

  @Test
  void test_FindFileDtoById_ReturnAFileDto_WhenSuccessful() {
    var dto = fileRepository.findFileDtoById(1L);

    assertNotNull(dto);
    assertNotNull(dto.getFileName());
  }

  @Test
  void test_FindFileInfoDtoById_ReturnANonNullFileInfoDto_WhenSuccessful() {
    var dto = fileRepository.findFileInfoDtoById(1L);

    assertNotNull(dto);
    assertNotNull(dto.getFileName());
  }
}
