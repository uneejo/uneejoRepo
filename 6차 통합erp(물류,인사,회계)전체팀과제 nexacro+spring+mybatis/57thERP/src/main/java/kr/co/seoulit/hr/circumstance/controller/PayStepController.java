package kr.co.seoulit.hr.circumstance.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexacro.xapi.data.PlatformData;

import kr.co.seoulit.common.mapper.DatasetBeanMapper;
import kr.co.seoulit.hr.circumstance.serviceFacade.CircumstanceServiceFacade;
import kr.co.seoulit.hr.circumstance.to.PayStepTO;


@Controller
public class PayStepController{
	@Autowired
	private CircumstanceServiceFacade circumstanceFacade;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	@Autowired
	private DataSource dataSource;
	
	/* 호봉목록을 가져오는 메서드 */
	@RequestMapping("hr/circumstance/findPayStepList.do")
	public void findPayStepList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		List<PayStepTO> payStepList=circumstanceFacade.findPayStepList();
		datasetBeanMapper.beansToDataset(outData, payStepList, PayStepTO.class);
    }

	/* 호봉관련처리를 일괄적으로 하는 메서드 */
	@RequestMapping("hr/circumstance/batchPayStep.do")
	public void batchPayStep(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		List<PayStepTO> payStepList=datasetBeanMapper.datasetToBeans(inData, PayStepTO.class);
		circumstanceFacade.batchPayStepList(payStepList);
		findPayStepList(request,response);
	}
	
	@RequestMapping("hr/circumstance/pdfPrint.do")
	public ModelAndView pdfPrintEmp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 String contract_no = request.getParameter("contractNo");
		//response.getOutputStream().flush();

		ModelMap modelMap = new ModelMap();
		modelMap.put("format", "pdf");
		modelMap.put("contract_no", contract_no);
		modelMap.put("jdbcDataSource", dataSource);
		ModelAndView modelAndView = new ModelAndView("multiformat-view", modelMap);

		return modelAndView;
	}
	
	
	
}
