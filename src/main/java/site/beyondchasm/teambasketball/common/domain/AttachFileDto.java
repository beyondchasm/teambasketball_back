package site.beyondchasm.teambasketball.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 파일 첨부를 위한 데이터 전송 객체.
 * <p>
 * 파일의 UUID, 이름, 업로드 경로, 표시 경로 및 이미지 여부와 같은 파일 메타데이터를 포함합니다.
 */
@Data
@Schema(description = "파일 첨부를 위한 데이터 전송 객체")
public class AttachFileDto {

  /**
   * 파일의 고유 식별자(UUID).
   */
  @Schema(description = "파일의 고유 식별자(UUID)")
  private String uuid;

  /**
   * 파일의 원래 이름.
   */
  @Schema(description = "파일의 원래 이름")
  private String fileName;

  /**
   * 서버에 파일이 업로드된 경로.
   */
  @Schema(description = "서버에 파일이 업로드된 경로")
  private String uploadPath;

  /**
   * 파일을 표시할 때 사용하는 경로.
   */
  @Schema(description = "파일을 표시할 때 사용하는 경로")
  private String displayPath;

  /**
   * 파일이 이미지인지 여부를 나타내는 플래그.
   */
  @Schema(description = "파일이 이미지인지 여부를 나타내는 플래그")
  private boolean image;
}
