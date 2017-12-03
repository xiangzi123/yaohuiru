//为表单加载数据，20120805 by 苏璐健
 //表单的Id作为参数，增加了单选按钮的赋值
function formLoadDataSu(formId,data){
	var dataForm;
 	//首先判断data类型，若为字符串则将其转换为json对象，若为json对象则不用重复转换
 	if(typeof(data) == "string") dataForm = $.parseJSON(data);
	else if(typeof(data) == "object") dataForm = data;
 	for(var obj in dataForm){
 	if(obj=="mainData"){
		for(var title in dataForm[obj]){
			var elemetObj;			
			if($(formId+" [name="+title+"]")&&$(formId+" [name="+title+"]").length>0){
				elemetObj = $(formId+" [name="+title+"]");
				//填充文本框
				if(elemetObj.get(0).tagName=="INPUT")
					{
					if(elemetObj.attr("type")=="radio"){//单选按钮
						for(var i=0;i<elemetObj.length;i++){
							if(elemetObj.eq(i).val()==dataForm[obj][title]){
								elemetObj.eq(i).attr("checked","checked");
							}
						}
					}
					else{
						if(elemetObj.attr("type")!="file")//忽略上传控件
							elemetObj.val(dataForm[obj][title]);
					}
					
				}
				//填充下拉框
				else if(elemetObj.get(0).tagName=="SELECT")
				    {
					//elemetObj.get(0).add(new Option(dataForm[obj][title], dataForm[obj][title]));
				    }
				//填充文本区域
				else if(elemetObj.get(0).tagName=="TEXTAREA")
				    {
					elemetObj.val(dataForm[obj][title]);
				    }
				
				}
			}
 		}else if($(formId+" [name="+obj+"]")!=null){
			var selectObj = $(formId+" [name="+obj+"]");
			//填充下拉框
			if(selectObj.get(0).tagName=="SELECT")
		    {
		    for(var i=0;i<dataForm[obj].length; i++)
		    	if(dataForm[obj][i]["num"]!=null&&dataForm[obj][i]["num"]!=""){
		    		if(dataForm[obj][i]["selected"]==true)
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"], false, true));
					else 
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"]));
		    	}else if(dataForm[obj][i]["id"]!=null&&dataForm[obj][i]["id"]!=""){
		    		if(dataForm[obj][i]["selected"]==true)
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"], false, true));
					else 
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"]));
		    	}else{
		    		if(dataForm[obj][i]["selected"]==true)
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"],"", false, true));
					else 
						selectObj.get(0).add(new Option(dataForm[obj][i]["name"],""));
		    	}
		    	
		    }
		}
 	}
}
//为表单加载数据
//表单的Id作为参数
function formLoadData(formId,data){
	var dataForm = data;
 	//首先判断data类型，若为字符串则将其转换为json对象，若为json对象则不用重复转换
 	if(typeof(data) == "string") dataForm = $.parseJSON(data);
	else if(typeof(data) == "object") dataForm = data;
 	for(var obj in dataForm){
 	if(obj=="mainData"){
		for(var title in dataForm[obj]){
			var elemetObj;			
			if($("#"+formId+" [name="+title+"]")&&$("#"+formId+" [name="+title+"]").length>0){
				elemetObj = $("#"+formId+" [name="+title+"]");
				//填充input域
				if(elemetObj.get(0).tagName=="INPUT")
					{
					if(elemetObj.attr("type")=="radio"){//单选按钮
						for(var i=0;i<elemetObj.length;i++){
							if(elemetObj.eq(i).val()==dataForm[obj][title]){
								elemetObj.eq(i).attr("checked","checked");
							}
						}
					}
					else{
						//填充文本框
						if(elemetObj.attr("type")!="file")//忽略上传控件
							elemetObj.val(dataForm[obj][title]);
					}
				}
				//填充文本区域
				else if(elemetObj.get(0).tagName=="TEXTAREA")
				    {
					elemetObj.val(dataForm[obj][title]);
				    }
				}
			}
 		}else if($("#"+formId+" [name="+obj+"]") && $("#"+formId+" [name="+obj+"]").length>0){
			var selectObj = $("#"+formId+" [name="+obj+"]");
			//填充下拉框
			if(selectObj.get(0).tagName=="SELECT")
			    for(var i=0;i<dataForm[obj].length; i++){
			    	if(dataForm[obj][i]["value"]!=null&&dataForm[obj][i]["value"]!=""){
			    		if(dataForm[obj][i]["selected"]==true)
							selectObj.get(0).add(new Option(dataForm[obj][i]["value"], dataForm[obj][i]["value"], false, true));
						else 
							selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["value"]));
			    	}else if(dataForm[obj][i]["id"]!=null&&dataForm[obj][i]["id"]!=""){
			    		if(dataForm[obj][i]["selected"]==true)
							selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"], false, true));
						else 
							selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"]));
			    	}else{
			    		if(dataForm[obj][i]["selected"]==true)
							selectObj.get(0).add(new Option(dataForm[obj][i]["name"],"", false, true));
						else 
							selectObj.get(0).add(new Option(dataForm[obj][i]["name"],""));
			    	}
			    }
			else if(selectObj.get(0).tagName=="INPUT")
				//填充文本框
				if(selectObj.attr("type")!="file")//忽略上传控件
					selectObj.val(dataForm[obj]);
		}
 	}
}

