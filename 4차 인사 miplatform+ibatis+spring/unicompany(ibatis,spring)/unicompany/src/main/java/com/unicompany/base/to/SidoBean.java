package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsSido")
public class SidoBean extends BaseBean{

	private String sidoCode;
	private String sidoValue;

	public String getSidoCode() {
		return sidoCode;
	}
	public void setSidoCode(String sidoCode) {
		this.sidoCode = sidoCode;
	}
	public String getSidoValue() {
		return sidoValue;
	}
	public void setSidoValue(String sidoValue) {
		this.sidoValue = sidoValue;
	}
}
