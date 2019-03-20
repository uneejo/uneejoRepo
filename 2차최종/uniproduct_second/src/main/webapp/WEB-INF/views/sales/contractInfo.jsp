<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수주 조회/수정</title>
<style>

#startDatePicker, #endDatePicker{
	display: inline;
	width: 125px;
	padding-left: 1%;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 30px;
	font-size: 20px;
}


#searchCustomerBox {
	display: inline;
	width: 150px;
	padding-left: 1%;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 30px;
	font-size: 18px;
}

.ui-datepicker{
	z-index: 9999 !important;
}

.ui-dialog { 
	z-index: 9999 !important ; 
	font-size:12px;
}

</style>
<script>

var lastSelected_contractGrid_Id;   // 가장 나중에 선택한 수주 grid 의 행 id 
var lastSelected_contractGrid_RowValue;   // 가장 나중에 선택한 수주 grid 의 행 값 

var lastSelected_contractDetailGrid_Id;    // 가장 나중에 선택한 수주상세 grid 의 행 id 
var lastSelected_contractDetailGrid_RowValue;   // 가장 나중에 선택된 (수정되기 전) 수주상세 grid 의 행 값 

var gridRowJson;  // 모든 그리드의 통합 Json Data => 배열 형식 : [ { ... } , { ... } , { ... } , ... ]

var searchCondition;  // 기간 검색 : 'searchByDate' , 거래처 검색 : 'searchByCustomer' 값으로 설정됨

var previousCellValue;  // 수정 가능한 셀에서 수정 전의 셀 값 
var resultList = [];  // 최종적으로 컨트롤러로 보내는 JS 객체 배열 

$(document).ready(function() {
	
	$("input[type=button], input[type=submit]").button();   // jqueryUI Button 위젯 적용
	$( "input[type=radio]" ).checkboxradio();   // jqueryUI Checkboxradio 위젯 적용

	$.datepicker.setDefaults({
		dateFormat : 'yy-mm-dd',
		prevText : '이전 달',
		nextText : '다음 달',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
									'8월', '9월', '10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월',
											'7월', '8월', '9월', '10월', '11월', '12월' ],
		dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
		showMonthAfterYear : true,
		yearSuffix : '년'
	}); /*한글로 달력 보이기 위해서 */

	$("#startDatePicker").datepicker({
		//defaultDate : "+5d",
		changeMonth : true,
		numberOfMonths : 1,
		onClose : function(selectedDate) {
			$( "#endDatePicker" ).datepicker( "option", "minDate", selectedDate );
		}
	});		
	
	$("#endDatePicker").datepicker({
		changeMonth : true,
		numberOfMonths : 1
	});		
	
	$("#codeDialog").hide();
	$('#searchCustomerBox').hide();

	
	initGrid();
	initEvent();
	
});

