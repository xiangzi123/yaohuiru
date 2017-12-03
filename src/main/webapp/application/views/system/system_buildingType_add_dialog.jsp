<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="addbuildingTypeForm" method="post" enctype="multipart/form-data">
	<div class="portlert-form-list">

		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>类型名称：</label>
			<div class="portlert-form-collection">
				<input id="typeName" name="typeName" type="text" class="portlert-form-input-field"  />
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
				<input type='text' name='planGraphText' id='addSite_planGraphText' class='portlert-form-input-field' />
				<div class="portlert-form-content" >
					<input type="file" name="planGraph" id="addSite_planGraph" size="28"  class="portlert-form-content-input" onchange="document.getElementById('addSite_planGraphText').value=this.value; fileValidate('addSite_planGraph');" />
				</div>
				<span class="portlert-form-msg-alert">&nbsp;</span>
       		</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label"></label>
			<div class="portlert-form-collection">
				<span class="portlert-form-msg-note">图片格式为jpg、png、gif，大小不要超过1MB!</span>
			</div>
		</div>
		<div class="divide"></div>

	</div>
	</form>
</div>	
