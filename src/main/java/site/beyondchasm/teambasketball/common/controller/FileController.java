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
import site.beyondchasm.teambasketball.common.domain.AttachFileDto;
import site.beyondchasm.teambasketball.common.service.S3Service;

@Log4j
@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  private S3Service s3Service;

  /**
   * 현재 날짜를 기준으로 폴더 경로 문자열 생성
   *
   * @return 폴더 경로 문자열 (yyyy/MM/dd 형식)
   */
  private String getFolder() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String str = sdf.format(date);
    return str.replace("-", File.separator);
  }

  /**
   * 다중 파일 업로드 메서드
   *
   * @param uploadFiles 업로드할 파일 배열
   * @return 업로드 결과 리스트와 상태 코드
   */
  @PostMapping(value = "/files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<AttachFileDto>> uploadMultiFile(
      @RequestParam("files") MultipartFile[] uploadFiles) {
    List<AttachFileDto> list = new ArrayList<>();

    for (MultipartFile multipartFile : uploadFiles) {
      AttachFileDto dto = new AttachFileDto();
      String originalFileName = multipartFile.getOriginalFilename();
      originalFileName = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
      dto.setFileName(originalFileName);

      UUID uuid = UUID.randomUUID();
      String uploadFileName = uuid + "_" + originalFileName;

      try {
        // 임시 파일 생성 및 전송
        File tempFile = File.createTempFile("upload-", uploadFileName);
        multipartFile.transferTo(tempFile);

        // S3에 업로드
        String s3Url = s3Service.uploadFile(uploadFileName, tempFile);

        if (s3Url != null) {
          dto.setUuid(uuid.toString());
          dto.setUploadPath(s3Url);
          list.add(dto);
        }

        tempFile.delete(); // 임시 파일 삭제
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 단일 파일 업로드 메서드
   *
   * @param uploadFile 업로드할 파일
   * @return 업로드된 파일 정보와 상태 코드
   */
  @PostMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<AttachFileDto> uploadSingleFile(
      @RequestParam("file") MultipartFile uploadFile) {
    AttachFileDto dto = new AttachFileDto();

    String originalFileName = uploadFile.getOriginalFilename();
    originalFileName = originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
    dto.setFileName(originalFileName);

    UUID uuid = UUID.randomUUID();
    String uploadFileName = uuid + "_" + originalFileName;

    try {
      // 임시 파일 생성 및 전송
      File tempFile = File.createTempFile("upload-", uploadFileName);
      uploadFile.transferTo(tempFile);

      // S3에 업로드
      String s3Url = s3Service.uploadFile(uploadFileName, tempFile);

      if (s3Url != null) {
        dto.setUuid(uuid.toString());
        dto.setUploadPath(s3Url);
      }

      tempFile.delete(); // 임시 파일 삭제
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * 파일 조회 메서드 - S3에서 파일을 다운로드하여 이미지 데이터 반환
   *
   * @param fileName 파일 이름
   * @return 이미지 데이터와 상태 코드
   */
  @GetMapping("/files/{fileName}")
  public ResponseEntity<byte[]> getFileImage(@PathVariable("fileName") String fileName) {
    byte[] imageData = s3Service.downloadFile(fileName);

    if (imageData != null) {
      HttpHeaders header = new HttpHeaders();
      header.setContentType(MediaType.IMAGE_JPEG); // 이미지 형식에 맞게 설정 가능
      return new ResponseEntity<>(imageData, header, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
