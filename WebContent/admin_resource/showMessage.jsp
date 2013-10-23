<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/top.jsp" %>

<%@ include file="/info_displayer.jsp" %>

<div class="kuang_196 F_Left Mt15">
	<%@ include file="/admin_resource/left.jsp" %>
</div>

<div class="kuang_746 F_Right Mt10">
	<div class="Ml20 Mt10 F14" style="line-height: 25px;">
		<table>
			<tr><td class="title" width="150px">留言板</td><td width="550px" style="text-align: right;"></td></tr>
			<tr style="border-top: solid #cccccc 1px; height: 5px;"></tr>
		</table>
		<s:if test="messageMap.size()>0" >
			<ul>
				<s:iterator value="messageMap">
					<li>
						<div class="message">
							<p class="F14">来自 <span style="font-weight: bold;"><s:property value="value['username']" /></span> :</p>
							<p><s:property value="value['content']" /></p>
							<p class="Mt5"><span class="subinfo"><s:property value="value['time']" /></span><span class="F_Right"><a href="#?id=<s:property value="key" />">回复</a> - <a href="/message/deleteMessage?id=<s:property value="key" />">删除</a></span></p>
							<s:set name="reply" value="value['reply']" />
							<s:if test='#reply!=null'>
								<div class="reply">
									<p><span class="subtitle">回复:</span>
									<s:property value="value['reply']" /></p>
									<p class="Mt5 subinfo"><s:property value="value['replyTime']" /></p>
								</div>
							</s:if>
						</div>
					</li>
				</s:iterator>
			</ul>
		</s:if>
	</div>
	
	<div class="Mt20" id="page" style="text-align: center;"></div>
</div>

<script>
function showPage(all,cur) {
	var max = arguments[2]?arguments[2]:10;
	var page_div = document.getElementById("page");
	var page = "<a";
	if(cur!=1) page += " href='?page="+(cur-1)+"'";
	page += " style='margin-right: 20px;'>上一页</a>";
	if(all<=max) {//不需要省略号
		for(var i=1;i<=all;++i) {
			if(i==cur) page += "<a style='font-weight: bold; margin-left: 15px;'>"+i+"</a>";
			else page += "<a href='?page="+i+"' style='margin-left: 15px;'>" + i + "</a>";
		}
	}
	else if(cur<=max/2) {//仅右侧省略号
		for(var i=1;i<=max-1;++i) {
			if(i==cur) page += "<a style='font-weight: bold; margin-left: 15px;'>"+i+"</a>";
			else page += "<a href='?page="+i+"' style='margin-left: 15px;'>" + i + "</a>";
		}
		page += "<a href='?page="+max+"' style='margin-left: 15px;'>...</a>";
	}
	else if(all<cur+max/2) {//仅左侧省略号
		page += "<a style='margin-left: 15px;'>...</a>";
		for(var i=cur-parseInt(max/2)+1;i<=all;++i) {
			if(i==cur) page += "<a style='font-weight: bold; margin-left: 15px;'>"+i+"</a>";
			else page += "<a href='?page="+i+"' style='margin-left: 15px;'>" + i + "</a>";
		}
	}
	else {//两侧省略号
		page += "<a href='?page="+(cur-parseInt(max/2))+"' style='margin-left: 15px;'>...</a>";
			for(var i=cur-parseInt(max/2)+1;i<=cur+parseInt(max/2)-1;++i) {
				if(i==cur) page += "<a style='font-weight: bold; margin-left: 15px;'>"+i+"</a>";
				else page += "<a href='?page="+i+"' style='margin-left: 15px;'>" + i + "</a>";
			}
		page += "<a href='?page="+(cur+parseInt(max/2))+"' style='margin-left: 15px;'>...</a>";
	}
	page += "<a";
	if(cur<all) page += " href='?page="+(cur+1)+"'";
	page += " style='margin-left: 30px;'>下一页</a>";
	page_div.innerHTML = page;
}

showPage(<s:property value="maxPage" />,<s:property value="curPage" />);
</script>

<%@ include file="/bottom.jsp" %>