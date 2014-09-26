/**
 * 角色权限管理
 */

$(document).ready(function() {

	$("#bindPermission_imgId").click(function() {
		bindPermissionUpdate($("#unbindPermission_selectId").val());
	});

	$("#unbindPermission_imgId").click(function() {
		unbindPermissionUpdate($("#bindPermission_selectId").val());
	});

});

function bindPermissionUpdate(_permissionIds) {
	if (_permissionIds == null || _permissionIds.length == 0) {
		alert("请选择权限！");
		return;
	}

	$.ajax({
		url : basePath + "wanmei/system/bind_permissions",
		data : {
			'roleId' : pageRoleId,
			'permissionIds' : _permissionIds.toString()
		},
		dataType : 'html',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response > 0) {
				window.location.href = basePath + "wanmei/system/show_permission_detail_page?roleId=" + pageRoleId + "&gameId=" + pageGameId;
			} else {
				alert("绑定权限失败！");
				window.location.href = basePath + "wanmei/system/show_permission_detail_page?roleId=" + pageRoleId + "&gameId=" + pageGameId;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alertInfo(XMLHttpRequest, "绑定权限失败！");
			return false;
		}
	});
}

function unbindPermissionUpdate(_permissionIds) {
	if (_permissionIds == null || _permissionIds.length == 0) {
		alert("请选择权限！");
		return;
	}

	$.ajax({
		url : basePath + "wanmei/system/unbind_permissions",
		data : {
			'roleId' : pageRoleId,
			'permissionIds' : _permissionIds.toString()
		},
		dataType : 'html',
		type : 'POST',
		async : true,
		success : function(response) {
			if (response > 0) {
				window.location.href = basePath + "wanmei/system/show_permission_detail_page?roleId=" + pageRoleId + "&gameId=" + pageGameId;
			} else {
				alert("解除权限失败！");
				window.location.href = basePath + "wanmei/system/show_permission_detail_page?roleId=" + pageRoleId + "&gameId=" + pageGameId;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alertInfo(XMLHttpRequest, "解除权限失败！");
			return false;
		}
	});
}
