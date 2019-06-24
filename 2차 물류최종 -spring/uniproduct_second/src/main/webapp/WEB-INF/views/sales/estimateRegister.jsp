<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>견적 등록</title>
<style>

#newEstimateDate, #estimateAmountBox, #unitPriceOfEstimateBox, #sumPriceOfEstimateDiv {
	display: inline;
	width: 115px;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 30px;
	font-size: 20px;
	text-align : center;
	
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

var lastSelected_estimateDetailGrid_Id;    // 가장 나중에 선택한 견적상세 grid 의 행 id 
var lastSelected_estimateDetailGrid_RowValue;   // 가장 나중에 선택한 견적상세 grid 의 행 값 

var statusForInsertWorking = false;  // 현재 작업중인 견적 존재 여부

var estimateDate;   // 작업중인 견적에서 유효일자 값

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

	$("#newEstimateDate").datepicker({
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
		colNames : [ "거래처코드", "견적일자", "유효일자","견적담당자코드", "견적요청자", "비고", "status" ], 
		colModel : [ 
			{ name: "customerCode", width: "80", resizable: true, align: "center"} ,
			{ name: "estimateDate", width: "90", resizable: true, align: "center" } ,
			{ name: "effectiveDate", width: "90", resizable: true, align: "center"},
			{ name: "personCodeInCharge", width: "100", resizable: true, align: "center"} ,
			{ name: "estimateRequester", width: "90", resizable: true, align: "center", editable: true } ,
			{ name: "description", width: "150", resizable: true, align: "center", editable: true } ,
			{ name: "status", width: "100", align: "center", editable: false}

		], 
		caption : '견적', 
		sortname : 'estimateNo', 
		multiselect : false, 
		multiboxonly : false,
		viewrecords : true, 
		rownumWidth : 30, 
		height : 50, 
		width : 900,
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
		
		onCellSelect : function(rowid, iCol, cellcontent, e) {
		
            if(iCol == 1) {  // 거래처 cell 클릭
            	showCodeDialog(this, "estimateGrid", rowid , iCol , "CL-01","거래처 코드 검색");
                             //grid,     gridName,  rowid,  iCol,   divisionCodeNo,  title
            
            } 
					
		},
		
	
	}); // 견적 그리드 끝
	

	// 견적 상세 그리드 시작
	$('#estimateDetailGrid').jqGrid({ 
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "품목코드", "품목명", "단위", "납기일", "견적수량", "견적단가", "합계액", "비고", "status", "beforeStatus"], 
		colModel : [ 
			{ name: "itemCode", width: "70", resizable: true, align: "center"} ,
			{ name: "itemName", width: "150", resizable: true, align: "center"} ,
			{ name: "unitOfEstimate", width: "40", resizable: true, align: "center"} ,
			{ name: "dueDateOfEstimate", width: "70", resizable: true, align: "center", editable: true,
//				  formatter: 'date', 
//				  formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' },
				  edittype: 'text', 
		          editoptions: { size: 12, maxlengh: 12, 
						dataInit: function (element) { 
							$(element).datepicker({ 
								minDate : $('#newEstimateDate').val() ,
								changeMonth: true, 
								numberOfMonths: 1, 
								onClose: function(dateText, datepicker) {
									
									$('#estimateDetailGrid').editCell(1, 5 ,false);   // iRow,iCol, true/false
																											// $(this) .. => 안됨, 여기서 this 는 inputField 를 의미

									$(this).editCell(lastSelected_estimateDetailGrid_Id, 4 , false);     // iRow,iCol, true/false
								}
		                  })}
		          }, 
		          editrules: { date: true } 
			} ,
			{ name: "estimateAmount", width: "70", resizable: true, align: "center",
				formatter:'integer',formatoptions: { defaultValue:'0', thousandsSeparator: ',' }
	        } ,
			{ name: "unitPriceOfEstimate", width: "80", resizable: true, align: "center", 
				formatter:'integer',formatoptions: { defaultValue:'0' ,thousandsSeparator: ',' }
	        } ,
			{ name: "sumPriceOfEstimate", width: "80", resizable: true, align: "center", 
			        formatter:'integer',formatoptions: { defaultValue:'0', thousandsSeparator: ',' }
	        } , 	
			{ name: "description", width: "100", resizable: true, align: "center", editable: true } ,
			{ name: "status", width: "80", resizable: true, align: "center" } ,
			{ name: "beforeStatus", width: "10", resizable: true, align: "center" , hidden: true } 

		], 
		caption : '견적상세', 
		sortname : 'estimateDetailNo', 
		multiselect : true, 
		multiboxonly : false,
		viewrecords : true, 
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
        		previousCellValue = null;	 // "" 이면 이전 값으로 돌리는 경우 setCell 함수가 안먹힘
        	} else {
        		previousCellValue = value;
        	}
		},
		
		afterSaveCell(rowid, cellname, value, iRow, iCol){

        	var status = $(this).getCell(rowid,"status");

        	if(status == 'DELETE') {
        	
        		alertError("사용자 에러", "삭제 예정인 행이었습니다 ^^ </br> 원래 값으로 돌릴께요");
    			$(this).setCell(rowid,cellname, previousCellValue);	

        	} else if(status == 'NORMAL') {
        		
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
		
		onCellSelect : function(rowid, iCol, cellcontent, e) {

			if( lastSelected_estimateDetailGrid_Id != rowid ){
				lastSelected_estimateDetailGrid_Id = rowid;
				lastSelected_estimateDetailGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
			}
		
			var status = $(this).getCell(rowid,"status");

            if(iCol == 2 || iCol == 3) {  // 품목코드 또는 품목명 cell 클릭
            	showCodeDialog(this , "estimateDetailGrid",  rowid , iCol , "IT-_I","완제품 및 반제품 검색");
            		
            } else if (iCol == 4) {  // 단위 cell 클릭
            	showCodeDialog(this ,"estimateDetailGrid",  rowid , iCol , "UT","단위 검색");

            } else if( iCol == 6 || iCol == 7 || iCol == 8 ) {
            	showInputDialog(this ,rowid);
            }
		}
	}); // 견적 상세 그리드 끝
}

