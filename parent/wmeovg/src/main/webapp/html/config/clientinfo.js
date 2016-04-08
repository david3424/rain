
function testUrl(id){
	var grid = Ext.getCmp('client-info-grid');
	
	try{
		grid.getEl().mask('连接测试中，请稍后···');
		Ext.Ajax.request({url:'/wmeovg/config/clientinfo_testUrl.action',
			params:{'entity.id':id},
	    	success:function(result){
			 	switch(parseInt(result.responseText)){
			 		//case 1001:
			 			//Ext.example.msg('提示', '连接成功'); break;
			 		case 500:
			 			Ext.example.msg('提示', '客户端接口配置错误(500)'); break;
			 		case 404:
			 			Ext.example.msg('提示', '客户端接口未配置(404)'); break;
			 		case -1:
			 			Ext.example.msg('提示', '客户端服务器连接超时'); break;
			 		default:
			 			//Ext.example.msg('提示', '测试失败，请重试('+ result.responseText +')'); break;
			 			Ext.example.msg('提示',  result.responseText ); break;
			 	}
			 	
			 	//grid.getEl().unmask();
			}
		});
	}catch (e) {
		Ext.example.msg('提示', '客户端服务器连接超时');
		//grid.getEl().unmask();
	}finally{
		
		grid.getEl().unmask();
	}
}

