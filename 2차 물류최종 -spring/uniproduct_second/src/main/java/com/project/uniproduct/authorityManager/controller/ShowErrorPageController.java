package com.project.uniproduct.authorityManager.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class ShowErrorPageController extends MultiActionController {

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();


	// method 지정 없이 handleRequestInternal 메소드 오버라이딩
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {


		String viewName = "redirect:" + request.getContextPath() + "/hello.html";


		if (request.getRequestURI().contains("accessDenied")) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorTitle", "Access Denied");
			modelMap.put("errorMsg", "접근 권한이 없습니다. 관리자에게 문의하세요 ^^");
			viewName = "errorPage";
		}

	           modelAndView = new ModelAndView(viewName, modelMap);

		
		return modelAndView;
	}

}
