<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>견적 검색/수정</title>
<style>
#startDatePicker, #endDatePicker, #estimateAmountBox,
	#unitPriceOfEstimateBox, #sumPriceOfEstimateDiv {
	display: inline;
	width: 115px;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 30px;
	font-size: 20px;
	text-align: center;
}

.ui-datepicker {
	z-index: 9999 !important;
}

.ui-dialog {
	z-index: 9999 !important;
	font-size: 12px;
}
</style>
<script>

var lastSelected_estimateGrid_Id;   // 가장 나중에 선택한 견적 grid 의 행 id 
var lastSelected_estimateGrid_RowValue;   // 가장 나중에 선택한 견적 grid 의 행 값 

var lastSelected_estimateDetailGrid_Id;    // 가장 나중에 선택한 견적상세 grid 의 행 id 
var lastSelected_estimateDetailGrid_RowValue;   // 가장 나중에 선택된 (수정되기 전) 견적상세 grid 의 행 값 

var gridRowJson;  // 모든 그리드의 통합 Json Data => 배열 형식 : [ { ... } , { ... } , { ... } , ... ]

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

	$("#InputDialog").hide();
	
	initGrid();
	initEvent();
	
});

function initGrid() {
	
	// 견적 그리드 시작
	$('#estimateGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "견적일련번호", "거래처코드", "견적일자", "수주여부", "견적요청자", "유효일자","견적담당자코드", "비고", "status"], 
		colModel : [ 
			{ name: "estimateNo", width: "100", resizable: true, align: "center"} ,
			{ name: "customerCode", width: "80", resizable: true, align: "center"} ,
			{ name: "estimateDate", width: "90", resizable: true, align: "center", 
				  formatter: 'date', 
				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d', defaultValue:null }  } ,
			{ name: "contractStatus", width: "60", resizable: true, align: "center" } ,
			{ name: "estimateRequester", width: "90", resizable: true, align: "center" } ,
			{ name: "effectiveDate", width: "90", resizable: true, align: "center",
				  formatter: 'date', 
				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d', defaultValue:null } } ,
			{ name: "personCodeInCharge", width: "100", resizable: true, align: "center" } ,
			{ name: "description", width: "150", resizable: true, align: "center", editable: true },
			{ name: "status", width: "100", editable: false, hidden : true }
		], 
		caption : '견적', 
		sortname : 'estimateNo', 
		multiselect : true, 
		multiboxonly : false,
		viewrecords : true, 
		rownumWidth : 30, 
		height : 100, 
		width : 1000,
		autowidth : false, 
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
        onSelectRow: function(id) {
			
			if( lastSelected_estimateGrid_Id != id ){
				lastSelected_estimateGrid_Id = id;
				lastSelected_estimateGrid_RowValue = $(this).jqGrid('getRowData', id); 
			}
			
			showEstimateDetailGrid();
			
		}

	}); // 견적 그리드 끝
	
	$('#estimateGrid').navGrid("#estimateGridPager", {
		add : false,
		del : false,
		edit : false,
		search : true,
		refresh : true,
		view: true
	});
	
	// 견적 상세 그리드 시작
	$('#estimateDetailGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "삭제", "견적상세일련번호", "품목코드", "품목명", "단위", "납기일", 
			"견적수량", "견적단가", "합계액", "비고", "status", "estimateNo", "beforeStatus"], 
		colModel : [ 		
			{ name: "check", width: "50", resizable: true, align: "center" ,
				formatter : function (celvalue, icol, rowObj) {
				     var chk = "<input type='checkbox' name='chk' />";     
				     return chk; 
				}
			},
			{ name: "estimateDetailNo", width: "110", resizable: true, align: "center"} ,
			{ name: "itemCode", width: "70", resizable: true, align: "center" } ,
			{ name: "itemName", width: "150", resizable: true, align: "center"} ,
			{ name: "unitOfEstimate", width: "40", resizable: true, align: "center"} ,
			{ name: "dueDateOfEstimate", width: "70", resizable: true, align: "center", editable: true,
//				  formatter: 'date',   => 주석 처리 : 여기 지정되면 사용자가 값을 미입력시 걸러주지 못함
//				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' },  
				  edittype: 'text', 
		          editoptions: { size: 12, maxlengh: 12, 
						dataInit: function (element) { 
							$(element).datepicker({ 
								minDate : lastSelected_estimateGrid_RowValue.estimateDate ,
								changeMonth: true, 
								numberOfMonths: 1, 
								onClose: function(dateText, datepicker) {
									$('#estimateDetailGrid').editCell(lastSelected_estimateDetailGrid_Id, 6, false); 
								}
		                  })}
		          }, 
		          editrules: { date: true } 
			} ,
			{ name: "estimateAmount", width: "70", resizable: true, align: "center",
				formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } ,
			{ name: "unitPriceOfEstimate", width: "80", resizable: true, align: "center", 
				formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } ,
			{ name: "sumPriceOfEstimate", width: "80", resizable: true, align: "center", 
			        formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }
	        } , 	
	        { name: "description", width: "100", resizable: true, align: "center", editable: true } ,
			{ name: "status", width: "80", resizable: true, align: "center" } ,
			{ name: "estimateNo", width: "100", resizable: true, align: "center" } ,
			{ name: "beforeStatus", width: "10", resizable: true, align: "center" , hidden: true} 

		], 
		caption : '견적상세', 
		sortname : 'estimateDetailNo', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : false, 
		rownumWidth : 30, 
		height : 100, 
		width : 1000,
		autowidth : false, 
		shrinkToFit : false, 
		cellEdit : true,
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
		
		beforeEditCell(rowid, cellname, value, iRow, iCol){

			if( lastSelected_estimateDetailGrid_Id != rowid ){
				lastSelected_estimateDetailGrid_Id = rowid;
				lastSelected_estimateDetailGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
			
        	if(value == null || value == "" ) {
        		previousCellValue = " ";	
        	} else {
        		previousCellValue = value;
        	}
		},

		afterSaveCell(rowid, cellname, value, iRow, iCol){

        	var status = $(this).getCell(rowid,"status");
        	
        	if(status == 'DELETE') {
        	
        		alertError("사용자 에러", "삭제 예정인 행이었습니다 ^^ </br> 원래 값으로 돌릴께요");
    			$(this).setCell(rowid,cellname, previousCellValue);	

        	}  else if(status == 'NORMAL') {
        		
        		if( previousCellValue != value ) {
    				$(this).setCell(rowid,"status", "UPDATE");
        		}
        		
        	}
		},
		
		beforeSelectRow : function( rowid, event ) {
			
        	var beforeStatus = $(this).getCell(rowid,"beforeStatus");
        	var currentStatus = $(this).getCell(rowid,"status");

        	if($(event.target).is(":checkbox")) {
            	
                if($(event.target).is(":checked")) {
        			$(this).setCell(rowid,"status", "DELETE");
        			$(this).setCell(rowid,"beforeStatus", currentStatus);
                } else {
        			$(this).setCell(rowid,"status", beforeStatus);
                }
                
            }
		},
		
		onSelectRow: function(rowid) {   
	
			if( lastSelected_estimateDetailGrid_Id != rowid ){
				lastSelected_estimateDetailGrid_Id = rowid;
				lastSelected_estimateDetailGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
			
		},
		
		onCellSelect : function(rowid, iCol, previousCellValue, e) {
			
			if( lastSelected_estimateDetailGrid_Id != rowid ){
				lastSelected_estimateDetailGrid_Id = rowid;
				lastSelected_estimateDetailGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}

			var status = $(this).getCell(rowid,"status");
		
			if(status == "NORMAL" ){

                if(iCol == 3 || iCol == 4) {  // 품목코드, 품목명 cell 클릭
                	
                	showCodeDialog(this ,rowid , iCol , "IT-_I","완제품 및 반제품 검색");
                
                } else if (iCol == 5) {  // 단위 cell 클릭
                	
                	showCodeDialog(this ,rowid , iCol , "UT","단위 검색");

                } else if( iCol == 7 || iCol == 8 || iCol == 9 ) {  
                	
                	showInputDialog(this ,rowid);
                	
                } 

            } else if ( ( status == "INSERT" || status == "UPDATE" )  ) {

                if(iCol == 3) {  // 품목코드 cell 클릭
                	
                	showCodeDialog(this ,rowid , iCol , "IT-_I","완제품 및 반제품 검색");
                		
                } else if (iCol == 5) {  // 단위 cell 클릭
                	
                	showCodeDialog(this ,rowid , iCol , "UT","단위 검색");

                } else if(iCol == 7 || iCol == 8 || iCol == 9 ) {
                	
                	showInputDialog(this ,rowid);
                	
                }
                
            } 
		}
	}); // 견적 상세 그리드 끝
}

