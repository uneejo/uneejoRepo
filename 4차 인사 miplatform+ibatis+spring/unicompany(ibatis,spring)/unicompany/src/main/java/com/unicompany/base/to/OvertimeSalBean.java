package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsOvertimeSal")
public class OvertimeSalBean extends BaseBean{

	private String overtimeSalCode;
	private String overtimeSalName;
	private String overtimeSalRate;

	public String getOvertimeSalCode() {
		return overtimeSalCode;
	}
	public void setOvertimeSalCode(String overtimeSalCode) {
		this.overtimeSalCode = overtimeSalCode;
	}
	public String getOvertimeSalName() {
		return overtimeSalName;
	}
	public void setOvertimeSalName(String overtimeSalName) {
		this.overtimeSalName = overtimeSalName;
	}
	public String getOvertimeSalRate() {
		return overtimeSalRate;
	}
	public void setOvertimeSalRate(String overtimeSalRate) {
		this.overtimeSalRate = overtimeSalRate;
	}

}
