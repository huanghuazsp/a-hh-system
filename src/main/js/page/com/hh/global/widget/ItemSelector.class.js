Ext
		.define(
				'com.hh.global.widget.ItemSelector',
				{
					extend : 'Ext.ux.form.ItemSelector',
					alias : 'widget.widgetItemSelector',
					constructor : function(config) {
						this.config = config || {};
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
							this.store = Ext
									.create(
											'com.hh.base.BaseDataStore',
											{
												fields : [ this.valueField,
														this.displayField ],
												root : {},
												proxy : {
													type : 'ajax',
													url : this.config.action == null ? 'system-GlobalComboBox-queryItem'
															: this.config.action,
													extraParams : extraParams
												}
											});
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

						this.superclass.constructor.call(this, this.config);

					},
					getSubmitValue : function() {
						if (this.submitType == 'list') {
							var rangeArray = this.toField.store.getRange();
							var submitValueArray = [];
							for ( var i = 0; i < rangeArray.length; i++) {
								submitValueArray
										.push(rangeArray[i].data[this.valueField]);
							}
							return submitValueArray;
						}

						var rangeArray = this.toField.store.getRange();
						var submitValueArray = '';
						for ( var i = 0; i < rangeArray.length; i++) {
							submitValueArray += rangeArray[i].data[this.valueField]
									+ ',';
						}
						if (submitValueArray != ''
								&& submitValueArray.length > 0) {
							return submitValueArray.substr(0,
									submitValueArray.length - 1);
						} else {
							return '';
						}
					},
					height : 170,
					displayField : 'text',
					queryMode : "local",
					valueField : 'id',
					submitType : null,
					columnWidth : 1,
					formDesigner : false
				});