//保存当前tab的url
//var tabCurrentPath ='';
//var reqBasePath = basePath;
/*if(reqBasePath=="http://10.139.33.70:7001/laborsys/"){
	reqBasePath = "http://10.139.7.140:7001/laborsys/";
}*/
jQuery(function($){
    // 备份jquery的ajax方法  
    var _ajax=$.ajax;
    // 重写ajax方法，先判断登录在执行success函数 
    $.ajax=function(opt){
    	var _success = opt && opt.success || function(a, b){};
        var _opt = $.extend(opt, {
        	success:function(data, textStatus){
        		// 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)
        		if(typeof data =="object" && data.toString().indexOf('dengluchaoshi') != -1) {
        			window.location.href= basePath + "/basicframe/login/logout.jsp";
        			return;
        		}
        		_success(data, textStatus);  
            } ,
            //如果前台定义了接收类型为json，而返回的data不是json格式，将会跳转到error方法里来
            error:function(XMLHttpRequest, textStatus, errorThrown){
            	// 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)         	
        		if(typeof XMLHttpRequest =="object" && typeof(XMLHttpRequest.responseText) != "undefined" && XMLHttpRequest.responseText.toString().indexOf('dengluchaoshi') != -1) {
        			window.location.href= basePath + "/basicframe/login/logout.jsp";
        			return;
        		}
            }
        });
        _ajax(_opt);
    };
    
    $('.easyui-combobox').combobox({ editable:false}); 
});

var exportformatdata = [
	{"valueName": "请选择导出格式", "valueCode": ""},
	{"valueName": "Excel", "valueCode": "excel"},
	{"valueName": "Word", "valueCode": "word"},
	{"valueName": "Pdf", "valueCode": "pdf"}	
];
$(function(){
	//禁止后退键 作用于Firefox、Opera 
	document.onkeypress=banBackSpace; 
	//禁止后退键 作用于IE、Chrome 
	document.onkeydown=banBackSpace; 

	$.ajaxSetup ({
		cache: false //关闭AJAX缓存
	});
	
	/*高级查询*/
	$(".expert_search").click(function() {
		var surl=$(this).attr("chref");
		if($("#expert_s").length ==0){
			$.ajax({ url: surl,cache: false,dataType: "html", 
				success: function(data){
					$("#expert_sea").append(data);
					$.parser.parse($("#expert_sea"));
				}
			});
		}
	});

});


var dialogdiv = parent.window.$("#detail_dialog");

var imageDiv = top.window.$("#image_dialog");
var img = top.window.$("#img");

//表格的当前页码=分页控件的当前页码，解决每页行号都从1开始的bug
function rowNumber(dg){
	if(typeof(pageNumber)!="undefined" && typeof(pageSize)!="undefined" && pageNumber!="" && pageSize!="" ) {
		dg.datagrid('options').pageNumber = pageNumber;
		dg.datagrid('options').pageSize=pageSize;
	}else{
		dg.datagrid('options').pageNumber=getPageNamber(dg);
		dg.datagrid('options').pageSize=dg.datagrid('getPager').pagination("options").pageSize;
	}
}

//返回当前页码
function getPageNamber(dg){
	if(typeof(pageNumber)!="undefined" && typeof(pageSize)!="undefined" && pageNumber!="" && pageSize!="" ) {
		dg.datagrid('getPager').pagination("options").pageNumber=pageNumber;
		dg.datagrid('getPager').pagination("options").pageSize=pageSize;
		return pageNumber;
	}else{
		return dg.datagrid('getPager').pagination("options").pageNumber;
	}
}

//设置当前页码
function setPageNamber(dg,pageNumber,pageSize){
	dg.datagrid('getPager').pagination("options").pageNumber=pageNumber;
	dg.datagrid('getPager').pagination("options").pageSize=pageSize;
}

//计算datagrid序号
function getRowNumber(dg, index){
	var options = dg.datagrid('getPager').data("pagination").options; 
	var curPage = options.pageNumber;
	var pageSize = options.pageSize;
	return (pageSize * (curPage - 1)) + (index + 1);
}

//打开全局弹出页面
function dialogopen(href, title, width, height, fun){
	if(!isNo(href)){
		alert("请求参数错误");
		return;
	}
	dialogdiv.dialog({
		title:title, width:width, height:height, 
		maximizable:false, maximized:false 
	});
	dialogdiv.dialog({
		onClose: function () {
			if(fun) fun();
			dialogdiv.find("iframe").attr("src", "");
			dialogdiv.dialog({
				onClose: function () {}
			});
		}
	});
	dialogdiv.find("iframe").attr("src", href);
	dialogdiv.dialog('open');
}

