<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="checkRoleForm" method="post">
		<div class="portlert-form-list" >
			<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>角色ID：</label>
				<div class="portlert-form-collection">
					<input id="role_id" name="roleId" type="text" class="portlert-form-input-field" readonly="readonly"/>
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>	
			<div class="portlert-form-list">
				<div class="portlert-form-row">
					<label class="portlert-form-label"><em>*</em>角色名：</label>
					<div class="portlert-form-collection">
						<input id="name" name="name" type="text" class="portlert-form-input-field" readonly="readonly"/>
						<span class="portlert-form-msg-alert">&nbsp;</span>
					</div>
				</div>	
			</div>
			<div class="portlert-form-row">
	        <label class="portlert-form-label">角色权限：</label>
				<div class="portlert-form-collection" style="display:none">
					<select id="roleSelectL" multiple="multiple" name="privilege" size="8" style="float:left;width:185px">
					</select>
				</div>
				<div class="portlert-form-collection" >
					<select id="roleSelectR" disabled="disabled" multiple="multiple" name="roleSelectR" size="8" style="float:left;width:185px">
					</select>
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
	   	 	</div>	
			<div class="portlert-form-row">
				<label class="portlert-form-label">角色描述：</label>
				<div class="portlert-form-collection">
					<input id="description" name="description" type="text" class="portlert-form-input-field" readonly="readonly"/>
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


