<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../common/init.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" />
<title>修改密码</title>
<jsp:include page="../common/meta.jsp" flush="true" />
<jsp:include page="../common/style.jsp" flush="true" />
</head>
<body id="activity_pane">
	<div class="rightSide">
		<div class="top">
			<div class="left"></div>
			<div class="current">当前位置：&gt; 密码修改</div>
		</div>
		<div class="content">
			<!----- 编辑区 ----->
			<div class="edit" style="margin-left:10px;display: none;">
				<div class="welcome">
					<div class="portlert-form-list"
						style="position:absolute;width:500px;height:200px;left:50%;top:50%;margin:-100px 0 0 -200px;">
						<form id="changePasswordForm" method="post"
							action="<%=basePath%>system/user/changePassword.do">
							<div class="portlert-form-row">
								<label class="portlert-form-label">用 户 名:</label>
								<div class="portlert-form-collection">
									<input type="text" id="userLoginName"
										class="portlert-form-input-field user" name="userLoginName"
										value=<sec:authentication property="name"/> readonly="readonly" /> <span
										class="portlert-form-msg-alert">&nbsp;</span>
								</div>
							</div>
							<div class="portlert-form-row">
								<label class="portlert-form-label">原 密 码: </label>
								<div class="portlert-form-collection">
									<input type="password" id="originalpw"
										class="portlert-form-input-field password" name="originalpw" />
									<span class="portlert-form-msg-alert">&nbsp;</span>
								</div>
							</div>
							<div class="portlert-form-row">
								<label class="portlert-form-label">新 密 码:</label>
								<div class="portlert-form-collection">
									<input type="password" id="password"
										class="portlert-form-input-field password" name="password" />
									<span class="portlert-form-msg-alert">&nbsp;</span>
								</div>
							</div>
							<div class="portlert-form-row">
								<label class="portlert-form-label">确认密码:</label>
								<div class="portlert-form-collection">
									<input type="password" id="confirmPassword"
										class="portlert-form-input-field password"
										name="confirmPassword" /> <span
										class="portlert-form-msg-alert">&nbsp;</span>
								</div>
							</div>
							<div class="portlert-form-row">
								<label class="portlert-form-label"></label>
								<div class="portlert-form-collection">
									<input id="saveBtn" type="submit"
										class="portlert-form-button-disEdit" value="修改" />
									&nbsp;&nbsp; <input id="" type="reset"
										class="portlert-form-button-disEdit" value="重 置" />
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="bottom">
			<div class="left"></div>
			<div class="right">
				<div class="center"></div>
			</div>
		</div>
	</div>

	<!--引入提示告警和确认  -->
	<%@ include file="../common/alert_dialog.jsp"%>
	<%@ include file="../common/confirm_dialog.jsp"%>
	<jsp:include page="../common/script.jsp" flush="true" />
	<script language="javascript">
		$(function() {
			initAlertDialog("alertDialog");
			//权限验证
			if (validateRole('activity_pane', '0,1,2') == false)
				return;
			$(".edit").show();
			//新建或编辑验证
			$("#changePasswordForm").validate({
				rules : {
					originalpw:{required:true,maxlength:20,byteRangeLength:[0,20],stringEN:true,
						remote : {
							url : global_context+"/system/user/validatePW.do", //后台处理程序   
							type : "post", //数据发送方式
							dataType : "json" //接受数据格式   
						}
					},
					password : {
						required : true,
						minlength : 6,
						maxlength : 30,
						stringEN : true
					},
					confirmPassword : {
						required : true,
						minlength : 6,
						maxlength : 30,
						stringEN : true,
						equalTo : "#password"
					}
				},
				onkeyup : false,
				errorPlacement : function(error, element) { //指定错误信息位置
					error.appendTo(element.parent().children().filter("span"));
				},
				messages : {
					originalpw:{remote:"输入的原密码不正确"}
				}
			});
			var options = {
				success : function(data) {
					if (data == true) {
						alertJQ("操作成功，请重新登录！", 1000);
						setTimeout('reLogin()', 1000);
					} else if (data == false) {
						alertJQ("服务器出现问题，请联系管理人员");
					}
				}
			};
			// 将options传给ajaxForm
			$('#changePasswordForm').ajaxForm(options);
		});

		function reLogin() {
			$.post('<%=basePath%>logout.jsp',function(){
					parent.location.href =global_jsp_position+'/core/login.jsp';
			});	
		}
	</script>
</body>
</html>




