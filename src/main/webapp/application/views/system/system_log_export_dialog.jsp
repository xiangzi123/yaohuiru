<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="section">
	<form id="exportLogForm" method="post">
		<div class="portlert-form-list">
			<div class="portlert-form-row">
				<label class="portlert-form-label">选择模块：</label>
				<div class="portlert-form-collection">
					<select id="module" name="module" class="portlert-form-input-field">
						<option value=''>--请选择--</option>
						<option value="机构管理模块">机构管理</option>
						<option value="用户管理模块">用户管理</option>
						<option value="角色管理模块">角色管理</option>
						<option value="权限管理模块">权限管理</option>
						<option value="学院工资模块">学院工资</option>
						<option value="预约管理模块">预约管理</option>
						<option value="发布管理模块">发布管理</option>
					</select> <span class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div class="portlert-form-row">
				<label class="portlert-form-label">选择开始时间：</label>
				<div class="portlert-form-collection">
					<input id="starttime" name="starttime" type="text"
						class="portlert-form-input-field time" /> <span
						class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
			<div class="portlert-form-row">
				<label class="portlert-form-label">选择结束时间：</label>
				<div class="portlert-form-collection">
					<input id="endtime" name="endtime" type="text"
						class="portlert-form-input-field time" /> <span
						class="portlert-form-msg-alert">&nbsp;</span>
				</div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){	
  	$( ".time" ).datetimepicker({
	  		showSecond: true,
	  		dateFormat: 'yy-mm-dd',
	  		timeFormat:	'HH:mm:ss',
			dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
			dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
			dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
			firstDay: 1,
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			monthNamesShort:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			showButtonPanel: true,
			currentText: '当前时间',
			closeText: '关闭',
			changeMonth: true,
			changeYear: true,
/* 			beforeShow: function (input, inst) {  
       			    inst.dpDiv.css({marginTop: -input.offsetHeight*8 + 'px', marginLeft: -input.offsetWidth*2 + 'px'});
           }, */
           onSelect: function() {
        	   $(this).change();
    	  }
		}
  	);
	});

</script>