function initEvent() {

	$('#estimateInsertButton').on("click", function() {
		
		if( statusForInsertWorking != true && $('#newEstimateDate').val() != '' ) {
			
			var nextRecordNum = Number($("#estimateGrid").getGridParam("records"))+1;
			
		
			var esdateCut=$('#newEstimateDate').val().split("-")
			var esdate=new Date(esdateCut[0],esdateCut[1],esdateCut[2]);
			esdate.setDate(esdate.getDate() + 7);
            
		    var month=esdate.getMonth() < 10 ? "0" + esdate.getMonth() : esdate.getMonth()			
			var efdate=esdate.getFullYear()+"-"+month+"-"+esdate.getDate()

			

			$('#estimateGrid').jqGrid('addRowData' , nextRecordNum, 
				{ "estimateDate": $('#newEstimateDate').val() , 
				  "effectiveDate": efdate,
					"personCodeInCharge" : "${sessionScope.empCode}",   // 담당자 코드에 현재 접속중인 사원의 사원코드 입력
					"status":"INSERT" }  );
			
			statusForInsertWorking = true ;
			
		} else if( $('#newEstimateDate').val() == '' ){
			alertError("사용자 에러","견적 일자를 입력하지 않았습니다");
		} else {
			alertError("사용자 에러","작업중인 견적이 있습니다");
		}
	});

	
	$('#estimateDetailInsertButton').on("click",function() {
		
		
		// 현재 작업중인 견적 그리드의 행 값
		var estimateValue  = $('#estimateGrid').jqGrid('getRowData', 1);  

		if( statusForInsertWorking == false ) {
			alertError("사용자 에러","새로운 견적을 먼저 등록하세요");
		} else if(estimateValue.customerCode == "") {
			alertError("사용자 에러","거래처 코드를 먼저 등록하세요");
		} else if(estimateValue.effectiveDate == "") {
			alertError("사용자 에러","견적 유효일자를 먼저 입력하세요");
		} else {
			var newRowNum = $('#estimateDetailGrid').jqGrid('getDataIDs').length+1;
			
			$('#estimateDetailGrid').addRowData(
					newRowNum, { "status":"INSERT" });	
		}
	});
	
	
	$('#estimateDetailDeleteButton').on("click",function() {
		
		var checkedDeleteList = $('#estimateDetailGrid').jqGrid('getGridParam','selarrrow'); 

		$(checkedDeleteList).each( function(index, chekedRowId) {
			var rowObject = $('#estimateDetailGrid').getRowData(chekedRowId); // 행의 row 값 정보 객체
        	$("#estimateDetailGrid").delRowData(chekedRowId);
		});

	});
	
	
	$('#batchSaveButton').on("click",function() {
		
		var checkedDeleteList = $('#estimateDetailGrid').jqGrid('getGridParam','selarrrow'); 
		
		$(checkedDeleteList).each( function(index, chekedRowId) {

			$("#estimateDetailGrid").delRowData(chekedRowId);

		});
		
		
		if(Number($("#estimateDetailGrid").getGridParam("records")) == 0) {
			alertError("사용자 에러", "견적 상세가 입력되지 않았습니다");
			return;
		}
		
		
		var rowIdList =  $('#estimateDetailGrid').jqGrid('getDataIDs');
		
		// 사용자 유효성 검사 
		$(rowIdList).each( function(index, rowId) {
			var rowObject = $('#estimateDetailGrid').getRowData(rowId); // 행의 row 값 정보 객체

			if( rowObject.itemCode == "" ) {
				alertError("사용자 에러", "품목코드를 입력하지 않은 행이 있습니다 </br> 저장 목록에서 제외합니다");					
			} else if(rowObject.unitOfEstimate == "" ) {
				alertError("사용자 에러", "단위를 입력하지 않은 행이 있습니다 </br> 저장 목록에서 제외합니다");
			} else if(rowObject.dueDateOfEstimate == ""){
				alertError("사용자 에러", "납기일을 입력하지 않은 행이 있습니다 </br> 저장 목록에서 제외합니다");
			} else if(rowObject.estimateAmount == "0" || rowObject.unitPriceOfEstimate == "0") {
				alertError("사용자 에러", "견적수량/견적단가를 입력하지 않은 행이 있습니다 </br> 저장 목록에서 제외합니다");
			} else {
				resultList.push(rowObject);	
			}
			
		});
				
		var newEstimateRowValue = $('#estimateGrid').getRowData(1);
		var newEstimateDetaillist = $('#estimateDetailGrid').getRowData()
		
		// 견적 Bean 이 될 견적정보 JS 객체에 estimateDetailBeanList 멤버속성 지정
		newEstimateRowValue.estimateDetailTOList = newEstimateDetaillist; 
		
		var confirmMsg = "견적일자 " + $('#newEstimateDate').val() 
			+ " , 견적상세 " + Number($("#estimateDetailGrid").getGridParam("records")) 
			+ "건을 추가합니다. \r\n계속하시겠습니까?"  ; 
		
		if( confirm(confirmMsg) ) {
			
			$.ajax({ 
				type : 'POST',
				url : '${pageContext.request.contextPath}/sales/addNewEstimate.do' ,
				async :false,
				data : {
					method : 'addNewEstimate', 
					estimateDate : $('#newEstimateDate').val() ,
					newEstimateInfo : JSON.stringify(newEstimateRowValue) 
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					
					console.log(dataSet);
					
					var resultMsg = 
						"< 견적 등록 내역 >   <br/><br/>"
						+ "새로운 견적번호 : " + dataSet.result.newEstimateNo + "</br></br>"
						+ "견적상세번호 : " + dataSet.result.INSERT  + "</br></br>"
						+ "위와 같이 작업이 처리되었습니다";
						
					alertError("성공", resultMsg);
				}
			});  	
			
		}
		
		resultList = [];   // 초기화
		statusForInsertWorking = false;
		$('#estimateGrid').jqGrid('clearGridData');  // 견적그리드 데이터 비우기
		$('#estimateDetailGrid').jqGrid('clearGridData');  // 견적상세그리드 데이터 비우기


	});	

	
}


