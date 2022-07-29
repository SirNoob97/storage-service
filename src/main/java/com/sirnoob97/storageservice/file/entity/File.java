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
import com.sirnoob97.storageservice.file.dto.FileDto;
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
@NamedNativeQueries(value = @NamedNativeQuery(name = "File.findFileDto", query = """
    SELECT  f.file_name, f.file_size, f.mime_type, fd.file_data
      FROM file AS f JOIN file_data AS fd
      ON f.id = fd.id
    """, resultSetMapping = "File.fileToFileDto"))
@SqlResultSetMappings(value = @SqlResultSetMapping(name = "File.fileToFileDto",
    classes = @ConstructorResult(targetClass = FileDto.class,
        columns = {@ColumnResult(name = "file_name", type = String.class),
            @ColumnResult(name = "file_size", type = Long.class),
            @ColumnResult(name = "mime_type", type = String.class),
            @ColumnResult(name = "file_data", type = Byte[].class)})))
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
