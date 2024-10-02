package site.beyondchasm.teambasketball.common.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j;
import site.beyondchasm.teambasketball.common.domain.RegionDTO;
import site.beyondchasm.teambasketball.common.service.RegionService;

@Log4j
@RestController
@RequestMapping("/region")
public class RegionController {

	@Resource(name = "regionService")
	private RegionService regionService;

	// 유저정보 조회 API
	@GetMapping("/list")
	public ResponseEntity<List<RegionDTO>> list() {
		return ResponseEntity.ok(regionService.getRegionList());
	}

}
