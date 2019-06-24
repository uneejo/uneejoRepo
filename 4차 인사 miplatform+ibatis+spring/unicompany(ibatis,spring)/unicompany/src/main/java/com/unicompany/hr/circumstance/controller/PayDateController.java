package com.unicompany.hr.circumstance.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.circumstance.service.CircumstanceServiceFacade;
import com.unicompany.hr.circumstance.to.SalPaymentDateBean;

public class PayDateController extends AbstractMiplatformMultiActionController{
	/* CircumstanceServiceFacade setting */
	private CircumstanceServiceFacade circumstanceServiceFacade;
	public void setCircumstanceServiceFacade(CircumstanceServiceFacade circumstanceServiceFacade) {
		this.circumstanceServiceFacade = circumstanceServiceFacade;
	}

	/* 귀속년월의 등록된 급/상여 지급일및 관련정보를 조회하는 메서드 */
	public void findSalPaymentDateList(PlatformData inData, PlatformData outData) throws Exception {
		String inputedYearMonth = inData.getVariable("inputedYearMonth").getValue().asString();
		List<SalPaymentDateBean> salPaymentsDateList = circumstanceServiceFacade.findSalPaymentDateList(inputedYearMonth);
		datasetBeanMapper.beansToDataset(outData, salPaymentsDateList, SalPaymentDateBean.class);
    }


	// 귀속년월의 급/상여 지급일 및 관련정보를 등록및 수정,삭제한 내용을 일괄처리하는 메서드 
	public void batchSalPaymentDate(PlatformData inData, PlatformData outData) throws Exception {
		
		List<SalPaymentDateBean> salPaymentDateList = datasetBeanMapper.datasetToBeans(inData, SalPaymentDateBean.class);
		circumstanceServiceFacade.batchSalPaymentDate(salPaymentDateList);
    }
    
}
