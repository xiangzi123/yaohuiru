<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
	<form id="modifyPrivilegeForm" method="post">
		<h1>修改权限</h1>
		<div class="portlert-form-list">
			<div class="portlert-form-row"  style="display: none;">
				<label class="portlert-form-label"><em>*</em>权限编号：</label>
				<div class="portlert-form-collection">
					<input id="modify_num" name="num" type="text"
						class="portlert-form-input-field" readOnly="readOnly"
						onfocus="this.blur()" /> <span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>权限名称：</label>
				<div class="portlert-form-collection">
					<input id="modify_name" name="name" type="text"
						class="portlert-form-input-field" /> <span
						class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>权限描述：</label>
				<div class="portlert-form-collection">
					<textarea id="modify_description" name="description"
						class="portlert-form-textarea" rows="3" cols=""></textarea>
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<!-- <div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>显示名称：</label>
				<div class="portlert-form-collection">
					<input id="modify_displayName" name="displayName" type="text"
						class="portlert-form-input-field" /> <span
						class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div> -->
			<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>权限 URL：</label>
				<div class="portlert-form-collection">
					<textarea id="modify_url" name="url" class="portlert-form-textarea"
						rows="3" cols=""></textarea>
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>权限类型：</label>
				<div class="portlert-form-collection">
				<select id ="modify_type" name="type" style="width:150px; height:25px;" onchange="addInputTarget(this);"></select>
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div id="authButton" class="portlert-form-row" style="display: none;">
				<label class="portlert-form-label"><em>*</em>按钮授权名：</label>
				<div class="portlert-form-collection">
					<input id="modify_target" name="target" type="text" class="portlert-form-input-field" /> 
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
		</div>
		<div>
			<input type="submit" id="subButt" value="确认" />
		</div>
	</form>
</div>


