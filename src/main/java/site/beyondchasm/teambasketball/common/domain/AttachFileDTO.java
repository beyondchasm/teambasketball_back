package site.beyondchasm.teambasketball.common.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	private String uuid; // uuid
	private String file_name; // 파일
	private String upload_path; // 업로드경
	private String display_path;
	private boolean image; // 이미지 여부
}
