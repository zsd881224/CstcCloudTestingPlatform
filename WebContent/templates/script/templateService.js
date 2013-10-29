function info_check() {
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {display_info(xmlhttp);};
		xmlhttp.open("POST","/templateService/info_check",true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function display_info(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('info_displayer').innerHTML=xmlhttp.responseText;
		}
	}
}

function loadSystemTemplate() {
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateTemplateList(xmlhttp);};
		xmlhttp.open("POST","/templateService/listSystemTemplate",true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateTemplateList(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('listSystemTemplate').innerHTML=xmlhttp.responseText;
			loadBasicInfo();
		}
	}
}

function loadBasicInfo() {
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateBasicInfo(xmlhttp);};
		xmlhttp.open("POST","/templateService/getBasicInfo",true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateBasicInfo(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('basicInfo').innerHTML=xmlhttp.responseText;
			loadDevicesInfo();
		}
	}
}
	
function loadDevicesInfo() {
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateDevicesInfo(xmlhttp);};
		xmlhttp.open("POST","/templateService/listApp",true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateDevicesInfo(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('devicesInfo').innerHTML=xmlhttp.responseText;
			info_check();
		}
	}
}

function releaseResource(vappname) {
	document.getElementById('devicesInfo').innerHTML+="<p class='subinfo'>正在释放资源，请稍后...</p>";

	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateReleaseResource(xmlhttp);};
		xmlhttp.open("POST","/templateService/releaseResource?vapp="+vappname,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateReleaseResource(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			loadBasicInfo();
		}
	}
}

function showNetworkSetting(vappname) {
	document.getElementById('networkInfo').innerHTML="<p class='subinfo'>正在载入，请稍后...</p>";
	document.getElementById('console').innerHTML="<p class='subinfo'>点击‘显示终端’打开相应虚拟机控制台</p>";
	document.getElementById('runState').innerHTML="<p class='subinfo'>请选择需要查看运行状况的虚拟机</p>";
	
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateNetworkInfo(xmlhttp);};
		xmlhttp.open("POST","/templateService/getNetworkInfo?vapp="+vappname,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function changeState(vmid, newstate) {
	document.getElementById('networkInfo').innerHTML+="<p class='subinfo'>正在发送指令，请稍后...</p>";
	
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateNetworkInfo(xmlhttp);};
		xmlhttp.open("POST","/templateService/changeState?vmid="+vmid+"&newstate="+newstate,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateNetworkInfo(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('networkInfo').innerHTML=xmlhttp.responseText;
			info_check();
		}
	}
}

function showRunState(obj) {
	document.getElementById('runState').innerHTML="<p class='subinfo'>正在载入，请稍后...</p>";
	
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateRunState(xmlhttp);};
		xmlhttp.open("POST","/templateService/getRunState?vmid="+obj,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateRunState(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			document.getElementById('runState').innerHTML=xmlhttp.responseText;
			info_check();
		}
	}
}

function createVapp(templateName,vappName) {
	document.getElementById('devicesInfo').innerHTML+="<p class='subinfo'>正在新建虚拟设备，请稍后...</p>";
	
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateCreateVappState(xmlhttp);};
		xmlhttp.open("POST","/templateService/createVapp?templatename="+templateName+"&name="+vappName,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateCreateVappState(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			loadBasicInfo();
		}
	}
}

function showConsole(vmId) {
	document.getElementById('console').innerHTML="<p class='subinfo'>正在连接虚拟机，请稍后...</p>";
	
	var xmlhttp=null;
	if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
		xmlhttp=new XMLHttpRequest();
	}
	else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp!=null) {
		xmlhttp.onreadystatechange=function() {updateConsole(xmlhttp);};
		xmlhttp.open("POST","/templateService/showConsole?vmid="+vmId,true);
		xmlhttp.send(null);
	}
	else {
		alert("Your browser does not support XMLHTTP.");
	}
}

