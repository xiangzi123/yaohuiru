<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="buildingTypeModifyForm" method="post">
  	 <div class="portlert-form-row" style="display:none">
			<label class="portlert-form-label"><em>*</em>类型Id：</label>
			<div class="portlert-form-collection">
				<input id="typeId" name="typeId" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
	<div class="portlert-form-list">
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em></em>类型名称：</label>
			<div class="portlert-form-collection">
				<input id="typeName" name="typeName" type="text" class="portlert-form-input-field" />
			<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>


		<div class="portlert-form-row">
			<label class="portlert-form-label">类型描述：</label>
			<div class="portlert-form-collection">
				<input id="typeDes" name="typeDes" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
				<div class="portlert-form-row">
			<label class="portlert-form-label">楼宇图标：</label>
			<div class="portlert-form-collection">
				<input id="icon" name="icon" type="text" class="portlert-form-input-field" />
				<div class="portlert-form-content" >
					<input type="file" name="planGraph" id="addSite_planGraph" size="28"  class="portlert-form-content-input" onchange="document.getElementById('addSite_planGraphText').value=this.value; fileValidate('addSite_planGraph');" />
				</div>
				<span class="portlert-form-msg-alert">&nbsp;</span>
       		</div>
		</div>	
	</div>
	</form>
</div>	