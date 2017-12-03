$(function(){
	refreshAuth();
	$("#logList").jqGrid({
	    url:global_context+"/system/log/list.do",
	    datatype: "json",
	    mtype: "GET",
	    width:"100%",
	    height:"auto",
	    autowidth:true,
	    viewsortcols:[true,'vertical',true],
	    colModel : [   
			{name:"user",index:"user",label : "用户",width:20,align:"center"},
			{name:"discription",index:"discription",label : "用户姓名",width:20,align:"center", key:true},
	      	{name:"ip",index:"ip",label : "IP地址",width:20,sortable:true,align:"center"},	      	
	        {name:"date",index:"date",label : "时&nbsp;间",width:20,sortable:true,align:"center"},
	        {name:"action",index:"action",label : "行&nbsp;为",width:20,sortable:false,align:"center"},
	        {name:"module",index:"module",label : "所属模块",width:20,sortable:false,align:"center" }
		 ],
	    pager: "#logListPager",	    
	    sortname: "date",
	    sortorder: "desc",
	    rowNum:10,
	    rowList:[10,20,50],
	    viewrecords: true,
	    gridview: true,
	    pgbuttons:true,
	    pginput:true,
	    prmNames : {  
		    page:"pageNo",     //表示请求页码的参数名称  
		    rows:"rows",     //表示请求行数的参数名称  
		    totalrows:"totalrows", // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal  
			search:"search",// 表示是否是搜索请求的参数名称
			sort:"sortName",// 表示用于排序的列名的参数名称  
		    order:"sortOrder"// 表示采用的排序方式的参数名称
		},
		jsonReader: {  
	        root:"contents", 
	        page: "pageNo",
	        total: "totalPages",
	        records: "totalRecords",
	        id:"pageNo",
	        repeatitems : false 
	    },
	    gridComplete:function(){ 
			var bodyObj=document.getElementById('main-content');
			if(bodyObj.scrollHeight>bodyObj.clientHeight||bodyObj.offsetHeight>bodyObj.clientHeight){
				$("#main-content .toolBar").css('width',$(this).width()-12);
			}
	    }
  	});
	$("#logDialog").dialog({
		title:'导出日志',
		autoOpen:false,
		width: 'auto',
		//height:550,
		modal: true,
		resizable:true,
		show: 'fade',
		hide: 'fade',
 		close:function(){
 			$( this ).empty();
 		}
	});
 	initConfirmDialog("confirmDialog");
 	initAlertDialog("alertDialog");

});

$(function(){	
  	$( ".time" ).datetimepicker({
	  		showSecond: false,
	  		dateFormat: 'yy-mm-dd',
	  		timeFormat:	'HH:mm',
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

function query() {
		var startTime = $("#start_time").val();
  		var endTime = $("#end_time").val();
  		var user = $("#query_user").val();
  		var module = $("#query_module").val();
	var postData = {
		user :$.trim(user),
		startTime:$.trim(startTime),
		endTime:$.trim(endTime),
		module :$.trim(module)
	};
	if(startTime <= endTime){
		//合并数据 	
		postData = $.extend($("#logList").getGridParam("postData"), postData);
		//将合并后的数据设置到表格属性中，记得加search:true 
		$("#logList").setGridParam({
			search : true,
			postData : postData
		});
		$("#logList").trigger("reloadGrid", [ {
			page : 1
		} ]);	
	}else{
		alertJQ("开始时间不可大于结束时间！");
	}


}
function exportLogIn(){
	$("#activity_pane").showLoading();
	$("#logDialog").dialog('option','title','导出日志');
	$("#logDialog").dialog('option','buttons',{
		"确定":exportLogOut,
		"取消":function(){
			$(this).dialog("close");
		}
	});
	selectId = $("#logList").jqGrid("getGridParam", "selrow");
	$("#logDialog").load(global_jsp_position+'/system/system_log_export_dialog.jsp',function(data){
		exportLogInCB();
	});
}
function exportLogInCB(){
	$("#exportLogForm").validate({
		rules:{
			endtime:{
				endTime:true
			}
		},
		onkeyup : false,
		onclick : false,
		errorPlacement : function(error, element) { //指定错误信息位置
			error.appendTo(element.parent().children().filter("span"));
		},
		messages : {
/*			resType:{remote:"必填字段"},
			place:{remote:"必填字段"},
			approver:{remote:"必填字段"},
			remark:{remote:"最多可填100字"}*/
		},
		submitHandler : function() { //验证通过后调用此函数
			exportLog();
		}
	});
	//注册增加ajax表单
	$("#exportLogForm").ajaxForm();
	$('#activity_pane').hideLoading();
	$("#logDialog").dialog("open");
}
//时间先后的校验
jQuery.validator.addMethod("endTime", function(value, element) {
	var startTime = $('#starttime').val();
	return startTime <= value;
}, "必须大于开始时间");

function exportLog(){
	disableButton();
	$('#activity_pane').showLoading();
	$("#exportLogForm").ajaxSubmit({
		url : global_context+"/system/log/exportLog.do",
		success : exportLogOutCB
	});
}
//新增用户出口
function exportLogOut() {
	$(":input").val().trim();
	$("#exportLogForm").submit();
}
function exportLogOutCB(data){
	enableButton();
	$('#activity_pane').hideLoading();
	if (data== true) {
		$("#logDialog").dialog("close");
		alertJQ("日志文件已经导出到C盘logexport文件夹，如需修改路径请联系管理员。", 3000);
		$("#logList").trigger("reloadGrid");
	} else if (data == false) {
		alertJQ("对不起，操作失败，服务器出现问题！");
	}
}
