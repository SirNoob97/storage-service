package com.sirnoob97.storageservice.file.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
