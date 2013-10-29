<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="vmListMap.size()>0" >
	<table id="vm" class="F15">
		<s:iterator value="vmListMap" id="name">
			<tr>
				<td>
					<a class="F15" href="#" onclick="showRunState('<s:property value='value["vmid"]' />');return false;" title="<s:property value='value["vmname"]' />"><s:property value='value["vmname"]' /></a>&nbsp
					<span>当前状态：</span>
					<s:if test='value["vmstatus"].equals("POWERED_OFF")'>
						<span style="color: red;">关机</span>
					</s:if>
					<s:elseif test='value["vmstatus"].equals("POWERED_ON")'>
						<span style="color: green;">开机</span>
					</s:elseif>
					<s:elseif test='value["vmstatus"].equals("SUSPENDED")'>
						<span style="color: #FFBF00;">挂起</span>
					</s:elseif>
					<button onclick="showConsole('<s:property value='value["vmid"]' />')" <s:if test='!value["vmstatus"].equals("POWERED_ON")'>disabled="disabled"</s:if> >显示终端</button>
				</td>
			</tr>
			<tr>
				<td>
					<span>改变状态：</span>
					<s:if test='value["vmstatus"].equals("POWERED_OFF")'>
						<button onclick="changeState('<s:property value='value["vmid"]'/>','poweron')" style='width: 60px;' >开机</button>
					</s:if>
					<s:elseif test='value["vmstatus"].equals("POWERED_ON")'>
						<button onclick="changeState('<s:property value='value["vmid"]' />','suspend')" style='width: 60px;' >挂起</button>
						<button onclick="changeState('<s:property value='value["vmid"]' />','poweroff')" style='width: 60px;' >关机</button>
						<button onclick="changeState('<s:property value='value["vmid"]' />','reset')" style='width: 60px;' >重启</button>
					</s:elseif>
					<s:elseif test='value["vmstatus"].equals("SUSPENDED")'>
						<button onclick="changeState('<s:property value='value["vmid"]' />','resume')" style='width: 60px;' >恢复</button>
					</s:elseif>
				</td>
			</tr>
		</s:iterator>
	</table>
</s:if>