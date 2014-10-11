/**
 * 工具模块
 */
var Utils = {};
Utils.Constants = {
    ONE_DAY: 86400000, // 1000*60*60*24
    DATE_REGEXP: /^20\d{2}-\d{2}-\d{2}/
};

/**
 * 时间工具
 */
Utils.DateUtils = {
    /**
     * 两个时间的间隔，单位为天，注意若start，比end还晚，那就是负值
     *
     * @param start
     *            日期字符串，形如“yyyy-mm-dd”
     * @param end
     *            日期字符串，形如“yyyy-mm-dd”
     * @returns {Number}
     */
    daysBetween: function (start, end) {
        if (!Utils.Constants.DATE_REGEXP.test(start)) {
            return null;
        }
        if (!Utils.Constants.DATE_REGEXP.test(end)) {
            return null;
        }
        var startArr = start.split("-"), endArr = end.split("-"), startDate = new Date(
            startArr[0], startArr[1] - 1, startArr[2]), endDate = new Date(
            endArr[0], endArr[1] - 1, endArr[2]), milliseconds = endDate
            .getTime()
            - startDate.getTime(), days = milliseconds
            / Utils.Constants.ONE_DAY;

        return days;
    },
    /**
     * 在指定日期（字符串形式：“yyyy-mm-dd”）上加上指定天数后，返回新的的时间字符串
     *
     * @param dateStr
     * @param dayNum
     * @returns {String}
     */
    plusDayNum: function (datestr, dayNum) {
        if (!Utils.Constants.DATE_REGEXP.test(datestr)) {
            return null;
        }
        var array = datestr.split("-");

        var date = new Date(array[0], array[1] - 1, array[2]), milliseconds = dayNum
            * Utils.Constants.ONE_DAY;
        newDateMilliseconds = milliseconds + date.getTime(),
            newDate = new Date(), newDate.setTime(newDateMilliseconds),
            month = newDate.getMonth() + 1, day = newDate.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        return newDate.getFullYear() + "-" + month + "-" + day;
    },
    /**
     * 将日期转换为统一的String格式
     *
     * @param date
     * @returns {String}
     */
    dateToString: function (date) {
        if (date) {
            var year = date.getFullYear(), month = date.getMonth() + 1, day = date
                .getDate();
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            return year + "-" + month + "-" + day;
        }
        return null;
    },
    /**
     * 将日期字符串转换为日期对象，要求日期字符串为yyyy-mm-dd格式
     *
     * @param date
     * @returns {String}
     */
    stringToDate: function (str) {
        if (!Utils.Constants.DATE_REGEXP.test(str)) {
            return null;
        }
        return new Date(str.replace(/-/g, "/"));
    },
    /**
     * 获取指定日期所在星期的周一，返回字符串
     *
     * @param theDate
     * @returns {String}
     */
    firstDateOfWeek: function (theDate) {
        var firstDateOfWeek, dayOfWeek = theDate.getDay();
        // 如果是星期天
        if (dayOfWeek == 0) {
            firstDateOfWeek = this.plusDayNum(this.dateToString(theDate), -6);
        } else {
            firstDateOfWeek = this.plusDayNum(this.dateToString(theDate),
                -(dayOfWeek - 1));
        }
        return firstDateOfWeek;
    },
    /**
     * 获取指定日期所在星期的周日，返回字符串
     *
     * @param theDate
     * @returns {String}
     */
    lastDateOfWeek: function (theDate) {
        // 先得到周一，再加6
        return this.plusDayNum(this.firstDateOfWeek(theDate), 6);
    }
};

$(function () {
    initBox();
    // initDatePicker(); //公共初始化日期
});

function initDatePicker() {
    $('.form_datetime').datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        endDate: '+1',
        language: 'zh-CN'
    });
}

