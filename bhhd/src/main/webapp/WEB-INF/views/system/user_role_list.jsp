<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/user_role_list.js"></script>
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

					<!-- 查询box -->
					<div>
						<p><button id="add_user_role_id" class="btn btn-primary">新建</button></p>
					</div>
					<!-- box end -->

		            <!-- 表格box -->
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-table"></i><span class="break"></span><span>角色列表</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
							<table id="roleTable"  class="table table-bordered table-striped dataTable table-hover"></table>
							<!-- 
							<table class="table table-striped">
								<thead>
									<tr>
										<th>#</th>
										<th width="15%">角色名称</th>
										<th width="15%">角色代号</th>
										<th>角色描述</th>
										<th width="20%">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${roleDetailList}" var="roleDetail" varStatus="status">
									<tr>
										<td><c:out value="${status.count}"></c:out></td>
										<td><a href="<c:url value='pppppp/system/show_permission_detail_page?roleId=${roleDetail.roleId}'/>"><c:out value="${roleDetail.roleName}"></c:out></a></td>
										<td><c:out value="${roleDetail.roleCode}"></c:out></td>
										<td><c:out value="${roleDetail.roleDescribe}"></c:out></td>
										<td><button class="btn btn-link" onclick="editUserRole(${roleDetail.roleId}, '${roleDetail.roleName}', '${roleDetail.roleCode}', '${roleDetail.roleDescribe}');">编辑</button>
				    					<button class="btn btn-link" onclick="deleteUserRole(${roleDetail.roleId});">删除</button></td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							 -->
						</div><!-- box-content end -->
					</div><!-- box end -->

				</div>
				<!-- content end -->
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>

<div id="dialog_form" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
<div class="modal-header"><h4>角色管理</h4></div>
<div class="modal-body">
    <div id="validate_tip" class="alert alert-error div-display">
    	<span id="validate_content"></span>
    </div>
    <div id="success_tip" class="alert alert-success div-display">
    	<span id="success_content"></span>
    </div>
    <form class="form-horizontal">
    <fieldset>
    	<legend>请输入角色信息</legend>
    	<div id="role_name_main" class="control-group">
            <label class="control-label" for="role_name">角色名称：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="role_name" placeholder="请输入角色名称..">
            </div>
        </div>
        <div id="role_code_main" class="control-group">
            <label class="control-label" for="role_code">角色代号：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="role_code" placeholder="请输入角色代号..">
            </div>
        </div>
        <div id="role_describe_main" class="control-group">
            <label class="control-label" for="role_describe">角色描述：</label>
        	<div class="controls">
        		<textarea  id="role_describe" class="input-xlarge resiz-none" rows="3" placeholder="请输入角色详细描述.."></textarea>
            </div>
        </div>
    </fieldset>
    </form>
</div>
<div class="modal-footer">
    <button type="button" class="btn" onclick="closeDialog();">取消</button>
    <button type="button" class="btn btn-primary" onclick="changeDialog();">提交</button>
</div>
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
