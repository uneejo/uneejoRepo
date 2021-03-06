package com.unicompany.base.controller;

import java.util.ArrayList;
import java.util.List;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.PlatformData;
import com.unicompany.base.serviceFacade.BaseServiceFacade;
import com.unicompany.base.to.CodeBean;
import com.unicompany.base.to.CodeInfoBean;
import com.unicompany.base.to.DetailCodeBean;
import com.unicompany.common.controller.AbstractMiplatformMultiActionController;

public class CodeController extends AbstractMiplatformMultiActionController{
	/* BaseServiceFacade setting */
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	public void findCodeList(PlatformData inData, PlatformData outData) throws Exception{
		List<CodeBean> codeList=baseServiceFacade.findCodeList();
		List<DetailCodeBean> detailCodeList=new ArrayList<DetailCodeBean>();
		for(CodeBean codeBean : codeList){
			List<DetailCodeBean> detailCodeBeanList=codeBean.getDetailCodeList();
			/* ArrayList를  ArrayList에 담을 수 있는 메서드 : addAll   ->add의 경우 ArrayList는 못담는듯 하다 에러가 난다 */
			detailCodeList.addAll(detailCodeBeanList);
		}
		datasetBeanMapper.beansToDataset(outData, codeList, CodeBean.class);
		datasetBeanMapper.beansToDataset(outData, detailCodeList, DetailCodeBean.class);
	}

	public void batchCode(PlatformData inData, PlatformData outData) throws Exception{
		Dataset dataset =inData.getDataset("dsCode");
		/*존재하지 않으면 unknown 나오는 듯? 코드가 변동이 있을 경우. 삭제를 지웠기 때문에  없오도 될듯 . 있어도 되고 or이기 때문에*/
		if(dataset.getRowStatus(0)!="unknown" || dataset.getDeleteRowCount() >0){
			CodeInfoBean codeInfoBean=new CodeInfoBean(); /*코드와 상세코드 list를 담아 둠.(여러개를 고칠경우 )*/
			codeInfoBean.setCodeList(datasetBeanMapper.datasetToBeans(inData, CodeBean.class));
			codeInfoBean.setDetailCodeList(datasetBeanMapper.datasetToBeans(inData, DetailCodeBean.class));
			baseServiceFacade.batchCode(codeInfoBean);
		}else{
			List<DetailCodeBean> codeDetailList=datasetBeanMapper.datasetToBeans(inData, DetailCodeBean.class);
			baseServiceFacade.batchDetailCode(codeDetailList);
		}

		findCodeList(inData,outData);
	}


}