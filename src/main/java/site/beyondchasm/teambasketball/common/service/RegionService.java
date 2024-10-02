package site.beyondchasm.teambasketball.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import site.beyondchasm.teambasketball.common.dao.RegionDao;
import site.beyondchasm.teambasketball.common.domain.RegionDTO;

@Service
public class RegionService {

	@Resource(name = "regionDao")
	private RegionDao regionDao;

	public List<RegionDTO> getRegionList() {
		return regionDao.getRegionList();
	}

}
