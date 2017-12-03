<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="modifyRoleForm" method="post">
  	 <div class="portlert-form-list">
		<div class="portlert-form-row" style="display:none">
			<label class="portlert-form-label"><em>*</em>角色Id：</label>
			<div class="portlert-form-collection">
				<input id="roleId" name="roleId" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
	<div class="portlert-form-list">
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>角色名：</label>
			<div class="portlert-form-collection">
				<input id="name" name="name" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
	</div>
	<div class="portlert-form-row" style="display:none">
	        <label class="portlert-form-label"><em>*</em>角色权限配置：</label>
			<div class="portlert-form-collection">
				<select id="roleSelectL" multiple="multiple" name="privilege" size="8" style="float:left;width:185px">
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
	<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>角色描述：</label>
			<div class="portlert-form-collection">
				<input id="description" name="description" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
	<div class="portlert-form-row" style="display:none">
			<label class="portlert-form-label">父节点：</label>
			<div class="portlert-form-collection">
				<input id="parentId" name="parentId" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
	</div>	
</div>
	</form>
</div>	


