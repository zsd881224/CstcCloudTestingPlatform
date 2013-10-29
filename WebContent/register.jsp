<%@ include file="/top.jsp" %>

<%@ include file="info_displayer.jsp" %>

<form action="/regist" method="POST" onsubmit="return check_all()">
	<div style="margin-left: 200px; line-height: 35px;">
		<table>
			<tr>
				<td><label for="user">用户名：</label></td>
				<td><input type="text" name="user" id="username" style="width: 210px;" onchange="check_user()"/></td>
				<td><label id="user_info" class="subinfo Ml10">请输入6~20位用户名，可由字母、数字和下划线组成</label></td>
			</tr>
			<tr>
				<td><label for="pswd">密码：</label></td>
				<td><input type="password" name="pswd" id="password" style="width: 210px;" onchange="check_pswd()"/></td>
				<td><label id="pswd_info" class="subinfo Ml10">请输入6~20位密码</label></td>
			</tr>
			<tr>
				<td><label for="pswd2">确认密码：</label></td>
				<td><input type="password" name="pswd2" id="password2" style="width: 210px;" onchange="check_pswd2()"/></td>
				<td><label id="pswd2_info" class="subinfo Ml10">请确保两次密码输入相同</label></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="register" value="提交" style="width: 70px;"/><input type="reset" value="重设" style="width: 70px; margin-left: 50px;"/></td>
		</table>
	</div>
</form>

<script>
function check_user() {
	var user=document.getElementById("username").value;

	if(user==null||user=="") {
		document.getElementById("user_info").innerHTML="请输入用户名";
		return false;
	}
	
	if(user.length<6||user.length>20) {
		document.getElementById("user_info").innerHTML="请确认用户名长度为6-20位";
		return false;
	}
	
	var pattern=/^[a-zA-Z0-9_]{6,20}$/;
	if(!pattern.exec(user)) {
		document.getElementById("user_info").innerHTML="请确保用户名中只包含字母、数字与下划线";
		return false;
	}
	
	document.getElementById("user_info").innerHTML="用户名格式正确";
	return true;
}

function check_pswd() {
	var pswd=document.getElementById("password").value;

	if(pswd==null||pswd=="") {
		document.getElementById("pswd_info").innerHTML="请输入密码";
		return false;
	}
	
	if(pswd.length<6||pswd.length>20) {
		document.getElementById("pswd_info").innerHTML="请确认密码长度为6-20位";
		return false;
	}
	
	document.getElementById("pswd_info").innerHTML="密码格式正确";
	return true;
}

function check_pswd2() {
	var pswd=document.getElementById("password").value;
	var pswd2=document.getElementById("password2").value;
	
	if(!check_pswd()) {
		document.getElementById("pswd2_info").innerHTML="请先确认密码格式正确";
		return false;
	}
	
	if(pswd!=pswd2) {
		document.getElementById("pswd2_info").innerHTML="与第一次输入不同";
		return false;
	}
	
	document.getElementById("pswd2_info").innerHTML="输入正确";
	return true;
}

function check_all() {
	var flag1 = check_user();
	var flag2 = check_pswd();
	var flag3 = check_pswd2();
	var result = flag1&&flag2&&flag3;
	
	if(!result) alert("输入信息有误！");
	
	return result;
}
</script>

<%@ include file="/bottom.jsp" %>