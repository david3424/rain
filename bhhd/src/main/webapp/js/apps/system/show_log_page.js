/**
 * 用户操作日志
 */
$(document).ready(function() {
	initDatePicker();

	getLogTables();

	$("#searchBtn").click(function() {
		getLogTables();
	});

	$("#log_type").change(function() {
		getLogTables();
	});

	$("#srh_beginDateReset").click(function() {
		$("#beginDate").val(null);
		$("#endDate").val(null);
	});

});

function getLogTables() {
	var url = basePath + "pppppp/system/get_log_table";
	var columns = [{"sTitle": "时间", "mData": "optionTimeStr"},
	       	       {"sTitle": "帐号", "mData": "account"},
	    	       {"sTitle": "IP", "mData": "loginIp"},
	    	       {"sTitle": "日志内容", "mData": "message"}];
	var params =[{name: 'beginDate',  value: $("#beginDate").val()},
    			 {name: 'endDate', value: $("#endDate").val()},
    			 {name: 'logType', value: $.trim($("#log_type").val())},
    		     {name: 'userAccount', value: $.trim($("#user_account").val())},
    		     {name: 'optionContent', value: $.trim($("#option_content").val())}];
	commonDataTables("logTable", url, columns, params);
}
