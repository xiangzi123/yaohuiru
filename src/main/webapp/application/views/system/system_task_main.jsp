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
        	当前位置：&gt; 施工单管理
        </div>
    </div>
    <div class="content" id="main-content">
    	<div class="sear" id="query_form"> 
           <label>施工单编号：<input type="text" id="query_taskId"/></label>
            <label>日期：<input type="text" size="12" id="query_date" name="querydate" class="time"/></label>
           <label>施工人：<input type="text" id="query_personId"/></label>
             <label>施工住户：<input type="text" id="query_residentId"/></label>
              <label>施工类型：
            	<select id="query_type" name="type" size="1">
            	<option value=''>--请选择--</option>
            	<option value='1'>开机</option>
            	<option value='0'>维修</option>
         	    </select>
            </label>
            </br>
             <label>完成情况：
            	<select id="query_situation" name="situation" size="1">
            	<option value=''>--请选择--</option>
            	<option value='0'>未完成</option>
            	<option value='1'>已完成</option>
         	    </select>
            </label>
            <label>回访状态：
            	<select id="query_state" name="state" size="1">
            	<option value=''>--请选择--</option>
            	<option value='0'>待回访</option>
            	<option value='1'>已回访</option>
         	    </select>
            </label>
            <label><input type="button" value="查询" class="butt" onclick="query()"/></label>
        </div>
        <div class="toolBar">&nbsp;
            <input type="button" value="新增" class="new auth" authKey = "task_add"  onclick="addTaskIn();"/>
        </div>
		<table id="taskList"></table>
		<div id="taskListPager"></div>
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
	<script type="text/javascript" src="<%=templatePath%>js/system/system_task_main.js"></script>
</body>
</html>
