$(function(){
	//权限验证
	//if(validateRole('activity_pane','0')== false){
	//	$(".content").empty();
	//	return;
	//}
	//主页面表格
	refreshAuth();
	$("#userList").jqGrid({
	    url:global_context+"/system/user/list.do",
	    datatype: "json",
	    mtype: "GET",
	    width:"100%",
	    height:"auto",
	    multiselect: true,
	    autowidth:true,
	    viewsortcols:[true,'vertical',true],
	    //multiselect:false, //是否在首列加入复选框
	    //multiselectWidth:30, //复选框的宽度
	    colModel : [
			{name:"userId",index:"user_id",label : "用户ID",align:"center", hidden:true,key:true},
			{name:"type",index:"type",label : "用户类型",align:"center", hidden:true},
			{name:"permitNum",index:"permit_num",label : "工作证号",width:20,align:"center",sortable:true},
	      	{name:"loginName",index:"login_name",label : "登录名",width:20,sortable:true,align:"center"},	      	
	        {name:"userName",index:"user_name",label : "姓&nbsp;名",width:15,sortable:true,align:"center"},
	        {name:"roleNames",index:"roleNames",label : "角&nbsp;色",width:30,sortable:false,align:"center"},
	        {name:"type",index:"type",label : "类&nbsp;型",width:15,sortable:false,align:"center" , hidden:true},
		    {name:"status",index:"status",label : "账号状态",width:15,sortable:false,align:"center"},
	        {name:"departmentName",index:"departmentName",label : "所属机构",width:30,sortable:false,align:"center" },
	        {name:"setup",label : "配&nbsp;置",width:30,sortable:false,align:"center" }
		 ],
	    pager: "#userListPager",	    
	    sortname: "user_id",
	    sortorder: "asc",
	    //rowNum:-1,
	    rowNum:15,
	    rowList:[15,30,50],
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
	    gridComplete:function(){  //在此事件中循环为每一行添加详细信息	     	
	    	var ids=$(this).jqGrid('getDataIDs'); 
			for(var i=0; i<ids.length; i++){
				var id=ids[i];     
				var setupstr = null;
				var rowData = $(this).jqGrid('getRowData',id);
				var typeValue = "\""+rowData.type+"\"";
				setupstr = "<img id='" + id + "' src='"+global_context+"/application/images/user_modify.gif' title='修改' class='auth' authKey = 'user_mod'  style='cursor:pointer;' onclick='modifyUserIn(this.id);'/>&nbsp;&nbsp;";
				setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/user_check.gif' title='查看' class='auth' authKey = 'user_check' style='cursor:pointer;' onclick='checkUser(this.id);'/>&nbsp;&nbsp;";
				if(rowData.status=="正常态"){
					setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/reset.gif' title='恢复密码' class='auth' authKey = 'user_reset' style='cursor:pointer;' onclick='recoverUserPassIn(this.id);'/>&nbsp;&nbsp;";
					setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/lockUser.gif' title='冻结' class='auth' authKey = 'user_lock' style='cursor:pointer;' onclick='lockUserCF(this.id);'/>&nbsp;&nbsp;";
					setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/expireUser.gif' title='废弃' class='auth' authKey = 'user_expire' style='cursor:pointer;' onclick='expireUserCF(this.id);'/>&nbsp;&nbsp;";
			//		setupstr += "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/user_del.gif' title='删除' style='cursor:pointer;' onclick='delUserCF(this.id);'/>";
				}else if(rowData.status=="冻结态"){
					setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/unlockUser.gif' title='解冻' class='auth' authKey = 'user_unlock' style='cursor:pointer;' onclick='unLockUserCF(this.id);'/>";
				}else if(rowData.status=="废弃态"){
					setupstr += "<img id='" + id + "' src='"+global_context+"/application/images/recoverUser.gif' title='启用' class='auth' authKey = 'user_restart' style='cursor:pointer;' onclick='recoverUserCF(this.id);'/>&nbsp;&nbsp;";
			//		setupstr += "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/user_del.gif' title='删除' style='cursor:pointer;' onclick='delUserCF(this.id);'/>";
				}
				$(this).jqGrid("setCell",id,"setup",setupstr,{'padding':'2px 0 0 0'});
			}
			refreshAuth();
			var bodyObj=document.getElementById('main-content');
			if(bodyObj.scrollHeight>bodyObj.clientHeight||bodyObj.offsetHeight>bodyObj.clientHeight){
				$("#main-content .toolBar").css('width',$(this).width()-12);
			}
        }              
  	});
  	//查询下拉列表初始化
  	$.post(global_context+"/system/user/info.do", function(data){
  		domsLoadData("query_form", data);
  	});
  	//功能配置信息对话框
  	$( "#userDialog" ).dialog({
  		title:'增加用户',
		autoOpen: false,
		width: 'auto',
		/*height:'400',*/
	 	modal: true,
		resizable:true,
		show: 'fade',
		hide: 'fade', 
 		close:function(){
 			$( this ).empty();
 		}
	});		
  	
	//导入弹窗 暂时注释 后面的系统应当根据逻辑修改
//	$( "#importDialog" ).dialog({
//		autoOpen: false,
//		width: '500',
//		height:'400',
//		modal: true,
//		resizable:true,
//		show: 'fade',
//		hide: 'fade',
//		buttons: {
//			"校验": validateExcel,
//			"确定": importExcel,
//			"取消": function() {
//				$( this ).dialog( "close" );
//			}
//		},
//		close:function(){
//			$( "#importDialog" ).empty();
//		}
//	});
	
	initConfirmDialog("confirmDialog");
	initAlertDialog("alertDialog");
});	

