Ext.BLANK_IMAGE_URL = "/wmeovg/js/ext/resources/images/default/s.gif";

Ext.onReady(function() {	
	  	Ext.QuickTips.init(); 
	  	
	 // 登陆框
		new Ext.Window({
			id:'userlogin-window',
			title:'没有登陆或登陆超时',
			iconCls: 'exclamation-icon',
			renderTo:Ext.getBody(),
			width:300,
			height:160,
			modal:true,
			bodyStyle:'padding-top:50px; padding-left:35px',
			layout:'fit',
			closable:false,
			resizable:false,
			draggable :false,
			items:[{
				xtype:'label',
				html:'您没有登陆或登陆超时，请<a href="/wmeovg">重新登陆</a>'
			}]
		});
		window.onresize = function(){
			Ext.getCmp('userlogin-window').center(Ext.getBody());
		};
	  	Ext.Ajax.on('requestcomplete', function(conn, response, options){
	  		
	  		try{
		  		var result = Ext.decode(response.responseText);
		  		if(result.sessionstatus && result.sessionstatus == 'timeout'){
		  			Ext.getCmp('userlogin-window').show();
		  		}
	  		}catch(ex){
	  			
	  		}
	  	}, this);
	  	
	  	var clock = new Ext.Toolbar.TextItem('');

		var tabs =  new Ext.TabPanel({
			id:'tab-panel',
			region:'center',
			//baseCls:'tab-background',
			deferredRender:false,
			enableTabScroll:true,
			plugins:new Ext.ux.TabCloseMenu(),
			bbar:new Ext.ux.StatusBar({
                id: 'time-status',
                items: [clock]
            }),
	        listeners: {
	            render: {
	                fn: function(){
			
	                    Ext.fly(clock.getEl().parent()).addClass('x-status-text-panel').createChild({cls:'spacer'});

	                    // Kick off the clock timer that updates the clock el every second:
					    Ext.TaskMgr.start({
					        run: function(){
					            Ext.fly(clock.getEl()).update(new Date().format('Y年m月d日 G:i:s'));
					        },
					        interval: 1000
					    });
	                },
	                delay: 100
	            }
	        }
	    });
		
		// 单击功能，添加tab
		var addTab = function(node) {
			var tab = tabs.getComponent(node.id);
			if(!tab){
				var tab = tabs.add({
					id:node.id,
					title: node.text, 
					//baseCls:'tab-background',
					autoLoad:{url:'/wmeovg/' + node.attributes.url,scripts:true},
					closable:true,
					autoScroll:true});
			}else{
				tab.getUpdater().refresh();
			}
			tabs.activate(tab);
        };
        
        // 初始化个人菜单
        Ext.Ajax.request({url:'/wmeovg/sys/tree.action',
        	success:function(result){
        	var  treeArray= new Array();
        	var json = Ext.decode(result.responseText);
        	for(var i=0; i<json.length; i++){
        		var menu = json[i];
        		
        		var tree = new Ext.tree.TreePanel({
        			title: menu.text,
        			//baseCls:'tab-background',
        			collapsible:true,
        	        titleCollapse:true,
        	        loader: new Ext.tree.TreeLoader({dataUrl:'/wmeovg/sys/node.action'}),
        	        root: new Ext.tree.AsyncTreeNode({
        	        	id: menu.id,
        	        	expanded: true
        	        }),
        	        rootVisible: false,
        	        tools:[{
        	               id:'refresh',
        	               qtip:'刷新',
        	               handler:function(event, el, tree, tc){
        	        		   tree.body.mask("加载中,请稍后...");
        	        		   
        	                   tree.root.reload(function(callback){
        	                	   tree.body.mask().remove();
        	                   });
        	               }
        	           }]
        		});
        		tree.on('click', function(node){
        			if(!node.isLeaf()){
                		if(node.isExpanded())
                			node.collapse();
                		else
                		node.expand();
                	}else{
                		if(node.attributes.target == "_blank")
                			window.top.location.href = '/wmeovg/' + node.attributes.url;
                		else
                			addTab(node);
                	}
                });

        		treeArray.push(tree);
        	}
        	
        	var welcome = new Ext.Toolbar.TextItem('');
        	
        	var viewport = new Ext.Viewport( {
    			layout : 'border',
    			items : [ {
    				region : 'north',
    				contentEl : 'north-div',
    				//bodyStyle:"background: url('/wmeovg/images/banner_background.png') repeat-x;",
    				border : true,
    				height : 80
    			},{
    				id:'user-treePanel',
    		        region: 'west',
    		        contentEl : 'west-div',
    		        collapsible: true,
    		        collapseMode:'mini',
    		        hideCollapseTool :true,
    		        //title: '活动开发平台',
    		        layout:'accordion',
    		        width: 200,
    		        minSize : 200,
    		        maxSize : 250,
    		        autoScroll: true,
    		        split: true,
    		        items:treeArray,
        		    tbar:[welcome,'->',{
        		    	//text:'刷新',
        		    	iconCls:'refresh',
        		    	handler:function(){
        		    		location.href='/wmeovg/index.html';
        		    	}
        		    }],
        	        listeners: {
        	            render: {
        	                fn: function(){
						    	Ext.Ajax.request({url:'/wmeovg/sys/user_getCurrentUser.action',
			    	            	success:function(result){
						    		 Ext.fly(welcome.getEl()).update('<font color=#28488d>&nbsp;&nbsp;欢迎,&nbsp;&nbsp;'+ result.responseText + "</font>");
			    	    		}
			    	    		});
        	                }
        	            },
    	                delay: 100
        	        }}, tabs
    		    ]
        	});	
        	
        	
        }
        });
	});