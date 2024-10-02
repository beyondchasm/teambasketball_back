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

    public List<CourtDto> getCourtList(CourtFilterCommand filterCommand) {
        List<CourtDto> courts = courtMapper.getCourtList(filterCommand);


        return courts;
    }

    public CourtDto getCourtDetail(Long id) {
        return courtMapper.getCourtDetail(id);
    }

    public List<CourtImageDto> getCourtImages(Long id) {
        return courtMapper.getCourtImages(id);
    }

}
