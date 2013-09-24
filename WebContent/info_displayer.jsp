<div id="info_displayer">
	<%
	if(session.getAttribute("info")!=null) {
		if(session.getAttribute("info_kind").equals("info")) {
	%>
			<div class="alert_info"><span><%=session.getAttribute("info") %></span><input type="button" onclick="document.getElementById('info_displayer').innerHTML=''" value="关闭" /></div>
	<%
		} else if(session.getAttribute("info_kind").equals("warning")) {
	%>
			<div class="alert_warning"><span><%=session.getAttribute("info") %></span><input type="button" onclick="document.getElementById('info_displayer').innerHTML=''" value="关闭" /></div>
	<%
		}
		
		session.removeAttribute("info");
		session.removeAttribute("info_kind");
	}
	%>
</div>