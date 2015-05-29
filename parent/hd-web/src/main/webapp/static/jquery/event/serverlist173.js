(function ($) {
    function ServerList() {
    }

    $.extend(ServerList.prototype, {
        gameProperties: {
            inf: 43
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
            var aid = $.serverlist.gameProperties[settings.gamename];
            $.ajax({
                url: "/server/list/server173json?t=" + new Date().getTime(),
                dataType: "json",
                data: {gameId: aid},
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
                //普通列表
                if (!includeides && !exceptids) {
                    $.serverlist.createOption(target, $zone);
                } else if (includeides) {
                    for (var i = includArray.length - 1; i >= 0; i--) {
                        if (includArray[i] == $zone.attr("server")) {
                            $.serverlist.createOption(target, $zone);
                            break;
                        }
                    }
                } else if (exceptids) {
                    var notInclude = true;
                    for (var i = exceptArray.length - 1; i >= 0; i--) {
                        if (exceptArray[i] == $zone.attr("server")) {
                            notInclude = false;
                            break;
                        }
                    }
                    if (notInclude) {
                        $.serverlist.createOption(target, $zone);
                    }

                }
            });
        },
        createOption: function (target, zoneInfo) {
            var option = [];
            option.push('<option value="');
            option.push(zoneInfo.attr('server'));
            option.push('_');
            option.push(zoneInfo.attr("serverName"));
            option.push('">');
            option.push(zoneInfo.attr("serverName"));
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