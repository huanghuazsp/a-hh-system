Ext
		.define(
				'com.hh.global.widget.RadioGroup',
				{
					extend : 'Ext.form.RadioGroup',
					alias : 'widget.widgetRadioGroup',
					constructor : function(config) {
						this.config = config || {};
						var me = this;
						if (this.config.columnWidth) {
							if (!this.config.columns) {
								this.columns = this.config.columnWidth * 4;
							}
						}

						if (this.config.formDesigner) {
							this.config.border = 1;
							this.config.style = {
								borderColor : '#000000',
								borderStyle : 'solid',
								borderWidth : '1px'
							};
							me.html = '<font color=red>我是单选控件</font>';
						}

						var paramType = this.config.paramType;

						var extraParams = this.config.extraParams;

						if (typeof extraParams == "string") {
							extraParams = Ext.decode(extraParams);
						}
						if (paramType == null) {
						} else {
							var paramsMap = {};
							for ( var p in extraParams) {
								paramsMap[paramType + '.' + p] = extraParams[p];
							}
							extraParams = paramsMap;
						}

						var me = this;
						var action = this.config.action == null ? 'system-GlobalRadio-queryItem'
								: this.config.action
						if (this.config.data == null || '' == this.config.data) {
						} else {
							var data = this.config.data;
							if (typeof data == "string") {
								data = Ext.decode(data);
							}
							var returnObject = data;
							this.items = me.queryItems(returnObject);
						}
						this.superclass.constructor.call(this, this.config);

						if (this.config.data == null || '' == this.config.data) {
							if (!me.formDesigner) {
								Request.synRequestObject(action, extraParams,
										Ext.bind(this.addMyItems, this));
							}
						}
						this.on('render', function(p) {
							if (me.formDesigner) {
								me.removeAll();
							}
						});
					},
					addMyItems : function(resultData) {
						var me = this;
						var items = me.queryItems(resultData);
						me.add(items);
					},
					queryItems : function(returnObject) {
						var me = this;
						var displayField = 'text';
						var valueField = 'id';
						if (this.config.displayField != null
								&& this.config.displayField != '') {
							displayField = this.config.displayField;
						}
						if (this.config.valueField != null
								&& this.config.valueField != '') {
							valueField = this.config.valueField;
						}
						var items = [];
						if (returnObject) {
							for ( var i = 0; i < returnObject.length; i++) {
								var item = {
									boxLabel : returnObject[i][displayField],
									name : me.config.name,
									inputValue : returnObject[i][valueField],
									checked : this.config.value == returnObject[i][valueField]
								};
								items.push(item);
							}
						}
						return items;
					},
					paramType : null,
					extraParams : {},
					setValue : function(valueObjectStr) {
						for ( var i = 0; i < this.items.items.length; i++) {
							if (valueObjectStr == this.items.items[i].inputValue) {
								this.items.items[i].setValue(true);
								break;
							}
						}
					},
					getRadioValue : function() {
						if(this.getValue()){
							return this.getValue()[this.name];
						}
					},
					columnWidth : 1,
					columns : 4,
					formDesigner : false,
					displayField : 'text',
					valueField : 'id'
				});