function updateStatus(id, name, status) {
	var msg;
	if(status==0)
		msg = '接受客户端' + name + '的兑换请求?';
	else if(status ==-1)
		msg = '拒绝客户端' + name + '的兑换请求? <br/>拒绝后该客户端下的<span style="color:red">所有物品的兑换请求都将被拒绝</span>';
	Ext.MessageBox.show( {
		title : '确认',
		msg : msg,
		buttons : Ext.MessageBox.YESNOCANCEL,
		fn : function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request( {
					url : '/wmeovg/config/clientinfo_updateStatus.action',
					params : {
						'entity.id' : id,
						'entity.status' : status
					},
					success : function(result) {
						Ext.example.msg('提示', '操作已成功');
						var grid = Ext.getCmp('client-info-grid');
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
	    this.searchFormId = divPrefix + "-searchForm";
	    var priority = [
	  	               ['0', '0'],
	  	               ['1', '1'],
	  	               ['2', '2'],
	  	               ['3', '3']
	  	              ];
		/**
		 * 添加
		 */
		this.addForm = new Ext.form.FormPanel({
			renderTo: this.addFormId,
			labelAlign:'right',
			labelWidth:50,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield'},
			items:[{
				fieldLabel:'名称',
				name:'entity.name',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false,
				width:160
			},{
				fieldLabel:'优先级',
				name:'entity.priority',
				xtype:'combo',
				emptyText:'请选择...',
				hiddenName:'entity.priority',
				width:80,
				allowBlank:false,
				editable:false,
				mode:'local',
				resizable :false,
				triggerAction:'all',
				store:new Ext.data.SimpleStore({
                	fields:['type', 'text'],
                	data: priority
                }),
				displayField:'text',
				valueField:'text',
				forceSelection:true,
				name:'type',
				lazyInit :false,
				listeners:{
						focus:function(combo){
							combo.onLoad();
						},
			    	  select:function(combo, record, index){
			      		}
			      }
			},{
				fieldLabel:'url',
				name:'entity.rootUrl',
				//regex:/^https?:\/\/(([\w-]+(:[0-9]+)?)|(([\w-]+\.)+[\w-]+(:[0-9]+)?))(\/[\w-]+)*$/,
				regex:/^https?:\/\/(([\w-]+(:[0-9]+)?)|(([\w-]+\.)+[\w-]+(:[0-9]+)?))(\/[\w-\.]+)*$/,
				regexText:'请输入正确的URL地址(http或https)<br/>如http://www.wanmei.com 或 http://www.wanmei.com/myapp',
				autoCreate:{tag:'input',type:'text', maxlength:'250'},
				allowBlank:false,
				width:250
			},{
				fieldLabel:'客户端IP',
				name:'entity.clientIp',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false,
				width:160
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
							url:'/wmeovg/config/clientinfo_add.action',
							method:'post',
							success:function(form,action){
							instance.store.reload();
							instance.addWin.hide();
							Ext.example.msg('提示', '添加成功。');
						},
						failure:function(form, action){
							Ext.example.msg('提示', '添加失败，请重试。');
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
		this.reader = new Ext.data.JsonReader({},[{name:'entity.id', type:'int', mapping:'id'},{name:'entity.name',mapping:'name', type:'string'},
		                                          {name:'entity.priority', mapping:'priority', type:'int'},
		                                         {name:'entity.rootUrl',mapping:'rootUrl', type:'string'},
		                                         {name:'entity.clientIp',mapping:'clientIp', type:'string'},
		                                         {name:'entity.privateKey',mapping:'privateKey', type:'string'}]);
		this.editForm = new Ext.form.FormPanel({
			renderTo: this.editFormId,
			labelAlign:'right',
			labelWidth:50,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield'},
			items:[{
				name:'entity.id',
				xtype:'hidden'
			},{
				fieldLabel:'名称',
				name:'entity.name',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false,
				width:160
			},{
				fieldLabel:'优先级',
				name:'entity.priority',
				width:80,
				xtype:'combo',
				emptyText:'请选择...',
				hiddenName:'entity.priority',
				allowBlank:false,
				editable:false,
				mode:'local',
				resizable :false,
				triggerAction:'all',
				store:new Ext.data.SimpleStore({
                	fields:['type', 'text'],
                	data: priority
                }),
				displayField:'text',
				valueField:'text',
				forceSelection:true,
				name:'type',
				lazyInit :false,
				listeners:{
						focus:function(combo){
							combo.onLoad();
						},
			    	  select:function(combo, record, index){
			      		}
			      }
			},{
				fieldLabel:'url',
				name:'entity.rootUrl',
				//regex:/^https?:\/\/(([\w-]+(:[0-9]+)?)|(([\w-]+\.)+[\w-]+(:[0-9]+)?))(\/[\w-]+)*$/,
				regex:/^https?:\/\/(([\w-]+(:[0-9]+)?)|(([\w-]+\.)+[\w-]+(:[0-9]+)?))(\/[\w-\.]+)*$/,
				regexText:'请输入正确的URL地址(http或https)<br/>如http://www.wanmei.com 或 http://www.wanmei.com/myapp',
				autoCreate:{tag:'input',type:'text', maxlength:'250'},
				allowBlank:false,
				width:250
			},{
				fieldLabel:'客户端IP',
				name:'entity.clientIp',
				//autoCreate:{tag:'input',type:'text', maxlength:'20'},
				allowBlank:false,
				width:160
			},{
				fieldLabel:'私钥',
				name:'entity.privateKey',
				xtype:'displayfield',
				width:150
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
							url:'/wmeovg/config/clientinfo_edit.action',
							method:'post',
							success:function(form,action){
								instance.store.reload();
								instance.editWin.hide();
								Ext.example.msg('提示', '修改成功。');
						},
						failure:function(form, action){
							Ext.example.msg('提示', '修改失败，请重试。');
						}
					});
				}
			}
			},{
				text:'重置',
				handler:function(){
				instance.editForm.load({
	    			//waitMsg:'正在加载数据，请稍后···',
	    			url:'/wmeovg/config/clientinfo_get.action',
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
			fields:['id', 'cid', 'name', 'privateKey', 'clientIp', 'priority', 'rootUrl', 'user', 'createtime', 'requestCount', 'callbackCount', 'status'],	        
	        url:'/wmeovg/config/clientinfo_list.action'
	    });
		
		var baseparams = {limit:25};
		this.store.baseParams = baseparams;
		
		function renderTestUrl(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return ('<a onclick="testUrl('+ record.get('id') +')" href="javascript:void(0)"> 测试</a>');
		}
		
		function renderStatus(value, cellmeta, record, rowIndex, columnIndex, store){
			
			if(value==0)
				return ('<center><span disabled>接受</span>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="updateStatus('+ record.get('id') +', \''+ record.get('name') +'\',-1)" href="javascript:void(0)" style="color:red;">拒绝</a></center>');
			else if(value ==-1)
				return ('<center><a onclick="updateStatus('+ record.get('id') +',\''+ record.get('name') +'\', 0)" href="javascript:void(0)" style="color:green;">接受</a>&nbsp;&nbsp;&nbsp;&nbsp;<span disabled>拒绝</span></center>');
		}
		function renderJMSCount(value, cellmeta, record, rowIndex, columnIndex, store){
			
			return record.get('requestCount') + "/" + record.get('callbackCount');
		}
		this.store.setDefaultSort('id', 'desc');
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel({columns:[
		                 	              //new Ext.grid.RowNumberer(),
		                 	              this.sm,
		                 	              {header:'名称',dataIndex:'name',sortable:true,width:30},
		                	              {header:'标识',dataIndex:'cid',sortable:true,width:6},
		                	              {header:'私钥',dataIndex:'privateKey',sortable:true,width:6},
		                	              {header:'客户端IP',dataIndex:'clientIp',sortable:true,width:6},
		                	              {header:'优先级',dataIndex:'priority',sortable:true,width:9},
		                	              {header:'根目录URL', dataIndex:'rootUrl',width:40},
		                	              {header:'回调测试', dataIndex:'',renderer:renderTestUrl, width:10},
		                	              {header:'JMS', dataIndex:'',renderer:renderJMSCount,width:10},
		                	              {header:'创建人', dataIndex:'user',width:10},
		                	              {header:'创建时间', dataIndex:'createtime',width:20},
		                	              {header:'兑换请求状态', dataIndex:'status',renderer:renderStatus,width:20,align:'center'}
		                	              ],
		                	              defaults:{
												//align:'center'
											}
		                	              });
		
		function loadUpdate(fucId){
    		instance.editForm.load({
    			//waitMsg:'正在加载数据，请稍后···',
    			url:'/wmeovg/config/clientinfo_get.action',
    			params:{id:fucId},
    			success:function(){
    				
    				instance.editWin.show();
    			}
    		});
		}
		
		this.grid = new Ext.grid.GridPanel({
			id:'client-info-grid',
			renderTo:this.gridId,
			width:980,
		    autoHeight:true,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'rootUrl',
		    cm: this.cm ,
		    viewConfig: {
		        forceFit: true
		    },
		    sm: this.sm,
		    tbar:[{
		    	text:'添加',
		    	iconCls:'add',
		    	handler: this.showAddWindow
		    },'-',{
		    	ref:'../acceptBtn',
		    	text:'接受',
		    	disabled:true,
		    	iconCls:'accept',
		    	handler:function(){
			    	var selectedRows = instance.grid.getSelectionModel().getSelections();
			    	var ids = new Array();
		    		for(var i=0; i <selectedRows.length; i++){
		    			ids.push(selectedRows[i].id);
		    		}
			    	if(ids.length > 0){
			    		Ext.MessageBox.show({
			    			title:'确认',
			    			msg:'确定接受这些客户端的兑换请求（<font color="red">请谨慎操作</font>）？',
			    			buttons:Ext.MessageBox.YESNOCANCEL,
			    			
			    			fn:function(btn){
			    			if(btn=='yes'){
			    				
			    	    		Ext.Ajax.request({url:'/wmeovg/config/clientinfo_accept.action',
			    	            	success:function(result){
			    	    			instance.store.reload();
			    	    			Ext.example.msg('提示', '操作成功。');
			    	    		},params:{ids:ids}
			    	    		});
			    			}
			    		},
			    			icon:Ext.MessageBox.QUESTION
			    		});
			    	}
			    }
		    },'-',{
		    	ref:'../rejectBtn',
		    	text:'拒绝',
		    	disabled:true,
		    	iconCls:'delete',
		    	handler:function(){
			    	var selectedRows = instance.grid.getSelectionModel().getSelections();
			    	var ids = new Array();
		    		for(var i=0; i <selectedRows.length; i++){
		    			ids.push(selectedRows[i].id);
		    		}
			    	if(ids.length > 0){
			    		Ext.MessageBox.show({
			    			title:'确认',
			    			msg:'确定拒绝这些客户端的兑换请求（<font color="red">请谨慎操作</font>）？',
			    			buttons:Ext.MessageBox.YESNOCANCEL,
			    			
			    			fn:function(btn){
			    			if(btn=='yes'){
			    				
			    	    		Ext.Ajax.request({url:'/wmeovg/config/clientinfo_reject.action',
			    	            	success:function(result){
			    	    			instance.store.reload();
			    	    			Ext.example.msg('提示', '操作成功。');
			    	    		},params:{ids:ids}
			    	    		});
			    			}
			    		},
			    			icon:Ext.MessageBox.QUESTION
			    		});
			    	}
			    }
		    }],
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有记录"
	        }),
	        listeners:{
		    	cellclick:function(grid, rowIndex, columnIndex, e){
		    		if(columnIndex == 1 ){
			    		var record = grid.getStore().getAt(rowIndex);
			    		loadUpdate(record.get("id"), e);
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
		    	}
		    }
		});
		
		this.clientInfoStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/clientinfo_list.action',
			root: 'records',
			fields:['id','name']
		});
		
		this.userStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/sys/user_list.action',
			root: 'records',
			fields:['id','name']
		});
		
		this.searchForm = new Ext.form.FormPanel({
			//region : 'north',
			border:false,
			renderTo: this.searchFormId,
			width:250,
			labelAlign:'right',
			labelWidth:50,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield'},
			items:[{
				fieldLabel:'客户端',
				xtype:'combo',
				store:this.clientInfoStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.id'
			},{
				fieldLabel:'创建人',
				xtype:'combo',
				store:this.userStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'entity.user.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.user.id'
			}],
			buttons:[{
				text:'查询',
				handler:function(){
					instance.store.baseParams = instance.store.baseParams || baseparams;
					Ext.apply(instance.store.baseParams, instance.searchForm.getForm().getValues());

					instance.store.reload();
				}
			},{
				text:'重置',
				handler:function(){
					instance.searchForm.getForm().reset();
					
					delete instance.store.baseParams;
					
					instance.store.reload({params:{start:0, limit:25},baseParams:baseparams});
				}
			}],
			buttonAlign:'center'});
		/*
		this.panel = new Ext.Panel({
			renderTo:this.gridId,
			width:1000,
			border:false,
			//layout:'border',
			items:[this.grid, this.searchForm]
		});*/
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.acceptBtn.setDisabled(sm.getCount() < 1);
	    });
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.rejectBtn.setDisabled(sm.getCount() < 1);
	    });
	}
	
	var clientinfoList = new PageList('clientinfo');
	clientinfoList.store.load({params:{start:0, limit:25}});
});