//保存当前选择的行Id
var selectId = "";

//增加用户入口函数
function addUserIn() {
	$('#activity_pane').showLoading();
	$("#userDialog").dialog('option', 'title', '新增用户');
	$("#userDialog").dialog('option', 'buttons', {
		"确定" : addUserOut,
		"取消" : function() {
			$(this).dialog("close");
		}
	});
	selectId = $("#userList").jqGrid("getGridParam", "selrow");
	//加载基本信息页面
	$("#userDialog").load(global_jsp_position+'/system/system_user_add_dialog.jsp',function(){
		$.post(global_context+"/system/user/info.do", addUserInCB);
		//多选框初始化
		initMultiSelect();
		//弹出部门树
		$("#add_organ").click(function() {
			initOrganTree();//system_organ_tree.js中定义 
			$('#organTreeDialog').dialog('option', 'buttons', {
				"确定" : function() {
					if (curOrgan != null)
						$("#add_organ").val(curOrgan.name);
						$("#add_organ_id").val(curOrgan.departmentId);
						$(this).dialog("close");
						curOrgan = null;
				},
				"取消" : function() {
					$(this).dialog("close");
					curOrgan = null;
				}
			});
			$("#organTreeDialog").dialog("open");
		});
	});
}

//新增用户入口回调
function addUserInCB(data) {
	//为表单填充数据
	domsLoadData("userAddForm", data);
	//增加表单验证
	$("#userAddForm").validate({
		rules : {
			loginName:{required:true,maxlength:20,byteRangeLength:[0,20],stringEN:true,
				remote : {
					url : global_context+"/system/user/validateLoginName.do", //后台处理程序   
					type : "post", //数据发送方式
					dataType : "json" //接受数据格式   
				}
			},
			permitNum:{required:true,maxlength:15,byteRangeLength:[0,15],isNumOrDot:true,
				remote : {
					url : global_context+"/system/user/validatePermitNum.do", //后台处理程序   
					type : "post", //数据发送方式
					dataType : "json" ,//接受数据格式   
					data:{oldPermitNum:" "}
				}
			},
			//password : {required : true,maxlength : 30,byteRangeLength : [ 6, 50 ],stringEN : true},
			//roleSelectR:{required:false},
			organName:{required:true},
			userName:{required:true,maxlength:30,byteRangeLength:[0,30]},
			telephone:{isPhone:true},
			contactNum:{isPhone:true},
			email:{email:true,maxlength:30,byteRangeLength:[0,30]},
			type:{required:true}
		},
		onkeyup : false,
		onclick : false,
		errorPlacement : function(error, element) { //指定错误信息位置
			error.appendTo(element.parent().children().filter("span"));
		},
		messages : {
			loginName:{remote:"此用户名称已存在"},
			permitNum:{remote:"此工作证号已存在"}
		},
		submitHandler : function() { //验证通过后调用此函数
			addUser();
		}
	});

	//注册增加ajax表单
	$("#userAddForm").ajaxForm();
	$('#activity_pane').hideLoading();
	$("#userDialog").dialog("open");
}

