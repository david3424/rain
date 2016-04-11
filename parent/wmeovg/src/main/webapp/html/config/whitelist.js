
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
	    this.searchFormId = divPrefix + "-searchForm";
	    this.exportFormId = divPrefix + "-exportForm";
	    
	    this.clientInfoStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/clientinfo_list.action',
			root: 'records',
			fields:['id','name']
		});
	    instance.clientInfoStore.load({params:{start:0,limit:20,query:''}});
	    this.gameStore = new Ext.data.JsonStore({
			idProperty:'id',
			totalProperty:'totalCount',
			url: '/wmeovg/config/game_list.action',
			root: 'records',
			fields:['id','name']
		});
	    instance.gameStore.load({params:{start:0,limit:20,query:''}});
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
                fieldLabel:'邮件标题',
                name:'entity.title',
                //autoCreate:{tag:'input',type:'text', maxlength:'20'},
                allowBlank:true
            },{
                fieldLabel:'邮件正文',
                name:'entity.text',
                //autoCreate:{tag:'input',type:'text', maxlength:'20'},
                allowBlank:true
            }
                ,{
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
                fieldLabel:'邮件标题',
                name:'entity.title',
                //autoCreate:{tag:'input',type:'text', maxlength:'20'},
                allowBlank:true
            },{
                fieldLabel:'邮件正文',
                name:'entity.text',
                //autoCreate:{tag:'input',type:'text', maxlength:'20'},
                allowBlank:true
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
			fields:['id', 'clientName', 'game', 'appid', 'prizename', 'prizeid', 'title','text','count', 'sendCount', 'failCount', 'requestCount', 'callbackCount','user', 'createtime', 'status'],
	        url:'/wmeovg/config/whitelist_list.action'
	    });
		var baseparams = {limit:20};
		this.store.baseParams = baseparams;
		
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
				return ('<center><span disabled>接受</span>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="updateStatus('+ record.get('id') +', \''+ escape(record.get('clientName')) +'\',\''+ record.get('appid') +'\',\''+ escape(record.get('prizename')) +'\',-1)" href="javascript:void(0)" style="color:red;">拒绝</a></center>');
			else if(value ==-1)
				return ('<center><a onclick="updateStatus('+ record.get('id') +',\''+ escape(record.get('clientName')) +'\', \''+ record.get('appid') +'\',\''+ escape(record.get('prizename')) +'\',0)" href="javascript:void(0)" style="color:green;">接受</a>&nbsp;&nbsp;&nbsp;&nbsp;<span disabled>拒绝</span></center>');
		}

		this.sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
		this.cm = new Ext.grid.ColumnModel([
		                 	              //new Ext.grid.RowNumberer(),
		                 	              this.sm,
		                 	              {header:'发送客户端',dataIndex:'clientName',sortable:true,width:30},
		                 	              {header:'应用标识',dataIndex:'appid',sortable:true,width:20},
		                	              {header:'物品名称',dataIndex:'prizename',sortable:true,width:20},
		                	              {header:'物品ID',dataIndex:'prizeid',sortable:true,width:15},
                                          {header:'邮件标题',dataIndex:'title',sortable:true,width:20},
                                          {header:'邮件正文',dataIndex:'text',sortable:true,width:25},
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
	    	//	}});
	    	//}});
		}
		/*
		var fbutton = new Ext.ux.form.FileUploadField({
	        renderTo: 'fi-button',
	        buttonOnly: true,
	        buttonText:'选择文件',
	        name:'exportFile',
	        listeners: {
	            'fileselected': function(fb, v){
					alert(v)
	            }
	        }
	    });*/
		
		this.uploadForm = new Ext.FormPanel({
	        fileUpload: true,
	        frame:true,
			border:false,
			autoHeight: true,
			labelWidth: 65,
			defaults: {
	            anchor: '95%',
	            allowBlank: false,
	            msgTarget: 'side'
	        },
	        items: [{
	            xtype: 'fileuploadfield',
	            emptyText: '选择文件，目前只支持Excel 2007或2010文件',
	            fieldLabel: '请选择文件',
	            name: 'upload',
	            buttonText: '',
	            buttonCfg: {
	                iconCls: 'upload-icon'
	            }
	        	/*,
		        listeners: {
		            'fileselected': function(fb, v){
						alert(v)
		            }
		        }*/
	        },{
	        	xtype:'label',
	        	html:'Excel格式为：应用标识（>5字符），物品名称，物品ID，发送总量，邮件标题（可为空），邮件正文（可为空）'
	        }]
		});
		
		this.uploadWin = new Ext.Window({
			title:'导入（第一步 文件上传）',
			renderTo:Ext.getBody(),
			layout:'fit',
			plain:true,
			modal:true,
			width:450,
			autoHeight:true,
			resizable:false,
			closeAction:'hide',
			border:false,
			items: [this.uploadForm],
			buttons:[{
				text:'确定',
				handler:function(){
				if(instance.uploadForm.getForm().isValid()){
					instance.uploadWin.hide();
					instance.uploadForm.getForm().submit({
	                    url: '/wmeovg/upload/dataUpload.action',
	                    method:'post',
	                    waitMsg: '正在上传并验证文件...',
	                    success: function(form, action){
							instance.uploadWin.hide();
							instance.loadExportPreview(action.result.filename);
							form.reset();
	                    },
						failure:function(form, action){
	                    	form.reset();
	                    	Ext.example.msg('提示', action.result.msg);
						}
	                });
                }
			}
			},{
				text:'重置',
				handler:function(){instance.uploadForm.getForm().reset();}
			},{
				text:'取消',
				handler:function(){instance.uploadWin.hide();}
			}],
			buttonAlign:'center'
		});
		
		
		this.loadExportPreview = function(filename){
			
			Ext.getBody().mask('正在进行数据解析，请稍后···');
			var store = new Ext.data.JsonStore( {
				root : 'beans',
				fields : [ 'appid', 'prizename', 'prizeid', 'count','title','text' +
                    '' ],
				url : '/wmeovg/upload/data_preview.action?filename=' + filename
			});

			var sm = new Ext.grid.CheckboxSelectionModel( {
				handleMouseDown : Ext.emptyFn
			});
			var cm = new Ext.grid.ColumnModel( [
					{
						header : '应用标识',
						dataIndex : 'appid',
						width : 30
					}, {
						header : '物品名称',
						dataIndex : 'prizename',
						width : 20
					}, {
						header : '物品ID',
						dataIndex : 'prizeid',
						width : 10
					},{
						header : '发送总量',
						dataIndex : 'count',
						width : 15
					},{
                        header : '邮件标题',
                        dataIndex : 'title',
                        width : 20
                   },
                   {
                        header : '邮件正文',
                        dataIndex : 'text',
                        width : 20
                   }]);
			
			store.load({callback:function(){
					var success = this.reader.jsonData.success;
					if(!success){
						Ext.example.msg('提示', "解析失败：" + this.reader.jsonData.msg);
						Ext.getBody().unmask();
						return;
					}
					var zeroCount = this.reader.jsonData.zeroCount;
					var xmlTotalCount = this.reader.jsonData.xmlTotalCount;
					var xmlRepeatCount = this.reader.jsonData.xmlRepeatCount;
					var appid = this.reader.jsonData.appid;

					var items=[{
						name:'filename',
						value:filename,
						xtype:'hidden'
					},{
						fieldLabel:'发送客户端',
						xtype:'combo',
						allowBlank:false,
						editable:false,
						store:instance.clientInfoStore,
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
						store:instance.gameStore,
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
					}];
					if(appid>0){
						items.push({
							fieldLabel : '应用标识',
							allowBlank : false,
							name : 'entity.appid',
							emptyText : '默认应用标识('+appid+'行)',
							minLength : 5,
							//autoCreate:{tag:'input',type:'text', maxlength:'15'},
							regex : /^[a-z]+[0-9_a-z]*$/,
							regexText : '只能输入数字、小写字母或下划线(必须以字母开头)'
						});
					}
					if(zeroCount>0){
						
						items.push({
							fieldLabel : '发送总量',
							combineErrors : false,
							xtype : 'compositefield',
							items : [ {
								name : 'entity.count',
								xtype : 'numberfield',
								value : '150000',
								emptyText : '默认总量('+zeroCount+'行)',
								allowBlank : false,
								allowDecimals : false,
								allowNegative : false,
								width : 90
							}, {
								name : 'iscount',
								xtype : 'checkbox',
								boxLabel : '无上限',
								listeners:{
									"check":function(){
										var countField = exportForm.getForm().findField('entity.count');
										if(this.checked){
											countField.setValue("-1");
											countField.disable();
										}else{
											countField.enable();
											countField.setValue("150000");
										}
									}
								},
								width : 80
							} ]
						});
					}
					var exportForm = new Ext.FormPanel( {
						//renderTo : instance.exportFormId,
						frame : true,
						labelAlign : 'left',
						bodyStyle : 'padding:5px',
						layout : 'column',
						items : [
								{
									columnWidth : 0.6,
									layout : 'fit',
									items : {
										xtype : 'grid',
										height : 300,
										loadMask : true,
										store : store,
										cm : cm,
										sm : sm,
										viewConfig : {
											forceFit : true
										}
									}
								},
								{
									columnWidth : 0.4,
									layout : 'anchor',
									items:[{
										xtype:'label',
										height:'5%',
										width:'100%',
										html:'&nbsp;&nbsp;&nbsp;<font style="font-weight: bolder; font-size:12px;">必填的默认值</font><p/>'
									},{
									height:'80%',
									width:'100%',
									xtype : 'fieldset',
									labelWidth : 70,
									//title : '必填的默认值',
									defaultType : 'textfield',
									autoHeight : true,
									border : false,
									items :items,
									buttons:[{
										text:'导入',
										handler:function(){
										if(exportForm.getForm().isValid()){
											exportForm.getForm().submit({
													waitMsg:'正在导入数据，请稍后···',
													url:'/wmeovg/upload/data_commit.action',
													method:'post',
													success:function(form,action){
													Ext.example.msg('提示', '导入成功（成功:'
															+action.result.successCount +
															'个  失败:' + action.result.failCount +
															'个  重复:' + action.result.repeatCount +'个）。');
													exportWin.hide();
													
													instance.resetSearchForm();
												},
												failure:function(form, action){
													
													Ext.example.msg('提示', action.result.msg);
												}
											});
										}
									}
									},{
										text:'校验',
										handler:function(){

											if(exportForm.getForm().isValid()){
												exportForm.getForm().submit({
														//waitMsg:'正在校验数据，请稍后···',
														url:'/wmeovg/upload/data_check.action',
														method:'post',
														success:function(form,action){
														//alert(Ext.encode(action.result))
														str = '（校验结果：'
															+action.result.successCount +
															'个 正确:' + action.result.repeatCount +'个重复）。';
														Ext.example.msg('提示', str);
														store.loadData(action.result);
														Ext.get('feedback-info').update(str);
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
											store.reload();
											exportForm.getForm().reset();
											Ext.get('feedback-info').update();
										}
									}],
									buttonAlign:'center'
								},{
									id:'feedback-info',
									xtype:'label',
									height:'15%',
									width:'100%'
								} ]
								}]
					});
					
					var exportWin = new Ext.Window({
						title : '导入（第二步完善物品信息）',
						renderTo:Ext.getBody(),
						layout:'fit',
						plain:true,
						modal:true,
						width:1200,
						height : 350,
						resizable:true,
						closeAction:'hide',
						border:false,
						items: [exportForm]
					});
					
					Ext.getBody().unmask();
					Ext.get('feedback-info').update("原始文件：总共" + xmlTotalCount+"条记录，其中" + xmlRepeatCount + "条重复");
					exportWin.show();
				}});
		};
		
		this.grid = new Ext.grid.GridPanel({
			id:'white-list-grid',
			renderTo:this.gridId,
			width:1400,
		    autoHeight:true,
		    loadMask:true,
		    store: this.store,
		    autoExpandColumn:'clientName',
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
			    			msg:'确定接受这些物品的兑换请求（<font color="red">请谨慎操作</font>）？',
			    			buttons:Ext.MessageBox.YESNOCANCEL,
			    			
			    			fn:function(btn){
			    			if(btn=='yes'){
			    				
			    	    		Ext.Ajax.request({url:'/wmeovg/config/whitelist_accept.action',
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
			    			msg:'确定拒绝这些物品的兑换请求（<font color="red">请谨慎操作</font>）？',
			    			buttons:Ext.MessageBox.YESNOCANCEL,
			    			
			    			fn:function(btn){
			    			if(btn=='yes'){
			    				
			    	    		Ext.Ajax.request({url:'/wmeovg/config/whitelist_reject.action',
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
		    },'->',{
		    	text:'导入',
		    	iconCls:'export-icon',
		    	handler: function(){
					instance.uploadWin.show();
				}
		    }],
		    bbar: new Ext.PagingToolbar({
	            pageSize: 20,
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
		
		this.resetSearchForm = function(){
			
			instance.searchForm.getForm().reset();
			delete instance.store.baseParams;
			instance.store.reload({params:{start:0, limit:20},baseParams:baseparams});
		};
		
		this.searchForm = new Ext.form.FormPanel({
			//region : 'north',
			border:false,
			renderTo: this.searchFormId,
			width:250,
			labelAlign:'right',
			labelWidth:60,
			frame:true,
			//baseCls: 'x-plain',
			border:false,
			defaults:{xtype:'textfield', width:160},
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
				hiddenName:'entity.clientInfo.id',
				//editable:false,
				minChars:2, // 输入字符多于2个时开始搜索
				triggerAction:'all',
				name:'entity.clientInfo.id'
			},{
				fieldLabel:'应用标识',
				name:'entity.appid',
				minLength:5,
				//autoCreate:{tag:'input',type:'text', maxlength:'15'},
				regex:/^[a-z]+[0-9_a-z]*$/,
				regexText:'只能输入数字、小写字母或下划线(必须以字母开头)'
				
			},{
				fieldLabel:'物品ID',
				name:'entity.prizeid',
				xtype:'numberfield',
				allowDecimals: false,
				allowNegative:false
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
					if(instance.searchForm.getForm().isValid()){
						instance.store.baseParams = instance.store.baseParams || baseparams;
						Ext.apply(instance.store.baseParams, instance.searchForm.getForm().getValues());
	
						instance.store.reload();
					}
				}
			},{
				text:'重置',
				handler:this.resetSearchForm
			}],
			buttonAlign:'center'});
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.acceptBtn.setDisabled(sm.getCount() < 1);
	    });
		
		instance.grid.getSelectionModel().on('selectionchange', function(sm){
			instance.grid.rejectBtn.setDisabled(sm.getCount() < 1);
	    });
	}
	
	var whitelistList = new PageList('whitelist');
	whitelistList.store.load({params:{start:0, limit:20}});
});