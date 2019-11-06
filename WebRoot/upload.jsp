<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.bcli.backend.vo.ErrorInfo"%>
<%
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
	Date date = new Date();
	int nowYear = Integer.valueOf(sdfYear.format(date));//获取当前年份
	//ErrorInfo error = (ErrorInfo) session.getAttribute("error");
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
	    /* margin-left: 20% */
	}
	tr{
	line-height: 50px
	}
	</style>
	
	
	</head>
	<body class="easyui-layout">
	<div style="text-align:center;padding:5px 0">
	<form id="filevalue"  action="" method="post" style="text-align:center;padding:5px 0">
       <div style="text-align:left; padding:5px 0">
       				请选择模块：<select class="form-control" name="type" id="seltype">
							 	<option value="首页图片">首页图片</option>							
								<option value="安全管理体系手册">安全管理体系手册</option>
								<option value="安全作业指引">安全作业指引</option>
								<option value="健康安全活动图片">健康安全活动图片</option>
								<option value="健康及安全政策">健康及安全政策</option>
								<option value="相关检测报告">相关检测报告</option>
							</select></div>
          <table style="text-align:center; padding:5px 0" id="changeTable">
               <tr>
                   <td> 文件上传：</td>
                   <td><input type="file" name="file1" id="file1"></td>
                   <td></td>
               </tr>
         </table>
    </form>
		<div style="text-align:left; margin-left: 5%">
			<button><a id="form" >提交</a></button>
			&nbsp;
			<button onclick="addRow()">添加更多文件..</button>
		</div>	

		<div>
			</button><button onclick="getpath()">获取路径测试..</button>
		</div>
	<!--引入easyui中文包-->
	<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script> 
	<script type="text/javascript">
    $("#form").click(function(){
    	var formFile = new FormData($("#filevalue")[0]);
        var data = formFile;
        $.ajax({
            url: "${pageContext.request.contextPath }/upload_deal",
            data: data,
            type: "Post",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (result) {
            	$.messager.alert('title',result.msg,'info');
            },
        })
    });
    
	/*点击清空  */
	  $("#seltype").bind("change", function () {
		  $("input[name='file1']").val("");
	  });
    /*添加更多的tr td  */
    function addRow(){
    	  var trNum = $("#changeTable").find("tr").length; //行数
		  var trObj = document.createElement("tr");
		  trObj.id = new Date().getTime(); 
		  trObj.innerHTML = "<td></td><td><input type='file' name='file1'></td>"+
		  "<td><div onclick='delRow(this)'>删除</div></td>"
		  document.getElementById("changeTable").appendChild(trObj);
		};  
    
    /*删除  */
    function delRow(obj){
    	var trId = obj.parentNode.parentNode.id;
    	var trObj = document.getElementById(trId);
    	$.messager.confirm('Confirm','确定删除当前文件？',function(r){
    	document.getElementById("changeTable").removeChild(trObj);
    	});
		
    };
    
    
    /*测试获取文件路径  */
    function getpath(){
    	  var typevalue=$("#seltype").val();
    	  console.log("typevalue"+typevalue);
    	  $.ajax({
              url: "/tkli/getfilepath",
              type: "POST",
              data : typevalue, 
              dataType: "json",
              contentType: 'text/json,charset=utf-8',   
              success: function (data) {
              	console.log(data);
              },
              error: function (data, status, e) {
              	 //alert(data.msg);
              }
          }); 
    }
    </script>
</body>
</html>