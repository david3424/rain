$().ready(function() {
	initTargetDescribe();
});

function initTargetDescribe() {
	targetDescribeDataTables();
}

// 显示指标信息表格
function targetDescribeDataTables() {
	
	var targetName = $("#targetName").val();
	var url = gamePath + 'sysmanager/targetdescribe/targetDescribeDataTables';
	var aoColumns = [
	                  { "sTitle": "指标ID", "mData": "kpi_id" },
	                  { "sTitle": "指标名称", "mData": "web_name" },
	    	          { "sTitle": "指标说明", "mData": "kpi_desc" }
	    	      ];
	var params = [
	              {name : "targetName", value : targetName}
	             ];
	commonDataTables("targetDescribeTable", url, aoColumns, params);

}