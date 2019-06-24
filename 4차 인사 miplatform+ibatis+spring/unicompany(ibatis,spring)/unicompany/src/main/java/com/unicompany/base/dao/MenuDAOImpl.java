package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.MenuBean;
import com.unicompany.common.dao.IBatisDAO;

public class MenuDAOImpl extends IBatisDAO implements MenuDAO{

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	/* menu목록을 가져오는 메서드 */
	public List<MenuBean> selectMenuList() {
		return getSqlMapClientTemplate().queryForList("Menu.findMenuList");
	}

}
