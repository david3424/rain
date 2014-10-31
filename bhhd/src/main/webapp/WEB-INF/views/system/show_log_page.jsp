<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台</title>
<%@ include file="../include/reference.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/apps/system/utils/system_common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/apps/system/show_log_page.js"></script>
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
										<th width="40%">时间范围</th>
										<th width="15%">日志类型</th>
										<th width="15%">登陆帐号</th>
										<th width="15%">日志内容</th>
										<th width="15%">操作<br></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="text" id="beginDate" class="input-small form_datetime" style="cursor: pointer;" readonly="readonly" /> -
          									<input type="text" id="endDate" class="input-small form_datetime" style="cursor: pointer;" readonly="readonly" />
          									<button id="srh_beginDateReset" class="btn">重置</button></td>
										<td><select id="log_type" class="input-small">
											<option value="1">登陆日志</option>
											<option value="2">操作日志</option>
										    </select>
										</td>
										<td><input type="text" id="user_account" class="input-small"></td>
										<td><input type="text" id="option_content" class="input-small"></td>
										<td><button id="searchBtn" class="btn">查询</button></td>
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
								<i class="icon-table"></i><span class="break"></span><span>日志明细</span>
							</h2>
							<div class="box-icon">
								<a class="btn-minimize" href="#"><i class="icon-chevron-up"></i>
								</a> <a class="btn-close" href="#"><i class="icon-remove"></i> </a>
							</div>
						</div><!-- box-header end -->
						<div class="box-content">
							<table id="logTable"  class="table table-bordered table-striped dataTable table-hover"></table>
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
