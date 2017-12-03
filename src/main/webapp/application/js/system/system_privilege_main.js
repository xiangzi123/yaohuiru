//树控件
var zTreeObj;
// 树控件配置属性
var setting = {
		edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false// ,
			// removeTitle:"删除",
			// renameTitle:"修改"
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "privilegeId",
				pIdKey: "parentId",
				rootPId: 0
			},
			key:{
				url:"xUrl"
			}
		},
		view:{
			showLine: false,
			dblClickExpand:true,
			addHoverDom:addHoverDom,
			removeHoverDom: removeHoverDom
		},
		callback: {
			onClick:treeClick,
			onDblClick:treeDbClick,
		// beforeDrag: beforeDrag,
		// beforeDrop: beforeDrop
		}
};

// 初始页面加载
$(function() {
	// 权限验证
	$('#activity_pane').showLoading();
	// 加载权限树
	$.post(global_context+"/system/privilege/privilegeList.do",function(data){
		$.each(data, function(i,n){
			if(n.level=='1'){
				n.open = false;
				n.isParent = true;
				n.drag = false;	
				n.drop = true;
			}else{
				n.open = false;
				n.isParent = false;
				n.drag = true;
				n.drop = true;	
			}
		});			
		zTreeObj = $.fn.zTree.init($("#privilege_tree"), setting, data);
		$('#activity_pane').hideLoading();
	});
	
	// 新增选择弹窗
 	$( "#addSelectDialog" ).dialog({
 		autoOpen: false,
 		width: '400',
 		height:'100',
 		modal: true,
 		resizable:true,
 		show: 'fade',
 		hide: 'fade'
 	});
 	initAlertDialog("alertDialog");
 	initConfirmDialog("confirmDialog");
});

// 定义全局变量，保存当前选择的节点
var currentNode;

// 树节点单击事件函数
function treeClick(event, treeId ,treeNode){
	currentNode=treeNode;
}

// 树节点双击事件函数
function treeDbClick(event, treeId ,treeNode){
	if(treeNode.open==true) zTreeObj.expandNode(treeNode,true);
	else  zTreeObj.expandNode(treeNode,false);
}

// 拖拽前事件函数
function beforeDrag(treeId, treeNodes) {

}

// 拖拽放下前事件函数
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	
}

// 鼠标移至树节点上的事件函数
function addHoverDom(treeId, treeNode) {
	// 添加新增按钮
	if(treeNode.type!= 3){
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.privilegeId).length>0) return;
		var addStr = "<span class='button add auth' id='addBtn_" + treeNode.privilegeId
			+ "' title='新增' authKey = 'privilege_add' onfocus='this.blur();'></span>";
		sObj.append(addStr);
		var btn = $("#addBtn_"+treeNode.privilegeId);
		// 增加按钮单击事件函数
		if (btn) btn.bind("click", function(){
			// 保存当前节点对象
			currentNode = treeNode;
			$("#content_body").empty();	
			var TypeStr = "";
			if(treeNode.level==0) {
					TypeStr = 	"<option value =''>--请选择--</option> " +
									"<option value ='1'>目录权限</option> " +
									"<option value ='2'>页面权限</option>";
			}
			else if(treeNode.level==1){
				if(treeNode.type==1) {
					TypeStr = 	"<option value =''>--请选择--</option> " +
									"<option value ='2'>页面权限</option>";
				}else if(treeNode.type==2){
					TypeStr = 	"<option value =''>--请选择--</option> " +
									"<option value='3'>按钮权限</option>";
				}		
			}else if(treeNode.level==2){
				if(treeNode.type==2){
					TypeStr = 	"<option value =''>--请选择--</option> " +
									"<option value='3'>按钮权限</option>";
				}
			}
			$("#content_body").load(global_jsp_position+'/system/system_privilege_add_dialog.jsp',function(data){
				$("#add_type").html(TypeStr);
				addPrivilegeInCB(currentNode.privilegeId,treeNode.level+1);//ztree的level从0开始
			});
		});
	}

	// 为所有节点添加修改按钮
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag||$("#modifyBtn_"+treeNode.privilegeId).length>0) return;
	var modifyStr = "<span class='button modify auth' id='modifyBtn_" + treeNode.privilegeId
	+ "' title='修改' authKey = 'privilege_mod' onfocus='this.blur();'></span>";
	sObj.append(modifyStr);
	var btn = $("#modifyBtn_"+treeNode.privilegeId);
	if (btn) btn.bind("click", function(){
		currentNode = treeNode;
		modifyPrivilegeIn(treeNode);
	});

	// 为所有节点添加删除按钮
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag||$("#deleteBtn_"+treeNode.privilegeId).length>0) return;
	var deleteStr = "<span class='button delete auth' id='deleteBtn_" + treeNode.privilegeId
	+ "' title='删除' authKey = 'privilege_del' onfocus='this.blur();'></span>";
	sObj.append(deleteStr);
	var btn = $("#deleteBtn_"+treeNode.privilegeId);
	if (btn) btn.bind("click", function(){
		// 确认是否删除
		delNodeCF("privilege_tree", treeNode);
	});
	refreshAuth();
}
// 鼠标移开树节点上的事件函数
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.privilegeId).unbind().remove();
	$("#modifyBtn_"+treeNode.privilegeId).unbind().remove();
	$("#deleteBtn_"+treeNode.privilegeId).unbind().remove();
};

