package com.unicompany.base.applicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unicompany.base.dao.PositionDAO;
import com.unicompany.base.to.PositionBean;

@Component
public class PositionAppServiceImpl implements PositionAppService {

	@Autowired
	private PositionDAO positionDAO;
	
	/* 직급목록을 가져오는 메서드 */
	@Override
	public List<PositionBean> findPositionList() {
		return positionDAO.selectPositionList();
	}
	
	public PositionBean findPosition(String positionCode) {
		
		return positionDAO.selectPosition(positionCode);
	}

}
