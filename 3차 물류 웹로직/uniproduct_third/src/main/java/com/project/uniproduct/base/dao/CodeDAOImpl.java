package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.CodeTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class CodeDAOImpl extends IbatisSupportDAO implements CodeDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<CodeTO> selectCodeList() {

		return (ArrayList<CodeTO>)getSqlMapClientTemplate().queryForList("code.selectCodeList");
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertCode(CodeTO bean) {
		
		getSqlMapClientTemplate().queryForList("code.insertCode",bean);

	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void updateCode(CodeTO bean) {

		getSqlMapClientTemplate().queryForList("code.updateCode",bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteCode(CodeTO bean) {

		getSqlMapClientTemplate().queryForList("code.deleteCode",bean);
	}

}
