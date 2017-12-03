<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>" />
	<jsp:include page="../common/meta.jsp" flush="true"/>
	<jsp:include page="../common/style.jsp" flush="true"/>
<title>北京邮电大学数媒学院信息管理系统</title>
<style type="text/css">
	.select_opt{float:left; width:35px; height:100%; margin-left:20px;vertical-align:center;}
	.select_opt p{width:20px; height:13px; margin-top:20px; background:url(<%=templatePath%>/images/arr.gif) no-repeat; cursor:pointer; text-indent:-999em}
	.select_opt p#toright{background-position:2px 0}
	.select_opt p#toleft{background-position:2px -16px}
</style>
</head>

<body id="activity_pane">
<div class="rightSide">
	<div class="top">
    	<div class="left"></div>
        <div class="current">
        	当前位置：&gt; 日志管理
        </div>
    </div>
    <div class="content" id="main-content">
    	<div class="sear" id="query_form"> 
        	<label>用户姓名：<input type="text" size="8" id="query_user" /></label>
            <label>开始时间：
					<input type="text" size="12" id="start_time" name="startTime" class="time"/>
            </label>
            <label>结束时间：
					<input type="text" size="12" id="end_time" name="endTime" class="time"/>
			</label>
            <label>所属模块：
            	<select id="query_module" name="module" size="1">
            	<option value=''>--请选择--</option>
            	<option value="机构管理模块">机构管理</option>
  				<option value="用户管理模块">用户管理</option>
                <option value="角色管理模块">角色管理</option>
                <option value="权限管理模块">权限管理</option>
                <option value="学院工资模块">学院工资</option>
                <option value="预约管理模块">预约管理</option>
                <option value="发布管理模块">发布管理</option>
                <option value="教务管理模块">教务管理</option>
         	    </select>
             </label>
            <label><input type="button" value="查询" class="butt" onclick="query()"/></label>
        </div>
        <div class="toolBar">
			&nbsp; <input type="button" value="导出" class="export auth" authKey = "log_export"  onclick="exportLogIn()"/>
		</div>
		<table id="logList"></table>
		<div id="logListPager"></div>
    </div>
    <div class="bottom">
    	<div class="left"></div>
        <div class="right"><div class="center"></div></div>
    </div>

<!-- 弹窗定义 -->
	<div id="logDialog">
	</div>
	<!--引入提示告警和确认  -->
	<%@ include file="../common/alert_dialog.jsp" %>
	<%@ include file="../common/confirm_dialog.jsp" %>
</body>
<jsp:include page="../common/script.jsp" flush="true"/>
<%-- <script type="text/javascript" src="<%=templatePath%>js/system/system_organ_tree.js"></script> --%>
<script type="text/javascript" src="<%=templatePath%>js/system/system_log_main.js"></script>
</html>