//打开全局弹出页面（自适应）
function dialogopen_auto(href, title, fun){
	if(!isNo(href)){
		alert("请求参数错误");
		return;
	}
	var width = document.body.clientWidth
	var height = document.body.clientHeight;
	dialogopen(href, title, width, height, fun);
}
function isNo(str) {
	var pattern = new RegExp("[`~#$^*{}''()<>￥……（）【】‘；。，、%]");   /*定义验证表达式*/
	if(pattern.test(str)){
	     return false;
	} 
	return true;/*进行验证*/
}
function dialogresize(width, height){
	dialogdiv.dialog({
		width:width, height:height 
	});
	dialogdiv.dialog('open');
}

//弹出窗口（本层）
function dialogopenself(href, title, width, height, fun){
	if(!isNo(href)){
		alert("请求参数错误");
		return;
	}
	var dialogdiv = $("#detail_dialog");
	dialogdiv.dialog({
		title:title, width:width, height:height, 
		maximizable:false, maximized:false
	});
	dialogdiv.dialog({
		onClose: function () {
			if(fun) fun();
			dialogdiv.find("iframe").attr("src", "");
			dialogdiv.dialog({
				onClose: function () {}
			});
		}
	});
	dialogdiv.find("iframe").attr("src", href);
	dialogdiv.dialog('open');
}

function dialogopenself_auto(href, title){
	if(!isNo(href)){
		alert("请求参数错误");
		return;
	}
	var width = document.body.clientWidth * 0.95;
	var height = document.body.clientHeight * 0.95;
	dialogopenself(href, title, width, height);
}

function dialogresizeself(width, height){
	var dialogdiv = $("#detail_dialog");
	dialogdiv.dialog({
		width:width, height:height 
	}); 
	dialogdiv.dialog('open');
}

//全局弹出页面回调方法
function doCallback(){
	if(!callback) return;
	var tab = top.$('#tabs').tabs('getSelected');
	var index = top.$('#tabs').tabs('getTabIndex', tab);
	var cmd = "top.document.getElementsByName('content')[" + index + "].contentWindow." + callback + "();"
	eval(cmd);
}

function d_close(){
	$("#detail_dialog").dialog('close');
}

function d_close_self(){
	var dialogdiv = $("#detail_dialog");
	dialogdiv.find("iframe").attr("src", "");
	$("#detail_dialog").dialog('close');
}

//给需要验证的input添加样式。
function reqtrue(){
	$(".requtrue").before('<span class="reqtrue"></span>');	
	$(".requtrue").after('<span class="error reerror"></span>');	
	$(".requfalse").after('<span class="error reerror"></span>');		
}

//查询字典数据集合
function getCodeValue(code){
	var data=[];
	var scodearr=top.codearr;
	for(var i=0; i<scodearr.length; i++){
		if(code ==scodearr[i].aaa100){
			data.push({ "aaa102": scodearr[i].aaa102, "aaa103":scodearr[i].aaa103 });
		}
	}
	return data;
}

//查询数据字典对应名称
function getValueNameByCode(arr, valueCode){
	if(arr){
		for(var i=0; i<arr.length; i++){
			if(valueCode ==arr[i].aaa102){
				return arr[i].aaa103;
			}
		}
	}
	return "";
}

//根据自定义对象填充
function fillCodeValue(id, arr){
	var data=[{ "valueName": "请选择", "valueCode": "" }];
	for(var i=0; i<arr.length; i++){
		data.push({ "valueName": arr[i].aaa103, "valueCode":arr[i].aaa102 });
	}
	$("#"+id).combobox({
		valueField: 'valueCode',    
		textField: 'valueName', 
		data:data
	});
	$("#"+id).combobox('setValue',data[0].valueCode );
}

function fillCodeValue2(id, arr, orgId, aab004){
	var data=[{ "valueName": aab004, "valueCode": orgId}];
	for(var i=0; i<arr.length; i++){
		data.push({ "valueName": arr[i].aaa103, "valueCode":arr[i].aaa102 });
	}
	$("#"+id).combobox({
		valueField: 'valueCode',    
		textField: 'valueName', 
		data:data
	});
	$("#"+id).combobox('setValue',data[0].valueCode );
}

//取类别信息，两个参数:code:id名称，类别编号;required：是否必填，false时添加请选择选项。
function codeValue(id,code,required){
	var codeUpper = code.toUpperCase();
	var data=[{ "valueName": "请选择", "valueCode": "" }];
	var scodearr=parent.parent.codearr;
	for(var i=0; i<scodearr.length; i++){
		if(codeUpper ==scodearr[i].aaa100){
			data.push({ "valueName": scodearr[i].aaa103, "valueCode":scodearr[i].aaa102 });
		}
	}
	$("#"+id).combobox({
		valueField: 'valueCode',    
		textField: 'valueName', 
		data:data
	});
	$("#"+id).combobox('setValue',data[0].valueCode );
}

