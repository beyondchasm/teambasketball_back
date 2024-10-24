package site.beyondchasm.teambasketball.auth.enums;

public enum MatchMemberStatus {
  APPLIED("applied"),    // 신청 상태
  APPROVED("approved"),  // 승인 상태
  REJECTED("rejected");  // 반려 상태

  private final String status;

  MatchMemberStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
