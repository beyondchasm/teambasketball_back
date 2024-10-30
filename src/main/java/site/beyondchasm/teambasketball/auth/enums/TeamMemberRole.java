package site.beyondchasm.teambasketball.auth.enums;

public enum TeamMemberRole {
  APPLIED("applied"),
  GUEST("guest"),
  MEMBER("member"),
  MANAGER("manager"),
  ADMIN("admin");

  private final String role;

  TeamMemberRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}