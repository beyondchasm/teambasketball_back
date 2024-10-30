package site.beyondchasm.teambasketball.common.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import site.beyondchasm.teambasketball.common.domain.RegionDto;

@Mapper
public interface RegionDao {

  List<RegionDto> getRegionList();

}
