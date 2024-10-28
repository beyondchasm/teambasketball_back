package site.beyondchasm.teambasketball.common.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

  private String uuid; // uuid
  private String fileName; // 파일
  private String uploadPath; // 업로드경
  private String displayPath;
  private boolean image; // 이미지 여부
}