function codeValue_(id, code, required) {
	var codeUpper = code.toUpperCase();
	var data=[{ "valueName": "请选择", "valueCode": "" }];
	var scodearr=parent.parent.codearr;
	for(var i=0; i<scodearr.length; i++){
		if(codeUpper ==scodearr[i].aaa100){
			data.push({ "valueName": scodearr[i].aaa103, "valueCode":scodearr[i].aaa102 });
		}
	}
	$("#"+id).combobox({
		valueField: 'valueCode',    
		textField: 'valueName', 
		data:data
	});
	$("#"+id).combobox('setValue',data[0].valueCode );

}

//取bbe303集体合同分类类别信息，删除4企业专项集体合同(女职工权益保护)
function codeValue_bbe303(code,required){
	var codeUpper = code.toUpperCase();
	codeMgrFacade.queryCodeValueByType(codeUpper,function(data2){
		data2.splice(3,1); //3指位置，从0开始，1指删除的数量。
		//if(!required){
			data2.unshift({ "valueName": "请选择", "valueCode": "" });
		//}
		$("#"+code).combobox({
			valueField: 'valueCode',    
	        textField: 'valueName', 
			data:data2
		});
		$("#"+code).combobox('setValue',data2[0].valueCode );

	});
	
}


//重置
function unitreset(){
	$(':input','#unitfrm').not(':button, :submit, :reset, :hidden,:radio').val('');
	$("#unitfrm .combo-text").val('请选择');
	reload();
}


//文件下载保存，兼容IE
function saveit(src) { 
	I1.document.location = src; 
	savepic(); 
	
	/*var elemIF = document.createElement("iframe");  
	elemIF.src = src;//文件路径
	elemIF.style.display = "none";  
	document.body.appendChild(elemIF);  */
} 
function savepic() { 
	if (I1.document.readyState == "complete") { 
		I1.document.execCommand("saveas"); 
	} else { 
		window.setTimeout("savepic()", 10); 
	} 
} 

//获取当前时间YYYYMM
function getDateYYYYMM(){
	var date = new Date;
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	month = (month<10 ? "0"+month : month);
	var mydate = (year.toString()+month.toString());
	return mydate;
}

//获取n月前的时间YYYYMM
function getBeforeDateYYYYMM(n){
	var date = new Date;
	var year = date.getFullYear();
	var month = date.getMonth()+1;	
	year = (month-n>0 ? year : year-1);
	month = (month-n>0 ? month-n : (month+12-n));
	month = (month<10 ? "0"+month : month);
	var mydate = (year.toString()+month.toString());
	return mydate;
}

//获取当前时间
function getMydate(){
	var myDate = new Date();
	var month =myDate.getMonth()+1;
	//月分小于10前面加0
	if(month < 10) month = "0" + month;
	var str = myDate.getFullYear()+"-";
	str += +month+"-"+myDate.getDate();
	return str;
}

//获取当前时间 yyyy/MM/dd HH:MM:SS
function getCurrentTimeStr(){
	var date = new Date();
	var seperator1 = "/";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		+ " " + date.getHours() + seperator2 + date.getMinutes()
		+ seperator2 + date.getSeconds();
	return currentdate;
}

//获取当前时间的下个月
function getMyNextMonth(){
	var myDate = new Date();
	var month =myDate.getMonth()+2;
	//月分小于10前面加0
	if(month < 10) month = "0" + month;
	var str = myDate.getFullYear()+"-";
	str += +month+"-"+myDate.getDate();
	return str;
}

//获取本月第一天
function getMyMonth(){
	var myDate = new Date();
	var month =myDate.getMonth()+1;
	if(month < 10) month = "0" + month;
	var str = myDate.getFullYear()+"-";
	var month =myDate.getMonth()+1;
	//月分小于10前面加0
	if(month < 10) str += "0";
	str += +month+"-01";
	return str;
}

//审核信息时，对比变更的信息。
function changeValue(filed,oldvalue){
	if($("[name="+filed +"]").attr("type")=='hidden'){
		var thisdiv = $("[name="+filed +"]").siblings(':input');
	}else{
		var thisdiv = $("[name="+filed +"]");
	}
	thisdiv.addClass("changetext");
	thisdiv.tooltip({
		position: 'right',
		content: '<span>原值：'+ oldvalue +'</span>'		
	});
}

//js获取url参数值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//去html标签并截取长度，str字符串，len长度，len为-1不截取
function delHtmlTag(str,len){  
	var title = str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	if(len !=-1 && title.length > len) {
		title = title.substring(0,len) +"......";
	}
	return title;
} 


