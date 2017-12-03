$(function(){
	refreshAuth();
	$("#roleList").jqGrid({
	    url:global_context+"/system/role/list.do",
	    datatype: "json",
	    mtype: "GET",
	    width:"100%",
	    height:"auto",
	    autowidth:true,
	    viewsortcols:[true,'vertical',true],
	    //multiselect:false, //是否在首列加入复选框
	    //multiselectWidth:30, //复选框的宽度
	    colModel : [   
			{name:"roleId",index:"role_id",label : "角色ID",width:30,align:"center", key:true,hidden:true},
	      	{name:"name",index:"name",label : "角色名",width:30,sortable:true,align:"center"},
	      	{name:"description",index:"description",label : "角色描述",width:30,sortable:false,align:"center"},
	      	{name:"setup",label : "配&nbsp;置",width:30,sortable:false,align:"center" }
		 ],
	    pager: "#roleListPager",	    
	    sortname: "role_id",
	    sortorder: "asc",
	    rowNum:15,
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
	    gridComplete:function(){  //在此事件中循环为每一行添加详细信息	     	
	    	var ids=$(this).jqGrid('getDataIDs'); 
			for(var i=0; i<ids.length; i++){
				var id=ids[i];     
				var setupstr = "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/user_modify.gif' title='修改' class='auth' authKey = 'role_mod' style='cursor:pointer;' onclick='modifyRoleIn(this.id);'/>&nbsp;&nbsp;";
				setupstr += "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/user_check.gif' title='查看' class='auth' authKey = 'role_check' style='cursor:pointer;' onclick='checkRole(this.id);'/>&nbsp;&nbsp;";
				setupstr += "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/modify.png' title='设置权限' class='auth' authKey = 'role_set' style='cursor:pointer;' onclick='addPrivilegeIn(this.id);'/>&nbsp;&nbsp;";
				setupstr += "<img id='" + id + "' src='"+global_context+"/application/template/themes/base/images/user_del.gif' title='删除' class='auth' authKey = 'role_del' style='cursor:pointer;' onclick='delRoleCF(this.id);'/>";
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
	//权限列表
	$.post(global_context+"/system/role/info.do", function(data){
  		domsLoadData("query_form", data);
  	});
	//机构列表
	$.post(global_context+"/system/user/info.do", function(data){
  		domsLoadData("query_form", data);
  	});
  	//功能配置信息对话框
  	$( "#roleDialog" ).dialog({
  		title:'增加角色',
		autoOpen: false,
		width: 'auto',
		//height:450,
		modal: true,
		resizable:true,
		show: 'fade',
		hide: 'fade',
 		close:function(){
 			$( this ).empty();
 		}
	});	
  	$( "#privilegeDialog" ).dialog({
  		title:'配置权限',
		autoOpen: false,
		width: 'auto',
		height:'500',
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

//保存当前选择的行Id
var selectId = "";

//增加角色入口
function addRoleIn() {
	//showLoading提供加载等待的效果
	$('#activity_pane').showLoading();
	$("#roleDialog").dialog('option', 'title', '新增角色');
	$("#roleDialog").dialog('option', 'buttons', {
		"确定" : addRoleOut,
		"取消" : function() {
			$(this).dialog("close");
		}
	});
	selectId = $("#roleList").jqGrid("getGridParam", "selrow");
	//加载基本信息页面
	$("#roleDialog").load(global_jsp_position+'/system/system_role_add_dialog.jsp',function(){	
		$.post(global_context+"/system/role/info.do",  addRoleInCB);
		//多选框初始化
		initMultiSelect();
	});
}

//新增用户入口回调
function addRoleInCB(data) {
	//为表单填充数据
	domsLoadData("addRoleForm", data);
	//增加表单验证
	$("#addRoleForm").validate({
		rules : {
			name:{required:true,maxlength:30,byteRangeLength:[0,30],
				remote:{
					url:global_context+"/system/role/validateRoleName.do", //后台处理程序   
					type : "post", //数据发送方式
					dataType : "json", //接受数据格式   
					data:{oldRoleName:" "}//此处的空格字符串不能删除，兼容IE10和IE11
				}
			},
			description:{required:true,maxlength:200,byteRangeLength:[0,200]},
		},
		onkeyup : false,
		onclick : false,
		errorPlacement : function(error, element) { //指定错误信息位置
			error.appendTo(element.parent().children().filter("span"));
		},
		messages : {
			name:{remote:"此角色名已存在"}
		},
		submitHandler : function() { //验证通过后调用此函数
			addRole();
		}
	});

	//注册增加ajax表单
	$("#addRoleForm").ajaxForm();
	$('#activity_pane').hideLoading();
	$("#roleDialog").dialog("open");
}

//新增用户出口
function addRoleOut() {
	$("#roleSelectR").find("option").each(function(){
		$(this).attr("SELECTED","SELECTED");
	});
	$(":input").val().trim();
	$("#addRoleForm").submit();
}
//新增用户，向后台发出请求
function addRole(){
	//禁用按钮
	disableButton();
	$('#activity_pane').showLoading();
	$("#addRoleForm").ajaxSubmit({
		url : global_context+"/system/role/addRole.do",
		success : addRoleOutCB
	});
}
//新增用户出口回调
function addRoleOutCB(data) {
	//启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	if (data== true) {
		$("#roleDialog").dialog("close");
		alertJQ("操作成功，请等待页面刷新！", 1000);
		$("#roleList").trigger("reloadGrid");
	} else if (data == false) {
		alertJQ("服务器异常，请通知管理人员");
	}
}

//ƒ用户入口函数
function modifyRoleIn(roleId) {
	$('#activity_pane').showLoading();
	$("#roleDialog").dialog('option', 'title', '修改角色');
	$("#roleDialog").dialog('option', 'buttons', {
		"保存" : modifyRoleOut,
		"取消" : function() {
			$(this).dialog("close");
		}
	});
	//加载基本信息页面
	$("#roleDialog").load(global_jsp_position+'/system/system_role_modify_dialog.jsp',function() {
		$.post(global_context+"/system/role/info.do", function(data){
			domsLoadData("modifyRoleForm", data);
			//根据id获取行数据,返回的是列表
			var rowDatas = $("#roleList").jqGrid('getRowData', roleId);
			//取到选中行某一字段的值，其中name为colModel中定义的字段名
			var rowName = rowDatas["name"];
			if(rowName=="资源申请审批人"||rowName=="教师"||rowName=="学生") {
				$("#modifyRoleForm [name='name']").attr('readonly','readonly'); 
			  //  var v= $("#modifyRoleForm #name+span").text();
				$("#modifyRoleForm #name+span").text("该角色名不能修改");
			}

		});
		$.post(global_context+"/system/role/modifyRoleIn.do", {roleId : roleId}, modifyRoleInCB);
		
		initMultiSelect();
	});
}

//修改用户入口回调
function modifyRoleInCB(data) {
	//为表单加载数据
	selectedRoles("modifyRoleForm", data);
	//修改表单验证
	$("#modifyRoleForm").validate({
		rules : { 
			name:{required:true,maxlength:30,byteRangeLength:[0,30],
				remote:{
					url:global_context+"/system/role/validateRoleName.do", //后台处理程序   
					type : "post", //数据发送方式
					dataType : "json", //接受数据格式   
					data:{oldRoleName:data["name"]}
				}
			},
			description:{required:true,maxlength:200,byteRangeLength:[0,200]},
			parentId:{required:false},
		},
		onkeyup : false,
		onclick : false,
		errorPlacement : function(error, element) { //指定错误信息位置
			error.appendTo(element.parent().children().filter("span"));
		},
		messages : {
			name:{remote:"此角色名已存在"}
		},
		submitHandler : function() { //验证通过后调用此函数
			//禁用按钮
			disableButton();
			$('#activity_pane').showLoading();

			$("#modifyRoleForm").ajaxSubmit({
				url : global_context+"/system/role/modifyRoleOut.do",
				success : modifyRoleOutCB
			});
		}
	});
	//注册增加用户ajax表单
	$("#modifyRoleForm").ajaxForm();
	$('#activity_pane').hideLoading();
	$("#roleDialog").dialog("open");
}

//修改用户出口
function modifyRoleOut() {
	$("#roleSelectR").find("option").each(function(){
		$(this).attr("SELECTED","SELECTED");
	});
	$(":input").val().trim();
	$("#modifyRoleForm").submit();
}

//修改用户出口回调函数
function modifyRoleOutCB(data) {
	//启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	$("#roleList").trigger("reloadGrid");
	if (data == true) {
		$("#roleDialog").dialog("close");
		alertJQ("操作成功，请等待页面刷新！", 1000);

	} else if (data==false) {
		alertJQ("服务器异常，请通知管理人员");
	}
}

//查看用户函数
function checkRole(roleId) {
	$('#activity_pane').showLoading();
	$("#roleDialog").dialog('option', 'title', '查看角色');
	$("#roleDialog").dialog('option', 'buttons', {
		"返回" :  function() {
			$(this).dialog("close");
		}
	});
	selectId = roleId;
	//加载基本信息页面
	$("#roleDialog").load(global_jsp_position+'/system/system_role_check_dialog.jsp',function() {
		$.post(global_context+"/system/role/info.do", function(data){
			domsLoadData("checkRoleForm", data);
			$.post(global_context+"/system/role/checkRole.do", {roleId : roleId}, function(data){
				//为表单加载数据
				//domsLoadData("checkRoleForm", data);
				initMultiSelectForCheck();
				selectedRoles("checkRoleForm", data);
				$('#activity_pane').hideLoading();
				$("#roleDialog").dialog("open");
			});
		});
	});
}


var roleId;
//设置角色权限
function addPrivilegeIn(roleIdTemp){
	roleId = roleIdTemp;
	$('#activity_pane').showLoading();
	$("#privilegeDialog").dialog('option', 'title', '设置权限');
	$("#privilegeDialog").dialog('option', 'buttons', {
		"保存" : savePrivilegeChange,
		"返回" :  function() {
			$(this).dialog("close");
		}
	});
	
	selectId = roleId;
	
	//加载基本信息页面
	$("#privilegeDialog").load(global_jsp_position+'/system/system_role_addPrivilege_dialog.jsp',function() {
		$('#activity_pane').hideLoading();
		$("#treeDemo").css({"height":"375"});
		$("#privilegeDialog").dialog("open");
		$.post(global_context+"/system/role/modifyRoleIn.do", {roleId : roleId},simpleResult);
	});
};

//选中该角色拥有的权限节点
function simpleResult(data){
	var result = data.roleSelectR;
	var extendResult = "";
	if(result != null){
		extendResult = result.split(",");
	}
	var zNodes;
	var setting = {
		check: {
				enable: true
			},
			
		data: {
			simpleData: {
				enable: true,
				idKey: "privilegeId",
				pIdKey: "parentId",
				rootPId: -1
					},
				key: {
						url: "xUrl"
				}
			}
	};
	
	function simpleContains(data1 ,data2){
		for(var i = 0;i<data1.length;i++){
				if(data1[i] == data2){
					return true;
				}
		}
		return false;
	};
	$.post(global_context+"/system/privilege/privilegeList.do", function(data){
		$.each(data, function(i,n){
		  if(simpleContains(extendResult,n.privilegeId)){
		  	$.extend(n,n,{checked:"true"});
		  }
		  switch(n.level){
			case 1:
				n.open = true;
				//n.isParent = true;
				n.drag = false;	
				n.drop = true;
				break;
			case 2:
				n.open = false;
				//n.isParent = true;
				n.drag = true;
				n.drop = true;
				break;
			case 3:
				n.open = false;
				//n.isParent = false;
				n.drag = true;
				n.drop = false;
				break;
			case 4:
				n.open = false;
				//n.isParent = true;
				n.drag = true;
				n.drop = false;
				break;
		} 
	});	
		zNodes = data;
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
}

function savePrivilegeChange(){
	//获得权限树
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if(treeObj != null){
		var nodes = treeObj.getCheckedNodes(true);
		var checkedLeaf = "";
		$.each(nodes,function(i,n){
			//alert(n.name);
				checkedLeaf += n.privilegeId + ",";
		});
		checkedLeaf = checkedLeaf.substring(0, checkedLeaf.length-1);
		$('#activity_pane').showLoading();
		$.post(global_context+"/system/role/savePrivilegeChange.do", {roleId : roleId,privliege : checkedLeaf},alertInfo);
	}
}

function alertInfo(data){
	if (data == true) {
		$("#privilegeDialog").dialog("close");
		alertJQ("操作成功，请等待页面刷新！", 1000);
	} else if (data==false) {
		$("#privilegeDialog").dialog("close");
		//未完成！
		alertJQ("服务器异常，请通知管理人员");
	}
	$('#activity_pane').hideLoading();
}

//确认删除
function delRoleCF(roleId) {
	//根据id获取行数据,返回的是列表
	var rowDatas = $("#roleList").jqGrid('getRowData', roleId);
	//取到选中行某一字段的值，其中name为colModel中定义的字段名
	var rowName = rowDatas["name"];
	if(rowName=="资源申请审批人"||rowName=="教师"||rowName=="学生") 
		alertJQ("该角色不能删除",1000);
	else
		confirmJQ("删除后无法恢复，确认删除？", delRole, roleId);
}

//删除
function delRole(roleId) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/role/deleteRole.do", {roleId : roleId}, function(data) {
		$('#activity_pane').hideLoading();
		if (data == "success") {
			alertJQ("操作成功，请等待页面刷新！", 1000);
			$("#roleList").jqGrid("delRowData", roleId);
		} else if (data == "failure") {
			alertJQ("服务器异常，请通知管理人员");
		}else if (data == "refuse"){
			alertJQ("该角色已配置给用户，不能删除");
		}else alertJQ(data);
	});
}


//查询
function query() {

	var postData = {
		
		name : $("#query_name").val().trim(),
		description : $("#query_description").val().trim(),
		//parentId : $("#query_parentId").val().trim()
		privilege : $("#query_privilege").val().trim(),
		organ : $("#query_organ").val().trim()
	};
	//合并数据 	
	postData = $.extend($("#roleList").getGridParam("postData"), postData);
	//将合并后的数据设置到表格属性中，记得加search:true 
	$("#roleList").setGridParam({
		search : true,
		postData : postData
	});
	$("#roleList").trigger("reloadGrid", [ {
		page : 1
	} ]);
}

//向表单填充内容
function domsLoadData(formId , data){
  		var id;
  		var name = "name";
  		for(var obj in data){
  			if($("#"+formId+" [name="+obj+"]") && $("#"+formId+" [name="+obj+"]").length>0){
  				var selectObj = $("#"+formId+" [name="+obj+"]");
  				if(obj=="privilege") id="privilegeId";//具体问题要变化，按照返回的json对象进行判断
  				else if(obj=="role") id ="roleId";
  				else if(obj == "organ"){
  					id = "departmentId";
  				}
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
  					if(selectObj.attr("type")!="file")//忽略上传控件
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
		else if(data[obj]!=null) $("#"+formId+" [name="+obj+"]").val(data[obj]);	
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
	

 }

function initMultiSelectForCheck(){

	var leftSel = $("#roleSelectL");
	var rightSel = $("#roleSelectR");

	leftSel.dblclick(function(){
		$(this).find("option:selected").each(function(){
			$(this).remove().appendTo(rightSel);
		});
	});
 }
