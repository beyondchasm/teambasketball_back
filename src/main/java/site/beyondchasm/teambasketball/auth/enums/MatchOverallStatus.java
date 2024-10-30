package site.beyondchasm.teambasketball.auth.enums;

/**
 * 매치의 전체 상태를 나타내는 열거형.
 */
public enum MatchOverallStatus {
  /**
   * 모집 중
   */
  RECRUITING("recruiting"),
  /**
   * 모집 마감
   */
  CLOSED("closed"),
  /**
   * 매치 진행 중
   */
  ONGOING("ongoing"),
  /**
   * 매치 완료
   */
  COMPLETED("completed");

  private final String status;

  /**
   * 매치 상태를 설정하는 생성자.
   *
   * @param status 상태를 나타내는 문자열
   */
  MatchOverallStatus(String status) {
    this.status = status;
  }

  /**
   * 상태를 반환하는 메서드.
   *
   * @return 상태 문자열
   */
  public String getStatus() {
    return status;
  }
}