// 新增权限入口
function addPrivilegeIn(){
	$('#activity_pane').showLoading();
	$("#content_body").empty();
	var TypeStr = 	"<option value =''>--请选择--</option> " +
							"<option value ='1'>目录权限</option> ";
	$("#content_body").load(global_jsp_position+'/system/system_privilege_add_dialog.jsp',function(data){
		$("#add_type").html(TypeStr);
		addPrivilegeInCB("0","0");
	});
}

// 新增入口回调
function addPrivilegeInCB(parentId,nodeLevel){
	// 注册新增表单验证
	// 注册增加用户ajax表单
	$("#addPrivilegeForm").ajaxForm();
	$('#activity_pane').hideLoading(); 
	$("#addPrivilegeForm").validate({
		rules:{
			name:{required:true,maxlength:50,byteRangeLength:[0,50],treeNodeNameExisted:["privilege_tree",parentId,function(){return $("#add_num").val();}]},
			description:{required:true,maxlength:100,byteRangeLength:[0,100],treeNodeDesExisted:["privilege_tree",parentId,function(){return $("#add_num").val();}]},
			url:{required:true,maxlength:100,treeNodeURLExisted:["privilege_tree",parentId,function(){return $("#add_num").val();}]},//URL其他判定未加
		 	type:{required:true},
			target:{required:true,maxlength:20,stringEN:true}
		},
		onkeyup:false,
		errorPlacement: function(error, element) { // 指定错误信息位置
			element.parent().children().filter("span").append("<br/>");
      		error.appendTo(element.parent().children().filter("span"));
       	},
    	submitHandler:function(){ // 验证通过后调用此函数
   	 		// 禁用按钮
    	 	disableButton();
    	 	$('#activity_pane').showLoading();
    		// 采用ajax提交表单
    		$("#addPrivilegeForm").ajaxSubmit({
    			url:global_context+"/system/privilege/addPrivilege.do",
				data:{
					parentId:parentId,level:nodeLevel,name:$("#add_name").val(),description:$("#add_description").val(),
					displayName:$("#add_displayName").val(),url:$("#add_url").val(),target:$("#add_target").val(),type:$("#add_type").val()
				},
				success:addPrivilegeOutCB // 成功增加的回调函数
			});
    	}
	});
}

