var  typeid;
$(document).ready(function () {
    queryData();

    /*validate*/

    $('#my_form').validate({
        rules: {
            menuTypeName:{
                required: true,
                rangelength: [3, 10]
            },
            menuOrder:{
                required: true,
                number:true
            },
            description:{
                required: false
            }
        }
    });
});
/* Get the rows which are currently selected */
function fnGetSelected(oTableLocal) {
    return oTableLocal.$('tr.row_selected');
}
function addMenuType() {
    var $form = $('#my_form');
    if(!$form.valid()){
        return false;
    }
        var option = {
            url: basePath + 'wanmei/system/addMenuType',
            type: "post",
            dataType: "json",
            success: function (response) {
                if (response.result == 'success') {
                    queryData();
                }
                if (response.result == 'error') {
                 showValidateTip("提交失败！");
                }
            },
            error: function () {
                showValidateTip("提交失败！");
            }
        };
    $form.ajaxSubmit(option);
}

var list = [];
function queryData() {
    var aoColumns = [
        { "sTitle": "菜单名称", "mData": "menuTypeName"},
        { "sTitle": "排序值", "mData": "menuOrder"},
        { "sTitle": "描述", "mData": "description"},
        {"sTitle": "操作", "mData": "menuTypeId", "mRender": function(data, type, row) {return operateButton(data, type, row);}}
    ];

    queryDataTables(list, aoColumns);
//	}
}
/*进入时加载数据*/
function queryDataTables(list, aoColumns) {
    var url = basePath + 'wanmei/system/getMenuTypeTableData';
    commonDataTables("dataTables", url, aoColumns, list);
}

/*该写在了代码里*/
function delMenuType(id) {
    typeid = id;
    $("#informInfoId").html("你确定要进行<strong style='color:red'>删除</strong>用户的操作吗？");

    $("#dialog_inform").modal({
        backdrop: false
    });
}

function closeDialogInform() {
    $("#dialog_inform").modal("hide");
    $("#success_tip_inform").val("").hide();
}


function doDelMenuType() {
    var menuTypeId = $("#params").val();
    var option = {
        url: basePath + 'wanmei/system/deleteMenuType',
        type: "get",
        dataType: "json",
        data: {
            menuTypeId: menuTypeId
        },
        success: function (response) {
            if (response.result == 'success') {
                showSuccessTip("删除成功！");
                setTimeout(function () {
                    closeDialogInform();
                }, stayTime);
                queryData();
            } else if (response.result == 'nodelete') {
                showValidateTip("该类型已存在功能，不能删除！");
                setTimeout(function () {
                    closeDialogInform();
                }, stayTime);
            } else {
                showValidateTip("删除失败！");
                setTimeout(function () {
                    closeDialogInform();
                }, stayTime);
            }
        },
        error: function () {
            showValidateTip("删除操作失败！");
            setTimeout(function () {
                closeDialogInform();
            }, stayTime);
        }
    };
    $.ajax(option);
}


function operateButton(cellvalue, options, rowObject) {
    var editBtn = "<button class='btn btn-link btn-mini' \
        onclick=\"editMenuType('" + rowObject['menuTypeId'] + "'\
        ,'" + rowObject['menuTypeName'] + "'\
        ,'" + rowObject['menuOrder'] + "'\
        ,'" + rowObject['description'] + "'\
        )\">编辑</button>\
         ";
    var deleteBtn = "<button class='btn btn-link btn-mini' onclick=\"deleteMenuType(" + rowObject['menuTypeId'] + ")\">删除</button>";

    return editBtn + "&ensp;" + deleteBtn;
}

function editMenuType(menuTypeId,menuTypeName,menuOrder,description) {
    $("#menuTypeId").val(menuTypeId);
    $("#menuTypeName").val(menuTypeName).focus();
    $("#menuOrder").val(menuOrder);
    $("#description").val(description);
}

function deleteMenuType(menuTypeId){
    $('#params').val(menuTypeId);
    $("#informInfoId").html("你确定要进行<strong style='color:red'>删除</strong>操作吗？");
    $("#dialog_inform").modal({
        backdrop : false
    });
}

