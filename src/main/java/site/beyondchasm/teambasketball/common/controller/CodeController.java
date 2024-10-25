package site.beyondchasm.teambasketball.common.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;
import site.beyondchasm.teambasketball.common.service.CodeService;
import site.beyondchasm.teambasketball.exception.CustomException;
import site.beyondchasm.teambasketball.exception.ErrorCode;

@Log4j
@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  /**
   * 모든 코드 목록을 조회하는 API
   *
   * @return ResponseEntity<List < CodeDTO>> 코드 목록
   */
  @GetMapping
  public ResponseEntity<List<CodeDTO>> getAllCodes() {
    List<CodeDTO> codes = codeService.getCodeList();
    if (codes.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(codes, HttpStatus.OK);
  }

//  /**
//   * 특정 코드 정보를 조회하는 API
//   *
//   * @param id 코드의 고유 ID
//   * @return ResponseEntity<CodeDTO> 특정 코드 정보
//   */
//  @GetMapping("/{id}")
//  public ResponseEntity<CodeDTO> getCodeById(@PathVariable Long id) {
//    CodeDTO code = codeService.getCodeById(id);
//    if (code == null) {
//      throw new CustomException(ErrorCode.CODE_NOT_FOUND);
//    }
//    return new ResponseEntity<>(code, HttpStatus.OK);
//  }
//
//  /**
//   * 새로운 코드를 생성하는 API
//   *
//   * @param codeDTO 생성할 코드 정보
//   * @return ResponseEntity<CodeDTO> 생성된 코드 정보
//   */
//  @PostMapping
//  public ResponseEntity<CodeDTO> createCode(@RequestBody CodeDTO codeDTO) {
//    CodeDTO createdCode = codeService.createCode(codeDTO);
//    return new ResponseEntity<>(createdCode, HttpStatus.CREATED);
//  }
//
//  /**
//   * 기존 코드를 업데이트하는 API
//   *
//   * @param id      업데이트할 코드의 고유 ID
//   * @param codeDTO 업데이트할 코드 정보
//   * @return ResponseEntity<CodeDTO> 업데이트된 코드 정보
//   */
//  @PutMapping("/{id}")
//  public ResponseEntity<CodeDTO> updateCode(@PathVariable Long id, @RequestBody CodeDTO codeDTO) {
//    CodeDTO updatedCode = codeService.updateCode(id, codeDTO);
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
