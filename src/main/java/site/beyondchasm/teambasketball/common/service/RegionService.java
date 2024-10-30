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

  public List<RegionDto> getRegionList() {
    return regionDao.getRegionList();
  }

}
