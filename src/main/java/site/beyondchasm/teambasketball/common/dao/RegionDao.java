package site.beyondchasm.teambasketball.common.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.beyondchasm.teambasketball.common.domain.RegionDto;

@Mapper
public interface RegionDao {

  /**
   * 모든 지역 목록을 조회하는 메서드
   *
   * @return List<RegionDto> 지역 목록
   */
  List<RegionDto> getRegionList();

  /**
   * 부모 리전 목록을 조회하는 메서드
   *
   * @return List<RegionDto> 부모 리전 목록
   */
  List<RegionDto> getParentRegionList();

  /**
   * 특정 부모 리전의 자식 리전 목록을 조회하는 메서드
   *
   * @param parentRegionCode 부모 리전 코드
   * @return List<RegionDto> 자식 리전 목록
   */
  List<RegionDto> getChildRegionList(@Param("parentRegionCode") String parentRegionCode);
}
