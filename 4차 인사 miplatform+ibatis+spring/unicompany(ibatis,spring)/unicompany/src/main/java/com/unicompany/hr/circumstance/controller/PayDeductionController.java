package com.unicompany.hr.circumstance.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.circumstance.service.CircumstanceServiceFacade;
import com.unicompany.hr.circumstance.to.PayDeductionBean;

public class PayDeductionController extends AbstractMiplatformMultiActionController{
	/* CircumstanceServiceFacade setting */
	private CircumstanceServiceFacade circumstanceServiceFacade;
	public void setCircumstanceServiceFacade(CircumstanceServiceFacade circumstanceServiceFacade) {
		this.circumstanceServiceFacade = circumstanceServiceFacade;
	}

	// 지급/공제항목을 조회하는 메서드 
	public void findPayDeductionList(PlatformData inData, PlatformData outData) throws Exception {
		List<PayDeductionBean> payDeductionList = circumstanceServiceFacade.findPayDeductionList();
		datasetBeanMapper.beansToDataset(outData, payDeductionList, PayDeductionBean.class);
    }
	
	// 지급/공제항목에 관한 일괄처리하는 메서드 
	public void batchPayDeduction(PlatformData inData, PlatformData outData) throws Exception {
		   List<PayDeductionBean> payDeductionList=datasetBeanMapper.datasetToBeans(inData, PayDeductionBean.class);
		   circumstanceServiceFacade.batchPayDeduction(payDeductionList);
		   findPayDeductionList(inData,outData);
	}
	
}
