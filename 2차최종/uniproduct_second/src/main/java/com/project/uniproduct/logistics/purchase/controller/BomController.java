package com.project.uniproduct.logistics.purchase.controller;

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
import com.project.uniproduct.logistics.purchase.serviceFacade.PurchaseServiceFacade;
import com.project.uniproduct.logistics.purchase.to.BomDeployTO;
import com.project.uniproduct.logistics.purchase.to.BomInfoTO;
import com.project.uniproduct.logistics.purchase.to.BomTO;

public class BomController extends MultiActionController {


	PurchaseServiceFacade purchaseSF;

    public void setPurchaseSF(PurchaseServiceFacade purchaseSF) {
    	this.purchaseSF=purchaseSF;
    }	
	
     private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

     private ModelAndView modelAndView;
 	 private ModelMap modelMap = new ModelMap();
	
	public ModelAndView searchBomDeploy(HttpServletRequest request, HttpServletResponse response) {

		String deployCondition = request.getParameter("deployCondition");
		String itemCode = request.getParameter("itemCode");

		try {

			ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode);

			modelMap.put("gridRowJson", bomDeployList);
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

	public ModelAndView searchBomInfo(HttpServletRequest request, HttpServletResponse response) {

		String parentItemCode = request.getParameter("parentItemCode");

	
		try {
	

			ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);

			modelMap.put("gridRowJson", bomInfoList);
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

	public ModelAndView searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {

	try {
		   ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

		   modelMap.put("gridRowJson", allItemWithBomRegisterAvailable);
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

	public ModelAndView batchBomListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());

		
		try {


			HashMap<String, Object> resultList = purchaseSF.batchBomListProcess(batchBomList);

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
