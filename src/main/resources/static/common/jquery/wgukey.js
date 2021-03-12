/**
 * 
 */

// 反序
function getReverseStr(str) {
    var out;
    var b = str.split('');
    b.reverse()
    out = b.join('');
    return out;

}

// base64编解码
function utf16to8(str) {
    var out, i, len, c;

    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}

function utf8to16(str) {
    var out, i, len, c;
    var char2, char3;

    out = "";
    len = str.length;
    i = 0;
    while (i < len) {
        c = str.charCodeAt(i++);
        switch (c >> 4) {
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                // 0xxxxxxx
                out += str.charAt(i - 1);
                break;
            case 12: case 13:
                // 110x xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                break;
            case 14:
                // 1110 xxxx 10xx xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                char3 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x0F) << 12) |
	((char2 & 0x3F) << 6) |
	((char3 & 0x3F) << 0));
                break;
        }
    }

    return out;
}
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
	-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
	-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

function base64encode(str) {
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if (i == len) {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}

function base64decode(str) {
    // alert(str);
    // alert(str.length);
    var c1, c2, c3, c4;
    var i, len, out;

    len = str.length;
    // alert(len);
    i = 0;
    out = "";

    while (i < len) {
        /* c1 */
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c1 == -1);
        if (c1 == -1)
            break;

        /* c2 */
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c2 == -1);
        if (c2 == -1)
            break;

        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

        /* c3 */
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61)
                return out;
            c3 = base64DecodeChars[c3];
        } while (i < len && c3 == -1);
        if (c3 == -1)
            break;

        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

        /* c4 */
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61)
                return out;
            c4 = base64DecodeChars[c4];
        } while (i < len && c4 == -1);
        if (c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}
