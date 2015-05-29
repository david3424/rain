<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta name="author" content="Design:Yang.Chao; Web Layout:Liang.Weiming;" />
<meta name="keywords" content="笑傲江湖 xiaoao xa 笑傲 笑傲江湖官网 笑傲官网 网络游戏 武侠游戏 笑傲 笑傲官网 国产 中华武术 金庸 令狐冲 东方不败 田伯光 林平之 独孤九剑 辟邪剑谱 葵花宝典 武侠游戏 笑傲江湖活动 笑网老玩家 江湖 武侠 完美世界 自创武功 浪漫武侠 自创江湖" />
<meta name="description" content="《笑傲江湖OL》是完美世界以全新国际级引擎制作的大型武侠网游，游戏还囊括了养成、经营、团队角色扮演等经典元素，再现原著中恢弘与自由的江湖世界。" />
<link rel="Shortcut Icon" href="http://xa.wanmei.com/favicon.ico"/>
<link rel="bookmark" href="http://xa.wanmei.com/favicon.ico"/>
<title>《笑傲江湖OL》萝莉时装秀出来! -《笑傲江湖OL》官方网站</title>
<link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
<link href="/form.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="http://event51.wanmei.com/xa/201406/upimg/style/master2.css"/>
<link rel="stylesheet" type="text/css" href="http://www.wanmei.com/public/style/bottom/bottom2.css" id="wm_bottom_styleid">

</head>
<body>
<div id="wrap" class="bg2">
	<div id="top" class="fix">
    	<div class="share" id="share_line"></div>
    </div>
   	<div class="bg">
        <div id="header" class="headh">
            <div class="wm-public" logo="{name:'xa',skin:4,top:0,left:690}"></div>
        </div>
        <div class="main">
            <div class="file_push">
                <a href="javascript:void(0)" class="file_pic_btn"></a>
                <p><span id="user_msg"><b>游客</b>您好！</span> <a href="javascript:void(0)" id="login">登录</a> <a href="javascript:void(0)" id="logout">登出</a>
                &nbsp;
                <a href="http://bbs.xa.wanmei.com/forum.php?mod=viewthread&tid=356445" target="_blank"><font style="color:#F00; font-weight:bold;">获奖名单公布，点此查看&gt;&gt;</font></a>

                </p>
            </div>
            <div class="activity_info">
                <div class="info_left">

                </div>
                <div class="info_right">
                    <p class="ac_title">活动介绍</p>
                    <p><img src="http://event51.wanmei.com/xa/201406/upimg/images/date3.jpg" alt="" /></p>
                    <p class="ac_rule">活动奖励：</p>
                    <p><b>江湖奖</b>：投票数量前2名玩家<br>每人获得100点卡一张，并且每人一只限量版教主猫。</p>
                    <p><b>天下奖</b>：投票数量第3名至第5名玩家<br>每人获得笑傲江湖限量版超大鼠标垫</p>
                    <p><b>武林奖</b>：第6名至第10名玩家<br>每人笑傲胸针+教主猫一只</p>
                    <!--<p style="text-align:right;"><a href="javascript:void(0)" class="showbox">查看详情&gt;&gt;</a></p>-->
                    <p style="color:#FF0; font-weight:bold;">注：禁止同一ID刷票行为，每人只能获得一次奖励机会</p>
                </div>
            </div>
            <div class="activity_list">
                <div class="get_tab">
                    <span>排序</span>
                    <ul>
                        <li><a href="javascript:void(0)" orderby="1" style="color:#fff;">按时间</a></li>
                        <li><a href="javascript:void(0)" orderby="2">按人气</a></li>
                    </ul>
                </div>
                <div id="data"  class="tab_list_pic"></div>
                <div id="page" class="list_page"></div>
            </div>
            <div class="clr"></div>
        </div>
    </div>
</div>
<!-- 弹出层集合开始--> 
<!--left tanchuceng-->
<div class="zz_bg"></div>
<!--详细活动信息-->
<div class="box" style="display:none">
  <h2><a href="#" class="close"></a></h2>
  <div class="lay_info">
    <p class="lay_title">活动介绍</p>
    <p class="lay_con1">【活动时间】</p>
    <p>6月29日-7月29日</p>
    <p class="lay_con1">【活动形式】</p>
    <P>1、玩家上传笑傲游戏内截图(人物或风景截图);</P>
    <P>2、上传截图玩家，关注并@笑傲江湖online官方微博转发分享本次活动，并@3名好友。</P>
    <P class="lay_con1">【奖励】</P>
    <P>封顶奖：投票数量前三名（100元完美一卡通）</P>
    <P>江湖奖：第四名 第五名（笑傲江湖教主猫）</P>
    <P>参与奖：第五名 至 第十名（笑傲江湖专属鼠标垫）</P>
    <P>微博转发奖随机抽取5名：关注并@笑傲江湖OL 官方微博 转发@3个以上好友。</P>
    <P>活动结束后，公布获奖名单。</P>
    <P>活动中禁止使用非正常手段刷票，本活动最终解释权归笑傲江湖官网所有。</p>
  </div>
