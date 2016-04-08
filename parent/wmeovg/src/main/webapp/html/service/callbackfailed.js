 function recallback( gid){
		  
		  Ext.Ajax.request({url:'/wmeovg/service/queueBrowser_recallbackByGid.action',
			params:{'gid':gid},
	      	success:function(result){
				switch(parseInt(result.responseText)){
			 		case 0:
			 			Ext.example.msg('提示', '发送回调成功'); break;
			 		case 1:
			 			Ext.example.msg('提示', '发送回调失败，请重试。'); break;
			 		default:
			 			Ext.example.msg('提示', '发送回调失败，请重试。'); break;
			 	}
				var grid = Ext.getCmp('prize-recallback-grid');
				grid.getStore().reload();
			}
		  });
		  
	  }
 function loadDetail( gid){
	 	var detailWin = Ext.getCmp('prize-recallback-detail-win');
	 	detailWin.removeAll();
		  
		  Ext.Ajax.request({url:'/wmeovg/service/prizelog_get.action',
			params:{'entity.gid':gid},
	      	success:function(result){
	    		 var prizelog = Ext.decode(result.responseText);
	    		 detailWin.add({fieldLabel:'流水号', xtype:'textfield',value: prizelog.gid, width:400});
	    		 
	    		 detailWin.add({
	    			 xtype:'panel',
	    			 width:500,
	    			 layout:'column',
	    			 plain:true,
	    			 baseCls:'x-plain',
	    			 items:[{
	    				 columnWidth:.4,
	    				 layout:'form',
	    				 baseCls:'x-plain',
	    				 defaults:{xtype:'textfield', width:140},
	    				 labelWidth:50,
	    				 labelAlign:'right',
	    				 items:[{fieldLabel:'客户端',value: prizelog.cid},
	    				        {fieldLabel:'应用', value: prizelog.appid},
	    				        {fieldLabel:'服务器', value: prizelog.zoneid},
	    				        {fieldLabel:'账号',value: prizelog.account},
	    				        {fieldLabel:'角色名',value: prizelog.rolename},
	    				        {fieldLabel:'物品ID', value: prizelog.prizeid},
	    				        {fieldLabel:'个数', value: prizelog.count},
	    				        {fieldLabel:'优先级', value: prizelog.priority},
	    				        {fieldLabel:'IP', value: prizelog.ip}]
	    			 },{
	    				 columnWidth:.6,
	    				 layout:'form',
	    				 baseCls:'x-plain',
	    				 defaults:{xtype:'textfield', width:190},
	    				 labelWidth:60,
	    				 labelAlign:'right',
	    				 items:[
	    				        {fieldLabel:'请求时间', value: prizelog.requestTime},
	    				        {fieldLabel:'发送时间', value: prizelog.sendTime},
	    				        {fieldLabel:'发送状态', value: prizelog.sendStatus},
	    				        {fieldLabel:'回调时间', value: prizelog.callbackTime},
	    				        {fieldLabel:'回调状态', value: prizelog.callbackHttpStatus},
	    				        {fieldLabel:'回调参数',labelStyle:'margin-top:40px', xtype:'textarea', value: prizelog.callback, height:103}]
	    			 }]
	    		 });
	    		 var grid = Ext.getCmp('prize-recallback-grid');
	    		 detailWin.show().getEl().center(grid.getEl());
	    		 grid.getEl().mask();
			}
		  });
		  
	  }
