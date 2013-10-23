<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/admin_resource/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<s:if test="testVappMap.size()>0" >
		<table>
			<s:iterator value="testVappMap">
				<tr>
					<td width="130px"><s:property value='value["vappname"]' /></td>
					<td width="150px"><s:property value='value["userid"]' /></td>
					<td width="150px"><s:property value='value["username"]' /></td>
				</tr>
			</s:iterator>
		</table>
	</s:if>

	<s:if test="systemVappMap.size()>0" >
		<table>
			<s:iterator value="systemVappMap">
				<tr>
					<td width="130px"><s:property value='value["vappname"]' /></td>
					<td width="150px"><s:property value='value["userid"]' /></td>
					<td width="150px"><s:property value='value["username"]' /></td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
</div>

<%@ include file="/bottom.jsp" %>