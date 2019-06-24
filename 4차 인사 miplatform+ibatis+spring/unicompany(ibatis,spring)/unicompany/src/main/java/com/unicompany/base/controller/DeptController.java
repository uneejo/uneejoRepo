package com.unicompany.base.controller;

import java.util.List;

import com.tobesoft.platform.data.PlatformData;
import com.unicompany.base.serviceFacade.BaseServiceFacade;
import com.unicompany.base.to.DepartmentBean;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;

public class DeptController extends AbstractMiplatformMultiActionController{
	/* baseServiceFacade setting */
	private BaseServiceFacade baseServiceFacade;
    public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
        this.baseServiceFacade = baseServiceFacade;
    }

    /* 부서목록을 조회하는 메서드 */
	public void findDeptList(PlatformData inData, PlatformData outData) throws Exception {

		String businessPlaceCode= inData.getVariable("businessPlaceCode").getValue().asString();
		List<DepartmentBean> deptList=baseServiceFacade.findDeptList(businessPlaceCode);
        datasetBeanMapper.beansToDataset(outData, deptList, DepartmentBean.class);
        /*
         * 	beansToDataset : bean --> dataset
         * */
    }
	
	/*부서관리 (등록/삭제/수정)*/
	public void batchDept(PlatformData inData, PlatformData outData) throws Exception {
		/*
		 * datasetToBean도 있다.
		 *
		 *  datasetToBeans : dataset --> bean
		 *
		 * */
        List<DepartmentBean> deptList=datasetBeanMapper.datasetToBeans(inData, DepartmentBean.class);
        baseServiceFacade.batchDept(deptList);

        findDeptList(inData,outData);
    }

	
}
