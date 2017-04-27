Ext.define("com.hh.global.widget.FileWindow", {
	extend : 'com.hh.global.SimpleFormPanelWindow',
	height : 150,
	width : 420,
	modal : true,
	maximizable : false,
	minimizable : false,
	title : '上传附件',
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
	},
	url : 'system-File-save',
	createFormPanel : function() {
		var page = this;
		var form = this.form = Ext.create('Ext.form.Panel', {
			bodyPadding : '10 10 0',
			region : 'center',
			url : page.url,
			defaults : {
				anchor : '100%',
				allowBlank : false,
				labelWidth : 50
			},
			items : [{
						xtype : 'textfield',
						name : 'name',
						allowBlank : false,
						fieldLabel : '名称'
					}, {
						xtype : 'filefield',
						emptyText : '请选择文件',
						fieldLabel : '文件',
						name : 'attachment',
						allowBlank : false,
						buttonText : '选择',
						listeners : {
							change : function(field) {
								field.previousSibling().setValue(field
												.getValue());
							}
						}
					}, {
						xtype : 'textfield',
						name : 'filePath',
						hidden : true,
						value : page.config.filePath
					}],

			buttons : [{
				text : '保    存',
				iconCls : 'yes',
				handler : function() {
					var form = this.up('form').getForm();
					if (form.isValid()) {
						Doing.hidden(false);
						form.submit({
							success : function(form, action, options) {
								Doing.hidden(true);
								var result = action.result;
								if (result != null) {
									page.config.parentPanel.grid.getStore()
											.add(	result);
								}
								ExtFrame.msg("<font color=green>上传成功！！</font>");
								page.closePage();
							},
							failure : function(form, action, options) {
								Doing.hidden(true);
								ExtFrame.msg("<font color=red>上传失败！！</font>");
							}
						});
					}
				}
			}, {
				iconCls : 'cancel',
				text : '取    消',
				handler : function() {
					page.closePage();
				}
			}]
		});

		return form;
	},
	getBtns : function() {
		return null;
	},
	submitForm : function() {
		var bereturn = this.bfSubmitForm();
		if (bereturn != false) {
			FormPanel.submit(this.form);
			this.close();
		}
	}
});