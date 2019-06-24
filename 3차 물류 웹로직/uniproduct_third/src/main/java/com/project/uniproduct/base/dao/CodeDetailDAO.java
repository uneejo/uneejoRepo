package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.CodeDetailTO;

public interface CodeDetailDAO {

	ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode);

	void insertDetailCode(CodeDetailTO TO);

	void updateDetailCode(CodeDetailTO TO);

	public void deleteDetailCode(CodeDetailTO TO);
	
	public void changeCodeUseCheck(String divisionCodeNo, String detailCode, String codeUseCheck);
	
	
}
