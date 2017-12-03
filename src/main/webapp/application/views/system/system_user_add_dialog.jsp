<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="userAddForm" method="post">
	<div class="portlert-form-list">
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>登录名称：</label>
			<div class="portlert-form-collection">
				<input id="loginName" name="loginName" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>工作证号：</label>
			<div class="portlert-form-collection">
				<input id="add_permitNum" name="permitNum" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">	
			<label class="portlert-form-label"><em>*</em>所属机构：</label>
			<div class="portlert-form-collection">
				<input id="add_organ" name="organName" class="portlert-form-input-field" readonly='readonly'/>	
				<input id="add_organ_id" name="organ" type="hidden"/>	
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>用户姓名：</label>
			<div class="portlert-form-collection">
				<input id="userName" name="userName" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>用户类型：</label>
			<div class="portlert-form-collection">
				<select id="add_type" name="type" size="1" class="portlert-form-input-field" >
	            	<option value='student'>教学人员</option>
	            	<option value='teacher'>行政人员</option>
         	    </select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label">用户性别：</label>
			<div class="portlert-form-collection">
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id ="add_sex" type="radio" name="sex" value="true" checked="checked"  />男</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input type="radio" name="sex" value="false" />女</label>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label">手机号码：</label>
			<div class="portlert-form-collection">
				<input id="add_phone" name="telephone" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label">电话号码：</label>
			<div class="portlert-form-collection">
				<input id="add_contact_num" name="contactNum" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label">E_mail：</label>
			<div class="portlert-form-collection">
				<input id="add_e_mail" name="email" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
	        <label class="portlert-form-label">用户角色配置：</label>
			<div class="portlert-form-collection">
				<select id="roleSelectL" multiple="multiple" name="role" size="8" style="float:left;width:185px">
				</select>
				<div class="select_opt">
					<p id="toright" title="添加">&gt;</p>
					<br />
					<p id="toleft" title="移除">&lt;</p>
					<input type="hidden" id="edit_userRoles" name="userRoles">
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
				<select id="roleSelectR" multiple="multiple" name="roleSelectR" size="8" style="float:left;width:185px">
				</select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
	    </div>	
	</div>
	</form>
</div>	