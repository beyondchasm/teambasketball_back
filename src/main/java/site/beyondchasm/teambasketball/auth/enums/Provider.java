package site.beyondchasm.teambasketball.auth.enums;

public enum Provider {
  KAKAO("kakao"), GOOGLE("google"), APPLE("apple");

  private final String provider;

  Provider(String provider) {
    this.provider = provider;
  }

  public String getProvider() {
    return provider;
  }
}