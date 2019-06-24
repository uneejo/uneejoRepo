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
import com.project.uniproduct.basicInfo.to.WorkplaceTO;

public class WorkplaceController extends MultiActionController {

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// serviceFacade 참조변수 선언
	private OrganizationServiceFacade orgSF;

	public void setOrgSF(OrganizationServiceFacade orgSF) {
		this.orgSF = orgSF;
	}

	public ModelAndView searchWorkplaceList(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");

		try {

			ArrayList<WorkplaceTO> workplaceList = orgSF.getWorkplaceList(companyCode);

			modelMap.put("gridRowJson", workplaceList);
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

		try {

			ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
			}.getType());

			HashMap<String, Object> resultMap = orgSF.batchWorkplaceListProcess(workplaceList);

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