//重置表单
//表单的Id作为参数，不清空，使表单回到初始状态
function resetForm(formId){
	//清空input text域
	 $(formId +" :text").each(function() {
		 $(this).attr("value", "");
	 });
	 //清空textarea域
	 $(formId +" textarea").each(function() {
		 $(this).attr("value", "");
	 });
	 //重置 select域
	 $(formId +" select").each(function() {
		 $(this).get(0).options[0].selected = true;
	 });
	//清空span
	 $(formId + " span.portlert-form-msg-alert").each(function(){
		 $(this).html("&nbsp;");
	 });
}
 
 //禁用按钮
 function disableButton() {
	 $(".ui-dialog-buttonpane button").attr("disabled","disabled"); 
 }
 //启用按钮
 function enableButton() {
	 $(".ui-dialog-buttonpane button").removeAttr("disabled");
 }
//操作成功对话框初始化方式  20110524 by luox
 function initSuccessDialog(id) {
	 //固定弹窗的名字，废弃参数id
	 $( "#successDialog" ).dialog({
			autoOpen: false,
			width: '350',
			height:'120',
			resizable:false,
			dialogClass: "littleDialog",
			draggable:false,
			hide: 'fade',
			close:function(){
		 		window.location.reload();
			}
		});
 }
 //不自动刷新的成功返回弹窗
 function initSuccessDialogWithNoRefresh(id) {
	 $( "#successDialogWithNoRefresh" ).dialog({
			autoOpen: false,
			width: '350',
			height:'120',
			resizable:false,
			dialogClass: "littleDialog",
			draggable:false,
			hide: 'fade',
			close:function(){
		 		//window.location.reload();
			}
		});
 }
 
 //操作失败对话框初始化方式  20110524 by luox
 function initFailureDialog(id) {
	 $( "#"+id ).dialog({
			autoOpen: false,
			width: '350',
			height:'120',
			resizable:false,
			dialogClass: "littleDialog",
			draggable:false,
			hide: 'fade',
			close:function(){}
		});
 }
 
 function initConfirmDialog(id) {
	 $( "#" + id ).dialog({
			autoOpen: false,
			width: '350',
			height:'120',
			modal: true,
			resizable:false,
			draggable:false,
			show: 'fade',
			hide: 'fade',
			buttons: {
				"确定": function (){
					$( this ).dialog("close");
				},
				"取消": function(){
					$( this ).dialog("close");
				}
			}
		});
 }
 
 var degree = 0;
 function initProgressbar(id) {
	 degree = 0;
	 $( "#"+id).progressbar({ value: degree });
		setTimeout(callback, 500);
 }
 
 //刷新
 function callback() {
	 degree += 5;
	 if(degree >100)
			degree=0;
	 $( "#divProgressbar").progressbar({ value: degree });
	 setTimeout(callback, 500);	
 }
 
 function initAlertDialog(id){
	 $("#"+id).dialog({
		 	autoOpen: false,
		 	width: '300',
		 	height:'120',
		 	resizable:false,
		 	draggable:false,
		 	hide: 'fade',
		 	buttons:''
		 });
 }

	function alertJQ(message, timeOut, width, height) {	
		
		$("#alert-msg").html(message);
		if(timeOut != undefined && timeOut !=""){
			$("#alertDialog").dialog('option','buttons',"");
			setTimeout("$( '#alertDialog' ).dialog( 'close' );", timeOut);
		}else{
			$("#alertDialog").dialog('option','buttons',{
				"确定" : function() {
					$(this).dialog("close");
				}
			});
		}

		if(width != undefined && width !=""){
			$("#alertDialog").dialog('option','width',width);
		}
		if(height != undefined && height !=""){
			$("#alertDialog").dialog('option','height',height);
		}
		
		$("#alertDialog").dialog("open");
		
	}
	

