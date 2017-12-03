<%@page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>合作交流</title>
<%String path = request.getContextPath();
String templatePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/application//"; %>
<link rel="stylesheet" id="indexstyle.css" href= "<%=templatePath%>css/demo/indexstyle.css" type="text/css" media="all">
<link rel="stylesheet" type="text/css" href="<%=templatePath%>css/demo/test.css">

<link rel="stylesheet" id="uw.homepage.bootstrap-css" href="<%=templatePath%>css/demo/test.css" type="text/css" media="all">
<link rel="stylesheet" type="text/css" href="<%=templatePath%>css/demo/erji.css">

<style>[touch-action="none"]{ -ms-touch-action: none; touch-action: none; }[touch-action="pan-x"]{ -ms-touch-action: pan-x; touch-action: pan-x; }[touch-action="pan-y"]{ -ms-touch-action: pan-y; touch-action: pan-y; }[touch-action="scroll"],[touch-action="pan-x pan-y"],[touch-action="pan-y pan-x"]{ -ms-touch-action: pan-x pan-y; touch-action: pan-x pan-y; }</style></head>

<body>
<div id="container">
  <div id="header"><img src="<%=templatePath%>css/demo/logo.png" width="960" height="140" border="0" usemap="#Map">
  </div>
  <div id="main">
    <div id="content">
      <div id="left">
        <div id="pic"><img src="<%=templatePath%>css/demo/hezuo.png" width="234" height="50"></div>
         <div id="unit">合作新闻</div>
       
     </div><!--end left-->
      <div id="right">
        <div id="bread">合作交流&gt;合作新闻</div>
        <div id="text">
        <div id="up"></div>
		<!--插入内容 -->
		${msg}
		<!--插入内容结束 -->
          </div><!--end text-->
        <div id="bottom"></div>
       <div id="footer"></div></div><!--end right-->
      
    </div><!--end content-->
   
  </div><!--end main-->
</div><!--end container-->


</body>

</html>