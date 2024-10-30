package site.beyondchasm.teambasketball.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 코드 정보를 담는 데이터 전송 객체.
 * <p>
 * 코드의 유형, ID, 이름을 포함합니다.
 */
@Data
@Schema(description = "코드 정보를 담는 데이터 전송 객체")
public class CodeDto {

  /**
   * 코드 유형.
   */
  @Schema(description = "코드 유형")
  private String codeType;

  /**
   * 코드의 고유 ID.
   */
  @Schema(description = "코드의 고유 ID")
  private String codeId;

  /**
   * 코드 이름.
   */
  @Schema(description = "코드 이름")
  private String codeName;
}
