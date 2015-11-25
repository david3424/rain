/**
 * 用户角色列表
 */
var methodMode, roleId;
$(document).ready(function() {
	getRoleTables();

	$("#add_user_role_id").click(function() {
		methodMode = 1;
		$("#dialog_form").modal({
			backdrop : false
	    });
	});
});

function getRoleTables() {
	var url = basePath + "pppppp/system/get_role_table";
	var columns = [
//				   {"sTitle": "角色名称", "mData": "roleName", "mRender": function(data, type, row) {return linkedFat(data, type, row);}},
				   {"sTitle": "角色名称", "mData": "roleName"},
	       	       {"sTitle": "角色代号", "mData": "roleCode"},
	    	       {"sTitle": "角色描述", "mData": "roleDescribe"},
	    	       {"sTitle": "操作", "mData": "roleId", "mRender": function(data, type, row) {return operateButton(data, type, row);}}];
	var params =[];
	commonDataTables("roleTable", url, columns, params);
}

function linkedFat(cellvalue, options, rowObject) {
	var _roleId = rowObject['roleId'];
	
	var linkedBtn = "<button class='btn btn-link btn-mini' onclick=\"showPermissionDetail("
    	+ _roleId
    	+ ")\">" + rowObject['roleName'] + "</button>";
    return linkedBtn;
}

function showPermissionDetail(_roleId) {
	window.location.href = basePath + "pppppp/system/show_permission_detail_page?roleId=" + _roleId;
}

function operateButton(cellvalue, options, rowObject) {
	var _roleId = rowObject['roleId'];
	var _roleName = "'" + rowObject['roleName'] + "'";
	var _roleCode = "'" + rowObject['roleCode'] + "'";
	var _description = rowObject['roleDescribe'];
    if (_description != null) _description = "'" + _description + "'";

	var editBtn = "<button class='btn btn-link btn-mini' onclick=\"editRole("
    	+ _roleId + ","
    	+ _roleName + ","
    	+ _roleCode + ","
    	+ _description
    	+ ")\">编辑</button>";

    var deleteBtn = "<button class='btn btn-link btn-mini' onclick=\"deleteRole("
    	+ _roleId
    	+ ")\">删除</button>";

    var setBtn = "<button class='btn btn-link btn-mini' onclick=\"setRoleGame("
    	+ _roleId
    	+ ")\">绑定类别</button>";

    return editBtn + "&ensp;" + deleteBtn + "&ensp;" + setBtn;
}

function setRoleGame(_roleId) {
	window.location.href = basePath + "pppppp/system/show_role_game_page?roleId=" + _roleId;
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
	getRoleTables();
}

function closeDialogInform() {
	$("#dialog_inform").modal("hide");
	$("#success_tip_inform").val( "" ).hide();
	getRoleTables();
}

function clearDialogInput() {
    $("#role_name").val("");
    $("#role_code").val("");
    $("#role_describe").val("");
}

function changeDialog() {
	$("#validate_tip").val( "" ).hide();
	$(".control-group").removeClass( "error" );
	
	if (methodMode == 1) {
		addUserRoleProcess(
					$.trim($("#role_name").val()),
				    $.trim($("#role_code").val()),
				    $.trim($("#role_describe").val()));
	} else if (methodMode == 2) {
		editUserRoleProcess(
					roleId,
				    $.trim($("#role_name").val()),
				    $.trim($("#role_code").val()),
				    $.trim($("#role_describe").val()));
	} else {
		alert("操作用户信息失败！");
	}
}

function addUserRoleProcess(_roleName, _roleCode, _roleDescribe) {
	if (_roleName == null || _roleName.length == 0) {
		showValidateTip("“角色名称”不能为空 ！");
		$("#role_name_main").addClass("error");
		$("#role_name").focus();
		return false;
	}

	if (_roleCode == null || _roleCode.length == 0) {
		showValidateTip("“角色代号”不能为空 ！");
		$("#role_code_main").addClass("error");
		$("#role_code").focus();
		return false;
	}
	
	if (_roleCode.substr(0,5)!="ROLE_") {
		showValidateTip("“角色代号”必须以 ROLE_开头！");
		$("#role_code_main").addClass("error");
		$("#role_code").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "pppppp/system/add_user_role",
    	data : {
    		'roleName' : _roleName,
    		'roleCode' : _roleCode,
    		'roleDescribe' : _roleDescribe
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("添加角色信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#role_name_main").addClass("error");
		        $("#role_name").focus();
		        showValidateTip("已经存在名称为“" + _roleName + "”的角色！");
    		} else if (response == 3) {
    			$("#role_code_main").addClass("error");
		        $("#role_code").focus();
		        showValidateTip("已经存在代码为“" + _roleCode + "”的角色！");
    		} else {
    			showValidateTip("添加角色信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "添加角色信息失败！");
			return false;
    	}
    });
}

function editRole(_roleId, _roleName, _roleCode,_roleDescribe) {
	methodMode = 2;
	$("#role_name").val(_roleName);
	$("#role_code").val(_roleCode);
	$("#role_describe").val(_roleDescribe);
	roleId = _roleId;

	$("#dialog_form").modal({
		backdrop : false
	});
}

function editUserRoleProcess(_roleId, _roleName, _roleCode, _roleDescribe) {
	if (_roleName == null || _roleName.length == 0) {
		showValidateTip("“角色名称”不能为空 ！");
		$("#role_name_main").addClass("error");
		$("#role_name").focus();
		return false;
	}

	if (_roleCode == null || _roleCode.length == 0) {
		showValidateTip("“角色代号”不能为空 ！");
		$("#role_code_main").addClass("error");
		$("#role_code").focus();
		return false;
	}
	
	if (_roleCode.substr(0,5)!="ROLE_") {
		showValidateTip("“角色代号”必须以 ROLE_开头！");
		$("#role_code_main").addClass("error");
		$("#role_code").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "pppppp/system/edit_user_role",
    	data : {
    		'roleId' : _roleId,
    		'roleName' : _roleName,
    		'roleCode' : _roleCode,
    		'roleDescribe' : _roleDescribe
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("编辑角色信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#role_name_main").addClass("error");
		        $("#role_name").focus();
		       showValidateTip("已经存在名称为“" + _roleName + "”的角色！");
    		} else if (response == 3) {
    			$("#role_code_main").addClass("error");
		        $("#role_code").focus();
		       showValidateTip("已经存在代码为“" + _roleCode + "”的角色！");
    		} else {
    			showValidateTip("编辑角色信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "编辑角色信息失败！");
			return false;
    	}
    });
}

function deleteRole(_roleId) {
	roleId = _roleId;
	$("#informInfoId").html("你确定要进行<strong style='color:red'>删除</strong>角色的操作吗？");

	$("#dialog_inform").modal({
		backdrop : false
	});
}

function changeDialogInform() {
	deleteUserRoleProcess(roleId);
}

function deleteUserRoleProcess(_roleId) {
	$.ajax({
		url : basePath + "pppppp/system/delete_user_role",
		data : {
			'roleId' : _roleId
		},
		dataType : 'json',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response == 1) {
				showSuccessTip_inform("删除角色成功！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			} else if (response == 2) {
				showValidateTip_inform("该角色下存在用户，不能被删除！");
			}else {
				showValidateTip_inform("删除角色失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			validateTipInfoInform(XMLHttpRequest, "删除角色失败！");
			return false;
		}
	});
}
