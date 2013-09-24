<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="vappListMap.size()>0" >
	<table>
		<s:iterator value="vappListMap">
			<tr><td><a href="#" onclick="showNetworkSetting('<s:property value='value["vappname"]' />');return false"><s:property value='value["vappname"]' /></a></td><td><s:property value='value["time"]' /></td><td><a href="#" onclick="releaseResource('<s:property value='value["vappname"]' />'); return false;">释放资源</a></td></tr>
		</s:iterator>
	</table>
</s:if>