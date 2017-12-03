<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>智能门锁信息管理系统</title>
<jsp:include page="../common/meta.jsp" flush="true" />
<jsp:include page="../common/style.jsp" flush="true" />

</head>
<body>
	<div class="leftSide">
		<div class="top_index">导航</div>
		<div class="nav">
			<c:forEach var="pri" items="${firstMenu}">
				<div class="menu_list" style="cursor: default;">
					<c:out value="${pri.name}"></c:out>
				</div>
				<c:if test="${pri.hasChild == true}">
					<dl id="dlLayout">
						<c:forEach var="spri" items="${pri.children}">
							<dt>
								<div class="icon1"
									<c:if test="${spri.hasChild == false}"> onclick="Menu('<%=menuPath%><c:out value="${spri.url}"></c:out>');"</c:if>>
									<c:out value="${spri.name}"></c:out>
								</div>
							</dt>
							<c:if test="${spri.hasChild == true}">
								<dd>
									<c:forEach var="tpri" items="${spri.children}">
										<a
											onclick="Menu('<%=menuPath %><c:out value="${tpri.url}"></c:out>');"><c:out
												value="${tpri.name}"></c:out>
										</a>
									</c:forEach>
								</dd>
							</c:if>
						</c:forEach>
					</dl>
				</c:if>
			</c:forEach>

<%-- 			<div class="menu_list">基础平台</div>
			<dl id="0">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>system/system_organ_main.jsp');">机构管理</div>
				</dt>

				<dt>
					<div class="icon2"
						onClick="Menu('<%=modulesPath%>system/system_user_main.jsp');">用户管理</div>
				</dt>
				<dt>
					<div class="icon3"
						onClick="Menu('<%=modulesPath%>system/system_role_main.jsp');">角色管理</div>
				</dt>


				<dt>
					<div class="icon4"
						onClick="Menu('<%=modulesPath%>system/system_privilege_main.jsp');">权限管理</div>
				</dt>

			</dl>--%>
<%--
			<div class="menu_list">施工信息</div>
			<dl id="menu4">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resou/resou_insta_main.jsp');">基础信息维护</div>
				</dt>
				<dt>
					<div class="icon2"
						onClick="Menu('<%=modulesPath%>resou/resou_insta_main.jsp');">施工情况查看</div>
				</dt>
			</dl>

<%--			<div class="menu_list">发布管理</div>
			<dl id="menu4">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>post/post_module_main.jsp');">版块管理</div>
				</dt>
				<dt>
					<div class="icon2"
						onClick="Menu('<%=modulesPath%>post/post_content_management_main.jsp');">内容管理</div>
				</dt>
			</dl>


			<div class="menu_list">预约管理</div>
			<dl id="appoint_manage">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resource/resource_resou_main.jsp');">预约资源管理</div>
				</dt>
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resource/resource_resou_apply_main.jsp');">预约申请</div>
				</dt>
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resource/resource_resou_approve_main.jsp');">预约审批</div>
				</dt>
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resource/resource_device_apply_main.jsp');">设备申请</div>
				</dt>
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>resource/resource_device_approve_main.jsp');">设备审批</div>
				</dt>
			</dl> --%>
			
			<%-- <div class="menu_list">发布查看</div>
			<dl id="menu4">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>post/post_notice_main.jsp');">通知</div>
				</dt>
				<dt>
					<div class="icon2"
						onClick="Menu('<%=modulesPath%>post/post_reminder_main.jsp');">温馨提示</div>
				</dt>
				<dt>
					<div class="icon3"
						onClick="Menu('<%=modulesPath%>post/post_commonDocument_main.jsp');">常用文档</div>
				</dt>
			</dl> --%>
			
			<%-- <div class="menu_list">教务管理</div>
			<dl id="menu4">
				<dt>
					<div class="icon1"
						onClick="Menu('<%=modulesPath%>teaching/teaching_mycourse_main.jsp');">我的课程</div>
				</dt>
			</dl> --%>
			
			
			
			
		</div>
	</div>
	<jsp:include page="../common/script.jsp" flush="true" />
	<script type="text/javascript">
		$(function() {
			/*点击一级菜单  */
			$("div.menu_list").click(
					function() {
						/* 如果有已经打开的一级菜单先收起来  */
						$(this).next().addClass("current"); //给dl加属性为了收起的时候不收本菜单      	   
						$(this).siblings().find("dd").slideUp("normal");
						$(this).siblings().find("dt").removeClass("active")
								.removeClass("current_row");
						$(this).siblings("dl").not(".current")
								.slideUp("normal");//收起其他展开的一级菜单
						$(this).next().removeClass("current");
						$(this).next().slideToggle("normal");/*点击的一级菜单展开或收起*/
					});
			/*点击 二级菜单 */
			$("dt").click(function() {
				/*$(this).find("dd").slideToggle("normal");
				  $(this).siblings().find("dd").slideUp("normal"); 
				     这种方法简单但当二级菜单收起时，是active状态，右侧显示减号*/
				if ($(this).hasClass("current_row")) {/* 点二级菜单时是为了收起的*/
					$(this).find("dd").slideUp("slow");
					$(this).removeClass("active").removeClass("current_row");
				} else {/*点二级是要下拉  */
					$(this).find("dd").slideDown("slow");
					$(this).addClass("current_row");
					$(this).siblings().find("dd").slideUp("slow");/*其他如果之前三级菜单有展开的 就把它收起来  */
					$(this).siblings().removeClass("current_row");
				}

			});
		});
	</script>
</body>

</html>
