package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.dao.MenuDAO;
import com.unicompany.base.to.MenuBean;

public class MenuAppServiceImpl implements MenuAppService {

	private MenuDAO menuDAO;
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@Override
	/* menu 목록을 가져오는 메서드 */
	public List<MenuBean> findMenuList() {
		return menuDAO.selectMenuList();
	}
}
