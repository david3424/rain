(function($) {
    $.hd = $.hd || {};
    $.extend($.hd, {
        /**
         * 分页的静态方法
         *
         */
        pagination:function(options) {
            //分页参数

            options = $.extend({
                url:"",
                pageSize:10,
                params:{},
                data_area_id: "data",
                tmpl_script: "tmpl",
                page_area_id: "page",
                num_display_entries:6,
                current_page:0,
                num_edge_entries:0,
                prev_text:"上一页",
                next_text:"下一页",
                ellipse_text:"...",
                prev_show_always:true,
                next_show_always:true,
                callback: function(page) {
                    getData(page);
                }
            }, options || {});
            options.reload = true;
            var params = options.params || {};
            var getData = function(page) {
                params.pageNo = parseInt(page) + 1;
                params.pageSize = options.pageSize;
                options.items_per_page = options.pageSize;
                $.post(options.url, params, function(data) {

                    $("#" + options.data_area_id).empty().append($("#" + options.tmpl_script).tmpl(data));  //模版应用
//                    console.log($("#" + options.data_area_id).html());
                    if (options.reload) {
                        $("#" + options.page_area_id).pagination(data.totalCount, options); //分页代码
                        options.reload = false;
                    }
                },'json');
            }
            getData(options.current_page);
        },

        jump:function(options) {
            $.extend({
                jump_button:"jump",
                page_number:"page_number"
            }, options || {});
            $("#" + options.jump_button).click(function() {
                var pageNo = parseInt($("#" + options.page_number).val());
                if (isNaN(pageNo) || pageNo < 1) {
                    return;
                } else {
                    options.pagination_options.current_page = pageNo - 1;
                    options.pagination_options.reload = true;
                    $.hd.pagination(options.pagination_options);
                }
            });
        },

        refreshPagination:function(options) {
            options.reload = true;
            $.hd.pagination(options);
        }
    });
})(jQuery);

