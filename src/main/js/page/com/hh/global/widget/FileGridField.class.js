Ext
		.define(
				"com.hh.global.widget.FileGridField",
				{
					extend : 'Ext.form.FieldContainer',
					alias : 'widget.widgetFileGridField',
					mixins : {
						bindable : 'Ext.util.Bindable',
						field : 'Ext.form.field.Field'
					},
					hi_add : false,
					hi_delete:false,
					hi_tbar :false,
					layout : 'fit',
//					height : 120,
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
						var me = this;

						var store = Ext.create('Ext.data.Store', {
							fields : [ 'path', 'name', 'attachmentFileName' ],
							data : {
								'items' : me.config.value
							},
							proxy : {
								type : 'memory',
								reader : {
									type : 'json',
									root : 'items'
								}
							}
						});

						this.store = store;

						var varGrid = this.grid = Ext
								.create(
										'Ext.grid.Panel',
										{
											plugins : Ext
													.create('Ext.grid.plugin.CellEditing'),
											store : store,
											border : true,
											title : this.title,
											columns : [
													{
														header : 'path',
														dataIndex : 'path',
														flex : 1,
														hidden : true
													},
													{
														header : '名称',
														dataIndex : 'name',
														flex : 1
													},
													{
														header : '附件名称',
														dataIndex : 'attachmentFileName',
														flex : 1,
														renderer : function(
																value, p, data) {
															return Ext.String
																	.format(
																			'<a href="javascript:Href.download(\''
																					+ data.data.attachmentFileName
																					+ '\',\''
																					+ data.data.path
																					+ '\');">{0}</a>',
																			value);
														}
													} ],
											tbar : me.getToolbar()
										});
						this.add(varGrid);
					},
					getToolbarItem : function(type) {
						var panel = this;
						if (type == 'add') {
							return {
								iconCls : 'add',
								text : '添加',
								handler : function() {
									panel.doAdd();
								}
							};
						} else if (type == 'delete') {
							return {
								iconCls : 'delete',
								text : '删除',
								handler : function() {
									panel.doDelete();
								}
							};
						} 
					},
					getTbarItems : function() {
						var page = this;
						var tbaritems = [];
						if(!page.hi_add){
							tbaritems.push(this.getToolbarItem("add"));
						}
						if(!page.hi_delete){
							tbaritems.push(this.getToolbarItem("delete"));
						}
						return tbaritems;
					},
					getToolbar : function() {
						var page = this;
						if(page.hi_tbar==true){
							return null;
						}else{
							return Ext.create('com.hh.base.BaseToolbar', {
								items : page.getTbarItems()
							});
						}
					},
					doAdd : function() {
						var page = this;
						Desktop.openWindow("com.hh.global.widget.FileWindow", {
							addWindow : false,
							filePath : this.config.filePath,
							parentPanel : page
						});
					},
					doDelete : function() {
						var grid = this.grid;
						var records = grid.getSelectionModel().getSelection();
						grid.getStore().remove(records);
					},
					getValue : function() {
						var objectList = [];
						if (this.grid != null) {
							this.grid.getStore().each(function(record) {
								objectList.push(record.data);
							});
						}
						return Json.objTostr(objectList);
					},
					setValue : function(value) {
						this.grid.getStore().removeAll();
						if (!Util.isNull(value)) {
							var objectList = Ext.decode(value);
							for ( var i = 0; i < objectList.length; i++) {
								this.grid.getStore().add(objectList[i]);
							}
						}
					},
					getSubmitData : function() {
						var object = {};
						object[this.getName()] = this.getValue();
						return object;
					}
				});