// 连接SSKEY
var Mes = false;
function linksskey() {

    try {
        Mes = WGUKey.ConnectUK("WALLGREATRSA");
        if (Mes === false) {
            alert("请插入SSKEY设备!"); // 请插入SSKEY设备!
            // return false;
        }
    }
    catch (err) {
        alert("连接SSKEY异常");
        // return false;
    }
    // alert("连接成功");
    return Mes;
}
// 签名
function sign(indata) {
    var poutdata = "";
    if (indata == "") {
        alert("请输入签名数据!"); // 请重新输入签名数据
    } else {
        var b = WGUKey.UKSignData("WALLGREAT", indata); // 开始签名
        if (b === false) {
            alert("SSKey签名失败!"); // SSKey签名失败!
        }
        // poutdata = getReverseStr(WGUKey.Data1);

        var temp = base64decode(WGUKey.Data1);
        poutdata = getReverseStr(temp);
        poutdata = base64encode(poutdata);

        // 先反BASE64，反序，再BASE64
       // var temp = decode64(WGUKey.Data1);
       // poutdata = getReverseStr(temp);
       // poutdata = encode64(poutdata);
    }
    return poutdata;
}
// 连接UKEY签名
function linksign() {
    if (Mes == false) {
        alert("请插入UKEY设备!");
        return false;
    }
    var indata = document.getElementById("testInfo").value;
    var poutdata = document.getElementById("lasttestInfo");
    try {
        var issignlinkok = false;
        issignlinkok = linksskey();
        if (issignlinkok == true) {
            poutdata.value = sign(indata);
            // alert(sign(indata));
            return poutdata.value;
        }
    }
    catch (err) {
        alert("SSKEY签名发生错误!"); // SSKEY签名发生错误!
    }
    // return "";
    return false;
}
// 读出签名证书
function readCertData(dwCertFlag, pszContainerName) {
    var certdata = "";
    var b = WGUKey.UKReadCertData(dwCertFlag, pszContainerName);

    if (b == true) {
        certdata = WGUKey.Data1;
        return certdata;
    }
    return "";

}
function verifyData(szCertData, pbSrcData, szDestData, nCertFlag) {
    var b = WGUKey.UKVerifyData(szCertData, pbSrcData, szDestData, nCertFlag);

    return b;
}
// 验证签名
function verify() {
    if (Mes == false) {
        alert("请插入UKEY设备!");
        return false;
    }
    var szCertData = readCertData(1, "WALLGREAT");

    // alert(szCertData);
    var pbSrcData = document.getElementById("testInfo").value;
    
    var szDestData = document.getElementById("lasttestInfo").value;

    // 先反BASE64，反序，再BASE64
    var temp = decode64(szDestData);
    szDestData = getReverseStr(temp);
    szDestData = encode64(szDestData);

    var b = verifyData(szCertData, pbSrcData, szDestData, 0);
    if (b == true) {
        alert("验证签名成功!");
    } else {
        alert("验证签名失败!");
        return false;
    }
    return "";
}
        function verifyServer() {
    if (Mes == false) {
        alert("请插入UKEY设备!");
        return false;
    }
    // var szCertData =
	// "MIIChTCCAiigAwIBAgIFAUfF2lUwDAYIKoEcz1UBg3UFADB+MQswCQYDVQQGEwJDTjEOMAwGA1UECAwFSHVuYW4xETAPBgNVBAcMCENoYW5nc2hhMS8wLQYDVQQKDCZEb25nRmFuZyBFbGVjdHJvbmljIENlcnRpZmljYXRlIENlbnRlcjEbMBkGA1UEAwwSRG9uZ0ZhbmcgZVRydXN0IENBMB4XDTE1MDgyNDA3MTgzMVoXDTIxMDgyMjA3MTgzMVowbTEMMAoGA1UEBgwDQ0hOMQswCQYDVQQIDAJITjELMAkGA1UEBwwCQ1MxFTATBgNVBAoMDOW6lOeUqOe9keWFszEVMBMGA1UECwwM5bqU55So572R5YWzMRUwEwYDVQQDDAzlupTnlKjnvZHlhbMwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQI1LwGGthQ8fEpzwLjET4MNLx5WoBkn0bMXpfeNtPbXDN4JW4JALtQlMTcBfBNSa4OtIWc/SjyJmywiXUOPbpko4GhMIGeMAkGA1UdEwQCMAAwHQYDVR0OBBYEFFkPjq435mZA1YLgovCdyNGcMbZWMGUGA1UdIwReMFyAFKDuNiWRcriCGoBLYzwRHuu4qxZqoTKkMDAuMQswCQYDVQQGEwJDTjEOMAwGA1UECgwFTlJDQUMxDzANBgNVBAMMBlJPT1RDQYIQWXVNqo+dgQVDI1T4ZjeGVTALBgNVHQ8EBAMCBLAwDAYIKoEcz1UBg3UFAANJADBGAiEAoBj1e9S8f1siast2CRR/pOgi53XktyWgs58tgXvJxNwCIQCCHQpRHxymM/szlo0hRQNV9k7EP2Q/U+zu6/5C5YI2YQ==";
    // readCertData(1, "WALLGREAT");
            var szCertData = document.getElementById("servercert").value;
    // alert(szCertData);
            
    var pbSrcData = document.getElementById("testInfo").value;
    var szDestData = document.getElementById("lasttestInfo").value;

    var temp = decode64(szDestData);
    szDestData = getReverseStr(temp);
    szDestData = encode64(szDestData);

    var b = verifyData(szCertData, pbSrcData, szDestData, 0);
    if (b == true) {
        alert("验证签名成功!");
    } else {
        alert("验证签名失败!");
        return false;
    }
    return "";
}		    // 获取随即数
function gettheRand(dataLen) {
    // linksskey();
    var b = WGUKey.UKGetRand(dataLen);

    if (b == true) {
        return WGUKey.Data1;
    }
    return "";
}
function symCrypt(dwFlags, szPaws, szData) {
    var b = WGUKey.UKSymCrypt(dwFlags, szPaws, szData);

    return WGUKey.Data1;
}
// 对称加密
function crypt() {
    if (Mes == false) {
        alert("请插入UKEY设备!");
        return false;
    }
    // szPaws = "1111111111111111";
    szPaws = "1234567812345678";
    // gettheRand(r);
    // alert(szPaws);
    var dwFlags = 2;
    var szData = document.getElementById("CryptInfo").value;
    if (szData == "") {
        alert("要加密的数据不能为空!");
        return false;
    }
    szPaws = base64encode(utf16to8(szPaws));
    szData = base64encode(utf16to8(szData));
    // alert(szData);
    if (szPaws != "" && szData != "") {
        var re = symCrypt(dwFlags, szPaws, szData)
        document.getElementById("lastCryptInfo").value = re;
        // alert(re);
        return false;
    } else {
        alert("请输入加密数据!");
        return false;
    }
    alert("加密失败!");
}
function SymDeCrypt(nAlgType, szPaws, szData) {

    var b = WGUKey.UKSymDeCrypt(nAlgType, szPaws, szData);
    // alert(b);
    if (b == true) {
        return WGUKey.Data1;
    }
    alert("解密失败！");
    return "";
}
function deCrypt() {
    if (Mes == false) {
        alert("请插入UKEY设备!");
        return false;
    }
    var nAlgType = 2;
    var szData = "";
    szData = document.getElementById("lastCryptInfo").value;
    if (szData == "") {
        alert("要解密的数据不能为空!");
        return false;
    }
    var szPaws;
    // szPaws = "1111111111111111";
    szPaws = "1234567812345678";
    szPaws = base64encode(utf16to8(szPaws));
    var b = SymDeCrypt(nAlgType, szPaws, szData);
    if (b == "") {
        document.getElementById("lastdeCrypt").value = "";
        return false;
    }
    // alert(b);
    b = utf8to16(base64decode(b));
    // alert(b);
    document.getElementById("lastdeCrypt").value = b;
    // alert(SymDeCrypt(nAlgType,szPaws,szData));
}
function readsigncert() {

    var szCertData = readCertData(1, "WALLGREAT");
    var pbSrcData = document.getElementById("signcert");
    pbSrcData.value = szCertData;
}
function readcryptcert() {

    var szCertData = readCertData(2, "WALLGREAT");
    var pbSrcData = document.getElementById("cryptcert");

    pbSrcData.value = szCertData;
}
// 非对称加密
function asymCrypt(dwFlags, pszContainerName, szCertData, szData, dwArithFlag) {
    var b = WGUKey.UKAsymCrypt(dwFlags, pszContainerName, szCertData, szData, dwArithFlag);
    if (b == true) {
        return WGUKey.Data1;
    }
    return "";
}
function testasymCrypt() {
    var dwArithFlag = 2;
    var dwFlags = 2;
    var pszContainerName = "WALLGREAT";
    var szCertData = readCertData(2, "WALLGREAT");
    var szData = "";
    szData = document.getElementById("asymCryptInfo").value;
    if (szData == "") {
        alert("要加密的数据不能为空!");
        return false;
    }
    szData = base64encode(utf16to8(szData));
    var b = asymCrypt(dwFlags, pszContainerName, szCertData, szData, dwArithFlag);

    document.getElementById("lastasymCryptInfo").value = b;
}
function asymDeCrypt(dwFlag, pszContainerName, szData, dwArithFlag) {
    var b = WGUKey.UKAsymDeCrypt(dwFlag, pszContainerName, szData, dwArithFlag);
    alert(b);
    if (b == true) {
        var re = utf8to16(base64decode(WGUKey.Data1));
        // alert(re);
        return re;
        // return WGUKey.Data1;
    }
    alert('解密失败!');
    return "";
}
function testasymDeCrypt() {
    var dwFlag = 2;
    var pszContainerName = "WALLGREAT";
    var szData1 = "";
    szData1 = document.getElementById("lastasymCryptInfo").value;
    if (szData1 == "") {
        alert("要解密的数据不能为空!");
        return false;
    }
    dwArithFlag = 2;
    // alert(szData1);
    var c = asymDeCrypt(dwFlag, pszContainerName, szData1, dwArithFlag);
    // alert(b);
    if (c == "") {
        document.getElementById("lasymdeCrypt").value = "";
        return false;
    }
    // c =base64decode(c);
    // alert(c);
    document.getElementById("lasymdeCrypt").value = c;
    return true;
}


