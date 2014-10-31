<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台</title>

    <%@ include file="../include/reference.jsp"%>

<script type="application/javascript" src="<c:url value='/js/libs/upload/jquery.form.js'/>"></script>
<script type="application/javascript" src="<c:url value='/js/apps/system/utils/system_common.js' />"></script>
<script type="application/javascript" src="<c:url value='/js/apps/system/menu_type_list.js'/>"></script>

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
						
						<div class="box">
							<div class="box-header">
								<h2>
									<em class="icon-edit"></em><span class="break"></span>添加
								</h2>
								<div class="box-icon">
									<a class="btn-minimize" href="#"><em class="icon-chevron-up"></em>
									</a>
								</div>
							</div>
							<!-- box-header end -->
							<div class="box-content">
								<div id="dialog_form">
									    
									    <div id="validate_tip" class="alert alert-error div-display" style="display: none;">
									    	<span id="validate_content"></span>
									    </div>
									    <div id="success_tip" class="alert alert-success div-display" style="display: none;">
									    	<span id="success_content"></span>
									    </div>
									    
									    <form  id="my_form" class="form-horizontal" action="#" method="post" >
									    <fieldset>
									    	<legend>添加菜单类型</legend>
                                            <input type="hidden" name="menuTypeId" id="menuTypeId" />
                                            <div id="1_main" class="control-group">
                                                <label class="control-label" for="menuTypeName">类型名称：</label>
                                                <div class="controls">
                                                    <input type="text" class="input-xlarge" id="menuTypeName" name="menuTypeName" placeholder="请输入类型名称..">
                                                </div>
                                            </div>

                                            <div id="2_main" class="control-group">
                                                <label class="control-label" for="menuOrder">排序：</label>
                                                <div class="controls">
                                                    <input type="text" class="input-xlarge" id="menuOrder" name="menuOrder" placeholder="请输入数字..">
                                                </div>
                                            </div>

                                            <div id="3_main" class="control-group">
                                                <label class="control-label" for="description">描述：</label>
                                                <div class="controls">
                                                    <input type="text" class="input-xlarge" id="description" name="description" placeholder="请输入简要描述..">
                                                </div>
                                            </div>

									        <div id="comments_main" class="control-group">								            
									        	<div class="controls">
									        		<input type="button" class="btn"  value="提交" onclick="addMenuType();" />
									            </div>
									        </div>
									    </fieldset>
									    </form>
									</div>			
							</div>
							<!-- box-content end -->

							<!-- 表格box -->
						<div class="box" id="tableBox">
							<div class="box-header">
								<h2>
									<em class="icon-table"></em><span class="break"></span><span>菜单类型数据</span>
								</h2>
								<div class="box-icon">
									<a class="btn-minimize" href="#"><em class="icon-chevron-up"></em>
									</a> <a class="btn-close" href="#"><em class="icon-remove"></em> </a>
								</div>
							</div>
							<!-- box-header end -->
							<div class="box-content">
								<table id="dataTables"
									class="table table-bordered table-striped dataTable table-hover">
									</table>
							</div>
							<!-- box-content end -->
						</div>
						<!-- box end -->
								
						</div>
						<!-- box end -->
					
				</div>
			</div>
		</div>
		<%@ include file="../commons/footer.jsp"%>
	</div>
   <%--提示框--%>
    <div id="dialog_inform" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
        <div class="modal-header"><h4>操作确认</h4></div>
        <div class="modal-body">
            <p><em class="icon-warning-sign"></em><span id="informInfoId">你确定要进行此操作吗？</span></p>
            <p><input type="hidden" id="params" /></p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn" onclick="closeDialogInform();">取消</button>
            <button type="button" class="btn btn-primary" onclick="doDelMenuType();">提交</button>
        </div>
    </div>

</body>
</html>