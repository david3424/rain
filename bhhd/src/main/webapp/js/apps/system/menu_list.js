/**
 * 菜单管理
 */
var methodMode, permissionId;
$(document).ready(function() {
	getMenuTables();

	$("#srhGameId").change(function() {
		getMenuTables();
	});

	$("#srhMenuTypeId").change(function() {
		getMenuTables();
	});

    $("#searchBtn").click(function() {
    	getMenuTables();
    });

	$("#add_menu_id").click(function() {
		methodMode = 1;
		$("#dialog_form").modal({
			backdrop : false
	    });
	});
});

function getMenuTables() {
	var url = basePath + "wanmei/system/get_menu_table";
	var columns = [{"sTitle": "所属商品", "mData": "gameName"},
	       	       {"sTitle": "菜单类型", "mData": "menuTypeName"},
	    	       {"sTitle": "菜单名称", "mData": "resourceName"},
	    	       {"sTitle": "菜单URL", "mData": "resourceUrl"},
	    	       {"sTitle": "操作", "mData": "permissionId", "mRender": function(data, type, row) {return operateButton(data, type, row);}}];
	var params =[{name: 'gameId',  value: $("#srhGameId").val()},
    			 {name: 'menuTypeId', value: $("#srhMenuTypeId").val()},
    			 {name: 'resourceName', value: $.trim($("#srhResourceName").val())},
    		     {name: 'resourceUrl', value: $.trim($("#srhResourceUrl").val())}];
	commonDataTables("menuTable", url, columns, params);
}

function operateButton(cellvalue, options, rowObject) {
	var _permissionId = rowObject['permissionId'];
	var _resourceName = "'" + rowObject['resourceName'] + "'";
	var _resourceUrl = "'" + rowObject['resourceUrl'] + "'";
	var _gameId = rowObject['gameId'];
	var _menuTypeId = rowObject['menuTypeId'];
	var _description = rowObject['description'];
	if (_description != null) _description = "'" + _description + "'";
	
	var editBtn = "<button class='btn btn-link btn-mini' onclick=\"editMenu("
    	+ _permissionId + ","
    	+ _resourceName + ","
    	+ _resourceUrl + ","
    	+ _gameId + ","
    	+ _menuTypeId + ","
    	+ _description
    	+ ")\">编辑</button>";

    var deleteBtn = "<button class='btn btn-link btn-mini' onclick=\"deleteMenu("
    	+ _permissionId
    	+ ")\">删除</button>";

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
	getMenuTables();
}

function closeDialogInform() {
	$("#dialog_inform").modal("hide");
	$("#success_tip_inform").val( "" ).hide();
	getMenuTables();
}

function clearDialogInput() {
	$("#resource_name").val("");
	$("#resource_url").val("");
	$("#game_id").val("");
	$("#menu_type_id").val("");
	$("#describe").val("");
}

function changeDialog() {
	$("#validate_tip").val( "" ).hide();
	$(".control-group").removeClass( "error" );
	
	if (methodMode == 1) {
		addMenuProcess($.trim($("#resource_name").val()),
    			   $.trim($("#resource_url").val()),
    			   $.trim($("#game_id").val()),
    			   $.trim($("#menu_type_id").val()),
    			   $.trim($("#describe").val()));
	} else if (methodMode == 2) {
		editMenuProcess(permissionId,
				   $.trim($("#resource_name").val()),
    			   $.trim($("#resource_url").val()),
    			   $.trim($("#game_id").val()),
    			   $.trim($("#menu_type_id").val()),
    			   $.trim($("#describe").val()));
	} else {
		alert("操作菜单信息失败！");
	}
}

