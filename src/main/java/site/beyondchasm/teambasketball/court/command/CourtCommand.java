package site.beyondchasm.teambasketball.court.command;

import lombok.Data;

@Data
public class CourtCommand {
    private long court_id;
    private String court_type;
    private String court_type_detail;
    private String court_name;
    private String city_code;
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
    private double rating;
}
