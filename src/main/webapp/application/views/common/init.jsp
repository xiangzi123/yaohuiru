<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String modulesPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/application/views/";
String templatePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/application/";
%>
