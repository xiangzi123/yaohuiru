<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%String path = request.getContextPath();
String templatePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/application//"; %>
<link rel="stylesheet" type="text/css" href="<%=templatePath%>css/demo/article.css"/>
</head>
<body>
<!--header begin-->

<!--header end-->
<!--acticle begin-->
<div class="section" id="demoSize">
<div id="acticle">
    <div id="act_content">
    	<ul id="act_title">${title}</ul>
        <ul id="act_inf">发布时间:${time}&nbsp;&nbsp;出处:${from}</ul>
        <ul id="act_main">
		<p>
		${msg}
	　　</p>   	
    </div>
</div>
<!--acticle end-->
<!--footer begin-->

<!--footer end-->
</div>
</body>
</html>