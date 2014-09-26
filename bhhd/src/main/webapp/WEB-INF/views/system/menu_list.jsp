<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页游统计平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/menu_list.js"></script>
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
								<i class="icon-edit"></i><span class="break"></span>选择查询条件
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a>
							</div>
						</div>
						<!-- box-header end -->
						<div class="box-content">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="20%">游戏列表</th>
										<th width="20%">菜单类型</th>
										<th width="20%">菜单名称</th>
										<th width="20%">菜单URL</th>
										<th width="20%">操作<br></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><form:select id="srhGameId" path="gameList" cssStyle="width: 120px;">
                    						<form:option value="" label="全部"></form:option>
                    						<form:options items="${gameList}" itemValue="gameId" itemLabel="gameName" /></form:select></td>
										<td><form:select id="srhMenuTypeId" path="menuTypeList" cssStyle="width: 120px;">
                    						<form:option value="" label="全部"></form:option>
                    						<form:options items="${menuTypeList}" itemValue="menuTypeId" itemLabel="menuTypeName" /></form:select></td>
										<td><input type="text" id="srhResourceName" class="input-small"></td>
										<td><input type="text" id="srhResourceUrl" class="input-small"></td>
										<td><button id="searchBtn" class="btn">查询</button>&ensp;&ensp;&ensp;&ensp;
										    <button id="add_menu_id" class="btn btn-primary">新建</button></td>
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
								<i class="icon-table"></i><span class="break"></span><span>菜单明细</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
    						<table id="menuTable"  class="table table-bordered table-striped dataTable table-hover"></table>
						</div><!-- box-content end -->
					</div><!-- box end -->

				</div>
				<!-- content end -->
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>

<div id="dialog_form" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
<div class="modal-header"><h4>菜单管理</h4></div>
<div class="modal-body">
    <div id="validate_tip" class="alert alert-error div-display">
    	<span id="validate_content"></span>
    </div>
    <div id="success_tip" class="alert alert-success div-display">
    	<span id="success_content"></span>
    </div>
    <form class="form-horizontal">
    <fieldset>
    	<legend>请输入菜单信息</legend>
    	<div id="resource_name_main" class="control-group">
            <label class="control-label" for="resource_name">菜单名称：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="resource_name" placeholder="请输入菜单名称..">
            </div>
        </div>
        <div id="resource_url_main" class="control-group">
            <label class="control-label" for="resource_url">菜单URL：</label>
        	<div class="controls">
        		<input type="text" class="input-xlarge" id="resource_url" placeholder="请输入菜单URL..">
            </div>
        </div>
        <div id="game_id_main" class="control-group">
            <label class="control-label" for="game_id">所属游戏：</label>
        	<div class="controls">
        		<form:select id="game_id" path="gameList" items="${gameList}" itemValue="gameId" itemLabel="gameName"></form:select>
            </div>
        </div>
        <div id="menu_type_id_main" class="control-group">
            <label class="control-label" for="menu_type_id">菜单类型：</label>
        	<div class="controls">
        		<form:select id="menu_type_id" path="menuTypeList" items="${menuTypeList}" itemValue="menuTypeId" itemLabel="menuTypeName"></form:select>
            </div>
        </div>
        <div id="describe_main" class="control-group">
            <label class="control-label" for="describe">菜单描述：</label>
        	<div class="controls">
        		<textarea  id="describe" class="input-xlarge resiz-none" rows="3" placeholder="请输入菜单详细描述.."></textarea>
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
