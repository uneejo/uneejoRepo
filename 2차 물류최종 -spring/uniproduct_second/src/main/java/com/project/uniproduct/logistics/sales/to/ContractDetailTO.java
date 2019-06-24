package com.project.uniproduct.logistics.sales.to;

import com.project.uniproduct.base.to.BaseTO;

public class ContractDetailTO extends BaseTO {
	private String unitOfContract;
	private int contractAmount;
	private String contractDetailNo;
	private int sumPriceOfContract;
	private String mpsApplyStatus;
	private String description;
	private String itemCode;
	private int unitPriceOfContract;
	private String contractNo;
	private String itemName;
	private String dueDateOfContract;
	private String deliveryStatus;

	public String getUnitOfContract() {
		return unitOfContract;
	}

	public void setUnitOfContract(String unitOfContract) {
		this.unitOfContract = unitOfContract;
	}

	public int getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(int contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getContractDetailNo() {
		return contractDetailNo;
	}

	public void setContractDetailNo(String contractDetailNo) {
		this.contractDetailNo = contractDetailNo;
	}

	public int getSumPriceOfContract() {
		return sumPriceOfContract;
	}

	public void setSumPriceOfContract(int sumPriceOfContract) {
		this.sumPriceOfContract = sumPriceOfContract;
	}

	public String getMpsApplyStatus() {
		return mpsApplyStatus;
	}

	public void setMpsApplyStatus(String mpsApplyStatus) {
		this.mpsApplyStatus = mpsApplyStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getUnitPriceOfContract() {
		return unitPriceOfContract;
	}

	public void setUnitPriceOfContract(int unitPriceOfContract) {
		this.unitPriceOfContract = unitPriceOfContract;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDueDateOfContract() {
		return dueDateOfContract;
	}

	public void setDueDateOfContract(String dueDateOfContract) {
		this.dueDateOfContract = dueDateOfContract;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
}