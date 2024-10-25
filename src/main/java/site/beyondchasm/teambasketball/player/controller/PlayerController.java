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
   * 플레이어 목록 조회 API GET /api/players
   */
  @GetMapping
  public ResponseEntity<List<PlayerDto>> list(PlayerFilterCommand playerFilterCommand) {
    List<PlayerDto> playerList = playerService.getPlayerList(playerFilterCommand);
    return ResponseEntity.ok(playerList);
  }

  /**
   * 플레이어 상세 정보 조회 API GET /api/players/{id}
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
