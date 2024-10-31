package site.beyondchasm.teambasketball.common.service;

import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.common.dao.RegionDao;
import site.beyondchasm.teambasketball.common.domain.RegionDto;

@Service
public class RegionService {

  @Resource(name = "regionDao")
  private RegionDao regionDao;

  /**
   * 모든 부모 지역 목록을 조회하는 메서드
   *
   * @return List<RegionDto> 부모 지역 목록
   */
  public List<RegionDto> getParentRegionList() {
    return regionDao.getParentRegionList();
  }

  /**
   * 특정 부모 지역의 자식 지역 목록을 조회하는 메서드
   *
   * @param parentRegionCode 부모 지역 코드
   * @return List<RegionDto> 자식 지역 목록
   */
  public List<RegionDto> getChildRegionList(String parentRegionCode) {
    return regionDao.getChildRegionList(parentRegionCode);
  }

  /**
   * 모든 지역 목록을 조회하는 메서드
   *
   * @return List<RegionDto> 지역 목록
   */
  public List<RegionDto> getRegionList() {
    return regionDao.getRegionList();
  }
}
