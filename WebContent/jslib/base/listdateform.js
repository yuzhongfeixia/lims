Ext.define('gpersist.base.listdateform', {
  extend :'gpersist.base.listform',
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield',fieldLabel:gpersist.STR_BTN_BEGINDATE,labelWidth:60,width:160,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
      ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me },       
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPageSize)
      Ext.Array.insert(items, 14, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items,border:false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    me.plGrid.store.on('beforeload', function (store, options) {      
      Ext.apply(store.proxy.extraParams, {
        'rs.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '', 
        'rs.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : ''});
    });
  },
  
  OnGetExportParams: function (params) {
    var me = this;
    var mep = me.tranPrefix;
    
    params = params || {};
    
    Ext.apply(params, {
      'rs.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '', 
      'rs.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : ''} 
    );

    return params;
  },
  
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'searchbegindate', Ext.Date.add(tools.GetValue(mep + 'searchbegindate'), Ext.Date.DAY, -1));
    tools.SetValue(mep + 'searchenddate', Ext.Date.add(tools.GetValue(mep + 'searchenddate'), Ext.Date.DAY, -1));

    me.OnSearch();
  },
  
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'searchbegindate', Ext.Date.add(tools.GetValue(mep + 'searchbegindate'), Ext.Date.DAY, 1));
    tools.SetValue(mep + 'searchenddate', Ext.Date.add(tools.GetValue(mep + 'searchenddate'), Ext.Date.DAY, 1));

    me.OnSearch();
  }
});