package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsSigungu")
public class SigunguBean extends BaseBean{
	
	private String gungu;
	private String sido;

	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGungu() {
		return gungu;
	}
	public void setGungu(String gungu) {
		this.gungu = gungu;
	}
}
