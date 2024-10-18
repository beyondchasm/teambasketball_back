package site.beyondchasm.teambasketball.player.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import site.beyondchasm.teambasketball.player.command.PlayerFilterCommand;
import site.beyondchasm.teambasketball.player.model.PlayerDto;

@Mapper
public interface PlayerMapper {

    List<PlayerDto> getPlayerList(@Param("filter") PlayerFilterCommand filter);

    PlayerDto getPlayerDetail(Long user_id);

}