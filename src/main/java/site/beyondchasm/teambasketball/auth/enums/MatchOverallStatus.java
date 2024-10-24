package site.beyondchasm.teambasketball.auth.enums;

public enum MatchOverallStatus {
    RECRUITING("recruiting"),    // 모집 중
    CLOSED("closed"),          // 모집 마감
    ONGOING("ongoing"),       // 매치 진행 중
    COMPLETED("completed");       // 매치 완료

    private final String status;

    MatchOverallStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
