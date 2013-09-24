<%@ include file="/top.jsp"%>

<div id="info_displayer"></div>

<div class="F_Left">
	<p class="big_title">性能测试</p>
</div>

<div class="C_Both"></div>

<div class="F_Left Mt10 kuang_196">
	<div class="kuang_196_biaoti">
		<p class="biaoti_wenzi">测试工具模板列表</p>
	</div>
	<div class="kuang_196_neirong" id="listTestTemplate">
		<p class="subinfo">数据正在载入，请等待...</p>
	</div>
	
	<div class="kuang_196_biaoti Mt10">
		<p class="biaoti_wenzi">用户自定义</p>
	</div>
	<div class="kuang_196_neirong">
		<a href="#" style="font-size: 14px;">进入用户自定义界面</a>
	</div>
	
	<div class="kuang_196_biaoti Mt10">
		<p class="biaoti_wenzi">用户留言板</p>
	</div>
	<div class="kuang_196_neirong">
		<p style="font-size: 14px;">留言内容：</p>
		<form accept-charset="utf-8" onsubmit="document.charset='utf-8';" action="/message/leaveMessage" method="POST" class="Mt10" style="border: none;">
			<textarea name="content" id="content"
				style="height: 100px; width: 100%; font-size: 13px;"></textarea>
			<input class="Mt10" type="submit" value="提交" />
			<a class="F12 Ml50" href="/message">查看我的留言</a>
		</form>
	</div>
</div>

<div class="F_Right Mt10 kuang_746">
	<div class="F_Left kuang_370">
		<div class="kuang_370_biaoti">
			<p class="biaoti_wenzi">基本信息</p>
		</div>
		<div class="kuang_370_neirong" style="height: 150px; overflow-y: auto;" id="basicInfo">
			<p class="subinfo">数据正在载入，请等待...</p>
		</div>
	</div>
	
	<div class="F_Right kuang_370">
		<div class="kuang_370_biaoti">
			<p class="biaoti_wenzi">虚拟设备信息</p>
		</div>
		<div class="kuang_370_neirong" style="height: 150px; overflow-y: auto;" id="devicesInfo">
			<div class="subinfo">数据正在载入，请等待...</div>
		</div>
	</div>
	
	<div class="F_Left kuang_370 Mt6">
		<div class="kuang_370_biaoti">
			<p class="biaoti_wenzi">网络配置信息</p>
		</div>
		<div class="kuang_370_neirong" style="height: 150px; overflow-y: auto;" id="networkInfo">
			<p class="subinfo">请选择需要查看网络配置信息的虚拟设备</p>
		</div>
	</div>
	
	
	<div class="F_Right kuang_370 Mt6">
		<div class="kuang_370_biaoti">
			<p class="biaoti_wenzi">运行状况信息</p>
		</div>
		<div class="kuang_370_neirong" style="height: 150px; overflow-y: auto;" id="runState">
			<p class="subinfo">请选择需要查看运行状况的虚拟机</p>
		</div>
	</div>
	
	<div class="F_Left kuang_746 Mt6">
		<div class="kuang_746_biaoti">
			<p class="biaoti_wenzi">控制台</p>
		</div>
		<div class="kuang_746_neirong" style="height: 500px;">
			<div id="console" style="height: 430px;">
				<p class="subinfo">点击‘显示终端’打开相应虚拟机控制台</p>
			</div>
			<div style="width: 100%; border-top: solid #cccccc 1px;">
				<input id="closeConsoleBtn" disabled="disabled" class="F_Right M20" style="height: 40px; width: 100px;" type="button" value="关闭终端" onclick="closeConsole()" />
				<input id="maximizeBtn" disabled="disabled" class="F_Right M20" style="height: 40px; width: 100px;" type="button" value="最大化" onclick="maximize()" />
			</div>
		</div>
	</div>
</div>

<script>
window.onload = function loadAllInfo() {
	loadTestTemplate();
};
</script>

<script charset="utf-8" type="text/javascript" src="/templates/script/performanceTesting.js" ></script>
<script charset="utf-8" type="text/javascript" src="/templates/script/vmrc-embed.js" ></script>

<style>
.mesWindow{border:#666 1px solid;background:#fff;}
.mesWindowTop{border-bottom:#eee 1px solid;margin-left:4px;padding:3px;font-weight:bold;text-align:left;font-size:12px;}
.mesWindowContent{margin:4px;font-size:12px;}
.mesWindow .close{height:15px;width:28px;border:none;cursor:pointer;text-decoration:underline;background:#fff}
</style>

<%@ include file="/bottom.jsp"%>