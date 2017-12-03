// 字符验证       
jQuery.validator.addMethod("stringCheck", function(value, element) {       
     return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
}, "只能包括中文字、英文字母、数字和下划线"); 

//英文字符验证       
jQuery.validator.addMethod("stringEN", function(value, element) {       
     return this.optional(element) || /^\w+$/ .test(value);       
}, "只能包括英文字母、数字和下划线");

//数字和点验证       
jQuery.validator.addMethod("isNumOrDot", function(value, element) {       
     return this.optional(element) || /^[.0-9]+$/ .test(value);       
}, "只能包括数字和点");

//中文字两个字节       
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {       
    var length = value.length;       
    for(var i = 0; i < value.length; i++){       
       if(value.charCodeAt(i) > 127){       
        length++;       
        }       
    }
    return this.optional(element) || ( length >= param[0] && length <= param[1] );       
}, "请输入字节长度介于 {0} 和 {1} 之间的字符串。(一个中文字符占两个字节)");



//日期时间格式验证       
jQuery.validator.addMethod("dateTime", function(value, element) {       
	var dateTime = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d)(:([0-5]\d))?)?$/;
    return this.optional(element) || (dateTime.test(value));       
}, "日期时间格式错误");  
//时间格式验证
jQuery.validator.addMethod("isTime", function(value, element) {       
	var timeValue = /^(([01]\d{1})|(2[0123]))\u5C0F\u65F6([0-5]\d)\u5206(:([0-5]\d))?$/;
    return this.optional(element) || (timeValue.test(value));       
}, "时间格式错误");
//车牌号验证
jQuery.validator.addMethod("vehicleNo", function(value, element) { 
	var num = /^[\u4e00-\u9fa5][A-Z](?:\-|\.)?[A-Z0-9]{5}$|^WJ[0-9]{2}(?:\-|\.)?(?:[\u4e00-\u9fa5]|[A-Z0-9])[A-Z0-9]{4}$/; 
	return this.optional(element) || (num.test(value)); 
}, "车牌号格式错误");

//IP地址验证
jQuery.validator.addMethod("isIP", function(value, element) { 
	var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/; 
	return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256)); 
	}, "IP地址格式错误");
//子网掩码验证
jQuery.validator.addMethod("isMark", function(value, element) { 
	var mark = /^((254|252|248|240|224|192|128|0)\.0\.0\.0)|(255\.(254|252|248|240|224|192|128|0)\.0\.0)|(255\.255\.(254|252|248|240|224|192|128|0)\.0)|(255\.255\.255\.(254|252|248|240|224|192|128|0))$/;
	return this.optional(element) || (mark.test(value)); 
	}, "子网掩码格式错误");
//IP地址/子网掩码验证
jQuery.validator.addMethod("isIPMark", function(value, element) {
	var ipMark = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\/?(?:((?:254|252|248|240|224|192|128|0)\.0\.0\.0)|(255\.(?:254|252|248|240|224|192|128|0)\.0\.0)|(255\.255\.(?:254|252|248|240|224|192|128|0)\.0)|(255\.255\.255\.(?:254|252|248|240|224|192|128|0)))?$/; 
	return this.optional(element) || (ipMark.test(value)); 
	}, "IP地址/子网掩码格式错误");
// 身份证号码验证       
jQuery.validator.addMethod("isIdCardNo", function(value, element) {       
    return this.optional(element) || IdCardValidate(value);       
}, "请正确输入您的身份证号码");
     
// 手机号码验证       
jQuery.validator.addMethod("isMobile", function(value, element) {       
    var length = value.length;   
    var mobile = /^(1[3,5,8]\d{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));       
}, "请正确填写您的手机号码");       
     
