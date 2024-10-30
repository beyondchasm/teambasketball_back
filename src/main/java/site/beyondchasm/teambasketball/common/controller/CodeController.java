package site.beyondchasm.teambasketball.common.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.beyondchasm.teambasketball.common.domain.CodeDto;
import site.beyondchasm.teambasketball.common.service.CodeService;

@Log4j
@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  /**
   * 모든 코드 목록을 조회하는 API
   *
   * @return ResponseEntity<List < CodeDto>> 코드 목록
   */
  @GetMapping
  public ResponseEntity<List<CodeDto>> getAllCodes() {
    List<CodeDto> codes = codeService.getCodeList();
    if (codes.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(codes, HttpStatus.OK);
  }

//  /**
//   * 특정 코드 정보를 조회하는 API
//   *
//   * @param id 코드의 고유 ID
//   * @return ResponseEntity<CodeDto> 특정 코드 정보
//   */
//  @GetMapping("/{id}")
//  public ResponseEntity<CodeDto> getCodeById(@PathVariable Long id) {
//    CodeDto code = codeService.getCodeById(id);
//    if (code == null) {
//      throw new CustomException(ErrorCode.CODE_NOT_FOUND);
//    }
//    return new ResponseEntity<>(code, HttpStatus.OK);
//  }
//
//  /**
//   * 새로운 코드를 생성하는 API
//   *
//   * @param CodeDto 생성할 코드 정보
//   * @return ResponseEntity<CodeDto> 생성된 코드 정보
//   */
//  @PostMapping
//  public ResponseEntity<CodeDto> createCode(@RequestBody CodeDto CodeDto) {
//    CodeDto createdCode = codeService.createCode(CodeDto);
//    return new ResponseEntity<>(createdCode, HttpStatus.CREATED);
//  }
//
//  /**
//   * 기존 코드를 업데이트하는 API
//   *
//   * @param id      업데이트할 코드의 고유 ID
//   * @param CodeDto 업데이트할 코드 정보
//   * @return ResponseEntity<CodeDto> 업데이트된 코드 정보
//   */
//  @PutMapping("/{id}")
//  public ResponseEntity<CodeDto> updateCode(@PathVariable Long id, @RequestBody CodeDto CodeDto) {
//    CodeDto updatedCode = codeService.updateCode(id, CodeDto);
//    if (updatedCode == null) {
//      throw new CustomException(ErrorCode.CODE_NOT_FOUND);
//    }
//    return new ResponseEntity<>(updatedCode, HttpStatus.OK);
//  }
//
//  /**
//   * 특정 코드를 삭제하는 API
//   *
//   * @param id 삭제할 코드의 고유 ID
//   * @return ResponseEntity<Void> 삭제 성공 시 상태 코드 반환
//   */
//  @DeleteMapping("/{id}")
//  public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
//    boolean isDeleted = codeService.deleteCode(id);
//    if (!isDeleted) {
//      throw new CustomException(ErrorCode.CODE_NOT_FOUND);
//    }
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
}
