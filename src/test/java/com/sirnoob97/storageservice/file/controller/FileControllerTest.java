package com.sirnoob97.storageservice.file.controller;

import static com.sirnoob97.storageservice.util.DtoGenerator.randomFileInfoDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.sirnoob97.storageservice.file.dto.FileDto;
import com.sirnoob97.storageservice.file.service.FileService;

@WebMvcTest
class FileControllerTest {

  @MockBean
  private FileService fileService;

  @Autowired
  private MockMvc mockMvc;

  private static final String TEXT_PLAIN = MediaType.TEXT_PLAIN_VALUE;
  private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

  @Test
  void test_Upload_ReturnAFileInfoDtoAsJson() throws Exception {
    given(fileService.persistNewFile(any(MultipartFile.class), anyString())).willReturn(randomFileInfoDto());

    var mockFile = new MockMultipartFile("attachment", "TEST", TEXT_PLAIN, "TEST".getBytes());

    mockMvc.perform(multipart(POST, "/")
        .file(mockFile)
        .accept(JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(JSON))
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.id").isNumber());

    verify(fileService, times(1)).persistNewFile(any(MultipartFile.class), anyString());
  }

  @Test
  void test_List_ReturnAnFileInfoDtoSetAsJsonArray() throws Exception {
    given(fileService.listFiles(anyInt(), anyInt())).willReturn(Set.of(randomFileInfoDto()));

    mockMvc.perform(get("/")
        .param("limit", "10")
        .param("offset", "0")
        .accept(JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(JSON))
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0]").isNotEmpty())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].id").isNumber());

    verify(fileService, times(1)).listFiles(anyInt(), anyInt());
  }

  @Test
  void test_Download_ReturnAFileAsByteArray() throws Exception {
    var data = "TESTING".getBytes();
    var fileDto = FileDto.builder()
        .fileName("TEST")
        .fileSize((long) data.length)
        .mimeType(TEXT_PLAIN)
        .fileData(data)
        .build();

    given(fileService.getFileDto(anyLong())).willReturn(fileDto);

    mockMvc.perform(get("/1")
        .accept(fileDto.getMimeType()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(TEXT_PLAIN))
        .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
        .andExpect(content().bytes(data));

    verify(fileService, times(1)).getFileDto(anyLong());
  }

  @Test
  void test_Delete_Return204AsStatusCode() throws Exception {
    doNothing().when(fileService).deleteFile(anyLong());

    mockMvc.perform(delete("/1"))
    .andExpect(status().isNoContent());

    verify(fileService, times(1)).deleteFile(anyLong());
  }
}
