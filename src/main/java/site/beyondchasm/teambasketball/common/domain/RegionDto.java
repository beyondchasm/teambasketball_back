package site.beyondchasm.teambasketball.common.domain;

import lombok.Data;

@Data
public class RegionDto {

  private String regionCode;
  private String regionName;
  private String parentRegionCode;
}
