package com.sirnoob97.storageservice.file.entity;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

  @Query(nativeQuery = true)
  Optional<FileDto> findFileDtoById(Long id);

  @Query(nativeQuery = true)
  Set<FileInfoDto> listFileInfoDtos(int limit, int offset);
}
