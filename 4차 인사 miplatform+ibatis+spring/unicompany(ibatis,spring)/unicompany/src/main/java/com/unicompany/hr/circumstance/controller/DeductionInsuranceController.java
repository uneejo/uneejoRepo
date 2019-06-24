package com.unicompany.hr.circumstance.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.circumstance.service.CircumstanceServiceFacade;
import com.unicompany.hr.circumstance.to.DeductionTaxBean;
import com.unicompany.hr.circumstance.to.IncomeTaxBean;

public class DeductionInsuranceController extends AbstractMiplatformMultiActionController{
	/* CircumstanceServiceFacade setting */
	private CircumstanceServiceFacade circumstanceServiceFacade;
	public void setCircumstanceServiceFacade(CircumstanceServiceFacade circumstanceServiceFacade) {
		this.circumstanceServiceFacade = circumstanceServiceFacade;
	}

	/* 보험공제 목록을 가져오는 메서드 */
	public void findDeductionInsuranceList(PlatformData inData, PlatformData outData) throws Exception {
		List<DeductionTaxBean> deductionsTaxList=circumstanceServiceFacade.findDeductionTaxList();
		List<IncomeTaxBean> incomeTaxList=circumstanceServiceFacade.findIncomeTaxList();
		datasetBeanMapper.beansToDataset(outData, deductionsTaxList, DeductionTaxBean.class);
		datasetBeanMapper.beansToDataset(outData, incomeTaxList, IncomeTaxBean.class);
    }
	
	// 보험공제, 소득세를 삭제하는 메서드 
	public void removeDeductionTax(PlatformData inData, PlatformData outData) throws Exception {
	
		DeductionTaxBean deductionTaxBean=datasetBeanMapper.datasetToBean(inData, DeductionTaxBean.class);
		List<IncomeTaxBean> incomeTaxList=datasetBeanMapper.datasetToBeans(inData, IncomeTaxBean.class);
		circumstanceServiceFacade.removeDeductionTax(deductionTaxBean);
		circumstanceServiceFacade.removeIncomeTaxList(incomeTaxList);
		findDeductionInsuranceList(inData,outData);
	}

	// 보험공제, 소득세 관련부분을 일괄적으로 처리하는 메서드 
	public void batchDeductionTax(PlatformData inData, PlatformData outData) throws Exception{
		DeductionTaxBean deductionTaxBean=datasetBeanMapper.datasetToBean(inData, DeductionTaxBean.class);
		List<IncomeTaxBean> incomeTaxList=datasetBeanMapper.datasetToBeans(inData, IncomeTaxBean.class);
		circumstanceServiceFacade.addDeductionTax(deductionTaxBean);
		circumstanceServiceFacade.addIncomeTaxList(incomeTaxList);
		findDeductionInsuranceList(inData,outData);
	}

	
    
}
