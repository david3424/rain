<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--
  Created by IntelliJ IDEA.
  User: ssw
  Date: 2015/6/26
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
    <link href="http://event51.wanmei.com/hex/201507/moshi/style/master.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="http://www.wanmei.com/public/js/wm.js" top="false" bottom="false"></script>
    <script type="text/javascript" src="http://www.wanmei.com/public/js/jq_171.js"></script>
    <title>个人中心</title>
    <style>
    	.msg{ margin-left:75px;}
    </style>
</head>
<body>
<div class="bg">
    <!--left-->
    <div class="left">
        <%--<div class="wm-public" logo="{name:'hex',skin:2,top:20,left:-5,zindex:500}" title="HEX官方网站">HEX官方网站</div>--%>
        <!--left_nav-->
        <div class="left_nav">
            <a id="nav1" href="${ctx}/demo/webmall/index"></a>
            <a id="nav2" href="${ctx}/demo/webmall/toexchange"></a>
            <a id="nav3" href="javascript:alert('敬请期待！')"></a>
            <a id="nav4" class="on" href="${ctx}/demo/webmall/mycenter"></a>
        </div>
    </div>
    <!--right-->
    <div class="right">
        <div class="title"><img src="" alt="title1"/></div>
        <!--login or logout-->
        <div class="loginbox">
            <!--登录前-->
            <c:choose>
                <c:when test="${empty sessionScope.USER.account}">
                    <p class="loginbtn"><a href="javascript:void(0)" id="login">登录</a></p>
                </c:when>
                <c:otherwise>
                    <p class="logoutbtn">欢迎您，<span
                            class="username">${sessionScope.USER.account}</span>当前数量：<span>${stoneCount}</span>个<a
                            href="javascript:void(0)" id="logout">【退出】</a></p>
                </c:otherwise>
            </c:choose>


            <!--登录后-->

        </div>
        <!--地址，明细，已兑换物品-->
        <ul class="list_list">
            <li>邮寄地址</li>
            <li class="on"> 明细</li>
            <li onClick="aa();">已兑换物品</li>
        </ul>
        <div class="conbox">
            <!--地址-->
            <div class="conbox01 address">
                <h1>邮寄地址</h1>

                <div class="formbox">
                    <form id="form1" name="form1" class="formaddress" method="post" action="${ctx}/demo/webmall/submit">
                        <div class="bin">
                            <p>姓名：</p>
                            <input class="name" id="truename" name="truename" type="text" placeholder="10字以内"
                                   maxlength="32"
                                   rule="{required:true,minlength:1,maxlength:10}"
                                   message="{required: '姓名不能为空。'}"/>
                                   <br/>
                            <span class="msg"></span>

                        </div>
                        <div class="bin">
                            <p>电话：</p><input class="phonenum" id="phone" name="phone" placeholder="11位手机号码" type="text"
                                             maxlength="11"
                                             rule="{required:true,phone:true,minlength:1,maxlength:11}"
                                             message="{required: '手机号不能为空。'}"/>
                                             <br/>
                            <span class="msg"></span>
                        </div>
                        <div class="bin">
                            <p>邮箱：</p><input class="email" id="email" name="email" type="text" placeholder="100字以内"
                                             maxlength="32"
                                             rule="{required:true,email:true,minlength:1,maxlength:100}"
                                             message="{required: '邮箱不能为空。'}"/>
                                             <br/>
                            <span class="msg"></span>
                        </div>
                        <div class="bin">
                            <p>邮寄地址：</p>
                            <select class="province" id="province" name="provinceName" rule="{required:true}"
                                    message="{required: '省份不能为空。'}">
                                <option value="">省份</option>
                            </select>
                            <input type="hidden" value="" name="province" id="provinceHidden"/>
                            <select class="city" id="city" name="cityName" rule="{required:true}"
                                    message="{required: '城市不能为空。'}">
                                <option value="">城市</option>
                            </select>
                            <input type="hidden" value="" name="city" id="cityHidden"
                                   rule="{required:true}">
                            <select class="city" id="country" name="countryName" message="{required: '地区不能为空。'}">
                                <option value="">地区</option>
                            </select>
                            <input type="hidden" value="" id="countryHidden" name="country" rule="{required:true}">
                            <input class="address01" name="address" id="address" type="text" placeholder="详细街道地址"
                                   maxlength="100"
                                   rule="{required:true,minlength:1,maxlength:100}"
                                   message="{required: '详细地址不能为空。'}"/>
                                   <br/>
                            <span class="msg"></span>

                        </div>
                        <p class="textmsg">* 请确保所填信息无误，我们将按此邮寄礼品。因地址错误寄送失败需您自行承担责任。</p>

                        <div class="submitbtn01">
                            <a href="javascript:void(0)" id="btn_submit">提交</a>
                        </div>
                    </form>
                </div>
            </div>
            <!--明细-->
            <div class="conbox01 detail">
                <ul class="ulcon" id="data">
                </ul>
                <div class="page" id="page"></div>
            </div>
        </div>

    </div>