function initGrid() {
	
	// 수주 그리드 시작
	$('#contractGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "수주일련번호", "견적일련번호", "수주유형분류", "거래처코드", "거래처명", "견적일자", "수주일자", 
			"수주요청자", "수주담당자명", "비고" , "contractType","personCodeInCharge"], 
		colModel : [ 
			{ name: "contractNo", width: "100", resizable: true, align: "center"} ,
			{ name: "estimateNo", width: "100", resizable: true, align: "center"} ,
			{ name: "contractTypeName", width: "100", resizable: true, align: "center"} ,
			{ name: "customerCode", width: "80", resizable: true, align: "center", hidden : true } ,
			{ name: "customerName", width: "120", resizable: true, align: "center"} ,
			{ name: "estimateDate", width: "90", resizable: true, align: "center", 
				  formatter: 'date', 
				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d', defaultValue:null }  } ,
			{ name: "contractDate", width: "90", resizable: true, align: "center", 
				  formatter: 'date', 
				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d', defaultValue:null }  } ,
			{ name: "contractRequester", width: "90", resizable: true, align: "center" } ,
			{ name: "empNameInCharge", width: "100", resizable: true, align: "center" } ,
			{ name: "description", width: "150", resizable: true, align: "center" } ,
			
			{ name: "contractType", width: "100", resizable: true, align: "center" , hidden : true },
			{ name: "personCodeInCharge", width: "100", resizable: true, align: "center" , hidden : true }

		], 
		caption : '수주', 
		sortname : 'contractNo', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : true, 
		rownumWidth : 30, 
		height : 100, 
		width : 1000,
		autowidth : true, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,   // -1 : 모든 로우 한번에 표시, 그런데 잘 안먹히는 경우 많음
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
		pager : '#estimateGridPager',
		
        onSelectRow: function(rowid) {
			
			if( lastSelected_contractGrid_Id != rowid ){
				lastSelected_contractGrid_Id = rowid;
				lastSelected_contractGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
			
			showContractDetailGrid();
			
		}

	}); // 수주 그리드 끝
	
	$('#contractGrid').navGrid("#contractGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
	
	

	// 수주상세 그리드 시작
	$('#contractDetailGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "수주상세일련번호", "수주일련번호", "품목코드", "품목명", "단위", "납기일", "수주수량", "수주단가", "합계액" , 
			"MPS 적용상태", "납품여부" , "비고", "status" , "beforeStatus" ], 
		colModel : [ 
			{ name: "contractDetailNo", width: "120", resizable: true, align: "center"} ,
			{ name: "contractNo", width: "100", resizable: true, align: "center" , hidden : true } ,
			{ name: "itemCode", width: "70", resizable: true, align: "center" } ,
			{ name: "itemName", width: "150", resizable: true, align: "center"} ,
			{ name: "unitOfContract", width: "40", resizable: true, align: "center"} ,
			{ name: "dueDateOfContract", width: "90", resizable: true, align: "center", 
				  formatter: 'date', 
				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d', defaultValue:null }  } ,
			{ name: "contractAmount", width: "70", resizable: true, align: "center",
				formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } ,
			{ name: "unitPriceOfContract", width: "80", resizable: true, align: "center", 
				formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } ,
			{ name: "sumPriceOfContract", width: "80", resizable: true, align: "center", 
			        formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } , 
	        { name: "mpsApplyStatus", width: "100", resizable: true, align: "center" } ,
	        { name: "deliveryStatus", width: "100", resizable: true, align: "center" } ,
			{ name: "description", width: "100", resizable: true, align: "center" } ,
			{ name: "status", width: "100", resizable: true, align: "center" , hidden : true } ,
			{ name: "beforeStatus", width: "100", resizable: true, align: "center", hidden : true } 
		], 
		caption : '수주상세', 
		sortname : 'contractDetailNo', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : true, 
		rownumWidth : 30, 
		height : 100, 
		width : 1000,
		autowidth : true, 
		shrinkToFit : false, 
		cellEdit : false,
		rowNum : 50,  
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
		pager : '#contractDetailGridPager',
		
        onSelectRow: function(rowid) {
			
			if( lastSelected_contractDetailGrid_Id != rowid ){
				lastSelected_contracDetailGrid_Id = rowid;
				lastSelected_contracDetailGrid_RowValue = $(this).jqGrid('getRowData', id); 
			}
			
		}

	}); // 수주상세 그리드 끝
	
	$('#contractDetailGrid').navGrid("#contractDetailGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
	
}

function initEvent() {
	
	$(':input:radio[name=searchCondition]').on("click", function() {
		
		searchCondition = $(':input:radio[name=searchCondition]:checked').val();
		
		if( searchCondition == 'searchByDate' ) {
			
			$('#searchCustomerBox').hide();
			$('#startDatePicker').show();
			$('#endDatePicker').show();
			
		} else if(  searchCondition == 'searchByCustomer'  ) {

			$('#searchCustomerBox').show();
			$('#startDatePicker').hide();
			$('#endDatePicker').hide();

		}
	
	});
	
	
	$('#searchCustomerBox').on("click", function() {
		
		showCodeDialogForInput(this, 'CL-01', '거래처 검색');
	
	});
	
	
	$('#contactSearchButton').on("click", function() {

		showContractGrid();
		
	});
	

	$('#pdfOpenButton').on("click", function() {
		
        window.open(
        		"${pageContext.request.contextPath}/base/report.html?method=contractReport&orderDraftNo=" + lastSelected_contractGrid_RowValue.contractNo, 
        		"window", "width=1600, height=1000");
        
	});
	
	
	
}


function showContractGrid() {
	
	
	searchCondition = $(':input:radio[name=searchCondition]:checked').val();
	
	if( searchCondition == null ) {
		alertError("사용자 에러" , "검색 조건을 먼저 선택하세요")
		return; 
		
	} else if ( searchCondition == 'searchByDate' ) {
		
		if( $('#startDatePicker').val() == '시작일' || $('#startDatePicker').val() == '종료일' ) {
			alertError("사용자 에러" , "검색 시작일과 종료일을 입력하세요")
			return; 
						
		}
		
	} else if ( searchCondition == 'searchByCustomer' ) {
		
		if( $('#searchCustomerBox').val() == '거래처 검색' ) {
			alertError("사용자 에러" , "검색할 거래처를 먼저 선택하세요")
			return; 
			
		}
		
	}

	// 초기화
	$('#contractGrid').jqGrid('clearGridData');
	$('#contractDetailGrid').jqGrid('clearGridData');
	lastSelected_contractGrid_Id = "";
	
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/sales/searchContract.do' ,
		async :false,
		data : {
			method : 'searchContract', 
			searchCondition : $(':input:radio[name=searchCondition]:checked').val() ,
			startDate : $('#startDatePicker').val(),
			endDate : $('#endDatePicker').val(),
			customerCode : $('#customerCodeBox').val()
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) {
			console.log(dataSet);
			
			gridRowJson = dataSet.gridRowJson;
			
			if(gridRowJson.length != 0) {

				// 수주 Data 넣기
				$('#contractGrid')
					.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
					.trigger('reloadGrid');
				
				
				
			} else {
				alertError("ㅜㅜ" , "검색된 결과가 없습니다");
				
			}
			
			
		}
	});  
	
}

