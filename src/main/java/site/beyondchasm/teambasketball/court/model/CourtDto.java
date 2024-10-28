package site.beyondchasm.teambasketball.court.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CourtDto {

  private long courtId;
  private String courtType;
  private String courtTypeDetail;
  private String courtName;
  private String regionCode;
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
  private String linkUrl;
  private double rating;
  private Date updatedAt;
}