function initConfirmDialog(id) {
	$("#"+id).dialog({
		autoOpen : false,
		width : '300',
		height : '120',
		modal : true,
		resizable : false,
		draggable : false,
		show : 'fade',
		hide : 'fade'
	});
}
 
function confirmJQ(message, callback, data, width, height) {
	
	$("#confirm-msg").html(message);
	$("#confirmDialog").dialog('option','buttons',{
		"确定" : function() {
			if (data == undefined || data ==""){
			  callback();
			}else{
			  callback(data);
			}
			$(this).dialog("close");
		},
		"取消" : function() {
			$(this).dialog("close");
		}
	});
	
	if(width != undefined && width !=""){
		$("#confirmDialog").dialog('option','width',width);
	}
	if(height != undefined && height !=""){
		$("#confirmDialog").dialog('option','height',height);
	}
	
	//加上自适应弹窗
	if(width == "auto"){
		$("#confirmDialog").dialog('option','width',"auto");
	}
	if(height == "auto"){
		$("#confirmDialog").dialog('option','height',"auto");
	}
	
	$("#confirmDialog").dialog("open");
}

 //调用成功或者返回对话框，data为ajax返回值，dialogName为当前操作的窗口名称
 function sucOrFailReturnDialog(data, dialogName){
	 if(data == "success") {
			$( "#" + dialogName ).dialog( "close" );
			$( "#successDialog" ).dialog( "open" );
			//因为在dialog定义中的close方法中加了reload操作，所以只要调用close方法就可以了
			setTimeout("$( '#successDialog' ).dialog( 'close' );", 1000);
			//下面的语句执行不到？
			/*alert(3213123);
			window.location.reload();*/
		}else if(data == "failure") {
			$( "#failureDialog" ).dialog( "open" );
			setTimeout("$( '#failureDialog' ).dialog( 'close' );", 1000);
		}
 }
  function sucOrFailRefReturnDialog(data, dialogName){
	 if(data == "success") {
			$( "#" + dialogName ).dialog( "close" );
			$( "#successDialogWithNoRefresh" ).dialog( "open" );
			//因为在dialog定义中的close方法中加了reload操作，所以只要调用close方法就可以了
			setTimeout("$( '#successDialogWithNoRefresh' ).dialog( 'close' );", 1000);
	
		}else if(data == "failure") {
			$( "#failureDialog" ).dialog( "open" );
			setTimeout("$( '#failureDialog' ).dialog( 'close' );", 1000);
		}
 }

//将数字转换为中文小写
 function num2Chinese(num){
	 var number = Number(num);
	 switch(number){
	 	case 0:
			return '○';
		case 1:
			return '一';
		case 2:
			return '二';
		case 3:
			return '三';
		case 4:
			return '四';
		case 5:
			return '五';
		case 6:
			return '六';
		case 7:
			return '七';
		case 8:
			return '八';
		case 9:
			return '九';
	 }
 }
//初始化默认日历控件，默认日历控件日期格式为：yy-mm-dd，elements为需要添加日历控件的表单元素的数组
function initDefaultDatepicker(elements){
	if(elements.length == 0 ){
		alert("请提供需要添加日历控件的表单元素！");
		return;
	}
	for(var i=0; i<elements.length; i++){
		$('#' + elements[i]).datetimepicker({
				//inline: true,
				dateFormat: 'yy-mm-dd',
				//timeFormat: 'hh:mm:ss',
				dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
				dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
				firstDay: 1,
				monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				monthNamesShort:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				showButtonPanel: true,
				currentText: '当前',
				closeText: '关闭',
				changeMonth: true,
				changeYear: true
				//showAnim: 'fade',
				//showSecond:true
			});
	}
}
	
//初始化客户化日历控件，elements为需要添加日历控件的表单元素的数组，dateFormat为日历控件的日期格式字符串，标准见jquery文档
function initCustomerDatepicker(elements, dateFormat){
	if(elements.length == 0 ){
		alert("请提供需要添加日历控件的表单元素！");
		return;
	}
	for(var i=0; i<elements.length; i++){
		$('#' + elements[i]).datetimepicker({
				//inline: true,
				dateFormat: dateFormat,
				dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
				dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
				firstDay: 1,
				monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				monthNamesShort:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				showButtonPanel: true,
				currentText: '今天',
				closeText: '关闭',
				//changeMonth: true,
				//changeYear: true,
				showAnim: 'fade'
				
			});
	}
}