</div>
<!--详细活动信息--> 

<!--上传图片-->
<div class="box02" style="display:none">
  <h2><a href="#" class="close"></a></h2>
  <div class="lay_info">
    <p class="lay_title">上传图片</p>
    <form id="form1" method="post" action="${ctx}/xa/anniversary/save" class="frm">
      <ul style="padding-left: 75px;text-align: left;">
        <li> <span id="nickname">昵称：</span>
          <input name="nickname" type="text" placeholder=""
             maxlength="11"
             rule="{required:true,minlength:1,maxlength:11}"
             message="{required: '请填写您的昵称。'}"/>
          <span class="msg"></span> </li>
        <li><span>图片：</span>
          <input type="file" name="img" id="file" rule="{required:true}"
             message="{required: '请上传图片。'}" onChange="" style="color:#000;"/>
          <span class="msg"></span> </li>
      </ul>
      <div>注：图片格式为JPG，仅限1M以内。</div>
      <div class="box_btn" style="padding-left:125px; *+padding-left:0;"><a href="javascript:void(0);" id="btn_submit1"  class="pic_push">提交</a></div>
    </form>
  </div>
</div>
<!--上传图片--> 

<!--投票分享-->
<div class="box01" style="display:none">
  <h2><a href="#" class="close"></a></h2>
  <div class="lay_info">
    <p class="lay_title">投票成功</p>
    <p>感谢您的投票，您还可以与好友一起分享此活动。</p>
    <p class="sharebtn"><span>分享到：</span>
    <a href="javascript:wmShare.qqZone(wmShare.url, wmShare.title, 'http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg')" class="qqzone" title="qq空间"></a>
    <a href="javascript:wmShare.sinaT(wmShare.url, wmShare.title, 'http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg')" class="sina" title="新浪微博"></a>
    <a href="javascript:wmShare.qqT(wmShare.url, wmShare.title, 'http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg')" class="qq" title="qq微博"></a>
    <a href="javascript:wmShare.renren(wmShare.url)" class="renren" title="人人网"></a>
    <a href="javascript:wmShare.kaixin(wmShare.url, wmShare.title, 'http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg')" class="kaixin" title="开心网"></a>
    <a href="javascript:wmShare.douban(wmShare.url, wmShare.title, 'http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg')" class="douban" title="豆瓣网"></a>
    </p>
  </div>
</div>
<!--投票分享--> 
<!--left tanchuceng--> 

<!-- 弹出层集合结束--> 

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form2.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/serverlist.js"></script>
<script type="text/javascript" src="http://www.wanmei.com/public/js/wm.js"></script>
<script type="text/javascript" src="http://www.wanmei.com/public/js/swfobject.js "></script>

<script type="text/javascript">
        $(function() {
            initLoginAndLogout();//初始化登录
            initForm();//初始化表单
            checkLogin();
            paginationOptions.params.order=1;
            $.hd.pagination(paginationOptions);
        });

        var username = "${sessionScope.USER.account}";
        function login() {
            wanmei.passport.islogin({session:'USER',_false:function() {
                wanmei.passport.login({session:'USER',hdid:"xa_anniversary_status"});
            }});
        }
        function logout() {
            wanmei.passport.logout({session:'USER'})
        }
        //初始化表单
        function initLoginAndLogout() {
            if (username != "") {
                $("#login").hide();
                $('#user_msg').html('<b>'+username+'</b>您好！');
            } else {
                $("#logout").hide();
            }
            $("#login").click(function() {
                login();
            });
            $("#logout").click(function() {
                logout();
            });
        }
        //初始化表单

        var isFirstCommit=true;
        var nickname='';
        function checkLogin(){
            $.getJSON('${ctx}/xa/anniversary/checkLogin',{r:Math.random()},function(data){
                if(data.success){
                    isFirstCommit=false;
                    nickname=data.message;
                    $('#nickname').siblings().remove();
                    $('#nickname').html('昵称 :'+nickname);
                }
            });
        }


        function initForm() {
            //验证
            var validator = $.wrapValidate('#form1', {
                errorPlacement: function(label, element) {
                    var errorContainer = element.parent().find('.msg');
                    label.appendTo(errorContainer);
                },
                submitHandler: function(form, event) {
                    $(form).ajaxSubmit({
                        success: function(data) {
                            //var json=eval(data);
                            if (data.success) {
                                validator.resetForm();
                                $(".box01").css("display","none");
                                $(".zz_bg").css("display","none");
                                checkLogin();
                            }
                            $.simpleAlert(data.message);
                        },
                        dataType: "json"
                    });
                }
            });
            $('#form1').placeholder();
            //提交
            $("#btn_submit1").click(function() {
                if (username == "") {
                    $.simpleAlert({
                        content:"请先登录",
                        ok:function() {
                            login();
                        }
                    });
                    return false;
                }
                $('#form1').submit();
            });

        }

    function bindBalBtn(obj) {
                if (username=='') {
					//$(".box01").css("display","none");
                    login();
                    return;
                }
                var url = "${ctx}/xa/anniversary/ballot";
                $.getJSON(url, {id:$(obj).attr('bal_btn'),t:new Date().getTime()}, function(data) {
                    if (data.success) {
                         $('#bal_'+$(obj).attr('bal_btn')).find('b').html(parseInt($('#bal_'+$(obj).attr('bal_btn')).find('b').html())+1);
                        //$.simpleAlert(data.message);
                        $(".zz_bg").css({"display":"block","height":$(document).height()});
                        $(".box01").css({
                            left:Math.floor(($("body").width()-$(".box").width())/2)-20+"px",
                            top:Math.floor(($(window).height()-$(".box").height())/2)+"px",
                            display:"block"
                        });
                        $(".close").click(function(){
                            $(".box01").css("display","none");
                            $(".zz_bg").css("display","none");
                        });
						wmShare.pic="http://xa.wanmei.com/resources/jpg/140929/10071411976409310.jpg";
                    }else if(data.js&&data.message&&data.message!=''&&data.message!=null){
                        $.simpleAlert({
                            content:data.message,
                            ok:function() {
                                eval(data.js);
                            }
                        });
                    }else if(data.js){
                        eval(data.js);
                    }
                    else{
                        $.simpleAlert(data.message);
                    }
                });
    }
