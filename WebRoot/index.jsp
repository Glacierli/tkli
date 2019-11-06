 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- <%@ page import="constant.com.bcli.backend.constant.Constants"%> --%>
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
<script type="text/javascript">
			$(function(){
				$('a[title]').click(function(){
					var src = $(this).attr('title');
					var title = $(this).html();
					if($('#tt').tabs('exists' ,title)){
						$('#tt').tabs('select',title);
						var tab = $('#tt').tabs('getSelected');
						var url = $(tab.panel('options').content).attr('src');
						 $('#tt').tabs('update', {
                          tab: tab,
                             options: {
                             href:url
                                    }
                                 }); 
                         } else {
						$('#tt').tabs('add',{   
						    title:title,   
						    content:'<iframe frameborder=0 style=width:100%;height:100% src='+ src +' ></iframe>',   
						    closable:true  
						});  
					}
				});
			});
			$(document).ready(function(){
				
			});
	</script>
</head>
<body class="easyui-layout">
<!--标题  -->
	<div data-options="region:'north',border:false" style="height:60px;background-image:url(img/top.png);padding:10px"><img src="img/logo1.png" />
	</div>
	<div region="west" iconCls="icon-ok" split="true" title="菜单"
		style="width:200px;">
		<div id="aa" class="easyui-accordion" fit=true>
			<div title="后台管理" selected="true" style="padding:10px; padding-left: 20px;">
				<a title="test.jsp">前台数据配置</a> <br /> <br /> &nbsp;
				 <a title="upload.jsp">文件上传</a> <br /> <br />  &nbsp;
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height:30px;background-image:url(img/bottom.png);padding:10px;">技术支持：</div>
	<div region="center" title="主界面" style="padding:5px;">
		<div id="tt" class="easyui-tabs" fit=true
			style="width:500px;height:250px;"></div>
	</div>
</body>
</html>