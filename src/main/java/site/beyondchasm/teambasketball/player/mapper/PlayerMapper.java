package site.beyondchasm.teambasketball.player.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

@Mapper
public interface PlayerMapper {

  /**
   * 필터 조건에 맞는 플레이어 목록을 조회합니다.
   *
   * @param filter 플레이어 필터 조건
   * @return 조회된 플레이어 목록
   */
  List<PlayerDto> getPlayerList(@Param("filter") PlayerFilterCommand filter);

  /**
   * 특정 사용자의 플레이어 상세 정보를 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 조회된 플레이어 상세 정보
   */
  PlayerDto getPlayerDetail(Long userId);

}