function showCodeDialog(grid, gridName, rowid, iCol, divisionCodeNo, title){
	
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
            postData: { 
            		method: "findDetailCodeList" ,
            		divisionCode: divisionCodeNo   
            },
            jsonReader: { 
            	root: "detailCodeList" 
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
				
				if(codeUseCheck != 'N' && codeUseCheck != 'n') {
					
					switch(gridName) {
					
						case "estimateGrid" :
							
							if(iCol == 1) {  // 견적에서 사업장 코드 셀 클릭시
								$(grid).setCell(rowid, iCol, detailCode);	
							} 

						case "estimateDetailGrid" : 
							
							if(iCol == 2 || iCol == 3) {  // 견적 상세에서 품목코드, 품목명 셀 클릭시
								
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
									$(grid).setCell(rowid, 2, detailCode);					
									$(grid).setCell(rowid, 3, detailName);	
								}

							} else if(iCol == 4) {
								$(grid).setCell(rowid, iCol, detailCode);	
							}

						default : 
					}
					
					$("#codeDialog").dialog("close");	

				} else {
					alertError("사용자 에러", "사용 가능한 코드가 아닙니다");		
				}
			}
		});
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
		autoOpen : false,  // 자동으로 열리지 않게
		width : 250,
		height : 250,
		modal : true,   // 폼 외부 클릭 못하게
		buttons : {  // 버튼 이벤트 적용
			"확인" : function() {
	
				$(grid).setCell(rowid, "estimateAmount", $('#estimateAmountBox').val() );
				$(grid).setCell(rowid, "unitPriceOfEstimate", $('#unitPriceOfEstimateBox').val() );
				$(grid).setCell(rowid, "sumPriceOfEstimate", $('#sumPriceOfEstimateDiv').text() );
				
				$("#InputDialog").dialog("close");

			},
			"취소" : function() {
				$("#InputDialog").dialog("close");
			}
		}
	});
	
	$("#InputDialog").dialog("open");  // 입력 창 열기

}

