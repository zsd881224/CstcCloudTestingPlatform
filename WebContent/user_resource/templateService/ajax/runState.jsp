<%@ taglib prefix="s" uri="/struts-tags"%>

<ul>
	<li>VM名称：<s:property value='vmDetail["vmname"]' /></li>
	<li>计算机名：<s:property value='vmDetail["computername"]' /></li>
	<li>CPU个数：<s:property value='vmDetail["noofcpu"]' /></li>
	<li>内存大小：<s:property value='vmDetail["memorysize"]' /></li>
	<s:set name="count" value="1" />
	<s:iterator value="vmDetail">
		<s:if test='key.indexOf("harddisk")!=-1' >
			<li>硬盘 <s:property value="#count" />：<s:property value="value" /></li>
			<s:set name="count" value="#count+1" />
		</s:if>
	</s:iterator>
</ul>