// 新增出口回调函数
function addPrivilegeOutCB(data){
	// 启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	
	if(data.result=="success") {
		var parentId = data.identity.parentId;
		//增加树节点
		var newNode;
		newNode = {"privilegeId":data.identity.privilegeId,"parentId":data.identity.parentId,"name":data.identity.name,"url":data.identity.url,"type":data.identity.type,
							"target":data.identity.target,"description":data.identity.description,open: false, isParent:false, drag: false, drop: false};
		if(parentId==null)
			currentNode = zTreeObj.addNodes(null, newNode, false)[0];
		else{
			var parentNode = zTreeObj.getNodeByParam("privilegeId",parentId,null);
			currentNode = zTreeObj.addNodes(parentNode, newNode, false)[0];
		}
		alertJQ("操作成功，请等待页面刷新！",1000);
		zTreeObj.selectNode(currentNode);
		$("#content_body").empty();
	}else if(data.result== "failure") {
		alertJQ("服务器异常，请通知管理人员！");
	}
}

// 修改入口
function modifyPrivilegeIn(treeNode) {
	$('#activity_pane').showLoading();
	$("#content_body").empty();
	var TypeStr;
	/*修改的是目录级的权限*/
	if(treeNode.type==1){
		/*无父节点 只能是目录级权限*/
		if(treeNode.level==0){ 
				TypeStr = 	"<option value =''>--请选择--</option> " +
			           "<option value ='1'>目录权限</option> ";  
		}
		else{
			/*有父子节点 只能是目录*/
			if(treeNode.children != undefined && treeNode.children != "")
				TypeStr = 	"<option value =''>--请选择--</option> " +
			           "<option value ='1'>目录权限</option> ";  
			else /*无子节点，可以改成目录或者页面*/
				TypeStr = 	"<option value =''>--请选择--</option> " +	
							"<option value ='1'>目录权限</option>"+
							"<option value ='2'>页面权限</option> ";  			
		}
	}
/*	修改页面级的权限*/
	else if(treeNode.type==2){ 
		/*一般有父子节点，不能修改为别的*/
			if(treeNode.children != undefined && treeNode.children != "")
				TypeStr = 	"<option value =''>--请选择--</option> " +
		           "<option value ='2'>页面权限</option> "; 
			else{
				/*如果没有子节点，而且是直接目录下，还可以修改成二级目录*/
				if(treeNode.level==1)
					TypeStr = 	"<option value =''>--请选择--</option> " +	
					"<option value ='1'>目录权限</option>"+
					"<option value ='2'>页面权限</option> ";  
				else
					TypeStr = 	"<option value =''>--请选择--</option> " +
			           "<option value ='2'>页面权限</option> "; 
			}
	}
	else{
		TypeStr = 	"<option value =''>--请选择--</option> " +
					"<option value ='3'>按钮权限</option> "; 	
	}

	$("#content_body").load(global_jsp_position+'/system/system_privilege_modify_dialog.jsp',function(data){
		$("#modify_type").html(TypeStr);
		$("#modify_num").val(treeNode.privilegeId);
		$("#modify_name").val(treeNode.name);
		$("#modify_description").val(treeNode.description);
		$("#modify_url").val(treeNode.url);
		$("#modify_target").val(treeNode.target);
		$("#modify_type").val(treeNode.type);
		if(treeNode.type==3) $("#authButton").show();
		modifyPrivilegeInCB(treeNode);
	});
	
}
// 修改入口
function modifyPrivilegeInCB(treeNode) {
	 treeNode.id=treeNode.privilegeId;
	 var num = $("#modify_num").val();
	 $('#activity_pane').hideLoading();
	 $("#modifyPrivilegeForm").validate({ 
		rules:{
			name:{required:true,maxlength:50,byteRangeLength:[0,50],treeNodeNameExisted:["privilege_tree",treeNode,num]},
			description:{required:true,maxlength:100,byteRangeLength:[0,100],treeNodeDesExisted:["privilege_tree",treeNode,num]},
			url:{required:true,maxlength:100,treeNodeURLExisted:["privilege_tree",treeNode,num]},//URL其他判定未加
			type:{required:true},
			target:{required:true,maxlength:20,stringEN:true}
		  },
		 // onkeyup:false,
		 errorPlacement: function(error, element) { // 指定错误信息位置
			 element.parent().children().filter("span").append("<br/>");
			 error.appendTo(element.parent().children().filter("span"));
		 },
		  submitHandler:function(){ // 验证通过后调用此函数
			  // 禁用按钮
			  $("#modifyPrivilegeForm").ajaxForm();
			  disableButton();
			  $('#activity_pane').showLoading();
			 // 采用ajax提交表单
			  $("#modifyPrivilegeForm").ajaxSubmit({ 
					url:global_context+"/system/privilege/modifyPrivilege.do",
					data:{
							privilegeId:treeNode.privilegeId,
							name:$("#modify_name").val(),
							description:$("#modify_description").val(),
							parentId:treeNode.parentId,
							level:treeNode.level+1,
							url:$("#modify_url").val(),
							type:$("#modify_type").val(),
							target:$("#modify_target").val()
						},
						success:modifyPrivilegeOutCB // 成功编辑的回调函数
				});
	    }
});
}

