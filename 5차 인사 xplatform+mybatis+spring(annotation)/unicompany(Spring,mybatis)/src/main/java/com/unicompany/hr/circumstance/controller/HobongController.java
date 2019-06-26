package com.unicompany.hr.circumstance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tobesoft.xplatform.data.PlatformData;

import com.unicompany.hr.circumstance.serviceFacade.CircumstanceServiceFacade;
import com.unicompany.common.mapper.DatasetBeanMapper;
import com.unicompany.hr.circumstance.to.HobongBean;

@Controller
public class HobongController{
	/* CircumstanceServiceFacade setting */
	@Autowired
	private CircumstanceServiceFacade circumstanceServiceFacade;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	/* 호봉목록을 가져오는 메서드 */
	@RequestMapping("hr/circumstance/findHobongList.do")
	public void findHobongList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		List<HobongBean> hobongList=circumstanceServiceFacade.findHobongList();
       datasetBeanMapper.beansToDataset(outData, hobongList, HobongBean.class);
    }

	/* 호봉관련처리를 일괄적으로 하는 메서드 */
	@RequestMapping("hr/circumstance/batchHobong.do")
	public void batchHobong(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		List<HobongBean> hobongList=datasetBeanMapper.datasetToBeans(inData, HobongBean.class);
	   circumstanceServiceFacade.batchHobong(hobongList);
	   findHobongList(request,response);
	}
}
