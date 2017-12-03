<%@page import="com.core.base.util.SystemConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String themesPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/application/";
String buttons = (String) request.getSession().getAttribute(SystemConstants.BUTTON_AUTH);
%>
	<!-- jQuery -->
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery-1.7.2.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery-ui-1.8.20.custom.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery.ui.draggable.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath%>js/plugins/zTree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=themesPath%>js/plugins/zTree/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery.effects.fade.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jqgrid/i18n/grid.locale-cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jqgrid/jquery.jqGrid.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/simpla.jquery.configuration.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery.form.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery.validate.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/jq/jquery/jquery-ui-timepicker-addon.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/plugins/loading/lazyload-min.js"></script>

<script type="text/javascript" src="<%=themesPath %>js/common/json2.js"></script>
<script type="text/javascript" src="<%=themesPath %>js/common/layout.js"></script>
<script type="text/javascript" src="<%=themesPath %>js/common/commonPageJS.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/common/grayscale.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/common/form_cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/common/form_customer_validate.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=themesPath %>js/plugins/loading/jquery.showLoading.js"></script>
<script type="text/javascript" src="<%=themesPath%>js/jq/jquery/jquery.autocomplete.min.js" charset="UTF-8"></script>

<input type="hidden" id="global_username" value="${sessionScope.SPRING_SECURITY_LAST_USERNAME}"/>
<input type="hidden" id="global_context" value="${pageContext.request.contextPath}"/>
<input type="hidden" id="global_url" value="${pageContext.request.requestURL}"/>

<script type="text/javascript">
var global_username = $("#global_username").val();
var global_context =  $("#global_context").val();
var global_url =  $("#global_url").val();//取得当前页面的request url以便控制按钮权限
var global_jsp_position = global_context+"/application/views";
var global_images_position = global_context+"/application/images/func";
var buttons = <%=buttons%>;
function Menu(url) {//重写父窗口中右侧子窗口iframe的src，实现左侧子窗口控制父窗口并显示内容在右侧子窗口
	$('#rightFrame', window.parent.document).attr('src', url);
}
</script>