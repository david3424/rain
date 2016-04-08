
Ext.onReady(function(){

	var roleuser = {};

	roleuser.roleStore = new Ext.data.JsonStore({
		url: '/wmeovg/sys/role_all.action',
		idProperty:'id',
		totalProperty:'totalCount',
		root: 'records',
		fields:['id','name']
	});
	roleuser.roleStore.load({params:{start:0,limit:10},callback:function(r){
		if(r[0]){
			roleuser.combo.setValue(r[0].get('id'));
			roleuser.firstStore.baseParams['bean.id'] = roleuser.combo.getValue();
			roleuser.secondStore.baseParams['bean.id'] = roleuser.combo.getValue();
	  		roleuser.firstStore.load({params:{start:0, limit:25}});
	  		roleuser.secondStore.load({params:{start:0, limit:25}});
		}
	}});
	
	roleuser.combo = new Ext.form.ComboBox({
		store:roleuser.roleStore,
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
				roleuser.firstStore.baseParams['bean.id'] = combo.getValue();
				roleuser.secondStore.baseParams['bean.id'] = combo.getValue();
    	  		roleuser.firstStore.load({params:{start:0, limit:25}});
    	  		roleuser.secondStore.load({params:{start:0, limit:25}});
      		}
      }});
	
	roleuser.firstStore = new Ext.data.JsonStore({
		root:'records',
		idProperty:'id',
		remoteSort:true,
		totalProperty:'totalCount',
		fields:['name', 'id', 'loginName'],
        url:'/wmeovg/sys/role_notusers.action'
    });
	//roleuser.firstStore .setDefaultSort('name', 'desc');
	
	roleuser.firstGrid = new Ext.grid.GridPanel({
		title:'不属于该角色的用户',
		iconCls:'delete',
		ddGroup: 'secondGridDDGroup',
		loadMask:true,
		enableDragDrop   : true,
        stripeRows       : true,
        autoWidth:true,
	    height:300,
	    store: roleuser.firstStore,
	    autoExpandColumn:'loginName',
	    columns: [{header:'姓名',dataIndex:'name',sortable:true,width:10},
	              {header:'用户名',dataIndex:'loginName',width:20}],
	    viewConfig: {
	        forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: roleuser.firstStore,
            displayInfo: true,
            displayMsg: '当前第{0} - {1}条 共{2}条记录',
            emptyMsg: "暂时没有用户"
        })
	});
	
	roleuser.secondStore = new Ext.data.JsonStore({
		root:'records',
		totalProperty:'totalCount',
		idProperty:'id',
		remoteSort:true,
		fields:['name', 'id', 'loginName'],
        url:'/wmeovg/sys/role_users.action'
    });
	
	//roleuser.secondStore .setDefaultSort('name', 'desc');
	
	roleuser.secondGrid = new Ext.grid.GridPanel({
		title:'该角色下的所有用户', 
		iconCls:'accept',
		ddGroup: 'firstGridDDGroup',
		loadMask:true,
		enableDragDrop   : true,
        stripeRows       : true,
        autoWidth:true,
	    height:300,
	    store: roleuser.secondStore,
	    autoExpandColumn:'loginName',
	    columns: [{header:'姓名',dataIndex:'name',sortable:true,width:10},
	              {header:'用户名',dataIndex:'loginName',width:20}],
	    viewConfig: {
	        forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
            pageSize: 25,
            store: roleuser.secondStore,
            displayInfo: true,
            displayMsg: '当前第{0} - {1}条 共{2}条记录',
            emptyMsg: "暂时没有用户"
        })
	    });
	
	roleuser.displayPanel = new Ext.Panel({
		width:900,
		height       : 634,
		layout       : 'hbox',
		renderTo     : 'roleuser-panel',
		defaults     : { flex : 1 }, //auto stretch
		layoutConfig : { align : 'stretch' },
		items        : [
			roleuser.firstGrid,
			roleuser.secondGrid
		],
		tbar:['->', // Fill
		      roleuser.combo,
		      '-',{
					text    : '刷新',
					iconCls:'refresh',
					handler : function() {
						roleuser.roleStore.reload();
						roleuser.firstStore.reload();
						roleuser.secondStore.reload();
					}
				}
		      ]
	});
	
    /****
     * Setup Drop Targets
     ***/
     // This will make sure we only drop to the  view scroller element
     roleuser.firstGridDropTargetEl =  roleuser.firstGrid.getView().scroller.dom;
     roleuser.firstGridDropTarget = new Ext.dd.DropTarget(roleuser.firstGridDropTargetEl, {
             ddGroup    : 'firstGridDDGroup',
             notifyDrop : function(ddSource, e, data){
                     var records =  ddSource.dragData.selections;
                     
                     var ids = new Array();
                     Ext.each(records, function(record,index,a){
                    	 ids.push(record.id);
                    	 ddSource.grid.store.remove(record);
                     }, ddSource.grid.store);
             		 
             		 Ext.Ajax.request({url:'/wmeovg/sys/role_delUser.action',
	    	            	success:function(result){
	    	    			//Ext.example.msg('提示', '删除成功。');
	    	    			
	                        roleuser.firstGrid.store.add(records);
             			},params:{ids:ids, 'bean.id':roleuser.combo.getValue()}
    	    		 });
             		 //roleuser.secondGrid.store.sort('name', 'desc');
                     //roleuser.firstGrid.store.sort('name', 'desc');
                     return true;
             }
     });


     // This will make sure we only drop to the view scroller element
     roleuser.secondGridDropTargetEl = roleuser.secondGrid.getView().scroller.dom;
     roleuser.secondGridDropTarget = new Ext.dd.DropTarget(roleuser.secondGridDropTargetEl, {
             ddGroup    : 'secondGridDDGroup',
             notifyDrop : function(ddSource, e, data){
                     var records =  ddSource.dragData.selections;
                     
                     var ids = new Array();
                     Ext.each(records, function(record, index, a){
                    	 ids.push(record.id);
                    	 ddSource.grid.store.remove(record);
                     }, ddSource.grid.store);
                    
                     Ext.Ajax.request({url:'/wmeovg/sys/role_addUser.action',
	    	            	success:function(result){
	    	    			//Ext.example.msg('提示', '添加成功。');
	    	    			
	    	    			roleuser.secondGrid.store.add(records);
          				},params:{ids:ids, 'bean.id':roleuser.combo.getValue()}
                     });
                     //roleuser.firstGrid.store.sort('name', 'desc');
                     //roleuser.secondGrid.store.sort('name', 'desc');
                     return true;
             }
     });
});