// 编辑出口回调函数
function modifyPrivilegeOutCB(data){
	// 启用按钮
	//enableButton();
	$('#activity_pane').hideLoading();
	if(data.result=="success") {
		var currentNode = zTreeObj.getNodeByParam("privilegeId",data.identity.privilegeId,null);
		currentNode.name = data.identity.name;
		zTreeObj.updateNode(currentNode);
		zTreeObj.selectNode(currentNode);
		currentNode.name=$("#modify_name").val();
		currentNode.description=$("#modify_description").val();
		currentNode.url=$("#modify_url").val();
		currentNode.type=$("#modify_type").val();
		currentNode.target=$("#modify_target").val();
		$("#content_body").empty();
	}else if(data.result== "failure") {
		alertJQ("服务器异常，请通知管理人员！");
	}
}

// 确认删除
function delNodeCF(treeId, treeNode) {
	
	if(treeNode.children != undefined && treeNode.children != ""){
		alertJQ("存在子权限，不能删除",'',350);
		return;
	}else
		confirmJQ("删除后无法恢复，确认删除？",delNode,treeNode);
}

// 删除节点
function delNode(treeNode) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/privilege/deletePrivilege.do", {privilegeId:treeNode.privilegeId},function(data){
		$('#activity_pane').hideLoading();
		if(data.result=="success") {
			var delRecordId = data.privilegeId;
			var currentNode = zTreeObj.getNodeByParam("privilegeId",delRecordId,null);
			zTreeObj.removeNode(currentNode,false);
			$("#content_body").empty();	
		}else if(data.result=="failure") {
			alertJQ("服务器异常，请通知管理人员！");
		}else alertJQ(data);
	});
}

function addInputTarget(obj){
	obj = $(obj);
	if(obj.val()=='3') {
		$("#authButton").show();//css({'display':'block'});
	}else $("#authButton").hide();
}

function queryBlur(){
	if($("#query_name").val() == ""){
		$("#query_name").val("输入名称查询");
	}
}

function queryClick(){
	if($("#query_name").val() == "输入名称查询"){
		$("#query_name").val("");
	}
}

function queryKeyUp(e){
	var e = e || window.event; 
	if(e.keyCode == '13'){
		query();
	}
}

// 查询节点
function query(){
	$(".search_btn").css("border","none");
	var name = $("#query_name").val().trim();
	if(name == "") return false;
	var nodes = zTreeObj.getNodesByParamFuzzy("name", name, null);
	zTreeObj.selectNode(nodes[0]);
	queryNodes = nodes;
	queryIndex = 0;
}

// 查询前一个节点
function queryPre(){
	if(queryIndex == 0){
		return false;
	}
	queryIndex--;
	zTreeObj.selectNode(queryNodes[queryIndex]);
}

// 查询后一个节点
function queryNext(){
	if(queryIndex == queryNodes.length-1){
		return false;
	}
	queryIndex++;
	zTreeObj.selectNode(queryNodes[queryIndex]);
}