package com.unicompany.hr.pm.controller;

import java.io.BufferedOutputStream;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.unicompany.common.email.EmailManagement;
import com.unicompany.common.mapper.DatasetBeanMapper;
import com.unicompany.hr.pm.serviceFacade.PMServiceFacade;
import com.unicompany.hr.pm.to.EducationInfoBean;
import com.unicompany.hr.pm.to.EmpInfoBean;
import com.unicompany.hr.pm.to.EmployeeInfoBean;
import com.unicompany.hr.pm.to.FamilyInfoBean;
import com.unicompany.hr.pm.to.LicenseInfoBean;
import com.unicompany.hr.pm.to.SalInfoBean;
import com.unicompany.hr.pm.to.WorkInfoBean;
import com.unicompany.hr.pm.to.ReportBean;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class RegistrationController{
	/* PMServiceFacade setting */
	@Autowired
	private PMServiceFacade pmServiceFacade;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	@Autowired
	private EmailManagement emailManagement;
	 
	// 해당사원의 모든 사원관련 상세정보를 가져오는 메서드 
	@RequestMapping("hr/pm/findEmployeeInfoAll.do")
	public void findEmployeeInfoAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		PlatformData outData = (PlatformData) request.getAttribute("outData");  
		EmployeeInfoBean employeeInfoBean=pmServiceFacade.findEmployeeInfoAll();
		
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getEmpInfoList(), EmpInfoBean.class);
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getWorkInfoList(), WorkInfoBean.class);
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getFamilyInfoList(), FamilyInfoBean.class);
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getLicenseInfoList(), LicenseInfoBean.class);
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getEducationInfoList(), EducationInfoBean.class);
		datasetBeanMapper.beansToDataset(outData, employeeInfoBean.getSalInfoList(), SalInfoBean.class);
		
		
		
    }

	// 사원의 이미지를 저장하는 메서드 
	@RequestMapping("hr/pm/saveEmpImg.do")
	public void saveEmpImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		DataSet dataset = inData.getDataSet("dsImg");
		FileOutputStream out = null;
		String fileName = dataset.getString(0, "EMP_FILE_NAME");
		try {
			if (fileName != null) {
				out = new FileOutputStream("E:/miplatformpj/unicompany/src/main/webapp/img/" + fileName);
				
				
				byte[] file = dataset.getBlob(0, "IMG_FILE_DATA");
				BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
				bufferedOut.write(file);
				bufferedOut.flush();
				bufferedOut.close();
				out.close();
				bufferedOut = null;
				out = null;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	// 사원의 상세정보를 일괄처리하는 메서드
	@RequestMapping("hr/pm/batchEmployeeInfoAll.do")
	public void batchEmployeeInfoAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		System.out.println(inData);
		EmployeeInfoBean employeeInfoBean=new EmployeeInfoBean();
		employeeInfoBean.setEmpInfoList(datasetBeanMapper.datasetToBeans(inData, EmpInfoBean.class));
		employeeInfoBean.setWorkInfoList(datasetBeanMapper.datasetToBeans(inData, WorkInfoBean.class));
		employeeInfoBean.setFamilyInfoList(datasetBeanMapper.datasetToBeans(inData, FamilyInfoBean.class));
		employeeInfoBean.setLicenseInfoList(datasetBeanMapper.datasetToBeans(inData, LicenseInfoBean.class));
		employeeInfoBean.setEducationInfoList(datasetBeanMapper.datasetToBeans(inData, EducationInfoBean.class));
	
		employeeInfoBean.setSalInfoList(datasetBeanMapper.datasetToBeans(inData, SalInfoBean.class));
		
		
		pmServiceFacade.batchEmployeeInfoAll(employeeInfoBean);
		findEmployeeInfoAll(request,response);
    }

	//재직증명서 pdf 
	@RequestMapping("hr/pm/pdfPrintEmp.do")
	public ModelAndView pdfPrintEmp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String empCode = request.getParameter("empCode");
		String format = request.getParameter("format");
		System.out.println("empCode : "+empCode);
		System.out.println("format : "+format);
		List<ReportBean> reportList = pmServiceFacade.findEmpInfoReport(empCode);
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportList);

		ModelMap modelMap = new ModelMap();
		modelMap.put("format", format);
		modelMap.put("source", source);
		ModelAndView modelAndView = new ModelAndView("multiformat-view", modelMap);
		//request.setAttribute("outData", null);
		return modelAndView;
	}
	
	//email 보내기
			@RequestMapping("hr/pm/sendEmail.do")
			public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
				PlatformData inData = (PlatformData) request.getAttribute("inData"); 
				  String empCode = inData.getVariable("sEmpCode").getString();
			      String sEmail = inData.getVariable("sEmail").getString();
			      emailManagement.sendEmailMgt(empCode,sEmail);
			}    
		
}