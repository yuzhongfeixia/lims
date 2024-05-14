Ext.define('alms.basstore', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '贮存柜管理',
      winWidth: 750,
      winHeight: 180,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stkstore',
      storeUrl: 'PrdSearchStkStore.do',
      saveUrl: 'PrdSaveStkStore.do',
      expUrl: 'PrdSearchStkStore.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'storeid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('贮存柜编号', 'stkstore.storeid', mep + 'storeid', 6, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('贮存柜名称', 'stkstore.storename', mep + 'storename', 20, '100%', false, 2),
          {xtype:'hiddenfield',name:'stkstore.deal.action',id: mep + 'datadeal'}
        ]} ,
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [tools.FormCombo('所属检测室', 'stkstore.deptid', mep + 'deptid', tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE), '96%', false, 11)
        ]}    
      ]}
    ];
    
    me.disNews = [];
    me.disEdits = ['storeid'];
    me.enNews = [ 'storeid','storename','deptid'];
    me.enEdits = [ 'storename','deptid'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '贮存柜编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchstoreid', id: mep + 'searchstoreid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '贮存柜名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchstorename', id: mep + 'searchstorename', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'stkstore.storeid': tools.GetEncode(tools.GetValue(mep + 'searchstoreid')),
          'stkstore.storename': tools.GetEncode(tools.GetValue(mep + 'searchstorename'))
        });
      });
    };
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'storeid', item.storeid);
    tools.SetValue(mep + 'storename', item.storename);
    tools.SetValue(mep + 'deptid', item.deptid);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'storeid').reset();
    Ext.getCmp(mep + 'storeid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.storeid) {
          tools.SetValue(mep + 'storeid', action.result.data.storeid);
        }
      }
    }
  }
  
});