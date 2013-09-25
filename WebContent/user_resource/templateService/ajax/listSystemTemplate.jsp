<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="templateNameSet.size()>0">
	<table class="F14">
		<s:iterator value="templateNameSet" id="name">
			<tr>
				<td><div>
					<a href="#" onclick="testMessageBox(event,'<s:property value="name" />');return false;" title="<s:property value="name" />"><s:property value="name" /></a>
				</div></td>
			</tr>
		</s:iterator>
	</table>
</s:if>