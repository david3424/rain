Array.prototype.remove = function (dx) {
    if (isNaN(dx) || dx > this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i]
        }
    }
    this.length -= 1
}
;
(function ($) {
    $.simpleDialog = function (a) {
        var options = {};
        options.skin = true;
        if (typeof a == 'string') {
            if (jQuery(a).length == 0) {
                options.content = '<div>' + a + '</div>'
            } else {
                options.content = a
            }
        } else if (typeof a == 'object') {
            if (a instanceof jQuery || (a.nodeType && a.nodeType == 1)) {
                options.content = a
            } else {
                options = $.extend(options, a)
            }
        }
        var tempFn = options.afterHide || function () {
        };
        options.afterHide = function () {
            //$.simpleDialog.queue.pop();
            var q = $.simpleDialog.queue;
            for (var i = 0; i < q.length; i++) {
                if (this.boxyId == q[i].boxyId) {
                    q.remove(i);
                    break;
                }
            }
            tempFn()
        }
        var dialog = new Boxy(options.content, options);
        $.simpleDialog.queue.push(dialog);
        return dialog
    }
    $.simpleAlert = function (arg) {
        var options = {};
        options.content = arg.content || '提示内容';
        if (typeof arg == 'string') {
            options.content = arg;
        }
        options.title = arg.title || '提示';
        options.ok = arg.ok || function () {
        };
        options.okText = arg.okText || '确定';
        options.icon = arg.icon || 'ico_ok';
        options.afterHide = arg.afterHide || function () {
        };
        var html = '<div class="boxy-tip">\
						  	<div class="msg"><table><tr><td><span class="' + options.icon + '"></span></td><td>' + options.content + '</td></tr></table></div>\
								<div class="btn"><button class="btn_ok" action-type="ok" type="button"><span>' + options.okText + '</span></button></div>\
						  </div>';
        return new $.simpleDialog({
            title: options.title,
            content: html,
//            afterHide: function () {
//                options.afterHide();
//            },
            afterShow: function () {
                var self = this;
                var okbtn = $('[action-type=ok]', this.getInner());
                okbtn.focus().click(function () {
                    self.hide();
                    options.ok();
                });
            }
        });
    }
    $.simpleConfirm = function (options) {
        options = $.extend({
            title: '提示',
            content: '提示内容',
            ok: function () {
            },
            cancel: function () {
            },
            okText: '确定',
            noText: '取消',
            icon: 'ico_ok'
        }, options || {});
        var html = '<div class="boxy-tip">\
						  	<div class="msg"><table><tr><td><span class="' + options.icon + '"></span></td><td>' + options.content + '</td></tr></table></div>\
								<div class="btn"><button class="btn_ok" action-type="ok" type="button"><span>' + options.okText + '</span></button><button class="btn_cancel" action-type="cancel" type="button"><span>' + options.noText + '</span></button></div>\
						  </div>';
        return new $.simpleDialog({
            title: options.title,
            content: html,
            afterShow: function () {
                var self = this;
                var okbtn = $('[action-type=ok]', this.getInner());
                var nobtn = $('[action-type=cancel]', this.getInner());
                okbtn.focus().click(function () {
                    self.hide();
                    options.ok();
                });
                nobtn.click(function () {
                    self.hide();
                    options.cancel();
                });
            }
        });

    }
    $.simpleDialog.queue = [];
//待修改
    $.simpleDialog.get = function (id) {
        var q = $.simpleDialog.queue;
        for (var i = 0; i < q.length; i++) {
            if (q[i].boxyId == id) return q[i]
        }
        return null
    }
    $.simpleDialog.close = function (id) {
        var q = $.simpleDialog.queue;
        var dlg = q[q.length - 1];
        if (id) dlg = $.simpleDialog.get(id);
        dlg && dlg.hide();
    }
    $.simpleDialog.closeAll = function () {
        var q = $.simpleDialog.queue;
        for (var i = 0; i < q.length; i++) {
            q[i].hide();
            --i
        }
    }
})(jQuery);

