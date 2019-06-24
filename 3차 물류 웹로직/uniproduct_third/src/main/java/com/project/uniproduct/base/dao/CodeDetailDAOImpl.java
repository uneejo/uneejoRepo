package com.project.uniproduct.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.base.to.CodeDetailTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;


public class CodeDetailDAOImpl extends IbatisSupportDAO implements CodeDetailDAO {


	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode) {

	return(ArrayList<CodeDetailTO>)getSqlMapClientTemplate().queryForList("codeDetail.selectDetailCodeList",divisionCode);

		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertDetailCode(CodeDetailTO bean) {

       getSqlMapClientTemplate().queryForList("codeDetail.insertDetailCode",bean);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDetailCode(CodeDetailTO bean) {
      
		getSqlMapClientTemplate().queryForList("codeDetail.updateDetailCode",bean);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteDetailCode(CodeDetailTO bean) {
    
		getSqlMapClientTemplate().queryForList("codeDetail.deleteDetailCode",bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeCodeUseCheck(String divisionCodeNo, String detailCode, String codeUseCheck) {
      
		HashMap<String,String> map= new HashMap<>();
		map.put("divisionCodeNo", divisionCodeNo);
		map.put("detailCode", detailCode);
		map.put("codeUseCheck", codeUseCheck);
		
		getSqlMapClientTemplate().queryForList("codeDetail.changeCodeUseCheck",map);
	}

}
