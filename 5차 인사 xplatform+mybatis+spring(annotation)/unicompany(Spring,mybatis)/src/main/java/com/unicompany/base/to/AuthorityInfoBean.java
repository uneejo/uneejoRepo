package com.unicompany.base.to;

import java.util.List;

import com.unicompany.common.to.BaseBean;

public class AuthorityInfoBean extends BaseBean{

	private List<AuthorityBean> authorityList;
	private List<MenuAuthorityBean> menuAuthorityList;

	public List<AuthorityBean> getAuthorityList() {
		return authorityList;
	}
	public void setAuthorityList(List<AuthorityBean> authorityList) {
		this.authorityList = authorityList;
	}
	public List<MenuAuthorityBean> getMenuAuthorityList() {
		return menuAuthorityList;
	}
	public void setMenuAuthorityList(List<MenuAuthorityBean> menuAuthorityList) {
		this.menuAuthorityList = menuAuthorityList;
	}
}
