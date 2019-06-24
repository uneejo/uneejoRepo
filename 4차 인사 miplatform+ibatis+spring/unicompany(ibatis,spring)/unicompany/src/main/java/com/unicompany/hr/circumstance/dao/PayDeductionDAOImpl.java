package com.unicompany.hr.circumstance.dao;

import java.util.List;

import com.unicompany.common.dao.IBatisDAO;
import com.unicompany.hr.circumstance.to.PayDeductionBean;

public class PayDeductionDAOImpl extends IBatisDAO implements PayDeductionDAO {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	/* 지급/공제목록을 조회하는 메서드 */
	public List<PayDeductionBean> selectPayDeductionList() {
		return getSqlMapClientTemplate().queryForList("payDeduction.selectPayDeductionList");
	}

	
	// 지급/공제항목을 추가하는 메서드 
	@SuppressWarnings("deprecation")
	@Override
	public void insertPayDeduction(PayDeductionBean payDeductionBean) {
		getSqlMapClientTemplate().insert("payDeduction.insertPayDeduction",payDeductionBean);
	}

	//지급/공제항목을 수정하는 메서드 
	@SuppressWarnings("deprecation")
	@Override
	public void updatePayDeduction(PayDeductionBean payDeductionBean) {
		getSqlMapClientTemplate().update("payDeduction.updatePayDeduction",payDeductionBean);
	}

	// 지급/공제항목을 삭제하는 메서드 
	@SuppressWarnings("deprecation")
	@Override
	public void deletePayDeduction(PayDeductionBean payDeductionBean) {
		getSqlMapClientTemplate().delete("payDeduction.deletePayDeduction",payDeductionBean);
	}
	
}
