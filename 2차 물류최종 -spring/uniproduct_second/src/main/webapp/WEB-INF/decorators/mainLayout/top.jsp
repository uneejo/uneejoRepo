<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css" />
<script 	src="${pageContext.request.contextPath}/js/menu.js"></script>

<style>

	.ui-jqgrid .ui-widget-header {
		height: 30px;
		font-size: 1.5em;
	}
	
	.ui-jqgrid .ui-jqgrid-hdiv {
		font-size: 1em;
		height: 30px;
	}

	.ui-jqgrid .ui-jqgrid-bdiv {
		overflow-x: auto;
		overflow-y: scroll;
	}
	
	#error-dialog {
		z-index: 10002 !important;
		display: none;
		font-size: 1.1em;
		color: black;
	}

</style>
<script>

	$(document).ready(function() {
		$("input[type=button], input[type=submit]").button();

		$("#cssmenu").menumaker({
			title : "Menu",
			format : "multitoggle"
		});
		
		hh.onclick=homepage;
		logout.onclick=loginform;
	
	});

	
	function homepage(){
		location.href="${pageContext.request.contextPath}/hello.html"
		
	}
	function loginform(){
		location.href="${pageContext.request.contextPath}/loginform.html"
		
	}

	//에러 메시지 폼인 error-dialog 를 전담하여 보여주는 함수
	function alertError(title, message) {
	
		// error-dialog 보이게 하기
		$("#error-dialog").attr("style", "display:block");

		$("#error-dialog").dialog({   // jqueryUI dialog 위젯 적용
			autoOpen : true,  // 자동으로 열리도록
			modal : true,     // 외부 클릭 못하게
			title : title,   // error-dialog 폼 제목
			width : 'auto',
			height : 'auto',
			position : {    // 폼 열릴 때 위치
				my : "center center",  
				at : "center-120 center-30"   // 폼 열릴 때, 대강 화면 중앙에 오도록
			},
			buttons : {  // 버튼 이벤트 적용
				"확인" : function() {
					$("#error-dialog").attr("style", "display:none");
					$("#error-dialog").dialog("close");
				}
			}
		});
		$("#error-dialog #errorMsg").html(message);

	}
	
</script>

<!--  사용자 정보 Div -->
<table style="width: 100%;">
	<tr>
		<td style="padding-left: 500px">
		</td>
		<td style="padding-left: 20px text-align: center;"><h1><a id="hh">unicompany</a></h1>
		</td>
		<td style="padding-left: 350px; text-align: Right;">
			<h4>사원ID : ${sessionScope.userId} <br/>
				직급 : ${sessionScope.positionName} <br/>  
				사원이름 : ${sessionScope.empName} <br/>
			    사업장명 : ${sessionScope.workplaceName} <br/>
				부서 : ${sessionScope.deptName}</h4>
	    <input type="button" value="로그아웃" id="logout">	
	    
		</td>
	</tr>
</table>

<!--  메뉴 div -->
<div id="cssmenu">${sessionScope.menuCode}</div>

<!-- 에러폼 div -->
<div id="error-dialog">
	<p id="errorMsg"></p>
</div> 
