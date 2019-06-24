<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수주 등록</title>
<style>

#startDatePicker, #endDatePicker {
	display: inline;
	width: 115px;
	padding-left: 1%;
	margin-bottom: 10px;
	transition: 0.6s;
	outline: none;
	height: 30px;
	font-size: 20px;
}

.ui-datepicker{
	z-index: 9999 !important;
}

.ui-dialog { 
	z-index: 9999 !important; 
	font-size:12px;
}

</style>
<script>

 var gridRowJson;
 var lastSelected_contractCandidateGrid_Id;
 var lastSelected_contractCandidateGrid_RowValue;
 var lastSelected_estimateDetailGrid_Id;
 var lastSelected_estimateDetailGrid_RowValue;
$(document).ready(function() {
	$("input[type=button]").button();   // jqueryUI Button 위젯 적용	
	
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
		
	})
	
	$("#endDatePicker").datepicker({
		changeMonth : true,
		numberOfMonths : 1
	})

$("#codeDialog").attr("style", "display:none");
	
	initGrid();
	initEvent();
	
	
	
});

function initGrid(){
	
	$('#contractCandidateGrid').jqGrid({
		mtype:'POST',
		datatype:'local',
		colNames:["견적일련번호","수주유형분류","거래처코드","견적일자","수주요청자","유효일자","견적담당자코드","비고"],
		colModel:[{name:"estimateNo"},
	               {name:"contractType"},
	               {name:"customerCode"},
	               {name:"estimateDate"},
	               {name:"contractRequester", editable:true},
	               {name:"effectiveDate"},
	               {name:"personCodeInCharge"},
	               {name:"description" , editable:true}
		],
		caption:'수주가능견적조회',
		sortname:'estimateNo',
		cellEdit:false,
		editurl :'clientArray',
		cellsubmit : 'clientArray',
		viewrecords : true, 
		multiselect:true,
		multiboxonly:true,
		scrollerbar:true,
		autoencode:true,
		resizable:true,
		loadtext:'로딩중...',
		cache:false,
		pager:'contractCandidateGridPager',
	
		onSelectRow: function(rowid,status) {   
			if( lastSelected_contractCandidateGrid_Id != rowid ){
				lastSelected_contractCandidateGrid_Id = rowid;
				lastSelected_contractCandidateGrid_RowValue = $(this).jqGrid('getRowData', rowid); 		
				}
				else{                                                          //구글링해서 찾은거!! multiselect 개별 선택 할 수 있게 하는 거
					$(this).jqGrid("resetSelection"); 
				     lastSelected_contractCandidateGrid_Id = undefined;
	                 status = false; }
        	
			showEstimateDetailGrid();
		},
		beforeSelectRow: function (rowId, e) {
			$(this).editCell(0, 0, false);
            $(this).jqGrid("resetSelection");
            return true;
        },
        onCellSelect : function(rowid, iCol, previousCellValue, e) { 
        	    
                   if(iCol == 2) {
				
                   	showCodeDialog(this ,rowid , iCol , "CT","수주 유형 분류")
                   
                   }
                   if(iCol == 5 || iCol == 8) {
       				
       				$(this).jqGrid('setGridParam',{ cellEdit : true });  // editable : true 인 셀 클릭시 cellEdit 변경

       			} else {
       				
       				$(this).jqGrid('setGridParam',{ cellEdit : false });
       				
       			}

        }

        	
    
	
	});
	
	
	$('#estimateDetailGrid').jqGrid({
		mtype : 'POST', 
		datatype : 'local',
		colNames : [ "견적상세일련번호", "품목코드", "품목명", "단위", "납기일", 
			          "견적수량", "견적단가", "합계액", "비고"], 
		colModel : [
			{ name: "estimateDetailNo", width: "110", resizable: true, align: "center"} ,
			{ name: "itemCode", width: "70", resizable: true, align: "center" } ,
			{ name: "itemName", width: "150", resizable: true, align: "center"} ,
			{ name: "unitOfEstimate", width: "40", resizable: true, align: "center"} ,
			{ name: "dueDateOfEstimate", width: "70", resizable: true, align: "center", editable: true},
            { name: "estimateAmount", width: "70", resizable: true, align: "center",
		       formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }},
         	{ name: "unitPriceOfEstimate", width: "80", resizable: true, align: "center", 
		      formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }},
            { name: "sumPriceOfEstimate", width: "80", resizable: true, align: "center", 
	        formatter:'integer',formatoptions: { defaultValue: '0', thousandsSeparator: ',' }},
	        { name: "description", width: "100", resizable: true, align: "center", editable: true } 

             ], 
             caption : '견적상세', 
     		 sortname : 'estimateDetailNo', 
     		 multiselect :true , 
     		autoencode : true, 
    		resizable : true,
    		loadtext : '로딩중...', 
    		emptyrecords : '데이터가 없습니다.', 
    		cache : false,
    		onSelectRow: function(rowid) {   
    			
    			if( lastSelected_estimateDetailGrid_Id != rowid ){
    				lastSelected_estimateDetailGrid_Id = rowid;
    				lastSelected_estimateDetailGrid_RowValue = $(this).jqGrid('getRowData', rowid); 
    			}			
    		} 

		
	});
	
	
	
	
	
	
	
}



