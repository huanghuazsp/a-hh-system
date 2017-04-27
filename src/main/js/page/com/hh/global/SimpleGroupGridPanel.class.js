Ext.define('com.hh.global.SimpleGroupGridPanel', {
	extend : 'com.hh.base.BaseServicePanel',
	action : '',
	deleteAction : '',
	hi_crightMenu : true,
	hi_rightMenu : true,
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
		var grid = this.createGrid();
		this.add(grid);
	},
	createGrupMessage : function() {
		return Ext.create('Ext.grid.feature.Grouping', {
					groupHeaderTpl : this.groupHeaderTpl,
					hideGroupedHeader : true
				});
	},
	groupField : 'id',
	createStore : function() {
		var page = this;
		var proxy = ExtStore.getGridStoreProxyByObject({
					url : page.action,
					extraParams : this.getExtraParams()
				});
		var store = this.store = Ext.create('com.hh.base.BaseDataStore', {
					groupField : page.groupField,
					pageSize : static_var.pageSize,
					fields : page.getStoreFields(),
					proxy : proxy
				});
		return store;
	},
	getGridRecords : function() {
		return this.grid.getSelectionModel().getSelection();
	},
	getGridRecord : function() {
		return this.grid.getSelectionModel().getLastSelected();
	},
	createGrid : function() {
		var page = this;
		var groupingFeature = this.createGrupMessage();
		var grid = this.grid = Ext.create('Ext.grid.Panel', {
					store : page.createStore(),
					padding : '1',
					tbar : this.getToolbar(),
					region : 'center',
					features : [groupingFeature],
					columns : page.getGridColumns(),
					selModel : Ext.create('Ext.selection.CheckboxModel')
				});
		if (!this.hi_rightMenu) {
			var rightMenu = page.getRightMenu();
			this.grid.on('itemcontextmenu', function(a, b, c, d, e) {
						rightMenu.showAt(e.getXY());
						rightMenu.doConstrain();
						e.stopEvent();
					}, page);
		}
		if (!this.hi_crightMenu) {
			var containeRrightMenu = this.getContainerRightMenu();
			this.grid.on('containercontextmenu', function(view, e) {
						containeRrightMenu.showAt(e.getXY());
						containeRrightMenu.doConstrain();
						e.stopEvent();
					}, page);
		}
		return grid;
	},
	getRightMenuItems : function() {
	},
	getRightMenu : function() {
		var panel = this;
		this.rightMenu = Ext.create('Ext.menu.Menu', {
					items : panel.getRightMenuItems()
				});

		return this.rightMenu;
	},
	getContainerRightMenuItems : function() {
	},
	getContainerRightMenu : function() {
		var panel = this;
		this.rightMenu = Ext.create('Ext.menu.Menu', {
					items : panel.getContainerRightMenuItems()
				});

		return this.rightMenu;
	},
	getTbarItems : function() {
		return [this.getToolbarItem("delete"), this.getToolbarItem("search")];
	},
	getToolbar : function() {
		var panel = this;
		return Ext.create('com.hh.base.BaseToolbar', {
					items : panel.getTbarItems()
				});
	},
	getToolbarItem : function(type) {
		var panel = this;
		if (type == 'delete') {
			return {
				iconCls : 'delete',
				text : '删除',
				handler : function() {
					panel.doDelete();
				}
			};
		} else if (type == 'search') {
			return {
				iconCls : 'refresh',
				text : '刷新',
				handler : function() {
					panel.doSearch();
				}
			};
		}
	},
	doDelete : function() {
		var panel = this;
		var grid = this.grid;
		var records = grid.getSelectionModel().getSelection();
		this.doBaseDelete(grid, records, panel.deleteAction);
	},
	doBaseDelete : function(grid, records, url) {
		if (Util.isNull(records)) {
			ExtFrame.info("请选中要删除的数据！");
		} else {
			var strids = Util.recordsToStrByKey(records, "id");
			var orgids = Util.recordsToStrByKey(records, "orgid");
			var createUsers = Util.recordsToStrByKey(records, "createUser");
			if (Util.isNull(strids)) {
				return;
			}
			var result = Ext.Msg
					.confirm(
							'请确认',
							'<span style="color:red"><b>提示:</b>您确认要删除信息吗？,请慎重...</span>',
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
	getGridColumns : function() {
		return [{
					text : 'id',
					flex : 1,
					dataIndex : 'id'
				}];
	},
	extraParams : {},
	getExtraParams : function() {
		return this.extraParams;
	},
	doSearch : function() {
		// var params = {};
		// var items = this.searchPabnel.getForm().items;
		// for ( var i = 0; i < items.length; i++) {
		// params[items[i].name] = this.searchPabnel.getForm()
		// .findField(items[i].name).getValue();
		// }
		// Ext.apply(this.grid.getStore().proxy.extraParams,
		// params);
		this.grid.getStore().load();
	}
});