</script>


<!--分页数据html模板开始-->
<script type="text/x-jquery-tmpl" id="tmpl">

	<div class="list_pic">
      <ul>
	   {{each(i,obj) records}}
	       <li>
              <p class="img"><a href="http://img.event.wanmei.com{{= obj.image}}" rel="lightbox-tour"><img src="http://img.event.wanmei.com{{= obj.image}}" alt=""  class="showImage" /></a></p>
              <p class="username">昵称：{{= obj.nickname}}</p>
              <p class="vote_num" id="bal_{{= obj.id}}"><strong>票数：<b>{{= obj.ballot}}</b>票</strong></p>
              <p class="vote_btn"><a href="javascript:void(0)" class="vote_aa" bal_btn="{{= obj.id}}" onclick="bindBalBtn(this)">投票</a></p>
            </li>
		 {{/each}}	
	</ul>
	</div>	
</script>

<link rel="stylesheet" type="text/css" href="http://xa.wanmei.com/js/lightbox/jquery.lightbox-0.5.css" />
<script type="text/javascript" src="http://event51.wanmei.com/xa/201406/upimg/images/lightbox.js" ></script>
<script type="text/javascript">
    var paginationOptions = {
        url: "/xa/anniversary/page",
        pageSize: 12,
        current_page: 0,
        params:{r:Math.random()},
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",     
        num_display_entries: 10,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: "",
		afterLoad: function(){
			setTimeout(function(){
			$('[rel="lightbox-tour"]').lightBox();},1000)
				//////vote
		}
    };

    $('[orderby]').click(function(){
        var order=$(this).attr('orderby');
        paginationOptions.params.order=order;
        $.hd.pagination(paginationOptions);
    })
</script>


<script type="text/javascript">
    $(function(){
////tanchuceng
        $(".showbox").click(function(){
            $(".zz_bg").css({"display":"block","height":$(document).height()});
            $(".box").css({
                left:Math.floor(($("body").width()-$(".box").width())/2)-20+"px",
                top:Math.floor(($(window).height()-$(".box").height())/2)+"px",
                display:"block"
            });
        });
        $(".close").click(function(){
            $(".box ").css("display","none");
            $(".zz_bg").css("display","none");
        });

//////pic push
        $(".file_pic_btn").click(function(){
            if (username=='') {
                login();
                return;
            }
            $(".zz_bg").css({"display":"block","height":$(document).height()});
            $(".box02").css({
                left:Math.floor(($("body").width()-$(".box").width())/2)-20+"px",
                top:Math.floor(($(window).height()-$(".box").height())/2)+"px",
                display:"block"
            });
        });
        $(".close").click(function(){
            $(".box02").css("display","none");
            $(".zz_bg").css("display","none");
        });

        var _li=$(".get_tab ul li");
        _li.click(function(){
            $(this).children("a").css({"color":"#fff"}).end().siblings().children("a").css({"color":"#5f2223"});
        });
    });
</script>
<script type="text/javascript" src="http://www.wanmei.com/public/js/wmshare.js" share="{type:'lineNav', game:'xa'}"></script>
<script>
 wmShare.title = "#《笑傲江湖OL》萝莉时装秀出来!# 为了回馈广大玩家的支持，一大波教主猫、加厚超大限量版鼠标垫、笑傲胸针、点卡，正在来袭！！奖品多多，动动手指就有机会获得。http://event.wanmei.com/xa/anniversary/index"
</script>
<div class="wm-public none" analytics="{single:'xa201407love3824'}"></div>
</body>
</html>
