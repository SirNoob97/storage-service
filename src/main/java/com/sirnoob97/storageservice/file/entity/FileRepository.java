package com.sirnoob97.storageservice.file.entity;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

  @Query(nativeQuery = true)
  Optional<FileDto> findFileDtoById(Long id);

  @Query(nativeQuery = true)
  Optional<FileInfoDto> findFileInfoDtoById(Long id);
}
