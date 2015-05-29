(function(e){
    var e = wms = {
        currentState : false ,
        currentNum : 0,
        box : "#wrap ,.main",
        main : "#main",
        navBtn : ".nav li a",
        win : function(){return $(window).height()},
        mainLength : function(){return $(".main").length-1},
        windSize : function(){
            var self = this,
                box = $(self.box);
            box.height(self.win);
        },
        mouseWheel : function(){
            var self = this,
                height = self.mainLength() * self.win();
            $(document).on("mousewheel" , function(event){
                if (event.deltaY === 1) {
                    if(self.currentNum <= 0 || self.currentState) {return false}
                    self.rolling($(self.main), self.win() * (--self.currentNum));
                    self.navBtnClass(self.currentNum);
                } else if(event.deltaY === -1){
                    if(self.currentNum >= self.mainLength() || self.currentState) {return false}
                    self.rolling($(self.main), self.win() * (++self.currentNum));
                    self.navBtnClass(self.currentNum);
                }
            });
        },
        rolling : function(box, y){
            var self = this;
            self.currentState = true;
            box.animate({
                "top" : -y
            },400,function(){
                self.currentState = false;
            });
        },
        imgShow : function(id){
            if (!id) return false;
            var box = $(id);
            box.hover(function(){
                $(this).find("div").stop().slideToggle();
            },function(){
                $(this).find("div").stop().slideToggle();
            });
        },
        valignCenet : function(id){
            if (!id) return false;
            var box = $(id),
                self = this;
                isH = $(".main").outerHeight() < box.outerHeight();
            if (!isH) {
                box.css({"margin-top":($(".main").outerHeight() - box.outerHeight()) /2});
            } else {
                box.css({"margin-top":0});
            }
        },
        tab : function(btn , event , box , lr ,fn){
            if (!btn) return false;
            var index = 0,
                btn = $(btn),
                thisEvent = event || "click",
                getBox = function(){
                    return btn.parent().next().attr("class");
                },
                thisBox = box || ("." + getBox());
            btn.bind(thisEvent , function(event){
                if(lr){
                    var thisId = $(this).attr("id");
                    if (thisId == "left") {
                        if(index === 0) return false;
                        index--;
                    } else if (thisId == "right") {
                        if(index == $(thisBox).length) return false;
                        index++;
                    }
                } else {
                    index = btn.index(this);
                    $(this).addClass("on").siblings().removeClass("on");
                }
                $(thisBox).eq(index).stop().fadeIn().siblings(thisBox).stop().fadeOut();
                if (fn && typeof fn == "function") fn(index , $(this).text());
            });
        },
        navBtnClass : function(i){
            var self = this,
                btn = $(self.navBtn);
                btn.removeClass("on");
                btn.eq(i).addClass("on");
        },
        navBtnEvent : function(){
            var self = this,
                btn = $(self.navBtn);
            btn.each(function(i){
                $(this).click(function(){
                    self.currentNum = i;
                    self.rolling($(self.main), self.win() * self.currentNum);
                    self.navBtnClass(i);
                });
            });
        }
    }
})(this)