<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript">
    var pageRoleId = "${roleDetail.roleId}";
    var pageGameId = "${gameDetail.gameId}";
</script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/permission_detail.js"></script>
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
						<li><a href="<%=basePath%>pppppp/system/show_role_page">用户角色</a><span class="divider">/</span></li>
						<li><a href="<%=basePath%>pppppp/system/show_role_game_page?roleId=${roleDetail.roleId}">用户商品绑定</a><span class="divider">/</span></li>
						<li class="active">角色权限管理</li>
					</ul>
		            <!-- 表格box -->
					<div class="box">
						<div class="box-header">
							<h2>
								<i class="icon-table"></i><span class="break"></span><span>角色权限管理</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
							<h4>角色信息</h4>
    						<br>
    						<p>角色名称：<c:out value="${roleDetail.roleName}"></c:out></p>
    						<p>职责描述：<c:out value="${roleDetail.roleDescribe}"></c:out></p>
   	 						<hr />
    						<table class="table table-striped">
    							<thead>
      								<tr>
        								<th><span class='badge badge-important'>未绑定权限</span></th>
        								<th>&nbsp;</th>
        								<th><span class='badge badge-success'>已绑定权限</span></th>
      								</tr>
    							</thead>
    							<tbody>
      								<tr>
        								<td><form:select id="unbindPermission_selectId" size="40" style="width: 400px" path="unbindPermissions" items="${unbindPermissions}" itemValue="permissionId" itemLabel="resourceName" multiple="true"></form:select></td>
        								<td style="padding-top: 100px;">
          								<button id="bindPermission_imgId"  class="btn btn-success">绑定</button>
          								<br>
          								<br>
		  								<br>
										<button id="unbindPermission_imgId"  class="btn btn-danger">解除</button></td>
        								<td><form:select id="bindPermission_selectId" size="40" style="width: 400px" path="bindPermissions" items="${bindPermissions}" itemValue="permissionId" itemLabel="resourceName" multiple="true"></form:select></td>
      									<td width="200px"></td>
      									<td width="200px"></td>
      								</tr>
    							</tbody>
    						</table>
						</div><!-- box-content end -->
					</div><!-- box end -->

				</div>
				<!-- content end -->
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>

</body>
</html>
