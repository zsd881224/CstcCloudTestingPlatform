<%@ page language="java" pageEncoding="utf-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html;charset=utf-8"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>CSTC</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="content-language" content="zh-CN" />
	<link rel="stylesheet" href="/templates/css/reset.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="/templates/css/layout.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="/templates/css/universal.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="/templates/css/CstcCloudTestingPlatform.css" type="text/css" media="screen" />
</head>
<body>

<div class="logo">
	<a href="/index"><img src="/templates/img/logo.png" border="0" /></a>
</div>

<div class="daohang">
	<div class="F_Left daohang_left">
		<strong><a href="/index" title="首页">&nbsp&nbsp&nbsp首 页&nbsp&nbsp&nbsp</a></strong>
		<% if(session.getAttribute("userid")!=null) { %>
			<% if(session.getAttribute("isadmin")!=null) { %>
			<strong><a href="/admin/switchToUserView" title="用户视图">用户视图</a></strong>
			<strong><a href="https://172.16.21.248/cloud/" target="blank" title="后台管理">后台管理</a></strong>
			<% } else { %>
			<strong><a href="/performanceTesting" title="性能测试">性能测试</a></strong>
			<strong><a href="/templateService" title="模板服务">模板服务</a></strong>
			<strong><a href="/environmentService" title="环境定制服务">环境定制服务</a></strong>
			<strong><a href="/bigDataAnalysis" title="大数据分析服务">大数据分析服务</a></strong>
			<% } %>
		<% } %>
	</div>
	<div class="F_Right daohang_right">
		<% if(session.getAttribute("userid")!=null) { %>
		<p>你好，<% if(session.getAttribute("isadmin")!=null) { %>管理员 <% } %>
			<a href="/userInfo" style="font-weight: bold; color: white;">
			<% if(session.getAttribute("nickname")!=null&&session.getAttribute("nickname")!="") { %>
			<%=session.getAttribute("nickname") %>
			<% } else { %>
			<%=session.getAttribute("username") %>
			<% } %>
		</a>！&nbsp&nbsp&nbsp<a href="/logout">退出</a></p>
		<% } else { %>
		<p>你好，请先 <a href="/index">登陆</a> 再进行相关操作</p>
		<% } %>
	</div>
</div>