</script>
</head>
<body>

<fieldset style="display: inline; width: 240px; height: 60px">
<legend>견적 등록 </legend>
	<label for="newEstimateDate" style="display: inline-block ; font-size: 20px; margin-right: 5px">견적일자</label>
	<input type="text" id="newEstimateDate" style="display: inline-block ;"/>
</fieldset>

    <input type="button" value="견적추가" id="estimateInsertButton" />
 	<input type="button" value="일괄저장" id="batchSaveButton" />
 
<table id="estimateGrid" ></table>

<input type="button" value="견적상세추가" id="estimateDetailInsertButton" />
<input type="button" value="선택한상세삭제" id="estimateDetailDeleteButton" />

<table id="estimateDetailGrid" ></table>

<div id="InputDialog">
	<div>
		<label for="estimateAmountBox" style="font-size: 20px; margin-right: 10px">견적수량</label>
			<input type="text" id="estimateAmountBox"/> <br/>
		<label for="unitPriceOfEstimateBox" style="font-size: 20px; margin-right: 10px">견적단가</label>
			<input type="text" id="unitPriceOfEstimateBox" readonly/> <br/>
		<label for="unitPriceOfEstimateBox" style="font-size: 20px; margin-right: 10px">합계액 : </label>
			<div id="sumPriceOfEstimateDiv"></div>
	</div>
</div>

<div id="codeDialog" >
	<table id="codeGrid"></table>
</div>

</body>
</html>

