<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="equipmentTypeModifyForm" method="post">
	<div class="portlert-form-list">
				<div class="portlert-form-row" style="display:none">
			<label class="portlert-form-label"><em>*</em>类型Id：</label>
			<div class="portlert-form-collection">
				<input id="equipmentId" name="equipmentId" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">
			<label class="portlert-form-label">类型名称：</label>
			<div class="portlert-form-collection">
				<input id="equipmentName" name="equipmentName" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>



<div class="portlert-form-row">
			<label class="portlert-form-label">电信设施类型：</label>
			<div class="portlert-form-collection">
				<select id="equipmentType" name="equipmentType" size="1" class="portlert-form-input-field" >
	            	<option value='楼宇设施'>楼宇设施</option>
	            	<option value='单元实施'>单元设施</option>
	            	<option value='住户实施'>住户设施</option>
         	    </select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
				<div class="portlert-form-row">
			<label class="portlert-form-label">设施描述：</label>
			<div class="portlert-form-collection">
				<input id="description" name="description" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
	</div>
	</form>
</div>	