
Ext.onReady(function(){
	
	var model = {};
	
	model.parentStore = new Ext.data.JsonStore({
		idProperty:'id',
		totalProperty:'totalCount',
		url: '/wmeovg/sys/model_parent.action',
		root: 'records',
		fields:['id','name']
	});
	//model.parentStore.load({params:{start:0,limit:10}});
	/**
	 * 添加
	 */
	model.addForm = new Ext.form.FormPanel({
		renderTo: "model-addForm",
		labelAlign:'right',
		labelWidth:45,
		frame:true,
		//baseCls: 'x-plain',
		border:false,
		defaults:{xtype:'textfield',width:250},
		items:[{
			fieldLabel:'名称',
			name:'model.name',
			allowBlank:false
		},{
			fieldLabel:'父模块',
			allowBlank:false,
			xtype:'combo',
			store:model.parentStore,
			mode:'remote',
			resizable :true,
			pageSize:10,
			minListWidth:240,
			valueField:'id',
			displayField:'name',
			emptyText:'请选择...',
			hiddenName:'model.parent.id',
			//editable:false,
			minChars:2, // 输入字符多于2个时开始搜索
			triggerAction:'all',
			name:'model.parent.id'
		},{
			fieldLabel:'排序',
			xtype:'numberfield',
			allowDecimals: false,
			allowNegative:false,
			allowBlank:false,
			name:'model.sort'
		},{
			fieldLabel:'备注',
			name:'model.note',
			xtype:'textarea',
			height:100
		}]
	});
	model.addWin = new Ext.Window({
		title:'添加模块',
		//applyTo:'model-addWin',
		renderTo:Ext.getBody(),
		modal:true,
		layout:'fit',
		plain:true,
		width:350,
		autoHeight:true,
		resizable:false,
		closeAction:'hide',
		border:false,
		items: [model.addForm],
		buttons:[{
			text:'添加',
			handler:function(){
			if(model.addForm.getForm().isValid()){
				model.addForm.getForm().submit({
						waitMsg:'正在保存数据，请稍后···',
						url:'/wmeovg/sys/model_add.action',
						method:'post',
						success:function(form,action){
						model.store.reload();
						model.addWin.hide();
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
			handler:function(){model.addForm.getForm().reset();}
		},{
			text:'取消',
			handler:function(){model.addWin.hide();}
		}],
		buttonAlign:'center'
	});
	model.showAddWindow = function(){
		model.addForm.getForm().reset(); // 防止前一次的输入被记住
		model.addWin.show(this);
	};
	
	/**
	 * 修改
	 */
	model.reader = new Ext.data.JsonReader({},[{name:'model.id', type:'int', mapping:'id'},{name:'model.name', mapping:'name', type:'string'},
	   	                                  {name:'model.sort', mapping:'sort',type:'int'},
	   	                                  {name:'model.note',mapping:'note', type:'string'},
	   	                                  {name:'model.parent.id',mapping:'parentid', type:'int'}]);
	model.editForm = new Ext.form.FormPanel({
		renderTo: "model-editForm",
		loadMask:true,
		labelAlign:'right',
		labelWidth:45,
		frame:true,
		//baseCls: 'x-plain',
		border:false,
		defaults:{xtype:'textfield',width:250},
		items:[{
			name:'model.id',
			xtype:'hidden'
		},{
			fieldLabel:'名称',
			name:'model.name',
			allowBlank:false
		},{
			fieldLabel:'父模块',
			xtype:'combo',
			allowBlank:false,
			store:model.parentStore,
			mode:'remote',
			resizable :true,
			pageSize:10,
			minListWidth:240,
			valueField:'id',
			displayField:'name',
			emptyText:'请选择...',
			hiddenName:'model.parent.id',
			//editable:false,
			minChars:2,
			triggerAction:'all',
			name:'model.parent.id'
		},{
			fieldLabel:'排序',
			name:'model.sort',
			xtype:'numberfield',
			allowDecimals: false,
			allowBlank:false,
			allowNegative:false
		},{
			fieldLabel:'备注',
			name:'model.note',
			xtype:'textarea',
			height:100
		}],
		reader:model.reader
		
	});
	model.editWin = new Ext.Window({
		title:'修改模块',
		//applyTo:'model-editWin',
		renderTo:Ext.getBody(),
		modal:true,
		layout:'fit',
		plain:true,
		width:350,
		autoHeight:true,
		resizable:false,
		closeAction:'hide',
		border:false,
		items: [model.editForm],
		buttons:[{
			text:'修改',
			handler:function(){
			if(model.editForm.getForm().isValid()){
			model.editForm.getForm().submit({
						waitMsg:'正在保存数据，请稍后···',
						url:'/wmeovg/sys/model_edit.action',
						method:'post',
						success:function(form,action){
							model.store.reload();
							model.editWin.hide();
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
			model.editForm.load({
    			//waitMsg:'正在加载数据，请稍后···',
    			url:'/wmeovg/sys/model_get.action',
    			params:{id:model.editForm.getForm().getValues()['model.id']}
    		});
			}
		},{
			text:'取消',
			handler:function(){model.editWin.hide();}
		}],
		buttonAlign:'center'
	});
	
	model.store = new Ext.data.JsonStore({
		root:'records',
		totalProperty:'totalCount',
		idProperty:'id',
		remoteSort:true,
		
		fields:['name', 'sort', 'note', 'id', 'parentName'],
        url:'/wmeovg/sys/model_list.action',
        baseParams: {limit:25}
    });
	
	model.store.setDefaultSort('id', 'desc');
	
	model.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
	model.cm = new Ext.grid.ColumnModel([
	                 	              //new Ext.grid.RowNumberer(),
	                 	              model.sm,
	                	              {header:'模块名称',dataIndex:'name',sortable:true,width:40},
	                	              {header:'父模块名称',dataIndex:'parentName',width:40},
	                	              {header:'排序', dataIndex:'sort',width:10},
	                	              {header:'备注', dataIndex:'note'}]);
	
	model.loadUpdate = function(modelId){
		// 重新加载一次，防止第一次出现combo的值,query设为空,limit设为0
		model.parentStore.load({params:{start:0,limit:0,query:''},callback:function(){
			model.editForm.load({
    			//waitMsg:'正在加载数据，请稍后···',
    			url:'/wmeovg/sys/model_get.action',
    			params:{id:modelId},
    			success:function(){
    				model.parentStore.load({params:{start:0,limit:10}});
    				model.editWin.show();
    			}
    		});
		}});
	};
	
	model.grid = new Ext.grid.GridPanel({
		width:900,
	    autoHeight:true,
	    loadMask:true,
	    store: model.store,
	    autoExpandColumn:'note',
	    cm: model.cm ,
	    viewConfig: {
	        forceFit: true
	    },
	    sm: model.sm,
	    tbar:[{
	    	text:'添加',
	    	iconCls:'add',
	    	handler: model.showAddWindow
	    },'-',{
	    	ref:'../deleteBtn',
	    	text:'删除',
	    	disabled:true,
	    	iconCls:'delete',
	    	handler:function(){
	    	var selectedRows = model.grid.getSelectionModel().getSelections();
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
	    				
	    	    		Ext.Ajax.request({url:'/wmeovg/sys/model_del.action',
	    	            	success:function(result){
	    	    			model.store.reload();
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
	    			msg:'请选择要删除的模块',
	    			buttons:Ext.MessageBox.OK,
	    			icon:Ext.MessageBox.WARNING
	    		});
	    	}
	    }
	    },'-','模块名称:',
            new Ext.ux.form.SearchField({
                store: model.store,
                width:100
            })],
	    bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: model.store,
            displayInfo: true,
            displayMsg: '当前第{0} - {1}条 共{2}条记录',
            emptyMsg: "暂时没有模块"
        }),
        listeners:{
	    	cellclick:function(grid, rowIndex, columnIndex, e){
	    		if(columnIndex == 1 ){
		    		var record = grid.getStore().getAt(rowIndex);
		    		model.loadUpdate(record.get("id"), e);
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
	
	model.grid.render('model-grid');
	model.store.load({params:{start:0, limit:25}});

	model.grid.getSelectionModel().on('selectionchange', function(sm){
		model.grid.deleteBtn.setDisabled(sm.getCount() < 1);
    });
});