function updateConsole(xmlhttp) {
	if (xmlhttp.readyState==4) {// 4 = "loaded"
		if (xmlhttp.status==200) {// 200 = "OK"
			info_check();
			document.getElementById('console').innerHTML = "<div style='border: solid 1px black; background: gray; width: 732px; height: 420px;' id='pluginPanel'></div>";
			var obj = eval("(" + xmlhttp.responseText + ")");
			init();
			startup();
			connect(obj.host, obj.ticket, obj.vmid);
			document.getElementById("closeConsoleBtn").disabled=false;
			document.getElementById("maximizeBtn").disabled=false;
		}
	}
}

function closeConsole() {
	disconnect();
	document.getElementById('console').innerHTML = "<p class='subinfo'>点击‘显示终端’打开相应虚拟机控制台</p>";
	document.getElementById("closeConsoleBtn").disabled=true;
	document.getElementById("maximizeBtn").disabled=true;
}

function maximize() {
	setFullscreen();
}

//弹出窗口部分 
var isIe=(document.all)?true:false;

//设置select的可见状态
function setSelectState(state) {
	var objl=document.getElementsByTagName('select');
	for(var i=0;i<objl.length;i++) {
		objl[i].style.visibility=state;
	}
}

function mousePosition(ev) {
	if(ev.pageX || ev.pageY) {
		return {x:ev.pageX, y:ev.pageY};
	}
	return {
		x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop
	};
}

//弹出方法
function showMessageBox(wTitle,content,pos,wWidth) {
	closeWindow();
	var bWidth=parseInt(document.documentElement.scrollWidth);
	var bHeight=parseInt(document.documentElement.scrollHeight);
	if(isIe) {
		setSelectState('hidden');
	}
	var back=document.createElement("div");
	back.id="back";
	var styleStr="top:0px;left:0px;position:absolute;background:#666;width:"+bWidth+"px;height:"+bHeight+"px;";
	styleStr+=(isIe)?"filter:alpha(opacity=0);":"opacity:0;";
	back.style.cssText=styleStr;
	document.body.appendChild(back);
	showBackground(back,50);
	var mesW=document.createElement("div");
	mesW.id="mesWindow";
	mesW.className="mesWindow";
	mesW.innerHTML="<div class='mesWindowTop'><table width='100%' height='100%'><tr><td>"+wTitle+"</td><td style='width:1px;'><input type='button' onclick='closeWindow();' title='关闭窗口' class='close' value='关闭' /></td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"+content+"</div><div class='mesWindowBottom'></div>";
	styleStr="left:"+(((pos.x-wWidth)>0)?(pos.x-wWidth):pos.x)+"px;top:"+(pos.y)+"px;position:absolute;width:"+wWidth+"px;";
	mesW.style.cssText=styleStr;
	document.body.appendChild(mesW);
}

//让背景渐渐变暗
function showBackground(obj,endInt) {
	if(isIe) {
		obj.filters.alpha.opacity+=1;
		if(obj.filters.alpha.opacity<endInt) {
			setTimeout(function(){showBackground(obj,endInt);},5);
		}
	} else {
		var al=parseFloat(obj.style.opacity);al+=0.01;
		obj.style.opacity=al;
		if(al<(endInt/100)) {
			setTimeout(function(){showBackground(obj,endInt);},5);
		}
	}
}

//关闭窗口
function closeWindow() {
	if(document.getElementById('back')!=null) {
		document.getElementById('back').parentNode.removeChild(document.getElementById('back'));
	}
	
	if(document.getElementById('mesWindow')!=null) {
		document.getElementById('mesWindow').parentNode.removeChild(document.getElementById('mesWindow'));
	}
	
	if(isIe) {
		setSelectState('');}
}

//测试弹出
function testMessageBox(ev,templateName)	{
	var objPos = mousePosition(ev);
	messContent="<div style='padding:20px 0 20px 0;'><p>使用模板："
		+templateName
		+"</p><label for='name'>vApp名称：</label>"
		+"<input type='text' id='name' name='name' style='width: 200px' />"
		+"<input type='button' onclick=createVapp('"
		+templateName
		+"',document.getElementById('name').value);closeWindow(); value='新建虚拟设备' /></div>";
	showMessageBox('新建虚拟设备',messContent,objPos,400);
}