function initEvent() {
	
	$('#estimateSearchButton').on("click",function() {

		showEstimateGrid();
		
	});
	
	$('#estimateDetailInsertButton').on("click",function() {

		//가장 나중에 선택된 (수정되기 전) 견적 grid 의 행의 수주여부
		var contractStatus = lastSelected_estimateGrid_RowValue.contractStatus; 
		
		if(contractStatus != "") {

			var errorMsg = ( contractStatus == 'Y' ) ? "이미 수주된 견적입니다." : "이미 취소된 견적입니다."
			alertError("사용자 에러",errorMsg);
			return;
			
		}
		
		if(lastSelected_estimateGrid_Id != null ) {
		
			var newRowNum = $('#estimateDetailGrid').jqGrid('getDataIDs').length+1;  // 새로운 행 넘버
			
			$('#estimateDetailGrid').addRowData(
					newRowNum, 
					{ "estimateDetailNo" : "저장시 지정됨" , 
						"estimateNo" : lastSelected_estimateGrid_RowValue.estimateNo , 
						"status" : "INSERT" } );
		
		} else  {
			
			alertError("사용자 에러","조회할 견적을 먼저 선택하세요");
		
		} 

	});

	
	$('#estimateDetailDeleteButton').on("click",function() {
	
		//가장 나중에 선택된 (수정되기 전) 견적 grid 의 행의 수주여부
		var contractStatus = lastSelected_estimateGrid_RowValue.contractStatus; 
		
		if(contractStatus != "") {

			var errorMsg = ( contractStatus == 'Y' ) ? "이미 수주된 견적입니다." : "이미 취소된 견적입니다."
			alertError("사용자 에러",errorMsg);
			return;
			
		}
		
		var rowIdList =  $('#estimateDetailGrid').jqGrid('getDataIDs');  // 견적상세 그리드의 전체 행 ID 배열
		
		var deleteCount = 0;

		$(rowIdList).each( function(index, rowId) {   // 전체 행에 대해 반복문 시작

			var rowObject = $('#estimateDetailGrid').getRowData(rowId); // 한 행의 row 값 정보 객체
			
			var status = rowObject.status;
			
			if(status == 'DELETE' && rowObject.estimateDetailNo != '저장시 지정됨' ) {
				
				resultList.push(rowObject);
				deleteCount++;
				
			} else if(status == 'DELETE' && rowObject.estimateDetailNo == '저장시 지정됨' ) {
				
				$('#estimateDetailGrid').delRowData(rowId);
				
			}
			
		});
				
		var confirmMsg = deleteCount + "개의 견적 상세를 삭제합니다. 계속하시겠습니까?";
		
		if( resultList.length != 0 && confirm(confirmMsg) ) {
			
			$.ajax({ 
				type : 'POST',
				url : '${pageContext.request.contextPath}/sales/batchEstimateDetailListProcess.do' ,
				data : {
					method : 'batchListProcess', 
					batchList : JSON.stringify(resultList),
					estimateNo : lastSelected_estimateGrid_RowValue.estimateNo
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					
					console.log(dataSet);

					var resultMsg = "견적상세 </br>" + dataSet.result.DELETE + "</br>총 " + dataSet.result.DELETE.length + " 개가 삭제되었습니다";
					alertError("성공", resultMsg);

				}
			});
			
		} else if(resultList.length != 0 && !confirm(confirmMsg)) {
			
			alertError("^^", "취소되었습니다");
			
		} 

		resultList = [];   // 초기화
		
		// 견적, 견적상세 그리드 새로고침
		showEstimateGrid();
	});


	
	$('#estimateDeleteButton').on("click",function() {
		
		//가장 나중에 선택된 (수정되기 전) 견적 grid 의 행의 수주여부
		var contractStatus = lastSelected_estimateGrid_RowValue.contractStatus; 
		
		if(contractStatus != "") {

			var errorMsg = ( contractStatus == 'Y' ) ? "이미 수주된 견적입니다." : "이미 취소된 견적입니다."
			alertError("사용자 에러",errorMsg);
			return;
			
		}
		
		var rowIdList =  $('#estimateGrid').jqGrid('getDataIDs');  // 견적상세 그리드의 전체 행 ID 배열
		
		var deleteCount = 0;

		$(rowIdList).each( function(index, rowId) {   // 전체 행에 대해 반복문 시작

			var rowObject = $('#estimateGrid').getRowData(rowId); // 한 행의 row 값 정보 객체
			
			var status = rowObject.status;
			
			if(status == 'DELETE' && contractStatus!='Y') {
				
				resultList.push(rowObject);
				deleteCount++;
				
			} 
			
		});
				
		var confirmMsg = deleteCount + "개의 견적을 삭제합니다. 계속하시겠습니까?";
		
		if( resultList.length != 0 && confirm(confirmMsg) ) {
			
			$.ajax({ 
				type : 'POST',
				url : '${pageContext.request.contextPath}/sales/batchEstimateProcess.do' ,
				data : {
					method : 'batchListProcess', 
					batchList : JSON.stringify(resultList),
					estimateNo : lastSelected_estimateGrid_RowValue.estimateNo
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					
					console.log(dataSet);

					var resultMsg = "견적상세 </br>" + dataSet.result.DELETE + "</br>총 " + dataSet.result.DELETE.length + " 개가 삭제되었습니다";
					alertError("성공", resultMsg);

				}
			});
			
		} else if(resultList.length != 0 && !confirm(confirmMsg)) {
			
			alertError("^^", "취소되었습니다");
			
		} 

		resultList = [];   // 초기화
		
		// 견적, 견적상세 그리드 새로고침
		showEstimateGrid();
	});

	

	
	
	$('#batchSaveButton').on("click",function() {

		//가장 나중에 선택된 (수정되기 전) 견적 grid 의 행의 수주여부
		var contractStatus = lastSelected_estimateGrid_RowValue.contractStatus; 
		
		if( contractStatus != "" ) {

			var errorMsg = ( contractStatus == 'Y' ) ? "이미 수주된 견적입니다." : "이미 취소된 견적입니다."
			alertError("사용자 에러",errorMsg);
			return;
			
		}
		
		var rowIdList =  $('#estimateDetailGrid').jqGrid('getDataIDs'); 
		
		var insertCount = 0;
		var updateCount = 0;
		var deleteCount = 0;

		var errorMsg = "< 제외 목록 > \r";  
		
		$(rowIdList).each( function(index, rowId) {   // 전체 행에 대해 반복문 시작
			
			var rowObject = $('#estimateDetailGrid').getRowData(rowId); // 행의 row 값 정보 객체
			var status = rowObject.status;
			
			if(status == 'INSERT' ) {
				
				// 에러 출력함수는 실제로 실행되지는 않음 ㅜㅜ => 그냥 잘못 입력된 행들을 필터링해주는 역할
				if( rowObject.itemCode == '' ) {
					errorMsg += ( rowId + "행 : 품목코드 미입력 \r" );
				} else if(rowObject.unitOfEstimate == '' ) {
					errorMsg += ( rowId + "행 : 단위 미입력 \r" );
				} else if(rowObject.dueDateOfEstimate == '' ){
					errorMsg += ( rowId + "행 : 납기일 미입력 \r" );
				} else if(rowObject.estimateAmount == "0" || rowObject.unitPriceOfEstimate == "0") {
					errorMsg += ( rowId + "행 : 견적수량/견적단가 미입력 \r" );
				} else {
					
					resultList.push(rowObject);	
					insertCount++;
					
				}

			} else if (status == 'UPDATE') {
				
				resultList.push(rowObject);
				updateCount++;
				
			} else if (status == 'DELETE') {
				
				if(rowObject.estimateDetailNo != '저장시 지정됨' ) {
					resultList.push(rowObject);
					deleteCount++;
				} else {
					$('#estimateDetailGrid').delRowData(rowId);
				}
				
			}
		});
		
		var confirmMsg = 
			( ( errorMsg == "< 제외 목록 > \r" ) ? "" : errorMsg + "\r" ) + 
			"< 가능한 작업 목록 > \r" +
			( ( insertCount != 0 ) ? insertCount + "개의 견적 상세 추가 \n" : "" )+
			( ( updateCount != 0 ) ? updateCount + "개의 견적 상세 수정 \n" : "" ) +
			( ( deleteCount != 0 ) ? deleteCount + "개의 견적 상세 삭제 \n" : ""  )+
			"\r위와 같이 작업합니다. 계속하시겠습니까?"

		var confirmStatus = "";
		
		if(resultList.length != 0) {
			
			confirmStatus = confirm(confirmMsg);
				
		}

		
		if(resultList.length != 0 && confirmStatus) {
			
			$.ajax({ 
				type : 'POST',
				url : '${pageContext.request.contextPath}/sales/batchEstimateDetailListProcess.do' ,
				async :false,
				data : {
					method : 'batchListProcess', 
					batchList : JSON.stringify(resultList),
					estimateNo : lastSelected_estimateGrid_RowValue.estimateNo
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {

					console.log(dataSet);
					var resultMsg = 
						"< 견적상세 작업 내역 >   <br/><br/>"
						+ "추가된 견적상세 : "
						+ ( ( dataSet.result.INSERT.length != 0 ) ? dataSet.result.INSERT : "없음" ) + "</br></br>"
						+ "수정된 견적상세 : " 
						+ ( ( dataSet.result.UPDATE.length != 0 ) ? dataSet.result.UPDATE : "없음" ) + "</br></br>"
						+ "삭제된 견적상세 : " 
						+ ( ( dataSet.result.DELETE.length != 0 ) ? dataSet.result.DELETE : "없음" ) + "</br></br>"
						+ "위와 같이 작업이 처리되었습니다";
						
					alertError("성공", resultMsg);
				}
			});  
			
		} else if(resultList.length != 0 && !confirmStatus) {
			
			alertError("^^", "취소되었습니다");
			
		} else if(resultList.length == 0) {
			
			alertError("^^", "추가/수정/삭제할 견적 상세가 없습니다");
		}

		resultList = [];   // 초기화
		
		// 견적, 견적상세 그리드 새로고침
		showEstimateGrid();
		
	});
	

	$('#pdfOpenButton').on("click", function() {
		
        window.open("${pageContext.request.contextPath}/base/report.html?method=estimateReport&orderDraftNo=" + lastSelected_estimateGrid_RowValue.estimateNo, "window", "width=1600, height=1000");
        
	});
	
	
}

function showEstimateGrid() {
		
	// 사용자 유효성 검사
	if( $(':input:radio[name=searchDateCondition]:checked').val() == undefined  ) {
		
		alertError('사용자 에러' , '견적 검색 조건을 먼저 선택하세요');
		return;
		
	} else if ( $("#startDatePicker").val() == '시작일' || $("#endDatePicker").val() == '종료일' ||  $("#endDatePicker").val() == '' ) {
		
		alertError('사용자 에러' , '견적 검색 일자를 모두 입력하세요');
		return;
		
	}
	
	// 초기화 
	$('#estimateGrid').jqGrid('clearGridData');
	$('#estimateDetailGrid').jqGrid('clearGridData');
	lastSelected_estimateGrid_Id = "";
	
	
	// ajax 시작
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/sales/searchEstimate.do' ,
		data : {
			method : 'searchEstimateInfo', 
			startDate : $("#startDatePicker").val() ,
			endDate : $("#endDatePicker").val() ,
			dateSearchCondition : $(':input:radio[name=searchDateCondition]:checked').val()
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) { 
			
			console.log(dataSet);
			gridRowJson = dataSet.gridRowJson;  // gridRowJson : 그리드에 넣을 json 형식의 data
			
			if( gridRowJson.length != 0 ) {

				// 견적Data 넣기
				$('#estimateGrid')
					.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
					.trigger('reloadGrid');
			
				// 견적상세 그리드 새로고침
				showEstimateDetailGrid();
				
			} else {
				alertError('ㅜㅜ','조회된 데이터가 없습니다');
				
			}
			
	}});  // ajax 끝
	
	
}


