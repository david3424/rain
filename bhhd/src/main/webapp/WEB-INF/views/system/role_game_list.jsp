<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页游统计平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/role_game_list.js"></script>
<script type="text/javascript">
    var pageRoleId = "${roleDetail.roleId}";
</script>
</head>
<body>
<%@ include file="../commons/header.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div id="menu" class="span2">
				<%@ include file="../commons/menu.jsp"%>
			</div>
			<div class="span10">
				<div id="content" class="container-fluid">

					<ul class="breadcrumb">
						<li><a href="<%=basePath%>wanmei/system/show_role_page">用户角色</a><span class="divider">/</span></li>
						<li class="active">用户游戏绑定</li>
					</ul>

					<!-- 查询box -->
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-edit"></i><span class="break"></span>角色信息
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a>
							</div>
						</div>
						<!-- box-header end -->
						<div class="box-content">
							<p>角色名称：<c:out value="${roleDetail.roleName}"></c:out></p>
    						<p>职责描述：<c:out value="${roleDetail.roleDescribe}"></c:out></p>
							<!-- box-content end -->
						</div>
					</div>
					<!-- box end -->

		            <!-- 表格box -->
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-table"></i><span class="break"></span><span>角色游戏绑定列表</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
							<table id="roleGameTable"  class="table table-bordered table-striped dataTable table-hover"></table>
						</div><!-- box-content end -->
					</div><!-- box end -->

				</div>
				<!-- content end -->
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>

<div id="dialog_inform" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
<div class="modal-header"><h4>操作确认</h4></div>
<div class="modal-body">
	<div id="validate_tip_inform" class="alert alert-error div-display">
    	<span id="validate_content_inform"></span>
    </div>
    <div id="success_tip_inform" class="alert alert-success div-display">
    	<span id="success_content_inform"></span>
    </div>
    <p><i class="icon-warning-sign"></i><span id="informInfoId">你确定要进行此操作吗？</span></p>
</div>
<div class="modal-footer">
    <button type="button" class="btn" onclick="closeDialogInform();">取消</button>
    <button type="button" class="btn btn-primary" onclick="changeDialogInform();">提交</button>
</div>
</div>

</body>
</html>