function addMenuProcess(_resourceName, _resourceUrl, _gameId, _menuTypeId, _description) {
	if (_resourceName == null || _resourceName.length == 0) {
		showValidateTip("“菜单名称 ”不能为空 ！");
		$("#resource_name_main").addClass("error");
		$("#resource_name").focus();
		return false;
	}
	if (_resourceUrl == null || _resourceUrl.length == 0) {
		showValidateTip("“菜单URL ”不能为空 ！");
		$("#resource_url_main").addClass("error");
		$("#resource_url").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "wanmei/system/add_menu",
    	data : {
    		'resourceName' : _resourceName,
			'resourceUrl' : _resourceUrl,
			'gameId' : _gameId,
			'menuTypeId' : _menuTypeId,
			'description' : _description
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("添加菜单信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#resource_url_main").addClass("error");
		        $("#resource_url").focus();
		        showValidateTip("已经存在菜单URL为“" + _resourceUrl + "”的菜单！");
    		} else {
    			showValidateTip("添加菜单信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "添加菜单信息失败！");
			return false;
    	}
    });
}

function editMenu(_permissionId, _resourceName, _resourceUrl, _gameId, _menuTypeId, _description) {
	methodMode = 2;
	$("#resource_name").val(_resourceName);
	$("#resource_url").val(_resourceUrl);
	$("#game_id").val(_gameId);
	$("#menu_type_id").val(_menuTypeId);
	$("#describe").val(_description);
	permissionId = _permissionId;

	$("#dialog_form").modal({
		backdrop : false
	});
}

function editMenuProcess(_permissionId, _resourceName, _resourceUrl, _gameId, _menuTypeId, _description) {
	if (_resourceName == null || _resourceName.length == 0) {
		showValidateTip("“菜单名称 ”不能为空 ！");
		$("#resource_name_main").addClass("error");
		$("#resource_name").focus();
		return false;
	}
	if (_resourceUrl == null || _resourceUrl.length == 0) {
		showValidateTip("“菜单URL ”不能为空 ！");
		$("#resource_url_main").addClass("error");
		$("#resource_url").focus();
		return false;
	}

    $.ajax({
    	url : basePath + "wanmei/system/edit_menu",
    	data : {
    		'permissionId' : _permissionId,
    		'resourceName' : _resourceName,
			'resourceUrl' : _resourceUrl,
			'gameId' : _gameId,
			'menuTypeId' : _menuTypeId,
			'description' : _description
    	},
    	dataType : 'html',
    	type : 'POST',
    	async : true,
    	success : function(response) {
    		if (response == 1) {
    			showSuccessTip("编辑菜单信息成功！");
    			setTimeout(function() {closeDialogForm();}, stayTime);
    		} else if (response == 2) {
    			$("#resource_url_main").addClass("error");
		        $("#resource_url").focus();
		       showValidateTip("已经存在菜单URL为“" + _resourceUrl + "”的菜单！");
    		} else {
    			showValidateTip("编辑菜单信息失败！");
    		}
    	},
    	error : function(XMLHttpRequest, textStatus, errorThrown) {
    		validateTipInfo(XMLHttpRequest, "编辑菜单信息失败！");
			return false;
    	}
    });
}

function deleteMenu(_permissionId) {
	permissionId = _permissionId;
	$("#informInfoId").html("你确定要进行<strong style='color:red'>删除</strong>菜单的操作吗？");

	$("#dialog_inform").modal({
		backdrop : false
	});
}

function changeDialogInform() {
	deleteMenuProcess(permissionId);
}

function deleteMenuProcess(_permissionId) {
	$.ajax({
		url : basePath + "wanmei/system/delete_menu",
		data : {
			'permissionId' : _permissionId
		},
		dataType : 'json',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response == 1) {
				showSuccessTip_inform("删除菜单成功！");
				setTimeout(function() {closeDialogInform();}, stayTime);

			} else if (response == 0) {
				showSuccessTip_inform("该菜单已经被删除！");
				setTimeout(function() {closeDialogInform();}, stayTime);
			} else {
				showValidateTip_inform("删除菜单失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			validateTipInfoInform(XMLHttpRequest, "删除菜单失败！");
			return false;
		}
	});
}
