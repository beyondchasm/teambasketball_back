package site.beyondchasm.teambasketball.player.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.model.PlayerDto;
import site.beyondchasm.teambasketball.player.service.PlayerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/players")
public class PlayerController {

  private final PlayerService playerService;

  /**
   * 플레이어 목록 조회
   *
   * @param playerFilterCommand 플레이어 필터 조건
   * @return 필터링된 플레이어 목록
   */
  @GetMapping
  public ResponseEntity<List<PlayerDto>> list(PlayerFilterCommand playerFilterCommand) {
    List<PlayerDto> playerList = playerService.getPlayerList(playerFilterCommand);
    return ResponseEntity.ok(playerList);
  }

  /**
   * 특정 플레이어의 상세 정보 조회
   *
   * @param id 조회할 플레이어 ID
   * @return 조회된 플레이어의 상세 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<PlayerDto> getPlayerDetail(@PathVariable Long id) {
    PlayerDto playerDetail = playerService.getPlayerDetail(id);
    if (playerDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(playerDetail);
  }
}
