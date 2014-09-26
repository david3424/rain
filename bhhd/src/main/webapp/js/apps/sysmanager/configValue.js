var ConfigValue = {};

ConfigValue.Contants = {
	CAN_EDIT : 1,
	CANNOT_EDIT : 0,
	EDIT_SUCCESS : "success"	
}

ConfigValue.Component = {
	aoColumns : null
}

$(function() {
	ConfigValue.Component.aoColumns = [
		{ "sTitle": "功能", "mData": "id", "sWidth" : "15%" },
		{ "sTitle": "值", "mData": "value", "sWidth" : "35%" },
		{ "sTitle": "描述", "mData": "description", "sWidth" : "50%" }
    ];
	var readParams = [
		{name : "gameId", value : 13},
		{name : "canEdit", value : ConfigValue.Contants.CANNOT_EDIT}
  	];
	
	var url = gamePath + 'sysmanager/configvalue/getTable';
	buildEditableDataTable("readConfigValueTable", url, ConfigValue.Component.aoColumns, readParams);
	
	var editParams = [
  		{name : "gameId", value : 13},
  		{name : "canEdit", value : ConfigValue.Contants.CAN_EDIT}
	];
	var oTable = buildEditableDataTable("editConfigValueTable", url, ConfigValue.Component.aoColumns, editParams);
	oTable.makeEditable({
		sUpdateURL : gamePath + 'sysmanager/configvalue/updateRow',
//		sUpdateURL : function(value, settings) {
//			// 这里必须返回相同value，其他任何处理都会被当做一个错误信息
//			return value;
//		},
		"aoColumns" : [
		     null, // null表示不可编辑
		     {
		         placeholder: 'null',	//指定默认值，如果没有值就用该默认值显示
		    	 tooltip: '双击修改值',
		    	 type: 'textarea',
		    	 submit: '保存',
		    	 cancel: '取消'
		     },
		     {
		         placeholder: 'null',	//指定默认值，如果没有值就用该默认值显示
		    	 tooltip: '双击修改描述',
		    	 type: 'textarea',
		    	 submit: '保存',
		    	 cancel: '取消'
		     }
		],
		fnOnEdited: function(status) {
			if (status === ConfigValue.Contants.EDIT_SUCCESS) {
				alert("编辑成功");
			}
			else {
				alert("编辑失败");
			}
		}
	});

});

function buildEditableDataTable(elementId, url, aoColumns, params){ 
	return $('#' + elementId).dataTable({
		"bSort": false,
		"bPaginate": false,
		"bProcessing": true,
		"bFilter" : false,
		"bServerSide": true,
    	"bLengthChange": true,
    	"iDisplayLength": 100,
		"sAjaxSource": url,
		"bDestroy": true,
		"sServerMethod": "POST",
		"aoColumns": aoColumns,
		"fnServerParams": function(aoData){
			for(var i = 0, len = params.length; i < len; i++){
				aoData.push(params[i]);
			}
	    },
	    "fnServerData" : function(sSource, aoData, fnCallback, oSettings) {
	    	$.ajax({
	    		"dataType" : 'json',
	    		"type" : "POST",
	    		"url" : sSource,
	    		"data" : {
	    			dt_json : $.toJSON(aoData)
	    		},
	    		"success" : fnCallback 
	    	});
	    },
	    "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		    // 利用fnRowCallback为每个tr赋id，aData对应的是ConfigValueBean
	    	$(nRow).attr("id", aData.id);
		},
	    "oLanguage": {
	        "sLengthMenu": '每页显示 <select>'+
					        '<option value="100">100</option>'+
					        '<option value="200">200</option>'+
					        '<option value="300">300</option>'+
					        '</select> 条记录',
	        "sZeroRecords": "对不起，查询不到任何相关数据",
	        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
	        "sInfoEmtpy": "找不到相关数据",
	        "sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
	        "sProcessing": "正在加载中...",
	        "sSearch": "搜索",
	        "sUrl": "", 
	        "oPaginate": {
	            "sFirst":    "第一页",
	            "sPrevious": " 上一页 ",
	            "sNext":     " 下一页 ",
	            "sLast":     " 最后一页 "
	        }
	    }
	});
}