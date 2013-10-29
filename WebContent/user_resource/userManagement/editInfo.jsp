<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/user_resource/userManagement/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<form accept-charset="utf-8" onsubmit="document.charset='utf-8';" action="/userInfo/saveInfo" method="POST">
		<div class="Ml20 Mt10 F14" style="line-height: 25px;">
			<table>
				<tr><td class="title" width="150px">编辑信息</td><td width="550px" style="text-align: right;"></td></tr>
				<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
				<tr><td>用户名：</td><td><s:property value="username" /></td></tr>
				<tr><td>昵称：</td><td><input type="text" style="width: 150px;" id="nickname" name="nickname" value="<s:property value="nickname" />" /></td></tr>
				<tr><td>电子邮箱：</td><td><input type="text" style="width: 300px;" id="email" name="email" value="<s:property value="email" />" /></td></tr>
				<tr><td>QQ：</td><td><input type="text" style="width: 150px;" id="qq" name="qq" value="<s:property value="qq" />" /></td></tr>
				<tr><td>通信地址：</td><td><input type="text" style="width: 500px;" id="address" name="address" value="<s:property value="address" />" /></td></tr>
				<tr><td>邮编：</td><td><input type="text" style="width: 150px;" id="zip" name="zip" value="<s:property value="zip" />" /></td></tr>
				<tr><td></td><td><input type="submit" value="保存" style="width: 100px;" /></td></tr>
			</table>
		</div>
	</form>
</div>

<script>
function checkStr(str){
	// [\u4E00-\uFA29]|[\uE7C7-\uE7F3]汉字编码范围
	var re1 = new RegExp("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$");
	if (!re1.test(str)){
		alert("否");
		return false;
	}
		alert("是");
		return true;
}
</script> 

<%@ include file="/bottom.jsp" %>