<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.structure.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.theme.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/jqGrid/plugins/ui.multiselect.css" />
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<script 	src="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.js"></script>
<script 	src="${pageContext.request.contextPath}/scripts/jqGrid/js/jquery.jqGrid.min.js"></script>
<script 	src="${pageContext.request.contextPath}/scripts/jqGrid/js/i18n/grid.locale-kr.js"></script>
<script 	src="${pageContext.request.contextPath}/scripts/jqGrid/js/i18n/grid.locale-en.js"></script>
<decorator:head />
<style>
body {
   margin: 0 auto;
}

#topmenu {
   margin: 5px 5px 5px 5px;
   padding: 5px 5px 5px 5px;
   height: 20%;
   min-width: 95%;
}

#wrap {
   height: 100%;
   min-width: 100%;
}

#contents {
   width: 100%;
   height: 100%;
   float: left;
}

#section {
   margin: 5px 5px 5px 5px;
   padding: 5px 5px 5px 5px;
   width: 95%;
   float: center;
   min-width: 80%;
}

#bottom {
   text-align: center;
   min-width: 80%;
}
</style>
</style>
</head>
<body>

	<div id="topmenu">
		<jsp:include page="top.jsp" />
	</div>
	<div id="wrap" style="z-index: 100">
		<div id="contents">
			<div id="section">
				<decorator:body />
			</div>
		</div>
	</div>
	<div id="bottom">
		<jsp:include page="bottom.jsp" />
	</div>
	
	
	
</body>
</html>