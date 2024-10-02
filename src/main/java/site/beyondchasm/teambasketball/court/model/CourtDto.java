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
	private long court_id;
	private String court_type;
	private String court_type_detail;
	private String court_name;
	private String region_code;
	private String district_code;
	private String postal_code;
	private String road_address;
	private String address_code;
	private String latitude;
	private String longitude;
	private String phone_number;
	private String parking_available;
	private String facility_status;
	private String accessibility_info;
	private String opening_hours;
	private String court_description;
	private String link_url;
	private double rating;
	private Date updated_at;
}