//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外 
function banBackSpace(e){ 
	var ev = e || window.event;//获取event对象 
	var obj = ev.target || ev.srcElement;//获取事件源 
	
	var t = obj.type || obj.getAttribute('type');//获取事件源类型 
	
	//获取作为判断条件的事件类型 
	var vReadOnly = obj.getAttribute('readonly'); 
	var vEnabled = obj.getAttribute('enabled'); 
	//处理null值情况 
	vReadOnly = (vReadOnly == null) ? false : vReadOnly; 
	vEnabled = (vEnabled == null) ? true : vEnabled; 
	
	//当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
	//并且readonly属性为true或enabled属性为false的，则退格键失效 
	var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") && (vReadOnly==true || vEnabled!=true))?true:false; 
	
	//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
	var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false; 
	
	//判断 
	if(flag2){ 
		return false; 
	} 
	if(flag1){ 
		return false; 
	} 
} 

/* 
* 特殊字符在ASCII码中所表示的范围为32~48，57~65，90~97 
* event.returnValue=false;设置键盘输入主false，则不能在文本框中输入内容 
*/ 
function checkComments(){ 
	if (( event.keyCode > 32 && event.keyCode < 48) || 
	( event.keyCode > 57 && event.keyCode < 65) || 
	( event.keyCode > 90 && event.keyCode < 97) 
	) { 
	event.returnValue = false; 
	} 
}

//查询框输入验证， 只能输入数字
function checkNumbers(){
	if ( event.keyCode > 57 || event.keyCode < 48){ 
		event.returnValue = false; 
	} 
}

//filepath：download/unitOperationManual.doc
function fileDownload(filepath){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action",basePath +"/file/download");
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","filepath");
	input2.attr("value",filepath);
	$("body").append(form);//将表单放置在web中
	form.append(input2);
	form.submit();//表单提交 
}

//filepath：/download/unitOperationManual.doc
function fileDownload2(filepath){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action",basePath +"/file/download2");
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","filepath");
	input2.attr("value",filepath);
	$("body").append(form);//将表单放置在web中
	form.append(input2);
	form.submit();//表单提交 
}

function windowClear(div,frm){
	$('#'+div).window({
		onBeforeClose:function(){
			$(':input','#'+frm).not(':button, :submit, :reset, :radio, :disabled').val('');
			$("#"+frm+" .combo-text").val('请选择');
		}
	});
	$('#'+div).window('close');
}

function getAa10Aaa103(aaa100,aaa102){
	var scodearr=parent.parent.codearr;
	for(var i=0; i<scodearr.length; i++){
		if(aaa100 ==scodearr[i].aaa100 && aaa102 ==scodearr[i].aaa102){
			return scodearr[i].aaa103;
		}
	}
	return '';
}

//重置事件  
function ClearInput(frm) { 
	//$('#'+frm).form('clear');
	$(':input','#'+frm).not(':button, :submit, :reset, :hidden,:radio, :disabled').val('');
	$("#"+frm+" .combo-text").not(':disabled').val('请选择');
	$("#"+frm+" .combo-text").siblings(":input").val('');
	var validator = $("#"+frm).validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
}

function ClearInput2(frm) { 
	//$('#'+frm).form('clear');
	$(':input','#'+frm).not(':button, :submit, :reset, :hidden,:radio').val('');
	$("#"+frm+" .combo-text").not(':disabled').val('请选择');
	$("#"+frm+" .combo-text").siblings(":input").val('');
	var validator = $("#"+frm).validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
}

Date.prototype.format = function(fmt) { 
     var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
};

function dateFormat(timeStr, format){
	if(!timeStr){
		return "";
	}
	timeStr = timeStr.replace("-", "/");
	var stime = new Date(timeStr);
	var fmt = format ? format : "yyyy/MM/dd hh:mm:ss";
	return stime.format(fmt);
}

var addFunCallBack; //新增回调方法
var updateFunCallBack; //修改回调方法
var deleteFunCallBack; //删除回调方法
var submitFunCallBack; //提交回调方法
var cancelFunCallBack; //取消回调方法

//提示信息
function showMsg(message) {
	$.messager.alert('提示', message);
}

/*
 * treejson列表转换为树
 */
function formatTreeJson(list, index){
	var root = list[index];
	var nodes = new Array();
	for(var i=index; i<list.length; i++){
		if(root.id == list[i].parentId){
			nodes[nodes.length] = formatTreeJson(list, i);
		}
	}
	//非根节点checked设为false
	if(nodes.length > 0){
		if(root.parentId){
			root.state = "closed";
		}
//		root.checked = false;
	}
	root.children = nodes;
	return root;
}
