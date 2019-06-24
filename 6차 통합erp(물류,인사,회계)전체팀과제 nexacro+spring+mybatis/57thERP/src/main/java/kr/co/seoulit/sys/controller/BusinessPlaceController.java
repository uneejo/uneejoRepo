package kr.co.seoulit.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexacro.xapi.data.PlatformData;

import kr.co.seoulit.common.mapper.DatasetBeanMapper;
import kr.co.seoulit.sys.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.sys.to.BusinessPlaceTO;
import kr.co.seoulit.sys.to.CodeTO;
import kr.co.seoulit.sys.to.DepartmentTO;
import kr.co.seoulit.sys.to.SequenceTo;

@Controller
public class BusinessPlaceController {

	@Autowired
	private BaseServiceFacade baseServiceFacade;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView = null;

	@RequestMapping("sys/findBusinessPlaceList.do")
	public ModelAndView findBusinessPlaceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//PlatformData outData = (PlatformData) request.getAttribute("outData");

		List<BusinessPlaceTO> businessPlaceList = baseServiceFacade.findBusinessPlaceList();
		modelMap.put("businessPlaceList",businessPlaceList);
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}

	@RequestMapping("sys/batchBusinessPlace.do")
	public void batchBusinessPlace(HttpServletRequest request, HttpServletResponse response)throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		List<BusinessPlaceTO> batchBusinessPlaceList=datasetBeanMapper.datasetToBeans(inData, BusinessPlaceTO.class);
	    baseServiceFacade.batchBusinessPlace(batchBusinessPlaceList);
	    findBusinessPlaceList(request,response);

	}
}