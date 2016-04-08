
Ext.onReady(function(){

	
	function RoleFuc(panelName){
		
		var rf = this;
		this.panelName = panelName;
		
		this.roleStore = new Ext.data.JsonStore({
			url: '/wmeovg/sys/role_all.action',
			idProperty:'id',
			totalProperty:'totalCount',
			root: 'records',
			fields:['id','name']
		});
		this.roleStore.load({params:{start:0,limit:10},callback:function(r){
			if(r[0]){
				rf.combo.setValue(r[0].get('id'));
				rf.firstStore.baseParams['bean.id'] = rf.combo.getValue();
				rf.secondStore.baseParams['bean.id'] = rf.combo.getValue();
				rf.firstStore.load({params:{start:0, limit:25}});
				rf.secondStore.load({params:{start:0, limit:25}});
			}
		}});
		
		this.combo = new Ext.form.ComboBox({
			store:this.roleStore,
			valueField:'id',
			displayField:'name',
			emptyText:'请选择角色...',
			hiddenName:'roleId',
			editable:false,
			triggerAction:'all',
			mode:'remote',
			resizable :true,
			pageSize:10,
			minListWidth:240,
			name:'roleName',
			listeners:{
	    	  select:function(combo, record, index){
					rf.firstStore.baseParams['bean.id'] = combo.getValue();
					rf.secondStore.baseParams['bean.id'] = combo.getValue();
	    	  		rf.firstStore.load({params:{start:0, limit:25}});
	    	  		rf.secondStore.load({params:{start:0, limit:25}});
	      		}
	      }});
		
		this.firstStore = new Ext.data.JsonStore({
			root:'records',
			idProperty:'id',
			remoteSort:true,
			totalProperty:'totalCount',
			fields:['name', 'id', 'model'],
	        url:'/wmeovg/sys/role_notfucs.action'
	    });
		//rf.firstStore .setDefaultSort('name', 'desc');
		
		this.firstGrid = new Ext.grid.GridPanel({
			title:'不属于该角色的功能',
			iconCls:'delete',
			ddGroup: 'secondGridDDGroup',
			loadMask:true,
			enableDragDrop   : true,
	        stripeRows       : true,
	        autoWidth:true,
		    height:300,
		    store: this.firstStore,
		    autoExpandColumn:'name',
		    columns: [{header:'功能',dataIndex:'name',sortable:true,width:20},
		              {header:'所属模块',dataIndex:'model',width:10}],
		    viewConfig: {
		        forceFit: true
		    },
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.firstStore,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有功能"
	        })
		});
		
		this.secondStore = new Ext.data.JsonStore({
			root:'records',
			totalProperty:'totalCount',
			idProperty:'id',
			remoteSort:true,
			fields:['name', 'id', 'model'],
	        url:'/wmeovg/sys/role_fucs.action'
	    });
		
		//rf.secondStore .setDefaultSort('name', 'desc');
		
		this.secondGrid = new Ext.grid.GridPanel({
			title:'该角色下的所有功能', 
			iconCls:'accept',
			ddGroup: 'firstGridDDGroup',
			loadMask:true,
			enableDragDrop   : true,
	        stripeRows       : true,
	        autoWidth:true,
		    height:300,
		    store: this.secondStore,
		    autoExpandColumn:'name',
		    columns: [{header:'功能',dataIndex:'name',sortable:true,width:20},
		              {header:'所属模块',dataIndex:'model',width:10}],
		    viewConfig: {
		        forceFit: true
		    },
		    bbar: new Ext.PagingToolbar({
	            pageSize: 25,
	            store: this.secondStore,
	            displayInfo: true,
	            displayMsg: '当前第{0} - {1}条 共{2}条记录',
	            emptyMsg: "暂时没有功能"
	        })
		    });
		
		this.displayPanel = new Ext.Panel({
			width:900,
			height       : 634,
			layout       : 'hbox',
			renderTo     : this.panelName,
			defaults     : { flex : 1 }, //auto stretch
			layoutConfig : { align : 'stretch' },
			items        : [
				this.firstGrid,
				this.secondGrid
			],
			tbar:['->', // Fill
			      this.combo,
			      '-',{
						text    : '刷新',
						iconCls:'refresh',
						handler : function() {
							rf.roleStore.reload();
							rf.firstStore.reload();
							rf.secondStore.reload();
						}
					}
			      ]
		});
		
	    /****
	     * Setup Drop Targets
	     ***/
	     // This will make sure we only drop to the  view scroller element
		this.firstGridDropTargetEl =  this.firstGrid.getView().scroller.dom;
		this.firstGridDropTarget = new Ext.dd.DropTarget(this.firstGridDropTargetEl, {
	             ddGroup    : 'firstGridDDGroup',
	             notifyDrop : function(ddSource, e, data){
	                     var records =  ddSource.dragData.selections;
	                     
	                     var ids = new Array();
	                     Ext.each(records, function(record,index,a){
	                    	 ids.push(record.id);
	                    	 ddSource.grid.store.remove(record);
	                     }, ddSource.grid.store);
	             		 
	             		 Ext.Ajax.request({url:'/wmeovg/sys/role_delFuc.action',
		    	            	success:function(result){
		    	    			//Ext.example.msg('提示', '删除成功。');
		    	    			
		                        rf.firstGrid.store.add(records);
	             			},params:{ids:ids, 'bean.id':rf.combo.getValue()}
	    	    		 });
	             		 //rf.secondGrid.store.sort('name', 'desc');
	                     //rf.firstGrid.store.sort('name', 'desc');
	                     return true;
	             }
	     });
	
	
	     // This will make sure we only drop to the view scroller element
		 this.secondGridDropTargetEl = this.secondGrid.getView().scroller.dom;
		 this.secondGridDropTarget = new Ext.dd.DropTarget(this.secondGridDropTargetEl, {
	             ddGroup    : 'secondGridDDGroup',
	             notifyDrop : function(ddSource, e, data){
	                     var records =  ddSource.dragData.selections;
	                     
	                     var ids = new Array();
	                     Ext.each(records, function(record, index, a){
	                    	 ids.push(record.id);
	                    	 ddSource.grid.store.remove(record);
	                     }, ddSource.grid.store);
	                    
	                     Ext.Ajax.request({url:'/wmeovg/sys/role_addFuc.action',
		    	            	success:function(result){
		    	    			//Ext.example.msg('提示', '添加成功。');
		    	    			
		    	    			rf.secondGrid.store.add(records);
	          				},params:{ids:ids, 'bean.id':rf.combo.getValue()}
	                     });
	                     //rf.firstGrid.store.sort('name', 'desc');
	                     //rf.secondGrid.store.sort('name', 'desc');
	                     return true;
	             }
	     });
	}
	
	new RoleFuc('rf-panel');
});