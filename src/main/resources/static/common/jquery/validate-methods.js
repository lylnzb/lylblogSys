// JavaScript Document


/*****************************************************************

                  jQuery Validate扩大验证办法  (linjq)       

*****************************************************************/

$(function(){

    // 断定整数value是否便是0 
    jQuery.validator.addMethod("isIntEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value==0;       
    }, "整数必须为0"); 

    // 断定整数value是否大于0
    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value>0;       
    }, "整数必须大于0"); 

    // 断定整数value是否大于或便是0
    jQuery.validator.addMethod("isIntGteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value>=0;       
    }, "整数必须大于或便是0");   

    // 断定整数value是否不便是0 
    jQuery.validator.addMethod("isIntNEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value!=0;       
    }, "整数必须不便是0");  

    // 断定整数value是否小于0 
    jQuery.validator.addMethod("isIntLtZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<0;       
    }, "整数必须小于0");  
    

    // 断定整数value是否小于或便是0 
    jQuery.validator.addMethod("isIntLteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<=0;       
    }, "整数必须小于或便是0");  

    // 断定浮点数value是否便是0 
    jQuery.validator.addMethod("isFloatEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value==0;       
    }, "浮点数必须为0"); 

    // 断定浮点数value是否大于0
    jQuery.validator.addMethod("isFloatGtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>0;       
    }, "浮点数必须大于0"); 

    // 断定浮点数value是否大于或便是0
    jQuery.validator.addMethod("isFloatGteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>=0;       
    }, "浮点数必须大于或便是0");   

    // 断定浮点数value是否不便是0 
    jQuery.validator.addMethod("isFloatNEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value!=0;       
    }, "浮点数必须不便是0");  

    // 断定浮点数value是否小于0 
    jQuery.validator.addMethod("isFloatLtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<0;       
    }, "浮点数必须小于0");  

    // 断定浮点数value是否小于或便是0 
    jQuery.validator.addMethod("isFloatLteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<=0;       
    }, "浮点数必须小于或便是0");  

    // 断定浮点型  
    jQuery.validator.addMethod("isFloat", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "只能包含数字、小数点等字符"); 

    // 匹配integer
    jQuery.validator.addMethod("isInteger", function(value, element) {       
         return this.optional(element) || (/^[-\+]?\d+$/.test(value) && parseInt(value)>=0);       
    }, "匹配integer");  

    // 断定数值类型,包含整数和浮点数
    jQuery.validator.addMethod("isNumber", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "匹配数值类型,包含整数和浮点数");  

    // 只能输入[0-9]数字
    jQuery.validator.addMethod("isDigits", function(value, element) {       
         return this.optional(element) || /^\d+$/.test(value);       
    }, "只能输入0-9数字");      

    // 断定中文字符 
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "只能包含中文字符。");   

    // 断定英文字符 
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "只能包含英文字符。");   

     // 号码验证    
    jQuery.validator.addMethod("isMobile", function(value, element) {    
      var length = value.length;
      return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(147)|(15[0-9]{1})|16[6]{1}|(17[0-8]{1})|(18[0-9]{1})|(19[8-9]{1}))+\d{8})$/.test(value));    
    }, "请正确填写您的号码。");

    // 德律风号码验证    
    jQuery.validator.addMethod("isPhone", function(value, element) {    
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
      return this.optional(element) || (tel.test(value));    
    }, "请正确填写您的德律风号码。");

    // 接洽德律风(/德律风皆可)验证   
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|16[6]{1}|(17[0-8]{1})|(18[0-9]{1})|(19[8-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写您的号码"); 

     // 匹配qq      
    jQuery.validator.addMethod("isQq", function(value, element) {       
         return this.optional(element) || /^[1-9]\d{4,12}$/;       
    }, "匹配QQ");   

     // 邮政编码验证    
    jQuery.validator.addMethod("isZipCode", function(value, element) {    
      var zip = /^[0-9]{6}$/;    
      return this.optional(element) || (zip.test(value));    
    }, "请正确填写您的邮政编码。");  

    // 匹配暗码,以字母开首,长度在6-12之间,只能包含字符、数字和下划线。      
    jQuery.validator.addMethod("isPwd", function(value, element) {       
         return this.optional(element) || /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[a-zA-Z\d@$!%*#?&()_+^]{8,20}$/.test(value);    
         
    }, "长度介于8到20之间，必须含大写字母、小写字母、数字。");  

    // 身份证号码验证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
      //var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;   
      return this.optional(element) || isIdCardNo(value);    
    }, "请输入正确的身份证号码。"); 

    // IP地址验证   
    jQuery.validator.addMethod("ip", function(value, element) {    
      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
    }, "请填写正确的IP地址。");

    // 字符验证,只能包含中文、英文、数字、下划线等字符。    
    jQuery.validator.addMethod("stringCheck", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
    }, "只能包含中文、英文、数字、下划线等字符");   

    // 匹配english  
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "匹配english");   

    // 匹配汉字  
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u4e00-\u9fa5]+$/.test(value);       
    }, "匹配汉字");   

    // 匹配中文(包含汉字和字符) 
    jQuery.validator.addMethod("isChineseChar", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "匹配中文(包含汉字和字符) "); 

    // 断定是否为合法字符(a-zA-Z0-9-_)
    jQuery.validator.addMethod("isRightfulString", function(value, element) {       
         return this.optional(element) || /^[A-Za-z0-9_-]+$/.test(value);       
    }, "断定是否为合法字符(a-zA-Z0-9-_)");   

    // 断定是否包含中英文特别字符,除英文"-_"字符外
    jQuery.validator.addMethod("isContainsSpecialChar", function(value, element) {  
         var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\"")(\:)(\;)(\"")("",)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\')(\；)(\：)(\”)(\“)(\，)(\。)(\、)(\、)(\？)]+/);   
         return this.optional(element) || !reg.test(value);       
    }, "含有中英文特别字符");  
	
    //工商登记执照号验证， 匹配字母数字半角-_()或全角（）——
    jQuery.validator.addMethod("AAB007", function(value, element) {       
         return this.optional(element) ||  /^[a-zA-Z0-9-_\(\)\（\）\-\_\——]{10,24}$/.test(value);   
    }, "10-24个字符，限字母数字半角-_()或全角（）——");   

    //组织机构代码验证， 匹配数字，符号"-""—""_"
    jQuery.validator.addMethod("AAB003", function(value, element) {       
         return this.optional(element) ||  /^[a-zA-Z0-9-_\-\_]{10,14}$/.test(value);   
    }, '10-14个字符，限英文、数字、符号"-""—""_"'); 

    // 姓名验证,只能包含中文，英文，符号".""•""`"字符。    
    jQuery.validator.addMethod("isname", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z\u4e00-\u9fa5-_\.\•\`]+$/.test(value);       
    }, '4-60个字符，限中文、英文、".""•""`"字符');   

    //联系电话验证， 匹配数字，符号"-""—""_"
    jQuery.validator.addMethod("isphone", function(value, element) {       
         return this.optional(element) ||  /^[0-9-_\-\_\—]{6,15}$/.test(value);   
    }, '6-15个字符，限数字，符号"-""—""_"');   

    // 用户名验证,由首字符为字母，6~20个字母、数字组成。    
    jQuery.validator.addMethod("isusername", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z][a-zA-Z0-9]{5,19}$/.test(value);    
    }, '由首字符为字母，6~20个字母、数字组成');   

    // 密码验证,由6~20个大小写字母数字及特殊符号组成。    
    jQuery.validator.addMethod("ispassword", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z0-9-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]{6,20}$/.test(value);
    }, '由6~20个大小写字母数字及特殊符号组成');   
	
    // 匹配数字，符号"-""—""_"
    jQuery.validator.addMethod("nochinese", function(value, element) {       
         return this.optional(element) ||  /^[a-zA-Z0-9-_\-\_\—]+$/.test(value);   
    }, '限英文、数字、符号"-""—""_"'); 
	
	//重写date，解决日期格式yyyy-mm-dd 在ie下不合法bug.
	jQuery.validator.methods.date = function (value, element) {
		var matches = /(\d{4})[-\/](\d{2})[-\/](\d{2})/.exec(value);
		if (matches == null) return this.optional(element)|| false;
		return this.optional(element) || true;
	};	
	//验证小数位数两位数
	jQuery.validator.addMethod("isFloat4", function(value, element) {    
		 var returnVal = true;
		 var ArrMen= value.split(".");    //截取字符串
		 if(ArrMen.length==2){
            if(ArrMen[1].length>4){    //判断小数点后面的字符串长度
                return false;
            }
         }else if(ArrMen.length>2){
         	 return false;
         }else{
        	 return true;
         }
         return returnVal;
	}, '小数点不能超过4位'); 
	jQuery.validator.addMethod("isFloat2", function(value, element) {    
		var returnVal = true;
		var ArrMen= value.split(".");    //截取字符串
		if(ArrMen.length==2){
	          if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
	              return false;
	          }
        }else if(ArrMen.length>2){
        	return false;
        }else{
        	return true;
        }
       return returnVal;
   	}, '小数点不能超过2位'); 
    //身份证号码的验证规矩
    function isIdCardNo(num){ 
    	//if (isNaN(num)) {alert("输入的不是数字！"); return false;} 
    	var len = num.length, re; 
    	if (len == 15) 
			re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
    	else if (len == 18) 
			re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
    	else {
            //alert("输入的数字位数不合错误。"); 
            return false;
        } 
    	var a = num.match(re); 
    	if (a != null) 
    	{ 
    		if (len==15){ 
				var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
				var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
    		} 
    		else{ 
				var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
				var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
			} 
    		if (!B) {
				//alert("输入的身份证号 "+ a[0] +" 里出身日期不合错误。"); 
				return false;
			} 
    	} 
    	if(!re.test(num)){
            //alert("身份证最后一位只能是数字和字母。");
            return false;
        }
    	 return true; 
    } 
    
    jQuery.validator.addMethod("isIdCardNo2", function(value, element){ 
    	// 加权因子
        var weight_factor = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
        // 校验码
        var check_code = ['1', '0', 'X' , '9', '8', '7', '6', '5', '4', '3', '2'];
        var code = value + "";
        var last = value[17];//最后一位
        var seventeen = code.substring(0,17);
        // ISO 7064:1983.MOD 11-2
        // 判断最后一位校验码是否正确
        var arr = seventeen.split("");
        var len = arr.length;
        var num = 0;
        for(var i = 0; i < len; i++){
            num = num + arr[i] * weight_factor[i];
        }
        
        // 获取余数
        var resisue = num%11;
        var last_no = check_code[resisue];

        // 格式的正则
        // 正则思路
        /*
	        第一位不可能是0
	        第二位到第六位可以是0-9
	        第七位到第十位是年份，所以七八位为19或者20
	        十一位和十二位是月份，这两位是01-12之间的数值
	        十三位和十四位是日期，是从01-31之间的数值
	        十五，十六，十七都是数字0-9
	        十八位可能是数字0-9，也可能是X
        */
        var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/;

        // 判断格式是否正确
        var format = idcard_patter.test(value);

        // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
        return last === last_no && format ? true : false;
    } , '身份证格式不正确'); 
});