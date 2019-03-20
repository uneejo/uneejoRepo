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
import com.project.uniproduct.logistics.sales.serviceFacade.SalesServiceFacade;
import com.project.uniproduct.logistics.sales.to.ContractDetailTO;
import com.project.uniproduct.logistics.sales.to.ContractInfoTO;
import com.project.uniproduct.logistics.sales.to.ContractTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;

public class ContractController extends MultiActionController {


	SalesServiceFacade salesSF;
	
	public void setSalesSF(SalesServiceFacade salesSF) {
		this.salesSF = salesSF;
	}
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	public ModelAndView searchContract(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");


		try {

			ArrayList<ContractInfoTO> contractInfoTOList = null;

			if (searchCondition.equals("searchByDate")) {

				String[] paramArray = { startDate, endDate };
				contractInfoTOList = salesSF.getContractList("searchByDate", paramArray);

			} else if (searchCondition.equals("searchByCustomer")) {

				String[] paramArray = { customerCode };
				contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

			}

			modelMap.put("gridRowJson", contractInfoTOList);
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

	public ModelAndView searchContractDetail(HttpServletRequest request, HttpServletResponse response) {

		
		String contractNo = request.getParameter("contractNo");

		try {

			ArrayList<ContractDetailTO> contractDetailTOList = salesSF.getContractDetailList(contractNo);

			modelMap.put("gridRowJson", contractDetailTOList);
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

	public ModelAndView searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) {

	
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		

		try {

			ArrayList<EstimateTO> estimateListInContractAvailable = salesSF
					.getEstimateListInContractAvailable(startDate, endDate);

			modelMap.put("gridRowJson", estimateListInContractAvailable);
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

	public ModelAndView addNewContract(HttpServletRequest request, HttpServletResponse response) {

	
		String batchList = request.getParameter("batchList");
		String contractDate = request.getParameter("contractDate");
		String personCodeInCharge = request.getParameter("personCodeInCharge");
        

		try {
			EstimateTO previousEstimateTO = gson.fromJson(batchList, EstimateTO.class);

			ContractTO workingContractTO = gson.fromJson(batchList, ContractTO.class);

			HashMap<String, Object> resultList = salesSF.addNewContract(contractDate, personCodeInCharge,
					workingContractTO, previousEstimateTO);

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

	public ModelAndView cancleEstimate(HttpServletRequest request, HttpServletResponse response) {

		
		String estimateNo = request.getParameter("estimateNo");
		
		try {

			salesSF.changeContractStatusInEstimate(estimateNo, "N");

			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			modelMap.put("cancledEstimateNo", estimateNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		modelAndView = new ModelAndView("jsonView", modelMap);
		return modelAndView;
		
	}

}
