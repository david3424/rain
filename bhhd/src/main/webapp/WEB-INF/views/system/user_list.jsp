<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页游统计平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/user_list.js"></script>
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
					<div class="box">
						<div class="box-header">
							<h2>
								<em class="icon-edit"></em><span class="break"></span>选择查询条件
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><em class="icon-chevron-up"></em>
								</a>
							</div>
						</div>
						<!-- box-header end -->
						<div class="box-content">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="20%">角色名称</th>
										<th width="20%">用户状态</th>
										<th width="20%">用户姓名</th>
										<th width="20%">登陆帐号</th>
										<th width="20%">操作<br></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><form:select id="user_role_id" path="roleDetailListAll" items="${roleDetailListAll}" itemValue="roleId" itemLabel="roleName" cssStyle="width: 120px;" /></td>
										<td><form:select id="user_status_id" path="userStatusListAll" items="${userStatusListAll}" itemValue="id" itemLabel="name" cssStyle="width: 120px;" /></td>
										<td><input type="text" id="account_name" class="input-small" /></td>
										<td><input type="text" id="user_account" class="input-small" /></td>
										<td><button id="searchBtn" class="btn">查询</button>&ensp;&ensp;&ensp;&ensp;
										    <button id="add_user_id" class="btn btn-primary">新建</button></td>
									</tr>
								</tbody>
							</table>
							<!-- box-content end -->
						</div>
					</div>
					<!-- box end -->
		            <!-- 表格box -->
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-table"></i><span class="break"></span><span>用户明细</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
    						<table id="userTable"  class="table table-bordered table-striped dataTable table-hover"></table>
						</div><!-- box-content end -->
					</div><!-- box end -->

				</div>
				<!-- content end -->
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>

<div id="dialog_form" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
<div class="modal-header"><h4>用户管理</h4></div>
<div class="modal-body">
    <div id="validate_tip" class="alert alert-error div-display">
    	<span id="validate_content"></span>
    </div>
    <div id="success_tip" class="alert alert-success div-display">
    	<span id="success_content"></span>
    </div>
    <form class="form-horizontal" action="">
    <fieldset>
    	<legend>请输入用户信息</legend>
    	<div id="account_main" class="control-group">
            <label class="control-label" for="account">登陆帐号：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="account" placeholder="请输入登陆帐号..">
            </div>
        </div>
        <div id="user_name_main" class="control-group">
            <label class="control-label" for="user_name">用户名称：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="user_name" placeholder="请输入用户名称..">
            </div>
        </div>
        <div id="user_pwd_main" class="control-group">
            <label class="control-label" for="user_pwd">登录密码：</label>
        	<div class="controls">
        		<input type="password" class="input-xlarge" id="user_pwd" placeholder="请输入登录密码..">
            </div>
        </div>
        <div id="role_id_main" class="control-group">
            <label class="control-label" for="role_id">用户角色：</label>
        	<div class="controls">
        		<form:select id="role_id" path="roleDetailList" items="${roleDetailList}" itemValue="roleId" itemLabel="roleName"></form:select>
            </div>
        </div>
        <div id="user_status_main" class="control-group">
            <label class="control-label" for="user_status">用户状态：</label>
        	<div class="controls">
        		<form:select id="user_status" path="userStatusList" items="${userStatusList}" itemValue="id" itemLabel="name"></form:select>
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
    <p><em class="icon-warning-sign"></em><span id="informInfoId">你确定要进行此操作吗？</span></p>
</div>
<div class="modal-footer">
    <button type="button" class="btn" onclick="closeDialogInform();">取消</button>
    <button type="button" class="btn btn-primary" onclick="changeDialogInform();">提交</button>
</div>
</div>

</body>
</html>
