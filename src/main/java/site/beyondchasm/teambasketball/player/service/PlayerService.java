package site.beyondchasm.teambasketball.player.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.mapper.PlayerMapper;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

@RequiredArgsConstructor
@Service
public class PlayerService {
	private final PlayerMapper playerMapper;

	public List<PlayerDto> getPlayerList(PlayerFilterCommand filterCommand) {
		List<PlayerDto> players = playerMapper.getPlayerList(filterCommand);

		return players;
	}

	public PlayerDto getPlayerDetail(Long id) {
		return playerMapper.getPlayerDetail(id);
	}

}
