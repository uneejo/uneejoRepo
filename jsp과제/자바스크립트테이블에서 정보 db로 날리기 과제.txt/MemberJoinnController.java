package kr.co.seoulit.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.seoulit.common.servlet.ModelAndView;
import kr.co.seoulit.common.servlet.mvc.Controller;
import kr.co.seoulit.member.sf.MemberServiceFacade;
import kr.co.seoulit.member.sf.MemberServiceFacadeImpl;
import kr.co.seoulit.member.to.MemberBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MemberJoinnController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response){
		// TODO Auto-generated method stub
		 String uri=request.getRequestURI();
		 String contextPath=request.getContextPath();
		 String viewName="redirect:"+contextPath+"/menu.html";
		 HashMap<String,Object> map=new HashMap<String,Object>();
		ModelAndView mav=null;
	 	try {		
	 		String s=request.getParameter("sendData");   // toJSON으로 날린거 받아서
	 		System.out.println(s);
	 		JSONObject json=JSONObject.fromObject(s);  //다시 객체로 바꿈.
	 		JSONArray array=(JSONArray)json.get("list");   //객체에 키값 list의 value는 배열이라서 배열로 받음.
	 		for(int a=0; a<array.size(); a++) {             // 배열의크기구하기 ->size()
	 		JSONObject json1=array.getJSONObject(a);   // 배열하나하나 빼오기
	 		MemberBean bean1=(MemberBean)JSONObject.toBean(json1,MemberBean.class);  // bean에다가 바로 넣기
			MemberServiceFacade sf=MemberServiceFacadeImpl.getInstance();  
			sf.addMember(bean1);    // DB에 insert하기
	 		}

	 		 mav=new ModelAndView(viewName,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
	 		 map.put("errorCode", -1);
	 		 map.put("errorMsg", e.getMessage());
	 		 mav=new ModelAndView("error",map);
		}	
		return mav;
	}
}
