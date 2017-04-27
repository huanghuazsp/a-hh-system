Ext
		.define(
				'com.hh.global.NavigAtionWindow',
				{
					extend : 'com.hh.base.BaseServicePanel',
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, config);
						this.add(this.createTree());
						this.add(this.createTab());
					},
					mainTab : null,
					createTree : function() {
						var win = this;

						var treeStoreData = null;

						this.isActionForm = win.id== Menu.actionFormId;

						if (this.isActionForm) {
							treeStoreData = Menu.childrenList;
						} else {
							treeStoreData = Menu.getTreeChildrens(win.id.replace("panel",""));
						}
						var store = Ext.create('Ext.data.TreeStore', {
							root : {
								children : treeStoreData
							}
						});
						var className = this.$className + "mainTab";
						var tree = Ext
								.create(
										'com.hh.base.BaseTreePanel',
										{
											padding:'1',
											region : 'west',
											title : '导航菜单',
											store : store,
											listeners : {
												itemclick : function(view,
														record) {
													if (record.get("leaf") == false
															&& !win.isActionForm) {
														return;
													}
													var tabs = win.mainTab;
													var tabPanelId = win.id
															+ '-'
															+ record.get("id");
													var tab = tabs
															.getComponent(tabPanelId);
													if (!tab) {
														try {
															Doing.hidden(false);
															var action = record.raw.action;
															if (record.raw.action
																	.substr(0,
																			3) != 'com') {
																action = 'com.hh.global.BaseIframeWindow';
															}
															$import(action);
															var paramObj = {};
															paramObj.id = tabPanelId;
															paramObj.vsj = record.raw.action;
															paramObj.title = record
																	.get("text");
															paramObj.icon = record
																	.get("icon");
															var resultwin = Ext
																	.create(
																			action,
																			paramObj);
															resultwin.closable = true;
															tabs
																	.add(resultwin);
															tabs
																	.setActiveTab(resultwin);
														} catch (theException) {
															ExtFrame
																	.error('功能注册失败：'
																			+ theException.message);
															Doing.hidden(true);
														}
													} else {
														tabs.setActiveTab(tab);
													}
												}
											}
										});
						return tree;
					},
					createTab : function() {
						var tabs = Ext.create('com.hh.base.BaseTabPanel', {
								padding:'1',
							region : 'center',
							items : [],
							plugins : Ext.create('Ext.ux.TabCloseMenu', {})
						});
						this.mainTab = tabs;
						return tabs;
					}
				})