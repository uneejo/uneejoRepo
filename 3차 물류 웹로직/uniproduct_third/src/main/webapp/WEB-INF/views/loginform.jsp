<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uniproduct 프로젝트 로그인 (3차)</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/plugins/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_util.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginform_modified.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/jqGrid/plugins/ui.multiselect.css" />

<script    src="${pageContext.request.contextPath}/scripts/jquery/jquery-3.3.1.min.js"></script>
<script    src="${pageContext.request.contextPath}/scripts/jqueryUI/jquery-ui.min.js"></script>
<script    src="${pageContext.request.contextPath}/scripts/jqGrid/js/jquery.jqGrid.min.js"></script>
<script    src="${pageContext.request.contextPath}/scripts/jqGrid/js/i18n/grid.locale-kr.js"></script>
<style>
#bodyTag{
background-color:#7C96C9;
}
.ui-jqgrid .ui-jqgrid-hdiv {
   font-size: 0.8em;
   height: 35px;
}

.ui-jqgrid .ui-widget-header {
   height: 37px;
   font-size: 0.9em;
}

.ui-jqgrid .ui-jqgrid-bdiv {
   overflow-x: auto;
   overflow-y: scroll;
}

</style>
<script>

   <%session.invalidate();%>     //session 초기화

   // 여기서는 안쓰지만, 그냥 넣은 변수들
   var lastSelected_CompanyCodeGrid_Id;   // 회사코드 grid 에서 마지막 선택한 row 의 id
   var lastSelected_CompanyCodeGrid_RowValue;  // 회사코드 grid 에서 마지막 선택한 row 의 data
   
   var lastSelected_WorkplaceCodeGrid_Id;  // 사업장코드 grid 에서 마지막 선택한 row 의 id
   var lastSelected_WorkplaceCodeGrid_RowValue;  // 사업장코드 grid 에서 마지막 선택한 row 의 data
   
   $(document).ready(function() {
      
      // MemberLogInController 의 LogInCheck 메서드에서 보낸 erreoCode 가 음수인 경우, 즉 로그인 실패
      if ("${requestScope.errorCode}" < 0) {   
         alertError("로그인 에러", "로그인을 실패하였습니다. </br> ${requestScope.errorMsg}");
      } else {
         $("#error-dialog").attr("style", "display:none");
      }

      // 회사코드, 사업장코드 보여주는 dialog-form 에 jqueryUI dialog 위젯 적용
      $("#dialog-form").dialog({
         title : '코드 검색',
         autoOpen : false,  // 자동으로 열리지 않게
         width : 630,
         height :480,
         modal : true   // 폼 외부 클릭 못하게
      });
      
      initEvent();  // 이벤트 적용 함수
      showCompanyCodeGrid();   // 회사 코드 grid 세팅 함수

   });

   function initEvent() {
      
      // 회사코드 검색 버튼 클릭시
      $("#companyCodeSearchButton").on("click",function() {
         
         $("#companyCodeGridDiv").show();  // 회사코드 grid 있는 div 보여주고,
         $("#workplaceCodeGridDiv").hide();  // 사업장 코드 grid 있는 div 숨김
         
         $("#dialog-form").dialog("open");  // 코드 검색 창 열기
      });

      
      // 사업장코드 검색 버튼 클릭시
      $("#workplaceCodeSearchButton").on("click",function() {

         // companyCode 텍스트박스에 입력된 값이 없음 : 사업장 검색 불가능 => 에러 띄우기
         if($("#companyCode").val() == "") {
            alertError("입력 에러", "회사 코드를 먼저 입력하세요 ^^");

         } else { 
            
            $("#companyCodeGridDiv").hide();  // 회사코드 grid 있는 div 숨기고,
            $("#workplaceCodeGridDiv").show();  // 사업장 grid 코드 있는 div 보여주기
            
            // 사업장 코드 grid 보여주기
            showWorkplaceCodeGrid();
            
            $("#dialog-form").dialog("open");   // 코드 검색 창 열기
         }
      });

   }
   
   // 에러 메시지 폼인 error-dialog 를 전담하여 보여주는 함수
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
            at : "center-70 center-50"   // 폼 열릴 때, 대강 화면 중앙에 오도록
         },
         buttons : {  // 버튼 이벤트 적용
            "확인" : function() {
               $("#error-dialog").attr("style", "display:none");
               $("#error-dialog").dialog("close");
            }
         }
      });
      
      // error-dialog 안의 errorMsg p 태그에 에러 메시지 적용
      $("#error-dialog #errorMsg").html(message);   
   }

   function showCompanyCodeGrid() {

      // 회사코드 ajax 시작
      $.ajax({ 
         type : 'POST',
         url : '${pageContext.request.contextPath}/basicInfo/searchCompany.do',
         data : {
            
            // MultiActionController : 여기서는 MemberLoginController 의 searchCompanyCode 메서드 호출
            method : 'searchCompanyList', 
         },
         dataType : 'json', 
         cache : false, 
         success : function(dataSet) { 
            console.log(dataSet);
            var gridRowJson = dataSet.gridRowJson;  // gridRowJson : 그리드에 넣을 json 형식의 data
            
            // 회사코드 grid 시작
            $('#companyCodeGrid').jqGrid({ 
               mtype : 'POST', 
               datatype : 'local',
               colNames : [ "회사코드", " 회사명", " 회사구분", " 사업자번호" ], 
               colModel : [ 
                  { name: "companyCode", width: "90", resizable: true, align: "center"} ,
                  { name: "companyName", width: "200", resizable: true, align: "center"} ,
                  { name: "companyDivision", width: "90", resizable: true, align: "center"} ,
                  { name: "businessLicenseNumber", width: "120", resizable: true, align: "center"} ,
               ], 
               caption : '회사코드 검색', 
               sortname : 'companyCode', 
               multiselect : false, 
               multiboxonly : false,
               viewrecords : true, 
               rownumWidth : 30, 
               height : 300, 
               width : 580,
               autowidth : false, 
               shrinkToFit : true, 
               cellEdit : false,
               rowNum : 50,   // -1 : 모든 로우 한번에 표시, 그런데 잘 안먹히는 경우 많음
               scrollerbar: true, 
               //rowList : [ 10, 20, 30 ],
               viewrecords : true, 
               editurl : 'clientArray', 
               cellsubmit : 'clientArray',
               rownumbers : true, 
               autoencode : true, 
               resizable : true,
               loadtext : '로딩중...', 
               emptyrecords : '데이터가 없습니다.', 
               cache : false, 
               
               // grid 의 로우 선택시 이벤트 : 여기서는 안쓰지만, 그냥 적용해 봤음
               onSelectRow: function(id) {   
                  if( lastSelected_CompanyCodeGrid_Id != id ){
                     lastSelected_CompanyCodeGrid_Id = id;
                     lastSelected_CompanyCodeGrid_RowValue = $(this).jqGrid('getRowData', id); 
                      }
                  
                  // 선택된 로우의 회사코드 값을 companyCode 텍스트박스에 넣기
                  $("#companyCode").val( lastSelected_CompanyCodeGrid_RowValue.companyCode );

                  $("#dialog-form").dialog("close");   // 폼 닫기                  
                  
               }
            }); // 회사코드 grid 끝
            
            // 회사코드 Data 넣기
            $('#companyCodeGrid')
               .jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
               .trigger('reloadGrid');
         
         
      }});  // 회사코드 ajax 끝
   }
   
   
   function showWorkplaceCodeGrid() {
      
      $.jgrid.gridUnload("#workplaceCodeGrid");   // 사업장 코드 grid 완전 초기화

      // 사업장코드 ajax 시작
      $.ajax({ 
         type : 'POST',
         url : '${pageContext.request.contextPath}/basicInfo/searchWorkplace.do',
         data : {
            companyCode : $("#companyCode").val(),  // 주의, 변수의 값을 넘길 때는 "" 나 '' 있으면 안됨!!

            // MultiActionController : 여기서는 MemberLoginController 의 searchWorkplaceCode 메서드 호출
            method : 'searchWorkplaceList'    
         },
         dataType : 'json', 
         cache : false, 
         success : function(dataSet) { 
            console.log(dataSet);
            var gridRowJson = dataSet.gridRowJson;      
            
            // 사업장코드 grid 시작
            $('#workplaceCodeGrid').jqGrid({ 
               mtype : 'POST', 
               datatype : 'local',
               colNames : ["회사코드", " 사업장코드", " 사업장명", " 사업장번호"], 
               colModel : [ 
                  { name: "companyCode", width: "90", resizable: true, align: "center"} ,
                  { name: "workplaceCode", width: "110", resizable: true, align: "center"} ,
                  { name: "workplaceName", width: "200", resizable: true, align: "center"} ,
                  { name: "businessLicenseNumber", width: "110", resizable: true, align: "center"}
               ], 
               caption : '사업장코드 검색', 
               sortname : 'workplaceCode', 
               multiselect : false, 
               multiboxonly : false,
               viewrecords : true, 
               rownumWidth : 30, 
               height : 300, 
               width : 580,
               autowidth : false, 
               shrinkToFit : true, 
               cellEdit : false,
               rowNum : 10, 
               scrollerbar: true, 
               rowList : [ 10, 20, 30 ],
               viewrecords : true, 
               editurl : 'clientArray', 
               cellsubmit : 'clientArray',
               rownumbers : true, 
               autoencode : true, 
               resizable : true,
               loadtext : '로딩중...', 
               emptyrecords : '데이터가 없습니다.', 
               cache : false, 
               pager : '#workplaceCodeGridPager',   // pager 적용시에는 반드시 이 옵션 있어야 함
               onSelectRow: function(id) {
                  if( lastSelected_WorkplaceCodeGrid_Id != id ){
                     lastSelected_WorkplaceCodeGrid_Id = id;
                     lastSelected_WorkplaceCodeGrid_RowValue = $(this).jqGrid('getRowData', id); 
                      }
                  
                  // 선택된 로우의 사업장코드 값을 workplaceCode 텍스트박스에 넣기
                  $("#workplaceCode").val( lastSelected_WorkplaceCodeGrid_RowValue.workplaceCode );

                  $("#dialog-form").dialog("close");   // 폼 닫기      
                  
               }
            }); // 사업장코드 grid 끝

            // 사업장코드 pager 설정
            $('#workplaceCodeGrid').navGrid('#workplaceCodeGridPager', {
               add : false,
               del : false,
               edit : false,
               search : true,
               refresh : true,
               view: true
            });      
            
            // 사업장코드 Data 넣기
            $('#workplaceCodeGrid')
               .jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
               .trigger('reloadGrid');   
            
      }});   // 사업장코드 ajax 끝
      
   }
   