//初始化客户化日历控件，elements为需要添加日历控件的表单元素的数组，dateFormat为日历控件的日期格式字符串，标准见jquery文档
function initCustomerDatepicker(elements, dateFormat, timeFormat){
	if(elements.length == 0 ){
		alert("请提供需要添加日历控件的表单元素！");
		return;
	}
	for(var i=0; i<elements.length; i++){
		$('#' + elements[i]).datetimepicker({
				//inline: true,
				dateFormat: dateFormat,
				timeFormat: timeFormat,
				dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
				dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
				firstDay: 1,
				monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				monthNamesShort:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				showButtonPanel: true,
				currentText: '今天',
				closeText: '关闭',
				//changeMonth: true,
				//changeYear: true,
				showAnim: 'fade',
				showSecond:false,
				showMinute:false,
			});
	}
}

//初始化客户化日历控件，elements为需要添加日历控件的表单元素的数组，timeFormat为日历控件的日期格式字符串，标准见jquery文档
function initCustomerDatepicker(elements, dateFormat, timeFormat,changeMonth,changeYear,showSecond,showMinute){
	if(elements.length == 0 ){
		alert("请提供需要添加日历控件的表单元素！");
		return;
	}
	for(var i=0; i<elements.length; i++){
		$('#' + elements[i]).datetimepicker({
				//inline: true,
				dateFormat: dateFormat,
				timeFormat: timeFormat,
				dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
				dayNamesMin:['日', '一', '二', '三', '四', '五', '六'],
				firstDay: 1,
				monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				monthNamesShort:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
				showButtonPanel: true,
				currentText: '今天',
				closeText: '关闭',
				changeMonth: changeMonth,
				changeYear: changeYear,
				showAnim: 'fade',
				showSecond:showSecond,
				showMinute:showMinute
			});
	}
}

//初始化自动补全控件
function initAutoComplete(element,data){
	$('#' + element).autocomplete(data,{
		width: 180,
		minChars: 0,
		//max: 15,
		matchContains: true,
		scroll: true,
		scrollHeight: 180
	});
}
//将表单数据序列化到json对象
(function($){  
    $.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    };  
})(jQuery); 

function validateRole(msgId,roleStr){
	
	var height = document.documentElement.clientHeight/2;
	var width =  document.documentElement.clientWidth/2-100;
	//登录验证
	if(global_username==""||global_username==undefined){
		var html= "<div style='position:absolute;top:"+height+"px;left:"+width+"px;color:red;'>您尚未登录，请点击<a href="+global_jsp_position+"/core/login.jsp target = '_parent'>登录</a>！</div>";
		$("#"+msgId).append(html);
		return false;
	}
	return true;
}

function refreshAuth(){
	var autenticatedButtons = new Array();
	for(var url in buttons){
		if(global_url.indexOf(url)!=-1){
			autenticatedButtons = buttons[url];
			break;
		}		
	}
	var objs = $(".auth");
	var removeAuth= new Array();
	for(var i=0; i<objs.size(); i++){
		var obj = $(objs.get(i));
		var has = false;
		for(var button in autenticatedButtons){
			if(autenticatedButtons[button]==obj.attr("authKey")) {
				has = true;
				break;
			}
		}
		if(has == false) removeAuth.push(obj);
	}
	for(var dis in removeAuth){
		disableButtons(removeAuth[dis]);
	}
}

function disableButtons(obj) {
	obj.addClass("grayscale");
//		obj.fadeTo(3500,0.2);
//		grayscale(obj);
	if(obj.attr('href'))
		obj.removeAttr('href');
	if(obj.attr('onclick'))
		obj.removeAttr('onclick');
	obj.unbind();
}

