/**
 * 角色权限列表
 */
var methodMode, gameId;
$(document).ready(function() {
	getRoleGameTables();

});

function getRoleGameTables() {
	var url = basePath + "pppppp/system/get_role_game_table";
	var columns = [{"sTitle": "商品名称", "mData": "gameName", "sWidth": "25%"},
	       	       {"sTitle": "商品缩写", "mData": "gameAb", "sWidth": "25%"},
	    	       {"sTitle": "操作", "mData": "roleId", "sWidth": "50%", "mRender": function(data, type, row) {return operateButton(data, type, row);}}];
	var params =[{name: 'roleId', value: pageRoleId}];
	commonDataTables("roleGameTable", url, columns, params);
}

function operateButton(cellvalue, options, rowObject) {
	var _roleId = rowObject['roleId'];
	var _gameId = rowObject['gameId'];

	var bindBtn = "";
	var permitBtn = "";

	if (_roleId == -1) {
		bindBtn = "<button class='btn btn-warning btn-mini' onclick=\"bindGame("
				+ pageRoleId + ","
    			+ _gameId
    			+ ")\">绑定类别</button>";
	} else {
		bindBtn = "<button class='btn btn-success btn-mini' onclick=\"unbindGame("
				+ pageRoleId + ","
    			+ _gameId
    			+ ")\">解绑类别</button>";

    	permitBtn = "<button class='btn btn-link btn-mini' onclick=\"permitReSource("
    	+ pageRoleId + ","
    	+ _gameId
    	+ ")\">资源授权</button>";
	}

    return bindBtn + "&ensp;&ensp;" + permitBtn;
}

function permitReSource(_roleId, _gameId) {
	window.location.href = basePath + "pppppp/system/show_permission_detail_page?roleId=" + _roleId + "&gameId=" + _gameId;
}

function bindGame(_roleId, _gameId) {
	methodMode = 1;
	gameId = _gameId;
	$("#informInfoId").html("你确定要进行此角色<strong style='color:red'>绑定</strong>商品的操作吗？");

	$("#dialog_inform").modal({
		backdrop : false
	});
}

function unbindGame(_roleId, _gameId) {
	methodMode = 2;
	gameId = _gameId;
	$("#informInfoId").html("你确定要进行此角色<strong style='color:red'>解绑</strong>商品的操作吗？");

	$("#dialog_inform").modal({
		backdrop : false
	});
}

function changeDialogInform() {
	if (methodMode == 1) {
		bindProcess(gameId);
	} else if (methodMode == 2) {
		unbindProcess(gameId);
	} else {
		alert("操作角色商品设置失败！");
	}
}

function bindProcess(_gameId) {
	$.ajax({
		url : basePath + "pppppp/system/bind_role_game",
		data : {
			'roleId' : pageRoleId,
			'gameId' : _gameId
		},
		dataType : 'json',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response == 1) {
				showSuccessTip_inform("角色绑定成功！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			} else if (response == 2) {
				showSuccessTip_inform("该角色已经绑定！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			}else {
				showValidateTip_inform("角色绑定失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			validateTipInfoInform(XMLHttpRequest, "角色绑定失败！");
			return false;
		}
	});
}

function unbindProcess(_gameId) {
	$.ajax({
		url : basePath + "pppppp/system/unbind_role_game",
		data : {
			'roleId' : pageRoleId,
			'gameId' : _gameId
		},
		dataType : 'json',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response == 1) {
				showSuccessTip_inform("角色解绑商品成功！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			} else if (response == 2) {
				showSuccessTip_inform("该角色已经解绑商品！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			}else {
				showValidateTip_inform("角色解绑商品失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			validateTipInfoInform(XMLHttpRequest, "角色解绑商品失败！");
			return false;
		}
	});
}

function closeDialogInform() {
	$("#dialog_inform").modal("hide");
	$("#success_tip_inform").val( "" ).hide();
	getRoleGameTables();
}
