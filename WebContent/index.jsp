<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="Mt20" style="width: 300px; margin-left: auto; margin-right: auto; border: solid #000 2px; padding: 20px; border-radius: 15px;">
	<form action="/login" method="POST">
		<table>
			<tr>
				<td><label for="user">用户名：</label></td>
				<td><input type="text" id="user" name="user" /></td>
				<td><input type="submit" name="login" value="登陆" style="width: 80px; margin-left: 5px;" /></td>
			</tr>
			<tr>
		   		<td><label for="pswd">密&nbsp&nbsp&nbsp码：</label></td>
		   		<td><input type="password" id="pswd" name="pswd" /></td>
		   		<td><input type="submit" name="admin" value="管理员登陆" style="width: 80px; margin-left: 5px;" /></td>
		   	</tr>
		</table>
		<div class="subinfo Mt10"><a href="#" class="Ml50">找回密码</a><a href="/register" class="Ml50">注册</a></div>
	</form>
</div>

<%@ include file="/bottom.jsp" %>