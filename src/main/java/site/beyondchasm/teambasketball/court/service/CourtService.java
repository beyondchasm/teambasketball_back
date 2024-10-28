package site.beyondchasm.teambasketball.court.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.court.command.CourtFilterCommand;
import site.beyondchasm.teambasketball.court.mapper.CourtMapper;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.court.model.CourtImageDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourtService {

  private final CourtMapper courtMapper;

  /**
   * 필터 조건에 맞는 코트 목록을 조회합니다.
   *
   * @param filterCommand 코트 필터 조건
   * @return 조회된 코트 목록
   */
  public List<CourtDto> getCourtList(CourtFilterCommand filterCommand) {
    return courtMapper.getCourtList(filterCommand);
  }

  /**
   * 특정 코트의 상세 정보를 조회합니다.
   *
   * @param id 코트 ID
   * @return 조회된 코트 상세 정보
   */
  public CourtDto getCourtDetail(Long id) {
    return courtMapper.getCourtDetail(id);
  }

  /**
   * 특정 코트의 이미지 목록을 조회합니다.
   *
   * @param id 코트 ID
   * @return 조회된 코트 이미지 목록
   */
  public List<CourtImageDto> getCourtImages(Long id) {
    return courtMapper.getCourtImages(id);
  }
}
