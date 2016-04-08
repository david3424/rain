
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
			labelAlign:'right',
			labelWidth:70,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield', width:130},
			items:[{
				fieldLabel:'游戏名称',
				name:'entity.name',
				autoCreate:{tag:'input',type:'text', maxlength:'8'},
				allowBlank:false
			},{
				fieldLabel:'游戏简称',
				name:'entity.serverName',
				autoCreate:{tag:'input',type:'text', maxlength:'8'},
				allowBlank:false
			},{
				fieldLabel:'aid',
				name:'entity.aid',
				xtype:'numberfield',
				allowBlank:false,
				allowDecimals: false,
				allowNegative:false
			}]
		});
		this.addWin = new Ext.Window({
			title:'添加',
			renderTo:Ext.getBody(),
			layout:'fit',
			plain:true,
			modal:true,
			width:250,
			autoHeight:true,
			resizable:false,
			closeAction:'hide',
			border:false,
			items: [this.addForm],
			buttons:[{
				text:'添加',
				width:50,
				handler:function(){
				if(instance.addForm.getForm().isValid()){
					instance.addForm.getForm().submit({
							//waitMsg:'正在保存数据，请稍后···',
							url:'/wmeovg/config/game_add.action',
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
				width:50,
				handler:function(){instance.addForm.getForm().reset();}
			},{
				text:'取消',
				width:50,
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
		                                          {name:'entity.serverName', mapping:'serverName', type:'string'},
		                                         {name:'entity.aid',mapping:'aid', type:'int'}]);
		this.editForm = new Ext.form.FormPanel({
			renderTo: this.editFormId,
			labelAlign:'right',
			labelWidth:70,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield',width:130},
			items:[{
				name:'entity.id',
				xtype:'hidden'
			},{
				fieldLabel:'游戏名称',
				name:'entity.name',
				autoCreate:{tag:'input',type:'text', maxlength:'8'},
				allowBlank:false
			},{
				fieldLabel:'游戏简称',
				name:'entity.serverName',
				autoCreate:{tag:'input',type:'text', maxlength:'8'},
				allowBlank:false
			},{
				fieldLabel:'aid',
				name:'entity.aid',
				xtype:'numberfield',
				allowBlank:false,
				allowDecimals: false,
				allowNegative:false
			}],
			reader:this.reader
			
		});
		this.editWin = new Ext.Window({
			title:'修改',
			renderTo:Ext.getBody(),
			layout:'fit',
			plain:true,
			modal:true,
			width:250,
			autoHeight:true,
			resizable:false,
			closeAction:'hide',
			border:false,
			items: [this.editForm],
			buttons:[{
				text:'修改',
				width:50,
				handler:function(){
				if(instance.editForm.getForm().isValid()){
				instance.editForm.getForm().submit({
							waitMsg:'正在保存数据，请稍后···',
							url:'/wmeovg/config/game_edit.action',
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
				width:50,
				handler:function(){
				instance.editForm.load({
	    			//waitMsg:'正在加载数据，请稍后···',
	    			url:'/wmeovg/config/game_get.action',
	    			params:{id:instance.editForm.getForm().getValues()['entity.id']}
	    		});
				}
			},{
				text:'取消',
				width:50,
				handler:function(){instance.editWin.hide();}
			}],
			buttonAlign:'center'
		});
		
		this.store = new Ext.data.JsonStore({
			root:'records',
			totalProperty:'totalCount',
			idProperty:'id',
			remoteSort:true,			
			fields:['id', 'name', 'serverName', 'aid'],	        
	        url:'/wmeovg/config/game_list.action',
	        baseParams: {limit:25}
	    });
		
		this.store.setDefaultSort('id', 'desc');
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              //this.sm,
		                 	              {header:'游戏名称',dataIndex:'name',sortable:true,width:40},
		                	              {header:'游戏简称',dataIndex:'serverName',sortable:true,width:20},
		                	              {header:'aid',dataIndex:'aid',sortable:true,width:10}]);
		
		function loadUpdate(fucId){
    		instance.editForm.load({
    			//waitMsg:'正在加载数据，请稍后···',
    			url:'/wmeovg/config/game_get.action',
    			params:{id:fucId},
    			success:function(){
    				
    				instance.editWin.show();
    			}
    		});
		}
		
		this.grid = new Ext.grid.GridPanel({
			renderTo:this.gridId,
			width:500,
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
		    },'-',/*{
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
		    				
		    	    		Ext.Ajax.request({url:'/wmeovg/config/game_del.action',
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
		    },'-',*/'游戏名称:',
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
		    		if(columnIndex == 0 ){
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
		
		/*
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.deleteBtn.setDisabled(sm.getCount() < 1);
	    });*/
	}
	
	var gameList = new PageList('game');
	gameList.store.load({params:{start:0, limit:25}});
});