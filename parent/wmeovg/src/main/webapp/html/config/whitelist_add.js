
function updateStatus(id, clientName, appid, prizename, status) {
	var msg;
	if(status==0)
		msg = '接受客户端：' + unescape(clientName) + '，应用标识：' + appid +'，物品：'+ unescape(prizename) +'的兑换请求?';
	else if(status ==-1)
		msg = '拒绝客户端：' + unescape(clientName) + '，应用标识：' + appid +'，物品：'+ unescape(prizename) + '的兑换请求? ';
	Ext.MessageBox.show( {
		title : '确认',
		msg : msg,
		buttons : Ext.MessageBox.YESNOCANCEL,
		fn : function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request( {
					url : '/wmeovg/config/whitelist_updateStatus.action',
					params : {
						'entity.id' : id,
						'entity.status' : status
					},
					success : function(result) {
						Ext.example.msg('提示', '操作已成功');
						var grid = Ext.getCmp('white-list-grid');
						grid.getStore().reload();
					}
				});
			}
		}
	});
}

Ext.onReady(function(){
	
	function PageList(divPrefix){
		
		var instance = this;
		
	    this.gridId = divPrefix + "-grid";
	    this.addFormId = divPrefix + "-addForm";
	    this.editFormId = divPrefix + "-editForm";
	    
	    this.clientInfoStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/clientinfo_list.action',
			root: 'records',
			fields:['id','name']
		});
	    instance.clientInfoStore.load({params:{start:0,limit:0,query:''}});
	    this.gameStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/game_list.action',
			root: 'records',
			fields:['id','name']
		});
	    instance.gameStore.load({params:{start:0,limit:0,query:''}});
		/**
		 * 添加
		 */
		this.addForm = new Ext.form.FormPanel({
			renderTo: this.addFormId,
			labelAlign:'right',
			labelWidth:100,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield', width:150},
			items:[{
				fieldLabel:'应用标识',
				name:'entity.appid',
				minLength:5,
				//autoCreate:{tag:'input',type:'text', maxlength:'15'},
				regex:/^[a-z]+[0-9_a-z]*$/,
				regexText:'只能输入数字、小写字母或下划线(必须以字母开头)',
				allowBlank:false
			},{
				fieldLabel:'物品名称',
				name:'entity.prizename',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false
			},{
				fieldLabel:'物品ID',
				name:'entity.prizeid',
				xtype:'numberfield',
				allowBlank:false,
				allowDecimals: false,
				allowNegative:false
			},{
				fieldLabel:'发送总数',
				combineErrors: false,
				xtype:'compositefield',
				items:[{
					name:'entity.count',
					xtype:'numberfield',
					allowBlank:false,
					allowDecimals: false,
					allowNegative:false,
					width:90
				},{
					name:'iscount',
					xtype:'checkbox',
					boxLabel :'无上限',
					listeners:{
						"check":function(){
							var countField = instance.addForm.getForm().findField('entity.count');
							if(this.checked){
								countField.setValue("-1");
								countField.disable();
							}else{
								countField.enable();
								instance.addForm.getForm().findField('entity.count').setValue("");
							}
						}
					},
				width:80
				}]
			},{
				fieldLabel:'发送客户端',
				xtype:'combo',
				allowBlank:false,
				editable:false,
				store:this.clientInfoStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.clientInfo.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.clientInfo.id'
			},{
				fieldLabel:'所属游戏',
				xtype:'combo',
				allowBlank:false,
				editable:false,
				store:this.gameStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.game.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.game.id'
			}]
		});
		this.addWin = new Ext.Window({
			title:'添加',
			renderTo:Ext.getBody(),
			layout:'fit',
			plain:true,
			modal:true,
			width:350,
			autoHeight:true,
			resizable:false,
			closeAction:'hide',
			border:false,
			items: [this.addForm],
			buttons:[{
				text:'添加',
				handler:function(){
				if(instance.addForm.getForm().isValid()){
					instance.addForm.getForm().submit({
							//waitMsg:'正在保存数据，请稍后···',
							url:'/wmeovg/config/whitelist_add.action',
							method:'post',
							success:function(form,action){
							instance.store.reload();
							instance.addWin.hide();
							Ext.example.msg('提示', '添加成功。');
						},
						failure:function(form, action){
							
							Ext.example.msg('提示', action.result.msg);
						}
					});
				}
			}
			},{
				text:'重置',
				handler:function(){instance.addForm.getForm().reset();}
			},{
				text:'取消',
				handler:function(){instance.addWin.hide();}
			}],
			buttonAlign:'center'
		});
		this.showAddWindow = function(btn,e){
			//instance.addForm.getForm().reset(); // 防止前一次的输入被记住
			instance.addWin.setPosition(e.getPageX(), e.getPageY());
			instance.addWin.show();
		};
		
		/**
		 * 修改
		 */
		this.reader = new Ext.data.JsonReader({},[{name:'entity.id', type:'int', mapping:'id'},{name:'entity.appid',mapping:'appid', type:'string'},
		                                          {name:'entity.prizename',mapping:'prizename', type:'string'},
		                                          {name:'entity.prizeid', mapping:'prizeid', type:'int'},
		                                         {name:'entity.count',mapping:'count', type:'int'},{name:'entity.clientInfo.id',mapping:'clientInfoId', type:'int'},
		                                         {name:'entity.game.id',mapping:'gameId', type:'int'}]);
		this.editForm = new Ext.form.FormPanel({
			renderTo: this.editFormId,
			labelAlign:'right',
			labelWidth:100,
			frame:true,
			border:false,
			defaults:{xtype:'textfield', width:150},
			items:[{
				name:'entity.id',
				xtype:'hidden'
			},{
				fieldLabel:'应用标识',
				name:'entity.appid',
				minLength:5,
				//autoCreate:{tag:'input',type:'text', maxlength:'15'},
				regex:/^[a-z]+[0-9_a-z]*$/,
				regexText:'只能输入数字、小写字母或下划线(必须以字母开头)',
				allowBlank:false
			},{
				fieldLabel:'物品名称',
				name:'entity.prizename',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false
			},{
				fieldLabel:'物品ID',
				name:'entity.prizeid',
				xtype:'numberfield',
				allowBlank:false,
				allowDecimals: false,
				allowNegative:false
			},{
				fieldLabel:'发送总数',
				xtype:'compositefield',
				items:[{
					name:'entity.count',
					xtype:'numberfield',
					allowBlank:false,
					allowDecimals: false,
					allowNegative:false,
					width:90
				},{
					name:'iscount',
					xtype:'checkbox',
					boxLabel :'无上限',
				    listeners:{
						"check":function(){
							var countField = instance.editForm.getForm().findField('entity.count');
							if(this.checked){
								countField.setValue("-1");
								countField.disable();
							}else{
								countField.enable();
								countField.setValue("");
							}
						}
					},
				   width:80
				}]
			},{
				fieldLabel:'发送客户端',
				xtype:'combo',
				allowBlank:false,
				editable:false,
				store:this.clientInfoStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.clientInfo.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.clientInfo.id'
			},{
				fieldLabel:'所属游戏',
				xtype:'combo',
				allowBlank:false,
				editable:false,
				store:this.gameStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.game.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.game.id'
			}],
			reader:this.reader
		});
		this.editWin = new Ext.Window({
			title:'修改',
			renderTo:Ext.getBody(),
			layout:'fit',
			plain:true,
			modal:true,
			width:350,
			autoHeight:true,
			resizable:false,
			closeAction:'hide',
			border:false,
			items: [this.editForm],
			buttons:[{
				text:'修改',
				handler:function(){
				if(instance.editForm.getForm().isValid()){
				instance.editForm.getForm().submit({
							waitMsg:'正在保存数据，请稍后···',
							url:'/wmeovg/config/whitelist_edit.action',
							method:'post',
							success:function(form,action){
								instance.store.reload();
								instance.editWin.hide();
								Ext.example.msg('提示', '修改成功。');
						},
						failure:function(form, action){
							
							Ext.example.msg('提示', action.result.msg);
						}
					});
				}
			}
			},{
				text:'重置',
				handler:function(){
				instance.editForm.load({
	    			//waitMsg:'正在加载数据，请稍后···',
	    			url:'/wmeovg/config/whitelist_get.action',
	    			params:{id:instance.editForm.getForm().getValues()['entity.id']}
	    		});
				}
			},{
				text:'取消',
				handler:function(){instance.editWin.hide();}
			}],
			buttonAlign:'center'
		});
		
		this.store = new Ext.data.JsonStore({
			root:'records',
			totalProperty:'totalCount',
			idProperty:'id',
			remoteSort:true,			
			fields:['id', 'clientName', 'game', 'appid', 'prizename', 'prizeid', 'count', 'sendCount', 'failCount', 'requestCount', 'callbackCount','user','createtime', 'status'],	        
	        url:'/wmeovg/config/whitelist_ownlist.action',
	        baseParams: {limit:25}
	    });
		
		this.store.setDefaultSort('id', 'desc');
		
		function renderTotalCount(value, cellmeta, record, rowIndex, columnIndex, store){
			
			if(value==-1)
				return "无上限";
			else
				return value;
		}
		function renderCount(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return record.get('sendCount') + "/" + record.get('failCount');
		}
		function renderJMSCount(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return record.get('requestCount') + "/" + record.get('callbackCount');
		}
		function renderStatus(value, cellmeta, record, rowIndex, columnIndex, store){
			
			if(value==0)
				return ('<center style="color:green;">接受</center>');
			else if(value ==-1)
				return ('<center style="color:red;">拒绝</center>');
		}

		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              //this.sm,
		                 	              {header:'发送客户端',dataIndex:'clientName',sortable:true,width:30},
		                 	              {header:'应用标识',dataIndex:'appid',sortable:true,width:20},
		                	              {header:'物品名称',dataIndex:'prizename',sortable:true,width:20},
		                	              {header:'物品ID',dataIndex:'prizeid',sortable:true,width:15},
		                	              {header:'发送总数',dataIndex:'count',sortable:true,width:15 , renderer:renderTotalCount},
		                	              {header:'请求/失败数目',dataIndex:'',renderer:renderCount,sortable:true,width:20},
		                	              {header:'JMS', dataIndex:'',renderer:renderJMSCount,width:15},
		                	              {header:'所属游戏',dataIndex:'game',sortable:true,width:20},
		                	              {header:'创建人', dataIndex:'user',width:10},
		                	              {header:'创建时间', dataIndex:'createtime',sortable:true,width:18},
		                	              {header:'兑换请求状态', dataIndex:'status',renderer:renderStatus,width:25,align:'center'}]);
		
		function loadUpdate(fucId){
			// 重新加载一次，防止第一次出现combo的值,query设为空,limit设为0
	    	//instance.clientInfoStore.load({params:{start:0,limit:0,query:''},callback:function(){
	    	//	instance.gameStore.load({params:{start:0,limit:0,query:''},callback:function(){
		    		instance.editForm.load({
		    			//waitMsg:'正在加载数据，请稍后···',
		    			url:'/wmeovg/config/whitelist_get.action',
		    			params:{id:fucId},
		    			success:function(form, action){
		    				var count = action.result.data['entity.count'];
		    				if(count == -1){
		    					instance.editForm.getForm().findField('iscount').setValue(true);
		    				}else{
		    					instance.editForm.getForm().findField('iscount').setValue(false);
		    					instance.editForm.getForm().findField('entity.count').setValue(count);
		    				}
		    				instance.editWin.show();
		    			}
		    		});
	    		//}});
	    //	}});
		}
		
		this.grid = new Ext.grid.GridPanel({
			id:'white-list-grid',
			renderTo:this.gridId,
			width:980,
		    autoHeight:true,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'clientName',
		    cm: this.cm ,
		    viewConfig: {
		        forceFit: true
		    },
		    sm: this.sm,
		    tbar:[/*{
		    	text:'添加',
		    	iconCls:'add',
		    	handler: this.showAddWindow
		    },'-',*/'奖品名称:',
	            new Ext.ux.form.SearchField({
	                store: this.store,
	                width:200
	            })],
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有记录"
	        }),
	        listeners:{/*
		    	cellclick:function(grid, rowIndex, columnIndex, e){
		    		if(columnIndex == 0 ){
			    		var record = grid.getStore().getAt(rowIndex);
			    		if(record.get('status') == -1)
			    			loadUpdate(record.get("id"), e);
			    		else
			    			Ext.example.msg('提示', '该物品的兑换请求已经接受，不可再次修改。');
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
		    					//var rowIndex = view.findRowIndex(tip.triggerElement);
		    					tip.body.dom.innerHTML = "单击第一列修改";
		    				}
		    			}
		    		});
		    	}*/
		    }
		});
		
		
		/*
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.deleteBtn.setDisabled(sm.getCount() < 1);
	    });*/
	}
	
	var whitelistList = new PageList('whitelist-add');
	whitelistList.store.load({params:{start:0, limit:25}});
});