//2011 05 19 add by 李昕融   
//json数据填充表单 
//data  Json数据集  其中
//dialog 页面标示
//checkType 清单选择器类型： allCheck清单和表单复选框， allRadio清单和表单单选框，normal默认为无
//listCheck清单复选框，listRadio清单单选框，tableCheck表单复选框，tableRadio表单单选框，
function fullFillForm(data,dialog,checkType){
	var dataForm;
	//首先判断data类型，若为字符串则将其转换为json对象，若为json对象则不用重复转换
	if(typeof(data) == "string") dataForm = $.parseJSON(data);
	else if(typeof(data) == "object") dataForm = data;
	//dataForm = data;
	for(var obj in dataForm){
		if(obj=="mainData"){
		for(var title in dataForm[obj]){
		var elemetObj;			
		if($("#"+dialog+"_"+title)&&$("#"+dialog+"_"+title).length>0){
			elemetObj = $("#"+dialog+"_"+title);
			//填充文本框
			if(elemetObj.get(0).tagName=="INPUT")
				{
				if(elemetObj.attr("type")!="file")//忽略上传控件
					elemetObj.val(dataForm[obj][title]);
				}
			//填充下拉框
			else if(elemetObj.get(0).tagName=="SELECT")
			    {
				//elemetObj.get(0).add(new Option(dataForm[obj][title], dataForm[obj][title]));
			    }
			//填充文本区域
			else if(elemetObj.get(0).tagName=="TEXTAREA")
			    {
				elemetObj.val(dataForm[obj][title]);
			    }
			//填充清单
			else if(elemetObj.get(0).tagName=="TD")
				{
				for(var i=0;i<dataForm[obj][title].length; i++)
					{
						if(checkType=="normal")
							elemetObj.append(dataForm[obj][title][i]+"<br>");
						else if(checkType=="allCheck"||checkType=="listCheck")
							elemetObj.append("<input type='checkbox' name='"+dialog+"simplelist' id='"+dialog+obj+"_"+title+"_"+i+"' value='"+dataForm[obj][title][i]+"' class='checkbox' />"+dataForm[obj][title][i]+"<br>");
						else if(checkType=="allRadio"||checkType=="listRadio")
							elemetObj.append("<input type='radio' name='"+dialog+"simplelist' id='"+dialog+obj+"_"+title+"_"+i+"' value='"+dataForm[obj][title][i]+"' class='radio' />"+dataForm[obj][title][i]+"<br>");
					}
				
				}
			}
		}
		}
		else if(document.getElementById(dialog+"_"+obj)!=null){
			var selectObj = $("#"+dialog+"_"+obj);
			//填充下拉框
			if(selectObj.get(0).tagName=="SELECT")
				    {
				    for(var i=0;i<dataForm[obj].length; i++)
				    	if(dataForm[obj][i]["num"]!=null&&dataForm[obj][i]["num"]!=""){
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"], false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"]));
				    	}else if(dataForm[obj][i]["id"]!=null&&dataForm[obj][i]["id"]!=""){
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"], false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"]));
				    	}else{
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"],"", false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"],""));
				    	}
				    	
				    }
				//填充列表
			else if(selectObj.get(0).tagName=="TD")
				{
				
				var pnum=0;
				for(var psize in dataForm[obj][0])
					{
					pnum++;
					}
				var pwidth = checkType!="normal"?97:100;
				var fff = 0;
				if (pnum!=0) pwidth = pwidth/pnum;
				//alert(pwidth);
				selectObj.html("");
				selectObj.append("<table style='width:100%' id='"+obj+"Table' class='tablelistcontent'><tr id='"+obj+"TableTr'></tr></table>");
				if(checkType!="normal")
					$("#"+obj+"TableTr").append("<td style='background-color:#D0E8F3; width:3%;'></td>");
				var ti = 0;
				//外层循环，遍历json list中的对象
				for(var j=0; j<dataForm[obj].length; j++){
					//tr以for循环索引为后缀来命名区分
					$("#"+obj+"Table").append("<tr id='"+obj+"TableTr"+j+"'></tr>");
					//处理第一列的控件
					for(var listRow in dataForm[obj][j])
						{
						if((dataForm[obj][j]["id"]!="tableTitle")&&(checkType!="normal")&&(listRow=="id"))
							{
							//obj为json中第一级与“maindata”同级的list属性名称
							if(checkType=="allCheck"||checkType=="tableCheck")
								$("#"+obj+"TableTr"+j).append("<td style=' width:3%; float:left'><input type='checkbox' name='"+dialog+"tablelist' id='"+dialog+"_"+obj+"_"+j+"' value='"+dataForm[obj][j][listRow]+"' class='checkbox' /></td>");
							else if(checkType=="allRadio"||checkType=="tableRadio")
								$("#"+obj+"TableTr"+j).append("<td style=' width:3%; float:left'><input type='radio' name='"+dialog+"tablelist' id='"+dialog+"_"+obj+"_"+j+"' value='"+dataForm[obj][j][listRow]+"' class='radio' /></td>");
							if(dataForm[obj][j]["selected"]==true)
								$("#"+dialog+"_"+obj+"_"+j).attr("checked",true);
							}
						}
					//处理表头
					if(dataForm[obj][j]["id"]=="tableTitle"){
						for(var listRow in dataForm[obj][j])
							if(listRow!="id"&&listRow!="selected"){
							$("#"+obj+"TableTr").append("<td style='background-color:#D0E8F3; width:"+pwidth+"%;' id='"+obj+"title"+ti+"' name='"+listRow+"'>"+dataForm[obj][j][listRow]+"</td>");
							ti++;
							}
					}
					//处理普通的表格数据
					else{
						for(var listRow in dataForm[obj][j])
							if(listRow!="id"&&listRow!="selected"){
							$("#"+obj+"TableTr"+j).append("<td style=' width:"+pwidth+"%; float:left' name='"+listRow+"'>"+dataForm[obj][j][listRow]+"</td>");
							}
					}
				}
			}
		}else if(document.getElementById(obj)!=null){
			var selectObj = $("#"+obj);
			//填充下拉框
			if(selectObj.get(0).tagName=="SELECT")
				    {
				    for(var i=0;i<dataForm[obj].length; i++)
				    	if(dataForm[obj][i]["num"]!=null&&dataForm[obj][i]["num"]!=""){
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"], false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["num"]));
				    	}else if(dataForm[obj][i]["id"]!=null&&dataForm[obj][i]["id"]!=""){
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"], false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], dataForm[obj][i]["id"]));
				    	}else{
				    		if(dataForm[obj][i]["selected"]==true)
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"], "", false, true));
							else 
								selectObj.get(0).add(new Option(dataForm[obj][i]["name"],""));
				    	}
				    	
				    }
				//填充列表
			else if(selectObj.get(0).tagName=="TD")
				{
				
				var pnum=0;
				for(var psize in dataForm[obj][0])
					{
					pnum++;
					}
				var pwidth = checkType!="normal"?97:100;
				var fff = 0;
				if (pnum!=0) pwidth = pwidth/pnum;
				selectObj.html("");
				selectObj.append("<table style='width:100%' id='"+obj+"Table' class='tablelistcontent'><tr id='"+obj+"TableTr'></tr></table>");
				if(checkType!="normal")
					$("#"+obj+"TableTr").append("<td style='background-color:#D0E8F3; width:3%;'></td>");
				var ti = 0;
				//外层循环，遍历json list中的对象
				for(var j=0; j<dataForm[obj].length; j++){
					//tr以for循环索引为后缀来命名区分
					$("#"+obj+"Table").append("<tr id='"+obj+"TableTr"+j+"'></tr>");
					//处理第一列的控件
					for(var listRow in dataForm[obj][j])
						{
						if((dataForm[obj][j]["id"]!="tableTitle")&&(checkType!="normal")&&(listRow=="id"))
							{
							//obj为json中第一级与“maindata”同级的list属性名称
							if(checkType=="allCheck"||checkType=="tableCheck")
								$("#"+obj+"TableTr"+j).append("<td style=' width:3%; float:left'><input type='checkbox' name='"+dialog+"tablelist' id='"+dialog+"_"+obj+"_"+j+"' value='"+dataForm[obj][j][listRow]+"' class='checkbox' /></td>");
							else if(checkType=="allCheck"||checkType=="tableRadio")
								$("#"+obj+"TableTr"+j).append("<td style=' width:3%; float:left'><input type='radio' name='"+dialog+"tablelist' id='"+dialog+"_"+obj+"_"+j+"' value='"+dataForm[obj][j][listRow]+"' class='radio' /></td>");
							if(dataForm[obj][j]["selected"]==true)
								$("#"+dialog+"_"+obj+"_"+j).attr("checked",true);
							}
						}
					//处理表头
					if(dataForm[obj][j]["id"]=="tableTitle"){
						for(var listRow in dataForm[obj][j])
							if(listRow!="id"&&listRow!="selected"){
							$("#"+obj+"TableTr").append("<td style='background-color:#D0E8F3; width:"+pwidth+"%;' id='"+obj+"title"+ti+"' name='"+listRow+"'>"+dataForm[obj][j][listRow]+"</td>");
							ti++;
							}
					}
					//处理普通的表格数据
					else{
						for(var listRow in dataForm[obj][j])
							if(listRow!="id"&&listRow!="selected"){
							$("#"+obj+"TableTr"+j).append("<td style=' width:"+pwidth+"%; float:left' name='"+listRow+"'>"+dataForm[obj][j][listRow]+"</td>");
							}
					}
				}
			}
		}	
	}
}





