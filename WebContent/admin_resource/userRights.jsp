<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/admin_resource/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<p>管理员权限表</p>
	<table>
		<tr>
			<th>ID</th>
			<th>类型</th>
			<th>权限</th>
		</tr>
		<tr>
			<td>0</td>
			<td>超级管理员</td>
			<td>所有操作</td>
		</tr>
		<tr>
			<td>1</td>
			<td>日常管理员</td>
			<td>可执行日常的管理工作</td>
		</tr>
	</table>
</div>

<%@ include file="/bottom.jsp" %>