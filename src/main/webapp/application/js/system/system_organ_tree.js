//部门树
var curOrgan = null;
var testClick = null;

var setting = {
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "departmentId",
			pIdKey : "parentId",
			rootPId : -1

		}
	},
	callback : {
		onClick : treeclick,
		onCheck : onCheck
	}
};

var zNodes;

$(function() {
	$.post(global_context+"/system/depart/departList.do", function(data) {
		$.each(data, function(i, n) {
			if (n.isLeaf == false) { // 根节点
				n.open = false; // 初始设为 折叠
				n.isParent = true;
			} else if (n.isLeaf == true) { // 非根节点
				n.open = false; // 初始设为 非折叠
				n.isParent = false;
			}
		});
		zNodes = data; // 节点可从数据库导出，也可本地生成
		initOrganTree();
	});
});

function initOrganTree() {
	$("#organTreeDialog").dialog({
		autoOpen : false,
		width : 400,
		/*height :300,*/
		modal : true,
		resizable : true,
		show : 'fade',
		hide : 'fade',
		title : '部门员工树',
/*		buttons : {
			"确定" : function() {
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}*/
	});
/*	var dialogHtml ='<div class="section">';
	dialogHtml += '<h1>请选择部门</h1>';
	dialogHtml += '<div class="portlert-form-row" >';
	dialogHtml += '<div class="portlert-form-collection" >';
	dialogHtml += '<input type="text" class="portlert-form-input-field"   id="query_Organ" name="query_Organ"/>';

	dialogHtml += '</div></div>';
	dialogHtml += '<div id="menuContent" class="menuContent" >';
	dialogHtml += '<ul id="treeDemo" class="ztree" ></ul>';
	dialogHtml += '</div></div>';
	$("#organTreeDialog").html(dialogHtml);*/
	// 弹出对话框
	$("#organTreeDialog").load(global_jsp_position+'/system/system_user_organTree_dialog.jsp',function() 
	{		
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});

	/*	支持模糊查询 暂时废掉
	 * $.post('/DMIMS/system/depart/departList.do', function(data) {
			var o = $.parseJSON(data);
			var sels = new Array();
			for ( var i in o) {
				sels.push(o[i]['userName']);
			}
			initAutoComplete("query_Organ", sels);
		});
	
		$("#query_Organ").bind(
				"propertychange input",
				function() {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var Node = treeObj.getNodeByParam('userName', $("#query_Organ")
							.val());
					if ($("#query_Organ").val() != ""
							&& testClick != $("#query_Organ").val()) {
						treeObj.expandAll(false);
						treeObj.selectNode(Node);
						curOrgan = Node;
						testClick = $("#query_Organ").val();
					}
				});*/
}

/*function openPersonDialog() {
	$("#organTreeDialog").dialog("open");
}*/

function treeclick(event, treeId, treeNode, clickFlag) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	//可以选择父节点，功能打开
	if (treeNode.isParent) treeObj.expandNode(treeNode);
	//else {
	$("#query_Organ").val(treeNode.name);
	curOrgan = treeNode;
	//}
}
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getCheckedNodes(true), v = "";
	for ( var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var personObj = $("#query_Organ");
	personObj.attr("value", v);
}

function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "query_Organ"
			|| event.target.id == "menuContent" || $(event.target).parents(
			"#menuContent").length > 0)) {
		hideMenu();
	}
}
