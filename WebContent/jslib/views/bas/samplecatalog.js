Ext.define('alms.samplecatalog', {
  extend: 'gpersist.base.listeditform',
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editInfo: '样品大类',
      winWidth: 750,
      winHeight:180,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassamplecatalog',
      storeUrl: 'BasSearchBasSampleCatalog.do',
      saveUrl: 'BasSaveBasSampleCatalog.do',
      expUrl: 'BasSearchBasSampleCatalog.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'samplecatalog',
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
          tools.FormText('样品大类编号', 'bscatalog.samplecatalog', mep + 'samplecatalog', 6, '96%', false, 1,'',90),
          tools.FormCombo('所属类别', 'bscatalog.samplecate', mep + 'samplecate', tools.ComboStore('SampleCate', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品大类名称', 'bscatalog.samplecatalogname', mep + 'samplecatalogname', 20, '100%', false, 1,'',90),
          {xtype:'hiddenfield',name:'bscatalog.deal.action',id: mep + 'datadeal'}
        ]}     
      ]}
    ];
    
    me.disNews = [];
    me.disEdits = ['samplecatalog'];
    me.enNews = [ 'samplecatalog', 'samplecatalogname','samplecate'];
    me.enEdits = ['samplecatalogname','samplecate'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品大类编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchsamplecatalog', id: mep + 'searchsamplecatalog', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品大类名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchsamplecatalogname', id: mep + 'searchsamplecatalogname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
          'bscatalog.samplecatalog': tools.GetEncode(tools.GetValue(mep + 'searchsamplecatalog')),
          'bscatalog.samplecatalogname': tools.GetEncode(tools.GetValue(mep + 'searchsamplecatalogname'))
        });
      });
    };
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
      
    me.callParent(arguments);
    tools.SetValue(mep + 'samplecate', gpersist.SELECT_MUST_VALUE);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'samplecatalog', item.samplecatalog);
    tools.SetValue(mep + 'samplecatalogname', item.samplecatalogname);
    tools.SetValue(mep + 'samplecate', item.samplecate);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'samplecatalog').reset();
    Ext.getCmp(mep + 'samplecatalog').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.samplecatalog) {
          tools.SetValue(mep + 'samplecatalog', action.result.data.samplecatalog);
        }
      }
    }
  }
  
});