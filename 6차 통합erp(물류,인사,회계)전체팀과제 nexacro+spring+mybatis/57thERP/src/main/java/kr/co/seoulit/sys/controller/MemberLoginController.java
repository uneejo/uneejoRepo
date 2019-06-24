package kr.co.seoulit.sys.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexacro.xapi.data.PlatformData;
import kr.co.seoulit.hr.emp.to.EmployeeTO;

import kr.co.seoulit.common.mapper.DatasetBeanMapper;
import kr.co.seoulit.sys.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.sys.to.MenuTO;

@Controller
public class MemberLoginController {

	@Autowired
	private BaseServiceFacade baseServiceFacade;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	@SuppressWarnings("unchecked")
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView = null;


	@RequestMapping("sys/checkLogin.do")
	public ModelAndView checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> loginInfo=new HashMap<>();
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");

		String businessPlaceCode = inData.getVariable("businessPlaceCode").getString().toUpperCase();
		String deptCode = inData.getVariable("deptCode").getString().toUpperCase();
		String empCode = inData.getVariable("empCode").getString().toUpperCase();
		String password = inData.getVariable("password").getString();
		loginInfo.put("businessPlaceCode", businessPlaceCode);
		loginInfo.put("deptCode", deptCode);
		loginInfo.put("empCode", empCode);
		loginInfo.put("password", password);
		//HashMap<String, Object> result = baseServiceFacade.accessToAuthority(loginInfo);
		EmployeeTO employee=baseServiceFacade.checkLogin(loginInfo);
		datasetBeanMapper.beanToDataset(outData, employee, EmployeeTO.class);
		modelMap.put("employee",employee);
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;

	}
}
