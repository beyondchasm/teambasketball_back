package site.beyondchasm.teambasketball.common.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.common.domain.RegionDto;
import site.beyondchasm.teambasketball.common.service.RegionService;

@Log4j
@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;

  /**
   * 모든 부모 지역 목록을 조회하는 API
   *
   * @return ResponseEntity<List < RegionDto>> 부모 지역 목록
   */
  @GetMapping("/parents")
  public ResponseEntity<List<RegionDto>> getParentRegions() {
    List<RegionDto> parentRegions = regionService.getParentRegionList();
    return ResponseEntity.ok(parentRegions);
  }

  /**
   * 선택된 부모 지역의 자식 지역 목록을 조회하는 API
   *
   * @param parentRegionCode 부모 지역 코드
   * @return ResponseEntity<List < RegionDto>> 자식 지역 목록
   */
  @GetMapping("/{parentRegionCode}/children")
  public ResponseEntity<List<RegionDto>> getChildRegions(@PathVariable String parentRegionCode) {
    List<RegionDto> childRegions = regionService.getChildRegionList(parentRegionCode);
    return ResponseEntity.ok(childRegions);
  }
}
