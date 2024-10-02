package site.beyondchasm.teambasketball.player.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;
import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.model.PlayerDto;
import site.beyondchasm.teambasketball.player.service.PlayerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
	private final PlayerService playerService;

	// 유저정보 조회 API
	@PostMapping("/list")
	public List<PlayerDto> list(@RequestBody PlayerFilterCommand playerFilterCommand) {
		return playerService.getPlayerList(playerFilterCommand);
	}

	// 플레이어 상세 정보 조회 API
	@GetMapping("/detail/{id}")
	public ResponseEntity<PlayerDto> getPlayerDetail(@PathVariable Long id) {
		PlayerDto playerDetail = playerService.getPlayerDetail(id);
		if (playerDetail == null) {
			throw new CustomException(ErrorCode.NOT_EXIST_USER);
		}
		return ResponseEntity.ok(playerDetail);
	}

}
