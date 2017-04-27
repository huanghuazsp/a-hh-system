Ext.define("com.hh.global.widget.GridField", {
	extend : 'Ext.form.FieldContainer',
	alias : 'widget.widgetGridField',
	mixins : {
		bindable : 'Ext.util.Bindable',
		field : 'Ext.form.field.Field'
	},
	layout : 'fit',
	no_delete_key : [],
	getFields : function() {
		return [ 'key', 'value' ];
	},
	getColumns : function() {
		var page = this;
		return [ {
			header : '键',
			dataIndex : 'key',
			flex : 1,
			editor : {
				xtype : 'textfield',
				selectOnFocus : true
			}
		}, {
			header : '值',
			dataIndex : 'value',
			flex : 1,
			editor : {
				xtype : 'textfield',
				selectOnFocus : true
			}
		}, page.getOperColumn() ];
	},
	getOperColumn : function() {
		var page = this;
		return {
			text : '操作',
			menuDisabled : true,
			sortable : false,
			xtype : 'actioncolumn',
			width : 30,
			items : [ {
				iconCls : "delete",
				tooltip : '删除',
				handler : function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					if (page.no_delete_key.inArray(rec.get('key'))) {
						ExtFrame.info("此信息项是必须的，不能删除！！");
					} else {
						page.varGridStore.remove(rec);
					}
				}
			} ]
		};
	},
	getTbarItemByType : function(type) {
		var page = this;
		if ('add' == type) {
			return {
				iconCls : "add",
				text : '添加',
				handler : function() {
					page.varGridStore.add({});
				}
			};
		}
	},
	getTbar : function() {
		var page = this;
		return [ page.getTbarItemByType('add') ];
	},
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		var me = this;
		var varGridStore = Ext.create('Ext.data.Store', {
			fields : me.getFields(),
			data : {
				'items' : this.config.value
			},
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			}
		});
		this.varGridStore = varGridStore;
		var varGrid = Ext.create('Ext.grid.Panel', {
			plugins : Ext.create('Ext.grid.plugin.CellEditing'),
			store : varGridStore,
			border : true,
			title : this.title,
			columns : this.getColumns(),
			tbar : this.getTbar()
		});
		this.add(varGrid);
	},
	getValue : function() {
		var objectList = [];
		this.varGridStore.each(function(record) {
			objectList.push(record.data);
		});
		return Json.objTostr2(objectList);
	},
	setValue : function(value) {
		this.varGridStore.removeAll();
		if (value) {
			var object = value;
			if (typeof value == "string") {
				object = Ext.decode(value);
			}
			this.varGridStore.add(object);
		}
	},
	getSubmitData : function() {
		var object = {};
		object[this.getName()] = this.getValue();
		return object;
	}
});