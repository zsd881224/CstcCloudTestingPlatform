<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="templateNameSet.size()>0">
	<table>
		<s:iterator value="templateNameSet" id="name">
			<tr>
				<td><div>
					<a href="#" onclick="testMessageBox(event,'<s:property value="name" />');return false;"><s:property value="name" /></a>
				</div></td>
			</tr>
		</s:iterator>
	</table>
</s:if>