//新增用户出口
function addUserOut() {
	$("#roleSelectR").find("option").each(function(){
		$(this).attr("SELECTED","SELECTED");
	});
	$(":input").val().trim();
	$("#userAddForm").submit();
}
//新增用户
function addUser(){
	//禁用按钮
	disableButton();
	$('#activity_pane').showLoading();
	$("#userAddForm").ajaxSubmit({
		url : global_context+"/system/user/addUserOut.do",
		success : addUserOutCB
	});
}
//新增用户出口回调
function addUserOutCB(data) {
	//启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	if (data== true) {
		$("#userDialog").dialog("close");
		alertJQ("操作成功，请等待页面刷新！", 1000);
		$("#userList").trigger("reloadGrid");
	} else if (data == false) {
		alertJQ("对不起，操作失败，服务器出现问题！");
	}
}

//修改用户入口函数
function modifyUserIn(userId) {
	$('#activity_pane').showLoading();
	$("#userDialog").dialog('option', 'title', '修改用户');
	$("#userDialog").dialog('option', 'buttons', {
		"保存" : modifyUserOut,
		"取消" : function() {
			$(this).dialog("close");
		}
	});

	//加载基本信息页面
	$("#userDialog").load(global_jsp_position+'/system/system_user_modify_dialog.jsp',function() {
		$.post(global_context+"/system/user/info.do", function(data){
			domsLoadData("userModifyForm", data);
			$.post(global_context+"/system/user/modifyUserIn.do", {userId : userId}, modifyUserInCB);
		});

		$("#modify_organ").click(function() {
			initOrganTree();
			$('#organTreeDialog').dialog('option', 'buttons', {
				"确定" : function() {
					if (curOrgan != null)
						$("#modify_organ").val(curOrgan.name);
						$("#modify_organ_id").val(curOrgan.departmentId);
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			});
			$("#organTreeDialog").dialog("open");
			
			$("#query_Organ").val($("#modify_organ").val());
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var Node = treeObj.getNodeByParam('departmentId', $("#modify_organ_id").val());
			if ($("#query_Organ").val() != "") {
				treeObj.expandAll(false);
				treeObj.selectNode(Node);
				curOrgan = Node;
			}
		});
	});
}

//修改用户入口回调
function modifyUserInCB(data) {
//	$.post(global_context+"/system/user/canModifyType.do", {loginName:data["loginName"],type:data["type"]},function(data){
//		if(data==false){
//			$("#userModifyForm [name='type']").attr("disabled","disabled");
//			$("#userModifyForm #type").html("<em>*</em>该用户基本信息已填写，不能修改类型");
//		}
//	}); 暂时注释，业务逻辑后续增加
	//多选框初始化
	initMultiSelect();
	//为表单加载数据
	selectedRoles("userModifyForm", data);
	//修改表单验证
	$("#userModifyForm").validate({
		rules : {
			permitNum:{required:true,maxlength:15,byteRangeLength:[0,15],isNumOrDot:true,
				remote : {
					url : global_context+"/system/user/validatePermitNum.do", //后台处理程序   
					type : "post", //数据发送方式
					dataType : "json", //接受数据格式   
					data:{oldPermitNum:data["permitNum"]}
				}
			},
			//roleSelectR:{required:true},
			userName:{required:true,maxlength:30,byteRangeLength:[0,30]},
			organName:{required:true},
			telephone:{isPhone:true},
			contactNum:{isTel:true},
			email:{email:true,maxlength:200,byteRangeLength:[0,200]},
			type:{required:true}
		},
		onkeyup : false,
		onclick : false,
		errorPlacement : function(error, element) { //指定错误信息位置
			error.appendTo(element.parent().children().filter("span"));
		},
		messages : {
			permitNum:{remote:"此工作证号已存在"}
		},
		submitHandler : function() { //验证通过后调用此函数
			//禁用按钮
			disableButton();
			$('#activity_pane').showLoading();
			$("#userModifyForm [name='type']").removeAttr("disabled");
			$("#userModifyForm").ajaxSubmit({
				url : global_context+"/system/user/modifyUserOut.do",
				success : modifyUserOutCB
			});
		}
	});
	//注册增加用户ajax表单
	//$("#userModifyForm").ajaxForm();
	$('#activity_pane').hideLoading();
	$("#userDialog").dialog("open");
}

//修改用户出口
function modifyUserOut() {
	$("#roleSelectR").find("option").each(function(){
		$(this).attr("SELECTED","SELECTED");
	});
	$(":input").val().trim();
	$("#userModifyForm").submit();
}

//修改用户出口回调函数
function modifyUserOutCB(data) {
	//启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	$("#userList").trigger("reloadGrid");
	if (data == true) {
		$("#userDialog").dialog("close");
		alertJQ("操作成功，请等待页面刷新！", 1000);

	} else if (data==false) {
		alertJQ("系统内部错误，请谅解！将尽快修复");
	}
}

//查看用户函数
function checkUser(userId) {
	$('#activity_pane').showLoading();
	$("#userDialog").dialog('option', 'title', '查看用户');
	$("#userDialog").dialog('option', 'buttons', {
		"返回" :  function() {
			$(this).dialog("close");
		}
	});
	selectId = userId;
	//加载基本信息页面
	$("#userDialog").load(global_jsp_position+'/system/system_user_check_dialog.jsp',function() {
		$.post(global_context+"/system/user/info.do", function(data){
			domsLoadData("checkUserForm", data);
			$.post(global_context+"/system/user/checkUser.do", {userId : userId}, function(data){
				//为表单加载数据
				selectedRoles("checkUserForm", data);
				$('#activity_pane').hideLoading();
				$("#userDialog").dialog("open");
			});
		});
	});
}

//确认删除
function delUserCF(userId) {
	confirmJQ("确认删除此用户吗？", delUser, userId);
}

//删除试卷生成策略
function delUser(userId) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/user/deleteUser.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("操作成功，请等待页面刷新", 1000);
			$("#userList").jqGrid("delRowData", userId);
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		}
	});
}

