package site.beyondchasm.teambasketball.common.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.beyondchasm.teambasketball.common.dao.CodeDao;
import site.beyondchasm.teambasketball.common.domain.CodeDto;

@Service
@RequiredArgsConstructor
public class CodeService {

  private final CodeDao codeDao;

  /**
   * 모든 코드 목록을 조회합니다.
   *
   * @return List<CodeDTO> 코드 목록
   */
  public List<CodeDto> getCodeList() {
    return codeDao.getCodeList();
  }

}
