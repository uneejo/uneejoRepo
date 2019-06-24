package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.PositionBean;

public interface PositionDAO {
	public List<PositionBean> selectPositionList();
	
	public PositionBean selectPosition(String positionCode);
}