function showEstimateDetailGrid() {
	
	$('#estimateDetailGrid').jqGrid('clearGridData');
	
	// gridRowJson 배열 Data 중의 어떤 객체 ( obj ) 의 estimateNo 가 
	// 가장 최근에 선택한 estimateGrid 행의 estimateNo 와 같으면
	
	$( gridRowJson ).each( function( index, obj )   {  // gridRowJson 의 전체 데이터에 대해 반복문 시작
		
		if( obj.estimateNo == lastSelected_estimateGrid_RowValue.estimateNo ) {
			
			// obj 의 estimateDetailTOList : 선택된 estimateNo 에 해당하는 견적상세 Data 
			$('#estimateDetailGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : obj.estimateDetailTOList })
				.trigger('reloadGrid');
			
		} 
		
	});  // 반복문 끝	
	
	
	/*
	
	// estimateGrid 를 클릭할 때마다 견적상세 Data 를 따로 불러오는 경우의 ajax
	
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/sales/searchEstimate.do' ,
		data : {
			method : 'searchEstimateDetailInfo', 
			estimateNo : lastSelected_estimateGrid_RowValue.estimateNo
		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) { 

			console.log(dataSet);
			var gridRowJson = dataSet.gridRowJson;

			// 견적상세Data 넣기
			$('#estimateDetailGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
		}		
	});	
	
	*/
	
}


function showInputDialog(grid ,rowid) {
	
	var itemcode=$(grid).getCell(rowid, "itemCode");
	var estimateAmount = $(grid).getCell(rowid, "estimateAmount");
	var unitPriceOfEstimate = $(grid).getCell(rowid, "unitPriceOfEstimate");
	var sumPriceOfEstimate = $(grid).getCell(rowid, "sumPriceOfEstimate");

	$('#estimateAmountBox, #unitPriceOfEstimateBox').on("keyup", function() {
		var sum = $('#estimateAmountBox').val() * $('#unitPriceOfEstimateBox').val();
		
		$('#sumPriceOfEstimateDiv').text(sum);
	});
	
	
	
	$.ajax({
		type:'POST',
		url:'${pageContext.request.contextPath}/logisticsInfo/searchItemprice.do',
		data:{
			itemCode:itemcode,
			method:'searchItemPrice'
		},
		dataType:'json',
		success:function(dataSet){
			
			$itemprice=parseInt(dataSet.itemPrice);		
		    $estimatecount=$('#estimateAmountBox').val(parseInt(estimateAmount));
			$unitprice=$('#unitPriceOfEstimateBox').val($itemprice);
			$sumprice=$('#sumPriceOfEstimateDiv').text(sumPriceOfEstimate);
		
			
		}
	})
	
	$("#InputDialog").dialog({
		title : '견적수량 / 견적단가 입력',
		autoOpen : true,  
		width : 250,
		height : 250,
		modal : true,   // 폼 외부 클릭 못하게
		buttons : {  // 버튼 이벤트 적용
			"확인" : function() {
				
				$(grid).setCell(rowid, "estimateAmount", $('#estimateAmountBox').val() );
				$(grid).setCell(rowid, "unitPriceOfEstimate", $('#unitPriceOfEstimateBox').val() );
				$(grid).setCell(rowid, "sumPriceOfEstimate", $('#sumPriceOfEstimateDiv').text() );
				
				$("#InputDialog").dialog("close");
				checkRowChanged(lastSelected_estimateDetailGrid_RowValue, grid, rowid);					

			},
			"취소" : function() {
				$("#InputDialog").dialog("close");
			}
		}
	});
	
	
}


function showCodeDialog(grid, rowid, iCol, divisionCodeNo, title){
	$("#codeDialog").dialog({
		title : '코드 검색' ,
		width:500,
		height:500,
		modal : true   // 폼 외부 클릭 못하게
	});
	
	$.jgrid.gridUnload("#codeGrid");
	
	$("#codeGrid").jqGrid({
            url: "${pageContext.request.contextPath}/base/codeList.do",
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
					
					if(iCol == 3) {
						var ids = $(grid).getRowData();
						var errorStatus = false;
						$(ids).each(function(index, obj) {
							
							var itemCodeInList = obj.itemCode;
							
							if(detailCode == itemCodeInList) {
								alertError("사용자 에러","견적 상세에 이미 있는 품목입니다");
								errorStatus = true;
								return false;
							} 
						})
						
						if(!errorStatus) {
							$(grid).setCell(rowid, iCol, detailCode);					
							$(grid).setCell(rowid, iCol+1, detailName);	
						}
						
					} else if(iCol == 5) {
						$(grid).setCell(rowid, iCol, detailCode);
					}
					
					$("#codeDialog").dialog("close");
					checkRowChanged(lastSelected_estimateDetailGrid_RowValue, grid, rowid);					

					
				} else {
					alertError("사용자 에러", "사용 가능한 코드가 아닙니다");
				}
			}
		});

}

