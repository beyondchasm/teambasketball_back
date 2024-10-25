package site.beyondchasm.teambasketball.common.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.common.domain.RegionDTO;
import site.beyondchasm.teambasketball.common.service.RegionService;

@Log4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;

  /**
   * 모든 지역 목록을 조회하는 API
   *
   * @return ResponseEntity<List < RegionDTO>> 지역 목록
   */
  @GetMapping("/regions")
  public ResponseEntity<List<RegionDTO>> getAllRegions() {
    List<RegionDTO> regions = regionService.getRegionList();
    return ResponseEntity.ok(regions);
  }
}
