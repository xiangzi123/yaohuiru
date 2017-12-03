<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/init.jsp"%>

<html>
<head>
	<title>智能门锁信息管理系统</title>
	<jsp:include page="../common/meta.jsp" flush="true"/>
	<jsp:include page="../common/style.jsp" flush="true"/>
</head>
<body class="login_bodyBg">
<div class="login_bg">
	<form id="loginForm" method="POST" action="j_spring_security_check">
    <table class="login">
      <tr>
        <td class="t_r">用&nbsp;户&nbsp;名</td>
        <td class="t_c"><input type="text" class="input" size="8"  id="username" name="username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/></td>
        <td class="t_l">
        <span class="tip">
        	${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message!="账号不存在！" ? '' : '账号不存在！'}
        	${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message!="账号被冻结！" ? '' : '账号被冻结！'}
        	${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message!="账号被废弃！" ? '' : '账号被废弃！'}
        	${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message!="Maximum sessions of 1 for this principal exceeded" ? '' : '该账号已登录！'}
        </span></td>
      </tr>
      <tr>
        <td class="t_r">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</td>
        <td class="t_c"><input type="password" class="input" size="8" id="password" name="password"/></td>
        <td class="t_l">
        <span class="tip">
        	${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message!="账号密码错误！" ? '' : '账号密码错误！'}
        </span></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><input type="submit" id="chargeSearch" value="登 录" class="login_btn_1"/>
        	<input type="reset" id="reset" value="重 置" class="login_btn_2"/>
       	</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </form>
    <div id="login-main-inform">
		<br />
		<p>
			<img src="<%=templatePath %>images/inform.png" />
			请输入您的用户名和密码进行登录，如果您还没有账号，请联系系统管理员！
		</p>
	</div>
   <%-- <div class="company">北京邮电大学</div> --%>
</div>
	<jsp:include page="../common/script.jsp" flush="true"/>
	<script type="text/javascript">
	$(function() {
		//注册表单验证
		$(":input").bind("focus",function(){
			$(".tip").empty();
		});
		$("#loginForm").validate({
			rules : {
				username : {required:true,maxlength:30,byteRangeLength:[0,30]},
				password : {required:true,maxlength:15},
			},
			errorPlacement : function(error, element) { //指定错误信息位置
					if(element.parent().next().children().filter("span").text()!="") element.parent().next().children().filter("span").text('');
				    error.appendTo(element.parent().next().children().filter("span"));
			}
		});
	});
	</script>
</body>
</html>