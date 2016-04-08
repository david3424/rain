 function loadGoodsDetail(id){

	 var detailWin = Ext.getCmp('goods-detail-win');
	 	detailWin.removeAll();
		  
		  Ext.Ajax.request({url:'/wmeovg/service/goodslog_get.action',
			params:{'entity.id':id},
	      	success:function(result){
	    		 var goodslog = Ext.decode(result.responseText);
	    		 detailWin.add({fieldLabel:'流水号', labelStyle:'width:62px', xtype:'textfield',value: goodslog.gid, width:400});
	    		 
	    		 var roleField = new Ext.form.TextField({
	    			 fieldLabel:'角色名',
	    			 value: goodslog.rolename
	    		 });
	    		 
	    		 if(goodslog.rolename != '' && goodslog.rolename!=null){
	 				
	 				if(goodslog.roleid != '' && goodslog.roleid!=null){
	 					roleField.fieldLabel = "角色名/<font color=green>ID</font>";
	 					roleField.value= goodslog.rolename + "/" + goodslog.roleid ;
	 				}
	 				
	 			}else if(goodslog.roleid != '' && goodslog.roleid!=null){
	 				
	 				 roleField.fieldLabel = "<font color=green>角色ID</font>";
	    			 roleField.value=goodslog.roleid;
	 			}
	    		
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
	    				 defaults:{xtype:'textfield', width:130},
	    				 labelWidth:60,
	    				 labelAlign:'right',
	    				 items:[{fieldLabel:'客户端',value: goodslog.cid},
	    				        {fieldLabel:'应用', value: goodslog.appid},
	    				        {fieldLabel:'服务器', value: goodslog.zoneid},
	    				        {fieldLabel:'账号',value: goodslog.account},
	    				        roleField,
	    				        {fieldLabel:'物品ID', value: goodslog.prizeid},
	    				        {fieldLabel:'个数', value: goodslog.count},
	    				        {fieldLabel:'优先级', value: goodslog.priority},
	    				        {fieldLabel:'IP', value: goodslog.ip}]
	    			 },{
	    				 columnWidth:.6,
	    				 layout:'form',
	    				 baseCls:'x-plain',
	    				 defaults:{xtype:'textfield', width:190},
	    				 labelWidth:70,
	    				 labelAlign:'right',
	    				 items:[
	    				        {fieldLabel:'请求时间', value: goodslog.requestTime},
	    				        {fieldLabel:'发送时间', value: goodslog.sendTime},
	    				        {fieldLabel:'发送状态', value: goodslog.sendStatus},
	    				        {fieldLabel:'回调时间', value: goodslog.callbackTime},
	    				        {fieldLabel:'回调状态', value: goodslog.callbackHttpStatus},
	    				        {fieldLabel:'接口流水号', value: goodslog.orderid},
	    				        {fieldLabel:'回调参数',labelStyle:'margin-top:40px', xtype:'textarea', value: goodslog.callback, height:75}]
	    			 }]
	    		 });
	    		 var grid = Ext.getCmp('goods-log-grid');
	    		 detailWin.show().getEl().center(grid.getEl());
	    		 grid.getEl().mask();
			}
		  });
		  
	  }
