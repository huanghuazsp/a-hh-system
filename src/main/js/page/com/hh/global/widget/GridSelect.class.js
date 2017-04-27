Ext.define('com.hh.global.widget.GridSelect', {
			extend : 'Ext.form.field.Trigger',
			alias : 'widget.widgetGridSelect',
			editable : false,
			allowBlank : false,
			trigger1Cls : Ext.baseCSSPrefix + 'form-clear-trigger',
			trigger2Cls : Ext.baseCSSPrefix + 'form-search-trigger',
			constructor : function(config) {
				this.config = config || {};
				this.callParent(arguments);

			},
			displayField : 'text',
			valueField : 'id',
			initEvents : function() {
				var me = this;
				me.callParent();
				if (!me.editable) {
					me.mon(me.inputEl, 'click', me.onTriggerClick, me);
				}
			},
			onTrigger1Click : function() {
				this.deleteData();
			},
			gridPanelPath : null,
			onTriggerClick : function() {
				if (this.gridPanelPath == null) {
					ExtFrame.msg("请指定打开的页面！");
				} else {
					var window = ExtUtil.open(this.gridPanelPath, {
								title : '请选择<font color=red>（双击选择）</font>',
								modal : true
							});
					var panel = window.panel;
					panel.grid.on("itemdblclick", function(view, record) {
								this.setRawValue(record.get(this.displayField));
								this.value = record.get(this.valueField);
								this.afChange(record);
								window.close();
							}, this);
				}
			},
			afChange : function(record) {
			},
			columnWidth : 0.5,
			deleteData : function() {
				this.setRawValue('');
				this.value = "";
			},
			getValue : function() {
				return this.value;
			},
			tableName : null,
			isGetText : false,
			setValue : function(value) {
				this.value = value;
				var page = this;
				if ((this.getRawValue() == '' || this.getRawValue() == null)
						&& page.tableName != null && value != null
						&& value != '' && page.isGetText == false) {
					page.isGetText = true;
					Request.request('system-GlobalComboBoxTree-findTextById', {
								id : value,
								table_name : page.tableName
							}, function(returnObj) {
								if (returnObj) {
									page
											.setRawValue(returnObj[page.displayField]);
								}
							});
				}
			},
			getSubmitData : function() {
				var object = {};
				object[this.getName()] = this.getValue();
				return object;
			}
		});