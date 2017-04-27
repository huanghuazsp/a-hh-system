Ext.define('com.hh.global.widget.CkEditField', {
	extend : 'Ext.form.FieldContainer',
	alias : 'widget.widgetCkeditor',
	mixins : {
		bindable : 'Ext.util.Bindable',
		field : 'Ext.form.field.Field'
	},
	height : 400,
	constructor : function(config) {
		this.config = config || {};
		this.callParent(arguments);
		var page = this;
		if (this.config.formDesigner == true) {
			this.border = 1;
			this.style = {
				borderColor : '#000000',
				borderStyle : 'solid',
				borderWidth : '1px'
			};
			this.html = '<font color=red>我是CKEDITOR控件</font>';
		} else {
			this.iframeId = UUID.getUUID();
			this.on('afterrender', function(p) {
				var paramstr = "?height=" + (this.getHeight() - 220);
				this
						.update("<iframe id="
								+ this.iframeId
								+ "  name="
								+ this.iframeId
								+ "  width=100% height=100%  frameborder=0 src='/hhcommon/opensource/ckeditor/ckeditor.jsp"
								+ paramstr + "'></iframe>");
			}, this);
		}

	},
	getValue : function() {
		var iframe = window.frames[this.iframeId];
		if (iframe && iframe.editor) {
			return iframe.editor.getData();
		}
	},
	setMyValue : function(value) {
		var iframe = window.frames[this.iframeId];
		if (iframe && iframe.editor) {
			iframe.editor.setData(value);
		} else {
			Ext.defer(this.setMyValue, 100, this, [value]);
		}
	},
	setValue : function(value) {
		Ext.defer(this.setMyValue, 10, this, [value]);
	},
	getSubmitData : function() {
		var object = {};
		object[this.getName()] = this.getValue();
		return object;
	}
});