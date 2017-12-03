<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>RemoveSession</title>
  </head> 
  <body>
  <%session=request.getSession();session.invalidate();%>
  </body>
</html>
