package com.unicompany.base.controller;


import java.util.ArrayList;
import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.base.serviceFacade.BaseServiceFacade;
import com.unicompany.base.to.PositionBean;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;
import com.unicompany.hr.circumstance.to.HobongBean;


public class PositionController extends AbstractMiplatformMultiActionController{
	/* BaseServiceFacade */
	private BaseServiceFacade baseServiceFacade;
    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }
    /* 직급목록을 가져오는 메서드 */
	public void findPositionList(PlatformData inData, PlatformData outData) throws Exception {
        List<PositionBean> positionList=baseServiceFacade.findPositionList();
        datasetBeanMapper.beansToDataset(outData, positionList, PositionBean.class);

      }
	
	/*직급하나 조회해서 호봉 가져오는 메서드*/
	public void findHobongList(PlatformData inData, PlatformData outData) throws Exception {
	
		String positionCode = inData.getVariable("positionCode").getValue().asString();
		PositionBean position=baseServiceFacade.findPosition(positionCode);
		 List<HobongBean> hobongList=position.getHobongList();
		 datasetBeanMapper.beansToDataset(outData, hobongList, HobongBean.class);
	}
}