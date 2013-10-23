<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/admin_resource/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<table>
		<tr>
			<td><label for="organization">Organization</label></td>
			<td><input type="text" id="organization" value="<s:property value="organization" />" /></td>
		</tr>
		<tr>
			<td><label for="vdc">vdc</label></td>
			<td><input type="text" id="vdc" value="<s:property value="vdc" />" /></td>
		</tr>
	</table>
</div>

<%@ include file="/bottom.jsp" %>