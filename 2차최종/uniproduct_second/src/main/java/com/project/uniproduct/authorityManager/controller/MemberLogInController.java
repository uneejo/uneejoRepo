package com.project.uniproduct.authorityManager.controller;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.project.uniproduct.authorityManager.exception.DataNotInputException;
import com.project.uniproduct.authorityManager.exception.IdNotFoundException;
import com.project.uniproduct.authorityManager.exception.PwMissMatchException;
import com.project.uniproduct.authorityManager.exception.PwNotFoundException;
import com.project.uniproduct.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.project.uniproduct.hr.to.EmpInfoTO;

public class MemberLogInController extends MultiActionController {


	private AuthorityManagerServiceFacade authorityManagerSF;
	
	public void setAuthorityManagerSF(AuthorityManagerServiceFacade authorityManagerSF){
		this.authorityManagerSF=authorityManagerSF;
	}

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	@SuppressWarnings("unused")
	private MessageSource messageSource;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	  
	public ModelAndView LogInCheck(HttpServletRequest request, HttpServletResponse response) {
          
		String viewName = null;
		HttpSession session = request.getSession();

		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String inputId = request.getParameter("userId");
		String inputPassWord = request.getParameter("userPassWord");

		try {
			if (companyCode.equals("") || workplaceCode.equals("") || inputId.equals("") || inputPassWord.equals("")) {
				throw new DataNotInputException("입력하지 않은 값이 있습니다");
			}

			EmpInfoTO TO = authorityManagerSF.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);

			if (TO != null) {

				ServletContext application = request.getServletContext();

				session.setAttribute("userId", TO.getUserId());
				session.setAttribute("empCode", TO.getEmpCode());
				session.setAttribute("empName", TO.getEmpName());
				session.setAttribute("deptCode", TO.getDeptCode());
				session.setAttribute("deptName", TO.getDeptName());
				session.setAttribute("positionCode", TO.getPositionCode());
				session.setAttribute("positionName", TO.getPositionName());
				session.setAttribute("companyCode", TO.getCompanyCode());
				session.setAttribute("workplaceCode", workplaceCode);
				session.setAttribute("workplaceName", TO.getWorkplaceName());

				String menuCode = authorityManagerSF.getUserMenuCode(workplaceCode, TO.getDeptCode(), TO.getPositionCode(),
						application);
				session.setAttribute("menuCode", menuCode);

				viewName = "redirect:/hello.html";
				System.out.println("로그인 되었습니다");
			}

		} catch (DataNotInputException e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
			viewName = "loginform";
		} catch (IdNotFoundException e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());
			viewName = "loginform";
		} catch (PwNotFoundException e3) {
			e3.printStackTrace();
			modelMap.put("errorCode", -3);
			modelMap.put("errorMsg", e3.getMessage());
			viewName = "loginform";
		} catch (PwMissMatchException e4) {
			e4.printStackTrace();
			modelMap.put("errorCode", -4);
			modelMap.put("errorMsg", e4.getMessage());
			viewName = "loginform";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("errorCode", -5);
			modelMap.put("errorMsg", e.getMessage());
			viewName = "loginform";
		}

		modelAndView = new ModelAndView(viewName, modelMap);

		return modelAndView;

	}

}