
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
			fields:['id', 'name', 'loginName', 'content',  'createtime', 'ip'],	        
	        url:'/wmeovg/sys/log_list.action',
	        baseParams: {limit:25}
	    });
		
		this.store.setDefaultSort('id', 'desc');
		
		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                	              {header:'用户',dataIndex:'name',sortable:true,width:20},
		                	              {header:'账号',dataIndex:'loginName',sortable:true,width:20},
		                	              {header:'操作内容',dataIndex:'content',sortable:true,width:70},
		                	              {header:'操作时间', dataIndex:'createtime',width:20},
		                	              {header:'IP', dataIndex:'ip',width:15}]);
		
		this.grid = new Ext.grid.GridPanel({
			renderTo:this.gridId,
			width:900,
		    autoHeight:true,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'content',
		    cm: this.cm ,
		    viewConfig: {
		        forceFit: true
		    },
		    sm: this.sm,
		    tbar:['操作内容查询:',
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
	}
	
	var logList = new PageList('log');
	logList.store.load({params:{start:0, limit:25}});
});