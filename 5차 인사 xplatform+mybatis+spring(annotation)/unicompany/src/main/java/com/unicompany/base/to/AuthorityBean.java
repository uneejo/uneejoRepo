package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsAuthority")
public class AuthorityBean extends BaseBean{

	private String authorityCode;
	private String authorityName;

	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
}