function asym() {
	var pszContainerName = "WALLGREAT";// 1.容器名="WALLGREAT"
	var certtype=4;//
	
	var encsym=document.getElementById("txtsym").value;  // 加密后的会话密钥
	var data = document.getElementById("txtdata").value; // 待加密的数据
	var txtencdata = document.getElementById("txtencdata");
	
	var temp = base64decode(encsym);
	encsym = getReverseStr(temp);
	encsym = base64encode(encsym);
	
	var res = WGUKey.UKImportSessionKeyAndEncrypt(pszContainerName, certtype, encsym, data);
	
	if (res == true) {
	    txtencdata.value = WGUKey.Data1;
	}
	else {
	     txtencdata.value= "false";
	}



}

var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
// 将Ansi编码的字符串进行Base64编码
function encode64(input) {
	var output = "";
	var chr1, chr2, chr3 = "";
	var enc1, enc2, enc3, enc4 = "";
	var i = 0;
	
	do {
	     chr1 = input.charCodeAt(i++);
		chr2 = input.charCodeAt(i++);
		chr3 = input.charCodeAt(i++);
		
		enc1 = chr1 >> 2;
		enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		enc4 = chr3 & 63;
		
		if (isNaN(chr2)) {
		enc3 = enc4 = 64;
		} else if (isNaN(chr3)) {
		enc4 = 64;
		}
		
		output = output + 
		keyStr.charAt(enc1) + 
		keyStr.charAt(enc2) + 
		keyStr.charAt(enc3) + 
		keyStr.charAt(enc4);
		chr1 = chr2 = chr3 = "";
		enc1 = enc2 = enc3 = enc4 = "";
	} while (i < input.length);
	
	return output;
}

