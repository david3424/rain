(function ($) {
    function ServerList() {
    }

    $.extend(ServerList.prototype, {
        gameProperties: {
            world2: [1, 2, 3, 4, 5, 6, 7, 8],
            wulin2: 9,
            w2i: 10,
            zhuxian2: 11,
            chibi: 12,
            rwpd: 13,
            kdxy: 14,
            sgcq: 18,
            mhzx2: 15,
            xlzj: 22,
            shenmo: 19,
            sgsj: 25,
            yt: 17,
            ys: 14,
            xajh: 23,
            xljz: 31,
            seiya: 28,
            sdxl: 26,
            mslr: 35,
            sw: 40,
            sd:32,
            ts:50
        },
        _attachServerList: function (target, settings) {
            var settings = $.extend({
                serverid: 'server',
                servername: "servername"
            }, settings || {});
            this._initServerList(target, settings);
            this._bindChange(target, settings);
        },
        _initServerList: function (target, settings) {
            $(target).append("<option value=''>请选择</option>");
            $.ajax({
                url: "/server/list/json?t=" + new Date().getTime(),
                dataType: "json",
                timeout: 30 * 1000,
                error: function () {
                    $(target).append("<option value=''>服务器维护</option>")
                },
                success: function (json) {
                    $.serverlist.resolveJSON(json, target, settings);
                }
            });
        },
        _bindChange: function (target, settings) {
            $(target).change(function () {
                var $serverInfo = $(this).val();
                if ($serverInfo == '') {
                    $("#" + settings.serverid).val('');
                    $("#" + settings.servername).val('');
                    return;
                }
                $("#" + settings.serverid).val($serverInfo.split("_")[0]);
                $("#" + settings.servername).val($serverInfo.split("_")[1]);
            });
        },
        resolveJSON: function (json, target, settings) {
            var aid = $.serverlist.gameProperties[settings.gamename];
            var includeides = settings.includeids;
            var exceptids = settings.exceptids;
            if (includeides && exceptids) {
                throw "includeids,exceptids只能配置一种。";
            } else if (includeides) {
                var includArray = includeides.split(",");
            } else if (exceptids) {
                var exceptArray = exceptids.split(",");
            }
            $.each(json.Datas, function (index, field) {
                var $zone = $(this);
                if (aid == $zone.attr("aid") || ("world2" == settings.gamename && $zone.attr("aid") <= 8)) {
                    //普通列表
                    if (!includeides && !exceptids) {
                        $.serverlist.createOption(target, $zone);
                    } else if (includeides) {
                        for (var i = includArray.length - 1; i >= 0; i--) {
                            if (includArray[i] == $zone.attr("id")) {
                                $.serverlist.createOption(target, $zone);
                                break;
                            }
                        }
                    } else if (exceptids) {
                        var notInclude = true;
                        for (var i = exceptArray.length - 1; i >= 0; i--) {
                            if (exceptArray[i] == $zone.attr("id")) {
                                notInclude = false;
                                break;
                            }
                        }
                        if (notInclude) {
                            $.serverlist.createOption(target, $zone);
                        }

                    }
                }
            });
        },
        createOption: function (target, zoneInfo) {
            var option = [];
            option.push('<option value="');
            option.push(zoneInfo.attr('id'));
            option.push('_');
            option.push(zoneInfo.attr("name"));
            option.push('">');
            option.push(zoneInfo.attr("name"));
            option.push('</option>');
            $(target).append(option.join(''));
        }

    });

    $.fn.serverlist = function (options) {
        return this.each(function () {
            $.serverlist._attachServerList(this, options);
        });
    };
    $.serverlist = new ServerList();
    $.serverlist.uuid = new Date().getTime();
    $.serverlist.version = "2.0";
}(jQuery));