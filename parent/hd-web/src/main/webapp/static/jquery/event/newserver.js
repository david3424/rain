/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-8-12
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */

(function ($) {
    function newServer() {
    }

    $.extend(newServer.prototype, {
        _attachServerList: function (target, settings) {
            var settings = $.extend({
                serverid: 'server',
                servername: "servername",
                cache: true,
                afterLoad:function(){}
            }, settings || {});
            if(!settings.hden){
                throw "请配置 hden属性";
            }
            this._initServerList(target, settings);
            this._bindChange(target, settings);
        },
        _initServerList: function (target, settings) {
            $(target).append("<option value=''>请选择</option>");
            $.ajax({
                url: "/server/list/newserver/restrict",
                data: {hden: settings.hden},
                cache: settings.cache,
                dataType: "json",
                timeout: 30 * 1000,
                error: function () {
                    $(target).append("<option value=''>服务器维护</option>")
                },
                success: function (json) {
                    $.newserver.resolveJSON(json, target);
                    settings.afterLoad();
                }
            });
        },
        _bindChange: function (target, settings) {
            $(target).change(function () {
                var $option = $(this).children("option:selected");
                var $server = $option.val();
                var $servername = $option.text();
                $("#" + settings.serverid).val($server);
                $("#" + settings.servername).val($servername);
            });
        },
        resolveJSON: function (json, target) {
            if (json.size == 0) {
                return;
            }
            $.each(json, function (index, field) {
                $.newserver.createOption(target, field);
            });
        },
        createOption: function (target, serverInfo) {
            var option = [];
            option.push('<option value="');
            option.push(serverInfo.server);
            option.push('">');
            option.push(serverInfo.serverName);
            option.push('</option>');
            $(target).append(option.join(''));
        }

    });

    $.fn.newserver = function (options) {
        return this.each(function () {
            $.newserver._attachServerList(this, options);
        });
    };
    $.newserver = new newServer();
    $.newserver.uuid = new Date().getTime();
    $.newserver.version = "1.0";
}(jQuery));
