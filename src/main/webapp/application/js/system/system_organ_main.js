//树控件
var zTreeObj;
//树控件配置属性
var setting = {
		edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false
			//removeTitle:"删除",
			//renameTitle:"修改"
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "departmentId",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		view:{
			showLine: false,
			//showIcon:false,
			dblClickExpand:true,
			addHoverDom:addHoverDom,
			removeHoverDom: removeHoverDom
		},
		callback: {
			onClick:treeClick,
			onDblClick:treeDbClick,
			//beforeDrag: beforeDrag,//暂时关闭
			//beforeDrop: beforeDrop//暂时关闭
		}
};

//初始页面加载
$(function() {
	//权限验证
	$('#activity_pane').showLoading();
	//加载部门树
	$.post(global_context+"/system/depart/departList.do",function(data){
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
		zTreeObj = $.fn.zTree.init($("#organ_tree"), setting, data);
		
		$('#activity_pane').hideLoading();

	});
	
	//新增选择弹窗
 	$( "#addSelectDialog" ).dialog({
 		autoOpen: false,
 		width: '400',
 		height:'100',
 		modal: true,
 		resizable:true,
 		show: 'fade',
 		hide: 'fade'
 	});

 	initConfirmDialog("confirmDialog");
 	initAlertDialog("alertDialog");
});

//定义全局变量，保存当前选择的节点
var currentNode;

//树节点单击事件函数
function treeClick(event, treeId ,treeNode){
	currentNode=treeNode;
}

//树节点双击事件函数
function treeDbClick(event, treeId ,treeNode){
	if(treeNode.open==true) zTreeObj.expandNode(treeNode,true);
	else  zTreeObj.expandNode(treeNode,false); //点击展开的关闭
/*	$("#content_body").empty();
	$.post("/DRTS/course/courseWare_checkCourseWare.action",{num:treeNode.num},function(data){
		if(data.mainData.content!= null && data.mainData.content != ""){
			$("#viewLink").css('display','');
			$("#content_body").append('<input type="hidden" id="courseContent"/>');
			$("#courseContent").val(data.mainData.content);
			$("#content_body").append('<iframe id="iframe2" src="/DRTS/course/course_view.jsp" style="width:100%;height:100%;border:0;" frameborder="0"></iframe>');
		}else{
			$("#viewLink").css('display','none');
			$("#courseContent").val("");
			$("#content_body").append('<div class="welcome"></div>');
		}
	});*/
}

//拖拽前事件函数
function beforeDrag(treeId, treeNodes) {
/*	var p_id = "";
	for (var i=0; i<treeNodes.length; i++) {
		//得到父节点
		var pnode = zTreeObj.getNodeByParam("departmentId", treeNodes[i].parentId, null);
		if(i == 0) p_id = pnode.departmentId;
		if (treeNodes[i].drag === false) {
			alertJQ(treeNodes[i].name + " 节点不能拖拽",2000);
			return false;
		}
		if(p_id != pnode.departmentId){
			alertJQ("不属于同一个父节点下的多个节点不能拖拽",2000);
			return false;
		}
	}
	
	return true;*/
}

//拖拽放下前事件函数
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
/*	if(targetNode.drop == false){
		alertJQ("不能拖拽到" + targetNode.name + "节点",2000);
		return false;
	}
	$('#activity_pane').showLoading();
	var treeIds = "";
	alert(treeNodes.length);
	for(var i=0; i<treeNodes.length; i++){
		treeIds = treeIds + treeNodes[i].departmentId + ",";
	}
	$.post("/DRTS/course/courseWare_dragCourseTreeNode.action",{treeIds:treeIds,targetId:targetNode.departmentId},function(data){
		$('#activity_pane').hideLoading();
		if(data.result == "success") {
			alertJQ("拖拽至新节点成功！",1000);
		}else if(data.result == "failure") {
			alertJQ(data.resultInfo);
		}
	});
	
	return true;*/
}

