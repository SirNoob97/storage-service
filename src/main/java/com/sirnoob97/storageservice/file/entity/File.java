package com.sirnoob97.storageservice.file.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.dto.FileInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity
@DynamicUpdate
//@formatter:off
@NamedNativeQueries(value = {
  @NamedNativeQuery(name = "File.findFileDtoById",
    query = """
      SELECT f.file_name AS fileName,
             f.file_size AS fileSize,
             f.mime_type AS mimeType,
             fd.file_data AS fileData
      FROM file AS f JOIN file_data AS fd
      ON f.data_id = fd.id
      WHERE f.id = :id
    """,
    resultSetMapping = "Mapping.fileToFileDto"),
  @NamedNativeQuery(name = "File.findFileInfoDtoById",
    query = """
      SELECT f.id As id,
             f.file_name AS fileName,
             f.file_size AS fileSize,
             f.mime_type AS mimeType
      FROM file AS f
      WHERE f.id = :id
    """,
    resultSetMapping = "Mapping.fileToFileInfoDto")
  })
@SqlResultSetMappings(value = {
    @SqlResultSetMapping(name = "Mapping.fileToFileDto",
      classes = @ConstructorResult(targetClass = FileDto.class,
                  columns = {
                    @ColumnResult(name = "fileName", type = String.class),
                    @ColumnResult(name = "fileSize", type = Long.class),
                    @ColumnResult(name = "mimeType", type = String.class),
                    @ColumnResult(name = "fileData", type = Byte[].class)
                  })),
    @SqlResultSetMapping(name = "Mapping.fileToFileInfoDto",
      classes = @ConstructorResult(targetClass = FileInfoDto.class,
                  columns = {
                    @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "fileName", type = String.class),
                    @ColumnResult(name = "fileSize", type = Long.class),
                    @ColumnResult(name = "mimeType", type = String.class)
                  }))
  })
//@formatter:on
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "file_size", nullable = false)
  private Long fileSize;

  @Column(name = "mime_type", nullable = false)
  private String mimeType;

  @OneToOne(fetch = FetchType.LAZY, optional = false, targetEntity = FileData.class)
  @JoinColumn(name = "data_id", nullable = false, referencedColumnName = "id")
  private FileData data;
}
