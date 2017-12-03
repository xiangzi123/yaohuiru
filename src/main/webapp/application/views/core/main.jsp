<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/init.jsp"%>
<%String buttons = (String) request.getSession().getAttribute("buttonAuth"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>智能门锁信息管理系统</title>
<jsp:include page="../common/meta.jsp" flush="true" />
<jsp:include page="../common/style.jsp" flush="true" />
</head>
<body class="mainBody">
	<iframe src="<%=modulesPath%>core/header.jsp" frameborder="0" id="header"></iframe>
	<iframe src="<%=modulesPath %>core/leftMenu.jsp" frameborder="0" class="leftMenu"></iframe>
	<div class="frameToggle"></div>
	<div class="rightContent" id="rightContent">
		<iframe src="<%=modulesPath%>core/indexContent.jsp" frameborder="0" style="height:100%; width:100%;" id="rightFrame"></iframe>
	</div>
	<jsp:include page="../common/script.jsp" flush="true" />
	<script type="text/javascript">
	    var buttons = <%=buttons%>;
	</script>
</body>
</html>