function initBox() {
    $('.btn-close').click(function (e) {
        e.preventDefault();
        $(this).parent().parent().parent().fadeOut();
    });
    $('.btn-minimize').click(
        function (e) {
            e.preventDefault();
            var $target = $(this).parent().parent().next('.box-content');
            if ($target.is(':visible'))
                $('i', $(this)).removeClass('icon-chevron-up').addClass(
                    'icon-chevron-down');
            else
                $('i', $(this)).removeClass('icon-chevron-down').addClass(
                    'icon-chevron-up');
            $target.slideToggle();
        });
}

function showValidateTip(_info) {
    $("#validate_content").html(_info);
    $("#validate_tip").show();
}
function showSuccessTip(_info) {
    $("#success_content").html(_info);
    $("#success_tip").show();
}

function stringToDate(str) {
    return new Date(str.replace(/-/g, "/"));
}

function dateFormat(date) {
    return $.format.date(date, "yyyy-MM-dd");
}

function timestampFormat(date) {
    return $.format.date(date, "yyyy-MM-dd hh:mm:ss");
}
function timestampFormat2(date) {
    return $.format.date(date, "yyyy-MM-dd HH:mm:ss");
}

/**
 * 时间和差
 */
function daysTime(_date, day) {
    var time = _date.split("-");

    var now = new Date(time[0], time[1] - 1, time[2]); //
    var milliseconds = day * 1000 * 60 * 60 * 24;
    var testdate = milliseconds + now.getTime();
    var testDate = new Date();
    testDate.setTime(testdate);
    var month = testDate.getMonth() + 1;
    var day = testDate.getDate();
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    return testDate.getFullYear() + "-" + month + "-" + day;
}


function m_daysTime(_date, n) {

    var nowtime1 = new Date(_date).getTime();
    nowtime1 = nowtime1 + n * 1000 * 60 * 60 * 24;
    nowtime1 = new Date(nowtime1);
    var month = nowtime1.getMonth() + 1;
    var day = nowtime1.getDate();
    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;
    return nowtime1.getFullYear() + "-" + month + "-" + day;

}

function initSelect2(selId, optn, placeholder) {
    $("#" + selId).html(optn);
    $("#" + selId).select2({
        placeholder: placeholder,
        allowClear: true,
        maximumSelectionSize: 5
    });
    $("#" + selId + "_clear").click(function () {
        $("#" + selId).select2("val", "");
    });
}

function commonDataTables(tableId, url, aoColumns, params) {
    var dt = $('#' + tableId).dataTable(
        {
            "bSort": false,
            "bProcessing": true,
            "bFilter": false,
            "bServerSide": true,
            "bLengthChange": true,
            "bAutoWidth": false,
            "iDisplayLength": 20,
            "sAjaxSource": url,
            "bDestroy": true,
            "sServerMethod": "POST",
            "aoColumns": aoColumns,
            "sPaginationType": "full_numbers", // 用这个分页的样式是自己设计的
            "fnServerParams": function (aoData) {
                for (var i = 0; i < params.length; i++) {
                    aoData.push(params[i]);
                }
            },
            "fnServerData": function (sSource, aoData, fnCallback) {
                $.ajax({
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": {
                        dt_json: $.toJSON(aoData)
                    },
                    "success": function (records) {
                        $('#loading_data').hide();
                        $.each(aoData, function (n, v) {
                            if (v.name == 'visittime') {
                                aoData[n].value = 2;
                            }
                        });
                        fnCallback(records);
                    }
                });
            },
            "oLanguage": {
                "sLengthMenu": '每页显示 <select>'
                    + '<option value="5">5</option>'
                    + '<option value="20">20</option>'
                    + '<option value="50">50</option>'
                    + '<option value="100">100</option>'
                    + '</select> 条记录',
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoEmtpy": "找不到相关数据",
                "sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
                "sProcessing": "正在加载中...",
                "sSearch": "搜索",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": " 首页 ",
                    "sPrevious": " 上一页 ",
                    "sNext": " 下一页 ",
                    "sLast": " 尾页 "
                }
            }
        });

    return dt;
}