//Flash Player Version Detection - Rev 1.6
//Detect Client Browser type
//Copyright(c) 2005-2006 Adobe Macromedia Software, LLC. All rights reserved.
var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
var isWin = (navigator.appVersion.toLowerCase().indexOf("win") != -1) ? true : false;
var isOpera = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;

function ControlVersion()
{
	var version;
	var axo;
	var e;

	// NOTE : new ActiveXObject(strFoo) throws an exception if strFoo isn't in the registry

	try {
		// version will be set for 7.X or greater players
		axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		version = axo.GetVariable("$version");
	} catch (e) {
	}

	if (!version)
	{
		try {
			// version will be set for 6.X players only
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
			
			// installed player is some revision of 6.0
			// GetVariable("$version") crashes for versions 6.0.22 through 6.0.29,
			// so we have to be careful. 
			
			// default to the first public version
			version = "WIN 6,0,21,0";

			// throws if AllowScripAccess does not exist (introduced in 6.0r47)		
			axo.AllowScriptAccess = "always";

			// safe to call for 6.0r47 or greater
			version = axo.GetVariable("$version");

		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 4.X or 5.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
			version = axo.GetVariable("$version");
		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 3.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
			version = "WIN 3,0,18,0";
		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 2.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			version = "WIN 2,0,0,11";
		} catch (e) {
			version = -1;
		}
	}
	
	return version;
}

