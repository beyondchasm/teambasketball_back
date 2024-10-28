package site.beyondchasm.teambasketball.court.command;

import lombok.Data;

@Data
public class CourtCommand {

  private long courtId;
  private String courtType;
  private String courtTypeDetail;
  private String courtName;
  private String cityCode;
  private String districtCode;
  private String postalCode;
  private String roadAddress;
  private String addressCode;
  private String latitude;
  private String longitude;
  private String phoneNumber;
  private String parkingAvailable;
  private String facilityStatus;
  private String accessibilityInfo;
  private String openingHours;
  private String courtDescription;
  private double rating;
}