function commonDataTablesFixed(tableId, url, aoColumns, params, tablename) {
    var table = $('#' + tableId).dataTable(
        {
            "bFilter": false,
            "bProcessing": true,
            "bServerSide": true,
            "bAutoWidth": false,
            "bDestroy": true,
            "sAjaxSource": url,
            "sServerMethod": "POST",
            "bDestroy": true,
            "iDisplayLength": 20,
            "aoColumns": aoColumns,
            "sPaginationType": "full_numbers", // 用这个分页的样式是自己设计的
            "sScrollX": "100%",
            "bAutoWidth": false,
            "sScrollerInner": "150%",
            "bScrollCollapse": true,
            // "fnServerParams" : function(aoData){
            // for(var i=0; i< params.length; i++){
            // aoData.push(params[i]);
            // }
            // },
            "fnServerData": function (sSource, aoData, fnCallback) {
                $.ajax({
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "async": false,
                    "data": {
                        dt_json: $.toJSON(aoData),
                        params: $.toJSON(params),
                        tablename: tablename
                    },
                    "success": function (records) {
                        if ($('#loading_data').length) {
                            $('#loading_data').hide();
                        }
                        $.each(aoData, function (n, v) {
                            if (v.name == 'visittime') {
                                aoData[n].value = 2;
                            }
                        });
                        fnCallback(records);
                    }
                });
            },
            "oLanguage": {
                "sLengthMenu": '每页显示 <select>'
                    + '<option value="20">20</option>'
                    + '<option value="50">50</option>'
                    + '<option value="100">100</option>'
                    + '</select> 条记录',
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoEmtpy": "找不到相关数据",
                "sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
                "sProcessing": "正在加载中...",
                "sSearch": "搜索",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": " 首页 ",
                    "sPrevious": " 上一页 ",
                    "sNext": " 下一页 ",
                    "sLast": " 尾页 "
                }
            }
        });
    return table;
}

function initHighChartsLine(chartsId, title, subTitle, categories, yTitle, tooltipValueSuffix, series) {
    $('#' + chartsId).highcharts({
        chart: {
            type: 'line',
            backgroundColor: '#FCFCFC' // transparent
        },
        title: {
            text: title,
            x: -20
            // center
        },
        subtitle: {
            text: subTitle,
            x: -20
        },
        xAxis: {
            categories: categories,
            labels: {
                rotation: -45,
                align: 'right'
            }
        },
        yAxis: {
            title: {
                text: yTitle
            },
            plotLines: [
                {
                    value: 0,
                    width: 1,
                    color: '#808080'
                }
            ]
        },
        tooltip: {
            valueSuffix: tooltipValueSuffix
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -10,
            y: 100,
            borderWidth: 0
        },
        series: series
    });
}

function initHighChartsBar(chartsId, title, subTitle, categories, yAxisTitle, series) {
    $('#' + chartsId).highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: title
        },
        subtitle: {
            text: subTitle
        },
        xAxis: {
            categories: categories,
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: yAxisTitle,
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: 'millions'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -100,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: '#FFFFFF',
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: series
    });
}