function decode64(input) {
	var output = "";
	var chr1, chr2, chr3 = "";
	var enc1, enc2, enc3, enc4 = "";
	var i = 0;
	
	if(input.length%4!=0)
	{
	        return "";
	}
	var base64test = /[^A-Za-z0-9\+\/\=]/g;
	if (base64test.exec(input))
	{
	        return "";
	}
	
	do {
		enc1 = keyStr.indexOf(input.charAt(i++));
		enc2 = keyStr.indexOf(input.charAt(i++));
		enc3 = keyStr.indexOf(input.charAt(i++));
		enc4 = keyStr.indexOf(input.charAt(i++));
		
		chr1 = (enc1 << 2) | (enc2 >> 4);
		chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
		chr3 = ((enc3 & 3) << 6) | enc4;
		     
		output = output + String.fromCharCode(chr1);
		
		if (enc3 != 64) {
		            output+=String.fromCharCode(chr2);
		}
		if (enc4 != 64) {
		            output+=String.fromCharCode(chr3);
		}
		
		chr1 = chr2 = chr3 = "";
		enc1 = enc2 = enc3 = enc4 = "";
	
	} while (i < input.length);
	
	return output;
}


function asydm() {
	var pszContainerName = "WALLGREAT";// 1.容器名="WALLGREAT"
	var certtype=4;//
	
	var encsym=document.getElementById("txtsym").value;  // 加密后的会话密钥
	var data = document.getElementById("txtdata").value; // 待解密的数据
	var txtencdata = document.getElementById("txtencdata");
	
	var temp = decode64(encsym);
	encsym = getReverseStr(temp);
	encsym = encode64(encsym);
	
	var res = WGUKey.UKImportSessionKeyAndDecryptEx(pszContainerName, certtype, encsym, data);
	
	if (res == true) {
	// txtencdata.value = res;
	    var decData = decode64(WGUKey.Data1);
	    // alert(decData);
	    txtencdata.value = decData;
	}
	else {
	     txtencdata.value= "false";
	}



}

function recryptcert() {
   
    var res = WGUKey.UKGetUkeyCertInfo(2,1, "WALLGREAT");

    var txtrecert = document.getElementById("txtrecert");
    if (res = true) {
        txtrecert.value = WGUKey.Data1;
    }
    else {
        txtrecert.value = false;
    }
}

function resigncert() {
    var res = WGUKey.UKGetUkeyCertInfo(1,1, "WALLGREAT");

    var txtrecert = document.getElementById("txtresigncert");
    if (res = true) {
        txtrecert.value = WGUKey.Data1;
    }
    else {
        txtrecert.value = false;
    }
}

function saveFile(){
	var fileContent = document.getElementById("txtFileSave").value;
	if (fileContent != '') {
		var isWrite = WGUKey.UKWriteFile(fileContent);
		alert(isWrite ? '写入成功' : '写入失败');
	}
}
function readFile(){
	var fileContent = WGUKey.UKReadFile();
    var txtrecert = document.getElementById("txtFileRead");
    if (fileContent = true) {
    txtrecert.value = WGUKey.Data1;
    }
    else {
        txtrecert.value = false;
    }
// document.getElementById("txtFileRead").value = fileContent;
}