</div>
<div class="footer">
</div>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
<script type="text/javascript" src="${ctx}/static/js/GetArea_new.js"></script>
<script type="text/javascript" src="${ctx}/static/js/Province_new_inland.js"></script>
<script type="text/javascript">
    function aa() {
        alert("敬请期待！");
    }
    $(function () {
        var _li = $(".list_list li");
        _li.click(function () {
            $(this).addClass("on").siblings().removeClass("on");
            var index = _li.index(this);
            $(".conbox .conbox01").eq(index).show().siblings().hide();
            if (index == 0) {
                initMyInfo();
            }
        });
        var type='${type}';
        if(!isEmpty(type)&&type==1){
            $(".list_list li").eq(0).addClass("on").siblings().removeClass("on");
            $(".conbox .conbox01").eq(0).show().siblings().hide();
        }
    });
</script>

<%--业务函数--%>
<script type="text/javascript">
    var username = "david3424";
    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };
    $(function () {
        initLoginLogout();
        initAddressEvent();
        initForm();
        initMyInfo();
    });
    var initAddressEvent = function () {
        GetProvince('document.form1', 'provinceName', 'cityName', 'countryName', '');
        $("#province").change(function () {
            GetCity('document.form1', 'provinceName', 'cityName', 'countryName', '');
            $("[name=province]").val($(this).find('option:selected').text());
        });
        $("#city").change(function () {
            GetCounty('document.form1', 'provinceName', 'cityName', 'countryName', '');
            $("[name=city]").val($(this).find('option:selected').text());
        });
        $("#country").change(function () {
            $("[name=country]").val($(this).find('option:selected').text());
        });
    };

    function login() {
    }
    function logout() {
    }
    function initLoginLogout() {
        $('#login').click(function () {
            login();
            return false;
        });
        $('#logout').click(function () {
            logout();
            return false;
        });
    }

    $("#myPrize").click(function () {
        if (isEmpty(username)) {
            login();
            return false;
        }
//        window.location.href = "/hex/webshop/view";
    });

    var initMyInfo = function () {
        if (isEmpty(username)) {
            return;
        }

        var url = "/demo/webmall/getUser";
        $.getJSON(url, {t: Math.random()}, function (json) {
            if (json.success) {
                $("#truename").val(json.bean.truename);
                $("#phone").val(json.bean.phone);
                $("#email").val(json.bean.email);
//                $("#province").val(json.bean.province);
                $("#provinceHidden").val(json.bean.province);
//                $("#city").val(json.bean.city);
                $("#cityHidden").val(json.bean.city);
//                $("#country").val(json.bean.country);
                $("#countryHidden").val(json.bean.country);
                $("#address").val(json.bean.address);
                $("#curAddress").html(json.bean.province + "  " +
                        json.bean.city + "  " + json.bean.country + "  " + json.bean.address);
                initSelect("province", json.bean.province);
                GetCity('document.form1', 'provinceName', 'cityName', 'countryName', '');
                initSelect("city", json.bean.city);
                GetCounty('document.form1', 'provinceName', 'cityName', 'countryName', '');
                initSelect("country", json.bean.country);
            }
        });
    }

    //下拉框选中方法
    var initSelect = function (id, reg_name) {
        $("#" + id + " option").each(function () {
            if ($(this).text() == reg_name) {
                $(this).attr("selected", "selected");
                return;
            }
        });
    }

    function initForm() {
        //验证
        var validator = $.wrapValidate('#form1', {
            errorPlacement: function (label, element) {
                var errorContainer = element.parent().find('.msg');
                label.appendTo(errorContainer);
            },
            submitHandler: function (form, event) {
                $(form).ajaxSubmit({
                    success: function (data) {
                        validator.resetForm();
                        $.simpleAlert({content: data.message, ok: function () {
                            location.reload();
                        }});
                    },
                    dataType: "json"
                });
            }
        });

        $('#form1').placeholder();
        //提交
        $("#btn_submit").click(function () {
            if (isEmpty(username)) {
                login();
                return false;
            }
            $('#form1').submit();
            return false;
        });
    }

</script>
<!--分页数据html模板开始-->
<script type="text/x-jquery-tmpl" id="tmpl">

        {{each(i,obj) records}}
              <li>
                <div class="left_li"><img src="http://event51.wanmei.com/hex/201507/moshi/images/baoshi.jpg"/></div>
                <div class="mid_li">
                    <h1> *{{= obj.score}}</h1>
                    <p><strong>来自：</strong>{{= obj.hdname}}</p>
                </div>
                <div class="rihgt_li">
                    <p class="height30"><strong>领取时间：</strong>{{= obj.createdate}}</p>
                </div>
              </li>
        {{/each}}

</script>
<script type="text/javascript">
    var paginationOptions = {
        url: "/demo/webmall/detailList",
        pageSize: 5,
        current_page: 0,
        params: {key: new Date().getTime()},
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",
        num_display_entries: 5,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: ""
//        afterLoad: initButton
    };

    $(function () {
        $.hd.pagination(paginationOptions);

    });

</script>
</body>
</html>


