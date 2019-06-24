package com.unicompany.hr.circumstance.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.circumstance.service.CircumstanceServiceFacade;
import com.unicompany.hr.circumstance.to.HobongBean;

public class HobongController extends AbstractMiplatformMultiActionController{
	/* CircumstanceServiceFacade setting */
	private CircumstanceServiceFacade circumstanceServiceFacade;
	public void setCircumstanceServiceFacade(CircumstanceServiceFacade circumstanceServiceFacade) {
		this.circumstanceServiceFacade = circumstanceServiceFacade;
	}
	
	/* 호봉목록을 가져오는 메서드 */
	public void findHobongList(PlatformData inData, PlatformData outData) throws Exception {
       List<HobongBean> hobongList=circumstanceServiceFacade.findHobongList();
       datasetBeanMapper.beansToDataset(outData, hobongList, HobongBean.class);
    }

	/* 호봉관련처리를 일괄적으로 하는 메서드 */
	public void batchHobong(PlatformData inData, PlatformData outData) throws Exception {
	   List<HobongBean> hobongList=datasetBeanMapper.datasetToBeans(inData, HobongBean.class);
	   circumstanceServiceFacade.batchHobong(hobongList);
	   findHobongList(inData,outData);
	}
}
