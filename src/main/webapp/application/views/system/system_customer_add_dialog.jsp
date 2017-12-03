<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
  		 <form id="addcustomerForm" method="post">
  		 <div class="portlert-form-list">
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>客户姓名：</label>
			<div class="portlert-form-collection">
				<input id="customerName" name="customerName" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>	
		<div class="portlert-form-row">	
			<label class="portlert-form-label"><em>*</em>住所：</label>
			<div class="portlert-form-collection">
				<input id="residentId" name="residentId" class="portlert-form-input-field" />		
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>客户手机：</label>
			<div class="portlert-form-collection">
				<input id="telephone" name="telephone" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>身份证号：</label>
			<div class="portlert-form-collection">
				<input id="idcard" name="idcard" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label">所用业务：</label>
			<div class="portlert-form-collection">
				<input id="business" name="business" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label">业务办理时间：</label>
			<div class="portlert-form-collection">
				<input id="sTime" name="sTime" type="text" class="time" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label">业务结束时间：</label>
			<div class="portlert-form-collection">
				<input id="eTime" name="eTime" type="text" class="time" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		</div>
	</form>
</div>	

<script type="text/javascript" src="application/js/system/system_customer_main.js"></script>