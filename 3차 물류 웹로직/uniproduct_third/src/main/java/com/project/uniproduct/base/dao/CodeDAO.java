package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.CodeTO;

public interface CodeDAO {

	public ArrayList<CodeTO> selectCodeList();

	public void insertCode(CodeTO codeTO);

	public void updateCode(CodeTO codeTO);

	public void deleteCode(CodeTO codeTO);

}