//重置用户密码入口
function recoverUserPassIn(userId) {
	confirmJQ("是否要恢复该用户的初始密码？", recoverUserPass, userId);
}

//重置用户密码策略
function recoverUserPass(userId) {
	$('#activity_pane').showLoading();

	$.post(global_context+"/system/user/recoverUserPass.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("恢复成功，初始密码为“88888888”", 1000);
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		}
	});
}	

function multiLock(){
	var userIds = jQuery('#userList').jqGrid('getGridParam','selarrrow');
	/*var userIds = new Array(); 
    if(rowData.length) 
    {
        for(var i=0;i<rowData.length;i++)
        { 
        	userIds.push(jQuery('#userList').jqGrid('getCell',rowData[i],'userId'));//name是colModel中的一属性 
        }
    }*/
    if(userIds.length!=0)
    	confirmJQ("是否要冻结用户？",multiLockUser,userIds);
    else
    	alertJQ("未选择用户",1000);
}

function multiLockUser(userIds){
	var idString = "";
	for(var i = 0;i < userIds.length;i++)
	{
		idString += userIds[i]+",";
	}
	$.post(global_context+"/system/user/multiLockUser.do", {userIds : idString}, function(data) {	
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("冻结成功，用户将无法登陆", 1000);
			$("#userList").trigger("reloadGrid");
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		} else alertJQ(data);
	});
}

//冻结用户入口
function lockUserCF(userId) {
	confirmJQ("是否要冻结用户？", lockUser, userId);
}

//冻结用户策略
function lockUser(userId) {
	$('#activity_pane').showLoading();

	$.post(global_context+"/system/user/lockUser.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("冻结成功，用户将无法登陆", 1000);
			$("#userList").trigger("reloadGrid");
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		} else alertJQ(data);
	});
}	

//解冻用户入口
function unLockUserCF(userId) {
	confirmJQ("是否要解冻用户？", unLockUser, userId);
}

//解冻用户策略
function unLockUser(userId) {
	$('#activity_pane').showLoading();

	$.post(global_context+"/system/user/unLockUser.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("解冻成功，用户将可以登陆", 1000);
			$("#userList").trigger("reloadGrid");
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		}else alertJQ(data);
	});
}	

