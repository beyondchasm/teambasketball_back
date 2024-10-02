package site.beyondchasm.teambasketball.common.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.common.service.CodeService;

@Log4j
@RestController
@RequestMapping("/code")
public class CodeController {

	@Resource(name = "codeService")
	private CodeService codeService;

	// 유저정보 조회 API
	@GetMapping("/list")
	public ResponseEntity<List<CodeDTO>> list() {
		return ResponseEntity.ok(codeService.getCodeList());
	}

}
