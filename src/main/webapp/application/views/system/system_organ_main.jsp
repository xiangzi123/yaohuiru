<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>" />
	<jsp:include page="../common/meta.jsp" flush="true"/>
	<jsp:include page="../common/style.jsp" flush="true"/>
	<title>机构管理</title>
	<style type="text/css">
	.ztree li span.add {margin-left:2px; margin-right: -1px; background-position:-144px 0px; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.modify {margin-left:2px; margin-right: -1px; background-position:-112px -48px; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.delete {margin-left:0px; margin-right: -1px; background-position:-110px -64px; vertical-align:top; *vertical-align:middle}
	</style>
</head>
<body id="activity_pane">
<div class="rightSide">
	<div class="top">
    	<div class="left"></div>
        <div class="current">
        	当前位置：&gt; 机构管理
        </div>
    </div>
    <!----- 内容区 ----->
    <div class="content" id="main-content">
      <!----- 目录树区 ----->
		<div class="tree">
        	<!-----搜索+增加 ----->
            <div class="searchWrap">
            	<div class="search_l"></div>
            	<input type="text" class="search_input" id="query_name" name="name" size="8" style="outline-style: none"/>
            	<div class="search_r"></div>
            	<input type="button" value="" class="search_btn" onclick="query()"/>
            	<a class="arr_l" onclick="queryPre()"></a>
            	<a class="arr_r" onclick="queryNext()"></a>
           	</div>
            <!-----end 搜索+增加 ----->
        	<!-----树 ----->
            <div class="tree_t">
       			<ul id="organ_tree" class="ztree"></ul>
            </div>
            <!-----end 树 ----->
        </div>
        <!----- 编辑区 ----->
        <div class="edit" id="content_body">
        	<div class="welcome"></div>
        </div>
    </div>
	    <!----- end 内容区 ----->
    <div class="bottom">
    	<div class="left"></div>
        <div class="right"><div class="center"></div></div>
    </div>
</div>
<!-- 弹窗定义 -->
	<div id="organDialog">
	</div>

	<!--引入提示告警和确认  -->
	<%@ include file="../common/alert_dialog.jsp" %>
	<%@ include file="../common/confirm_dialog.jsp" %>
	<jsp:include page="../common/script.jsp" flush="true"/> 
	<script type="text/javascript" src="<%=templatePath%>js/system/system_organ_main.js"></script>	
</body>
</html>
