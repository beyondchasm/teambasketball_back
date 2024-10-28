package site.beyondchasm.teambasketball.player.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.mapper.PlayerMapper;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

@RequiredArgsConstructor
@Service
public class PlayerService {

  private final PlayerMapper playerMapper;

  /**
   * 필터 조건에 맞는 플레이어 목록을 조회합니다.
   *
   * @param filterCommand 플레이어 필터 조건
   * @return 조회된 플레이어 목록
   */
  public List<PlayerDto> getPlayerList(PlayerFilterCommand filterCommand) {
    return playerMapper.getPlayerList(filterCommand);
  }

  /**
   * 특정 플레이어의 상세 정보를 조회합니다.
   *
   * @param id 플레이어 ID
   * @return 조회된 플레이어 상세 정보
   */
  public PlayerDto getPlayerDetail(Long id) {
    return playerMapper.getPlayerDetail(id);
  }
}
