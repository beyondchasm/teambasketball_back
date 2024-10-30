package site.beyondchasm.teambasketball.court.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.court.command.CourtFilterCommand;
import site.beyondchasm.teambasketball.court.model.CourtDto;
import site.beyondchasm.teambasketball.court.model.CourtImageDto;
import site.beyondchasm.teambasketball.court.service.CourtService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courts")
public class CourtController {

  @Autowired
  private CourtService courtService;

  /**
   * 코트 목록 조회 API GET /api/courts
   */
  @GetMapping
  public ResponseEntity<List<CourtDto>> getCourts(CourtFilterCommand courtFilterCommand) {
    List<CourtDto> courts = courtService.getCourtList(courtFilterCommand);
    return ResponseEntity.ok(courts);
  }

  /**
   * 특정 코트 상세 정보 조회 API GET /api/courts/{id}
   */
  @GetMapping("/{id}")
  public ResponseEntity<CourtDto> getCourtDetail(@PathVariable Long id) {
    CourtDto courtDetail = courtService.getCourtDetail(id);
    if (courtDetail == null) {
      throw new CustomException(ErrorCode.NOT_EXIST_USER);
    }
    return ResponseEntity.ok(courtDetail);
  }

  /**
   * 특정 코트의 이미지 목록 조회 API GET /api/courts/{id}/images
   */
  @GetMapping("/{id}/images")
  public ResponseEntity<List<CourtImageDto>> getCourtImages(@PathVariable Long id) {
    List<CourtImageDto> courtImages = courtService.getCourtImages(id);
    if (courtImages == null) {
      throw new CustomException(ErrorCode.BAD_REQUEST);
    }
    return ResponseEntity.ok(courtImages);
  }
}