// 电话号码验证       
jQuery.validator.addMethod("isTel", function(value, element) {       
    var tel = /^\d{3,4}-?\d{7,8}$/;    //电话号码格式010-12345678   
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的电话号码");   
  
// 联系电话(手机/电话皆可)验证   
jQuery.validator.addMethod("isPhone", function(value,element) {
    var mobile = /^(1[3,5,8]\d{9})$/;
    var tel = /^\d{3,4}-?\d{7,9}$/;   
    return this.optional(element) || (tel.test(value) || mobile.test(value));  
  
}, "请正确填写您的联系电话");   
     
// 邮政编码验证       
jQuery.validator.addMethod("isZipCode", function(value, element) {       
    var tel = /^[0-9]{6}$/;       
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的邮政编码");  

//自定义列表中重复值的校验方法,第一个参数是列表ID，第二个参数是列字段序号
$.validator.addMethod("gridValueExisted", function(value, element, param) {       

	var col= $("#"+param[0]).jqGrid('getCol',param[1],false);//获取列名为name的列的值
	var result = true;
	for(var i=0;i<col.length;i++){
		if(value == col[i]){
			result = false;
			return false;
		}
	}
	return result;
}, "在列表中已存在");

//资源管理节点树验证方法resouTreeNodeNumExisted
//自定义树节点编号重复校验方法,第一个参数树ID，第二个参数是当前节点类型,第三个参数是资源树节点编号
$.validator.addMethod("resouTreeNodeNumExisted", function(value, element, param) {       
		var treeObj = $.fn.zTree.getZTreeObj(param[0]);
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		
		var result = true;
		$.each(nodes,function(i, n){
			//不能重复
			if(value == n.resourceNum && param[1] == n.type && param[2] != n.num) {
				result = false;
				return false;
			}
		});
		return result;
}, "此节点编号已存在");

//resoutreeNodeNameExisted
//自定义树节点名称重复校验方法,第一个参数树ID，第二个参数是当前节点类型,第三个参数是资源树节点编号，第四个参数是父节点编号
$.validator.addMethod("resouTreeNodeNameExisted", function(value, element, param) {
		var treeObj = $.fn.zTree.getZTreeObj(param[0]);
		//获得当前节点的父节点
		var pNode = treeObj.getNodesByFilter(function(node){
			return node.num == param[3];
		},true);
		//在当前站点节点下查找是否有重名的资源实例节点
		var cNode = treeObj.getNodesByFilter(function(node){
			return node.name == value && node.type == param[1] && node.num != param[2];
		},true,pNode);
		if(cNode == null){
			return true;
		}else{
			return false;
		}
}, "此节点名称已存在");  

//自定义树节点编号重复校验方法,第一个参数树ID，第二个参数是树节点id
$.validator.addMethod("treeNodeNumExisted", function(value, element, param) {       
		var treeObj = $.fn.zTree.getZTreeObj(param[0]);
		var nodes = treeObj.transformToArray(treeObj.getNodes());
		
		var result = true;
		$.each(nodes,function(i, n){
			//不能重复
			if(value == n.num && param[1] != n.id) {
				result = false;
				return false;
			}
		});
		return result;
}, "此节点编号已存在");

//自定义树节点名称重复校验方法,第一个参数树ID，第二个参数是当前节点类型,第三个参数是资源树节点编号，第四个参数是父节点编号
$.validator.addMethod("treeNodeNameExisted", function(value, element, param) {
		var treeObj = $.fn.zTree.getZTreeObj(param[0]);
		//获得当前节点的父节点
		var pNode = null;/*= treeObj.getNodesByFilter(function(node){
			return node.id == param[1];
		},true);*/
		//在父节点下查找是否有重名的节点
		var cNode = treeObj.getNodesByFilter(function(node){
			return node.name == value &&  node.id != param[2];
		},true,pNode);
		if(cNode == null){
			return true;
		}else{
			return false;
		}
}, "此节点名称已存在");  

$.validator.addMethod("treeNodeDesExisted", function(value, element, param) {
	var treeObj = $.fn.zTree.getZTreeObj(param[0]);
	//获得当前节点的父节点
	var pNode =null;/* = treeObj.getNodesByFilter(function(node){
		return node.id == param[1];
	},true);*/
	//在父节点下查找是否有重名的节点
	var cNode = treeObj.getNodesByFilter(function(node){
		return node.description == value &&  node.id != param[2];
	},true,pNode);
	if(cNode == null){
		return true;
	}else{
		return false;
	}
}, "此节点描述已存在");  

$.validator.addMethod("treeNodeURLExisted", function(value, element, param) {
	var treeObj = $.fn.zTree.getZTreeObj(param[0]);
	//获得当前节点的父节点
	var pNode =null;/* = treeObj.getNodesByFilter(function(node){
		return node.id == param[1];
	},true);*/
	//在父节点下查找是否有重名的节点
	var cNode = treeObj.getNodesByFilter(function(node){
		return node.url == value && node.id != param[2];
	},true,pNode);
	if(cNode == null){
		return true;
	}else{
		return false;
	}
}, "此节点URL已存在"); 

//自定义站点下资源实例名称重复校验方法,第一个参数树ID，第二个参数是当前节点类型,第三个参数是资源树节点编号,第四个参数是当前资源的站点编号
$.validator.addMethod("instanceNameExistedInSite", function(value, element, param) {
		var treeObj = $.fn.zTree.getZTreeObj(param[0]);
		//获得当前站点节点
		var snode = treeObj.getNodesByFilter(function(node){
			return node.type == "ZD" && node.resourceNum == $("#"+param[3]).val();
		},true);
		
		//在当前站点节点下查找是否有重名的资源实例节点
		var inode = treeObj.getNodesByFilter(function(node){
			return node.name == value && node.type == param[1] && node.num != param[2];
		},true,snode);
		
		if(inode == null){
			return true;
		}else{
			return false;
		}
}, "此节点在本站点下已存在");  

//图片格式校验
$.validator.addMethod("photoValidate", function(value, element) {
	if(value==""){     
		return true;     
    }else{
    	var point = value.lastIndexOf(".");     
        var type = value.substr(point);     
        if(type==".jpg"||type==".gif"||type==".JPG"||type==".GIF"||type==".PNG"||type==".png"){     
        	return true;
        }else{     
            return false;     
        }     
    }     
}, "图片格式必须为jpg、gif、png");  

//excel格式校验
$.validator.addMethod("excelValidate", function(value, element, param) {
	if(value==""){     
		return true;     
    }else{
    	var point = value.lastIndexOf(".");     
        var type = value.substr(point);     
        if(type==".xls"||type==".XLS"||type==".xlsx"||type==".XLSX"){     
            return true;
        }else{     
            return false;     
        }     
    }     
}, "非Excel文件"); 



/**  
 * 身份证15位编码规则：dddddd yymmdd xx p   
 * dddddd：地区码   
 * yymmdd: 出生年月日   
 * xx: 顺序类编码，无法确定   
 * p: 性别，奇数为男，偶数为女  
 * <p />  
 * 身份证18位编码规则：dddddd yyyymmdd xxx y   
 * dddddd：地区码   
 * yyyymmdd: 出生年月日   
 * xxx:顺序类编码，无法确定，奇数为男，偶数为女   
 * y: 校验码，该位数值可通过前17位计算获得  
 * <p />  
 * 18位号码加权因子为(从右到左) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]  
 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]   
 * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )   
 * i为身份证号码从右往左数的 2...18 位; Y_P为脚丫校验码所在校验码数组位置  
 *   
 */  
  
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X   
function IdCardValidate(idCard) {   
    idCard = trim(idCard.replace(/ /g, ""));   
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");// 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];// 加权求和   
    }   
    valCodePosition = sum % 11;// 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));// 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }    
}   
 /**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
} 
