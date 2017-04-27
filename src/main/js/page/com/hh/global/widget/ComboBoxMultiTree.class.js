Ext.define("com.hh.global.widget.ComboBoxMultiTree", {
	extend : "com.hh.global.widget.BaseComboBoxTree",
	alias : 'widget.widgetComboBoxMultiTree',
	mainPanelHeight : 200,
	createTreePanel : function() {
		this.createTree();
		this.createGrid();
		var panel = Ext.create('Ext.panel.Panel', {
			layout : 'border',
			height : this.mainPanelHeight,
			floating : true,
			focusOnToFront : false,
			shadow : true,
			ownerCt : this.ownerCt,
			items : [ this.tree, this.grid ],
			minWidth : this.treeMinWidth
		});
		return panel;
	},
	createTree : function() {
		var page = this;
		if (this.selectType == 'img' && this.treeMinWidth == null) {
			this.treeMinWidth = 400;
		}
		var treecfg = {
			autoScroll : true,
			padding : '1',
			useArrows : true,
			store : this.createTreeStore(),
			rootVisible : false,
			border : true,
			tbar : this.getTbar(),
			region : 'center'
		};
		this.tree = Ext.create('Ext.tree.Panel', treecfg);
	},
	createGrid : function() {
		var page = this;
		var grid = this.grid = Ext.create('Ext.grid.Panel', {
			store : page.getStore(),
			padding : '1',
			split : true,
			tbar : this.getGridTbar(),
			columns : [ {
				header : '名称',
				dataIndex : 'text',
				flex : 1
			}, {
				header : 'id',
				dataIndex : 'id',
				flex : 1,
				hidden : true
			} ],
			width : 200,
			region : 'east',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			listeners : page.getGridListeners()
		});
	},
	getGridTbar : function() {
		var page = this;
		return Ext.create('com.hh.base.BaseToolbar', {
			enableOverflow : true,
			items : [ {
				iconCls : 'delete',
				text : '删除',
				handler : function() {
					var records = page.grid.getSelectionModel().getSelection();
					page.grid.getStore().remove(records);
				}
			}, '->', {
				iconCls : 'yes',
				text : '确定',
				handler : function() {
					page.collapse();
				}
			} ]
		});
	},
	getGridListeners : function() {
		var page = this;
		return {
			itemdblclick : function(grid, record) {
				grid.getStore().remove(record);
			}
		};
	},
	filterText : function(text) {
		return text;
	},
	addTreeItemClick : function() {
		var page = this;
		if (page.tree) {
			page.tree.on({
				itemclick : function(view, record) {
					if (page.select == 'leaf' && !record.raw.leaf) {
						return;
					}
					if (page.beitemclick(view, record)) {
						var store = page.grid.getStore();
						if (store.find("id", record.get("id")) < 0) {
							var data = record.raw;
							Ext.apply(data, {
								id : record.get("id"),
								text : page.filterText(record.get("text"))
							});
							page.grid.getStore().add(data);
						} else {
							ExtFrame.msg("您已经选中该项了！！");
						}
					}
				}
			});
		}
	},
	getStore : function() {
		var page = this;
		var storeCfg = {};
		if (page.gridAction) {
			var proxy = ExtStore.getGridStoreProxyByObject({
				url : page.gridAction
			});
			storeCfg = {
				fields : [ "id", "text" ],
				proxy : proxy
			};
		} else {
			var data = [];
			if (page.objectStr) {
				data = Ext.decode(page.objectStr);
			}
			storeCfg = {
				fields : [ "id", "text" ],
				data : data
			};
		}
		Ext.apply(storeCfg, {
			listeners : {
				datachanged : function(store) {
					var texts = "";
					var ids = "";
					store.each(function(record) {
						texts += record.get("text") + ",";
						ids += record.get("id") + ",";
					});
					if (ids) {
						ids = ids.substr(0, ids.length - 1);
						texts = texts.substr(0, texts.length - 1);
						page.setRawValue(texts);
						page.setDisplayValue(ids);
					} else {
						page.setRawValue("");
						page.setDisplayValue("");
					}
				}
			}
		});
		return Ext.create('com.hh.base.BaseDataStore', storeCfg);
	}
});
