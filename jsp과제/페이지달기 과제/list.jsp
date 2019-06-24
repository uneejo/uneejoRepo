<%@page import="kr.co.seoulit.common.to.ListForm"%>
<%@page import="kr.co.seoulit.member.to.MemberBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.7.js">
</script>
 <script type="text/javascript">
 $(document).ready(function(){
	 var r=<%=request.getParameter("rs")%>
	 $("#sel").val(r);
	 
 });
 
 function f(num){
	 document.forms[0].elements[0].value=num;
	 document.forms[0].elements[1].value=sel.value;
	 document.forms[0].submit();
	 }
</script>
</head> 
<body>
<h1>회원리스트</h1>
<% @SuppressWarnings("unchecked")
  ListForm list=(ListForm)request.getAttribute("list");
  List<MemberBean> li=(List<MemberBean>)list.getList();
  int pn=list.getPagenum();
  int rs=list.getRowsize();
  int dbc=list.getDbcount();
  int startrow=list.getStartrow();
  String id;
  int num=startrow;
  int startpage=list.getStartpage();
  int endpage=list.getEndpage();
  
  for(MemberBean bean:li){
	  id=bean.getId();
%>


<%=(num++)+"."%>&nbsp;<a href="/mvcTest77/member/detail.do?id=<%=id%>"><%=id%></a><br/>

<%} %>


<%

if(list.isPrevious()){ 
%> 
<a href="#" onclick="f(<%=pn-1%>)">◀</a>
<%
}
for(int n=startpage; n<=endpage; n++){ 
%>
<a href="#" onclick="f(<%=n%>)"><%=n%>&nbsp;</a>
<%
}

if(list.isNext()){
%>
<a href="#" onclick="f(<%=pn+1%>)">▶</a>

<% 
}
%>

<form action="list.do">
<input type="hidden" name="pn">
<input type="hidden" name="rs">
</form>
<select id="sel" onchange=f(<%=pn%>)>
<option>2</option>
<option>4</option>
<option>6</option>
</select>



</body>
</html>