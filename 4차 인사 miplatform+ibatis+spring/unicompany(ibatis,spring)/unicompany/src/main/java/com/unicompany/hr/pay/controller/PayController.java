package com.unicompany.hr.pay.controller;

import java.util.ArrayList;
import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.pay.service.PayServiceFacade;
import com.unicompany.hr.pay.to.PayDeductionBean;
import com.unicompany.hr.pay.to.SalaryInputBean;

public class PayController extends AbstractMiplatformMultiActionController{
	/* PayServiceFacade SETTING */
	private PayServiceFacade payServiceFacade;
	public void setPayServiceFacade(PayServiceFacade payServiceFacade) {
		this.payServiceFacade = payServiceFacade;
	}

	/* 급여를 계산하는 메서드 */
	public void payCalculate(PlatformData inData, PlatformData outData) throws Exception {
		String paymentDate = inData.getVariable("paymentDate").getValue().asString();
		
		String standardDate = inData.getVariable("standardDate").getValue().asString();// 추가 
		
		List<SalaryInputBean> salaryInputList=payServiceFacade.payCalculate(paymentDate,standardDate);
		
		List<PayDeductionBean> payDeductionList=new ArrayList<PayDeductionBean>();
		for(SalaryInputBean salaryInputBean:salaryInputList){
			payDeductionList.addAll(salaryInputBean.getPayDeductionList());
		}
		datasetBeanMapper.beansToDataset(outData, salaryInputList, SalaryInputBean.class);
		datasetBeanMapper.beansToDataset(outData, payDeductionList, PayDeductionBean.class);
    }
}