Ext.onReady(function(){
	
	function PageList(divPrefix){
		
		var instance = this;
	    this.gridId = divPrefix + "-grid";
	    this.editFormId = divPrefix + "-editForm";
	    
	    this.reader = new Ext.data.JsonReader({},[{name:'bean.id', type:'int', mapping:'id'},{name:'bean.content', mapping:'content',type:'string'}]);
			this.editForm = new Ext.form.FormPanel({
				renderTo: this.editFormId,
				frame:true,
				//baseCls: 'x-plain',
				border:false,
				defaults:{xtype:'textfield',width:300},
				layout:'fit',
				items:[{
					hideLabel:true,
					name:'bean.content',
					xtype:'displayfield',
					height:200
				}],
				reader:this.reader
				
			});
			this.editWin = new Ext.Window({
				title:'查看详细',
				renderTo:Ext.getBody(),
				layout:'fit',
				plain:true,
				modal:true,
				width:400,
				autoHeight:true,
				resizable:false,
				closeAction:'hide',
				border:false,
				items: [this.editForm]
			});
			function loadUpdate(id){
		    		instance.editForm.load({
		    			url:'/wmeovg/sys/log_get.action',
		    			params:{id:id},
		    			success:function(){
		    				
		    				instance.editWin.show();
		    			}
		    		});
			}
		this.store = new Ext.data.JsonStore({
			root:'records',
			totalProperty:'totalCount',
			idProperty:'id',
			remoteSort:true,			
			fields:['gid', 'cid', 'appid', 'prizeid' , 'sendtime'],	        
	        url:'/wmeovg/service/queueBrowser_findDlqCallbackFailedList.action',
	        baseParams: {limit:25}
	    });
		
		function renderRecallback(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return ("<a href='javascript:void(0)' onclick=\"recallback('"+ record.get("gid") +"')\">重新回调</a>");
		}
		function renderDetail(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return ("<a href='javascript:void(0)' onclick=\"loadDetail('"+ record.get("gid") +"')\">详细</a>");
		}
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                	              {header:'流水号',dataIndex:'gid',width:80},
		                	              {header:'客户端',dataIndex:'cid',width:30},
		                	              {header:'所属应用',dataIndex:'appid',width:20},
		                	              {header:'物品ID', dataIndex:'prizeid',width:20},
		                	              {header:'发送时间', dataIndex:'sendtime',width:30},
		                	              {header:'详细', dataIndex:'',renderer:renderDetail,width:10},
		                	              {header:'重新回调', dataIndex:'',renderer:renderRecallback,width:20}]);
		
		this.grid = new Ext.grid.GridPanel({
			id:'prize-recallback-grid',
			renderTo:this.gridId,
			height:609,
			width:900,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'content',
		    cm: this.cm ,
		    viewConfig: {
		        forceFit: true
		    },
		    sm: this.sm,
		    tbar:['流水号:',
	            new Ext.ux.form.SearchField({
	                store: this.store,
	                width:320
	            })],
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有记录"
	        }),
	        listeners:{
		    	cellclick:function(grid, rowIndex, columnIndex, e){

		    		if(columnIndex == 0 ){
			    		var record = grid.getStore().getAt(rowIndex);
			    		loadUpdate(record.get("id"));
		    		}
		    	},
		    	render:function(grid){
		    		var view = grid.getView();
		    		grid.tip = new Ext.ToolTip({
		    			target:view.mainBody,
		    			delegate:'.x-grid3-row',
		    			trackMouse:true,
		    			renderTo: document.body,
		    			listeners:{
		    				beforeshow:function updateTipBody(tip){
		    			
		    					tip.body.dom.innerHTML = "单击第一列查看详细";
		    				}
		    			}
		    		});
		    	}
		    }
		});
		
		this.detailWin = new Ext.Window({
		 	id:'prize-recallback-detail-win',
		    renderTo:this.grid.getEl(), 
		  	title:'查看详细',
			layout:'fit',
			width:500,
			autoHeight:true,
			//height:400,
			plain:true,
			bodyStyle:'padding-top:10px; padding-left:10px',
			resizable:false,
			closable:false,
			layout:'form',
			labelWidth:50,
			labelAlign:'right',
			items: [],
			buttons:[{
				text:'关闭',
				handler:function(){
					instance.detailWin.hide();
					instance.grid.getEl().unmask();
				}
			}],
			buttonAlign:'center'
	  });
	}
	
	var logList = new PageList('callbackfailed');
	logList.store.load({params:{start:0, limit:25}});
});