package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.MenuBean;

public interface MenuDAO {
	List<MenuBean> selectMenuList();
}
