package kr.co.seoulit.member.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.seoulit.common.servlet.ModelAndView;
import kr.co.seoulit.common.servlet.mvc.Controller;
import kr.co.seoulit.common.to.ListForm;
import kr.co.seoulit.member.sf.MemberServiceFacade;
import kr.co.seoulit.member.sf.MemberServiceFacadeImpl;
import kr.co.seoulit.member.to.MemberBean;

public class MemberListController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response){
		// TODO Auto-generated method stub
		int pn=1;
		if(request.getParameter("pn")!=null) {pn=Integer.parseInt(request.getParameter("pn"));}
		int rs=2;
		if(request.getParameter("rs")!=null) {rs=Integer.parseInt(request.getParameter("rs"));}
			
		 String uri=request.getRequestURI();
		 String contextPath=request.getContextPath();
		 String viewName=uri.split("[.]")[0].substring(contextPath.length());
		 HashMap<String,Object> map=new HashMap<String,Object>();
		ModelAndView mav=null;
	 	try {		
	 		ListForm list1=new ListForm();
			MemberServiceFacade sf=MemberServiceFacadeImpl.getInstance();
			int dbc=sf.getDbcount();
			list1.setPagenum(pn);
			list1.setRowsize(rs);
			list1.setDbcount(dbc);
			int startrow=list1.getStartrow();
			System.out.println(startrow);
			int endrow=list1.getEndrow();
			System.out.println(endrow);
			List<MemberBean> list=sf.getMemberList(startrow,endrow);
			list1.setList(list);
			
			map.put("list",list1);
	 		 mav=new ModelAndView(viewName,map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
	 		 map.put("errorCode", -1);
	 		 map.put("errorMsg", e.getMessage());
	 		 mav=new ModelAndView("error",map);
		}	
		return mav;
	}
}
