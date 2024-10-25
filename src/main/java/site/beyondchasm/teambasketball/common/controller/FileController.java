package site.beyondchasm.teambasketball.common.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import site.beyondchasm.teambasketball.common.domain.AttachFileDTO;
import site.beyondchasm.teambasketball.common.service.S3Service;

@Log4j
@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  private S3Service s3Service;

  private String getFolder() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String str = sdf.format(date);
    return str.replace("-", File.separator);
  }

  // 다중 파일 업로드
  @PostMapping(value = "/files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<AttachFileDTO>> uploadMultiFile(
      @RequestParam("files") MultipartFile[] uploadFiles) {
    List<AttachFileDTO> list = new ArrayList<>();

    for (MultipartFile multipartFile : uploadFiles) {
      AttachFileDTO dto = new AttachFileDTO();
      String originalFileName = multipartFile.getOriginalFilename();
      originalFileName = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
      dto.setFile_name(originalFileName);

      UUID uuid = UUID.randomUUID();
      String uploadFileName = uuid + "_" + originalFileName;

      try {
        File tempFile = File.createTempFile("upload-", uploadFileName);
        multipartFile.transferTo(tempFile);

        String s3Url = s3Service.uploadFile(uploadFileName, tempFile);

        if (s3Url != null) {
          dto.setUuid(uuid.toString());
          dto.setUpload_path(s3Url);
          list.add(dto);
        }

        tempFile.delete();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  // 단일 파일 업로드
  @PostMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<AttachFileDTO> uploadSingleFile(
      @RequestParam("file") MultipartFile uploadFile) {
    AttachFileDTO dto = new AttachFileDTO();

    String originalFileName = uploadFile.getOriginalFilename();
    originalFileName = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
    dto.setFile_name(originalFileName);

    UUID uuid = UUID.randomUUID();
    String uploadFileName = uuid + "_" + originalFileName;

    try {
      File tempFile = File.createTempFile("upload-", uploadFileName);
      uploadFile.transferTo(tempFile);

      String s3Url = s3Service.uploadFile(uploadFileName, tempFile);

      if (s3Url != null) {
        dto.setUuid(uuid.toString());
        dto.setUpload_path(s3Url);
      }

      tempFile.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  // 파일 조회
  @GetMapping("/files/{fileName}")
  public ResponseEntity<byte[]> getFileImage(@PathVariable("fileName") String fileName) {
    byte[] imageData = s3Service.downloadFile(fileName);

    if (imageData != null) {
      HttpHeaders header = new HttpHeaders();
      header.setContentType(MediaType.IMAGE_JPEG); // 이미지 유형에 맞게 변경 가능
      return new ResponseEntity<>(imageData, header, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
