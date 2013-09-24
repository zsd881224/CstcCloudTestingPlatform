<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/user_resource/userManagement/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<div class="Ml20 Mt10 F14" style="line-height: 25px;">
		<table>
			<tr><td class="title" width="150px">个人信息</td><td width="550px" style="text-align: right;"><a href="/userInfo/editInfo">编辑信息</a></td></tr>
			<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
			<tr><td>用户名：</td><td><s:property value="username" /></td></tr>
			<tr><td>昵称：</td><td><s:property value="nickname" /></td></tr>
			<tr><td>电子邮箱：</td><td><s:property value="email" /></td></tr>
			<tr><td>QQ：</td><td><s:property value="qq" /></td></tr>
			<tr><td>通信地址：</td><td><s:property value="address" /></td></tr>
			<tr><td>邮编：</td><td><s:property value="zip" /></td></tr>
		</table>
		
		<table class="Mt20">
			<tr><td class="title" width="150px">虚拟机信息</td><td width="550px"></td></tr>
			<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
			<tr><td>可建立VM个数：</td><td><s:property value="vmlimit" /></td></tr>
			<tr><td>已建立VM个数：</td><td><s:property value="vappCount" /></td></tr>
			<tr>
		</table>
		
		<table class="Mt20">
			<tr><td class="title" width="150px">登陆信息</td><td width="550px"></tr>
			<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
			<tr><td>注册日期：</td><td><s:property value="registTime" /></td></tr>
			<tr><td>上次登录：</td><td><s:property value="lasttimeLoginTime" /></td></tr>
			<tr><td></td><td class="subinfo">不是您登陆？请 <a href="/userInfo/changePassword">修改密码</a></td></tr>
		</table>
	</div>
</div>

<%@ include file="/bottom.jsp" %>