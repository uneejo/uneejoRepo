package com.project.uniproduct.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.uniproduct.basicInfo.serviceFacade.OrganizationServiceFacade;
import com.project.uniproduct.basicInfo.to.CompanyTO;

public class CompanyController extends MultiActionController {


	OrganizationServiceFacade orgSF;
   
	public void setOrgSF(OrganizationServiceFacade orgSF) {
		this.orgSF=orgSF;
	}
	
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	
	public ModelAndView searchCompanyList(HttpServletRequest request, HttpServletResponse response) {


		try {

			ArrayList<CompanyTO> companyList = orgSF.getCompanyList();

			modelMap.put("gridRowJson", companyList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<CompanyTO> companyList = gson.fromJson(batchList, new TypeToken<ArrayList<CompanyTO>>() {
		}.getType());

		try {

			HashMap<String, Object> resultMap = orgSF.batchCompanyListProcess(companyList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
	}

}
