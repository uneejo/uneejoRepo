package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.common.dao.IBatisDAO;
import com.unicompany.hr.pm.to.WorkInfoBean;

public class WorkInfoDAOImpl extends IBatisDAO implements WorkInfoDAO {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	/* 사원의 재직정보를 조회하는 메서드 */
	public List<WorkInfoBean> selectWorkInfoAll() {
		return getSqlMapClientTemplate().queryForList("workInfo.selectWorkInfoAll");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateWorkInfo(WorkInfoBean workInfoBean) {
		getSqlMapClientTemplate().update("workInfo.updateWorkInfo",workInfoBean);
	}
	

}
