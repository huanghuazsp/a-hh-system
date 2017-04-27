Ext.define('com.hh.global.SimpleTreePanel', {
	extend : 'com.hh.global.BaseSimpleTreePanel',
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
	}
});