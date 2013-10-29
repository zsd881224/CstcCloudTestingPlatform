<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div>
	<p class="title Ml30 Mt15">核心服务</p>
</div>

<div style="width: 548px; height: 198px; overflow: hidden; margin-left: 190px;">
	<table style="width: 550px; height: 200px; margin-left: -1px; margin-top: -1px;">
		<tr>
			<td style="border: solid #aaaaaa 1px; text-align: center; height: 100px;">
				<div style="height: 100px; overflow: hidden;">
					<div id="performanceTesting" onmouseover="setAnimateOver(this)" onmouseout="setAnimateOut(this)">
						<a style="display: block; height: 100px; line-height: 100px; font-weight: bold;" href="/performanceTesting">性能测试</a>
						<a style="display: block; height: 100px; line-height: 100px; background: yellow; color: black; font-weight: bold;" href="/performanceTesting">性能测试</a>
					</div>
				</div>
			</td>
			<td style="border: solid #aaaaaa 1px; text-align: center;">
				<div style="height: 100px; overflow: hidden;">
					<div id="templateServiece" onmouseover="setAnimateOver(this)" onmouseout="setAnimateOut(this)">
						<a style="display: block; height: 100px; line-height: 100px; font-weight: bold;" href="/templateService">模板服务</a>
						<a style="display: block; height: 100px; line-height: 100px; background: green; color: white; font-weight: bold;" href="/templateService">模板服务</a>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td style="border: solid #aaaaaa 1px; text-align: center;">
				<div style="height: 100px; overflow: hidden;">
					<div id="/environmentService" onmouseover="setAnimateOver(this)" onmouseout="setAnimateOut(this)">
						<a style="display: block; height: 100px; line-height: 100px; font-weight: bold;" href="/environmentService">环境定制服务</a>
						<a style="display: block; height: 100px; line-height: 100px; background: red; color: white; font-weight: bold;" href="/environmentService">环境定制服务</a>
					</div>
				</div>
			</td>
			<td style="border: solid #aaaaaa 1px; text-align: center;">
				<div style="height: 100px; overflow: hidden;">
					<div id="/bigDataAnalysis" onmouseover="setAnimateOver(this)" onmouseout="setAnimateOut(this)">
						<a style="display: block; height: 100px; line-height: 100px; font-weight: bold;" href="/bigDataAnalysis">大数据分析服务</a>
						<a style="display: block; height: 100px; line-height: 100px; background: blue; color: white; font-weight: bold;" href="/bigDataAnalysis">大数据分析服务</a>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>

<div>
	<p class="title Ml30 Mt15">其他功能</p>
</div>

<div class="Mt15 Ml30">
	<a href="/userInfo">个人信息</a>&nbsp&nbsp&nbsp<a href="/message">留言板</a>&nbsp&nbsp&nbsp<a href="/userInfo/changePassword">修改密码</a>
</div>

<script>
function animateOver(obj) {
	if(obj.style.marginTop=null||obj.style.marginTop=="") obj.style.marginTop="-2px";
	else if(parseInt(obj.style.marginTop)>-100) obj.style.marginTop=parseInt(obj.style.marginTop)-2+"px";
	else window.clearInterval(window[obj.id]);
}

function setAnimateOver(obj) {
	if(typeof(window[obj.id])!=undefined) window.clearInterval(window[obj.id]);
	window[obj.id] = setInterval(function(){animateOver(obj);}, 2);
}

function animateOut(obj) {
	if(obj.style.marginTop=null||obj.style.marginTop=="") window.clearInterval(window[obj.id]);
	else if(parseInt(obj.style.marginTop)<0) obj.style.marginTop=parseInt(obj.style.marginTop)+2+"px";
	else window.clearInterval(window[obj.id]);
}

function setAnimateOut(obj) {
	if(typeof(window[obj.id])!=undefined) window.clearInterval(window[obj.id]);
	window[obj.id] = setInterval(function(){animateOut(obj);}, 2);
}

</script>

<%@ include file="/bottom.jsp" %>