function initHighChartsColumn(chartsId, title, subTitle, categories, yAxisTitle, series) {
    $('#' + chartsId).highcharts({
        chart: {
            type: 'column',
            backgroundColor: '#FCFCFC' // transparent
        },
        title: {
            text: title
        },
        subtitle: {
            text: subTitle
        },
        xAxis: {
            categories: categories,
            labels: {
                rotation: -45,
                align: 'right'
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: yAxisTitle
            }
        },
        tooltip: {
            // headerFormat: '<span
            // style="font-size:10px">{point.key}</span><table>',
            // pointFormat: '<tr><td
            // style="color:{series.color};padding:0">{series.name}: </td>' +
            // '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            // footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: series
    });
}

// 初始化运营商
function initProviderSel(id, providerList) {
    var options = "";
    for (var i = 0; i < providerList.length; i++) {
        var provider = providerList[i];
        options += "<option value='" + provider.provider_id + "'>"
            + provider.provider_name + "</option>";
    }
    $("#" + id).html(options);
    return options;
}

//初始化运营商带全部
function initProviderWithAllSel(id, providerList) {
    var options = "<option value='all'>全部</option>";
    for (var i = 0; i < providerList.length; i++) {
        var provider = providerList[i];
        options += "<option value='" + provider.provider_id + "'>"
            + provider.provider_name + "</option>";
    }
    $("#" + id).html(options);
    return options;
}

// 初始化服务器
function initServerSel(id, serverList) {
    var options = "<option value='all'>全部</option>";
    // var options = "";
    var providerServerList = getInitServerList();
    for (var i = 0; i < providerServerList.length; i++) {
        var server = providerServerList[i];
        options += "<option value='" + server.groupName + "'>" + server.alias
            + "</option>";
    }
    $("#" + id).html(options);
    return options;
}
function getInitServerList() {
    var serverLsit = null;
    var option = {
        url: gamePath + 'gameincome/dailyreport/initProvidersAndServers',
        type: "post",
        dataType: "json",
        async: false,
        data: {},
        success: function (records) {
            serverLsit = records.serverLsit;
        },
        error: function () {

        }
    };
    $.ajax(option);

    return serverLsit;
}

// 运营商与服务器联动:
// provider：运营商值
// gameServerId:服务器ID
function providerAndServerLinkChange(provider, gameServerId) {
    var option = {
        url: gamePath + 'gameincome/dailyreport/initQueryCondition',
        type: "post",
        dataType: "json",
        data: {
            gameId: gameId,
            provider: provider
        },
        success: function (records) {
            var servers = records.servers;
            initProviderServerSel(gameServerId, servers);
        },
        error: function () {

        }
    };
    $.ajax(option);
}

function initProviderServerSel(id, serverList) {
    var options = "<option value='all'>全服</option>";
    // var options = "";
    for (var i = 0; i < serverList.length; i++) {
        var server = serverList[i];
        options += "<option value='" + server.groupName + "'>" + server.alias
            + "</option>";
    }
    $("#" + id).html(options);
    return options;
}

function getGraphServerList() {
    var graphServerList = null;
    var option = {
        url: gamePath + 'gameincome/dailyreport/initProvidersAndServers',
        type: "post",
        dataType: "json",
        async: false,
        data: {},
        success: function (records) {
            graphServerList = records.graphServerList;
        },
        error: function () {

        }
    };
    $.ajax(option);

    return graphServerList;
}

function initGraphProviderServerSel(serverList) {
    var options = "";
    for (var i = 0; i < serverList.length; i++) {
        var server = serverList[i];
        options += "<option value='" + server.groupName + "'>" + server.alias
            + "</option>";
    }
    return options;
}

function formatNumber(n) {
    var j = ",";
    var s = n + "";
    var l = s.length;
    var m = l % 3;
    if (m == l)
        return s;
    else if (m == 0)
        return (s.substring(m).match(/\d{3}/g)).join(j);
    else
        return [ s.substr(0, m) ].concat(s.substring(m).match(/\d{3}/g))
            .join(j);
}

// 格式化日期
function formatDateStr(datestr) {
    var time = datestr.split("-");
    var now = new Date(time[0], time[1] - 1, time[2]);
    var day = now.getDay();
    var week;
    switch (day) {
        case 0:
            week = "星期日";
            break;
        case 1:
            week = "星期一";
            break;
        case 2:
            week = "星期二";
            break;
        case 3:
            week = "星期三";
            break;
        case 4:
            week = "星期四";
            break;
        case 5:
            week = "星期五";
            break;
        case 6:
            week = "星期六";
            break;
        default:
            break;
    }
    return datestr + "( " + week + " )";
}

// 通用函数：格式化数据为%号格式显示
function formatPercent(n) {
    return (n * 100).toFixed(2) + '%';
}

function formatDouble(n) {
    return n.toFixed(2);
}
// 自动换行
function strAutoBr(data, type, row) {
    var oldstr = data;
    var newstr = '';
    var max = 50;
    if (oldstr.length > max) {
        var i = oldstr.length % max == 0 ? parseInt(oldstr.length / max)
            : (parseInt(oldstr.length / max) + 1);
        for (var j = 0; j < i; j++) {
            newstr += oldstr.substr(j * 50, max) + '<br>';
        }
    } else {
        newstr = oldstr + "";
    }

    return newstr;
}

// 初始化入口标识
function initClientSel(id) {
    var options = "<option value='QB'>全部</option>"
        + "<option value='WZD'>完整端</option>"
        + "<option value='WD'>微端</option>"
        + "<option value='WYD'>网页端</option>";
    $("#" + id).html(options);
}

function initClientSelByFlag(id, isClient) {
    if (isClient == 1) { //分端
        initClientSel(id);
    } else {
        var options = "<option value='QB'>全部</option>";
        $("#" + id).html(options);
    }
}

//格式化入口
function formatClient(data) {
    var re = "全部";
    if (data == 'WZD') re = "完整端";
    if (data == 'WD') re = "微端";
    if (data == 'WYD') re = "网页端";
    return re;
}

function initHighchartsStackedColumn(chartsId, title, categories, yAxisTitle, series, unit, max) {
    $('#' + chartsId)
        .highcharts(
        {
            chart: {
                type: 'column',
                backgroundColor: '#FCFCFC', // transparent
            },
            title: {
                text: title
            },
            xAxis: {
                categories: categories
            },
            yAxis: {
                min: 0,
                max: max,
                title: {
                    text: yAxisTitle
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor)
                            || 'gray'
                    }
                },
                labels: {
                    formatter: function () {
                        return this.value + unit;
                    }
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>'
                        + this.series.name + ': ' + this.y
                        + unit + '<br/>' + 'Total: '
                        + this.point.stackTotal + unit;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor)
                            || 'white',
                        formatter: function () {
                            return this.y + unit;
                        }
                    }
                }
            },
            series: series
        });
}

