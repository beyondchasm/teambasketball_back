package site.beyondchasm.teambasketball.common.controller;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import site.beyondchasm.teambasketball.common.domain.AttachFileDTO;

@Log4j
@Controller
@RequestMapping("/common/*")
public class CommonController {

	// 년/월/일 폴더 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	// 이미지 타입 확인 메서드
	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@PostMapping(value = "/uploadMultiFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadMultiFile(MultipartFile[] uploadFile) {

		// 파일 여러개->배열형태
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "/Users/createogu/derbymatch/upload/";

		// getFolder 메서드로 폴더 패턴 생성
		String uploadFolderPath = getFolder();
		// 폴더 만들기(부모,자식)
		File uploadPath = new File(uploadFolder, uploadFolderPath);

		// 업로드 폴더가 존재하지 않는다면 만들어준다.
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO dto = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();
			// ie의 경우 전체 파일 경로가 전송됨으로 \\까지 제외한 문자열이 실제 파일이름으로 저장
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			dto.setFile_name(uploadFileName);

			// 중복방지 uuid 적용==========================================================
			UUID uuid = UUID.randomUUID();
			// [uuid랜덤값_파일 본래이름]
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			try {
				// 브라우저에서 업로드된 파일을 서버내에 생성(부모,자식)
				File saveFile = new File(uploadPath, uploadFileName);
				// 브라우저에서 업로드받은 파일을 서버의 파일 도착지인 saveFile로 transferTo 을 이용해 전송
				multipartFile.transferTo(saveFile);

				dto.setUuid(uuid.toString());
				dto.setUpload_path(uploadFolderPath);
				dto.setDisplay_path(saveFile.getPath());
				list.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // End for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping(value = "/uploadSingleFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<AttachFileDTO> uploadSingleFile(MultipartFile uploadFile) {

		// 파일 여러개->배열형태
		String uploadFolder = "/Users/createogu/teambasketball/upload/";

		// getFolder 메서드로 폴더 패턴 생성
		String uploadFolderPath = getFolder();
		// 폴더 만들기(부모,자식)
		File uploadPath = new File(uploadFolder, uploadFolderPath);

		// 업로드 폴더가 존재하지 않는다면 만들어준다.
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		AttachFileDTO dto = new AttachFileDTO();

		String uploadFileName = uploadFile.getOriginalFilename();
		// ie의 경우 전체 파일 경로가 전송됨으로 \\까지 제외한 문자열이 실제 파일이름으로 저장
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
		dto.setFile_name(uploadFileName);

		// 중복방지 uuid 적용==========================================================
		UUID uuid = UUID.randomUUID();
		// [uuid랜덤값_파일 본래이름]
		uploadFileName = uuid.toString() + "_" + uploadFileName;

		try {
			// 브라우저에서 업로드된 파일을 서버내에 생성(부모,자식)
			File saveFile = new File(uploadPath, uploadFileName);
			// 브라우저에서 업로드받은 파일을 서버의 파일 도착지인 saveFile로 transferTo 을 이용해 전송
			uploadFile.transferTo(saveFile);

			dto.setUuid(uuid.toString());
			dto.setUpload_path(uploadFolderPath);
			dto.setDisplay_path(uploadFolderPath + "/" + uploadFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// 문자열로 파일경로를 fileName으로 받고 byte[]로 이미지 데이터를 전송(http 헤더 메시지에 포함해서)
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFileImage(String fileName) {
		log.info("썸네일 보여줘야 되는 파일 이름: " + fileName);
		File file = new File(fileName);

		log.info(file);
		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();

			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
