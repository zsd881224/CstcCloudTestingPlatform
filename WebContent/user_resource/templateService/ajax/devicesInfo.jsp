﻿<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="vappListMap.size()>0" >
	<table>
		<s:iterator value="vappListMap">
			<tr>
				<td width="130px" class="F15"><div class="Wb" style="width: 120px;"><a href="#" title="<s:property value='value["vappname"]' /> 创建自模板 <s:property value='value["systemTemplate"]' />" onclick="showNetworkSetting('<s:property value='value["vappname"]' />');return false"><s:property value='value["vappname"]' /></a></div></td>
				<td class="subinfo" width="150px"><s:property value='value["time"]' /></td>
				<td width="70px" class="F13"><a href="#" onclick="releaseResource('<s:property value='value["vappname"]' />'); return false;">释放资源</a></td>
			</tr>
		</s:iterator>
	</table>
</s:if>
<s:else>
<p class="subinfo">您还没有创建任何虚拟机</p>
</s:else>