function Boxy(element, options) {
    var $ = jQuery;
    this.boxy = jQuery('<div class="boxy_layer"><table><tr><td node-type="inner"></td></tr></table></div>');
    $.data(this.boxy[0], 'boxy', this);
    this.visible = false;
    this.options = jQuery.extend({},
        Boxy.DEFAULTS, options || {});
    this.boxyId = this.options.id || ('boxy_' + (+new Date()));
    if (this.options.skin) {
        $(Boxy.WRAPPER).appendTo(this.getInner())
    } else {
        $('<div node-type="content"></div>').appendTo(this.getInner())
    }
    this.setContent(element);
    this._setupTitleBar();
    this.boxy.css('display', 'none').appendTo(document.body);
    this.toTop();
    if (jQuery.browser.msie && jQuery.browser.version < 7) {
        this.options.fixed = false
    }
    if (this.options.fixed) {
        this.boxy.css('position', 'fixed')
    } else {
        this.boxy.css('position', 'absolute')
    }
    if (this.options.center && Boxy._u(this.options.x, this.options.y)) {
        this.center()
    } else {
        this.moveTo(Boxy._u(this.options.x) ? this.options.x : Boxy.DEFAULT_X, Boxy._u(this.options.y) ? this.options.y : Boxy.DEFAULT_Y)
    }
    if (this.options.show) this.show()
}
;
Boxy.EF = function () {
};
jQuery.extend(Boxy, {
    WRAPPER: '<div class="boxy-wrap">\
							<div class="boxy-inner">\
								<div class="boxy-title" node-type="draghandler">\
									<h3 node-type="title"></h3>\
									<a node-type="close" href="#" class="boxy-close">关闭</a>\
								</div>\
								<div class="boxy-content" node-type="content"></div>\
							</div>\
						</div>',
    DEFAULTS: {
        skin: true,
        title: null,
        esc: true,
        draggable: true,
        clone: false,
        center: true,
        show: true,
        modal: true,
        fixed: true,
        unloadOnHide: true,
        clickToFront: false,
        behaviours: Boxy.EF,
        afterDrop: Boxy.EF,
        afterShow: Boxy.EF,
        afterHide: Boxy.EF,
        beforeUnload: Boxy.EF
    },
    DEFAULT_X: 50,
    DEFAULT_Y: 50,
    zIndex: 1337,
    dragConfigured: false,
    resizeConfigured: false,
    dragging: null,
    get: function (ele) {
        var p = jQuery(ele).parents('.boxy-wrapper');
        return p.length ? jQuery.data(p[0], 'boxy') : null
    },
    linkedTo: function (ele) {
        return jQuery.data(ele, 'active.boxy')
    },
    isModalVisible: function () {
        return jQuery('.boxy-modal-blackout').length > 0
    },
    _u: function () {
        for (var i = 0; i < arguments.length; i++) if (typeof arguments[i] != 'undefined') return false;
        return true
    },
    _handleResize: function (evt) {
        var d = jQuery(document);
        jQuery('.boxy-modal-blackout,.boxy-modal-iframe').css('display', 'none').css({
            width: d.width(),
            height: d.height()
        }).css('display', 'block')
    },
    _handleDrag: function (evt) {
        var d;
        if (d = Boxy.dragging) {
            d[0].boxy.css({
                left: evt.pageX - d[1],
                top: evt.pageY - d[2]
            })
        }
    },
    _nextZ: function () {
        return Boxy.zIndex++
    },
    _viewport: function () {
        var d = document.documentElement,
            b = document.body,
            w = window;
        return jQuery.extend(jQuery.browser.msie ? {
            left: b.scrollLeft || d.scrollLeft,
            top: b.scrollTop || d.scrollTop
        } : {
            left: w.pageXOffset,
            top: w.pageYOffset
        },
            !Boxy._u(w.innerWidth) ? {
                width: w.innerWidth,
                height: w.innerHeight
            } : (!Boxy._u(d) && !Boxy._u(d.clientWidth) && d.clientWidth != 0 ? {
                width: d.clientWidth,
                height: d.clientHeight
            } : {
                width: b.clientWidth,
                height: b.clientHeight
            }))
    }
});
Boxy.prototype = {
    estimateSize: function () {
        this.boxy.css({
            visibility: 'hidden',
            display: 'block'
        });
        var dims = this.getSize();
        this.boxy.css('display', 'none').css('visibility', 'visible');
        return dims
    },
    getSize: function () {
        return [this.boxy.width(), this.boxy.height()]
    },
    getContentSize: function () {
        var c = this.getContent();
        return [c.width(), c.height()]
    },
    getPosition: function () {
        var b = this.boxy[0];
        return [b.offsetLeft, b.offsetTop]
    },
    getCenter: function () {
        var p = this.getPosition();
        var s = this.getSize();
        return [Math.floor(p[0] + s[0] / 2), Math.floor(p[1] + s[1] / 2)]
    },
    getInner: function () {
        return jQuery('[node-type=inner]', this.boxy)
    },
    getContent: function () {
        return jQuery('[node-type=content]', this.boxy)
    },
    setContent: function (newContent) {
        newContent = jQuery(newContent);
        this.element = newContent;
        if (this.options.clone) newContent = newContent.clone(true);
        if (newContent.parent().parent().size() > 0) {
            this._beforeElem = $('<span></span>').css('display', 'none');
            newContent.before(this._beforeElem)
        }
        newContent.css({
            display: 'block'
        });
        this.getContent().empty().append(newContent);
        this._setupDefaultBehaviours(newContent);
        this.options.behaviours.call(this, newContent);
        return this
    },
    moveTo: function (x, y) {
        this.moveToX(x).moveToY(y);
        return this
    },
    moveToX: function (x) {
        if (typeof x == 'number') this.boxy.css({
            left: x
        });
        else this.centerX();
        return this
    },
    moveToY: function (y) {
        if (typeof y == 'number') this.boxy.css({
            top: y
        });
        else this.centerY();
        return this
    },
    centerAt: function (x, y) {
        var s = this[this.visible ? 'getSize' : 'estimateSize']();
        if (typeof x == 'number') this.moveToX(x - s[0] / 2);
        if (typeof y == 'number') this.moveToY(y - s[1] / 2);
        return this
    },
    centerAtX: function (x) {
        return this.centerAt(x, null)
    },
    centerAtY: function (y) {
        return this.centerAt(null, y)
    },
    center: function (axis) {
        var v = Boxy._viewport();
        var o = this.options.fixed ? [0, 0] : [v.left, v.top];
        if (!axis || axis == 'x') this.centerAt(o[0] + v.width / 2, null);
        if (!axis || axis == 'y') this.centerAt(null, o[1] + v.height / 2);
        return this
    },
    centerX: function () {
        return this.center('x')
    },
    centerY: function () {
        return this.center('y')
    },
    resize: function (width, height, after) {
        if (!this.visible) return;
        var bounds = this._getBoundsForResize(width, height);
        this.boxy.css({
            left: bounds[0],
            top: bounds[1]
        });
        this.getContent().css({
            width: bounds[2],
            height: bounds[3]
        });
        if (after) after(this);
        return this
    },
    tween: function (width, height, after) {
        if (!this.visible) return;
        var bounds = this._getBoundsForResize(width, height);
        var self = this;
        this.boxy.stop().animate({
            left: bounds[0],
            top: bounds[1]
        });
        this.getContent().stop().animate({
                width: bounds[2],
                height: bounds[3]
            },
            function () {
                if (after) after(self)
            });
        return this
    },
    isVisible: function () {
        return this.visible
    },
    show: function () {
        if (this.visible) return;
        if (this.options.modal) {
            this.showModal()
        }
        this.boxy.stop().css({
            opacity: 1
        }).show();
        this.visible = true;
        this._fire('afterShow');
        return this
    },
    showModal: function () {
        var self = this;
        /*if (!Boxy.resizeConfigured) {
         Boxy.resizeConfigured = true;
         jQuery(window).resize(function() {
         Boxy._handleResize()
         })
         }*/
        var zindex = Boxy._nextZ();
        this.modalBlackout = jQuery('<div class="boxy-modal-blackout"></div>').css({
            position: 'absolute',
            backgroundColor: '#000',
            left: 0,
            top: 0,
            padding: 0,
            margin: 0,
            zIndex: zindex,
            opacity: 0.3,
            width: '100%',//jQuery(document).width(),
            height: jQuery(document).height()
        }).appendTo(document.body);
        if ($.browser.msie && jQuery.browser.version < 7) {
            this.modalIframe = $('<iframe class="boxy-modal-iframe" frameborder="0" scrolling="no" src="about:blank"></iframe>').css({
                position: 'absolute',
                left: 0,
                top: 0,
                opacity: 0,
                zIndex: zindex,
                width: '100%',//jQuery(document).width()
                height: jQuery(document).height()
            }).insertBefore(this.modalBlackout)
        }
        this.toTop();
        if (this.options.esc) {
            jQuery(document.body).bind('keypress.boxy',
                function (evt) {
                    var key = evt.which || evt.keyCode;
                    if (key == 27) {
                        self.hide();
                        jQuery(document.body).unbind('keypress.boxy')
                    }
                })
        }
    },
    hide: function (after) {
        if (!this.visible) return;
        var self = this;
        if (this.options.modal) {
            jQuery(document.body).unbind('keypress.boxy');
            this.modalBlackout.hide().remove();
            if ($.browser.msie && jQuery.browser.version < 7) {
                this.modalIframe.remove()
            }
        }
        self.boxy.css({
            display: 'none'
        });
        self.visible = false;
        self._fire('afterHide');
        if (after) after(self);
        if (self.options.unloadOnHide) self.unload();
        return this
    },
    toggle: function () {
        this[this.visible ? 'hide' : 'show']();
        return this
    },
    hideAndUnload: function (after) {
        this.options.unloadOnHide = true;
        this.hide(after);
        return this
    },
    unload: function () {
        this._fire('beforeUnload');
        if (this._beforeElem && this._beforeElem.length > 0) {
            this._beforeElem.replaceWith(this.element);
            this.element.hide()
        }
        this.boxy.remove()
    },
    toTop: function () {
        this.boxy.css({
            zIndex: Boxy._nextZ()
        });
        return this
    },
    getTitle: function () {
        return jQuery('[node-type=title]', this.boxy)
    },
    setTitle: function (t) {
        this.getTitle().html(t);
        return this
    },
    _getBoundsForResize: function (width, height) {
        var csize = this.getContentSize();
        var delta = [width - csize[0], height - csize[1]];
        var p = this.getPosition();
        return [Math.max(p[0] - delta[0] / 2, 0), Math.max(p[1] - delta[1] / 2, 0), width, height]
    },
    _setupTitleBar: function () {
        if (this.options.title) {
            this.setTitle(this.options.title);
            this._setupDefaultBehaviours(tb)
        }
        if (this.options.draggable) {
            var self = this;
            var tb = $('[node-type=draghandler]', this.boxy);
            if (tb.length == 0) return;
            tb[0].onselectstart = function () {
                return false
            }
            tb[0].unselectable = 'on';
            tb[0].style.MozUserSelect = 'none';
            if (!Boxy.dragConfigured) {
                jQuery(document).mousemove(Boxy._handleDrag);
                Boxy.dragConfigured = true
            }
            tb.css('cursor', 'move');
            tb.mousedown(
                function (evt) {
                    self.toTop();
                    Boxy.dragging = [self, evt.pageX - self.boxy[0].offsetLeft, evt.pageY - self.boxy[0].offsetTop];
                    jQuery(this).addClass('dragging')
                }).mouseup(function () {
                    jQuery(this).removeClass('dragging');
                    Boxy.dragging = null;
                    self._fire('afterDrop')
                })
        }
    },
    _setupDefaultBehaviours: function () {
        var self = this;
        if (this.options.clickToFront) {
            this.boxy.click(function () {
                self.toTop()
            })
        }
        jQuery('[node-type=close]', this.boxy).click(
            function () {
                self.hide();
                return false
            }).mousedown(function (evt) {
                evt.stopPropagation()
            })
    },
    _fire: function (event) {
        this.options[event].call(this)
    }
};