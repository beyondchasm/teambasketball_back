package site.beyondchasm.teambasketball.court.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.beyondchasm.teambasketball.court.command.CourtFilterCommand;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.court.model.CourtImageDto;
import site.beyondchasm.teambasketball.court.service.CourtService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/court")
public class CourtController {
    private final CourtService courtService;

    // 유저정보 조회 API
    @PostMapping("/list")
    public List<CourtDto> list(@RequestBody CourtFilterCommand courtFilterCommand) {
        return courtService.getCourtList(courtFilterCommand);
    }

    // 플레이어 상세 정보 조회 API
    @GetMapping("/detail/{id}")
    public ResponseEntity<CourtDto> getCourtDetail(@PathVariable Long id) {
        CourtDto courtDetail = courtService.getCourtDetail(id);
        if (courtDetail == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        return ResponseEntity.ok(courtDetail);
    }

    // 플레이어 상세 정보 조회 API
    @GetMapping("/images/{id}")
    public ResponseEntity<List<CourtImageDto>> getCourtImages(@PathVariable Long id) {
        List<CourtImageDto> courtImages = courtService.getCourtImages(id);
        if (courtImages == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return ResponseEntity.ok(courtImages);
    }
}
