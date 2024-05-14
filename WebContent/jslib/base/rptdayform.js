Ext.define('gpersist.base.rptdayform', {
	extend: 'gpersist.base.listform',

	dateLable: '报表日期',

	OnInitConfig: function () {
		var me = this;

		me.callParent(arguments);

		me.dateLable = '报表日期';
	},

	OnBeforeFormLoad: function () {
		var me = this;
		var mep = me.tranPrefix;

		me.OnInitGridToolBar();

		var nowdate = new Date();

		var items = [
     ' ', { xtype: 'datefield', fieldLabel: me.dateLable, labelWidth: 60, width: 160, name: 'RptDate', id: mep + 'RptDate',
     	format: 'Y-m-d', value: nowdate, selectOnFocus: false, allowBlank: true
     },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
      ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }, '-',
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me }, '-',
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];

		if (me.hasPageSize)
			Ext.Array.insert(items, 18, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
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
				rptday: tools.GetValue(mep + 'RptDate') ? Ext.Date.format(tools.GetValue(mep + 'RptDate'), 'Y-m-d') : ''
			});
		});
	},

	OnPrevSearch: function () {
		var me = this;
		var mep = me.tranPrefix;
		tools.SetValue(mep + 'RptDate', Ext.Date.add(tools.GetValue(mep + 'RptDate'), Ext.Date.DAY, -1));

		me.OnSearch();
	},

	OnNextSearch: function () {
		var me = this;
		var mep = me.tranPrefix;
		tools.SetValue(mep + 'RptDate', Ext.Date.add(tools.GetValue(mep + 'RptDate'), Ext.Date.DAY, 1));

		me.OnSearch();
	}
});