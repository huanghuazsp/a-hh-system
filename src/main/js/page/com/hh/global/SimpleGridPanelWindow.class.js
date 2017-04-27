Ext.define('com.hh.global.SimpleGridPanelWindow', {
	extend : 'com.hh.base.BaseServicePanel',
	isInit : true,
	hi_add : false,
	hi_update : false,
	hi_delete : false,
	hi_search : false,
	hi_up : true,
	hi_down : true,
	hi_bbar : false,
	toolbarItems : [],
	open : function(config) {
		var searchPanelItems = this.getSearchPanelItems();
		var items = [];
		if (searchPanelItems.length > 0) {
			// searchPanelItems.push({
			// name : 'orgCode',
			// xtype : 'widgetComboBoxTree',
			// fieldLabel : '组织机构',
			// columnWidth : 0.3,
			// action : 'usersystem-App-queryCurrOrgTree',
			// extraParams : {
			// action : this.action + this.gridAction
			// }
			// });
			items.push(this.createSearchPanel(searchPanelItems));
		}
		items.push(this.createGrid());
		this.add(items);
	},
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
		this.open(config);
	},
	editPage : null,
	pkey : 'id',
	addText : '新增',
	deleteText : '删除',
	updateText : '修改',

	addsurl : null,
	deletesurl : null,
	updatesurl : null,

	searchText : '搜索',
	getSearchPanelItems : function() {
		return [];
	},
	getEditPage : function() {
		return this.editPage;
	},
	searchPanelMaxHeighe : null,
	createSearchPanel : function(items) {
		this.searchPanel = Ext.create('com.hh.base.BaseFormPanel', {
					region : 'north',
					title : '按条件查询',
					items : items,
					height : this.searchPanelMaxHeighe,
					collapsible : true,
					padding : '1 1 1 1',
					collapsed : true,
					buttons : []
				});
		return this.searchPanel;
	},
	gridListeners : null,
	getGridListeners : function() {
		this.gridListeners;
	},
	setGridListeners : function(gridListeners) {
		this.gridListeners = gridListeners;
	},
	getStoreFields : function() {
		return ['id', 'text'];
	},
	getGridColumns : function() {
		return [{
					dataIndex : 'id',
					flex : 1,
					hidden : true
				}, {
					text : '名称',
					dataIndex : 'text',
					flex : 1
				}];
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
	getTbars : function() {
		return [];
	},
	getSelectTbarItems : function() {
		return [];
	},
	selectTbar : null,
	selectTbarToNull : function() {
		var panel = this;
		if (panel.selectTbar) {
			var selectTbarItem = panel.selectTbar.items.items;
			for (var i = 0; i < selectTbarItem.length; i++) {
				if (selectTbarItem[i]) {
					if (selectTbarItem[i].reset) {
						selectTbarItem[i].reset();
					}
				}
			}
		}
	},
	getSelectTbarValues : function() {
		var panel = this;
		var obj = {};
		if (panel.selectTbar) {
			var selectTbarItems = panel.selectTbar.items.items;
			for (var i = 0; i < selectTbarItems.length; i++) {
				var selectTbarItem = selectTbarItems[i];
				if (selectTbarItem) {
					if (selectTbarItem.getName && selectTbarItem.getValue
							&& selectTbarItem.getName()) {
						if (selectTbarItem.xtype != 'widgetRadioGroup') {
							obj[selectTbarItem.getName()] = selectTbarItem
									.getValue();
						} else {
							obj[selectTbarItem.getName()] = selectTbarItem
									.getRadioValue();
						}

					}
				}
			}
		}
		return obj;
	},
	createGrid : function() {
		var win = this;
		var fieldList = this.getStoreFields();
		fieldList.push("id", "orgid", "order", "createUser");
		var store = this.getStore(fieldList);

		var dockedItems = [];

		var toptbar = this.getTbars();
		for (var i = 0; i < toptbar.length; i++) {
			dockedItems.push(toptbar[i]);
		}

		var selectTbarItems = this.getSelectTbarItems();
		if (selectTbarItems.length > 0) {

			if (!win.hi_search) {
				selectTbarItems.push(this.getToolbarItem("search"));
				selectTbarItems.push(this.getToolbarItem("cancel"));
			}
			var selectTbar = this.selectTbar = Ext.create(
					"Ext.toolbar.Toolbar", {
						xtype : 'toolbar',
						dock : 'top',
						defaults : {
							labelWidth : 70,
							xtype : 'textfield',
							margin : '2 5 2 5'
						},
						items : selectTbarItems
					});
			dockedItems.push(selectTbar);
		}

		var gridConfig = {
			columns : this.getGridColumns(),
			bbar : this.getBbar(),
			store : store,
			border : 1,
			tbar : this.getToolbar(),
			dockedItems : dockedItems,
			listeners : this.getGridListeners(),
			selModel : Ext.create('Ext.selection.CheckboxModel')
		};
		Ext.apply(gridConfig, this.getGridConfig());
		if (this.gridOpen == true) {
			Ext.apply(gridConfig, {
						plugins : this.getGridRowPlugins()
					});
		}
		this.grid = Ext.create('com.hh.base.BaseGridPanel', gridConfig);
		if (this.getRightMenuItems() != null
				&& this.getRightMenuItems().length > 0) {
			var rightMenu = win.getRightMenu();
			this.grid.on('itemcontextmenu', function(a, b, c, d, e) {
						rightMenu.showAt(e.getXY());
						rightMenu.doConstrain();
						e.stopEvent();
					}, win);
		}
		if (this.getContainerRightMenuItems() != null
				&& this.getContainerRightMenuItems().length > 0) {
			var containeRrightMenu = win.getContainerRightMenu();
			if (containeRrightMenu != null) {
				this.grid.on('containercontextmenu', function(view, e) {
							containeRrightMenu.showAt(e.getXY());
							containeRrightMenu.doConstrain();
							e.stopEvent();
						}, win);
			}
		}

		return this.grid;
	},
	getGridConfig : function() {
		return {};
	},
	getGridRowPlugins : function() {
		var tpls = [];
		var columns = this.getGridColumns();
		for (var i = 0; i < columns.length; i++) {
			var column = columns[i];
			if (column.text && column.dataIndex) {
				var tpl = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>'
						+ column.text
						+ '：</b> {'
						+ column.dataIndex
						+ '}<br><br>'
				tpls.push(tpl);
			}
		}
		return [{
					ptype : 'rowexpander',
					rowBodyTpl : tpls
				}];
	},
	getBbar : function() {
		if (this.hi_bbar) {
			return null;
		}
		var page = this;
		return Ext.create('com.hh.base.BasePagingToolbar', {
					pageSize : static_var.pageSize,
					store : this.store,
					page : page
				});
	},
	storeConfig : {},
	gridAction : 'queryPagingData',
	getStore : function(fields, url1) {
		var proxy = ExtStore.getGridStoreProxyByObject({
					url : (url1 == null ? this.action + this.gridAction : url1),
					extraParams : this.getExtraParams()
				});
		this.store = Ext.create('com.hh.base.BaseDataStore', Ext.apply({
							pageSize : static_var.pageSize,
							fields : fields,
							proxy : proxy
						}, this.storeConfig));
		return this.store;
	},
	extraParams : {},
	getExtraParams : function() {
		return this.extraParams;
	},
	getGridRecords : function() {
		return this.grid.getSelectionModel().getSelection();
	},
	getGridRecord : function() {
		return this.grid.getSelectionModel().getLastSelected();
	},
	setExtraParams : function(extraParams) {
		this.extraParams = extraParams;
	},
	getToolbarItem : function(type) {
		var panel = this;
		if (type == 'add') {
			return {
				iconCls : 'add',
				text : panel.addText,
				surl : panel.addsurl,
				handler : function() {
					panel.doAdd();
				}
			};
		} else if (type == 'delete') {
			return {
				iconCls : 'delete',
				text : panel.deleteText,
				surl : panel.deletesurl,
				handler : function() {
					panel.doDelete();
				}
			};
		} else if (type == 'update') {
			return {
				iconCls : 'update',
				text : panel.updateText,
				surl : panel.updatesurl,
				handler : function() {
					panel.doUpdate();
				}
			};
		} else if (type == 'search') {
			return {
				iconCls : 'search',
				xtype : 'button',
				text : panel.searchText,
				handler : function() {
					panel.doSearch();
				}
			};
		} else if (type == 'cancel') {
			return {
				iconCls : 'cancel',
				xtype : 'button',
				text : '重置查询条件',
				handler : function() {
					if (panel.searchPanel) {
						panel.searchPanel.getForm().reset();
					}
					panel.selectTbarToNull();
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
	tbaritems : [],
	getTbarItems : function() {
		var toolbarItems = [];
		var page = this;
		if (!page.hi_add) {
			toolbarItems.push(this.getToolbarItem("add"));
		}
		if (!page.hi_update) {
			toolbarItems.push(this.getToolbarItem("update"));
		}
		if (!page.hi_delete) {
			toolbarItems.push(this.getToolbarItem("delete"));
		}
		if (!page.hi_up) {
			toolbarItems.push(this.getToolbarItem("up"));
		}
		if (!page.hi_down) {
			toolbarItems.push(this.getToolbarItem("down"));
		}
		this.pushToolbarItems(toolbarItems);
		if (!page.hi_search && page.selectTbar == null) {
			toolbarItems.push('->');
			toolbarItems.push(this.getToolbarItem("search"));
			if (this.getSearchPanelItems().length > 0 || this.selectTbar) {
				toolbarItems.push(this.getToolbarItem("cancel"));
			}
		}
		return toolbarItems;
	},
	getExAfTbarItems : function() {
		return [];
	},
	pushToolbarItems : function(toolbarItems) {
		var exAfTbarItems = this.getExAfTbarItems();
		for (var i = 0; i < exAfTbarItems.length; i++) {
			toolbarItems.push(exAfTbarItems[i]);
		}
	},
	getToolbar : function() {
		var panel = this;
		return Ext.create('com.hh.base.BaseToolbar', {
					items : panel.getTbarItems()
				});
	},
	doAdd : function() {
		var editPageUrl = this.getEditPage();
		var page = this;
		ExtUtil.open(editPageUrl, {
					parentPanel : this,
					callbackRefresh : function() {
						page.store.load();
					}
				});
	},
	doOperateDelete : function(record) {
		this.doBaseDelete(this.grid, [record], this.action + 'deleteByIds');
	},
	doBaseDelete : function(grid, records, url, msg) {
		if (Util.isNull(records)) {
			ExtFrame.msg("请选中要删除的数据！");
		} else {
			var strids = Util.recordsToStrByKey(records, this.pkey);
			var orgids = Util.recordsToStrByKey(records, "orgid");
			var createUsers = Util.recordsToStrByKey(records, "createUser");

			if (Util.isNull(strids)) {
				return;
			}
			var msgStr = '您确认要删除信息吗？,请慎重...';
			if (msg) {
				msgStr = msg;
			}
			var result = Ext.Msg.confirm('请确认',
					'<span style="color:red"><b>提示:</b>' + msgStr + '</span>',
					function(btn) {
						if (btn == 'yes') {
							Request.synRequestObject(url, {
										ids : strids,
										orgids : orgids,
										createUsers : createUsers
									});
							grid.getStore().load();
						}
					});
		}
	},
	getSelectRows : function() {
		return this.grid.getSelectionModel().getSelection();
	},
	getSelectRow : function() {
		return this.grid.getSelectionModel().getLastSelected();
	},
	doDelete : function(method, msg) {
		var panel = this;
		var methodAction = 'deleteByIds';
		if (method) {
			methodAction = method;
		}
		var url = panel.action + methodAction;
		var grid = this.grid;
		var records = grid.getSelectionModel().getSelection();
		this.doBaseDelete(grid, records, url, msg);
	},
	doUpdate : function() {
		var record = this.grid.getSelectionModel().getLastSelected();
		var page = this;
		var editPageUrl = this.getEditPage();
		if (Util.isNull(record)) {
			ExtFrame.msg('请选中要编辑的数据！');
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
	refresh : function() {
		this.store.load();
	},
	doSearch : function() {
		var params = {};
		if (this.searchPanel != null) {
			var items = this.searchPanel.getForm().items;
			for (var i = 0; i < items.length; i++) {
				params[items[i].name] = this.searchPanel.getForm()
						.findField(items[i].name).getValue();
			}
		}
		Ext.apply(params, this.getSelectTbarValues());
		Ext.apply(this.grid.getStore().proxy.extraParams, params);
		this.grid.getStore().load();
	},

	getOperateGridColumn : function() {
		var page = this;
		var editPageUrl = this.getEditPage();
		return {
			text : '操作',
			menuDisabled : true,
			sortable : false,
			xtype : 'actioncolumn',
			width : 50,
			items : [{
						iconCls : 'delete',
						tooltip : '删除',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							page.doOperateDelete(rec);
						}
					}, {
						iconCls : 'update',
						tooltip : '修改',
						handler : function(grid, rowIndex, colIndex) {
							var rec = grid.getStore().getAt(rowIndex);
							ExtUtil.open(editPageUrl, {
										parentPanel : page,
										parentRecord : rec,
										callbackRefresh : function() {
											page.store.load();
										}
									});
						}
					}]
		};
	},
	putParam : function(name, value) {
		this[name] = value;
	},
	orderUp : function() {
		var page = this;
		var store = page.grid.getStore();
		var record = page.grid.getSelectionModel().getLastSelected();
		if (Util.isNull(record)) {
			ExtFrame.msg("请选中要上移的数据！");
		} else {
			var index = store.indexOf(record);
			if (index > 0) {
				var recordup = store.getAt(index - 1);
				Request.request(page.action + 'order', {
							id1 : record.get("id"),
							id2 : recordup.get("id"),
							order1 : record.get("order"),
							order2 : recordup.get("order")
						}, function(result) {
							if (result.isSuccess == true) {
								store.removeAt(index);
								var uporder = record.get("order");
								record.set("order", recordup.get("order"));
								recordup.set("order", uporder);
								store.insert(index - 1, record);
								page.grid.getSelectionModel().select(index - 1);
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
		var store = page.grid.getStore();
		var record = page.grid.getSelectionModel().getLastSelected();
		if (Util.isNull(record)) {
			ExtFrame.msg("请选中要下移的数据！");
		} else {
			var index = store.indexOf(record);
			if (index < store.getCount() - 1) {
				var recordup = store.getAt(index + 1);
				Request.request(page.action + 'order', {
							id1 : record.get("id"),
							id2 : recordup.get("id"),
							order1 : record.get("order"),
							order2 : recordup.get("order")
						}, function(result) {
							if (result.isSuccess == true) {
								store.removeAt(index);
								var uporder = record.get("order");
								record.set("order", recordup.get("order"));
								recordup.set("order", uporder);
								store.insert(index + 1, record);
								page.grid.getSelectionModel().select(index + 1);
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