package com.project.uniproduct.authorityManager.dao;

import java.util.HashMap;
import java.util.List;

import com.project.uniproduct.authorityManager.to.UserMenuTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;


public class UserMenuDAOImpl extends IbatisSupportDAO implements UserMenuDAO {
	
	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode,
			String positionCode) {

		HashMap<String, String> map = new HashMap<>();

		map.put("workplaceCode",workplaceCode);
		map.put("deptCode",deptCode);
		map.put("positionCode",positionCode);
			
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<UserMenuTO> menuList=getSqlMapClientTemplate().queryForList("usermenu.selectUserMenuCodeList",map);
				
		HashMap<String, UserMenuTO> userMenuTOMap=new HashMap<>();
		
		for(UserMenuTO bean:menuList) {
			
			userMenuTOMap.put(bean.getNo() + "",bean);
		}
		
      	return userMenuTOMap;

	}
}