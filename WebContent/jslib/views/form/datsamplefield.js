Ext.define('alms.datsamplefield', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams:['BasSample', 'ClassSource', 'ClassField', 'FieldType', 'DataSource'],
      editInfo: '样品数据来源',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_datsamplefield',
      storeUrl: 'DatSearchDatSampleField.do',
      saveUrl: 'DatSaveDatSampleField.do',
      expUrl: 'DatSearchDatSampleField.do',
      hasPage: true,
      hasPrevNext: false,
      hasGridSelect: true,
      hasCopy: false,
      idPrevNext: 'parameterids'
    });
    
    me.callParent(arguments);
  },
  
  OnInitGridToolBar: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.callParent();
    
    var items = [
      ' ', tools.GetToolBarCombo('parameter', mep + 'parameter', 260, '检测参数', 60, tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE), gpersist.SELECT_MUST_VALUE),
      ' ', tools.GetToolBarCombo('source', mep + 'source', 160, '数据来源', 60, tools.ComboStore('ClassSource', gpersist.SELECT_MUST_VALUE), gpersist.SELECT_MUST_VALUE),
      ' ', tools.GetToolBarCombo('dsource', mep + 'dsource', 160, '取值来源', 60, tools.ComboStore('DataSource', gpersist.SELECT_MUST_VALUE), gpersist.SELECT_MUST_VALUE)
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnDeal', text: '模板导入', iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (!me.hasOtherSearch)
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo(tools.MustTitle('检测参数'), 'dsf.parameterids', mep + 'parameterids', tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE), '96%', false, 1),
          tools.FormText(tools.MustTitle('数据标识'), 'dsf.classfield', mep + 'classfield', 20, '96%', false, 3),
          tools.FormCombo(tools.MustTitle('取值来源'), 'dsf.datasource', mep + 'datasource', tools.ComboStore('DataSource', gpersist.SELECT_MUST_VALUE), '96%', false, 5)         
        ]},
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo(tools.MustTitle('数据来源'), 'dsf.classsource', mep + 'classsource', tools.ComboStore('ClassSource', gpersist.SELECT_MUST_VALUE), '100%', false, 2),
          tools.FormText(tools.MustTitle('数据名称'), 'dsf.classfieldname', mep + 'classfieldname', 20, '100%', false, 4),
          tools.FormCombo(tools.MustTitle('字段类型'), 'dsf.fieldtype', mep + 'fieldtype', tools.ComboStore('FieldType', gpersist.SELECT_MUST_VALUE), '100%', false, 6)
         ]}
      ]},
      { xtype: 'fieldcontainer', fieldLabel: '', layout: 'hbox', defaults: { labelStyle: 'font-weight:bold;', margins: '0 15 0 0' }, items: [
        tools.FormCheck(tools.MustTitle('是否分组相关'), 'dsf.isgroup', mep + 'isgroup', false, 8),
        tools.FormCheck(tools.MustTitle('是否试样相关'), 'dsf.isserial', mep + 'isserial', false, 9)]},
     
     {xtype: 'hiddenfield', name: 'dsf.deal.action', id: mep + 'datadeal'},
     {xtype: 'hiddenfield', name: 'dsf.parameternames', id: mep + 'parameternames'}
    
      
    ];
    
    me.disNews = [];
    me.disEdits = ['parameterids', 'classsource', 'classfield'];
    me.enNews = ['parameterids', 'classsource', 'classfield','classfieldname', 'fieldtype', 'isgroup', 'isserial', 
      'datasource'];
    me.enEdits = [ 'fieldtype', 'isgroup', 'isserial', 'datasource', 'parameterids','classfieldname'];
    
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments);
    
    tools.SetValue(mep + 'classsource', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'fieldtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'datasource', gpersist.SELECT_MUST_VALUE);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if (item && !Ext.isEmpty(item.parameterids)) {
      tools.SetValue(mep + 'classsource', item.classsource);
      tools.SetValue(mep + 'classfield',  item.classfield);
      tools.SetValue(mep + 'classfieldname',  item.classfieldname);
      tools.SetValue(mep + 'fieldtype', item.fieldtype);
      tools.SetValue(mep + 'isgroup', item.isgroup);
      tools.SetValue(mep + 'isserial', item.isserial);
      tools.SetValue(mep + 'datasource', item.datasource);
      tools.SetValue(mep + 'parameterids', item.parameterids);
      
      return true;
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'dsf.parameterids': tools.GetEncode(tools.GetValue(mep + 'parameter')),
          'dsf.classsource': tools.GetEncode(tools.GetValue(mep + 'source')),
          'dsf.datasource': tools.GetEncode(tools.GetValue(mep + 'dsource'))
        });
      });
    };
  },
  
  OnBeforeSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'parameternames', Ext.getCmp(mep + 'parameterids').getDisplayValue());
    return me.callParent(arguments);
  }
  
});