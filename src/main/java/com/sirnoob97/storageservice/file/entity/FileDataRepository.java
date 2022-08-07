package com.sirnoob97.storageservice.file.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

  @Modifying
  @Query(value = "DELETE FROM file_data WHERE file_data.id = ?", nativeQuery = true)
  int deleteFileDataById(Long id);
}
