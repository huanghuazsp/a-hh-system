Ext.define('com.hh.global.BaseIframeWindow', {
	extend : 'com.hh.base.BaseServicePanel',
	bodyStyle : "background-color:#ffffff;",
	buttons : [ {
		iconCls : 'table_refresh',
		text : '刷新页面',
		handler : function() {
			var page = this.up("panel");
			window.frames[page.iframeid].location.reload();
		}
	} ],
	constructor : function(config) {
		this.config = config || {};
		this.superclass.constructor.call(this, this.config);
		this.init();
	},
	init : function() {
		var page = this;
		page.iframeid = UUID.getUUID();
		var iframeHtml = '<iframe id="' + page.iframeid + '"  name="'
				+ page.iframeid
				+ '"  width=100% height=100%  frameborder=0 src="' + page.vsj
				+ '" ></iframe>';
		if (this.panel) {
			this.panel.update(iframeHtml);
		} else {
			this.update(iframeHtml);
		}
		page.on('destroy',function(){
			var iframe = document.getElementById(page.iframeid);
			iframe.src = "about:blank";
			iframe.parentElement.removeChild(iframe);	
		});
	}
});