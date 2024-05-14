Ext.define('alms.stkoutcount', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '耗材出库统计',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stkoutcount',
      storeUrl: 'PrdSearchStkOutCount.do',
      expUrl: 'PrdSearchStkOutCount.do',
      hasPage: true,
      idPrevNext: 'tranid',
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
      ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 60, width: 200, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '所属部门', 70, tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE)),
      '-', { xtype:'datefield',fieldLabel:'出库开始日期',labelWidth:90,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '', {xtype:'datefield',fieldLabel:'出库结束日期',labelWidth:90,width:200,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, +1),selectOnFocus:false,allowBlank:true}
    ];
    
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
    
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'stkout.prdname': tools.GetValueEncode(mep + 'searchprdname'),
      'stkout.deptid': tools.GetValueEncode(mep + 'searchdeptid'),
      'stkout.begindate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
      'stkout.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
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