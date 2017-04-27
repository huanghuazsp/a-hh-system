Ext.define('com.hh.global.SimpleFormPanelWindow', {
			extend : 'com.hh.base.BaseServicePanel',
			constructor : function(config) {
				this.config = config || {};
				if (this.config.getBtns) {
					this.buttons = this.config.getBtns();
				} else {
					this.buttons = this.getBtns();
				}
				this.callParent(arguments);
				this.add(this.createFormPanel());
				if (!Util.isNull(config.parentRecord)) {
					this.loadData(config.parentRecord.get(this.entityKey),
							config.parentRecord);
				} else if (config.objectId) {
					this.loadData(config.objectId);
				}
			},
			object : {},
			entityKey : 'id',
			modal : true,
			editAction : 'findObjectById',
			loadData : function(id, parentRecord) {
				var page = this;
				Request.request(this.action + this.editAction, {
							id : id,
							orgids : parentRecord.get('orgid'),
							createUsers : parentRecord.get('createUser')
						}, function(object) {
							page.object = object;
							FormPanel.setValues(page.form, page.object);
							page.afDataLoad();
						});
			},
			afDataLoad : function() {
			},
			getToolBarByType : function(type) {
				if (type == 'cancel') {
					return {
						iconCls : 'cancel',
						text : '重置表单',
						handler : function() {
							this.up('form').getForm().reset();
						}
					};
				}
			},
			toolBarItems : [],
			getExBeTbarItems : function() {
				return [];
			},
			getToolbar : function() {
			},
			submitForm : function(callback) {
				var bereturn = this.bfSubmitForm();
				if (bereturn != false) {
					FormPanel.submit(this.form, callback);
				}
			},
			bfSubmitForm : function() {
				return true;
			},
			afSbmitForm : function(result) {
				if (result.isSuccess) {
				}
			},
			getBtnByType : function(type) {
				var page = this;
				if ("save" == type) {
					return {
						iconCls : 'yes',
						text : '保    存',
						handler : function() {
							page.submitForm({
										afcallback : function(result) {
											if (result.isSuccess) {
												page.afSbmitForm(result);
												if (page.config.callbackRefresh) {
													page.config.callbackRefresh();
												}
											}
										}
									});
						}
					};
				} else if ("saveCancel" == type) {
					return {
						iconCls : 'yes',
						text : '保存关闭',
						handler : function() {
							page.submitForm({
										afcallback : function(result) {
											if (result.isSuccess) {
												page.afSbmitForm(result);
												page.closePage();
											}
										}
									});
						}
					};
				} else if ("cancel" == type) {
					return {
						iconCls : 'cancel',
						text : '取    消',
						handler : function() {
							page.closePage("cancel");
						}
					};
				}
			},
			getBtns : function() {
				return [this.getBtnByType("save"),
						this.getBtnByType("saveCancel"),
						this.getBtnByType("cancel")];
			},
			formBorder : true,
			submitMethod : 'save',
			createFormPanel : function() {
				var page = this;
				this.form = Ext.create('com.hh.base.BaseFormPanel', {
							border : page.formBorder,
							tbar : this.getToolbar(),
							url : this.action + this.submitMethod,
							items : this.getFormItems(),
							buttons : null
						});
				return this.form;
			},
			getFormItems : function() {
				return [];
			}
		});