Ext.onReady(function(){
	 
	function PageList(divPrefix){
		
		var instance = this;
		
	    this.gridId = divPrefix + "-grid";
		
	    this.clientInfoStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/clientinfo_list.action',
			root: 'records',
			fields:['cid','name']
		});
	    
		this.store = new Ext.data.JsonStore({
			root:'records',
			totalProperty:'totalCount',
			idProperty:'gid',
			remoteSort:true,			
			fields:['id', 'orderid', 'gid', 'cid', 'appid','zoneid', 'account','rolename', 'roleid', 'prizeid', 'count', 'priority', 'callback', 'sendStatus', 'processCount',
			        'callbackStatus', 'requestTime','prizeResendCount', 'sendTime', 'callbackHttpStatus' ,'callbackTime', 'callbackCount'],	        
	        url:'/wmeovg/service/goodslog_list.action'
	    });
		var baseparams = {limit:25};
		this.store.baseParams = baseparams;
		this.store.setDefaultSort('requestTime', 'desc');
		
		
		function renderRole(value, cellmeta, record, rowIndex, columnIndex, store){
			var rolename = record.get('rolename');
			var roleid= record.get('roleid');
			
			if(rolename != '' && rolename!=null){
				
				if(roleid != '' && roleid!=null){
					return rolename + "/" + "<font color=green>" + roleid + "</font>";
				}
				
				return rolename;
			}else if(roleid != '' && roleid!=null){
				
				return "<font color=green>" + roleid + "</font>";
			}
			
		}
		
		function renderCount(value, cellmeta, record, rowIndex, columnIndex, store){
			var count = record.get('count');
			var processCount= record.get('processCount');
			
			if(count == processCount)
				return  count + "/" + processCount;
			else if(count > processCount){
				return "<font color=red>" + count + "/" + processCount + "</font>";
			}
		}
		

		function renderSendStatus(value, cellmeta, record, rowIndex, columnIndex, store){
			var prizeResendCount = record.get('prizeResendCount');
			var sendStatus= record.get('sendStatus');
			
			if(sendStatus != 0)
				return "<font color=red>" + sendStatus + "/" + prizeResendCount + "</font>";
			else
				return sendStatus + "/" + prizeResendCount;
		}
		
		function renderCallbackHttpStatus(value, cellmeta, record, rowIndex, columnIndex, store){
			var callbackCount = record.get('callbackCount');
			var callbackHttpStatus= record.get('callbackHttpStatus');
			
			return callbackHttpStatus + "/" + callbackCount;
		}
		
		function renderDetail(value, cellmeta, record, rowIndex, columnIndex, store){
			
			///alert(record.get("id"))
			return ("<a href='javascript:void(0)' onclick=\"loadGoodsDetail('"+ record.get("id") +"')\">详细</a>");
		}
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              this.sm,
		                 	              //{header:'流水号',dataIndex:'gid',sortable:true,width:50},
		                	              {header:'客户端',dataIndex:'cid',sortable:true,width:20},
		                	              {header:'所属应用',dataIndex:'appid',sortable:true,width:10},
		                	              {header:'服务器',dataIndex:'zoneid',sortable:true,width:8},
		                	              {header:'账号',dataIndex:'account',sortable:true,width:12},
		                	              {header:'角色名/<font color=green>角色ID</font>', dataIndex:'', renderer: renderRole,width:15},
		                	              {header:'物品ID', dataIndex:'prizeid',sortable:true,width:10},
		                	              {header:'处理个数', dataIndex:'', renderer:renderCount,width:10},
		                	              {header:'优先级', dataIndex:'priority',sortable:true,width:8},
		                	              {header:'请求时间', dataIndex:'requestTime',sortable:true,width:13},
		                	              {header:'发送状态', dataIndex:'sendStatus',renderer:renderSendStatus,width:10},
		                	              //{header:'重发次数', dataIndex:'prizeResendCount',width:10},
		                	              {header:'发送时间', dataIndex:'sendTime',sortable:true,width:13},
		                	              {header:'回调时间', dataIndex:'callbackTime',sortable:true,width:13},
		                	              //{header:'回调次数', dataIndex:'callbackCount',width:10},
		                	              {header:'回调结果', dataIndex:'callbackHttpStatus',sortable:true,renderer:renderCallbackHttpStatus,width:10},
		                	              {header:'详细', dataIndex:'', renderer:renderDetail, width:10}
		                	              ]);

		var JMSCount = new Ext.Toolbar.TextItem('请求队列:0');
	    
		this.grid = new Ext.grid.GridPanel({
			id:'goods-log-grid',
			title:'虚拟物品兑换记录（新）',
			renderTo:this.gridId,
		    //autoHeight:true,
			height:632,
		    width:980,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'rootUrl',
		    cm: this.cm ,
		    viewConfig: {
		        forceFit: true
		    },
		    sm: this.sm,
		    tbar:[{
		    	text:'查询',
		    	iconCls:'magnifier-icon',
		    	handler: function(){
		    		instance.searchWin.show().getEl().center(instance.grid.getEl());
		    		instance.grid.getEl().mask();
		    	}
		    },'-',{
		    	text:'重新载入',
		    	ref:'../reloadBtn',
		    	disabled:true,
		    	iconCls:'magifier-zoom-out-icon',
		    	handler:function(){
		    		instance.searchForm.getForm().reset();
		    		delete instance.store.baseParams;
		    		instance.store.reload({params:{start:0, limit:25},baseParams:baseparams});
		    		
		    		instance.grid.reloadBtn.setDisabled(true);
		    		instance.grid.exportBtn.setDisabled(true);
		    		instance.grid.exportCsvBtn.setDisabled(true);
		    	}
		    }, '-','->', '-', {
			    	text:'导出选中的记录',
			    	ref:'../exportSelectedBtn',
			    	disabled:true,
			    	iconCls:'excel-icon',
			    	handler:function(){
				    	var selectedRows = instance.grid.getSelectionModel().getSelections();
				    	var goodslogGids = new Array();
			    		for(var i=0; i <selectedRows.length; i++){
			    			//alert(selectedRows[i].id)
			    			goodslogGids.push(selectedRows[i].id);
			    		}
			    		if(goodslogGids.length > 0){
			    			location.href = "/wmeovg/export/goodsLogSelectedExport.action?goodslogGids=" + goodslogGids;
			    		}
			    	}
			    }, '-', {
		    	text:'导出记录(XLS)',
		    	ref:'../exportBtn',
		    	disabled:true,
		    	iconCls:'excel-icon',
		    	handler:function(){

			    	location.href = '/wmeovg/export/goodsLogExport.action?' + instance.searchForm.getForm().getValues(true);
		    	}
		    }, '-', {
		    	text:'导出记录(CSV)',
		    	ref:'../exportCsvBtn',
		    	disabled:true,
		    	iconCls:'excel-icon',
		    	handler:function(){

			    	location.href = '/wmeovg/export/goodsLogExportAsCsv.action?' + instance.searchForm.getForm().getValues(true);
		    	}
		    }, '-',JMSCount],
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有记录"
	        }),
	        listeners:{
		    	render:function(grid){
		    		 Ext.TaskMgr.start({
					        run: function(){
						    	Ext.Ajax.request({url:'/wmeovg/service/goodsQueueBrowser.action',
			    	            	success:function(result){
						    		 	Ext.fly(JMSCount.getEl()).update(result.responseText);
			    	    			}
			    	    		});
					        },
					        interval: 5000
					    });
		    	}
		    }
		});
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.exportSelectedBtn.setDisabled(sm.getCount() < 1);
	    });
		
		this.searchForm = new Ext.form.FormPanel({
			region:'north',
			height:213,
			labelAlign:'right',
			labelWidth:80,
			bodyStyle:'padding:5px 0 0',
			frame:true,
			border:false,
			defaults:{xtype:'textfield', width:200},
			items:[{
				fieldLabel:'客户端',
				xtype:'combo',
				store:this.clientInfoStore,
				mode:'remote',
				resizable :true,
				editable:false,
				allowBlank:false,
				pageSize:10,
				minListWidth:240,
				valueField:'cid',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'searchBean.cid',
				triggerAction:'all',
				name:'searchBean.cid'
			},{
				hideLabel :true,
				xtype:'compositefield',
				combineErrors: false,
				width:285,
				items:[{
                    xtype: 'displayfield',
                    value: '所属应用:',
                    style:"text-align: right;padding:2px 0 0;",
                    width:80
                },{
				xtype:'textfield',
				name:'searchBean.appid',
				//allowBlank:false,
				width:84
			},{
                xtype: 'displayfield',
                value: '物品ID:',
                style:"text-align: right;padding:2px 0 0;",
                width:40
            },{
				xtype:'numberfield',
				name:'searchBean.prizeid',
				width:65,
				allowDecimals: false,
				allowNegative:false
			}]},{
				fieldLabel:'起始时间',
				xtype:'compositefield',
				msgTarget : 'side',
				items:[{
					xtype:'datefield',
					name:'searchBean.startdate',
					editable:false,
					format:'Y-m-d',
					width:115
				},{
					xtype:'timefield',
					name:'searchBean.starttime',
					editable:false,
					format:'H:i',
					width:80
				}]
			},{
				fieldLabel:'结束时间',
				xtype:'compositefield',
				items:[{
					xtype:'datefield',
					name:'searchBean.enddate',
					editable:false,
					format:'Y-m-d',
					width:115
				},{
					xtype:'timefield',
					name:'searchBean.endtime',
					editable:false,
					format:'H:i',
					width:80
				}]
			},{
				fieldLabel:'账号',
				name:'searchBean.account'
			},{
				fieldLabel:'流水号',
				name:'searchBean.gid'
			}],
			buttons:[{
				text:'查询',
				handler:function(){
				
					if(instance.searchForm.getForm().isValid()){
						instance.store.baseParams = instance.store.baseParams || baseparams;
						Ext.apply(instance.store.baseParams, instance.searchForm.getForm().getValues());
						instance.store.setDefaultSort('requestTime', 'desc');
						instance.searchWin.hide();
						instance.grid.getEl().unmask();
						instance.store.reload();
						
						instance.grid.reloadBtn.setDisabled(false);
						instance.grid.exportBtn.setDisabled(false);
						instance.grid.exportCsvBtn.setDisabled(false);
					}
				}
			},{
				text:'重置',
				handler:function(){instance.searchForm.getForm().reset();}
			},{
				text:'取消',
				handler:function(){
				
					instance.searchWin.hide();
					instance.grid.getEl().unmask();
				}
			}],
			buttonAlign:'center'
		});
		
	  this.searchWin = new Ext.Window({
		    renderTo:this.grid.getEl(), 
		  	title:'兑换记录查询框',
			layout:'fit',
			plain:true,
			//modal:true,
			width:350,
			closable:false,
			autoHeight:true,
			resizable:false,
			//closeAction:'hide',
			border:false,
			items: [this.searchForm]
	  });	
	 
	  this.detailWin = new Ext.Window({
		 	id:'goods-detail-win',
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
	 
	var goodslogList = new PageList('goodslog');
	goodslogList.store.load({params:{start:0, limit:25}});
});