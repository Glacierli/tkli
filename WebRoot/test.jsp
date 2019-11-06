<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
	Date date = new Date();
	int nowYear = Integer.valueOf(sdfYear.format(date));//获取当前年份
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>EHS交流园地后台管理</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<style>
	.weizhi{
	text-align:center;
	margin:0 auto;
	}
		table{
	    overflow-x: scroll;
	    margin-left: 20%
	}
	tr{
	line-height: 50px
	}
	</style>
	
	
	</head>
<body class="easyui-layout">
<div style="text-align:center;padding:5px 0">
 <form id="ff" method="post" style="text-align:center;padding:5px 0">
	<table style="text-align:right; padding:5px 0">
 	<tr>
 		 <th>公司年份：</th>
 		 <th> 
 		 <select class="easyui-combobox" id="gsyear" name="gsyear" panelHeight="auto" required:true style=" width:100%; padding-top:5px; margin-top:10px;">
 		 </th>
 	</tr>
 	<tr>
 		<th>公司损失工时：</th>
 		<th><input class="easyui-textbox" id="gsssgs" name="gsssgs" style="width:100%" required:true"> </th>
 	</tr>
	 <tr>
 		 <th>承包商年份：</th>
 		 <th> 
 		 <select class="easyui-combobox" id="cbsyear" name="cbsyear" panelHeight="auto" required:true style=" width:100%; padding-top:5px; margin-top:10px;">
 		 </th>
 	</tr>
 	<tr>
 		<th>承包商损失工时：</th>
 		<th> <input class="easyui-textbox" id="cbsssgs" name="cbsssgs" style="width:100%;" required:true"> </th>
	 </tr>
 	<tr>
 		<th>当前安全记录天数：</th>
 		<th>  <input class="easyui-textbox" id="SecuNum" name="SecuNum" style="width:100%;" required:true"> </th>
 	</tr>
 	<tr>
 		<th>最高安全记录天数：</th>
 		<th>  <input class="easyui-textbox" id="MaxSecuNum"  disabled style="width:100%;" required:true"> </th>
 	</tr>
 	<tr>
 		<th>最近损失事故发生时间：</th>
 		<th> 
 	 	  <input class="easyui-textbox"   type="date" id="DataTime" name="DataTime" placeholder="请选择时间" required="required" style="width:100%;">
    	</th>
 	</tr>
 	</table>
</form>
         <div style="text-align:left;padding:5px 0;margin-left: 30%">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px">清空</a>
        </div>
</div>
<!--引入easyui中文包-->
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script> 
	<script type="text/javascript">
    var currentYear = new Date().getFullYear();
    var select = document.getElementById("gsyear");
    for (var i = 0; i <= 10; i++) {
        var theOption = document.createElement("option");
        theOption.innerHTML = currentYear-i + "年";
        theOption.value = currentYear-i;
        select.appendChild(theOption);
    }
    var select2 = document.getElementById("cbsyear");
    for (var i = 0; i <= 10; i++) {
        var theOption = document.createElement("option");
        theOption.innerHTML = currentYear-i + "年";
        theOption.value = currentYear-i;
        select2.appendChild(theOption);
    }
</script>
	<script>
	/*数据回显   为了保证回显的数据是最新的
          在进入页面的时候调用接口查询一下数据，
          当提交数据的时候再去调用一下接口方法来刷新数据
	*/
	 $(function () {
		 refresh();
	 })
	 /*回显操作*/
	 function refresh(){
		 $.ajax({
	          url: "/tkli/Echo",
	          type: "POST",
	          data: {
	          },
	          contentType: 'text/json,charset=utf-8',
	          dataType: "json",
	          success: function (data) {
	              var json = eval(data)
	              console.log(json)
	              window.location.href="#";
	              for(var i in json){
	            	  if(i="gsyear"){
	            		  $('#gsyear').textbox('setValue',json[i]);
	            	  }
	            	  if(i="gsssgs"){
	            		  $('#gsssgs').textbox('setValue',json[i]);
	            	  } if(i="cbsyear"){
	            		  $('#cbsyear').textbox('setValue',json[i]);
	            	  }
						 if(i="cbsssgs"){
	            		  $('#cbsssgs').textbox('setValue',json[i]);
	            	  }
						 if(i="SecuNum"){
	            		  $('#SecuNum').textbox('setValue',json[i]);
	            	  }
						 if(i="DataTime"){
	            		  $('#DataTime').textbox('setValue',json[i]);
	            	  } 
						 if(i="MaxSecuNum"){
	            		  $('#MaxSecuNum').textbox('setValue',json[i]);
	            	  } 
					}
	          },
	          error: function (data, status, e) {
	        	  $.messager.alert('title','发生了未知的错误！！！','info');
	          }
	      }); 
	}
	 
	
	    /* 表单数据提交 */
        function submitForm(){
        		   var formvalue= $('#ff').serializeArray();
                   console.log(formvalue);
                   var mapvalue = {};
                   mapvalue["value"] = formvalue;
                   $.ajax({
                       url: "/tkli/getformvalue",
                       type: "POST",
                       data: JSON.stringify(mapvalue),
                       dataType: "json",
                       contentType: 'application/json',
                       success: function (data) {
                       	console.log(data)
       					//将状态信息和代码给全局变量
                           alert(data.msg);
                       	refresh();
                       },
                       error: function (data, status, e) {
                       	 alert(data.msg);
                       }
                   }); 
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>