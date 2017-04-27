Ext
		.define(
				"com.hh.global.widget.BaseComboBoxTree",
				{
					extend : "Ext.form.field.Picker",
					constructor : function(config) {
						this.config = config || {};
						this.callParent(arguments);
					},
					editable : false,
					submitType : 'id',
					textName : 'text',
					idName : 'id',
					trigger1Cls : Ext.baseCSSPrefix + 'form-clear-trigger',
					trigger2Cls : Ext.baseCSSPrefix + 'form-arrow-trigger',
					treeMinWidth : null,
					onTrigger1Click : function() {
						this.deleteData();
					},
					extraParams : {},
					getExtraParams : function() {
						var paramType = this.paramType;

						var extraParams = this.extraParams;

						if (typeof extraParams == "string") {
							extraParams = Ext.decode(extraParams);
						}
						if (paramType == null) {
							return extraParams;
						} else {
							var paramsMap = {};
							for ( var p in extraParams) {
								paramsMap[paramType + '.' + p] = extraParams[p];
							}
							return paramsMap;
						}
					},
					paramType : null,
					createPicker : function() {
						var page = this;
						page.picker = page.createTreePanel();
						page.addTreeItemClick();
						return page.picker;
					},
					addTreeItemClick : function() {
						var page = this;
						if (page.tree) {
							page.tree
									.on({
										itemclick : function(view, record) {
											if (page.select == 'leaf'
													&& !record.raw.leaf) {
												return;
											}
											if (page.selectType == 'img') {
												page
														.setFieldLabel("<img width=16 height=16 src='"
																+ record.raw[page.idName]
																+ "' />"
																+ page.config.fieldLabel);
											}
											if (page.beitemclick(view, record)) {
												page.setRawValue(record.raw[page.textName]);
												page.setDisplayValue(record.raw[page.idName]);
												page.afitemclick(view, record);
												page.collapse();
											}
										}
									});
						}
					},
					createTreeStore : function() {
						var page = this;
						if (this.config.data == null || '' == this.config.data) {
							this.store = Ext
									.create(
											'Ext.data.TreeStore',
											{
												root : {},
												proxy : {
													type : 'ajax',
													url : page.action == null ? 'system-GlobalComboBoxTree-queryTree'
															: page.action,
													extraParams : this
															.getExtraParams()
												}
											});
						} else {
							var data = this.config.data;
							if (typeof data == "string") {
								data = Ext.decode(data);
							}
							this.store = Ext.create(
									'Ext.data.TreeStore', {
										root : {
											children : data
										}
									});
						}
						return this.store;
					},
					createTreePanel : function() {
						if (this.selectType == 'img'
								&& this.treeMinWidth == null) {
							this.treeMinWidth = 400;
						}
						var treecfg = {
							height : 200,
							autoScroll : true,
							floating : true,
							focusOnToFront : false,
							shadow : true,
							ownerCt : this.ownerCt,
							useArrows : true,
							store : this.createTreeStore(),
							rootVisible : false,
							tbar : this.getTbar(),
							minWidth : this.treeMinWidth
						};

						this.tree = Ext.create('Ext.tree.Panel', treecfg);
						return this.tree;
					},
					afDeleteData : function() {

					},
					deleteData : function() {
						var page = this;
						page.setRawValue('');
						page.setDisplayValue('');
						page.collapse();
						page.afDeleteData();
					},
					getToolBarByType : function(type) {
						var page = this;
						if (type == 'expandAll') {
							return {
								iconCls : 'expand',
								text : '全部展开',
								handler : function() {
									page.tree.collapseAll();
									page.tree.expandAll();
								}
							};
						} else if (type == 'collapseAll') {
							return {
								iconCls : 'collapse',
								text : '全部收缩',
								handler : function() {
									page.tree.collapseAll();
								}
							};
						}
					},
					getToolBarItems : function() {
						return [ this.getToolBarByType('expandAll'),
								this.getToolBarByType('collapseAll') ];
					},
					getTbar : function() {
						var panel = this;
						return Ext.create('com.hh.base.BaseToolbar', {
							enableOverflow : true,
							items : panel.getToolBarItems()
						});
					},
					displayValue : null,
					setDisplayValue : function(value) {
						this.displayValue = value;
					},
					getDisplayValue : function() {
						return this.displayValue == null ? ""
								: this.displayValue;
					},

					getSubmitData : function() {
						var object = {};
						object[this.getName()] = this.getValue();
						return object;
					},
					afitemclick : function(view, record) {
						return true;
					},
					beitemclick : function(view, record) {
						return true;
					},
					defaultSubmitValue : null,
					getValue : function() {
						var defaultSubmitValue = this.defaultSubmitValue;
						var value = null;
						if (this.submitType == 'id') {
							value = this.getDisplayValue() == null
									|| this.getDisplayValue() == '' ? defaultSubmitValue
									: this.getDisplayValue();
						} else if (this.submitType == 'text') {
							if (this.getRawValue() == null
									|| this.getRawValue() == '') {
								value = defaultSubmitValue;
							} else {
								value = this.getRawValue();
							}
						} else {
							value = this.getDisplayValue() == null
									|| this.getDisplayValue() == '' ? defaultSubmitValue
									: this.getDisplayValue();
						}
						if (value == null || value == '') {
							value = this.getRawValue();
						}
						return value;
					},
					setValue : function(valueObjectStr) {
						var page = this;
						if (valueObjectStr == '' || valueObjectStr == null) {
							return;
						}
						var valueObject = null;
						if (this.submitType == 'text') {
							page.setRawValue(valueObjectStr);
							page.setDisplayValue(valueObjectStr);
						} else {
							if (typeof valueObjectStr == "string") {
								page.setIdValue(valueObjectStr);
							} else if (valueObjectStr instanceof Array) {
								page.objectStr = Ext.encode(valueObjectStr);
								var text = "";
								var id = "";
								for ( var i = 0; i < valueObjectStr.length; i++) {
									text += valueObjectStr[i][page.textName] + ",";
									id += valueObjectStr[i][page.idName] + ",";
								}
								if (text != "") {
									text = text.substr(0, text.length - 1);
								}
								if (id != "") {
									id = id.substr(0, id.length - 1);
								}
								page.setRawValue(text);
								page.setDisplayValue(id);
							} else {
								page.setRawValue(valueObjectStr[page.textName]);
								page.setDisplayValue(valueObjectStr[page.idName]);
							}
						}
					},
					setIdValue : function(valueObjectStr) {
						var page = this;
						if (valueObjectStr == '' || valueObjectStr == null) {
							return;
						}
						page.setRawValue(valueObjectStr);
						if (page.selectType == 'img') {
							page.setFieldLabel("<img width=16 height=16 src='"
									+ valueObjectStr + "' />&nbsp;&nbsp;"
									+ page.config.fieldLabel);
							page
									.setRawValue(valueObjectStr
											.substr(valueObjectStr
													.lastIndexOf('/') + 1));
							page.setDisplayValue(valueObjectStr);

							return;
						}

						if (this.getTextAction == null) {
							var extraParams = {};
							if (typeof this.extraParams == 'string') {
								extraParams = Ext.decode(this.extraParams);
							} else {
								extraParams = this.extraParams;
							}

							var tableName = this.tableName == null ? extraParams.table_name
									: this.tableName;
							if (tableName != null && tableName != '') {
								Request
										.request(
												'system-GlobalComboBoxTree-findTextById',
												{
													id : valueObjectStr,
													table_name : tableName
												},
												function(returnObj) {
													if (returnObj) {
														page.objectStr = returnObj.object;
														page
																.setRawValue(returnObj[page.textName]);
														page
																.setDisplayValue(returnObj[page.idName]);
													}
												});
							} else {
								page.setRawValue(valueObjectStr);
								page.setDisplayValue(valueObjectStr);
							}
						} else {
							Request.request(this.getTextAction, {
								id : valueObjectStr
							}, function(returnObj) {
								page.setRawValue(returnObj[page.textName]);
								page.setDisplayValue(returnObj[page.idName]);
							});
						}
					},
					getTextAction : null,
					tableName : null
				});
Ext.define("com.hh.global.widget.ComboBoxTree", {
	extend : "com.hh.global.widget.BaseComboBoxTree",
	alias : 'widget.widgetComboBoxTree',
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
	}
});