function isInteger(str) {
    var regex = /^\d+$/;
    return regex.test(str);
}

function initHighChartsLineDetails(chartsId, title, subTitle, categories, yTitle, tooltipValueSuffix, series, min, max, unit, xTitle) {
    $('#' + chartsId).highcharts({
        chart: {
            type: 'line',
            backgroundColor: '#FCFCFC' // transparent
        },
        title: {
            text: title,
            x: -20
            // center
        },
        subtitle: {
            text: subTitle,
            x: -20
        },
        xAxis: {
            categories: categories,
            labels: {
                rotation: -45,
                align: 'right'
            },
            title: {
                text: xTitle
            },
        },
        yAxis: {
            min: min,
            title: {
                text: yTitle
            },
            plotLines: [
                {
                    value: 0,
                    width: 1,
                    color: '#808080'
                }
            ],
            labels: {
                formatter: function () {
                    return this.value + unit;
                }
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>'
                    + this.series.name + ': ' + this.y
                    + unit;
            },
            valueSuffix: tooltipValueSuffix
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -10,
            y: 100,
            borderWidth: 0
        },
        series: series
    });
}

function initHighChartsColumnDetails(chartsId, title, subTitle, categories, yAxisTitle, series, min, max, unit, xTitle) {
    $('#' + chartsId).highcharts({
        chart: {
            type: 'column',
            backgroundColor: '#FCFCFC', // transparent
        },
        title: {
            text: title
        },
        subtitle: {
            text: subTitle
        },
        xAxis: {
            categories: categories,
            title: {
                text: xTitle
            },
            labels: {
                rotation: -45,
                align: 'right'
            }
        },
        yAxis: {
            min: min,
            title: {
                text: yAxisTitle
            }, labels: {
                formatter: function () {
                    return this.value + unit;
                }
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}' + unit + '</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: series
    });
}
