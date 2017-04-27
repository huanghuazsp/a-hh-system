Ext.define('com.hh.global.widget.CkEditField', {
	extend : 'Ext.form.field.TextArea',
	alias : 'widget.widgetCkeditor',
	height : 400,
	initComponent : function() {
		this.callParent(arguments);
		this.on('afterrender', function(p) {
			Ext.apply(this.CKConfig, {
				height : this.getHeight()
			});
			var instance = CKEDITOR.instances[this.inputEl.id];  
			if (instance) { CKEDITOR.remove(instance); } 
			this.editor = CKEDITOR.replace(this.inputEl.id, this.CKConfig);
			this.editor.setData(this.value);
			this.editorId = this.editor.id;
		}, this);
	},
	onRender : function(ct, position) {
		if (!this.el) {
			this.defaultAutoCreate = {
				tag : 'textarea',
				autocomplete : 'off'
			};
		}
		this.callParent(arguments)
	},
	setValue : function(value) {
		this.callParent(arguments);
		this.value = value;
		if (this.editor) {
			this.editor.setData(value);
		}
	},
	getRawValue : function() {
		if (this.editor) {
			return this.editor.getData()
		} else {
			return ''
		}
	}
});

CKEDITOR.on('instanceReady', function(e) {
	var o = Ext.ComponentQuery.query('widgetCkeditor[editorId="' + e.editor.id
			+ '"]'), comp = o[0];
	if (comp != null) {
		e.editor.resize(comp.getWidth() - comp.labelWidth, comp.getHeight())
		comp.on('resize', function(c, adjWidth, adjHeight) {
			c.editor.resize(adjWidth - c.labelWidth, adjHeight)
		});
	}
});