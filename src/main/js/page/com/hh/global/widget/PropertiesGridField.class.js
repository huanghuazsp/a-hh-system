Ext.define("com.hh.global.widget.PropertiesGridField", {
	extend : 'Ext.form.FieldContainer',
	alias : 'widget.widgetPropertiesField',
	mixins : {
		bindable : 'Ext.util.Bindable',
		field : 'Ext.form.field.Field'
	},
	layout : 'fit',
	no_delete_key : [],
	keyText : '键',
	valueText : '值',
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
		var me = this;
		var varGridStore = Ext.create('Ext.data.Store', {
					fields : ['key', 'value'],
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
			columns : [{
						header : this.keyText,
						dataIndex : 'key',
						flex : 1,
						editor : {
							xtype : 'textfield',
							selectOnFocus : true
						}
					}, {
						header : this.valueText,
						dataIndex : 'value',
						flex : 1,
						editor : {
							xtype : 'textfield',
							selectOnFocus : true
						}
					}, {
						text : '操作',
						menuDisabled : true,
						sortable : false,
						xtype : 'actioncolumn',
						width : 30,
						items : [{
									iconCls : "delete",
									tooltip : '删除',
									handler : function(grid, rowIndex, colIndex) {
										var rec = grid.getStore()
												.getAt(rowIndex);
										if (me.no_delete_key.inArray(rec
												.get('key'))) {
											ExtFrame.info("此信息项是必须的，不能删除！！");
										} else {
											varGridStore.remove(rec);
										}
									}
								}]
					}],
			tbar : [{
						iconCls : "add",
						text : '添加',
						handler : function() {
							varGridStore.add({
										"key" : "key",
										"value" : "value"
									});
						}
					} /*
						 * , { iconCls : "remove", text : '移除', handler :
						 * function() { var records =
						 * varGrid.getSelectionModel().getSelection(); if
						 * (records) { if (records[0]) {
						 * varGridStore.remove(records[0]); } } } }
						 */]
		});
		this.add(varGrid);
	},
	getValue : function() {
		var object = {};
		this.varGridStore.each(function(record) {
					object[record.get("key")] = record.get("value");
				});
		return Json.objTostr2(object);
	},
	setValue : function(value) {
		this.varGridStore.removeAll();
		if (value) {
			var object = value;
			if (typeof value == "string") {
				object = Ext.decode(value);
			}
			for (var p in object) {
				this.varGridStore.add({
							"key" : p,
							"value" : object[p]
						});
			}
		}
	},
	getSubmitData : function() {
		var object = {};
		object[this.getName()] = this.getValue();
		return object;
	}
});

Ext.define("com.hh.global.widget.PropertiesGridFieldList", {
			extend : 'com.hh.global.widget.PropertiesGridField',
			alias : 'widget.widgetPropertiesFieldList',
			constructor : function(config) {
				this.config = config || {};
				this.callParent(arguments);
			},
			getValue : function() {
				var valueList = [];
				this.varGridStore.each(function(record) {
							valueList.push({
										id : record.get("key"),
										text : record.get("value")
									});
						});
				if (valueList.length == 0) {
					return '';
				}
				return Json.objTostr2(valueList);
			},
			setValue : function(value) {
				this.varGridStore.removeAll();
				if (value) {
					var object = value;
					if (typeof value == "string") {
						object = Ext.decode(value);
						for (var i = 0; i < object.length; i++) {
							this.varGridStore.add({
										"key" : object[i].id,
										"value" : object[i].text
									});
						}
					}
				}
			}
		});