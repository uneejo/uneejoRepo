package com.unicompany.base.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.base.serviceFacade.BaseServiceFacade;
import com.unicompany.base.to.EtcSalBean;
import com.unicompany.base.to.OvertimeSalBean;
import com.unicompany.base.to.SudangInfoBean;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;

public class BasicSalaryController extends AbstractMiplatformMultiActionController {
	/* BaseServiceFacade setting */
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	/* 연장,야간,휴일,기타 수당관련 목록을 불러오는 메서드 */
	public void findSalaryList(PlatformData inData, PlatformData outData) throws Exception{
		List<OvertimeSalBean> overTimeSalList=baseServiceFacade.findOvertimeSalList();
		List<EtcSalBean> etcSalList=baseServiceFacade.findEtcSalList();
		datasetBeanMapper.beansToDataset(outData, overTimeSalList, OvertimeSalBean.class);
		datasetBeanMapper.beansToDataset(outData, etcSalList, EtcSalBean.class);
	}

	public void batchSudang(PlatformData inData, PlatformData outData) throws Exception{

			SudangInfoBean sudangInfoBean=new SudangInfoBean();
			sudangInfoBean.setOverTimeSalList(datasetBeanMapper.datasetToBeans(inData, OvertimeSalBean.class));
			sudangInfoBean.setEtcSalList(datasetBeanMapper.datasetToBeans(inData, EtcSalBean.class));

			baseServiceFacade.batchSudang(sudangInfoBean);


		findSalaryList(inData,outData);
	}


}