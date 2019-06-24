package com.unicompany.base.to;

import com.unicompany.common.annotation.Dataset;
import com.unicompany.common.to.BaseBean;

@Dataset(name="dsEtcSal")
public class EtcSalBean extends BaseBean{

	private String etcSalCode;
	private String etcSalName;
	private String etcSalPrice;


	public String getEtcSalCode() {
		return etcSalCode;
	}
	public void setEtcSalCode(String etcSalCode) {
		this.etcSalCode = etcSalCode;
	}
	public String getEtcSalName() {
		return etcSalName;
	}
	public void setEtcSalName(String etcSalName) {
		this.etcSalName = etcSalName;
	}
	public String getEtcSalPrice() {
		return etcSalPrice;
	}
	public void setEtcSalPrice(String etcSalPrice) {
		this.etcSalPrice = etcSalPrice;
	}

}
