package site.beyondchasm.teambasketball.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import site.beyondchasm.teambasketball.common.domain.RegionDTO;

@Mapper
public interface RegionDao {

	List<RegionDTO> getRegionList();

}