//JavaScript helper required to detect Flash Player PlugIn version information
function GetSwfVer(){
	// NS/Opera version >= 3 check for Flash plugin in plugin array
	var flashVer = -1;
	
	if (navigator.plugins != null && navigator.plugins.length > 0) {
		if (navigator.plugins["Shockwave Flash 2.0"] || navigator.plugins["Shockwave Flash"]) {
			var swVer2 = navigator.plugins["Shockwave Flash 2.0"] ? " 2.0" : "";
			var flashDescription = navigator.plugins["Shockwave Flash" + swVer2].description;
			var descArray = flashDescription.split(" ");
			var tempArrayMajor = descArray[2].split(".");			
			var versionMajor = tempArrayMajor[0];
			var versionMinor = tempArrayMajor[1];
			var versionRevision = descArray[3];
			if (versionRevision == "") {
				versionRevision = descArray[4];
			}
			if (versionRevision[0] == "d") {
				versionRevision = versionRevision.substring(1);
			} else if (versionRevision[0] == "r") {
				versionRevision = versionRevision.substring(1);
				if (versionRevision.indexOf("d") > 0) {
					versionRevision = versionRevision.substring(0, versionRevision.indexOf("d"));
				}
			} else if (versionRevision[0] == "b") {
				versionRevision = versionRevision.substring(1);
			}
			var flashVer = versionMajor + "." + versionMinor + "." + versionRevision;
		}
	}
	// MSN/WebTV 2.6 supports Flash 4
	else if (navigator.userAgent.toLowerCase().indexOf("webtv/2.6") != -1) flashVer = 4;
	// WebTV 2.5 supports Flash 3
	else if (navigator.userAgent.toLowerCase().indexOf("webtv/2.5") != -1) flashVer = 3;
	// older WebTV supports Flash 2
	else if (navigator.userAgent.toLowerCase().indexOf("webtv") != -1) flashVer = 2;
	else if ( isIE && isWin && !isOpera ) {
		flashVer = ControlVersion();
	}
	return flashVer;
}

