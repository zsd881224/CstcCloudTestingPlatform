<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/user_resource/userManagement/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<div class="Ml20 Mt10 F14" style="line-height: 25px;">
	<table>
		<tr><td class="title" width="150px">修改密码</td><td width="550px" style="text-align: right;"></td></tr>
		<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
	</table>
	<div class="F14 Mt15" style="margin-left: 200px; line-height: 35px;">
		<form action="/userInfo/doChangePassword" method="POST" onsubmit="return check_all();">
			<table>
				<tr>
					<td><label for="old">原密码：</label></td>
					<td><input type="password" id="old" name="old" onchange="check_old()" /></td>
					<td><label id="old_info" class="subinfo Ml10">请输入旧密码</label></td>
				</tr>
				<tr>
			   		<td><label for="new">新密码：</label></td>
			   		<td><input type="password" id="new" name="new" onchange="check_new()" /></td>
			   		<td><label id="new_info" class="subinfo Ml10">请输入6~20位密码</label></td>
			   	</tr>
			   	<tr>
			   		<td><label for="new2">再次输入：</label></td>
			   		<td><input type="password" id="new2" name="new2" onchange="check_new2()" /></td>
			   		<td><label id="new2_info" class="subinfo Ml10">请确保两次新密码输入相同</label></td>
			   	</tr>
			</table>
			<div class="Mt15" style="margin-left: 100px;"><input type="submit" value="提交" style="width: 100px;" /></div>
		</form>
	</div>
	</div>
</div>

<script>
function check_old() {
	var pswd=document.getElementById("old").value;

	if(pswd==null||pswd=="") {
		document.getElementById("old_info").innerHTML="请输入旧密码";
		return false;
	}
	
	document.getElementById("old_info").innerHTML="旧密码格式正确";
	return true;
}

function check_new() {
	var pswd=document.getElementById("new").value;

	if(pswd==null||pswd=="") {
		document.getElementById("new_info").innerHTML="请输入新密码";
		return false;
	}
	
	if(pswd.length<6||pswd.length>20) {
		document.getElementById("new_info").innerHTML="请确认新密码长度为6-20位";
		return false;
	}
	
	document.getElementById("new_info").innerHTML="新密码格式正确";
	return true;
}

function check_new2() {
	var pswd=document.getElementById("new").value;
	var pswd2=document.getElementById("new2").value;
	
	if(!check_new()) {
		document.getElementById("new2_info").innerHTML="请先确认新密码格式正确";
		return false;
	}
	
	if(pswd!=pswd2) {
		document.getElementById("new2_info").innerHTML="与第一次输入不同";
		return false;
	}
	
	document.getElementById("new2_info").innerHTML="输入正确";
	return true;
}

function check_all() {
	var flag1 = check_old();
	var flag2 = check_new();
	var flag3 = check_new2();
	var result = flag1&&flag2&&flag3;
	
	if(!result) alert("输入信息有误！");
	
	return result;
}
</script>

<%@ include file="/bottom.jsp" %>