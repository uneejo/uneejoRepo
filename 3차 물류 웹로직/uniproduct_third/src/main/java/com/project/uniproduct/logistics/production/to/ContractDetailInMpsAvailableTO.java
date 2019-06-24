package com.project.uniproduct.logistics.production.to;

import com.project.uniproduct.base.to.BaseTO;

public class ContractDetailInMpsAvailableTO extends BaseTO {
	private String contractDetailNo;
	private String contractType;
	private String planClassification;
	private String contractDate;
	private String mpsPlanDate;
	private String scheduledEndDate;
	private String deliveryDate;
	private String customerCode;
	private String customerName;
	private String contractRequester;
	private String itemCode;
	private String itemName;
	private String unitOfContract;
	private int contractAmount;
	private int deliveryAmount;
	private int planAmount;
	private String dueDateOfContract;
	private String description;

	public String getContractDetailNo() {
		return contractDetailNo;
	}

	public void setContractDetailNo(String contractDetailNo) {
		this.contractDetailNo = contractDetailNo;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getPlanClassification() {
		return planClassification;
	}

	public void setPlanClassification(String planClassification) {
		this.planClassification = planClassification;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getMpsPlanDate() {
		return mpsPlanDate;
	}

	public void setMpsPlanDate(String mpsPlanDate) {
		this.mpsPlanDate = mpsPlanDate;
	}

	public String getScheduledEndDate() {
		return scheduledEndDate;
	}

	public void setScheduledEndDate(String scheduledEndDate) {
		this.scheduledEndDate = scheduledEndDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractRequester() {
		return contractRequester;
	}

	public void setContractRequester(String contractRequester) {
		this.contractRequester = contractRequester;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

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

	public int getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(int deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	public int getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(int planAmount) {
		this.planAmount = planAmount;
	}

	public String getDueDateOfContract() {
		return dueDateOfContract;
	}

	public void setDueDateOfContract(String dueDateOfContract) {
		this.dueDateOfContract = dueDateOfContract;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}