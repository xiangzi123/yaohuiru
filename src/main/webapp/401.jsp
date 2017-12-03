<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/application/views/common/init.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>访问拒绝</title>
    <jsp:include page="/application/views/common/meta.jsp" flush="true"/>
	<jsp:include page="/application/views/common/style.jsp" flush="true"/>
  </head> 
  <body>
	    <div id="forceDialog" title="操作确认">
			<div >
				<p align="center" id="force-msg" style="color:#CC0000;font-size:14px;line-height:200%;">
				</p>
			</div>
		</div>
    <jsp:include page="/application/views/common/script.jsp" flush="true" />
	<script type="text/javascript">	
	
		$(function() {
			$('#header',window.parent.document).attr("src","");
			$('.leftMenu',window.parent.document).attr("src","");
			initForceDialog("forceDialog");
			$.post('<%=basePath%>logout.jsp');
			forceJQ("该账号正在其它地点登陆，为了账号安全，请重新登陆 ！", ReLogin);
		});
		
		function ReLogin(){
			window.parent.location.href = global_context+"/logout.jsp";
		}
		
		function initForceDialog(id) {
			$("#"+id).dialog({
				autoOpen : false,
				width : 'auto',
				height : '130',
				modal : true,
				resizable : false,
				draggable : false,
				show : 'fade',
				hide : 'fade',
				open:function(event,ui){
					$(".ui-dialog-titlebar-close", $(this).parent()).hide();
				}
			});
		}
		
		function forceJQ(message, callback, data, width, height) {
			$("#force-msg").html(message);
			$("#forceDialog").dialog('option','buttons',{
				"确定" : function() {
					if (data == undefined || data ==""){
					  callback();
					}else{
					  callback(data);
					}
					$(this).dialog("close");
				}
			});
			
			if(width != undefined && width !=""){
				$("#forceDialog").dialog('option','width',width);
			}
			if(height != undefined && height !=""){
				$("#forceDialog").dialog('option','height',height);
			}
			
			//加上自适应弹窗
			if(width == "auto"){
				$("#forceDialog").dialog('option','width',"auto");
			}
			if(height == "auto"){
				$("#forceDialog").dialog('option','height',"auto");
			}
			
			$("#forceDialog").dialog("open");
		}
	</script>
  </body>
</html>
