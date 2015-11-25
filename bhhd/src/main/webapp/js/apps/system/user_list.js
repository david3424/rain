/**
 * 用户管理列表
 */
var methodMode, userId;
$(document).ready(function() {
	getUserTables();

	$("#user_role_id").change(function() {
		getUserTables();
	});

	$("#user_status_id").change(function() {
		getUserTables();
	});

    $("#searchBtn").click(function() {
    	getUserTables();
    });

	$("#add_user_id").click(function() {
		methodMode = 1;
		$("#dialog_form").modal({
			backdrop : false
	    });
	});
});

function getUserTables() {
	var url = basePath + "pppppp/system/get_user_table";
	var columns = [{"sTitle": "登陆帐号", "mData": "account"},
	       	       {"sTitle": "用户姓名", "mData": "userName"},
	    	       {"sTitle": "角色名称", "mData": "roleName"},
	    	       {"sTitle": "用户状态", "mData": "status", "mRender": function(data, type, row) {return userStatusFat(data, type, row);}},
	    	       {"sTitle": "联运商", "mData": "agent"},
	    	       {"sTitle": "操作", "mData": "userId", "mRender": function(data, type, row) {return operateButton(data, type, row);}}];
	var params =[{name: 'roleId',  value: $("#user_role_id").val()},
    			 {name: 'userStatus', value: $("#user_status_id").val()},
    		     {name: 'userName', value: $.trim($("#account_name").val())},
    		     {name: 'account', value: $.trim($("#user_account").val())}];
	commonDataTables("userTable", url, columns, params);
}

function userStatusFat(cellvalue, options, rowObject) {
	var userStatus = "";
	if (cellvalue == 100) {
		userStatus = "<span class='badge badge-important'>无效帐号</span>";
	} else if (cellvalue == 200) {
		userStatus = "<span class='badge badge-success'>有效帐号</span>";
	}
	return userStatus;
}

function operateButton(cellvalue, options, rowObject) {
	var editBtn = "<button class='btn btn-link btn-mini' onclick=\"editUser("
        + rowObject['userId'] + ", '" + rowObject['account'] + "', '"  + rowObject['userName'] + "', "
        + rowObject['roleId'] + ", " + rowObject['status'] + ", '" + rowObject['agent'] + "')\">编辑</button>";
    var deleteBtn = "<button class='btn btn-link btn-mini' onclick=\"deleteUser(" + rowObject['userId'] + ")\">删除</button>";

    return editBtn + "&ensp;" + deleteBtn;
}

function closeDialog() {
	$("#dialog_form").modal("hide");
	$("#validate_tip").val( "" ).hide();
	$(".control-group").removeClass( "error" );
	clearDialogInput();
}

function closeDialogForm() {
	$("#dialog_form").modal("hide");
	$("#success_tip").val( "" ).hide();
	$(".control-group").removeClass( "error" );
	clearDialogInput();
	getUserTables();
}

function closeDialogInform() {
	$("#dialog_inform").modal("hide");
	$("#success_tip_inform").val( "" ).hide();
	getUserTables();
}

function clearDialogInput() {
	$("#account").val("");
	$("#user_name").val("");
	$("#user_pwd").val("");
	$("#role_id").val("");
	$("#user_status").val(200);
	$("#agent").val("");
}

function changeDialog() {
	$("#validate_tip").val( "" ).hide();
	$(".control-group").removeClass( "error" );
	
	if (methodMode == 1) {
		addUserProcess($.trim($("#account").val()),
    			   $.trim($("#user_name").val()),
    			   $.trim($("#user_pwd").val()),
    			   $.trim($("#role_id").val()),
    			   $.trim($("#user_status").val()),
    			   $.trim($("#agent").val()));
	} else if (methodMode == 2) {
		editUserProcess(userId,
				   $.trim($("#account").val()),
    			   $.trim($("#user_name").val()),
    			   $.trim($("#user_pwd").val()),
    			   $.trim($("#role_id").val()),
    			   $.trim($("#user_status").val()),
    			   $.trim($("#agent").val()));
	} else {
		alert("操作用户信息失败！");
	}
}

function addUserProcess(_account, _userName, _userPwd, _roleId, _userStatus, _agent) {
	if (_account == null || _account.length == 0) {
		showValidateTip("“登陆帐号 ”不能为空 ！");
		$("#account_main").addClass("error");
		$("#account").focus();
		return false;
	}
	if (_userName == null || _userName.length == 0) {
		showValidateTip("“用户姓名 ”不能为空 ！");
		$("#user_name_main").addClass("error");
		$("#user_name").focus();
		return false;
	}
	if (_userPwd == null || _userPwd.length == 0) {
		showValidateTip("“登陆密码 ”不能为空 ！");
		$("#user_pwd_main").addClass("error");
		$("#user_pwd").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "pppppp/system/add_user",
    	data : {
    		'account' : _account,
			'userName' : _userName,
			'userPwd' : _userPwd,
			'roleId' : _roleId,
			'userStatus' : _userStatus,
			'agent' : _agent
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("添加用户信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#account_main").addClass("error");
		        $("#account").focus();
		        showValidateTip("已经存在登陆帐号为“" + _account + "”的用户！");
    		} else {
    			showValidateTip("添加用户信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "添加用户信息失败！");
			return false;
    	}
    });
}

function editUser(_userId, _account, _userName, _roleId, _userStatus, _agent) {
	methodMode = 2;
	$("#account").val(_account);
	$("#user_name").val(_userName);
	$("#role_id").val(_roleId);
	$("#user_status").val(_userStatus);
	$("#agent").val(_agent);
	userId = _userId;

	$("#dialog_form").modal({
		backdrop : false
	});
}

function editUserProcess(_userId, _account, _userName, _userPwd, _roleId, _userStatus, _agent) {
	if (_account == null || _account.length == 0) {
		showValidateTip("“登陆帐号 ”不能为空 ！");
		$("#account_main").addClass("error");
		$("#account").focus();
		return false;
	}
	if (_userName == null || _userName.length == 0) {
		showValidateTip("“用户姓名 ”不能为空 ！");
		$("#user_name_main").addClass("error");
		$("#user_name").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "pppppp/system/edit_user",
    	data : {
    		'userId' : _userId,
    		'account' : _account,
			'userName' : _userName,
			'userPwd' : _userPwd,
			'roleId' : _roleId,
			'userStatus' : _userStatus,
			'agent' : _agent
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("编辑用户信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#account_main").addClass("error");
		        $("#account").focus();
		       showValidateTip("已经存在登陆帐号为“" + _account + "”的用户！");
    		} else {
    			showValidateTip("编辑用户信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "编辑用户信息失败！");
			return false;
    	}
    });
}

function deleteUser(_userId) {
	userId = _userId;
	$("#informInfoId").html("你确定要进行<strong style='color:red'>删除</strong>用户的操作吗？");

	$("#dialog_inform").modal({
		backdrop : false
	});
}

function changeDialogInform() {
	deleteUserProcess(userId);
}

function deleteUserProcess(_userId) {
	$.ajax({
		url : basePath + "pppppp/system/delete_user",
		data : {
			'userId' : _userId
		},
		dataType : 'json',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response == 1) {
				showSuccessTip_inform("删除用户成功！");
				setTimeout(function() {closeDialogInform();}, stayTime);

			} else if (response == 0) {
				showSuccessTip_inform("该用户已经被删除！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			} else {
				showValidateTip_inform("删除用户失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			validateTipInfoInform(XMLHttpRequest, "删除用户失败！");
			return false;
		}
	});
}
