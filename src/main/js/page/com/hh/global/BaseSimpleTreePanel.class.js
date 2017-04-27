Ext.define('com.hh.global.BaseSimpleTreePanel', {
			extend : 'com.hh.base.BasePanel',
			alias : 'widget.widgetBaseSimpleTreePanel',
			region : 'west',
			layout : 'fit',
			split : true,
			collapsible : true,
			border : true,
			padding : '1 1 1 1',
			title : null,
			query_action : null,
			delete_action : null,
			editPage : null,
			hi_add : false,
			hi_addRoot : false,
			hi_update : false,
			hi_delete : false,
			hi_expandAll : false,
			hi_collapseAll : false,
			constructor : function(config) {
				this.config = config || {};
				this.callParent(arguments);

				var tree = this.createTreePanel();
				this.tree = tree;
				this.add(tree);
			},
			getRightMenuItems : function() {
			},
			getRightMenu : function() {
				var panel = this;
				return Ext.create('Ext.menu.Menu', {
							items : panel.getRightMenuItems()
						});
			},
			getContainerRightMenuItems : function() {
			},
			getContainerRightMenu : function() {
				var panel = this;
				return Ext.create('Ext.menu.Menu', {
							items : panel.getContainerRightMenuItems()
						});
			},
			createTreePanel : function() {
				this.store = this.getTreeStore();
				this.tree = Ext.create('com.hh.base.BaseTreePanel', {
							tbar : this.getTbar(),
							bbar : this.getBbar(),
							store : this.getTreeStore(),
							border : false,
							listeners : this.getTreePanelListeners(),
							collapsible : false
						});
				if (this.getRightMenuItems() != null
						&& this.getRightMenuItems().length > 0) {
					var rightMenu = this.getRightMenu();
					this.tree.on('itemcontextmenu', function(a, b, c, d, e) {
								rightMenu.showAt(e.getXY());
								rightMenu.doConstrain();
								e.stopEvent();
							}, this);
				}

				if (this.getContainerRightMenuItems() != null
						&& this.getContainerRightMenuItems().length > 0) {
					var containeRrightMenu = this.getContainerRightMenu();
					this.tree.on('containercontextmenu', function(view, e) {
								containeRrightMenu.showAt(e.getXY());
								containeRrightMenu.doConstrain();
								e.stopEvent();
							}, this);
				}

				return this.tree;
			},
			getTreePanelListeners : function() {

			},
			extraParams : {},
			getExtraParams : function() {
				return this.extraParams;
			},
			setExtraParams : function(extraParams) {
				this.extraParams = extraParams;
			},
			getTreeStore : function() {
				var page = this;
				if (this.store) {
					return this.store;
				}
				this.store = Ext.create('Ext.data.TreeStore', {
							root : {},
							proxy : {
								type : 'ajax',
								url : page.query_action,
								extraParams : this.getExtraParams()
							}
						});
				return this.store;
			},
			getBbar : function() {
				return null;
				// var page = this;
				// return Ext.create('com.hh.base.BaseToolbar', {
				// items : page.getBbarItems()
				// });
			},
			getBbarItems : function() {
				return [this.getToolbarItem("refresh")];
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
				} else if (type == 'addRoot') {
					return {
						iconCls : 'add',
						text : '添加根节点',
						handler : function() {
							panel.doAddRoot();
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
				} else if (type == 'update') {
					return {
						iconCls : 'update',
						text : '编辑',
						handler : function() {
							panel.doUpdate();
						}
					};
				} else if (type == 'expandAll') {
					return {
						iconCls : 'expand',
						text : '展开',
						handler : function() {
							panel.tree.collapseAll();
							panel.tree.expandAll();
						}
					};
				} else if (type == 'collapseAll') {
					return {
						iconCls : 'collapse',
						text : '收缩',
						handler : function() {
							panel.tree.collapseAll();
						}
					};
				} else if (type == "refresh") {
					return {
						iconCls : 'table_refresh',
						text : '刷新',
						handler : function() {
							panel.tree.getStore().load(	);
						}
					};
				} else if (type == 'up') {
					return {
						iconCls : 'up',
						text : '上移',
						handler : function() {
							panel.orderUp();
						}
					};
				} else if (type == 'down') {
					return {
						iconCls : 'down',
						text : '下移',
						handler : function() {
							panel.orderDown();
						}
					};
				}
			},
			getExBeTbarItems : function() {
				return [];
			},
			getTbarItems : function() {
				var toolbarItems = [];

				var exBetoolbarItems = this.getExBeTbarItems();

				for (var i = 0; i < exBetoolbarItems.length; i++) {
					toolbarItems.push(exBetoolbarItems[i]);
				}
				if (this.editPage != null) {
					if (!this.hi_add) {
						toolbarItems.push(this.getToolbarItem("add"));
					}
					if (!this.hi_addRoot) {
						toolbarItems.push(this.getToolbarItem("addRoot"));
					}
					if (!this.hi_delete) {
						toolbarItems.push(this.getToolbarItem("update"));
					}
					if (!this.hi_update) {
						toolbarItems.push(this.getToolbarItem("delete"));
					}
				}

				if (!this.hi_expandAll) {
					toolbarItems.push(this.getToolbarItem("expandAll"));
				}
				if (!this.hi_collapseAll) {
					toolbarItems.push(this.getToolbarItem("collapseAll"));
				}
				toolbarItems.push(this.getToolbarItem("refresh"));
				return toolbarItems;
			},
			getTbar : function() {
				var panel = this;
				return Ext.create('com.hh.base.BaseToolbar', {
							enableOverflow : true,
							items : panel.getTbarItems()
						});
			},
			doAddRoot : function() {
				var page = this;
				var editPageUrl = this.editPage;
				ExtUtil.open(editPageUrl, {
							addType : 'root',
							parentPanel : this,
							callbackRefresh : function() {
								page.store.load();
							}
						});
			},
			doAdd : function() {
				var page = this;
				// var record = page.tree.getSelectionModel().getSelection()[0];
				var record = page.tree.getSelectionModel().lastFocused;
				if (record != null) {
					if (record.get('leaf')) {
						ExtFrame.info('不能给叶子节点添加子节点！！');
						return;
					}
				}
				var editPageUrl = this.editPage;
				ExtUtil.open(editPageUrl, {
							parentPanel : this,
							selectRecord : record,
							callbackRefresh : function() {
								page.store.load();
							}
						});
			},
			doDelete : function() {
				var panel = this;
				GridPanel.deleteByGrid(this.tree, panel.delete_action);
			},
			getSelect : function() {
				return this.tree.getSelectionModel().lastFocused;
			},
			doUpdate : function() {
				var page = this;
				var editPageUrl = this.editPage;
				// var record = this.tree.getSelectionModel().getSelection();
				var record = page.getSelect();
				if (Util.isNull(record)) {
					ExtFrame.info('请选中要编辑的数据！');
				} else {
					ExtUtil.open(editPageUrl, {
								parentPanel : this,
								parentRecord : record,
								callbackRefresh : function() {
									page.store.load();
								}
							});
				}
			},
			orderUp : function() {
				var page = this;
				var tree = page.tree;
				var store = tree.getStore();
				var record = tree.getSelectionModel().getLastSelected();
				if (Util.isNull(record)) {
					ExtFrame.msg("请选中要上移的数据！");
				} else {
					record = store.getNodeById(record.get("id"));
					var recordup = record.previousSibling;
					if (recordup != null) {
						Request.request(page.action + 'order', {
									id1 : record.get("id"),
									id2 : recordup.get("id"),
									order1 : record.raw.order,
									order2 : recordup.raw.order
								}, function(result) {
									if (result.isSuccess == true) {
										var parentNode = record.parentNode;
										parentNode.removeChild(record);
										var uporder = record.raw.order;
										record.raw.order = recordup.raw.order;
										recordup.raw.order = uporder;
										parentNode.insertChild(parentNode
														.indexOf(recordup),
												record);
									}
								}, {
									isDefaultMsg : true
								});
					} else {
						ExtFrame.msg("已经是第一个了");
					}
				}
			},
			orderDown : function() {
				var page = this;
				var tree = page.tree;
				var store = tree.getStore();
				var record = tree.getSelectionModel().getLastSelected();
				if (Util.isNull(record)) {
					ExtFrame.msg("请选中要下移的数据！");
				} else {
					record = store.getNodeById(record.get("id"));
					var recordup = record.nextSibling;
					if (recordup != null) {
						Request.request(page.action + 'order', {
									id1 : record.get("id"),
									id2 : recordup.get("id"),
									order1 : record.raw.order,
									order2 : recordup.raw.order
								}, function(result) {
									if (result.isSuccess == true) {
										var parentNode = record.parentNode;
										parentNode.removeChild(record);
										var uporder = record.raw.order;
										record.raw.order = recordup.raw.order;
										recordup.raw.order = uporder;
										parentNode.insertChild(parentNode
														.indexOf(recordup)
														+ 1, record);
									}
								}, {
									isDefaultMsg : true
								});
					} else {
						ExtFrame.msg("已经是最后一个了");
					}
				}
			}
		});