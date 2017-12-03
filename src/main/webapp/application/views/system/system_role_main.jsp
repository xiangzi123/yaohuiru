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
        	当前位置：&gt; 角色管理
        </div>
    </div>
    <div class="content" id="main-content">
    	<div class="sear" id="query_form"> 
        <!-- 	<label>角色ID：<input type="text"  id="query_roleID" /></label>
        -->     <label>角色名：<input type="text" id="query_name"/></label>
            <label>描述：<input type="text"  id="query_description"/></label>
            <label>权限：
            	<select id="query_privilege" name="privilege" size="1">
            	<option value=''>--请选择--</option>
         	    </select>
            </label>
            <label>所属机构：
            	<select id="query_organ" name="organ" size="1">
            	<option value=''>--请选择--</option>
         	    </select>
            </label>  
            <label><input type="button" value="查询" class="butt" onclick="query()"/></label>
        </div>
        <div class="toolBar">&nbsp;
            <input type="button" value="新增" class="new auth" authKey = "role_add"  onclick="addRoleIn();"/>
        </div>
		<table id="roleList"></table>
		<div id="roleListPager"></div>
    </div>
    <div class="bottom">
    	<div class="left"></div>
        <div class="right"><div class="center"></div></div>
    </div>

<!-- 弹窗定义 -->
	<div id="roleDialog">
	</div>
	<div id="privilegeDialog">
	</div>
	<!--引入提示告警和确认  -->
	<%@ include file="../common/alert_dialog.jsp" %>
	<%@ include file="../common/confirm_dialog.jsp" %>
	<jsp:include page="../common/script.jsp" flush="true"/>	
	<script type="text/javascript" src="<%=templatePath%>js/system/system_role_main.js"></script>
</body>
</html>