function checkRowChanged(previousRowValue, grid, rowid) {

	var nextRowValue = $(grid).jqGrid('getRowData', rowid);
	var edited = false;
	
	if(nextRowValue.status == 'NORMAL') {
		for(var key in previousRowValue) {
			if(nextRowValue[key] != previousRowValue[key]) {
				edited = true;
			}
		}		
	}
	
	if(edited) {
		$(grid).setCell(rowid, "status", "UPDATE");
	}

}

</script>
</head>
<body>
	<fieldset style="display: inline;">
		<legend>견적 검색 </legend>
		<label for="radio-1">견적일자</label> <input type="radio"
			name="searchDateCondition" value="estimateDate" id="radio-1">
		<label for="radio-2">유효일자</label> <input type="radio"
			name="searchDateCondition" value="effectiveDate" id="radio-2">
	</fieldset>

	<input type="text" value="시작일" id="startDatePicker" />
	<input type="text" value="종료일" id="endDatePicker" />
	<input type="button" value="견적조회" id="estimateSearchButton" />
	<input type="button" value="PDF 출력" id="pdfOpenButton" />


	<table id="estimateGrid"></table>
	<div id="estimateGridPager"></div>

	<input type="button" value="견적상세추가" id="estimateDetailInsertButton" />
	<input type="button" value="선택한상세삭제" id="estimateDetailDeleteButton" />
	<input type="button" value="일괄저장" id="batchSaveButton" />

	<table id="estimateDetailGrid"></table>
	<div id="estimateDetailGridPager"></div>

	<div id="InputDialog">
		<div>
			<label for="estimateAmountBox"
				style="font-size: 20px; margin-right: 10px">견적수량</label> <input
				type="text" id="estimateAmountBox" /> <br /> <label
				for="unitPriceOfEstimateBox"
				style="font-size: 20px; margin-right: 10px">견적단가</label> <input
				type="text" id="unitPriceOfEstimateBox" /> <br /> <label
				for="sumPriceOfEstimateDiv"
				style="font-size: 20px; margin-right: 10px">합계액 : </label>
			<div id="sumPriceOfEstimateDiv"></div>
		</div>
	</div>

	<div id="codeDialog">
		<table id="codeGrid"></table>
	</div>

</body>
</html>