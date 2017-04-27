Ext
		.define(
				'com.hh.global.widget.DateTimeField',
				{
					extend : 'Ext.form.FieldContainer',
					alias : 'widget.widgetDateField',
					mixins : {
						bindable : 'Ext.util.Bindable',
						field : 'Ext.form.field.Field'
					},
					layout : "column",
					no_delete_key : [],
					constructor : function(config) {
						this.config = config || {};
						this.superclass.constructor.call(this, this.config);
						var me = this;
						if (this.config.formDesigner == true) {
							me.border = 1;
							me.style = {
								borderColor : '#000000',
								borderStyle : 'solid',
								borderWidth : '1px'
							};
							me.html = '<font color=red>我是时间控件</font>';
						} else {
							me.date = Ext.create('Ext.form.field.Date', {
								format : 'Y年m月d日',
								columnWidth : 1
							});
							me.hour = Ext.create('Ext.form.field.Number', {
								minValue : 0,
								maxValue : 23,
								// columnWidth : 0.1,
								width : 40,
								enableKeyEvents : true,
								scope : this
							});

							me.minute = Ext.create('Ext.form.field.Number', {
								minValue : 0,
								maxValue : 59,
								// columnWidth : 0.1,
								width : 40,
								enableKeyEvents : true,
								scope : this
							});
							me.second = Ext.create('Ext.form.field.Number', {
								minValue : 0,
								width : 40,
								maxValue : 59,
								// columnWidth : 0.1,
								enableKeyEvents : true,
								scope : this
							});
							this.add(me.date);
							this.add(me.hour);
							this.add(me.minute);
							this.add(me.second);
						}
					},
					getValue : function() {
						var me = this;
						if(me.date==null){
							return;
						}
						var date = me.date.getValue();
						if (date != null) {
							var hour = (me.hour.getValue() == null ? '00'
									: me.hour.getValue())
									+ "";
							if (hour.length == 1) {
								hour = "0" + hour;
							}

							var minute = (me.minute.getValue() == null ? '00'
									: me.minute.getValue())
									+ "";
							if (minute.length == 1) {
								minute = "0" + minute;
							}

							var second = (me.second.getValue() == null ? '00'
									: me.second.getValue())
									+ "";
							if (second.length == 1) {
								second = "0" + second;
							}

							var year = date.getFullYear() + "";

							if (year.length != 4) {
								var length = 4 - year.length;
								var str = "";
								for ( var i = 0; i < length; i++) {
									str += "0";
								}
								year = str + year
							}

							var month = (date.getMonth() + 1) + "";
							if (month.length == 1) {
								month = "0" + month;
							}

							var day = date.getDate() + "";
							if (day.length == 1) {
								day = "0" + day;
							}
							var dateTimeString = year + "年" + month + "月" + day
									+ "日 " + hour + "时" + minute + "分" + second
									+ "秒";
							return me
									.safeParse(dateTimeString, 'Y年m月d日 H时i分s秒');
						}
					},
					safeParse : function(value, format) {
						var me = this, utilDate = Ext.Date, result = null, strict = me.useStrict, parsedDate;

						if (utilDate.formatContainsHourInfo(format)) {
							result = utilDate.parse(value, format, strict);
						} else {
							parsedDate = utilDate.parse(value + ' '
									+ me.initTime, format + ' '
									+ me.initTimeFormat, strict);
							if (parsedDate) {
								result = utilDate.clearTime(parsedDate);
							}
						}
						return result;
					},
					setValue : function(value) {
						var me = this;
						var date = new Date(value);

						var year = date.getFullYear() + "";

						if (year.length != 4) {
							var length = 4 - year.length;
							var str = "";
							for ( var i = 0; i < length; i++) {
								str += "0";
							}
							year = str + year
						}

						var month = (date.getMonth() + 1) + "";
						if (month.length == 1) {
							month = "0" + month;
						}

						var day = date.getDate() + "";
						if (day.length == 1) {
							day = "0" + day;
						}

						me.date.setValue(year + "年" + month + "月" + day + "日");

						me.hour.setValue(date.getHours());
						me.minute.setValue(date.getMinutes());
						me.second.setValue(date.getSeconds());

					},
					getSubmitData : function() {
						var object = {};
						object[this.getName()] = this.getValue();
						return object;
					}
				});