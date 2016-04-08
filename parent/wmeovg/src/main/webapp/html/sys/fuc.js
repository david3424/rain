
Ext.onReady(function(){
	
	function PageList(divPrefix){
		
		var instance = this;
	    this.gridId = divPrefix + "-grid";
	    this.addFormId = divPrefix + "-addForm";
	    this.editFormId = divPrefix + "-editForm";
	    
		this.parentStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/sys/model_parent.action',
			root: 'records',
			fields:['id','name']
		});
		/**
		 * 添加
		 */
		this.addForm = new Ext.form.FormPanel({
			renderTo: this.addFormId,
			labelAlign:'right',
			labelWidth:60,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield',width:250},
			items:[{
				fieldLabel:'功能名称',
				name:'bean.name',
				allowBlank:false
			},{
				fieldLabel:'所属模块',
				xtype:'combo',
				allowBlank:false,
				store:this.parentStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'bean.model.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'bean.model.id'
			},{
				fieldLabel:'功能地址',
				name:'bean.src',
				allowBlank:false
			},{
				fieldLabel:'排序',
				name:'bean.sort',
				xtype:'numberfield',
				allowBlank:false,
				allowDecimals: false,
				allowNegative:false
			},{
				fieldLabel:'备注',
				name:'bean.note',
				xtype:'textarea',
				height:100
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
							waitMsg:'正在保存数据，请稍后···',
							url:'/wmeovg/sys/fuc_add.action',
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
			instance.addForm.getForm().reset(); // 防止前一次的输入被记住
			instance.addWin.setPosition(e.getPageX(), e.getPageY());
			instance.addWin.show();
		};
		
		/**
		 * 修改
		 */
		this.reader = new Ext.data.JsonReader({},[{name:'bean.id', type:'int', mapping:'id'},{name:'bean.name', mapping:'name', type:'string'},
		                                         {name:'bean.src',mapping:'src', type:'string'},{name:'bean.sort', mapping:'sort',type:'int'},
		   	                                  {name:'bean.note',mapping:'note', type:'string'},
		   	                               {name:'bean.model.id',mapping:'parentid', type:'int'}]);
		this.editForm = new Ext.form.FormPanel({
			renderTo: this.editFormId,
			labelAlign:'right',
			labelWidth:60,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield',width:250},
			items:[{
				name:'bean.id',
				xtype:'hidden'
			},{
				fieldLabel:'功能名称',
				name:'bean.name',
				allowBlank:false
			},{
				fieldLabel:'所属模块',
				xtype:'combo',
				allowBlank:false,
				store:this.parentStore,
				mode:'remote',
				resizable :true,
				pageSize:10,
				listClass :'x-combo-list-height',
				minListWidth:240,
				valueField:'id',
				displayField:'name',
				emptyText:'请选择...',
				hiddenName:'bean.model.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'bean.model.id'
			},{
				fieldLabel:'功能地址',
				name:'bean.src',
				allowBlank:false
			},{
				fieldLabel:'排序',
				name:'bean.sort',
				xtype:'numberfield',
				allowDecimals: false,
				allowBlank:false,
				allowNegative:false
			},{
				fieldLabel:'备注',
				name:'bean.note',
				xtype:'textarea',
				height:100
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
							url:'/wmeovg/sys/fuc_edit.action',
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
	    			url:'/wmeovg/sys/fuc_get.action',
	    			params:{id:instance.editForm.getForm().getValues()['bean.id']}
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
			fields:['name', 'sort', 'note', 'id', 'model', 'src'],	        
	        proxy:new Ext.data.ScriptTagProxy({
	        	url:'/wmeovg/sys/fuc_list.action'
	        }),
	        baseParams: {limit:25}
	    });
		
		this.store.setDefaultSort('id', 'desc');
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              this.sm,
		                	              {header:'功能名称',dataIndex:'name',sortable:true,width:50},
		                	              {header:'所属模块',dataIndex:'model',sortable:true,width:40},
		                	              {header:'功能地址',dataIndex:'src',sortable:true,width:70},
		                	              {header:'排序', dataIndex:'sort',width:15},
		                	              {header:'备注', dataIndex:'note'}]);
		
		function loadUpdate(fucId){
			// 重新加载一次，防止第一次出现combo的值,query设为空,limit设为0
	    	instance.parentStore.load({params:{start:0,limit:0,query:''},callback:function(){
	    		instance.editForm.load({
	    			//waitMsg:'正在加载数据，请稍后···',
	    			url:'/wmeovg/sys/fuc_get.action',
	    			params:{id:fucId},
	    			success:function(){
	    				instance.parentStore.load({params:{start:0,limit:10}});
	    				instance.editWin.show();
	    			}
	    		});
	    	  }});
		}
		
		this.grid = new Ext.grid.GridPanel({
			renderTo:this.gridId,
			width:900,
		    autoHeight:true,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'note',
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
		    	ref:'../deleteBtn',
		    	text:'删除',
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
		    			msg:'确定删除？',
		    			buttons:Ext.MessageBox.YESNOCANCEL,
		    			fn:function(btn){
		    			if(btn=='yes'){
		    				
		    	    		Ext.Ajax.request({url:'/wmeovg/sys/fuc_del.action',
		    	            	success:function(result){
		    	    			instance.store.reload();
		    	    			Ext.example.msg('提示', '删除成功。');
		    	    		},params:{ids:ids}
		    	    		});
		    			}
		    		},
		    			icon:Ext.MessageBox.QUESTION
		    		});
		    	}else{
		    		Ext.MessageBox.show({
		    			title:'请选择',
		    			msg:'请选择要删除的记录',
		    			buttons:Ext.MessageBox.OK,
		    			icon:Ext.MessageBox.WARNING
		    		});
		    	}
		    }
		    },'-','名称:',
	            new Ext.ux.form.SearchField({
	                store: this.store,
	                width:100
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
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.deleteBtn.setDisabled(sm.getCount() < 1);
	    });
	}
	
	var userList = new PageList('fuc');
	userList.store.load({params:{start:0, limit:25}});
});