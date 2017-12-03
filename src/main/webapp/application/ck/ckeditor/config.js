/**
 * @license Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn'; //配置语言
//	config.skin = 'office2003';//界面v2,kama,office2003
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;华文中宋/华文中宋;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+ config.font_names;
	config.enterMode = CKEDITOR.ENTER_BR;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
//	config.shiftEnterMode = 'p';
	
	// config.uiColor = '#FFF'; //背景颜色
	// config.width = 400;
	//宽度    // config.height = 400;
	//高度   
	config.skin = 'moono_blue';
	//编辑器皮肤样式    // 取消 “拖拽以改变尺寸”功能
	// config.resize_enabled = false;
	// 使用基础工具栏
	// config.toolbar = "Basic";
	//添加行距插件
	config.extraPlugins += (config.extraPlugins ? ',lineheight' : 'lineheight');
	//config.extraPlugins = 'lineheight';
	config.toolbar = 'MyToolbar';  
	   
    config.toolbar_MyToolbar =  
    [  
     	
        ['Cut','Copy','Paste'],  
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],  
        ['Image','Flash','Table','HorizontalRule','SpecialChar',],
        ['Link', 'Unlink'], 
        ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
        ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
        ['Maximize'],
        '/',
        ['Styles','Format','Font', 'FontSize','lineheight','TextColor', 'BGColor'],
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['Source']
        
    ];
    config.toolbar_AnswerToolbar =  
        [  
         	
         	['Styles','Format','Font', 'FontSize','lineheight'],
         	'/',
         	['Bold','Italic','Underline','Strike','-','Subscript','Superscript','TextColor', 'BGColor'],
         	['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock','Image']
            
        ];
    config.toolbar_ProblemToolbar =  
        [  
         	['Cut','Copy','Paste'],  
	        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],  
	        ['Image','Table','HorizontalRule', 'Smiley','SpecialChar',],
	        ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
	        ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
	        ['Maximize'],
	        '/',
	        ['Styles','Format','Font', 'FontSize','lineheight','TextColor', 'BGColor'],
	        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ];
    // 使用全能工具栏
	//config.toolbar = "Full";
	//使用自定义工具栏
	// config.toolbar =
	// [
	// ['Source', ],    
	// ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord',],    
	// ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
	// ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', SpecialChar','PageBreak'],
	//  ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
    // '/',    
	// ['Bold', 'Italic', 'Underline','Strike', '-', 'Subscript', 'Superscript'],    
	// ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'],    
	// ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],    
	// ['Link', 'Unlink', 'Anchor'],   
	// '/',    
	// ['Styles','Format', 'Font', 'FontSize'],    
	// ['TextColor', 'BGColor'],    
	// ['Maximize', 'ShowBlocks', '-', 'About']    
	// ];   
	// 在 CKEditor 中集成 CKFinder注意 ckfinder 的路径选择要正确。
    //E:\usedBysugar\apache-tomcat-7.0.39\webapps\DMIMS\application\template\themes\base\js\ckfinder
    var basepath = '/SDL/application/ckfinder';
	config.filebrowserBrowseUrl = basepath + '/ckfinder.jsp',   
	config.filebrowserImageBrowseUrl = basepath +'/ckfinder.jsp?type=Images',   
	config.filebrowserFlashBrowseUrl = basepath+'/ckfinder.jsp?type=Flash',   
	config.filebrowserUploadUrl = basepath + '/core/connector/java/connector.java?command=QuickUpload&type=Files',   
	config.filebrowserImageUploadUrl = basepath + '/core/connector/java/connector.java?command=QuickUpload&type=Images',   
	config.filebrowserFlashUploadUrl = basepath + '/core/connector/java/connector.java?command=QuickUpload&type=Flash',   
	config.filebrowserWindowWidth = '1000',   config.filebrowserWindowHeight = '700' ;

};
