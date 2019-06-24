package com.unicompany.base.controller;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tobesoft.xplatform.data.PlatformData;
import com.unicompany.base.to.DustBean;
import com.unicompany.common.mapper.DatasetBeanMapper;
import com.unicompany.common.publicApi.PublicDustApiBroker;

import java.io.BufferedReader;

@Controller
public class DustController {
	   
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	@Autowired
	private PublicDustApiBroker publicDustApiBroker;
	
	    @RequestMapping("base/getDustValue.do")
	    public void getDustValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	PlatformData outData = (PlatformData) request.getAttribute("outData");
	    	
	    	List<DustBean> dustlist=publicDustApiBroker.requestDustGrade();

	      datasetBeanMapper.beansToDataset(outData, dustlist, DustBean.class); 		
	    }
	}