function showContractDetailGrid() {
	
	$('#contractDetailGrid').jqGrid('clearGridData');

	// gridRowJson 배열 Data 중의 어떤 객체 ( obj ) 의 contractNo 가 
	// 가장 최근에 선택한 contractGrid 행의 contractNo 와 같으면
	
	$( gridRowJson ).each( function( index, obj )   {  // gridRowJson 의 전체 데이터에 대해 반복문 시작
		
		if( obj.contractNo == lastSelected_contractGrid_RowValue.contractNo ) {
			
			// obj 의 contractDetailTOList : 선택된 contractNo 에 해당하는 수주상세 Data 
			$('#contractDetailGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : obj.contractDetailTOList })
				.trigger('reloadGrid');
			
		} 
		
	});  // 반복문 끝	
	
	/*
	
	// contractGrid 를 클릭할 때마다 수주상세 Data 를 따로 불러오는 경우의 ajax
	
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/sales/searchContractDetail.do' ,
		async :false,
		data : {
			method : 'searchContractDetail', 
			contractNo : lastSelected_contractGrid_RowValue.contractNo
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) {
			console.log(dataSet);
			
			var gridRowJson = dataSet.gridRowJson;
			
			$('#contractDetailGrid').jqGrid('clearGridData');
			
			// 수주상세 Data 넣기
			$('#contractDetailGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
		}	
	});

	*/
	
}


function showCodeDialogForInput(source, divisionCodeNo, title){
	
	$("#codeDialog").dialog({
		title : '코드 검색' ,
		width:500,
		height:500,
		modal : true   // 폼 외부 클릭 못하게
	});
	
	$.jgrid.gridUnload("#codeGrid");
	
	$("#codeGrid").jqGrid({
		
            url:"${pageContext.request.contextPath}/base/codeList.do",
            datatype: "json",
            jsonReader: { root: "detailCodeList" },
            postData: { 
            		method: "findDetailCodeList" ,
            		divisionCode: divisionCodeNo   
            },
			colNames:[ '상세코드번호' , '상세코드이름' , '사용여부' ],
			colModel:[
				{ name : 'detailCode', width:100, align : "center",editable:false},
				{ name : 'detailCodeName', width:100, align : "center", editable:false},
				{ name : 'codeUseCheck', width:100, align : "center",editable:false},
			],
			width: 450,
			height: 300,
			caption: title,
			align: "center",
			viewrecords:true,
			rownumbers: true,
			onSelectRow: function(id) {
				
				var detailCode=$("#codeGrid").getCell(id, 1);
				var detailName=$("#codeGrid").getCell(id, 2);
				var codeUseCheck=$("#codeGrid").getCell(id, 3);
				
				if(codeUseCheck != 'n' && codeUseCheck != 'N') {
					
					$(source).val(detailName);
					$('#customerCodeBox').val(detailCode);
					
					$("#codeDialog").dialog("close");			

				} else {
					alertError("사용자 에러", "사용 가능한 코드가 아닙니다");
				}
			}
		});

}


</script>
</head>
<body>
	<fieldset style="display: inline;">
	    <legend>수주 검색 </legend>
    		<label for="radio-1">기간 검색</label>
    		<input type="radio" name="searchCondition" value="searchByDate" id="radio-1">
    		<label for="radio-2">거래처 검색</label>
    		<input type="radio" name="searchCondition" value="searchByCustomer" id="radio-2">
	</fieldset>
	
	<input type="text" value="시작일" id="startDatePicker" />
	<input type="text" value="종료일" id="endDatePicker" />
	<input type="text" value="거래처 검색" id="searchCustomerBox" />
	<input type="hidden" id="customerCodeBox" />
	
	<input type="button" value="수주 조회" id="contactSearchButton" />
	<input type="button" value="PDF 출력" id="pdfOpenButton" />
 
<table id="contractGrid" ></table>
<div id="contractGridPager"></div>

<table id="contractDetailGrid" ></table>
<div id="contractDetailGridPager"></div>

<div id="codeDialog">
	<table id="codeGrid"></table>
</div>

</body>
</html>