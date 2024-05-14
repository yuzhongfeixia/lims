Ext.define('gpersist.base.rptmonthform', {
	extend: 'gpersist.base.listform',

	OnBeforeFormLoad: function () {
		var me = this;
		var mep = me.tranPrefix;

		me.OnInitGridToolBar();

		var nowdate = new Date();

		var items = [
      ' ', tools.GetToolBarCombo('SetYear', mep + 'SetYear', 120, '年份', 40, tools.GetYearStore(), nowdate.getFullYear()),
      ' ', tools.GetToolBarCombo('SetMonth', mep + 'SetMonth', 120, '月份', 40, tools.GetMonthStore(), gpersist.GetMonths[nowdate.getMonth()]),
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }, '-',
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me }, '-',
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];

		if (me.hasPageSize)
			Ext.Array.insert(items, 10, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
				id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.CON_PAGESIZE_MIN, minValue: gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX
			}]);

		//tools.SetToolbar(items, mep);

		var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items });

		me.tbGrid.add(toolbar);
	},

	OnBeforeSearch: function () {
		var me = this;
		var mep = me.tranPrefix;

		me.callParent();

		me.plGrid.store.on('beforeload', function (store, options) {
			Ext.apply(store.proxy.extraParams, {
				rptyear: tools.GetValue(mep + 'SetYear'),
				rptmonth: tools.GetValue(mep + 'SetMonth')
			});
		});
	}
});