//废弃用户入口
function expireUserCF(userId) {
	confirmJQ("是否要废弃该用户？", expireUser, userId);
}

//废弃用户策略
function expireUser(userId) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/user/expireUser.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("废弃成功，用户将无法登陆", 1000);
			$("#userList").trigger("reloadGrid");
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		}else alertJQ(data);
	});
}	

//启用用户入口
function recoverUserCF(userId) {
	confirmJQ("是否要启用该用户？", recoverUser, userId);
}

//启用用户策略
function recoverUser(userId) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/user/recoverUser.do", {userId : userId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == true) {
			alertJQ("启用成功，用户将可以登陆", 1000);
			$("#userList").trigger("reloadGrid");
		} else if (data == false) {
			alertJQ("系统内部错误，请谅解！将尽快修复");
		}else alertJQ(data);
	});
}	



//查询
function query() {

	var postData = {
		permitNum : $("#query_permitNum").val().trim(),
		loginName : $("#query_loginName").val().trim(),
		userName : $("#query_name").val().trim(),
		role : $("#query_role").val().trim(),
		organ : $("#query_organ").val().trim(),
		status:$("#query_status").val().trim()
	};
	//合并数据 	
	postData = $.extend($("#userList").getGridParam("postData"), postData);
	//将合并后的数据设置到表格属性中，记得加search:true 
	$("#userList").setGridParam({
		search : true,
		postData : postData
	});
	$("#userList").trigger("reloadGrid", [ {
		page : 1
	} ]);

}

function domsLoadData(formId , data){
  		var id;
  		var name = "name";
  		for(var obj in data){
  			if($("#"+formId+" [name="+obj+"]") && $("#"+formId+" [name="+obj+"]").length>0){
  				var selectObj = $("#"+formId+" [name="+obj+"]");
  				if(obj=="organ") id="departmentId";//具体问题要变化，按照返回的json对象进行判断
  				else if(obj=="role") id ="roleId";
  				//填充下拉框
  				if(selectObj.get(0).tagName=="SELECT")
  				    for(var i=0;i<data[obj].length; i++){
  				    	if(data[obj][i][id]!=null&&data[obj][i][id]!=""){
  				    		if(data[obj][i]["selected"]==true)
  								selectObj.get(0).add(new Option(data[obj][i][name], data[obj][i][id], false, true));
  							else 
  								selectObj.get(0).add(new Option(data[obj][i][name], data[obj][i][id]));
  				    	}else{
  				    		if(data[obj][i]["selected"]==true)
  								selectObj.get(0).add(new Option(data[obj][i][name],"", false, true));
  							else 
  								selectObj.get(0).add(new Option(data[obj][i][name],""));
  				    	}
  				    }
  				else if(selectObj.get(0).tagName=="INPUT")
  					//填充文本框
  					if((selectObj.attr("type")!="file")&&(selectObj.attr("hidden")!="true"))//忽略上传控件
  						selectObj.val(data[obj]);
  				}
  			}
}

//决定角色方位
function selectedRoles(formId , data){
	for(var obj in data){
		if(obj=="roleSelectR"&&data[obj]!=null){
			var strs = data[obj].split(",");
			$("#roleSelectL").val(strs).attr("selected",true).dblclick();
		}
		else if(data[obj]!=null && obj!="sex") $("#"+formId+" [name="+obj+"]").val(data[obj]);
		else $("input[name=sex][value="+data[obj]+"]").attr("checked",'checked');
	}
}
//初始化多选框
function initMultiSelect(){

	var leftSel = $("#roleSelectL");
	var rightSel = $("#roleSelectR");
	$("#toright").bind("click",function(){		
		leftSel.find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	$("#toleft").bind("click",function(){		
		rightSel.find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
	leftSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
	rightSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(leftSel);
		});
	});
	
	$("#add_organ").bind("focus",function(){	
		rightSel.find("option").each(function(){
			$(this).attr("SELECTED","SELECTED");
		});
		rightSel.blur();
	});
	$("#modify_organ").bind("focus",function(){	
		rightSel.find("option").each(function(){
			$(this).attr("SELECTED","SELECTED");
		});
		rightSel.blur();
	});
 }

