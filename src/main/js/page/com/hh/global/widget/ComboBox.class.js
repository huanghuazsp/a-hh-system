Ext
		.define(
				'com.hh.global.widget.ComboBox',
				{
					extend : 'Ext.form.field.ComboBox',
					alias : 'widget.widgetComboBox',
					selectDataMapList : {},
					constructor : function(config) {
						this.config = config || {};
						var me = this;
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
						if (this.config.valueField != null
								&& this.config.valueField != '') {
							this.valueField = this.config.valueField;
						}

						if (this.config.displayField != null
								&& this.config.displayField != '') {
							this.displayField = this.config.displayField;
						}

						if (this.config.data == null || '' == this.config.data) {

							var proxyConfig = {
								type : 'ajax',
								url : this.config.action == null ? 'system-GlobalComboBox-queryItem'
										: this.config.action,
								extraParams : extraParams
							};
							var storeConfig = {
								root : {},
								fields : [ this.valueField, this.displayField ],
								proxy : proxyConfig
							};
							this.store = Ext.create(
									'com.hh.base.BaseDataStore', storeConfig);
						} else {
							var data = this.config.data;
							if (typeof data == "string") {
								data = Ext.decode(data);
							}
							this.store = Ext.create(
									'com.hh.base.BaseDataStore', {
										fields : [ this.valueField,
												this.displayField ],
										data : data
									});
						}
						this.callParent(arguments);
					},
					value : '',
					fieldLabel : '未指定',
					displayField : 'text',
					queryMode : "local",
					valueField : 'id',
					editable : false,
					allowBlank : false,
					labelWidth : 70,
					columnWidth : 0.5,
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
					getValue : function() {
						var me = this, picker = me.picker, rawValue = me
								.getRawValue(), value = me.value;

						if (me.getDisplayValue() !== rawValue) {
							value = rawValue;
							me.value = me.displayTplData = me.valueModels = null;
							if (picker) {
								me.ignoreSelection++;
								picker.getSelectionModel().deselectAll();
								me.ignoreSelection--;
							}
						}

						if (me.multiSelect == true) {
							var rangeArray = value;
							var submitValueArray = '';
							for ( var i = 0; i < rangeArray.length; i++) {
								submitValueArray += rangeArray[i] + ',';
							}
							if (submitValueArray != ''
									&& submitValueArray.length > 0) {
								return submitValueArray.substr(0,
										submitValueArray.length - 1);
							} else {
								return '';
							}
						}
						return value;
					},
					setValue : function(value, doSelect) {
						var me = this;
						if (Ext.isEmpty(value)) {
							value = null;
						}
						if (Ext.isString(value) && me.multiSelect) {
							value = value.split(',');
						}
						this.callParent(arguments);
					}
				});