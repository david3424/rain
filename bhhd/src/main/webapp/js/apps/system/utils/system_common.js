/**
 * 首页菜单
 */
var stayTime = 1000;

/**
 * 显示操作成功提示
 */
function showSuccessTip(_info) {
	$("#success_content").html(_info);
	$("#success_tip").show();
}

/**
 * 显示验证错误提示
 */
function showValidateTip(_info) {
	$("#validate_content").html(_info);
	$("#validate_tip").show();
}

/**
 * 显示操作成功提示(inform)
 */
function showSuccessTip_inform(_info) {
	$("#success_content_inform").html(_info);
	$("#success_tip_inform").show();
}

/**
 * 显示验证错误提示(inform)
 */
function showValidateTip_inform(_info) {
	$("#validate_content_inform").html(_info);
	$("#validate_tip_inform").show();
}

/**
 * Ajax请求错误处理_alert
 */
function alertInfo(XMLHttpRequest, _msg) {
	if (XMLHttpRequest.getResponseHeader("__forbidden")){
		alert(_msg + "您目前权限不足，如需要开通权限，请联系管理员！");
	} else {
		alert(_msg);
	}
}

/**
 * Ajax请求错误处理_ValidateTip
 */
function validateTipInfo(XMLHttpRequest, _msg) {
	if (XMLHttpRequest.getResponseHeader("__forbidden")){
		showValidateTip(_msg + "您目前权限不足，如需要开通权限，请联系管理员！");
	} else {
		showValidateTip(_msg);
	}
}

/**
 * Ajax请求错误处理_ValidateTip
 */
function validateTipInfoInform(XMLHttpRequest, _msg) {
	if (XMLHttpRequest.getResponseHeader("__forbidden")){
		showValidateTip_inform(_msg + "您目前权限不足，如需要开通权限，请联系管理员！");
	} else {
		showValidateTip_inform(_msg);
	}
}

/**
 * 显示滑动条
 */
function setScrollLeft() {   
	window.scroll(0, 99999);
	return false;
}  

/**
 * 用原型法添加字符串类型的replaceAll方法
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, 'gm'), s2);
}

/**
 * 验证自然数
 */
function isNaturalNum(str){
	var reg = /^\d+$/;
	return reg.test(str);
}