//鼠标移至树节点上的事件函数
function addHoverDom(treeId, treeNode) {
	//添加新增按钮
	if(treeNode.level!=null){
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.departmentId).length>0) return;
		var addStr = "<span class='button add auth' id='addBtn_" + treeNode.departmentId
			+ "' title='新增' authKey = 'organ_add' onfocus='this.blur();'></span>";
		sObj.append(addStr);
		var btn = $("#addBtn_"+treeNode.departmentId);
		//增加按钮单击事件函数
		if (btn) btn.bind("click", function(){
			//保存当前节点对象
			currentNode = treeNode;
			$("#content_body").empty();	
			$("#content_body").load(global_jsp_position+'/system/system_organ_add_dialog.jsp',function(data){
				addOrganInCB(currentNode.departmentId,treeNode.level);
			});	
		});
	}

	//为所有节点添加修改按钮
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag||$("#modifyBtn_"+treeNode.departmentId).length>0) return;
	var modifyStr = "<span class='button modify auth' id='modifyBtn_" + treeNode.departmentId
	+ "' title='修改' authKey = 'organ_mod' onfocus='this.blur();'></span>";
	sObj.append(modifyStr);
	var btn = $("#modifyBtn_"+treeNode.departmentId);
	if (btn) btn.bind("click", function(){
		currentNode = treeNode;
		modifyOrganIn(treeNode);
	});

	//为所有节点添加删除按钮
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag||$("#deleteBtn_"+treeNode.departmentId).length>0) return;
	var deleteStr = "<span class='button delete auth' id='deleteBtn_" + treeNode.departmentId
	+ "' title='删除' authKey = 'organ_del' onfocus='this.blur();'></span>";
	sObj.append(deleteStr);
	var btn = $("#deleteBtn_"+treeNode.departmentId);
	if (btn) btn.bind("click", function(){
		//确认是否删除
		delNodeCF("organ_tree", treeNode);
	});
	refreshAuth();
}
//鼠标移开树节点上的事件函数
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.departmentId).unbind().remove();
	$("#modifyBtn_"+treeNode.departmentId).unbind().remove();
	$("#deleteBtn_"+treeNode.departmentId).unbind().remove();
};

//新增入口回调
function addOrganInCB(parentId,nodeLevel){
	$('#activity_pane').hideLoading();
	//注册新增表单验证
	$("#addOrganForm").validate({
		rules:{
			num:{required:true,maxlength:20,byteRangeLength:[0,20],isNumOrDot:true,treeNodeNumExisted:["organ_tree",""]},
			name:{required:true,maxlength:50,byteRangeLength:[0,50],treeNodeNameExisted:["organ_tree",parentId,function(){return $("#add_num").val();}]}
		},
		onkeyup:false,
		errorPlacement: function(error, element) { //指定错误信息位置
			element.parent().children().filter("span").append("<br/>");
      		error.appendTo(element.parent().children().filter("span"));
       	},
    	submitHandler:function(){ //验证通过后调用此函数
   	 		//禁用按钮
    	 	disableButton();
    	 	$('#activity_pane').showLoading();
    		//注册增加用户ajax表单
    		$("#addOrganForm").ajaxForm();
    		//采用ajax提交表单
    		$("#addOrganForm").ajaxSubmit({
    			url:global_context+"/system/depart/addDepartment.do",
				data:{parentId:parentId,level:nodeLevel,name:$("#add_name").val()},
				success:addOrganOutCB //成功增加的回调函数
			});
    	}
	});

}

//新增出口回调函数
function addOrganOutCB(data){
	//启用按钮
	enableButton();
	$('#activity_pane').hideLoading();
	if(data.result=="success") {
		var parentId = data.identity.parentId;
		//增加树节点
		var newNode;
		newNode = {"departmentId":data.identity.departmentId,"parentId":data.identity.parentId,"name":data.identity.name,open: false, isParent:false, drag: false, drop: false};
		if(parentId==null)
			currentNode = zTreeObj.addNodes(null, newNode, false)[0];
		else{
			var parentNode = zTreeObj.getNodeByParam("departmentId",parentId,null);
			currentNode = zTreeObj.addNodes(parentNode, newNode, false)[0];
		}
		alertJQ("操作成功，请等待页面刷新！",1000);
		zTreeObj.selectNode(currentNode);
		$("#content_body").empty();
	}else if(data.result== "failure") {
		alertJQ("服务器异常，请通知管理人员！");
	}
}

