<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css"> 
#container td { 
border:1px solid navy; width:100px;
text-align:center; 
}
</style>
<script src="https://code.jquery.com/jquery-1.10.2.js">     // 제이쿼리 import 무조건
</script>
<script src="jquery.json-2.3.js">   //toJSON 가능하게 import? 무조건
</script>
<script>
$(document).ready(function(){
initTable();
$("#addButton").click(addFunc);
$("#updateButton").click(updateFunc);
});

function initTable(){
var colArray=["ID","PW","ADDR","TEL"];
var trObj=$("<tr/>");                             //tr생성
trObj.appendTo("#container");                // tr을  아이디가 container인 테이블에 갖다붙임   
$.each(colArray,function(index,name){
$("<td/>",{"html":name}).appendTo(trObj);   //td생성하는데 안에내용  colArray value값 즉 ID PW ADD TEL 들어가게됨. 그것을 appendTo(부모) 니깐
                                                                               //  trObj즉 tr에 붙임.
}); 
}

var num=0;
function addFunc(){
	num++;
eval("var trObj=$('<tr id=t"+num+"/>')");   // tr의 아이디를 t1 t2 이런식으로 줄거임
trObj.appendTo("#container");                   // container 테이블에 tr붙임.
for(var col=0; col<4; col++){                        //총 칼럼 4개니깐 4번 돌릴꺼임.
eval("var tdObj=$('<td id=t"+num+"-"+col+"/>')");     // eval 함수 사용하여 td의 아이디를 t1-1 이런식으로 만들거 
tdObj.append(                                          
$("<input type=text />").css({"width":"100px"})          // td에 붙일거다 text를  그리고 text의 css는 width:100px
).appendTo(trObj).keypress(function(e){              //이 td를 tr에붙일거고 keypress 액션이 걸리는 아이는 td이다.
if(e.keyCode==13){                                                    //e.ketCode에서 e는 event 이다 즉 이벤트걸리는 키가  13이면(enter) true가 되어 실행된다.
	$(this).html($($(this).children()[0]).val());     // $(this)는 td이고 이것의 자식의 0번째는 text이다 
                                                                                                         그것의 내용을 td html해라는 즉 text가 td내용으로 되버림.
	$($(this).children()[0]).remove();           //넣고나서 text 지워버린다.
	$($(this).next().children()[0]).focus();  // td의 next는 다음 td 이고 그것의 자식에 focus 걸기 ->즉 엔터치면 다음td안에 text로 포커스넘어감
}
});
}

}

function MyGrid(){                                 //MYGrid라는 객체를 하나 생성함.
	
	this.getrow=function(rownum){     //getrow 안에는 행을 넣을수 있다.
		var n=rownum;    //rownum이 1로 넘어왔다 치면  n도 1이다.
		var str=""; 
		for(a=0; a<4; a++){
			var ttt=eval("$('#t"+n+"-"+a+"').text()");     //ttt=t1-0 t1-1 t1-2 t1-3 이런식
		if(ttt.trim().length!=0)     //공백이아니면 str에 /t1-0이런식으로 추가되도록
		{str+="/"+ttt}
		else
			{str+="/"}          //공백일경우 /t1-0/공백/     <-이런식으로 될거임.
		}
		
		var title=["id","pw","addr","tel"];
		var obj={};   //공객체 하나 만듬
		var array=str.split("/");   //   str을 '/'로 잘라주기
		for(b=0; b<4; b++){
			var v=String(array[b+1]);  //  "/" 기준으로 자를경우 0칸은 무조건없음.그래서 array[b+1]한거임 
                                                                                                      // String으로 강제형변환한 이유는 밑에 trim()에서 오류걸림. 
			if(v.trim().length!=0)        //v가 공백이 아니면
			{obj[""+title[b]+""]=v;}      //객체에 key value 추가함.	
			
		}
	 if(Object.getOwnPropertyNames(obj).length===0)   //구글링해서 가져온 것인데 만약 빈객체일 경우 true 뜸.
		 {return null;}
	 else {return obj;} 
	}	
}

function updateFunc(){
	
	var dataSet=[];	
	var mygrid=new MyGrid()
	for(c=1; c<$("#container").find('tr').length; c++){              // 테이블 행갯수 구하는방법 이것도 구글링 함.
	dataSet.push(mygrid.getrow(c));                     //행갯수만큼 getrow호출
	}
	var data={"list":dataSet}                     
	var s=$.toJSON(data);                    //data객체를 toJSON으로 문자로 바꿈.
	var ss=encodeURIComponent(s);   //encoding하고 날릴거임.
    location.href="<%=request.getContextPath()%>/member/joinn.do?sendData="+ss+""   // 객체를 문자로 날린다.(ss)
	
}


</script>
</head>
<body>
<input type="button" value="추가" id="addButton">
<input type="button" value="업데이트" id="updateButton"><br/>
<table id="container"></table>
</body>
</html>