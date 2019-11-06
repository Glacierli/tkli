<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>EHS交流园地后台管理</title>
<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../easyui/demo.css">
<script type="text/javascript" src="../easyui/jquery.min.js"></script>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../common/js/jquery.ajaxfileupload.js"></script>

</head>

<body>
	<table id="dg" title="月度数据上报" class="easyui-datagrid" style="width:700px;height:350px" url="/cendpa/getErrorLog" toolbar="#toolbar"  pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="resource_name" width="100">数据类型</th>
                <th field="resource_code" width="100">数据类型编码</th>
                <th field="error_info" width="100">错误</th>
                <th field="create_time" width="100"   formatter="Common.TimeFormatter">时间</th>
            </tr>
        </thead>
    </table>
</body>

<script type="text/javascript">
/* 	$(function(){
		$.ajax({
			url : "/cendpa/timeinterval/getdata",
			data : {
			"type": "00"
			},
			type : "post",
			dataType : "json",
		    contentType : "application/x-www-form-urlencoded; charset=utf-8",
			error : function() {
				alert("服务器连接失败");
			},
			success : function(data) {
			console.log(data)
				data = eval(data);
				for (var i = 0; i < data.length; i++) {
					alert(data[i].batch);
				}

			}
		});
	}); */
	function add0(m){return m<10?'0'+m:m }
	
	var Common = {
		    //EasyUI用DataGrid用日期格式化
		    TimeFormatter: function (value, rec, index) {
		    	var time = new Date(value);
		    	var year = time.getFullYear();
		    	var month = time.getMonth()+1;
		    	var date = time.getDate();
		    	var hours = time.getHours();
		    	var minutes = time.getMinutes();
		    	var seconds = time.getSeconds();
		    	return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
		    }
		};
</script>
</html>