//修改入口
function modifyOrganIn(treeNode) {
	$("#content_body").empty();
	$("#content_body").load(global_jsp_position+'/system/system_organ_modify_dialog.jsp',function(data){
		$("#modify_num").val(treeNode.departmentId);
		$("#modify_name").val(treeNode.name);
		$("#modify_description").val(treeNode.description);
		modifyOrganInCB(treeNode);
	});
	
}
//修改入口
function modifyOrganInCB(treeNode) {
	$("#modifyOrganForm").ajaxForm();

	treeNode.id=treeNode.departmentId;
	var num =  $("#modify_num").val();
	$("#modifyOrganForm").validate({
		rules:{
			num:{required:true,maxlength:20,byteRangeLength:[0,20],isNumOrDot:true,treeNodeNumExisted:["organ_tree",""]},
			name:{required:true,maxlength:50,byteRangeLength:[0,50],treeNodeNameExisted:["organ_tree",treeNode,num]}
		},
		onkeyup:false,
		errorPlacement: function(error, element) { //指定错误信息位置
			element.parent().children().filter("span").append("<br/>");
      		error.appendTo(element.parent().children().filter("span"));
       	},
    	submitHandler:function(){ //验证通过后调用此函数
   	 		//禁用按钮
    	 	disableButton();
    	 	$('#activity_pane').showLoading();
    		//采用ajax提交表单
    		$("#modifyOrganForm").ajaxSubmit({ 
    			url:global_context+"/system/depart/modifyDepartment.do",
    			data:{departmentId:treeNode.departmentId,departmentName:$("#modify_name").val()},
    			success:modifyOrganOutCB //成功编辑的回调函数
    		});
    	}
	});
}

//编辑出口回调函数
function modifyOrganOutCB(data){
	$('#activity_pane').hideLoading();	
	if(data.result=="success") {
		var currentNode = zTreeObj.getNodeByParam("departmentId",data.identity.departmentId,null);
		currentNode.name = data.identity.name;
		zTreeObj.updateNode(currentNode);
		zTreeObj.selectNode(currentNode);
		$("#content_body").empty();
	}else if(data.result== "failure") {
		alertJQ("服务器异常，请通知管理人员！");
	}
}

//确认删除
function delNodeCF(treeId, treeNode) {
	if(treeNode.children != undefined && treeNode.children != ""){
		alertJQ("存在部门，不能删除",'',350);
		return;
	}else{
		var departmentId = treeNode.departmentId;
		$.post(global_context+"/system/depart/checkHasUser.do", {departmentId : departmentId}, function(data){
			if(data==true)
				alertJQ("该部门下存在未冻结用户，不能删除");
			else if(data==false){
				confirmJQ("删除后无法恢复，确认删除？",delNode,treeNode);
			}else alertJQ(data);
		});
	}
}

//删除节点
function delNode(treeNode) {
	$('#activity_pane').showLoading();
	$.post(global_context+"/system/depart/deleteDepartment.do", {departmentId:treeNode.departmentId},function(data){
		$('#activity_pane').hideLoading();
		if(data.result=="success") {
			var delRecordId = data.departmentId;
			var currentNode = zTreeObj.getNodeByParam("departmentId",delRecordId,null);
			zTreeObj.removeNode(currentNode,false);
			$("#content_body").empty();	
		}else if(data.result=="failure") {
			alertJQ("服务器异常，请通知管理人员！");
		}
	});
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

//查询节点
function query(){
	var name = $("#query_name").val().trim();
	if(name == "") return false;
	var nodes = zTreeObj.getNodesByParamFuzzy("name", name, null);
	zTreeObj.selectNode(nodes[0]);
	queryNodes = nodes;
	queryIndex = 0;
}

//查询前一个节点
function queryPre(){
	if(queryIndex == 0){
		return false;
	}
	queryIndex--;
	zTreeObj.selectNode(queryNodes[queryIndex]);
}

//查询后一个节点
function queryNext(){
	if(queryIndex == queryNodes.length-1){
		return false;
	}
	queryIndex++;
	zTreeObj.selectNode(queryNodes[queryIndex]);
}

