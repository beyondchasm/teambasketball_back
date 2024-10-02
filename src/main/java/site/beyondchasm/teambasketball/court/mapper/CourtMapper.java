package site.beyondchasm.teambasketball.court.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.court.command.CourtFilterCommand;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.court.model.CourtImageDto;

@Mapper
public interface CourtMapper {

	long getCourtListCount(@Param("filter") CourtFilterCommand filter);

	List<CourtDto> getCourtList(@Param("filter") CourtFilterCommand filter);

	CourtDto getCourtDetail(Long id);

	List<CourtImageDto> getCourtImages(Long id);

}