//When called with reqMajorVer, reqMinorVer, reqRevision returns true if that version or greater is available
function DetectFlashVer(reqMajorVer, reqMinorVer, reqRevision)
{
	versionStr = GetSwfVer();
	if (versionStr == -1 ) {
		return false;
	} else if (versionStr != 0) {
		if(isIE && isWin && !isOpera) {
			// Given "WIN 2,0,0,11"
			tempArray         = versionStr.split(" "); 	// ["WIN", "2,0,0,11"]
			tempString        = tempArray[1];			// "2,0,0,11"
			versionArray      = tempString.split(",");	// ['2', '0', '0', '11']
		} else {
			versionArray      = versionStr.split(".");
		}
		var versionMajor      = versionArray[0];
		var versionMinor      = versionArray[1];
		var versionRevision   = versionArray[2];

     	// is the major.revision >= requested major.revision AND the minor version >= requested minor
		if (versionMajor > parseFloat(reqMajorVer)) {
			return true;
		} else if (versionMajor == parseFloat(reqMajorVer)) {
			if (versionMinor > parseFloat(reqMinorVer))
				return true;
			else if (versionMinor == parseFloat(reqMinorVer)) {
				if (versionRevision >= parseFloat(reqRevision))
					return true;
			}
		}
		return false;
	}
}

function AC_AddExtension(src, ext)
{
var qIndex = src.indexOf('?');
if ( qIndex != -1)
{
 // Add the extention (if needed) before the query params
 var path = src.substring(0, qIndex);
 if (path.length >= ext.length && path.lastIndexOf(ext) == (path.length - ext.length))
   return src;
 else
   return src.replace(/\?/, ext+'?'); 
}
else
{
 // Add the extension (if needed) to the end of the URL
 if (src.length >= ext.length && src.lastIndexOf(ext) == (src.length - ext.length))
   return src;  // Already have extension
 else
   return src + ext;
}
}

function AC_Generateobj(objAttrs, params, embedAttrs) 
{ 
 var str = '';
 if (isIE && isWin && !isOpera)
 {
		str += '<object ';
		for (var i in objAttrs)
			str += i + '="' + objAttrs[i] + '" ';
		str += '>';
		for (var i in params)
			str += '<param name="' + i + '" value="' + params[i] + '" /> ';
		str += '</object>';
 } else {
		str += '<embed ';
		for (var i in embedAttrs)
			str += i + '="' + embedAttrs[i] + '" ';
		str += '> </embed>';
 }

 document.getElementById("wel_flex").innerHtml=str;
}

function AC_FL_RunContent(){
	var ret = 
	 AC_GetArgs
	 (  arguments, ".swf", "movie", "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
	  , "application/x-shockwave-flash"
	 );
	AC_Generateobj(ret.objAttrs, ret.params, ret.embedAttrs);
}

function AC_GetArgs(args, ext, srcParamName, classid, mimeType){
var ret = new Object();
ret.embedAttrs = new Object();
ret.params = new Object();
ret.objAttrs = new Object();
for (var i=0; i < args.length; i=i+2){
 var currArg = args[i].toLowerCase();    

 switch (currArg){	
   case "classid":
     break;
   case "pluginspage":
     ret.embedAttrs[args[i]] = args[i+1];
     break;
   case "src":
   case "movie":	
     args[i+1] = AC_AddExtension(args[i+1], ext);
     ret.embedAttrs["src"] = args[i+1];
     ret.params[srcParamName] = args[i+1];
     break;
   case "onafterupdate":
   case "onbeforeupdate":
   case "onblur":
   case "oncellchange":
   case "onclick":
   case "ondblClick":
   case "ondrag":
   case "ondragend":
   case "ondragenter":
   case "ondragleave":
   case "ondragover":
   case "ondrop":
   case "onfinish":
   case "onfocus":
   case "onhelp":
   case "onmousedown":
   case "onmouseup":
   case "onmouseover":
   case "onmousemove":
   case "onmouseout":
   case "onkeypress":
   case "onkeydown":
   case "onkeyup":
   case "onload":
   case "onlosecapture":
   case "onpropertychange":
   case "onreadystatechange":
   case "onrowsdelete":
   case "onrowenter":
   case "onrowexit":
   case "onrowsinserted":
   case "onstart":
   case "onscroll":
   case "onbeforeeditfocus":
   case "onactivate":
   case "onbeforedeactivate":
   case "ondeactivate":
   case "type":
   case "codebase":
     ret.objAttrs[args[i]] = args[i+1];
     break;
   case "id":
   case "width":
   case "height":
   case "align":
   case "vspace": 
   case "hspace":
   case "class":
   case "title":
   case "accesskey":
   case "name":
   case "tabindex":
     ret.embedAttrs[args[i]] = ret.objAttrs[args[i]] = args[i+1];
     break;
   default:
     ret.embedAttrs[args[i]] = ret.params[args[i]] = args[i+1];
 }
}
ret.objAttrs["classid"] = classid;
if (mimeType) ret.embedAttrs["type"] = mimeType;
return ret;
}


