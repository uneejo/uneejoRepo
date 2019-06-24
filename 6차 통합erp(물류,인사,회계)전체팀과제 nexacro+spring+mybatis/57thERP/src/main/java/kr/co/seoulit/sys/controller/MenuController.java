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
import kr.co.seoulit.sys.to.CodeTO;
import kr.co.seoulit.sys.to.MenuTO;

@Controller
public class MenuController {

	@Autowired
	private BaseServiceFacade baseServiceFacade;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView = null;
	@RequestMapping("sys/findMenuList.do")
	public ModelAndView findMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData outData = (PlatformData) request.getAttribute("outData");

		List<MenuTO> menuList = baseServiceFacade.findMenuList();
		modelMap.put("menuList",menuList);
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;
	}

	@RequestMapping("sys/test.do")
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String empno=request.getParameter("empno");
		System.out.println("empno+++"+empno);

		List<CodeTO> codeList = baseServiceFacade.findCodeList();
		modelMap.put("codeList",codeList);
		modelAndView = new ModelAndView("jsonView",modelMap);
		//return modelAndView;

	}
}
