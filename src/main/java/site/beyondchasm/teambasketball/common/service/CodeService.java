package site.beyondchasm.teambasketball.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import site.beyondchasm.teambasketball.common.dao.CodeDao;
import site.beyondchasm.teambasketball.common.domain.CodeDTO;

@Service
public class CodeService {

	@Resource(name = "codeDao")
	private CodeDao codeDao; 

	public List<CodeDTO> getCodeList() {
		return codeDao.getCodeList();
	}

}
