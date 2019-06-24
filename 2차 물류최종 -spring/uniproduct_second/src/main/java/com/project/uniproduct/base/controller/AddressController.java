package com.project.uniproduct.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.project.uniproduct.base.serviceFacade.BaseServiceFacade;
import com.project.uniproduct.base.to.AddressTO;

public class AddressController extends MultiActionController {

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	// serviceFacade 참조변수 선언
	private BaseServiceFacade baseSF;

	public void setBaseSF(BaseServiceFacade baseSF) {
		this.baseSF = baseSF;
	}

	public ModelAndView searchAddressList(HttpServletRequest request, HttpServletResponse response) {

		String sidoName = request.getParameter("sidoName");
		String searchAddressType = request.getParameter("searchAddressType");
		String searchValue = request.getParameter("searchValue");
		String mainNumber = request.getParameter("mainNumber");

		try {

			ArrayList<AddressTO> addressList = baseSF.getAddressList(sidoName, searchAddressType, searchValue,
					mainNumber);

			modelMap.put("addressList", addressList);
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
