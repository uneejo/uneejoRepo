package kr.co.seoulit.sys.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nexacro.xapi.data.PlatformData;

import kr.co.seoulit.common.mapper.DatasetBeanMapper;
import kr.co.seoulit.sys.serviceFacade.BaseServiceFacade;
import kr.co.seoulit.sys.to.BusinessPlaceTO;
import kr.co.seoulit.sys.to.CodeTO;
import kr.co.seoulit.sys.to.SequenceTo;
import net.sf.json.JSONObject;

@Controller
public class CodeController {
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@Autowired
	private BaseServiceFacade baseServiceFacade;

	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	private ModelMap modelMap = new ModelMap();
	private ModelAndView modelAndView = null;
	@RequestMapping("sys/findCodeList.do")
	public ModelAndView findCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData outData = (PlatformData) request.getAttribute("outData");

		String code=request.getParameter("a");
		System.out.println(code);

		List<CodeTO> codeList = baseServiceFacade.findCodeList();
		modelMap.put("codeList",codeList);
		modelAndView = new ModelAndView("jsonView",modelMap);
		return modelAndView;

	}
	@RequestMapping("sys/batchCode.do")
	public void batchCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String batchList=request.getParameter("inData");
		System.out.println(batchList);
		List<CodeTO> List = gson.fromJson(batchList, new TypeToken<List<CodeTO>>() {}.getType());
		for(CodeTO ct:List) {
			System.out.println(ct.getCodeName());
		}


			baseServiceFacade.batchCodeListProcess(List);
	}
	@RequestMapping("sys/takeSerialCodeTest.do")
	public void takeSerialCodeTest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		HashMap<String, Object> findSeq=new HashMap<String, Object>();

		String Seq = inData.getVariable("findSeq").getString();
		findSeq.put("findSeq", Seq);
		System.out.println(findSeq);
		String serialNo=baseServiceFacade.takeSerialCodeTest(findSeq);

		SequenceTo seqTO=new SequenceTo();

		seqTO.setSeqNo(serialNo);
		datasetBeanMapper.beanToDataset(outData, seqTO, SequenceTo.class);

	}
}