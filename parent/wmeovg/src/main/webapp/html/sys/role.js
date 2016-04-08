
Ext.onReady(function(){
	
	function PageList(divPrefix){
		
		var instance = this;
	    this.gridId = divPrefix + "-grid";
	    this.addFormId = divPrefix + "-addForm";
	    this.editFormId = divPrefix + "-editForm";
		/**
		 * 添加
		 */
		this.addForm = new Ext.form.FormPanel({
			renderTo: this.addFormId,
			//baseCls: 'x-plain',
			labelAlign:'right',
			labelWidth:60,
			frame:true,
			//bodyStyle:'padding-top:10px;padding-left:5px',
			border:false,
			defaults:{xtype:'textfield',width:250},
			items:[{
				fieldLabel:'名称',
				name:'bean.name',
				allowBlank:false
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
			modal:true,
			layout:'fit',
			plain:true,
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
							url:'/wmeovg/sys/role_add.action',
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
		this.showAddWindow = function(){
			instance.addForm.getForm().reset(); // 防止前一次的输入被记住
			instance.addWin.show(this);
		};
		
		/**
		 * 修改
		 */
		this.reader = new Ext.data.JsonReader({},[{name:'bean.id', type:'int', mapping:'id'},{name:'bean.name', mapping:'name', type:'string'},
		                                         {name:'bean.type',mapping:'type', type:'string'}]);
		this.editForm = new Ext.form.FormPanel({
			renderTo: this.editFormId,
			//baseCls: 'x-plain',
			labelAlign:'right',
			labelWidth:60,
			frame:true,
			//bodyStyle:'padding-top:10px;padding-left:5px',
			border:false,
			defaults:{xtype:'textfield',width:250},
			items:[{
				name:'bean.id',
				xtype:'hidden'
			},{
				fieldLabel:'名称',
				name:'bean.name',
				allowBlank:false
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
			//applyTo:this.editWinId ,
			layout:'fit',
			plain:true,
			renderTo:Ext.getBody(),
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
							url:'/wmeovg/sys/role_edit.action',
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
	    			url:'/wmeovg/sys/role_get.action',
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
			fields:['name', 'note', 'id'],	        
	        url:'/wmeovg/sys/role_list.action',
	        baseParams: {limit:25}
	    });
		
		this.store.setDefaultSort('id', 'desc');
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              this.sm,
		                	              {header:'角色名称',dataIndex:'name',sortable:true,width:50},
		                	              {header:'备注',dataIndex:'note',sortable:true,width:70}]);
		
		function loadUpdate(roleId){
			instance.editForm.load({
    			//waitMsg:'正在加载数据，请稍后···',
    			url:'/wmeovg/sys/role_get.action',
    			params:{id:roleId},
    			success:function(){
    				instance.editWin.show();
    			}
    		});
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
		    				
		    	    		Ext.Ajax.request({url:'/wmeovg/sys/role_del.action',
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
	
	var roleList = new PageList('role');
	roleList.store.load({params:{start:0, limit:25}});
});