</script>
</head>

<body id="bodyTag">
 
    <div class="limiter">
      <div class="container-login100">
         <div class="wrap-login100 p-t-50 p-b-90">
            <form method="post" action="${pageContext.request.contextPath}/login.do?method=LogInCheck" class="login100-form validate-form flex-sb flex-w">
               <span class="login100-form-title p-b-51"> uniproduct </span>

               <div class="Code_div">
                  <input class="input100" type="text" id="companyCode" name="companyCode" placeholder="회사코드"> 
                  <span class="focus-input100"> </span>
               </div>
               
               <input type="button" value="+" id="companyCodeSearchButton" class="Code_search_b">

               <div class="Code_div">
                  <input class="input100" type="text" id="workplaceCode" name="workplaceCode" placeholder="사업장코드"> 
                  <span class="focus-input100"></span>
               </div>

               <input type="button" value="+" id="workplaceCodeSearchButton" class="Code_search_b">

               <div class="wrap-input100 validate-input m-b-16">
                  <input class="input100" type="text" name="userId" placeholder="사원ID"> 
                  <span class="focus-input100"></span>
               </div>

               <div class="wrap-input100 validate-input m-b-16">
                  <input class="input100" type="password" name="userPassWord" placeholder="비밀번호"> 
                  <span class="focus-input100"></span>
               </div>

               <div class="container-login100-form-btn m-t-17">
                  <button class="login100-form-btn">로그인</button>
               </div>

               <div id="message"></div>

            </form>
            
         </div>
      </div>
   </div>

   <!-- 코드 grid 보여주는 div -->
   <div id="dialog-form">
   
      <div id="companyCodeGridDiv">
         <table id="companyCodeGrid"></table>  <!-- 회사 코드  보여주는 grid 적용-->
      </div>
      
      <div id="workplaceCodeGridDiv">
         <table id="workplaceCodeGrid"></table>  <!-- 사업장 코드  보여주는 grid 적용-->
         <div id="workplaceCodeGridPager"></div>
      </div>
      
   </div>

   <!-- 에러 메시지 보여주는 div -->
   <div id="error-dialog" style="display: none;">
      <p id="errorMsg" style="font-size: 1.1em; color: black"></p>
   </div>
 
</body>
</html>