<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<div class="section">
  	 <form id="addTaskForm" method="post">
	<div class="portlert-form-list">
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>施工单编号：</label>
			<div class="portlert-form-collection">
				<input id="taskId" name="taskId" type="text" class="portlert-form-input-field" />
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
	
	    
	    <div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>施工日期：</label>
				<div class="portlert-form-collections">
					<input id="date" name="date" type="text" class="portlert-form-input-field" />
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
		</div>
		
		 
	    <div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>施工人：</label>
				<div class="portlert-form-collections">
					<input id="personId" name="person_id" type="text" class="portlert-form-input-field" />
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
		</div>
		
				 
	    <div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>施工住户：</label>
				<div class="portlert-form-collections">
					<input id="residentId" name="resident_id" type="text" class="portlert-form-input-field" />
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
		</div>
		
				 
	  	<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>施工类型：</label>
			<div class="portlert-form-collection">
				<select id="type" name="type" size="1" class="portlert-form-input-field" >
	                <option value=''>请选择</option>
	            	<option value='true'>开机</option>
	            	<option value='false'>维修</option>
         	    </select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		
				 
	 	<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>完成情况：</label>
			<div class="portlert-form-collection">
				<select id="situation" name="situation" size="1" class="portlert-form-input-field" >
	            	<option value=''>请选择</option>
	            	<option value='true'>已完成</option>
	            	<option value='false'>未完成</option>
         	    </select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
	
	    
		<div class="portlert-form-row">
				<label class="portlert-form-label"><em>*</em>个人评价：</label>
				<div class="portlert-form-collection">
					<input id="summary" name="summary" type="text" class="portlert-form-input-field" />
					<span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
		</div>
		<div class="portlert-form-row">
			<label class="portlert-form-label"><em>*</em>回访状态：</label>
			<div class="portlert-form-collection">
				<select id="situation" name="situation" size="1" class="portlert-form-input-field" >
	            	<option value=''>请选择</option>
	            	<option value='true'>已回访</option>
	            	<option value='false'>未回访</option>
         	    </select>
				<span class="portlert-form-msg-alert">&nbsp;</span>
			</div>
		</div>
		
	</div>		
	</form>
</div>	


