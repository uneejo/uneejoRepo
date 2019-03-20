package com.project.uniproduct.logistics.sales.controller;

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
import com.project.uniproduct.logistics.sales.serviceFacade.SalesServiceFacade;
import com.project.uniproduct.logistics.sales.to.EstimateDetailTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;

public class EstimateController extends MultiActionController {

	SalesServiceFacade salesSF;
    
	public void setSalesSF(SalesServiceFacade salesSF) {
		this.salesSF = salesSF;
	}

	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	
	public ModelAndView searchEstimateInfo(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateSearchCondition = request.getParameter("dateSearchCondition");

		
		try {
			

			ArrayList<EstimateTO> estimateTOList = salesSF.getEstimateList(dateSearchCondition, startDate, endDate);

			modelMap.put("gridRowJson", estimateTOList);
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

	public ModelAndView searchEstimateDetailInfo(HttpServletRequest request, HttpServletResponse response) {

		String estimateNo = request.getParameter("estimateNo");


		try {
			

			ArrayList<EstimateDetailTO> estimateDetailTOList = salesSF.getEstimateDetailList(estimateNo);

			modelMap.put("gridRowJson", estimateDetailTOList);
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

	public ModelAndView addNewEstimate(HttpServletRequest request, HttpServletResponse response) {

		String estimateDate = request.getParameter("estimateDate");
		String newEstimateInfo = request.getParameter("newEstimateInfo");

		EstimateTO newEstimateTO = gson.fromJson(newEstimateInfo, EstimateTO.class);


		try {


			HashMap<String, Object> resultList = salesSF.addNewEstimate(estimateDate, newEstimateTO);

			modelMap.put("result", resultList);
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

		ArrayList<EstimateDetailTO> estimateDetailTOList = gson.fromJson(batchList,
				new TypeToken<ArrayList<EstimateDetailTO>>() {
				}.getType());

		
		try {
			

			HashMap<String, Object> resultList = salesSF.batchEstimateDetailListProcess(estimateDetailTOList);

			modelMap.put("result", resultList);
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