function initEvent(){
	$("#contractCandidateSearchButton").on("click",function(){
		
		$('#contractCandidateGrid').jqGrid('clearGridData');
		$.ajax({
			url:'${pageContext.request.contextPath}/sales/searchEstimateInContractAvailable.do',
			data:{
				method:'searchEstimateInContractAvailable',
				startDate:$("#startDatePicker").val(),
			    endDate:$("#endDatePicker").val()
			},
			dataType:'json',
			cache:false,
			success:function(dataSet){
				console.log(dataSet);
				gridRowJson=dataSet.gridRowJson;
				if(gridRowJson!=0){
					$('#contractCandidateGrid').jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson}).trigger('reloadGrid');
				}
				else{
					alertError('데이터오류','조회된 데이터가 없습니다.');
				}
			}
		})
		

	})


	$('#contractBatchInsertButton').on("click", function() {
if(lastSelected_estimateDetailGrid_RowValue == undefined || lastSelected_estimateDetailGrid_RowValue == "" ) {
			
			alertError("사용자 에러", "수주 등록할 견적을 선택하세요");
			return;
			
		} else if(lastSelected_contractCandidateGrid_RowValue.contractType == "") {

			alertError("사용자 에러", "수주 유형을 선택하지 않았습니다");
			return;
			
		}
		
        var sel = $( "#estimateDetailGrid" ).jqGrid('getGridParam', "selarrrow" );  //체크한거 가져옴

		var confirmMsg = "견적번호 " + lastSelected_contractCandidateGrid_RowValue.estimateNo + " 을 수주 등록합니다. \r\n"
			+ "계속하겠습니까?";
	   
		 
		if( confirm(confirmMsg) ) {
		        estimateDetailList=new Array();
			for(var rowid in sel)
			{ estimateDetailList.push( $("#estimateDetailGrid").jqGrid('getRowData',parseInt(rowid)+1));
			}
		
			
		
			
			lastSelected_contractCandidateGrid_RowValue.contractDetailTOList = estimateDetailList;  // 컨트롤러에서 수주상세 리스트로 변환됨
			lastSelected_contractCandidateGrid_RowValue.estimateDetailTOList = estimateDetailList;  // 컨트롤러에서 견적상세 리스트로 변환됨
			
			
			// 오늘 일자 문자열 생성 : '2018-01-01' 형식
			var now = new Date();
			var today = now.getFullYear() + "-" +('0' + (now.getMonth() +1 )).slice(-2) + "-" + ('0' + now.getDate()).slice(-2);
		   
			$.ajax({ 
				type : 'POST',
				url : '${pageContext.request.contextPath}/sales/addNewContract.do' ,
				async :false,
				data : {
					method : 'addNewContract', 
					batchList : JSON.stringify(lastSelected_contractCandidateGrid_RowValue),
					personCodeInCharge : "${sessionScope.empCode}", 
					contractDate : 	today			
				},
				dataType : 'json', 
				cache : false, 
				success : function(dataSet) {
					console.log(dataSet);
					
					$('#contractCandidateGrid').delRowData(lastSelected_contractCandidateGrid_Id);
					$('#estimateDetailGrid').jqGrid('clearGridData');
					
					var newContractNo = dataSet.result.newContractNo;
					alertError("성공!", "견적번호 " + lastSelected_contractCandidateGrid_RowValue.estimateNo +
							" 가 </br> 수주번호 " + newContractNo + " 으로 등록되었습니다");
				}
			});  
			
		} else {
			
			alertError("^^", "취소되었습니다");
			
		}

		
	})
	
}



function showEstimateDetailGrid(){
	$('#estimateDetailGrid').jqGrid('clearGridData');
	$.ajax({ 
		type : 'POST',
		url : '${pageContext.request.contextPath}/sales/searchEstimate.do' ,
		data : {
			method : 'searchEstimateDetailInfo', 
			estimateNo : $('#contractCandidateGrid').jqGrid('getRowData', lastSelected_contractCandidateGrid_Id).estimateNo 

		},
		dataType : 'json', 
		cache : false, 
		success : function(dataSet) { 

			console.log(dataSet);
			gridRowJson = dataSet.gridRowJson;
			
			$('#estimateDetailGrid').jqGrid('clearGridData');

			// 견적상세Data 넣기
			$('#estimateDetailGrid')
				.jqGrid('setGridParam',{ datatype : 'local', data : gridRowJson })
				.trigger('reloadGrid');
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
				
				if(codeUseCheck != 'N' && codeUseCheck != 'n') {
					
					$(grid).setCell(rowid, iCol, detailCode);					
					
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
     <div>
	 <div style="padding-left:250px; padding-top:20px">
	<input type="text" value="시작일" id="startDatePicker" />
	<input type="text" value="종료일" id="endDatePicker" />
	<input type="button" value="수주가능견적조회" id="contractCandidateSearchButton" />
	<input type="button" value="수주등록" id="contractBatchInsertButton" />

	</div>
    </div>
 
<table id="contractCandidateGrid" ></table>
<div id="contractCandidateGridPager"></div>

<table align="center" id="estimateDetailGrid" ></table>
<div id="estimateDetailGridPager"></div>

<div id="codeDialog">
	<table id="codeGrid"></table>
</div>

</body>
</html>