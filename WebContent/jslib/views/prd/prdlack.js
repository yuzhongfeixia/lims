Ext.define('alms.prdlack', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '耗材库存查询',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdcodeforlack',
      storeUrl: 'PrdSearchPrdCodeForLack.do',
      expUrl: 'PrdSearchPrdCodeForLack.do',
      hasPage: true,
      idPrevNext: 'prdcode',
      hasDateSearch: true
    });

    me.callParent(arguments);
  },
  
  idBeginDate: 'searchbegindate',
  idEndDate: 'searchenddate',

  OnInitGridToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.callParent();

    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield', fieldLabel:gpersist.STR_BTN_BEGINDATE, labelWidth:60, width:160, name:'searchbegindate', id:mep + 'searchbegindate',
        format:'Y-m-d', value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1), selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield', fieldLabel:gpersist.STR_BTN_ENDDATE, labelWidth:60, width:160, name:'searchenddate', id:mep + 'searchenddate',
        format:'Y-m-d', value:nowdate, selectOnFocus:false, allowBlank:true},
//      ' ', alms.BarComboLabProject('searchlabid', mep + 'searchlabid', 300, lims.LAB_PROJECT, 60, gpersist.SELECT_ALL_VALUE),
      ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 60, width: 200, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true }
    ];
    
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
    
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'pc.search.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '',
      'pc.search.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : '',
      'pc.prdname': tools.GetValueEncode(mep + 'searchprdname')
    };
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    if (me.hasDateSearch)
      Ext.Array.insert(items, 2, [
        ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
        ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }
      ]);
        
    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  // 上一天查询
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;    
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, -1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, -1));

    me.OnSearch();
  },

  // 后一天查询
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, 1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, 1));

    me.OnSearch();
  },
  
  OnShow:function(){}
});