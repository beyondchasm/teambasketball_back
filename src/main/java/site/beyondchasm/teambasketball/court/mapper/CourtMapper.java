package site.beyondchasm.teambasketball.court.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.court.command.CourtFilterCommand;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.court.model.CourtImageDto;

@Mapper
public interface CourtMapper {

  /**
   * 필터 조건에 맞는 코트의 개수를 조회합니다.
   *
   * @param filter 코트 필터 조건
   * @return 조회된 코트의 개수
   */
  long getCourtListCount(@Param("filter") CourtFilterCommand filter);

  /**
   * 필터 조건에 맞는 코트 목록을 조회합니다.
   *
   * @param filter 코트 필터 조건
   * @return 조회된 코트 목록
   */
  List<CourtDto> getCourtList(@Param("filter") CourtFilterCommand filter);

  /**
   * 특정 코트의 상세 정보를 조회합니다.
   *
   * @param id 코트 ID
   * @return 조회된 코트 상세 정보
   */
  CourtDto getCourtDetail(Long id);

  /**
   * 특정 코트에 대한 이미지 목록을 조회합니다.
   *
   * @param id 코트 ID
   * @return 조회된 코트 이미지 목록
   */
  List<CourtImageDto> getCourtImages(Long id);

}
