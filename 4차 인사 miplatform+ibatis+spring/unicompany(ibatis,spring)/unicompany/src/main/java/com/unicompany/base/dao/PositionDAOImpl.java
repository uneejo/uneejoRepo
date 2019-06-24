package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.PositionBean;
import com.unicompany.common.dao.IBatisDAO;

public class PositionDAOImpl extends IBatisDAO implements PositionDAO {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	/* 직급목록을 가져오는 메서드 */
	public List<PositionBean> selectPositionList() {
		return getSqlMapClientTemplate().queryForList("position.selectPositionList");
	}
	
	@SuppressWarnings("deprecation")
	public PositionBean selectPosition(String positionCode) {
		
		return (PositionBean)getSqlMapClientTemplate().queryForObject("position.selectPosition",positionCode);
	}
}
